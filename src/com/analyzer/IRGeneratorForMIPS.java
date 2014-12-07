package com.analyzer;

import static com.analyzer.InstructionUtility.generateLoad;
import static com.analyzer.InstructionUtility.generateStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.compiler.Type;

import static com.analyzer.Instructions.*;
import static com.analyzer.LOAD_STORE.*;

public class IRGeneratorForMIPS {
	
	private static List<String> getLoadStoreRegisters(Map<String, String> annotatedVariablesWithRegister,
			LOAD_STORE isLoad) {
		List<String> loadInstructions = new ArrayList<>();
		for(Entry<String, String> annotatedVariable : annotatedVariablesWithRegister.entrySet()) {
			String variableName = annotatedVariable.getKey();
			String registerName = annotatedVariable.getValue();
			String loadInstruction = generateLoadInstruction(variableName, registerName, isLoad);
			loadInstructions.add(loadInstruction);
		}
		return loadInstructions;
	}
	
	private static String getLoadStoreRegistersWithFilter(String variableName, Map<String, String> annotatedVariablesWithRegister,
			LOAD_STORE isLoad) {
		String registerName = annotatedVariablesWithRegister.get(variableName);
		return generateLoadInstruction(variableName, registerName, isLoad);
	}
	
	private static String generateLoadInstruction(String variableName, String registerName, LOAD_STORE isLoad) {
		return (isLoad == LOAD_STORE.LOAD ? "load, " : "store, ") + variableName + ", " + registerName;
	}
	
	private static List<String> getTemporaryLoadStoreRegisters(String[] variablesNeedLoadStore, Map<String, String> temporaryVariablesRegisterMap, LOAD_STORE isLoad) {
		List<String> loadsOrStores = new ArrayList<>();		
		if(variablesNeedLoadStore != null) { 
			
			//TODO this is a hack. Very inefficient
			Set<String> vars = new HashSet<>();
			for(String var : variablesNeedLoadStore) {
				vars.add(var);
			}
			
			for(String variableName : vars) {
				if(temporaryVariablesRegisterMap.containsKey(variableName)) {
					if(isLoad == LOAD_STORE.LOAD) {
						loadsOrStores.add(generateLoad(variableName, temporaryVariablesRegisterMap.get(variableName)));					
					} else {
						loadsOrStores.add(generateStore(variableName, temporaryVariablesRegisterMap.get(variableName)));
					}
				}
			}
		}
		return loadsOrStores;
	}
	
	private static String manageRegisters(InstructionDetail instructionDetail, Map<String, String> variablesRegisterMap, 
			Map<String, String> temporaryVariablesRegisterMap, Map<String, String> promotedRegisters) {
		String[] splitedInstruction = instructionDetail.getOriginalInstruction().split(", ");
		
		int makeRoomForArrayName =  0;
		if(instructionDetail.isAnyOfInstructions(ARRAY_ASSIGN, ARRAY_LOAD, ARRAY_STORE)) {
			makeRoomForArrayName = 1;
		}
		for(int i=0; i<splitedInstruction.length - makeRoomForArrayName; i++) {
			String variableName = splitedInstruction[i];
			String replacement = promotedRegisters.get(variableName);
			if(replacement == null) {				
				replacement = temporaryVariablesRegisterMap.get(variableName);
			}
			if(replacement == null) {
				replacement = variablesRegisterMap.get(variableName);
			}
			if(replacement != null) {
				splitedInstruction[i] = replacement;
			}
		}
		
		String newInstruction = splitedInstruction[0];
		for(int i=1; i<splitedInstruction.length; i++) {
			newInstruction += ", " + splitedInstruction[i];
		}
		
		return newInstruction;
	}
	
	public static List<String> getAnnotatedIR(Map<String, Integer> intVariableOccurances, Map<String, Integer> floatVariableOccurances, 
			List<InstructionDetail> instructionDetails, boolean generateLoad, boolean generateStore) {
		RegisterFactory registerFactory = new RegisterFactory(intVariableOccurances, floatVariableOccurances);
		Map<String, String> variablesRegisterMap = registerFactory.getRegisterMap();

		List<String> annotatedIR = new ArrayList<>();

		Instructions[] branchGotoInstructions = {BREQ, BRGEQ, BRGT, BRLEQ, BRLT, BRNEQ, GOTO};
		Instructions[] dontNeedStoreAfter = {BREQ, BRGEQ, BRGT, BRLEQ, BRLT, BRNEQ, GOTO, CALL, CALLR, RETURN};
		
		if(generateLoad && !instructionDetails.get(0).isAnyOfInstructions(LABEL, FUNC)){
			//if start isn't a label or a function load whatever needed, cuz ain't nobody got time for that
			annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD));
		}
		
		/*
		 * Note: the generateStore, generateLoad flags help in the case of 
		 * naive allocation to turn off loads and store if necessary
		 */
		for(InstructionDetail instructionDetail : instructionDetails) {
			if(generateStore && instructionDetail.isAnyOfInstructions(RETURN, CALL, CALLR)) {
				//if we see the above instructions we have to store
				annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, STORE));
				annotatedIR.add(instructionDetail.getOriginalInstruction());
				if(instructionDetail.isAnyOfInstructions(CALLR)) {
					if(variablesRegisterMap.isEmpty()) {
						Map<String, String> temporaryVariablesRegisterMap = registerFactory.createTemporaryRegisterMap(new String[]{instructionDetail.getLHS()}, null);
						annotatedIR.add(getLoadStoreRegistersWithFilter(instructionDetail.getLHS(), temporaryVariablesRegisterMap, LOAD));
						registerFactory.resetAvailableTemporaryRegisterIndex();
					} else {						
						annotatedIR.add(getLoadStoreRegistersWithFilter(instructionDetail.getLHS(), variablesRegisterMap, LOAD));
					}
				}
				
			} else if(generateLoad && instructionDetail.isAnyOfInstructions(LABEL, FUNC)){
				//else if start a label or a function load whatever needed
				annotatedIR.add(instructionDetail.getOriginalInstruction());
				annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD));
			} else {
				//else it's a normal operation that needs work on RHS and LHS variables
				
				String[] variablesNeedLoad = instructionDetail.getRHS();
				String lhs = instructionDetail.getLHS();
				String[] variablesNeedStore = {lhs}; // TODO this needs refactoring
				if(lhs == null) {
					variablesNeedStore = null;
				}

				Map<String, String> temporaryVariablesRegisterMap = registerFactory.createTemporaryRegisterMap(variablesNeedLoad, variablesNeedStore);  
				annotatedIR.addAll(getTemporaryLoadStoreRegisters(variablesNeedLoad, temporaryVariablesRegisterMap, LOAD));
				
				if(instructionDetail.isAnyOfInstructions(ARRAY_ASSIGN, ARRAY_LOAD, ARRAY_STORE)) {
					/*
					 *  Hacky but YOLO.
					 *  arrays are as weird as Kanye West. The lhs (a.k.a variablesNeedStore) are the ones who need
					 *  to be loaded. Therefore, the variablesNeedStore are the ones needed to be loaded.
					 */
					
					// See if the type of the array is fixedpt (only applicable to array_store and array_assign)
					String[] variablesNeedPromotion = null;
					String arrayName = instructionDetail.getArrayName();
					boolean isFloatArray = arrayName.matches(".*"+Type.ARRAY.getSuffix() + Type.FIXPT.getNoPercentSuffix());
					if(isFloatArray && instructionDetail.isAnyOfInstructions(ARRAY_STORE, ARRAY_ASSIGN)) {
						String variableOrLiteralForPermotion = instructionDetail.getVariableOrLiteralForPermotion();
						if(InstructionUtility.isIntIsh(variableOrLiteralForPermotion)) {
							variablesNeedPromotion = new String[]{variableOrLiteralForPermotion};
						}
					}
					
					String[] fakeLHS = {"fakeLHSToForcePromotionOfInt%f"};
					Map<String, String> registersToPromote = registerFactory.getRegistersToPromotion(variablesNeedPromotion, fakeLHS, temporaryVariablesRegisterMap);
					Map<String, String> promotedRegisters = registerFactory.getPromotedRegisters(registersToPromote);
					annotatedIR.addAll(registerFactory.getPromotions(registersToPromote, promotedRegisters));
					if(instructionDetail.isAnyOfInstructions(ARRAY_LOAD, ARRAY_STORE)) {
						annotatedIR.addAll(getTemporaryLoadStoreRegisters(new String[]{instructionDetail.getArrayName()}, temporaryVariablesRegisterMap, LOAD));
					}
					annotatedIR.add(manageRegisters(instructionDetail, variablesRegisterMap, temporaryVariablesRegisterMap, promotedRegisters));

				} else {
					Map<String, String> registersToPromote = registerFactory.getRegistersToPromotion(variablesNeedLoad, variablesNeedStore, temporaryVariablesRegisterMap);
					Map<String, String> promotedRegisters = registerFactory.getPromotedRegisters(registersToPromote);
					
					annotatedIR.addAll(registerFactory.getPromotions(registersToPromote, promotedRegisters));
					boolean isBranch = generateStore && instructionDetail.isAnyOfInstructions(branchGotoInstructions);
					if(isBranch){
						annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, STORE));
					}
					
					annotatedIR.add(manageRegisters(instructionDetail, variablesRegisterMap, temporaryVariablesRegisterMap, promotedRegisters));
					
					if(!isBranch) {					
						annotatedIR.addAll(getTemporaryLoadStoreRegisters(variablesNeedStore, temporaryVariablesRegisterMap, STORE));				
					}
				}
				registerFactory.resetAvailableTemporaryRegisterIndex();
			}
			
		}
		
		if(generateStore && !instructionDetails.get(instructionDetails.size()-1).isAnyOfInstructions(dontNeedStoreAfter)) {
			annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, STORE));
		}
		
		//TODO testing - delete me later
		annotatedIR.add("");
		
		return annotatedIR;
	}
}