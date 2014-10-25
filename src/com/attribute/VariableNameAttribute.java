package com.attribute;

public class VariableNameAttribute implements Attribute{
	
	private String variableName;
	private String type;
	private String declaringProcedure; 
	
	public VariableNameAttribute(String variableName, String type, String declaringFunctionName){
		
	}
	
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
	public String getDeclaringProcedure() {
		return declaringProcedure;
	}
	public void setDeclaringProcedure(String declaringProcedure) {
		this.declaringProcedure = declaringProcedure;
	}
}