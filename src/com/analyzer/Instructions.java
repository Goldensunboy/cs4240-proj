package com.analyzer;

import com.exception.BadDeveloperException;



public enum Instructions {
	
	ASSIGN ("assign", 1, 2, 3, -1),
	ADD ("add", 3, 1, 3, -1),
	SUB ("sub", 3, 1, 3, -1),
	MULT ("mult", 3, 1, 3, -1),
	DIV ("div", 3, 1, 3, -1),
	AND ("and", 3, 1, 3, -1),
	OR ("or", 3, 1, 3, -1),
	GOTO ("goto", -1, -1, -1, 1),
	BREQ ("breq", -1, 1, 3, 3),
	BRNEQ ("brneq", -1, 1, 3, 3),
	BRLT ("brlt", -1, 1, 3, 3),
	BRGT ("brgt", -1, 1, 3, 3),
	BRGEQ ("brgeq", -1, 1, 3, 3),
	BRLEQ ("brleq", -1, 1, 3, 3),
	RETURN ("return", -1, 1, 2, -1),
	CALL ("call", -1, 1, 2, 1),
	CALLR ("callr", 1, 3, 6, 2),
	ARRAY_STORE ("array_store", 1, 2, 4, -1), // NIY
	ARRAY_LOAD ("array_load", 1, 2, 4, -1), // NIY
	ARRAY_ASSIGN ("array_assign", 1, 3, 4, -1),
	LABEL("label", -1, -1, -1, 0),
	FUNC("func", -1, -1, -1, 0),
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

	public int getLhsIndex() {
		return lhsIndex;
	}
	
	public int getRhsStartIndex() {
		return rhsStartIndex;
	}
	
	public int getRhsEndIndex() {
		return rhsEndIndex;
	}
	
	public int getLabelIndex() {
		return labelIndex;
	}

	public String getName() {
		return name;
	}
	
	// Array specific methods
	public int getArrayNameIndex() {
		switch (this) {
		case ARRAY_ASSIGN:
			return 1;
		case ARRAY_STORE:
			return 1;
		case ARRAY_LOAD:
			return 2;
		}
		throw new BadDeveloperException("This method is only for arrays, don't use it with any other instruction");
	}
	
	public int getArrayIndex() {
		if(this == ARRAY_LOAD) {
			return 3;
		}
		
		throw new BadDeveloperException("This method is only for ARRAY_LOAD. Don't use it with anything else");
	}
}
