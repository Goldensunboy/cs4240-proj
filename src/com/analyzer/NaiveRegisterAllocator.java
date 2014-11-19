package com.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class NaiveRegisterAllocator implements RegisterAllocator {
	
	private List<String> labelList;
	private Set<String> varSet;
	private boolean liveMatrix[][];
	private List<String> annotatedIRCode;
	private Map<String, Integer> varMap;
	private Map<String, GraphNode> varGraph;
	
	public List<String> getLabelList() {
		return labelList;
	}
	
	public Set<String> getVarSet() {
		return varSet;
	}
	
	public boolean[][] getLiveMatrix() {
		return liveMatrix;
	}
	
	public List<String> getAnnotatedIRCode() {
		return annotatedIRCode;
	}
	
	public Map<String, Integer> getVarMap() {
		return varMap;
	}
	
	public Map<String, GraphNode> getVarGraph() {
		return varGraph;
	}
	
	public NaiveRegisterAllocator(List<String> IRList) {
		analyzeRegistersFromIRCode(IRList);
	}

	/**
	 * Annotate IRList by the naive method
	 * @param IRList Correctly generated IR code from the parser
	 */
	private void analyzeRegistersFromIRCode(List<String> IRList) {
		
		labelList = new ArrayList<String>();
		varSet = new HashSet<String>();
		annotatedIRCode = new ArrayList<String>();
		
		// First pass: Enumerate the labels, and determine how many variables there are
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
		
		/* Second pass: Determine how long each variable is alive
		 * First  axis: Y, the current IR line in IRList
		 * Second axis: X, the variable from varSet
		 * Values in this matrix at [line][var] indicate var is live at line if true
		 */
		liveMatrix = new boolean[IRList.size()][varSet.size()];
		varMap = new HashMap<String, Integer>();
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
		
		// Third pass: Construct graph
		varGraph = new HashMap<String, GraphNode>();
		for(String s : varMap.keySet()) {
			varGraph.put(s, new GraphNode(s));
		}
		for(int i = 0; i < IRList.size(); ++i) {
			// For each row of the live matrix, locate usage of variables
			for(String s : varMap.keySet()) {
				if(liveMatrix[i][varMap.get(s)]) {
					// Locate concurrent usage of variables
					for(String s2 : varMap.keySet()) {
						if(liveMatrix[i][varMap.get(s2)] && !s.equals(s2)) {
							// If two variables are alive concurrently, connect them
							varGraph.get(s).connect(varGraph.get(s2));
						}
					}
				}
			}
		}
		
		// Color the graph
		for(GraphNode n : varGraph.values()) {
			int color = 0;
			boolean collision;
			do {
				collision = false;
				for(GraphNode g : n.getNeighbors()) {
					if(color == g.getColor()) {
						collision = true;
					}
				}
				n.setColor(color++);
			} while(collision);
		}
		
		// Annotate variable names with register assignments
		for(String s : IRList) {
			String parts[] = s.split(", ");
			if(parts.length == 1) {
				annotatedIRCode.add(s);
			} else {
				String line = parts[0];
				for(int i = 1; i < parts.length; ++i) {
					line += ", " + parts[i] +
							(parts[i].contains("$") ? "#r" + varGraph.get(parts[i]).getColor() : "");
				}
				annotatedIRCode.add(line);
			}
		}
	}
}
