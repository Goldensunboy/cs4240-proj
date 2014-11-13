package com.attribute;


public class VariableAttribute implements Attribute{
	
	private String variableName;
	private String declaringFunctionName;
	private String typeName;
	private final int scopeId;
	
	public VariableAttribute(String variableName, String typeName, String declaringFunctionName, int scopeId) {
		this.variableName = variableName;
		this.declaringFunctionName = declaringFunctionName;
		this.typeName = typeName;
		this.scopeId = scopeId;
	}
	
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getDeclaringProcedure() {
		return declaringFunctionName;
	}
	public void setDeclaringProcedure(String declaringFunctionName) {
		this.declaringFunctionName = declaringFunctionName;
	}
	
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Variable Name: ");
		buffer.append(variableName);
		buffer.append(", Declaring Procedure: ");
		buffer.append(declaringFunctionName);
		buffer.append(", Type Name: ");
		buffer.append(typeName);
		buffer.append(", scopeId: ");
		buffer.append(scopeId);
		return buffer.toString();
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public int getScopeId() {
		return scopeId;
	}

//	@Override
//	public Attribute getAttributeClone() {
//		return new VariableAttribute(variableName, typeName, declaringFunctionName, initialized, scopeId);
//	}
}