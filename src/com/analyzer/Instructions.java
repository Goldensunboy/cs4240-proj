package com.analyzer;

import java.util.Arrays;


public enum Instructions {
	
	ASSIGN ("assign", 0, 1, 1, -1),
	ADD ("add", 3, 1, 2, -1),
	SUB ("sub", 3, 1, 2, -1),
	MULT ("mult", 3, 1, 2, -1),
	DIV ("div", 3, 1, 2, -1),
	AND ("and", 3, 1, 2, -1),
	OR ("or", 3, 1, 2, -1),
	GOTO ("goto", -1, -1, -1, 1),
	BREQ ("breq", -1, 1, 2, 2),
	BRNEQ ("brneq", -1, 2, 1, 2),
	BRLT ("brlt", -1, 1, 2, 2),
	BRGT ("brgt", -1, 1, 2, 2),
	BRGEQ ("brgeq", -1, 1, 2, 2),
	BRLEQ ("brleq", -1, 1, 2, 2),
	RETURN ("return", -1, 1, 1, -1),
	CALL ("call", -1, 1, 1, 1),
	CALLR ("callr", 1, 3, 5, 2),
	ARRAY_STORE ("array_store", -1, -1, -1, -1), // NIY
	ARRAY_LOAD ("array_load", -1, -1, -1, -1), // NIY
	// Array Store??? why is it assign??? why Santosh!!! 
	LABEL("label", -1, -1, -1, 1)
	;
	
	private final String name;
	private final int lhsIndex, rhsStartIndex, rhsEndIndex, labelIndex;
	Instructions(String name, int lhsIndex, int rhsStartIndex, int rhsEndIndex, int labelIndex){
		this.name = name;
		this.lhsIndex =lhsIndex;
		this.rhsStartIndex = rhsStartIndex;
		this.rhsEndIndex = rhsEndIndex;
		this.labelIndex = labelIndex;
	}
	
	public boolean hasLHS() {
		return lhsIndex != -1;
	}
	
	public boolean hasRHS() {
		return rhsStartIndex != -1;
	}

	public boolean isLabel() {
		return this.getName() != LABEL.getName();
	}
	
	public boolean isControlFlow() {
		return isLabel() && labelIndex != -1;
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
	
	public String getName() {
		return name;
	}
	
}
