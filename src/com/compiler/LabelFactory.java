package com.compiler;

import java.util.HashMap;

public class LabelFactory {
	private HashMap<String, Integer> counts = new HashMap<String, Integer>();
	public String nextLabel(String postfix) {
		if(!counts.containsKey(postfix)) {
			counts.put(postfix, new Integer(0));
		}
		Integer i = counts.get(postfix);
		return "L_" + postfix + "_" + i++;
	}
}
