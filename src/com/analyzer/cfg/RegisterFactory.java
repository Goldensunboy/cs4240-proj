package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import static code_generation.RegisterFile.AVAILABLE_FLOAT_REGISTERS;
import static code_generation.RegisterFile.AVAILABLE_TEMPORARY_FLOAT_REGISTERS;
import static code_generation.RegisterFile.AVAILABLE_INT_REGISTERS;
import static code_generation.RegisterFile.AVAILABLE_TEMPORARY_INT_REGISTERS;
import static com.analyzer.InstructionUtility.isIntLiteral;
import static com.analyzer.InstructionUtility.isIntRegister;
import static com.analyzer.InstructionUtility.getConversion;
import static com.analyzer.InstructionUtility.isIntIsh;
import static com.analyzer.InstructionUtility.isFloatIsh;

import com.exception.ShouldNotHappenException;

public class RegisterFactory {

	private Map<String, Integer> intVariableOccurances, floatVariableOccurances;
	private final int MAX_INT_REGISTERS = AVAILABLE_INT_REGISTERS.length;
	private final int MAX_FLOAT_REGISTERS = AVAILABLE_FLOAT_REGISTERS.length;
	private int availableIntRegisterIndex = 0, availableFloatRegisterIndex = 0;
	private int availableTemporaryIntRegisterIndex = 0, availableTemporaryFloatRegisterIndex = 0;
	private static final boolean IS_FLOAT = true; 
	private static final boolean IS_INT = !IS_FLOAT;
	
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
		
		createRegisterMap();
	}
	
	private void createRegisterMap() {
		if(intVariableOccurances == null || floatVariableOccurances==null) {
			this.registerMap = null;
		}
		Map<String, String> registerMap = allocateRegister(intVariableOccurances, MAX_INT_REGISTERS);
		registerMap.putAll(allocateRegister(floatVariableOccurances, MAX_FLOAT_REGISTERS));
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
				String variableNameRegisterAnnotated = getNextAvailableRegister(variableName);
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
			String variableNameRegisterAnnotated = getNextAvailableRegister(variableName);
			registerMap.put(variableName, variableNameRegisterAnnotated);
		}
		
		return registerMap;	
	}
	
	private String getRegisterFromMaps(String variableName, Map<String, String> temporaryVariablesRegisterMap) {
		String registerName = registerMap.get(variableName);
		return registerName != null ? registerName : temporaryVariablesRegisterMap.get(variableName);
	}
	
	public List<String> generateConversionIntToFloat(String[] lhsVariables, String[] rhsVariables, Map<String, String> temporaryVariablesRegisterMap) {
		List<String> registersToPromote = getRegistersToPromote(lhsVariables, rhsVariables, temporaryVariablesRegisterMap);
		return getPromotions(registersToPromote);
	}
	
	private List<String> getRegistersToPromote(String[] lhsVariables, String[] rhsVariables, Map<String, String> temporaryVariablesRegisterMap) {
		List<String> registersToPromote = new ArrayList<>();
		
		if(rhsVariables == null) {
			return registersToPromote;
		}
		
		for(String variableName : rhsVariables) {
			String registerName = getRegisterFromMaps(variableName, temporaryVariablesRegisterMap);
			if(registerName == null) {
				throw new ShouldNotHappenException("Couldn't find the corresponding register for: " + variableName);
			}
			
			if(isIntIsh(variableName)) {				
				registersToPromote.add(registerName);
			}
		}			
		
		if(registersToPromote.size() != rhsVariables.length) {
			return registersToPromote; 
		} else {
			if(lhsVariables == null) {
				return new ArrayList<>();
			}
			if(isFloatIsh(lhsVariables[0])) {
				return registersToPromote;
			}
		}
		
		return registersToPromote;
	}
		
	private List<String> getPromotions(List<String> registersToPromote) {
		List<String> conversionsToBeInserted = new ArrayList<>();
		if (registersToPromote == null || registersToPromote.size() == 0) {
			return conversionsToBeInserted; 
		}
		
		for (String intRegisterName : registersToPromote) {
			conversionsToBeInserted.addAll(getConversion(intRegisterName, getAvailableTemporaryRegister(IS_FLOAT)));
		}
		
		return conversionsToBeInserted;
	}
	
	private String getAvailableTemporaryRegister(boolean isFloat) {
		if(isFloat) {
			return AVAILABLE_TEMPORARY_FLOAT_REGISTERS[availableTemporaryFloatRegisterIndex++];	
		}
		return AVAILABLE_TEMPORARY_INT_REGISTERS[availableTemporaryIntRegisterIndex++];
	}

	private String getNextAvailableRegister(String variableName) {
		boolean isInt = variableName.split("%")[1].equals("i");
		if(isInt) {
			return AVAILABLE_INT_REGISTERS[availableIntRegisterIndex++];
		}
		return AVAILABLE_FLOAT_REGISTERS[availableFloatRegisterIndex++];
	}
	
	public void resetAvailableTemporaryRegisterIndex() {
		availableTemporaryIntRegisterIndex = 0;
		availableTemporaryFloatRegisterIndex = 0;
	}
	
	public Map<String, String> createTemporaryRegisterMap(String[] variablesNeedLoad, String[] variablesNeedStore) {
		Map<String, String> temporaryRegisterMap = new Hashtable<>();

		if(variablesNeedLoad != null) {
			for(String variableName : variablesNeedLoad) {
				if(registerMap.containsKey(variableName)) {
					continue;
				}
				if(isIntLiteral(variableName) || isIntRegister(variableName)) {
					temporaryRegisterMap.put(variableName, getAvailableTemporaryRegister(IS_INT));
				} else {
					temporaryRegisterMap.put(variableName, getAvailableTemporaryRegister(IS_FLOAT));
				}
			}
		}
		
		if(variablesNeedStore != null) {	
			for(String variableName : variablesNeedStore) {
				if(registerMap.containsKey(variableName)) {
					continue;
				}
				if(isIntLiteral(variableName) || isIntRegister(variableName)) {
					temporaryRegisterMap.put(variableName, getAvailableTemporaryRegister(IS_INT));
				} else {
					temporaryRegisterMap.put(variableName, getAvailableTemporaryRegister(IS_FLOAT));
				}
			}
		}
		
		return temporaryRegisterMap;
	}
}