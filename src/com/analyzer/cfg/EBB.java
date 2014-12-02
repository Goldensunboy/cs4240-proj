package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EBB {
	private List<BasicBlock> basicBlocks;
	
	public EBB() {
		basicBlocks = new ArrayList<>();
	}
	
	public void addToBasicBlocks(BasicBlock basicBlock) {
		basicBlocks.add(basicBlock);
	}
	
	public List<String> getAnnotatedIR() {
		Map<String, Integer> intVariableOccurances = new Hashtable<>();
		Map<String, Integer> floatVariableOccurances = new Hashtable<>();
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
		return null;
	}
}
