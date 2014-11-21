package com.analyzer;

import java.util.ArrayList;
import java.util.List;

public class IRAnalyzer {
	public static List<InstructionDetail> analyze(List<String> IRList) {
		List<InstructionDetail> analyzedIR = new ArrayList<>();
		
		for(String instruction : IRList) {
			analyzedIR.add(new InstructionDetail(instruction));
		}
		
		return analyzedIR;
	}
}
