package com.analyzer.basic_block_approach.ebb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.analyzer.IRGeneratorForMIPS;
import com.analyzer.InstructionDetail;
import com.analyzer.basic_block_approach.BasicBlock;

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
		List<InstructionDetail> instructionDetails = new ArrayList<>();
		
		for(BasicBlock basicBlock : basicBlocks) {
			instructionDetails.addAll(basicBlock.getInstructionDetails());
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
		
		IRGeneratorForMIPS irGeneratorForMIPS = new IRGeneratorForMIPS();
		irGeneratorForMIPS.getAnnotatedIR(intVariableOccurances, floatVariableOccurances, instructionDetails);
		
		return null;
	}
}