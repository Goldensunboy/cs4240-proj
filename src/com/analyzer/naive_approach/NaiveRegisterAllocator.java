package com.analyzer.naive_approach;

import static com.analyzer.IRGeneratorForMIPS.getAnnotatedIR;

import java.util.Hashtable;
import java.util.List;

import com.analyzer.IRAnalyzer;
import com.analyzer.InstructionDetail;
import com.analyzer.RegisterAllocator;
public class NaiveRegisterAllocator implements RegisterAllocator{

	private List<InstructionDetail> instructionDetails;
	public NaiveRegisterAllocator(List<String> IRList) {
		this.instructionDetails = IRAnalyzer.analyze(IRList);
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {
		return getAnnotatedIR(new Hashtable<String, Integer>(), new Hashtable<String, Integer>(), instructionDetails, true, true);
	}

}
