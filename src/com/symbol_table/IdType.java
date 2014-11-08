package com.symbol_table;

public enum IdType {
	FUNCTION_NAME("Function name", false),
	VARIABLE_NAME("Variable name", true),
	USER_DEFINED_TYPE("User defined type", false),
	FUNCTION_PARAMETER("Function parameter", false),
	FUNCTION_ARGUMENT("Function argument", true),
	VARIABLE_DECLARATION("Variable", false),
	ARRAY_NAME("Array", false),
	RETURN_TYPE("Return type", true),
	VARIABLE_TYPE("Variable or parameter type", true),
	
	NIY("Not implemented yet", false);
	
	private String name;
	private boolean alreadyDeclared;
	IdType(String name, boolean alreadyDeclared) {
		this.name = name;
		this.alreadyDeclared = alreadyDeclared;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isAlreadyDeclared() {
		return alreadyDeclared;
	}
}
