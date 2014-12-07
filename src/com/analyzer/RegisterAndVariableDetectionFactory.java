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
	
	public static HashMap<String, HashMap<String, Integer>> getFunctionArraySizes(List<String> IRList) {
		HashMap<String, HashMap<String, Integer>> funcmap = new HashMap<String, HashMap<String, Integer>>();
		String currFunc = null;
		for(String s : IRList) {
			if(Pattern.matches("FUNC_.*:", s)) {
				currFunc = s.substring(0, s.length() - 1);
				funcmap.put(currFunc, new HashMap<String, Integer>());
			} else {
				String[] parts = s.split(", ");
				if(parts.length == 4 && "assign".equals(parts[0])) {
					HashMap<String, Integer> arrmap = funcmap.get(currFunc);
					if(!arrmap.containsKey(parts[1])) {
						arrmap.put(parts[1], Integer.parseInt(parts[2]));
					}
				}
			}
		}
		return funcmap;
	}
}
