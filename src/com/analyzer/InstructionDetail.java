package com.analyzer;

import java.util.Arrays;

public class InstructionDetail {
	
	private String[] splitedInstruction;
	private Instructions instruction;
	
	public InstructionDetail(String line) {
		this.splitedInstruction = line.split(",");
		String instructionName = splitedInstruction[0];
		instructionName = instructionName.matches("^LABEL*") ? Instructions.LABEL.getName() : instructionName;
		this.instruction = Instructions.valueOf(instructionName);		
	}
	
	public String getInstructionName() {
		return splitedInstruction[0];
	}
	
	public String getLHS() {
		// index 1 is the index of LHS if exists
		return splitedInstruction[1];
	}
	
	public String[] getRHS() {
		int startIndex;
		
		if(instruction.hasLHS()) {
			startIndex = 2;
		} else {
			startIndex = 1;
		}
		
		return Arrays.copyOfRange(splitedInstruction, startIndex, splitedInstruction.length);
	}

	public boolean hasLHS() {
		return instruction.hasLHS();
	}
	
}
