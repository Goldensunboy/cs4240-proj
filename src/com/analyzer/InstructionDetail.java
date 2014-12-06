package com.analyzer;

import static com.analyzer.Instructions.*;

import java.util.Arrays;

public class InstructionDetail {
	
	private String[] splitedInstruction;
	private Instructions instruction;
	private String originalInstruction;	
	private static Instructions[] branchGotoInstructions = {BREQ, BRGEQ, BRGT, BRLEQ, BRLT, BRNEQ, GOTO};
	private static Instructions[] dontNeedStoreAfter = {BREQ, BRGEQ, BRGT, BRLEQ, BRLT, BRNEQ, GOTO, CALL, CALLR, RETURN};
	private static Instructions[] controlFlowInstructions = {GOTO, BREQ, BRNEQ, BRLT, BRGT, BRGEQ, BRLEQ, FUNC, LABEL};
	private static Instructions[] instructionsWithLabel = {GOTO, BREQ, BRNEQ, BRLT, BRGT, BRGEQ, BRLEQ, CALL, CALLR, LABEL, FUNC};
	private int lhsIndex, rhsStartIndex, rhsEndIndex, labelIndex;
		
	
	public InstructionDetail(String line) {
		this.originalInstruction = line;
		this.splitedInstruction = line.split(", ");
		String instructionName = splitedInstruction[0];
		if(instructionName.matches("^LABEL.*")) {
			splitedInstruction[0] = splitedInstruction[0].split(":")[0];
			instructionName =  Instructions.LABEL.getName(); 
		}
		if(instructionName.matches("^FUNC.*")) {			
			instructionName = Instructions.FUNC.getName();
		}
		this.instruction = Instructions.valueOf(instructionName.toUpperCase());
		lhsIndex = instruction.getLhsIndex();
		rhsStartIndex = instruction.getRhsStartIndex();
		rhsEndIndex = instruction.getRhsEndIndex();
		labelIndex = instruction.getLabelIndex();
	}
	
	public boolean isBranch() {
		return isAnyOfInstructions(branchGotoInstructions);
	}
	
	public boolean doesNeedStoreAfter() {
		return isAnyOfInstructions(dontNeedStoreAfter);
	}

	public boolean letsFallThrough() {
		return !isAnyOfInstructions(GOTO);
	}
	
	public boolean isReturn() {
		return isAnyOfInstructions(RETURN);
	}
	
	public boolean isLabel() {
		return isAnyOfInstructions(LABEL);
	}
	
	public boolean hasLabel() {
		return isAnyOfInstructions(instructionsWithLabel);
	}
	
	public boolean isControlFlow() {
		return isAnyOfInstructions(controlFlowInstructions);
	}
	
	public String getOriginalInstruction() {
		return originalInstruction;
	}
	
	public String getInstructionName() {
		return splitedInstruction[0];
	}
	
	public Instructions getInstruction() {
		return instruction;
	}
	
	public String getLHS() {
		if(hasLHS()) {			
			return getLHS(splitedInstruction);
		}
		return null;
	}
	
	public String[] getRHS() {
		if(hasRHS()) {
			return getRHS(splitedInstruction);
		}
		return null;
	}

	public String getLabel() {
		if(hasLabel()) {			
			return splitedInstruction[labelIndex];
		}
		return null;
	}
	
	public boolean isAnyOfInstructions(Instructions ... instructions) {
		for (Instructions instruction : instructions) {
			if(instruction.equals(this.instruction)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasLHS() {
		return lhsIndex != -1;
	}
	
	public boolean hasRHS() {
		return rhsStartIndex != -1;
	}
	
	public String getLHS(String[] splitedInstruction) {
		return splitedInstruction[lhsIndex];
	}
	
	public String[] getRHS(String[] splitedInstruction) {
		return Arrays.copyOfRange(splitedInstruction, rhsStartIndex, rhsEndIndex);
	}
	
	public String getLabel(String[] splitedInstruction) {
		return splitedInstruction[labelIndex];
	}
	
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(Arrays.toString(splitedInstruction));
		strBuffer.append("\t");
		
		strBuffer.append(instruction);
		strBuffer.append("\t");

		String lhs = getLHS();
		strBuffer.append(lhs);
		strBuffer.append("\t");
		
		String [] rhs = getRHS();
		strBuffer.append(Arrays.toString(rhs));
		strBuffer.append("\t");
		
		String label = getLabel();
		strBuffer.append(label);
		strBuffer.append("\n");
		
		return strBuffer.toString();
	}
}
