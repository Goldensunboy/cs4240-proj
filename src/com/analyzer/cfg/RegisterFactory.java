package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.exception.ShouldNotHappenException;

public class RegisterFactory {

	private Map<String, Integer> intVariableOccurances, floatVariableOccurances;
	private final int MAX_INTS = 18;
	private final int MAX_FLOATS = 24;
	private static final String FLOAT_IMMEDIATE_LOAD_FIRST_POSITION = "li.s, $f30, ";
	private static final String FLOAT_IMMEDIATE_LOAD_SECOND_POSITION = "li.s, $f31, ";
	private static final String INT_IMMEDIATE_LOAD_FIRST_POSITION = "li, $t8, ";
	private static final String INT_IMMEDIATE_LOAD_SECOND_POSITION = "li, $t9, ";
	private static final int FIRST_POSITION = 1; // TODO

	private Map<String, String> registerMap;
	
	private final static Comparator<Entry<String, Integer>> ValueComparator = new Comparator<Entry<String, Integer>>() {
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
		createRegisterMap();
	}
	
	private void createRegisterMap() {
		if(intVariableOccurances == null || floatVariableOccurances==null) {
			this.registerMap = null;
		}
		Map<String, String> registerMap = allocateRegister(intVariableOccurances, MAX_INTS);
		registerMap.putAll(allocateRegister(floatVariableOccurances, MAX_FLOATS));
		this.registerMap = registerMap;
	}
	
	public Map<String, String> getRegisterMap() {		
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
		return getNextAvailableRegister(variableName);
	}

	public static String getLoadImmediate(String number, int position) {
		boolean isFloat = number.matches(".*\\..*");
		if(position == FIRST_POSITION) {
			if(isFloat) {
				return FLOAT_IMMEDIATE_LOAD_FIRST_POSITION + number;
			} else {
				return INT_IMMEDIATE_LOAD_FIRST_POSITION + number;
			}
		} else {
			if (isFloat) {
				return FLOAT_IMMEDIATE_LOAD_SECOND_POSITION + number;
			} else {
				return INT_IMMEDIATE_LOAD_SECOND_POSITION + number;
			}
		}
	}

	public List<String> generateConversionIntToFloat(String[] variables) {
		if(variables.length>2) {
			throw new ShouldNotHappenException("Only two vairbales are expected");
		}
		
		List<String> conversionsToBeInserted = new ArrayList<>();
		boolean isFirstPosition = true;
		
		// this for loop is only gone through 2 times at most
		for (int i=0; i<variables.length; i++) {
			String variableName = variables[i];
			String allocatedRegister = getAllocatedRegister(variableName);
			if(allocatedRegister == null) {
				conversionsToBeInserted.addAll(generateConversionWithLoad(variableName, isFirstPosition));
			} else {
				conversionsToBeInserted.addAll(generateConversionWithoutLoad(variableName, isFirstPosition));
			}
			isFirstPosition = false;
		}
		
		return conversionsToBeInserted;
	}
	
	private String getAllocatedRegister(String variableName) {
		if (registerMap == null) {
			return null;
		}
		return registerMap.get(variableName);
	}

	private List<String> generateConversionWithoutLoad(String intRegisterName, boolean isFirstPosition) {
		String floatRegisterName = "$f" + (30 + (isFirstPosition ? 0 : 1));
		return getConversion(intRegisterName, floatRegisterName);
	}	
	
	private List<String> generateConversionWithLoad(String variableName, boolean isFirstPosition) {
		List<String> conversion = new ArrayList<>();
		
		String intRegisterName = "$t" + (8 + (isFirstPosition ? 0 : 1));
		String floatRegisterName = "$f" + (30 + (isFirstPosition ? 0 : 1));
		String load = "load, " + variableName + ", " + intRegisterName;
		
		conversion.add(load);
		conversion.addAll(getConversion(intRegisterName, floatRegisterName));
		
		return conversion;
	}
	
	private List<String> getConversion(String intRegisterName, String floatRegisterName) {
		List<String> conversion = new ArrayList<>();
		String mtc = "mtc, " + intRegisterName + ", " + floatRegisterName;
		String cvt = "cvt.s.w, " + floatRegisterName + ", " + floatRegisterName;
		conversion.add(mtc);
		conversion.add(cvt);
		return conversion;
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