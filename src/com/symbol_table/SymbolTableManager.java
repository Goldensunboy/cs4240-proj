package com.symbol_table;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.attribute.Attribute;
import com.attribute.FunctionNameAttribute;
import com.compiler.ReturnType;
import com.exception.AttributeCastException;
import com.exception.ErroneousParserImplementationException;
import com.exception.NameSpaceConflictException;

/**
 * Manages scoping and any addition to the symbol table. 
 * @author saman
 *
 */
public class SymbolTableManager {
	
	// used for global types and function names
	private Scope globalScope = new Scope(null, -1);
	
	private Scope currentScope;	
	private int scopeId = 0;
	
	// the symbol table that holds all symbol names and their actual symbols in the code
	private Map<String, List<Symbol>> symbolTable = new Hashtable<String, List<Symbol>>();
	
	/**
	 * Takes the temporary attributeMap in the TigerParser and insert all of its values
	 * into the symbolTable
	 * 
	 * @param attributeMaps holds the attributes seen in the code before hitting a begin
	 * key.   
	 * @return the newly made scope 
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
	 * Takes the temporary attributeMap and inserts all of its values into the symbol table
	 * 
	 * @param attributeMaps a temporary map of attributes seen since the last begin
	 * 
	 * @return the enclosing scope of the current scope
	 */
	public Scope goToEnclosingScope(Map<String, Attribute> attributeMaps) {
		if(attributeMaps.size() > 0) {			
			Map<String, List<Symbol>> currentScopeMap = currentScope.putInScope(attributeMaps);
			for(Entry<String, List<Symbol>> currentScopeSymbol : currentScopeMap.entrySet()) {
				List<Symbol> symbolList = symbolTable.get(currentScopeSymbol.getKey());
				if(symbolList == null){				
					symbolList = new LinkedList<>();
				}
				symbolList.addAll(currentScopeSymbol.getValue());
				symbolTable.put(currentScopeSymbol.getKey(), symbolList);
			}
		}
		
		currentScope = currentScope.getEnclosingScope();
		return currentScope;
	}
	
	
	public Map<String, List<Symbol>> getSymbolTable() {
		return symbolTable;
	}
	
	/**
	 * Global scope is guaranteed to have a single attribute in it. This method
	 * returns that single attribute
	 */
	public Attribute getAttributeInGlobalScope(String attributeName) {
		List<Symbol> symbolList = symbolTable.get(attributeName);
		return symbolList == null ? null : symbolList.get(0).getAttribute();
	}

	/**
	 * looks at the attributeMap and the symbol table to ensure the attribute requested with attributeName
	 * is accessible in the current scope. If it's accessible the attribute will be returned, otherwise 
	 * null will be returned 
	 * 
	 * @param attributeName 
	 * @param attributeMap 
	 * @return the attribute that has the attributeName which is accessible from the current scope.
	 */
	public Attribute getAttributeInCurrentScope(String attributeName, Map<String, Attribute> attributeMap) {
		Attribute retVal = attributeMap.get(attributeName); 
		if(retVal != null){
			return retVal;
		}
		
		List<Symbol> symbolList = symbolTable.get(attributeName);
		if(symbolList == null) {
			// ID does not exist
			return null;
		}
		
		for(Symbol symbol : symbolList) {
			if(haveSameParentScope(symbol.getScope())) {
				return symbol.getAttribute();
			}
		}
		
		return null;
	}
	
	/**
	 * Goes through the current scope's enclosing scopes until whether it returns true if any of those 
	 * scope equal to the passed in scope, or return false otherwise.
	 * @param scope the scope needed to be matched against the current scope and its enclosing scopes
	 */
	private boolean haveSameParentScope(Scope scope) {
		Scope tempScope = currentScope;
		
		while (tempScope != null) {
			if(tempScope.equals(scope)) {
				return true;
			}
			tempScope = tempScope.getEnclosingScope();
		}
		return false;
	}

	/**
	 * given the function name returns all the parameters 
	 */
	public List<String> getFunctionParameters(String functionName) { 
		FunctionNameAttribute functionNameAttribute = getFunctionAttribute(functionName);
		return functionNameAttribute.getParams();
	}

	/**
	 * Given the function name returns the return type
	 */
	public ReturnType getFunctionReturnType(String functionName) {
		FunctionNameAttribute functionNameAttribute = getFunctionAttribute(functionName);
		return functionNameAttribute.getReturnType();
	}

	/**
	 * Finds the function attribute in the symbol table and casts it to 
	 * FunctionNameAttribute and returns it.
	 */
	private FunctionNameAttribute getFunctionAttribute(String functionName) {
		List<Symbol> symbolList = symbolTable.get(functionName);
		if (symbolList.size() != 1) {
			// SNH (Should Never Happen). Just a clue for us for debugging
			throw new NameSpaceConflictException("SNH: More than one function with the same name.");
		}
		FunctionNameAttribute functionNameAttribute;
		try {			
			functionNameAttribute= (FunctionNameAttribute)symbolList.get(0).getAttribute();
		} catch (ClassCastException e) {
			throw new AttributeCastException("SNH: Couldn't cast to FunctionNameAttribute");
		}
		
		return functionNameAttribute;
	}
	
	/**
	 * Checks to see if the attribute corresponding to the attributeName is in 
	 * a function scope
	 */
	public boolean isInFunctionScope() {
		try {
			return currentScope.getEnclosingScope().getEnclosingScope() == null;
		} catch (NullPointerException e){
			throw new ErroneousParserImplementationException("SNH: function should have at least two scopes");
		}
	}
	
	public Scope getCurrentScope() {
		return currentScope;
	}
}
