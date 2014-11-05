package com.symbol_table;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.attribute.Attribute;
import com.compiler.Type;

public class Scope {
	private String enclosingFunctionName;
	private int scopeId;
	private Scope enclosingScope;
	private Set<String> varNameSpace;
	private Set<String> typeNameSpace;
	private Set<String> functionNameSpace;
	private Map<String, List<Symbol>> symbolMap = new LinkedHashMap<>();
	private Type returnType;
	
	public Scope(Scope enclosingScope, int scopeId, String enclosingFunctionName){
		this.enclosingScope = enclosingScope;
		this.scopeId = scopeId;
		this.enclosingFunctionName = enclosingFunctionName;
		returnType = Type.VOID;
		varNameSpace = new HashSet<>();
		typeNameSpace = new HashSet<>();
		functionNameSpace = new HashSet<>();
	}
	
	/**
	 * Adds the elements of the attributeMap into the symbolMap and return the symbolMap
	 * @param attributeMap 
	 * @return
	 */
	public Map<String, List<Symbol>> putInScope(Map<String, Attribute> attributeMap) {
		for(Entry<String, Attribute> attribute : attributeMap.entrySet()) {
			List<Symbol> value = symbolMap.get(attribute.getKey());
			if(value == null) {
				value = new LinkedList<>();
			}
			value.add(new Symbol(this, attribute.getValue()));
			symbolMap.put(attribute.getKey(), value);
		}
		return symbolMap;
	}

	public List<Symbol> resolve(String attributeName) {
		List<Symbol> resolvedSymbol = symbolMap.get(attributeName);
		if (resolvedSymbol == null) {
			Scope runnerScope = enclosingScope;
			while (runnerScope != null && resolvedSymbol == null) {
				resolvedSymbol = runnerScope.symbolMap.get(attributeName);
				runnerScope = runnerScope.getEnclosingScope();
			}
		}
		return resolvedSymbol;
	}
	
	public Scope getEnclosingScope() {
		return enclosingScope;
	}

	public void setEnclosingScope(Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
	}

	public int getScopeId() {
		return scopeId;
	}

	public void setScopeId(int scopId) {
		this.scopeId = scopId;
	}
	
	public Map<String, List<Symbol>> getSymbolMap() {
		return symbolMap;
	}

	public String getEnclosingFunctionName() {
		return enclosingFunctionName;
	}

	public void setEnclosingFunctionName(String enclosingFunctionName) {
		this.enclosingFunctionName = enclosingFunctionName;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	public Set<String> getFunctionNameSpace() {
		return functionNameSpace;
	}
	
	public Set<String> getVarNameSpace() {
		return varNameSpace;
	}
	
	public Set<String> getTypeNameSpace() {
		return typeNameSpace;
	}
	
	public void addToFunctionNameSpace(String name) {
		functionNameSpace.add(name);
	}
	
	public void addToVarNameSpace(String name) {
		varNameSpace.add(name);
	}
	
	public void addToTypeNameSpace(String name) {
		typeNameSpace.add(name);
	}

	public void addToFunctionNameSpace(Set<String> names) {
		functionNameSpace.addAll(names);
	}
	
	public void addToVarNameSpace(Set<String> names) {
		varNameSpace.addAll(names);
	}
	
	public void addToTypeNameSpace(Set<String> names) {
		typeNameSpace.addAll(names);
	}
	
	public boolean isInFunctionNameSpace(String name, Set<String> unRegisteredNames, Set<String> globalScopeNames) {
		
		if (globalScopeNames.contains(name)) {
			return true;
		}
		
		if(unRegisteredNames.contains(name)) {
			return true;
		}
		
		return functionNameSpace.contains(name);
	}
	
	public boolean isInVarNameSpace(String name, Set<String> unRegisteredNames, Set<String> globalScopeNames) {
		
		if(globalScopeNames.contains(name)) {
			return true;
		}
		
		if(unRegisteredNames.contains(name)) {
			return true;
		}
		
		Scope temp = this;
		while (temp != null) {
			if (temp.typeNameSpace.contains(name)) {
				return true;
			}
			temp = temp.getEnclosingScope();
		}
		
		return varNameSpace.contains(name);
	}
	
	public boolean isInTypeNameSpace(IdType idType, String name, Set<String> unRegisteredNames, Set<String> globalScopeNames) {
		
		if(globalScopeNames.contains(name)) {
			return true;
		}
		
		if(unRegisteredNames.contains(name)) {
			return true;
		}
		
		Scope temp = this;
		while (temp != null) {
			boolean varNameConflict = idType == IdType.VAR_NAME ? false : temp.varNameSpace.contains(name);
			if (varNameConflict || temp.typeNameSpace.contains(name)) {
				return true;
			}
			temp = temp.getEnclosingScope();
		}
		
		return false;
	}
}
