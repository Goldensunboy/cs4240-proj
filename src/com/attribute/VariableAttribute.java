package com.attribute;


public class VariableAttribute implements Attribute{
	
	private String variableName;
	private String declaringProcedure;
	private String typeName;
	
	public VariableAttribute(String variableName, String typeName, String declaringFunctionName){
		this.variableName = variableName;
		this.declaringProcedure = declaringFunctionName;
		this.typeName = typeName;
	}
	
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getDeclaringProcedure() {
		return declaringProcedure;
	}
	public void setDeclaringProcedure(String declaringProcedure) {
		this.declaringProcedure = declaringProcedure;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Variable Name: ");
		buffer.append(variableName);
		buffer.append(", Declaring Procedure: ");
		buffer.append(declaringProcedure);
		return buffer.toString();
	}

	@Override
	public String getTypeName() {
		return typeName;
	}
}