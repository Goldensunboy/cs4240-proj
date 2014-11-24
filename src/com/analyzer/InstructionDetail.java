package com.analyzer;

public class InstructionDetail {
	
	private String[] splitedInstruction;
	private Instructions instruction;
	
	public InstructionDetail(String line) {
		this.splitedInstruction = line.split(", ");
		String instructionName = splitedInstruction[0];
		instructionName = instructionName.matches("^LABEL*") ? Instructions.LABEL.getName() : instructionName;
		System.out.println("----------- " +instructionName + " -----------");
		this.instruction = Instructions.valueOf(instructionName);		
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
	
	public boolean hasLHS() {
		return instruction.hasLHS();
	}
	
	public boolean hasRHS() {
		return instruction.hasRHS();
	}
}
