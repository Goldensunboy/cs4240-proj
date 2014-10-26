package com.symbol_table;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.attribute.Attribute;

public class SymbolTableManager {
	private Scope currentScope;
	private Scope globalScope = new Scope(null, -1);
	private int scopeId = 0;
	private Map<String, List<Symbol>> symbolTable = new Hashtable<>();
	
	/**
	 * when looking at begin
	 * @param attributeMaps
	 * @return
	 */
	public Scope makeNewScope(Map<String, Attribute> attributeMaps) {
		if(currentScope != null){
			symbolTable.putAll(currentScope.putInScope(attributeMaps));			
		} else {
			symbolTable.putAll(globalScope.putInScope(attributeMaps));
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
		Map<String, List<Symbol>> currentScopeMap = currentScope.putInScope(attributeMaps);
		for(Entry<String, List<Symbol>> currentScopeSymbol : currentScopeMap.entrySet()) {
			List<Symbol> symbolList = symbolTable.get(currentScopeSymbol.getKey());
			if(symbolList == null){				
				symbolList = new LinkedList<>();
			}
			symbolList.addAll(currentScopeSymbol.getValue());
			symbolTable.put(currentScopeSymbol.getKey(), symbolList);
		}
		
		currentScope = currentScope.getEnclosingScope();
		return currentScope;
	}
	
	
	public Map<String, List<Symbol>> getSymbolTable() {
		return symbolTable;
	}
}
