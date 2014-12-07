package com.analyzer.basic_block_approach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.analyzer.InstructionDetail;
import com.analyzer.Instructions;
import com.analyzer.basic_block_approach.ebb.EBB;
/**
 * @author saman
 * 
 */
public class BasicBlock {
	private List<InstructionDetail> instructionDetails;
	private Set<BasicBlock> predecessors, successors;
	private BasicBlock nextBasicBlock, previousBasicBlock;
	private Map<String, Integer> intVariableOccurances, floatVariableOccurances;
	private Set<String> returnVariableSet;
	public static int overallBlockId = 0;
	private int blockId;
	
	// Below 2 are used for EBB
	private boolean startOfEBB, endOfEBB;
	private EBB enclosingEBB;
	
	public BasicBlock() {
		instructionDetails = new ArrayList<>();
		predecessors = new HashSet<>();
		successors = new HashSet<>();
		intVariableOccurances = new Hashtable<>();
		floatVariableOccurances = new Hashtable<>();
		returnVariableSet = new HashSet<>();
		overallBlockId++;
		blockId = overallBlockId;
	}
	
	public boolean hasEnclosingEBB() {
		return enclosingEBB != null;
	}
	
	public void setEnclosingEBB(EBB enclosingEBB) {
		this.enclosingEBB = enclosingEBB;
	}
	
	public EBB getEnclosingEBB() {
		return enclosingEBB;
	}
	
	public void setAsStartOfEBB() {
		startOfEBB = true;
	}
	
	public void setAsEndOfEBB() {
		endOfEBB = true;
	}
	
	public boolean isStartOfEBB() {
		return startOfEBB;
	}
	
	public boolean isEndOfEBB() {
		return endOfEBB;
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
	
	public Set<BasicBlock> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(Set<BasicBlock> predecessors) {
		this.predecessors = predecessors;
	}

	public Set<BasicBlock> getSuccessors() {
		return successors;
	}

	public void setsuccessors(Set<BasicBlock> successors) {
		this.successors = successors;
	}

	/**
	 * Depending on if the instruction is RETURN or not update the 
	 * variable maps for int or float. 
	 * Since RHS and LHS have different behaviors (can have multiple 
	 * variables ins RHS) we have to treat them differently
	 * 
	 *  But no matter what, add to the basic block's instruction detail list
	 */
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

	/**
	 * LHS is only 1 therefore see if it's a variable (i.e. non-literal) 
	 * update its corresponding variable map
	 * 
	 * TODO countLHS and countRHS can potentially be combined
	 */
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

	/**
	 * if RHS and if a var happens two times, only record it once, because
	 * one load suffices
	 * 
	 * TODO countLHS and countRHS can potentially be combined
	 */
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