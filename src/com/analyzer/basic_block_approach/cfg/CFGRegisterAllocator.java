package com.analyzer.basic_block_approach.cfg;

import java.util.ArrayList;
import java.util.List;

import com.analyzer.RegisterAllocator;
import com.analyzer.basic_block_approach.BasicBlock;
import com.analyzer.basic_block_approach.BasicBlockFactory;

public class CFGRegisterAllocator implements RegisterAllocator{
	
	private BasicBlock root;
	
	public CFGRegisterAllocator (List<String> IRList) {
		this.root = BasicBlockFactory.makeBasicBlocks(IRList); 
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {

		List<String> annotateIR = new ArrayList<>();
		while(root != null) {
			annotateIR.addAll(root.getAnnotatedIR());
			root = root.getNextBasicBlock();
		}
		
		return annotateIR;
	}
}