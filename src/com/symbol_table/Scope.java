package com.symbol_table;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.attribute.Attribute;

public class Scope {
	private int scopeId;
	private Scope enclosingScope;
	private Map<String, List<Symbol>> symbolMap = new LinkedHashMap<>();
	
	public Scope(Scope enclosingScope, int scopeId){
		this.enclosingScope = enclosingScope;
		this.scopeId = scopeId;
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
}
