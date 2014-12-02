package com.analyzer.basic_block_approach.ebb;

import java.util.ArrayList;
import java.util.List;

import com.analyzer.RegisterAllocator;
import com.analyzer.basic_block_approach.BasicBlock;
import com.analyzer.basic_block_approach.BasicBlockFactory;

public class EBBRegisterAllocator implements RegisterAllocator{
	private BasicBlock root;
	
	public EBBRegisterAllocator(List<String> IRList) {
		this.root = BasicBlockFactory.makeBasicBlocks(IRList);
	}

	@Override
	public List<String> getAnnotatedIRCode() {
		makeEBB();
		return null;
	}
	
	private EBB makeEBB() {
		List<BasicBlock> leaders = new ArrayList<>();
		List<BasicBlock> ebbElements = new ArrayList<>();
		List<EBB> ebbs = new ArrayList<>();
		leaders.add(root);
		
		while(!leaders.isEmpty()) {
			BasicBlock leader = leaders.remove(0);
			ebbElements.add(leader);
			EBB extendedBasicBlock = new EBB();
			
			while(!ebbElements.isEmpty()) {
				BasicBlock currentBasicBlock = ebbElements.remove(0);
				extendedBasicBlock.addToBasicBlocks(currentBasicBlock);
				for(BasicBlock successor : currentBasicBlock.getSuccessors()) {
					if(successor.getPredecessors().size() > 1) {
						leaders.add(successor);
					} else {
						ebbElements.add(successor);
					}
				}
			}
			
			ebbs.add(extendedBasicBlock);
		}
		return ebbs.get(0);
	}
}