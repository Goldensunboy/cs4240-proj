package com.symbol_table;

import java.util.Hashtable;
import java.util.Map;

import com.attribute.Attribute;

public class SymbolTableManager {
	private Scope currentScope;
	private int scopeId = 0;
	private Map<String, Symbol> symboTable = new Hashtable<>();
	
	
	public Scope makeNewScope(Map<String, Attribute> attributeMaps) {
		if(currentScope != null){
			symboTable.putAll(currentScope.putInScop(attributeMaps));			
		}

		Scope newScope = new Scope(currentScope, scopeId++);
		currentScope = newScope;
		return currentScope;
	}
	
	public Scope goToEnclosingScope(Map<String, Attribute> attributeMaps) {
		symboTable.putAll(currentScope.putInScop(attributeMaps));
		
		currentScope = currentScope.getEnclosingScope();
		return currentScope;
	}
	
	public Map<String, Symbol> getSymboTable() {
		return symboTable;
	}
}
