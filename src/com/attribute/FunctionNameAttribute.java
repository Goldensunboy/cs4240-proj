package com.attribute;

import java.util.ArrayList;

public class FunctionNameAttribute implements Attribute{
	
	// Enums for return type and param types
	public enum ReturnType {
		Void,
		Int,
		FixPt
	};
	public enum ParamType {
		Int,
		FixPt
	};
	
	// Attributes for function name
	private String functionName;
	private ReturnType returnType;
	private ArrayList<ParamType> params;
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public ReturnType getReturnType() {
		return returnType;
	}
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}
	public ArrayList<ParamType> getParams() {
		return params;
	}
	public void setParams(ArrayList<ParamType> params) {
		this.params = params;
	}
}
