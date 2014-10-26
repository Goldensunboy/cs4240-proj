package com.symbol_table;

import java.util.Hashtable;
import java.util.Map;

import com.attribute.Attribute;

public class SymbolTableManager {
	private Scope currentScope;
	private Scope globalScope = new Scope(null, -1);
	private int scopeId = 0;
	private Map<String, Symbol> symboTable = new Hashtable<>();
	
	/**
	 * when looking at begin
	 * @param attributeMaps
	 * @return
	 */
	public Scope makeNewScope(Map<String, Attribute> attributeMaps) {
		if(currentScope != null){
			symboTable.putAll(currentScope.putInScope(attributeMaps));			
		} else {
			symboTable.putAll(globalScope.putInScope(attributeMaps));
		}

		Scope newScope = new Scope(currentScope, scopeId++);
		currentScope = newScope;
		return currentScope;
	}
	
	/**
	 * when we look at end
	 * @param attributeMaps
	 * @return
	 */
	public Scope goToEnclosingScope(Map<String, Attribute> attributeMaps) {
		symboTable.putAll(currentScope.putInScope(attributeMaps));
		
		currentScope = currentScope.getEnclosingScope();
		return currentScope;
	}
	
	
	public Map<String, Symbol> getSymboTable() {
		return symboTable;
	}
}
