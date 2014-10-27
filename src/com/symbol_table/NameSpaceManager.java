package com.symbol_table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.exception.NameSpaceConflictException;

public class NameSpaceManager {
	private Set<String> functionNames;
	private Set<String> varNames;
	private Set<String> typeNames;

	public NameSpaceManager() {
		initalizeFunctionNames();
		varNames = new HashSet<>();
		typeNames = new HashSet<>();
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
	public void manageNameSpace(IdType idType, String idName) {
		boolean usedBefore = functionNames.contains(idName);
		
		switch (idType) {
		case FUNCTION_NAME:
			usedBefore = usedBefore || varNames.contains(idName) || typeNames.contains(idName);
			if(usedBefore) {
				throw new NameSpaceConflictException("Function name is already in the name space");
			}
			functionNames.add(idName);
			break;
			
		case VAR_NAME:
			usedBefore = usedBefore || typeNames.contains(idName);
			if(usedBefore) {
				throw new NameSpaceConflictException("Var name is already in the name space");
			}
			varNames.add(idName);
			break;
			
		case NIY:
			//System.out.println("Warning :: ID is not implemented yet");
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
