package com.analyzer.basic_block_approach;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.analyzer.IRAnalyzer;
import com.analyzer.InstructionDetail;
import com.analyzer.Instructions;

public class BasicBlockFactory {

	/**
	 * goes through the IRDetails of the WHOLE program. Identifies the control flows (labels, branches)
	 * branches are the last IR of a basic block and labels are the beginning IR of a basic block.
	 * 
	 * If and IR is not a control flow, simply add to basic block
	 * 
	 * @param IRList
	 * @return first basic block
	 */
	public static BasicBlock makeBasicBlocks(List<String> IRList) {	
		List<InstructionDetail> IRDetails = IRAnalyzer.analyze(IRList);
		
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
				
				//function name starts a basic block with no predecessor 
				if(instructionDetail.getInstruction().equals(Instructions.FUNC)) {
					newBasicBlock = new BasicBlock();
					newBasicBlock.addInstructionDetail(instructionDetail);
					currentJustGotCreatedFromConditional = false;
				} else if(instructionDetail.isLabel()) {
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
				
				/*
				 *  NOTE: If the instruction is RETURN, then it will be followed by a 
				 *  new function name or EOF. Therefore we don't need to explicitly
				 *  do anything for RETURN. 
				 */
			}
		}
		
		return root;
	}
	
	/**
	 * Basic block can have a label IR as a leader or a non-label IR after branch as the 
	 * leader. 
	 * This method takes care of the former case.
	 * 
	 * @param labeledBasicBlock - once hit a label in any part of the IR we generate its corresponding 
	 * basic block. Once we actually hit the line with only the label i.e. beginning of the basic block
	 * we use this map to remember it's predecessor and successor
	 * 
	 * @param currentBasicBlock - will be swapped with the newly generated label-BasicBlock
	 * @param label 
	 * @param currentJustGotCreatedFromConditional - takes care of predecessor pointer to the
	 * label-BasicBlock, if the IR caused a fall-through
	 * @return
	 */
	private static BasicBlock labelAsLeader(Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, 
			String label, boolean currentJustGotCreatedFromConditional) {
		
		BasicBlock newBasicBlock = labeledBasicBlocks.get(label);
		
		if(newBasicBlock == null) {
			newBasicBlock = new BasicBlock();
			labeledBasicBlocks.put(label, newBasicBlock);
		}
		
		/*
		 * Takes care of fall-through from a branch directly to Label block
		 * 
		 * Swapping the currentBasicBlock with newBasicBlock, but recording the 
		 * appropriate predecessor/successor pointers 
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
	
	/**
	 * IR after goto will be leader too 
	 * @param labeledBasicBlocks
	 * @param currentBasicBlock
	 * @param label
	 * @param letsFallThrough
	 * @return
	 */
	private static BasicBlock afterGotoAsLabel(Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, 
			String label, boolean letsFallThrough) {
		
		/*
		 * add currentBasicBlock to the predecessors of the block we jump to
		 * NOTE: the following code block doesn't have affects on the new basic block
		 * it merely adjusts the predecessor/successor pointers between the block we 
		 * jump to in the goto IR above the currently considered IR and the basic block 
		 * we jump from 
		 */
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
