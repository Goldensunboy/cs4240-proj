package com.symbol_table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.antlr.runtime.Token;

import com.exception.ExceptionHandler;
import com.exception.NameSpaceConflictException;

public class NameSpaceManager {
	private Set<String> functionNames;
	private Set<String> varNames;
	private Set<String> typeNames;
	private ExceptionHandler exceptionHandler;
	public NameSpaceManager() {
		initalizeFunctionNames();
		varNames = new HashSet<>();
		typeNames = new HashSet<>();
		exceptionHandler = new ExceptionHandler();
	}
	
	private void initalizeFunctionNames() {
		functionNames = new HashSet<>();
		
		File file = new File("reserved/functions");
		Scanner scan;
		try {
			scan = new Scanner(file);

			while(scan.hasNextLine()) {
				functionNames.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void manageNameSpace(Token token, IdType idType) {
		String idName = token.getText();
		int lineNumber = token.getLine();
		
		boolean usedBefore = functionNames.contains(idName);
		String customMessage = idType.getName() + " is already in the name space";
		Class<? extends RuntimeException> exceptionClass = NameSpaceConflictException.class;
		
		switch (idType) {
		case FUNCTION_NAME:
			usedBefore = usedBefore || varNames.contains(idName) || typeNames.contains(idName);
			if(usedBefore) {
				exceptionHandler.handleException(lineNumber, customMessage, null, null, exceptionClass);
			}
			functionNames.add(idName);
			break;
			
		case VAR_NAME:
			usedBefore = usedBefore || typeNames.contains(idName);
			if(usedBefore) {
				exceptionHandler.handleException(lineNumber, customMessage, null, null, exceptionClass);
			}
			varNames.add(idName);
			break;
			
		case TYPE_NAME:
			usedBefore = usedBefore || varNames.contains(idName);
			if (usedBefore) { 
				exceptionHandler.handleException(lineNumber, customMessage, null, null, exceptionClass);
			}
			typeNames.add(idName);
			break;
		case NIY:
//			System.out.println("NIY");
			break;
		default:
			throw new RuntimeException("Not a valid IdType");
		}
			
	}
	
	public String toString() {
		StringBuffer content = new StringBuffer();
		
		content.append("\nFunction Names: ");
		for(String functionName : functionNames) 
			content.append(functionName).append(", ");
		
		content.append("\nVar Names: ");
		for(String varName : varNames) 
			content.append(varName).append(", ");

		content.append("\nType Names: ");
		for(String typeName : typeNames) 
			content.append(typeName).append(", ");
		
		content.append("\n");
		
		return content.toString();
	}
}
