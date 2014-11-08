package com.attribute;

import com.compiler.Type;

public class VariableAttribute implements Attribute{
	
	private String variableName;
	private Type type;
	private String declaringProcedure;
	private String typeName;
	
	public VariableAttribute(String variableName, String typeName, Type type /*soon this will be deprecated*/, String declaringFunctionName){
		this.variableName = variableName;
		this.type = type;
		this.declaringProcedure = declaringFunctionName;
		this.typeName = typeName;
	}
	
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
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
		buffer.append(", Type: ");
		buffer.append(type);
		buffer.append(", Declaring Procedure: ");
		buffer.append(declaringProcedure);
		return buffer.toString();
	}

	@Override
	public String getTypeName() {
		return typeName;
	}
}