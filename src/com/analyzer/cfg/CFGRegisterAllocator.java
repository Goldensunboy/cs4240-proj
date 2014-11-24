package com.analyzer.cfg;

import java.util.ArrayList;
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
		BasicBlock currentBasicBlock = new BasicBlock();
		Map<String, List<BasicBlock>> cfgMap = new Hashtable<>();
		for (InstructionDetail instructionDetail : IRDetails) {
			
			currentBasicBlock.addToUseDef(instructionDetail);
			
			if (instructionDetail.isControlFlow()) {
				List<BasicBlock> predecessorList = cfgMap.get(instructionDetail.getLabel());
				if(predecessorList == null) {
					predecessorList = new ArrayList<>();
				}
				predecessorList.add(currentBasicBlock);
				
				BasicBlock newBasicBlock = new BasicBlock();
				currentBasicBlock.addToSuccessors(newBasicBlock);
				newBasicBlock.addToPredecessors(currentBasicBlock);
				
				currentBasicBlock = newBasicBlock;
			}
		}
	}
}
