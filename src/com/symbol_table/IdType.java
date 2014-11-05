package com.symbol_table;

public enum IdType {
	FUNCTION_NAME("Function name"),
	VAR_NAME ("Variable name"),
	TYPE_NAME("Type name"),

	NIY("Not implemented yet");
	
	private String name;
	IdType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
