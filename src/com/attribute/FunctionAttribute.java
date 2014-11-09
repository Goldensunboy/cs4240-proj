package com.attribute;

import java.util.List;

import com.compiler.Type;

public class FunctionAttribute implements Attribute{
	
	// Enums for return type and param types
	public enum ParamType {
		INT,
		FIX_PT
	};
	
	// Attributes for function name
	private String functionName;
	private Type returnType;
	private List<String> params;
	private String typeName;
	
	public FunctionAttribute(String functionName, String typeName, 
			Type returnType, List<String> params) {
		this.functionName = functionName;
		this.returnType = returnType;
		this.params = params;
		this.typeName = typeName;
	}
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public Type getReturnType() {
		return returnType;
	}
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Function Name: ");
		buffer.append(functionName);
		buffer.append(", Return Type: ");
		buffer.append(returnType);
		buffer.append(", Parameters: ");
		for(String parameter : params)
			buffer.append(parameter).append(", ");
		return buffer.toString();
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return returnType;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}
	
}
