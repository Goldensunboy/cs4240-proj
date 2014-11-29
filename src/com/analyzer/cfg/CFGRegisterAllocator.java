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
		draw(makeBasicBlocks());
		
		// TODO - Testing, not complete
		List<String> myRetVal = new ArrayList<>();
		for (InstructionDetail detail : IRDetails) {
			myRetVal.add(detail.toString());
		}
		return myRetVal;
	}
	
	private void draw(BasicBlock block) {
		System.out.println(block);
		for (BasicBlock successor : block.getsuccessors()) {
			draw(successor);
		}
	}
	
	private BasicBlock makeBasicBlocks() {
		
		//BasicBlock needs to hold the first IR as the leader
		BasicBlock currentBasicBlock = new BasicBlock();
		currentBasicBlock.temp(IRDetails.get(0)); 
		BasicBlock root = currentBasicBlock;
		
		Map<String, BasicBlock> labeledBasicBlocks = new Hashtable<>();
		boolean currentJustGotCreatedFromFallThrough= false;
		
		for (int i=1; i<IRDetails.size(); i++) {
			InstructionDetail instructionDetail = IRDetails.get(i);

			if(instructionDetail.isControlFlow()) {

				//hitting a control flow changes the basic block
				BasicBlock newBasicBlock;
				String label = instructionDetail.getLabel();
				
				//Labels are leaders
				if(instructionDetail.isLabel()) {
					newBasicBlock = labelAsLeader(labeledBasicBlocks, currentBasicBlock, label, currentJustGotCreatedFromFallThrough);
					newBasicBlock.temp(instructionDetail);
					currentJustGotCreatedFromFallThrough = false;
					
				} else { //branches are not leaders
					currentBasicBlock.temp(instructionDetail);
					newBasicBlock = afterGotoAsLabel(labeledBasicBlocks, currentBasicBlock, label, instructionDetail.letsFallThrough());
					currentJustGotCreatedFromFallThrough = instructionDetail.letsFallThrough();
				}
				
				currentBasicBlock = newBasicBlock;
			} else { 
				currentBasicBlock.temp(instructionDetail);
				currentJustGotCreatedFromFallThrough = false;
			}
		}
		
		return root;
	}
	
	private BasicBlock labelAsLeader(Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, 
			String label, boolean currentJustGotCreatedFromFallThrough) {
		
		BasicBlock newBasicBlock = labeledBasicBlocks.get(label);
		
		if(newBasicBlock == null) {
			newBasicBlock = new BasicBlock();
			labeledBasicBlocks.put(label, newBasicBlock);
		}
		
		/*
		 * Takes care of fall-through from a branch directly to Label block
		 * 
		 * Swapping the currentBasicBlock with newBasicBlock
		 */
		if(currentJustGotCreatedFromFallThrough) {
			for (BasicBlock predecessor : currentBasicBlock.getPredecessors()) {							
				newBasicBlock.addToPredecessors(predecessor);
				predecessor.getsuccessors().remove(currentBasicBlock);
				predecessor.addToSuccessors(newBasicBlock);
			}
		} else {
			currentBasicBlock.addToSuccessors(newBasicBlock);
			newBasicBlock.addToPredecessors(currentBasicBlock);
		}
		
		return newBasicBlock;
	}
	
	private BasicBlock afterGotoAsLabel(Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, 
			String label, boolean letsFallThrough) {
		
		//add currentBasicBlock to the predecessors of the block we jump to 
		BasicBlock labeledBasicBlock = labeledBasicBlocks.get(label);
		if(labeledBasicBlock == null) {
			labeledBasicBlock = new BasicBlock();
			labeledBasicBlocks.put(label, labeledBasicBlock);						
		}
		labeledBasicBlock.addToPredecessors(currentBasicBlock);
		currentBasicBlock.addToSuccessors(labeledBasicBlock);

		BasicBlock newBasicBlock = new BasicBlock();
		if(letsFallThrough) {
			newBasicBlock.addToPredecessors(currentBasicBlock);
			currentBasicBlock.addToSuccessors(newBasicBlock);
		}
		return newBasicBlock;
	}
}
