package com.symbol_table;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.antlr.generated.TigerParser;
import com.attribute.Attribute;
import com.attribute.FunctionAttribute;
import com.attribute.TypeAttribute;
import com.compiler.Type;
import com.exception.AttributeCastException;
import com.exception.ErroneousParserImplementationException;
import com.exception.NameSpaceConflictException;
import com.exception.ShouldNotHappenException;

/**
 * Manages scoping and any addition to the symbol table. 
 * @author saman
 *
 */
public class SymbolTableManager {
	
	// used for global types and function names
	private Scope globalScope = new Scope(null, -1, null);
	private Set<String> globalTypeFunctionNameSpace = new HashSet<>();
	private Scope currentScope;	
	private int scopeId = 0;
	
	// the symbol table that holds all symbol names and their actual symbols in the code
	private Map<String, List<Symbol>> symbolTable = new Hashtable<String, List<Symbol>>();

	/*
	 * Sorry I couldn't think of a good name. This set keep tracks of the names that 
	 * have been used in as functionName, varName and typeName. So when we
	 * are creating new functionName's we won't use them again. It's independent
	 * of any scope.
	 *  
	 */
	private Set<String> expiredFunctionName = new HashSet<>();
	/**
	 * Takes the temporary attributeMap in the TigerParser and insert all of its values
	 * into the symbolTable
	 * 
	 * @param attributeMaps holds the attributes seen in the code before hitting a begin
	 * key.   
	 * @return the newly made scope 
	 */
	public Scope makeNewScope(Map<String, Attribute> attributeMaps, 
			String enclosingFunctionName, Map<String, Set<String>> nameSpaceMap) {

		addToExpiredFunctionName(nameSpaceMap); 
		
		if(currentScope != null){
			for(Entry<String, List<Symbol>> attribute : currentScope.putInScope(attributeMaps).entrySet()){
				List<Symbol> symbolList = symbolTable.get(attribute.getKey());
				if(symbolList == null) {
					symbolList = attribute.getValue();
				} else {					
					symbolList.addAll(attribute.getValue());
				}
				symbolTable.put(attribute.getKey(), symbolList);
			}
			currentScope.addToVarNameSpace(nameSpaceMap.get(TigerParser.VAR_NAMESPACE));
			currentScope.addToTypeNameSpace(nameSpaceMap.get(TigerParser.TYPE_NAMESPACE));
		} else {
			symbolTable.putAll(globalScope.putInScope(attributeMaps));
			globalScope.addToFunctionNameSpace(nameSpaceMap.get(TigerParser.FUNCTION_NAMESPACE));
			globalScope.addToTypeNameSpace(nameSpaceMap.get(TigerParser.TYPE_NAMESPACE));

			globalTypeFunctionNameSpace = globalScope.getFunctionNameSpace();
			globalTypeFunctionNameSpace.addAll(globalScope.getTypeNameSpace()); 
		}

		Scope newScope = new Scope(currentScope, scopeId++, enclosingFunctionName);
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
	public Scope goToEnclosingScope(Map<String, Attribute> attributeMaps,
									Map<String, Set<String>> nameSpaceMap) {

		addToExpiredFunctionName(nameSpaceMap);
		
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
		
		Type returnType = currentScope.getReturnType();
		
		currentScope = currentScope.getEnclosingScope();
		if (currentScope != null) {			
			currentScope.setReturnType(returnType);
		}
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
		FunctionAttribute functionNameAttribute = getFunctionAttribute(functionName);
		return functionNameAttribute.getParams();
	}

	/**
	 * Given the function name returns the return type
	 */
	public Type getFunctionReturnType(String functionName) {
		FunctionAttribute functionNameAttribute = getFunctionAttribute(functionName);
		return functionNameAttribute.getReturnType();
	}

	/**
	 * Finds the function attribute in the symbol table and casts it to 
	 * FunctionNameAttribute and returns it.
	 */
	private FunctionAttribute getFunctionAttribute(String functionName) {
		List<Symbol> symbolList = symbolTable.get(functionName);
		if (symbolList.size() != 1) {
			// SNH (Should Never Happen). Just a clue for us for debugging
			throw new NameSpaceConflictException("SNH: More than one function with the same name.");
		}
		FunctionAttribute functionNameAttribute;
		try {			
			functionNameAttribute= (FunctionAttribute)symbolList.get(0).getAttribute();
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
	
	/**
	 * return the return type of the current scope's function
	 */
	public Type getReturnType() {
		String functionName = currentScope.getEnclosingFunctionName();
		FunctionAttribute functionNameAttribute = getFunctionAttribute(functionName);
		return functionNameAttribute.getReturnType();
	}
	
	public Type getCurrentScopeReturnType() {
		return currentScope.getReturnType();
	}
	
	public boolean returnStatementSatisfied(String functionName) {
		Type returnType = getReturnType();
		return returnType == currentScope.getReturnType();
	}
	
	public void setCurrentScopeReturnType(Type returnType) {
		currentScope.setReturnType(returnType);
	}
	
	public TypeAttribute getOtherType(Map<String, Attribute> attributeMap, String typeName) {
		TypeAttribute varTypeAttribute;
		try {
			varTypeAttribute = (TypeAttribute) globalScope.getSymbolMap().get(typeName).get(0).getAttribute();				
		} catch (ClassCastException e) {
			throw new AttributeCastException(e.getMessage());
		}
		return varTypeAttribute;
	}
	
	public void addToExpiredFunctionName(Map<String, Set<String>> nameSpaceMap) {
		for (Entry<String, Set<String>> nameSpcae : nameSpaceMap.entrySet()) {
			expiredFunctionName.addAll(nameSpcae.getValue());
		}
	}
	
	public boolean doesNameSpaceConflict(IdType idType, String name, Map<String, Set<String>> unregisteredNamespaceMap) {

		Set<String> varNameSpace = unregisteredNamespaceMap.get(TigerParser.VAR_NAMESPACE);
		Set<String> typeNameSpace = unregisteredNamespaceMap.get(TigerParser.TYPE_NAMESPACE);
		Set<String> functionNameSpace = unregisteredNamespaceMap.get(TigerParser.FUNCTION_NAMESPACE);
		
		boolean functionNameConflict = globalScope.isInFunctionNameSpace(name, functionNameSpace, globalTypeFunctionNameSpace); 
		boolean typeNameConflict = currentScope == null ? false : currentScope.isInTypeNameSpace(idType, name, typeNameSpace, globalTypeFunctionNameSpace);
		boolean varNameConflict = currentScope == null ? false : currentScope.isInVarNameSpace(name, varNameSpace, globalTypeFunctionNameSpace);
		
		if(idType == IdType.FUNCTION_NAME) {
			for(Entry<String, Set<String>> nameSpace : unregisteredNamespaceMap.entrySet()) {
				if(nameSpace.getValue().contains(name)) {
					return true;
				}
			}
			return expiredFunctionName.contains(name);
		}
		
		if(idType == IdType.VAR_NAME) {
			return functionNameConflict || typeNameConflict || varNameConflict;
		}

		if(idType == IdType.TYPE_NAME) {
			return functionNameConflict || varNameConflict || typeNameConflict;
		}
		
		throw new ShouldNotHappenException();
	}
}
