package com.analyzer.basic_block_approach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.analyzer.IRGeneratorForMIPS;
import com.analyzer.InstructionDetail;
import com.analyzer.Instructions;
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
	
	public List<InstructionDetail> getInstructionDetails() {
		return instructionDetails;
	}
	
	public Map<String, Integer> getIntVariableOccurances() {
		return intVariableOccurances;
	}
	
	public Map<String, Integer> getFloatVariableOccurances() {
		return floatVariableOccurances;
	}

	public List<String> getAnnotatedIR() {
		IRGeneratorForMIPS testing = new IRGeneratorForMIPS();
		return testing.getAnnotatedIR(intVariableOccurances, floatVariableOccurances, instructionDetails);
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