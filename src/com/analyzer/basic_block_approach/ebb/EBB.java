package com.analyzer.basic_block_approach.ebb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.analyzer.basic_block_approach.BasicBlock;

public class EBB {
	private List<BasicBlock> basicBlocks;
	private Map<String, Integer> intVariableOccurances;
	private Map<String, Integer> floatVariableOccurances;
	
	public EBB() {
		intVariableOccurances = new Hashtable<>();
		floatVariableOccurances = new Hashtable<>();
		basicBlocks = new ArrayList<>();
	}
	
	public void addToBasicBlocks(BasicBlock basicBlock) {
		basicBlocks.add(basicBlock);
	}

	/*
	 * The methods below should be called only after all the basic blocks have 
	 * been added to this EBB
	 */
	public void buildOccuranceMaps() {
		for(BasicBlock basicBlock : basicBlocks) {
			for(Entry<String, Integer> occurance : basicBlock.getIntVariableOccurances().entrySet()) {
				Integer previousOccurance = intVariableOccurances.get(occurance.getKey());
				if(previousOccurance != null) {
					intVariableOccurances.put(occurance.getKey(), occurance.getValue() + previousOccurance);
				} else {
					intVariableOccurances.put(occurance.getKey(), occurance.getValue());
				}
			}
			for(Entry<String, Integer> occurance : basicBlock.getFloatVariableOccurances().entrySet()) {
				Integer previousOccurance = floatVariableOccurances.get(occurance.getKey());
				if(previousOccurance != null) {
					floatVariableOccurances.put(occurance.getKey(), occurance.getValue() + previousOccurance);
				} else {
					floatVariableOccurances.put(occurance.getKey(), occurance.getValue());
				}
			}
		}
	}
	
	public Map<String, Integer> getIntVariableOccurances() {
		return intVariableOccurances;
	}
	
	public Map<String, Integer> getFloatVariableOccurances() {
		return floatVariableOccurances;
	}
	
	public String toString() {
		return basicBlocks.toString();
	}
}