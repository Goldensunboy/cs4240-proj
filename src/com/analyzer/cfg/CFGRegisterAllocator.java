package com.analyzer.cfg;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.analyzer.IRAnalyzer;
import com.analyzer.InstructionDetail;
import com.analyzer.RegisterAllocator;

public class CFGRegisterAllocator implements RegisterAllocator{
	private List<String> IRList;
	private BasicBlock root;
	private List<InstructionDetail> IRDetails;
	
	public CFGRegisterAllocator (List<String> IRList) {
		this.IRList = IRList;
		this.IRDetails = IRAnalyzer.analyze(IRList); 
		root = new BasicBlock();
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {
		makeBasicBlocks();
		return null;
	}
	
	private void makeBasicBlocks() {
		/*
		 * TODO
		 * we want a map here to hold on to the BasicBlock references. Once the 
		 * label is encountered, manipulate the predecessor/successor accordingly
		 */
		Map<String, BasicBlock> cfgMap = new Hashtable<>();
	}
}
