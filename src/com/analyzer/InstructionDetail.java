package com.analyzer;

import java.util.Arrays;

public class InstructionDetail {
	
	private String[] splitedInstruction;
	private Instructions instruction;
	
	public InstructionDetail(String line) {
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
	}
	
	public String getInstructionName() {
		return splitedInstruction[0];
	}
	
	public String getLHS() {
		return instruction.getLHS(splitedInstruction);
	}
	
	public String[] getRHS() {
		return instruction.getRHS(splitedInstruction);
	}

	public boolean isControlFlow() {
		return instruction.isControlFlow();
	}
	
	public String getLabel() {
		return instruction.getLabel(splitedInstruction);
	}
	
	public boolean isLabel() {
		return instruction.isLabel();
	}
	
	public boolean hasLHS() {
		return instruction.hasLHS();
	}
	
	public boolean hasRHS() {
		return instruction.hasRHS();
	}
	
	public boolean letsFallThrough() {
		return Instructions.GOTO.equals(instruction);
	}
	
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(Arrays.toString(splitedInstruction));
		strBuffer.append("\t");
		
		strBuffer.append(instruction);
		strBuffer.append("\t");

		String lhs = instruction.hasLHS() ? instruction.getLHS(splitedInstruction) : null;
		strBuffer.append(lhs);
		strBuffer.append("\t");
		
		String [] rhs = instruction.hasRHS() ? instruction.getRHS(splitedInstruction) : null;
		strBuffer.append(Arrays.toString(rhs));
		strBuffer.append("\t");
		
		String label = instruction.hasLabel() ? instruction.getLabel(splitedInstruction) : null;
		strBuffer.append(label);
		strBuffer.append("\n");
		
		return strBuffer.toString();
	}
}
