package com.attribute;


public class VariableAttribute implements Attribute{
	
	private String variableName;
	private String declaringProcedure;
	private String typeName;
	private boolean initialized;
	private int scopeId;
	
	public VariableAttribute(String variableName, String typeName, String declaringFunctionName,
			boolean initialized, int scopeId) {
		this.variableName = variableName;
		this.declaringProcedure = declaringFunctionName;
		this.typeName = typeName;
		this.initialized = initialized;
		this.scopeId = scopeId;
	}
	
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	public boolean getInitialized() {
		return initialized;
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
		buffer.append(", Type Name: ");
		buffer.append(typeName);
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
}