package com.analyzer.cfg;

import static com.analyzer.InstructionUtility.generateLoad;
import static com.analyzer.InstructionUtility.generateStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.analyzer.IRGeneratorForMIPS;
import com.analyzer.InstructionDetail;
import com.analyzer.Instructions;
import com.analyzer.LOAD_STORE;
/**
 * @author saman
 * 
 */
public class BasicBlock {
	private List<InstructionDetail> instructionDetails;
	private List<BasicBlock> predecessors, successors;
	private BasicBlock nextBasicBlock, previousBasicBlock;
	private Map<String, Integer> intVariableOccurances, floatVariableOccurances;
	private Set<String> returnVariableSet;
	public static int overallBlockId = 0;
	private int blockId;
	private boolean hasBranch = false;
	
	public BasicBlock() {
		instructionDetails = new ArrayList<>();
		predecessors = new ArrayList<>();
		successors = new ArrayList<>();
		intVariableOccurances = new Hashtable<>();
		floatVariableOccurances = new Hashtable<>();
		returnVariableSet = new HashSet<>();
		overallBlockId++;
		blockId = overallBlockId;
	}
	
	public Map<String, Integer> getIntVariableOccurances() {
		return intVariableOccurances;
	}
	
	public Map<String, Integer> getFloatVariableOccurances() {
		return floatVariableOccurances;
	}

	public List<String> getAnnotatedIR() {
//		IRGeneratorForMIPS testing = new IRGeneratorForMIPS();
//		return testing.getAnnotatedIR(intVariableOccurances, floatVariableOccurances, instructionDetails);
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
			annotatedIR.add(manageRegisters(instructionDetail, variablesRegisterMap, temporaryVariablesRegisterMap, promotedRegisters));
			annotatedIR.addAll(getTemporaryLoadStoreRegisters(variablesNeedStore, temporaryVariablesRegisterMap, LOAD_STORE.STORE));
			registerFactory.resetAvailableTemporaryRegisterIndex();
		}
		
		if(hasBranch()) {
			String lastInstruction = annotatedIR.remove(annotatedIR.size()-1);
			annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD_STORE.STORE));
			annotatedIR.add(lastInstruction);
		} else {
			annotatedIR.addAll(getLoadStoreRegisters(variablesRegisterMap, LOAD_STORE.STORE));
		}
		
		return annotatedIR;
	}
	
	private boolean hasBranch() {
		return hasBranch;
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
	
	public void setNextBasicBlock(BasicBlock nextBasicBlock) {
		this.nextBasicBlock = nextBasicBlock;
	}
	
	public BasicBlock getNextBasicBlock() {
		return nextBasicBlock;
	}

	public void setPreviousBasicBlock(BasicBlock previousBasicBlock) {
		this.previousBasicBlock = previousBasicBlock;
	}
	
	public BasicBlock getPreviousBasicBlock() {
		return previousBasicBlock;
	}
	
	public boolean hasPredecessor() {
		return predecessors.size() != 0;
	}
	
	public boolean hasSuccessor() {
		return successors.size() != 0;
	}
	
	public void addToPredecessors(BasicBlock predecessor) {
		predecessors.add(predecessor);
	}
	
	public void addToSuccessors(BasicBlock successor) {
		successors.add(successor);
	}
	
	public List<BasicBlock> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(List<BasicBlock> predecessors) {
		this.predecessors = predecessors;
	}

	public List<BasicBlock> getSuccessors() {
		return successors;
	}

	public void setsuccessors(List<BasicBlock> successors) {
		this.successors = successors;
	}

	public void addInstructionDetail(InstructionDetail instructionDetail) {
		
		if(instructionDetail.isReturn()) {
			//TODO - more work needed for return values
			if (instructionDetail.getOriginalInstruction().equals(Instructions.RETURN.getName())) {
				returnVariableSet.add(null);
			} else {
				returnVariableSet.add(instructionDetail.getLHS());				
			}
		} else {
			if(instructionDetail.isBranch()) {
				hasBranch = true;
				// TODO - do this when making the instructions
			}
			if(instructionDetail.hasLHS()) {
				countLhsVariablesOccurances(instructionDetail);
			}
			
			if(instructionDetail.hasRHS()) {
				countRhsVariablesOccurances(instructionDetail);
			}
		}
		instructionDetails.add(instructionDetail);		
	}

	private void countLhsVariablesOccurances(InstructionDetail instructionDetail) {
		String variableName = instructionDetail.getLHS();
		if(isVariable(variableName)) {

			Map<String, Integer> variableOccurances = whichVariableOccuranceMap(variableName);
			
			Integer occuranceCount = variableOccurances.get(variableName);
			if(occuranceCount == null) {
				occuranceCount = 0; 
			}
			occuranceCount++;
			variableOccurances.put(variableName, occuranceCount);
		}		
	}
	
	private void countRhsVariablesOccurances(InstructionDetail instructionDetail) {
		Set<String> variableToUpdateOccurances = new HashSet<>();
		for(String variableName : instructionDetail.getRHS()) {
			if(isVariable(variableName)) {					
				variableToUpdateOccurances.add(variableName);
			}
		}

		for(String variableName : variableToUpdateOccurances) {
			
			Map<String, Integer> variableOccurances = whichVariableOccuranceMap(variableName);
			
			Integer occuranceCount = variableOccurances.get(variableName);
			if(occuranceCount == null) {
				occuranceCount = 0; 
			}
			occuranceCount++;
			variableOccurances.put(variableName, occuranceCount);
		}		
	}
	
	private Map<String, Integer> whichVariableOccuranceMap(String variableName) {

		boolean isInt = variableName.split("%")[1].equals("i");
		if(isInt) {
			return intVariableOccurances;
		} 
		
		return floatVariableOccurances;
	}
	private boolean isVariable(String variableName) {
		return variableName != null && variableName.split("\\$").length > 1;
	}
	
	public String toString() {
		String successorList = "Successor ID's: ";
		for (BasicBlock successor : successors) {
			successorList += successor.blockId + ", ";
		}
		String predecessorList = "Predecessor ID's: ";
		for (BasicBlock predecessor : predecessors) {
			predecessorList += predecessor.blockId + ", ";
		}
		String retVal = blockId + " || " + successorList;
		retVal += predecessorList+ "\n";
		retVal += instructionDetails + "\n";
		return retVal;
	}
}
