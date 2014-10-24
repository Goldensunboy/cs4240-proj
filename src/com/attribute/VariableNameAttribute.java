package com.attribute;

public class VariableNameAttribute implements Attribute{
	
	private String variableName;
	private String type;
	private FunctionNameAttribute declaringProcedure; 
	
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public FunctionNameAttribute getDeclaringProcedure() {
		return declaringProcedure;
	}
	public void setDeclaringProcedure(FunctionNameAttribute declaringProcedure) {
		this.declaringProcedure = declaringProcedure;
	}
}