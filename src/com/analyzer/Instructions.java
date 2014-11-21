package com.analyzer;

import java.util.Arrays;

public enum Instructions {
	ASSIGN ("assign", 2, true),
	ADD ("add", 3, true),
	SUB ("sub", 3, true),
	MULT ("mult", 3, true),
	DIV ("div", 3, true),
	AND ("and", 3, true),
	OR ("or", 3, true),
	GOTO ("goto", 1, false),
	BREQ ("breq", 3, false),
	BRNEQ ("brneq", 3, false),
	BRLT ("brlt", 3, false),
	BRGT ("brgt", 3, false),
	BRGEQ ("brgeq", 3, false),
	BRLEQ ("brleq", 3, false),
	RETURN ("return", 1, false),
	CALL ("call", 2, false),
	CALLR ("callr", 5, true),
	ARRAY_STORE ("array_store", 3, true), // should it? the LHS is an element of the array
	ARRAY_LOAD ("array_load", 3, true)
	// Array Store??? why is it assign??? why Santosh!!! 
	;
	
	private String name;
	private int expectedOperands;
	private boolean hasLHS;
	Instructions(String name, int expectedOperands, boolean hasLHS){
		this.name = name;
		this.expectedOperands = expectedOperands;
		this.hasLHS = hasLHS;
	}
	
	public String getInstructionName(String instruction) {
		return instruction.split(",")[0];
	}
	
	public String getLHS(String instruction) {
		return instruction.split(",")[1];
	}
	
	public String[] getRHS(String instruction) {
		String[] splitedInstruction = instruction.split(",");
		int startIndex;
		
		if(hasLHS) {
			startIndex = 2;
		} else {
			startIndex = 1;
		}
		
		return Arrays.copyOfRange(splitedInstruction, startIndex, splitedInstruction.length);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getExpectedOperands() {
		return expectedOperands;
	}
	
	public void setExpectedOperands(int expectedOperands) {
		this.expectedOperands = expectedOperands;
	}
}
