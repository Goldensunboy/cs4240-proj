package com.analyzer;

import java.util.ArrayList;
import java.util.List;

public class InstructionUtility {

	public static String generateLoad(String variableName, String registerName) {
		return whichLoad(variableName) + variableName + ", " + registerName;
	}
	
	public static String generateStore(String variableName, String registerName) {
		return "store, " + variableName + ", " + registerName;
	}
	
	private static String whichLoad(String variableName) {
		if(isLiteral(variableName)) {
			if(isFloat(variableName)) {				
				return "li.s, ";
			} else {
				return "li, ";
			}
		} else {
			return "load, ";
		}
	}
	
	public static boolean isLiteral(String variableName) {
		return !variableName.matches(".*%");
	}
	
	public static boolean isFloat(String variableName) {
		return variableName.matches(".*\\..*");
	}
	
	public static boolean isIntLiteral(String variableName) {
		return isLiteral(variableName) && !isFloat(variableName);
	}
	
	public static boolean isIntRegister(String variableName) {
		return variableName.matches(".*%i");
	}

	public static boolean isIntIsh(String variableName) {
		return isIntLiteral(variableName) || isIntRegister(variableName);
	}
	
	public static boolean isFloatLiteral(String variableName) {
		return isLiteral(variableName) && isFloat(variableName);
	}
	
	public static boolean isFloatRegister(String variableName) {
		return variableName.matches(".*%f");
	}
	
	public static boolean isFloatIsh(String variableName) {
		return isFloatLiteral(variableName) || isFloatRegister(variableName);
	}
	
	public static List<String> getConversion(String intRegisterName, String floatRegisterName) {
		List<String> conversion = new ArrayList<>();
		String mtc = "mtc, " + intRegisterName + ", " + floatRegisterName;
		String cvt = "cvt.s.w, " + floatRegisterName + ", " + floatRegisterName;
		conversion.add(mtc);
		conversion.add(cvt);
		return conversion;
	}
	
}
