package com.attribute;

import java.util.List;

public class FunctionNameAttribute implements Attribute{
	
	// Enums for return type and param types
	public enum ReturnType {
		VOID,
		INT,
		FIX_PT
	};
	public enum ParamType {
		INT,
		FIX_PT
	};
	
	// Attributes for function name
	private String functionName;
	private String returnType;
	private List<String> params;
	
	public FunctionNameAttribute(String functionName, String returnType, List<String> params) {
		this.functionName = functionName;
		this.returnType = returnType;
		this.params = params;
	}
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
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
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
