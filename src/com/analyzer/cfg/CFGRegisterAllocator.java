package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.analyzer.IRAnalyzer;
import com.analyzer.InstructionDetail;
import com.analyzer.RegisterAllocator;

public class CFGRegisterAllocator implements RegisterAllocator{
	private List<InstructionDetail> IRDetails;
	
	public CFGRegisterAllocator (List<String> IRList) {
		this.IRDetails = IRAnalyzer.analyze(IRList); 
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {
		BasicBlock root = makeBasicBlocks();

		List<String> annotateIR = new ArrayList<>();
		while(root != null) {
			annotateIR.addAll(root.getAnnotatedIR());
			root = root.getNextBasicBlock();
		}
		
		return annotateIR;
	}

	@SuppressWarnings("unused")
	private List<String> getIRDetailsToDraw() {
		// TODO - for testing
		List<String> myRetVal = new ArrayList<>();
		for (InstructionDetail detail : IRDetails) {
			myRetVal.add(detail.toString());
		}
		return myRetVal;
	}
	
	@SuppressWarnings("unused")
	private void drawBlocks(BasicBlock block) {
		//TODO - for testing
		while(block != null) {
			System.out.println(block);
			block = block.getNextBasicBlock();
		}
	}
	
	private BasicBlock makeBasicBlocks() {
		
		//BasicBlock needs to hold the first IR as the leader
		BasicBlock currentBasicBlock = new BasicBlock();
		currentBasicBlock.addInstructionDetail(IRDetails.get(0)); 
		BasicBlock root = currentBasicBlock;
		
		Map<String, BasicBlock> labeledBasicBlocks = new Hashtable<>();
		boolean currentJustGotCreatedFromConditional= false;
		
		for (int i=1; i<IRDetails.size(); i++) {
			InstructionDetail instructionDetail = IRDetails.get(i);

			if(instructionDetail.isControlFlow()) {

				//hitting a control flow changes the basic block
				BasicBlock newBasicBlock;
				String label = instructionDetail.getLabel();
				
				//Labels are leaders
				if(instructionDetail.isLabel()) {
					newBasicBlock = labelAsLeader(labeledBasicBlocks, currentBasicBlock, label, currentJustGotCreatedFromConditional);
					newBasicBlock.addInstructionDetail(instructionDetail);
					currentJustGotCreatedFromConditional = false;
					
				} else { //branches are not leaders
					currentBasicBlock.addInstructionDetail(instructionDetail);
					newBasicBlock = afterGotoAsLabel(labeledBasicBlocks, currentBasicBlock, label, instructionDetail.letsFallThrough());
					currentJustGotCreatedFromConditional = true;
				}
				currentBasicBlock.setNextBasicBlock(newBasicBlock);
				newBasicBlock.setPreviousBasicBlock(currentBasicBlock);
				currentBasicBlock = newBasicBlock;
			} else { 
				currentBasicBlock.addInstructionDetail(instructionDetail);
				currentJustGotCreatedFromConditional = false;
			}
		}
		
		return root;
	}
	
	private BasicBlock labelAsLeader(Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, 
			String label, boolean currentJustGotCreatedFromConditional) {
		
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
		if(currentJustGotCreatedFromConditional) {
			for (BasicBlock predecessor : currentBasicBlock.getPredecessors()) {							
				newBasicBlock.addToPredecessors(predecessor);
				predecessor.getSuccessors().remove(currentBasicBlock);
				predecessor.addToSuccessors(newBasicBlock);
				
			}
			currentBasicBlock.getPreviousBasicBlock().setNextBasicBlock(newBasicBlock);
			newBasicBlock.setPreviousBasicBlock(currentBasicBlock.getPreviousBasicBlock());
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
