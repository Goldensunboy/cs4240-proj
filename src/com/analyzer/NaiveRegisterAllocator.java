package com.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class NaiveRegisterAllocator implements RegisterAllocator {

	@Override
	public List<String> allocateRegistersFromIRCode(List<String> IRList) {
		
		// First pass: Enumerate the labels, and determine how many variables there are
		List<String> labelList = new ArrayList<String>();
		Set<String> varSet = new HashSet<String>();
		for(String s : IRList) {
			if(Pattern.matches(".*:", s)) {
				// This line is a label, add it to the label list
				labelList.add(s);
			} else {
				// This line is IR code, add referenced variables to variable set
				for(String p : s.split(", ")) {
					if(p.contains("$")) {
						varSet.add(p);
					}
				}
			}
		}
		
		System.out.println("Labels:\n\t" + labelList);
		System.out.println("Variables:\n\t" + varSet);
		
		/* Second pass: Determine how long each variable is alive
		 * First  axis: Y, the current IR line in IRList
		 * Second axis: X, the variable from varSet
		 * Values in this matrix at [line][var] indicate var is live at line if true
		 */
		boolean liveMatrix[][] = new boolean[IRList.size()][varSet.size()];
		HashMap<String, Integer> varMap = new HashMap<String, Integer>();
		int varIndex = 0;
		for(int i = 0; i < IRList.size(); ++i) {
			// Don't do anything to labels or parameterless return
			// Pattern.matches() is faster than Collection.contains()
			String s = IRList.get(i);
			if(!Pattern.matches(".*:", s) && !"return".equals(s)) {
				String[] parts = Arrays.copyOfRange(s.split(", "), 1, s.split(", ").length);
				for(String p : parts) {
					// If this is a variable, set array liveness
					if(p.contains("$")) {
						// If first encounter, add to hashmap
						if(!varMap.containsKey(p)) {
							varMap.put(p, varIndex++);
						} else {
							// Set everything between now and previous use to true
							int varIdx = varMap.get(p);
							for(int j = i - 1;; --j) {
								if(liveMatrix[j][varIdx]) {
									break;
								} else {
									liveMatrix[j][varIdx] = true;
								}
							}
						}
						liveMatrix[i][varMap.get(p)] = true;
					}
				}
			}
		}
		
		
		
		return null;
	}

}
