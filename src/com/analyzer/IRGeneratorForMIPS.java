package com.analyzer;

import static com.analyzer.InstructionUtility.generateLoad;
import static com.analyzer.InstructionUtility.generateStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class IRGeneratorForMIPS {
	
	private List<String> getLoadStoreRegisters(Map<String, String> annotatedVariablesWithRegister,
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
	
	private String generateLoadInstruction(String variableName, String registerName, LOAD_STORE isLoad) {
		return (isLoad == LOAD_STORE.LOAD ? "load, " : "store, ") + variableName + ", " + registerName;
	}
	
	private List<String> getTemporaryLoadStoreRegisters(String[] variablesNeedLoadStore, Map<String, String> temporaryVariablesRegisterMap, LOAD_STORE isLoad) {
		List<String> loadsOrStores = new ArrayList<>();
		if(variablesNeedLoadStore != null) { 
			for(String variableName : variablesNeedLoadStore) {
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
	
	private String manageRegisters(InstructionDetail instructionDetail, Map<String, String> variablesRegisterMap, 
			Map<String, String> temporaryVariablesRegisterMap, Map<String, String> promotedRegisters) {
		String[] splitedInstruction = instructionDetail.getOriginalInstruction().split(", ");
		for(int i=0; i<splitedInstruction.length; i++) {
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
	
	
	public List<String> getAnnotatedIR(Map<String, Integer> intVariableOccurances, Map<String, Integer> floatVariableOccurances, List<InstructionDetail> instructionDetails) {
		RegisterFactory registerFactory = new RegisterFactory(intVariableOccurances, floatVariableOccurances);
		Map<String, String> variablesRegisterMap = registerFactory.getRegisterMap();
		
		List<String> annotatedIR = getLoadStoreRegisters(variablesRegisterMap, LOAD_STORE.LOAD);
		for(InstructionDetail instructionDetail : instructionDetails) {
			
			if(instructionDetail.getInstructionName().equals(Instructions.RETURN.getName())) {
				continue; // TODO deal with this later
			}
			
			String[] variablesNeedLoad = instructionDetail.getRHS();
			String lhs = instructionDetail.getLHS();
			String[] variablesNeedStore = {lhs}; // TODO this need refactoring
			if(lhs == null) {
				variablesNeedStore = null;
			}

			Map<String, String> temporaryVariablesRegisterMap = registerFactory.createTemporaryRegisterMap(variablesNeedLoad, variablesNeedStore);  

			annotatedIR.addAll(getTemporaryLoadStoreRegisters(variablesNeedLoad, temporaryVariablesRegisterMap, LOAD_STORE.LOAD));
			
			Map<String, String> registersToPromote = registerFactory.getRegistersToPromotion(variablesNeedLoad, variablesNeedStore, temporaryVariablesRegisterMap);
			Map<String, String> promotedRegisters = registerFactory.getPromotedRegisters(registersToPromote);
			
			annotatedIR.addAll(registerFactory.getPromotions(registersToPromote, promotedRegisters));

			if(instructionDetail.isBranch()) {
				annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD_STORE.STORE));
			} 

			annotatedIR.add(manageRegisters(instructionDetail, variablesRegisterMap, temporaryVariablesRegisterMap, promotedRegisters));
			
			if(!instructionDetail.isBranch()){
				annotatedIR.addAll(getTemporaryLoadStoreRegisters(variablesNeedStore, temporaryVariablesRegisterMap, LOAD_STORE.STORE));				
			}
			
			registerFactory.resetAvailableTemporaryRegisterIndex();
		}
		
		if(!instructionDetails.get(instructionDetails.size()-1).isBranch()) {
			annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD_STORE.STORE));
		}
		
		//TODO testing - delete me later
		annotatedIR.add("");
		
		return annotatedIR;
	}
}