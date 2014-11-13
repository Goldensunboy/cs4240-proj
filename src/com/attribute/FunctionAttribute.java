package com.attribute;

import java.util.List;

public class FunctionAttribute implements Attribute{
	
	// Attributes for function name
	private String functionName;
	private String returnTypeName;
	private List<TypeAttribute> params;
	private final int scopeId;
	
	public FunctionAttribute(String functionName, String returnTypeName, List<TypeAttribute> params,
			int scopeId) {
		this.functionName = functionName;
		this.params = params;
		this.returnTypeName = returnTypeName;
		this.scopeId = scopeId;
	}
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public List<TypeAttribute> getParams() {
		return params;
	}
	public void setParams(List<TypeAttribute> params) {
		this.params = params;
	}
	
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Function Name: ");
		buffer.append(functionName);
		buffer.append(", Return type name: ");
		buffer.append(returnTypeName);
		buffer.append(", Parameters: ");
		for(TypeAttribute paramType : params)
			buffer.append(paramType).append(", ");
		return buffer.toString();
	}
	
	public static String getParamListStringRepresentationFactoryInTigerCodeForPhase2ErrorReporting(List<TypeAttribute> params) {
		if(params.size() == 0) {
			return "[void]";
		} else {
			String ret = "[";
			boolean b = true;
			for(TypeAttribute t : params) {
				if(b) {
					b = false;
				} else {
					ret += ", ";
				}
				ret += t.getType();
			}
			return ret + "]";
		}
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

	@Override
	public int getScopeId() {
		return scopeId;
	}

//	@Override
//	public Attribute getAttributeClone() {
//		return new FunctionAttribute(functionName, returnTypeName, params, scopeId);
//	}
}
