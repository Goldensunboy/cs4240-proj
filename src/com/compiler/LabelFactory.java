package com.compiler;

import java.util.HashMap;

public class LabelFactory {
	private HashMap<String, Integer> counts = new HashMap<String, Integer>();
	public String nextLabel(String postfix) {
		if(!counts.containsKey(postfix)) {
			counts.put(postfix, 0);
		}
		int i = counts.get(postfix);
		counts.put(postfix, i + 1);
		return "L_" + postfix + "_" + i;
	}
}
