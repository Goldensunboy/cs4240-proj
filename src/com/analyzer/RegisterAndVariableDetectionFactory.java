package com.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterAndVariableDetectionFactory {
	
	public static HashMap<String, List<String>> getFunctionVariables(List<String> IRList) {
		HashMap<String, List<String>> varmap = new HashMap<String, List<String>>();
		String currFunc = null;
		for(String s : IRList) {
			if(Pattern.matches("FUNC_.*:", s)) {
				currFunc = s.substring(0, s.length() - 1);
				varmap.put(currFunc, new ArrayList<String>());
			} else {
				String[] parts = s.split(", ");
				if(parts.length > 1) {
					for(String p : parts) {
						if(p.contains("$") && p.contains("%")) {
							String put = p;
							if(!varmap.get(currFunc).contains(put)) {
								varmap.get(currFunc).add(put);
							}
						}
					}
				}
			}
		}
		return varmap;
	}
	
	
	
	public static HashMap<String, List<String>> getFunctionRegisters(List<String> IRList) {
		HashMap<String, List<String>> regmap = new HashMap<String, List<String>>();
		String currFunc = null;
		for(String s : IRList) {
			if(Pattern.matches("FUNC_.*:", s)) {
				currFunc = s.substring(0, s.length() - 1);
				regmap.put(currFunc, new ArrayList<String>());
			} else {
				String[] parts = s.split(", ");
				if(parts.length > 1) {
					for(String p : parts) {
						if(p.contains("$") && !p.contains("%")) {
							String put = p;
							if(!regmap.get(currFunc).contains(put)) {
								regmap.get(currFunc).add(put);
							}
						}
					}
				}
			}
		}
		return regmap;
	}
}
