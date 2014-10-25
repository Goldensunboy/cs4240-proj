package com.symbol_table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.attribute.Attribute;

public class Scope {
	private int scopeId;
	private Scope enclosingScope;
	private Map<String, Symbol> symbolMap = new LinkedHashMap<>();
	
	public Scope(Scope enclosingScope, int scopeId){
		this.enclosingScope = enclosingScope;
		this.scopeId = scopeId;
	}
	
	/**
	 * Adds the elements of the attributeMap into the symbolMap and return the symbolMap
	 * @param attributeMap 
	 * @return
	 */
	public Map<String, Symbol> putInScope(Map<String, Attribute> attributeMap) {
		for(Entry<String, Attribute> attribute : attributeMap.entrySet()) {
			symbolMap.put(attribute.getKey(), new Symbol(this, attribute.getValue()));
		}
		return symbolMap;
	}

	public Symbol resolve(String attributeName) {
		Symbol resolvedSymbol = symbolMap.get(attributeName);
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
	
	public Map<String, Symbol> getSymbolMap() {
		return symbolMap;
	}
}
