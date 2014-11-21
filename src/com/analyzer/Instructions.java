package com.analyzer;


public enum Instructions {
	ASSIGN ("assign", 2, true, false),
	ADD ("add", 3, true, false),
	SUB ("sub", 3, true, false),
	MULT ("mult", 3, true, false),
	DIV ("div", 3, true, false),
	AND ("and", 3, true, false),
	OR ("or", 3, true, false),
	GOTO ("goto", 1, false, true),
	BREQ ("breq", 3, false, true),
	BRNEQ ("brneq", 3, false, true),
	BRLT ("brlt", 3, false, true),
	BRGT ("brgt", 3, false, true),
	BRGEQ ("brgeq", 3, false, true),
	BRLEQ ("brleq", 3, false, true),
	RETURN ("return", 1, false, true),
	CALL ("call", 2, false, true),
	CALLR ("callr", 5, true, true),
	ARRAY_STORE ("array_store", 3, true, false), // should it? the LHS is an element of the array
	ARRAY_LOAD ("array_load", 3, true, false),
	// Array Store??? why is it assign??? why Santosh!!! 
	LABEL("label", 0, false, true)
	;
	
	private final String name;
	private final boolean hasLHS, isControlFlow;
	private final int expectedOperands;
	
	Instructions(String name, int expectedOperands, boolean hasLHS, boolean isControlFlow){
		this.name = name;
		this.expectedOperands = expectedOperands;
		this.hasLHS = hasLHS;
		this.isControlFlow = isControlFlow;
	}
	
	public String getName() {
		return name;
	}

	public int getExpectedOperands() {
		return expectedOperands;
	}

	public boolean hasLHS() {
		return hasLHS;
	}
	
	public boolean isControlFlow() {
		return isControlFlow;
	}
}
