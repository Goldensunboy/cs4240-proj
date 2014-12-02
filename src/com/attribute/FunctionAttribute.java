package com.attribute;

import java.util.List;

public class FunctionAttribute implements Attribute{
	
	// Attributes for function name
	private String functionName;
	private String returnTypeName;
	private List<TypeAttribute> parameterTypes;
	private List<String> actualParameterNames;
	private final int scopeId;
	
	public FunctionAttribute(String functionName, String returnTypeName, List<TypeAttribute> parameterTypes, 
			List<String> actualParameterNames, int scopeId) {
		this.functionName = functionName;
		this.parameterTypes = parameterTypes;
		this.returnTypeName = returnTypeName;
		this.actualParameterNames = actualParameterNames;
		this.scopeId = scopeId;
	}
	
	// Getters and setters. You know, because "encapsulation"...
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public List<TypeAttribute> getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(List<TypeAttribute> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public List<String> getAcrualtParameters() {
		return actualParameterNames;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Function Name: ");
		buffer.append(functionName);
		buffer.append(", scope ID: ");
		buffer.append(scopeId);
		buffer.append(", Return type name: ");
		buffer.append(returnTypeName);
		buffer.append(", Parameters: ");
		for(TypeAttribute paramType : parameterTypes)
			buffer.append(paramType).append(", ");
		buffer.append(" ActualParameterNames: ");
		for(String actualName : actualParameterNames)
			buffer.append(actualName).append(", ");
		return buffer.toString();
	}
	
	public static String getParamListStringRepresentationFactoryInTigerCodeForPhase2ErrorReporting(List<TypeAttribute> parameterTypes) {
		if(parameterTypes.size() == 0) {
			return "[void]";
		} else {
			String ret = "[";
			boolean b = true;
			for(TypeAttribute t : parameterTypes) {
				if(b) {
					b = false;
				} else {
					ret += ", ";
				}
				ret += t.getAliasName();
//				ret += t.getType();
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
}
