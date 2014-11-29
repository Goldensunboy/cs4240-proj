package com.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class NaiveRegisterAllocator implements RegisterAllocator {
	
	private List<String> IRList, labelList, annotatedIRCode;
	private Set<String> varSet;
	private boolean liveMatrix[][];
	private Map<String, Integer> varMap;
	private Map<String, List<String>> funcVarMap;
	private Map<String, GraphNode> varGraphInt, varGraphFloat;
	
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
	
	public Map<String, List<String>> getFuncVarMap() {
		return funcVarMap;
	}
	
	public Map<String, GraphNode> getVarGraphInt() {
		return varGraphInt;
	}
	
	public Map<String, GraphNode> getVarGraphFloat() {
		return varGraphFloat;
	}
	
	public NaiveRegisterAllocator(List<String> IRList) {
		this.IRList = IRList;
		analyzeRegistersFromIRCode();
	}

	/**
	 * Annotate IRList by the naive method
	 * @param IRList Correctly generated IR code from the parser
	 */
	private void analyzeRegistersFromIRCode() {
		
		labelList = new ArrayList<String>();
		varSet = new HashSet<String>();
		annotatedIRCode = new ArrayList<String>();
		funcVarMap = new HashMap<String, List<String>>();
		String currFunc = "";
		
		// First pass: Enumerate the labels, and determine how many variables there are
		for(String s : IRList) {
			if(Pattern.matches(".*:", s)) {
				// This line is a label, add it to the label list
				labelList.add(s);
				if("FUNC_".equals(s.substring(0, 5))) {
					String funcName = s.substring(5, s.length() - 1);
					currFunc = funcName;
					funcVarMap.put(funcName, new ArrayList<String>());
				}
			} else {
				// This line is IR code, add referenced variables to variable set
				for(String p : s.split(", ")) {
					if(p.contains("$")) {
						varSet.add(p);
						if(!funcVarMap.get(currFunc).contains(p)) {
							funcVarMap.get(currFunc).add(p);
						}
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
		
		// Third pass: Construct graphs
		varGraphInt = new HashMap<String, GraphNode>();
		varGraphFloat = new HashMap<String, GraphNode>();
		for(String s : varMap.keySet()) {
			System.out.println(s);
			if("f".equals(s.split("%")[1])) {
				varGraphFloat.put(s, new GraphNode(s));
			} else {
				varGraphInt.put(s, new GraphNode(s));
			}
		}
		for(int i = 0; i < IRList.size(); ++i) {
			// For each row of the live matrix, locate usage of variables
			for(String s : varMap.keySet()) {
				if(liveMatrix[i][varMap.get(s)]) {
					// Locate concurrent usage of variables
					for(String s2 : varMap.keySet()) {
						// Only if the two types are compatible (float/float, int or arr/int or arr)
						if("f".equals(s.split("%")[1]) == "f".equals(s2.split("%")[1])) {
							// Two variables are alive concurrently
							if(liveMatrix[i][varMap.get(s2)] && !s.equals(s2)) {
								// If two variables are alive concurrently, connect them
								if("f".equals(s2.split("%")[1])) {
									varGraphFloat.get(s).connect(varGraphFloat.get(s2));
								} else {
									varGraphInt.get(s).connect(varGraphInt.get(s2));
								}
							}
						}
					}
				}
			}
		}
		
		// Color the graphs
		for(GraphNode n : varGraphInt.values()) {
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
		for(GraphNode n : varGraphFloat.values()) {
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
					String varTail = "";
					if(parts[i].contains("$")) {
						if(parts[i].contains("%f")) {
							int regnum = varGraphFloat.get(parts[i]).getColor();
							if(regnum < 8) {
								varTail = "#f" + (regnum + 4);
							} else if(regnum < 24) {
								varTail = "#f" + (regnum + 8);
							} else {
								varTail = "#xf" + (regnum - 24);
							}
						} else {
							int regnum = varGraphInt.get(parts[i]).getColor();
							if(regnum < 10) {
								varTail = "#t" + regnum;
							} else if(regnum < 17) {
								varTail = "#s" + (regnum - 10);
							} else {
								varTail = "#xi" + (regnum - 18);
							}
						}
					}
					line += ", " + parts[i] + varTail;
				}
				annotatedIRCode.add(line);
			}
		}
	}
	
	public void printRegisterAllocatorData() {
		// Print labels found
		System.out.println("Labels:");
		for(String s : labelList) {
			System.out.println("\t" + s);
		}
		
		// Print variables found
		ArrayList<String> varSetSorted =
				new ArrayList<String>(varSet);
		Collections.sort(varSetSorted, new Comparator<String>() {
			public int compare(String s0, String s1) {
				boolean s0temp = "$t".equals(s0.substring(0, 2));
				boolean s1temp = "$t".equals(s1.substring(0, 2));
				if(s0temp && !s1temp) {
					return 1;
				} else if(!s0temp && s1temp) {
					return -1;
				} else if(s0temp && s1temp) {
					String s0parts[] = s0.split("%");
					String s1parts[] = s1.split("%");
					int L = Integer.parseInt(s0parts[0].substring(2));
					int R = Integer.parseInt(s1parts[0].substring(2));
					return L - R;
				} else {
					String s0num = s0.split("\\$|%")[1];
					String s1num = s1.split("\\$|%")[1];
					int cmp = s0num.compareTo(s1num);
					if(cmp != 0) {
						return cmp;
					} else {
						return Integer.parseInt(s0num) - Integer.parseInt(s1num);
					}
				}
			}
		});
//		System.out.println("Variables:");
//		for(String s : varSetSorted) {
//			System.out.println("\t" + s);
//		}
		
		// Print live matrix
		System.out.println("Live matrix:");
//		System.out.println("\tHeight: " + liveMatrix.length);
//		System.out.println("\tWidth:  " + liveMatrix[0].length);
		
		// Print time alive for each variable
		int lineLength = 0;
		int varLength = 0;
		for(String s : IRList) {
			lineLength = s.length() > lineLength ? s.length() : lineLength;
		}
		for(String s : varMap.keySet()) {
			varLength = s.length() > varLength ? s.length() : varLength;
		}
		for(int i = 0; i < lineLength + 1; ++i) {
			System.out.print(' ');
		}
		for(String s : varMap.keySet()) {
			System.out.print(" " + s);
		}
		System.out.println();
		for(int i = 0; i < lineLength + 1; ++i) {
			System.out.print(' ');
		}
		System.out.print('+');
		for(String s : varMap.keySet()) {
			for(int i = 0; i < s.length() + 1; ++i) {
				System.out.print('-');
			}
		}
		System.out.println();
		ArrayList<String> varList = new ArrayList<String>(varMap.keySet());
		for(int i = 0; i < IRList.size(); ++i) {
			System.out.print(IRList.get(i));
			for(int j = 0; j < lineLength - IRList.get(i).length() + 1; ++j) {
				System.out.print(' ');
			}
			System.out.print('|');
			for(int j = 0; j < liveMatrix[0].length; ++j) {
				System.out.print(" " + (liveMatrix[i][varMap.get(varList.get(j))] ? '1' : '0'));
				for(int k = 0; k < varList.get(j).length() - 1; ++k) {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
		
		// Print graph nodes
		System.out.println("Variable graph:");
		for(String s : varSetSorted) {
			if(s.contains("%f")) {
				System.out.println("\t" + varGraphFloat.get(s));
			} else {
				System.out.println("\t" + varGraphInt.get(s));
			}
		}
		
		// Print annotated IR code
		System.out.println("Annotated IR code:");
		for(String s : annotatedIRCode) {
			System.out.println("\t" + s);
		}
		
		// Print out the variables per function
		System.out.println("Variables per function:");
		for(String f : funcVarMap.keySet()) {
			System.out.println("\t" + f + ":");
			for(String v : funcVarMap.get(f)) {
				System.out.println("\t\t" + v);
			}
		}
	}
}
