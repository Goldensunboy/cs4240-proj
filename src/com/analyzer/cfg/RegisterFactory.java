package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class RegisterFactory {

	private Map<String, Integer> intVariableOccurances, floatVariableOccurances;
	private final int MAX_INTS = 18;
	private final int MAX_FLOATS = 24;
	private static Comparator<Entry<String, Integer>> ValueComparator = new Comparator<Entry<String, Integer>>() {
		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			Integer value1 = o1.getValue();
			Integer value2 = o2.getValue();
			return value2.compareTo(value1);
		}
	};

	public RegisterFactory(Map<String, Integer> intVariableOccurances, Map<String, Integer> floatVariableOccurances) {
		this.intVariableOccurances = intVariableOccurances;
		this.floatVariableOccurances = floatVariableOccurances;
		fillOutAvailableRegisters();
	}
	
	public Map<String, String> allocateRegister() {
		Map<String, String> registerMap = allocateRegister(intVariableOccurances, MAX_INTS);
		registerMap.putAll(allocateRegister(floatVariableOccurances, MAX_FLOATS));
		return registerMap;
	}
	
	
	private Map<String, String> allocateRegister(Map<String, Integer> variableOccurances, int MAX) {
		Map<String, String> registerMap = new Hashtable<>();

		if(variableOccurances.size() <= MAX) {
			for(Entry<String, Integer> entry : variableOccurances.entrySet()) {
				String variableName = entry.getKey();
				String variableNameRegisterAnnotated = annotateWithRegister(variableName);
				registerMap.put(variableName, variableNameRegisterAnnotated);
			}
			
			return registerMap;
		}
		
		PriorityQueue<Entry<String, Integer>> entryPQ = new PriorityQueue<>(variableOccurances.size(), ValueComparator);
		for(Entry<String, Integer> entry : variableOccurances.entrySet()) {
			entryPQ.add(entry);
		}
		
		for(int i=0; i<MAX; i++) {
			String variableName = entryPQ.poll().getKey();
			String variableNameRegisterAnnotated = annotateWithRegister(variableName);
			registerMap.put(variableName, variableNameRegisterAnnotated);
		}
		
		return registerMap;	
	}
	
	private String annotateWithRegister(String variableName){
		return /*variableName + "#" +*/ getNextAvailableRegister(variableName);
	}

	/*
	 * TODO Below this line are temporary methods that need to be soon replaced
	 */
	List<String> availableInts = new ArrayList<>();
	List<String> availableFloats = new ArrayList<>();
	private void fillOutAvailableRegisters() {
		for(int i=0; i<18; i++) {
			availableInts.add("iTemp"+i);
			availableFloats.add("fTemp"+i);
		}
		for(int i=18; i<24; i++) {
			availableFloats.add("fTemp"+i);
		}
	}
	
	private String getNextAvailableRegister(String variableName) {
		boolean isInt = variableName.split("%")[1].equals("i");
		if(isInt) {
			return availableInts.remove(0);
		}
		return availableFloats.remove(0);
	}
}