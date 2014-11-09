package com.attribute;

import java.util.List;

public class FunctionAttribute implements Attribute{
	
	// Attributes for function name
	private String functionName;
	private String returnTypeName;
	private List<String> params;
	
	public FunctionAttribute(String functionName, String returnTypeName, List<String> params) {
		this.functionName = functionName;
		this.params = params;
		this.returnTypeName = returnTypeName;
	}
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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
		buffer.append(", Return type name: ");
		buffer.append(returnTypeName);
		buffer.append(", Parameters: ");
		for(String parameter : params)
			buffer.append(parameter).append(", ");
		return buffer.toString();
	}

	@Override
	public String getTypeName() {
		return getReturnTypeName();
	}

	public String getReturnTypeName() {
		return returnTypeName;
	}

	public void setReturnTypeName(String returnTypeName) {
		this.returnTypeName = returnTypeName;
	}
	
}
