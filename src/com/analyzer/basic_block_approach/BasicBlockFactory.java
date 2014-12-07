package com.analyzer.basic_block_approach;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.analyzer.IRAnalyzer;
import com.analyzer.InstructionDetail;

public class BasicBlockFactory {

	private static boolean LETS_FALLTHROUGH = true;
	
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
		
		InstructionDetail firstFunction = IRDetails.remove(0);
		BasicBlock root = new BasicBlock();
		root.addInstructionDetail(firstFunction);
		
		BasicBlock currentBasicBlock = root;
		InstructionDetail previousInstructionDetail = IRDetails.get(0);
		Map<String, BasicBlock> labeledBasicBlocks = new Hashtable<>();
		
		for(InstructionDetail instructionDetail : IRDetails) {
			
			// LABEL, FUNC,
			if(instructionDetail.isLabelOrFunc()) {
				currentBasicBlock = makeLabelOrFuncAsLeader(instructionDetail, labeledBasicBlocks, previousInstructionDetail, currentBasicBlock);
			} 
			// previous BRANCH, current normal ==> new BasicBlock
			else if(previousInstructionDetail.isBranch()) {
				currentBasicBlock = makeAfterGotoOrBranchAsLeader(previousInstructionDetail, labeledBasicBlocks, currentBasicBlock, LETS_FALLTHROUGH);
			} 
			// previous GOTO, current normal ==> new BasicBlock
			else if(previousInstructionDetail.isGoto()) {
				currentBasicBlock = makeAfterGotoOrBranchAsLeader(previousInstructionDetail, labeledBasicBlocks, currentBasicBlock, !LETS_FALLTHROUGH);
			}
			
			currentBasicBlock.addInstructionDetail(instructionDetail);
			previousInstructionDetail = instructionDetail;
		}
		
		return root;
	}
	
	private static BasicBlock makeLabelOrFuncAsLeader(InstructionDetail instructionDetail, Map<String, BasicBlock> labeledBasicBlocks,
			InstructionDetail previousInstructionDetail, BasicBlock currentBasicBlock) {
		BasicBlock newBasicBlock = new BasicBlock();

		// LABEL
		if (instructionDetail.isLabel()) {
			// if labeled block is already instantiated, use that as newBasicBlock 
			String label = instructionDetail.getLabel();
			if(labeledBasicBlocks.containsKey(label)) {
				newBasicBlock = labeledBasicBlocks.get(label);						
			} else {
				labeledBasicBlocks.put(label, newBasicBlock);
			}
			
			/*
			 * GOTO doesn't let fall-through. So anything other than GOTO
			 * requires predecessor/successor manipulation
			 */
			if(!previousInstructionDetail.isGoto()) {
				setPredecessorSuccessor(currentBasicBlock, newBasicBlock);
			} 
			
			if(previousInstructionDetail.isBranchOrGoto()) {
				BasicBlock labeledBasicBlock = getLabeledBasicBlock(labeledBasicBlocks, previousInstructionDetail.getLabel());
				setPredecessorSuccessor(currentBasicBlock, labeledBasicBlock);
			}
		}
		return setNexPreviousPointers(currentBasicBlock, newBasicBlock);
	}
	
	private static BasicBlock makeAfterGotoOrBranchAsLeader(InstructionDetail previousInstructionDetail, 
			Map<String, BasicBlock> labeledBasicBlocks, BasicBlock currentBasicBlock, boolean letsFallThrough) {
		BasicBlock newBasicBlock = new BasicBlock();
		String label = previousInstructionDetail.getLabel();
		// BRANCH lets fall-through, successor/predecessor need work
		if(letsFallThrough) {
			setPredecessorSuccessor(currentBasicBlock, newBasicBlock);
		}
		
		// manipulate the successor/predecessor between labeledBlock and current block
		BasicBlock labeledBasicBlock = getLabeledBasicBlock(labeledBasicBlocks, label);
		setPredecessorSuccessor(currentBasicBlock, labeledBasicBlock);

		return setNexPreviousPointers(currentBasicBlock, newBasicBlock);
	}
	
	private static BasicBlock setNexPreviousPointers(BasicBlock currentBasicBlock, BasicBlock newBasicBlock) {
		currentBasicBlock.setNextBasicBlock(newBasicBlock);
		newBasicBlock.setPreviousBasicBlock(currentBasicBlock);
		return newBasicBlock;
	}
	
	private static void setPredecessorSuccessor(BasicBlock predecessor, BasicBlock successor) {
		successor.addToPredecessors(predecessor);
		predecessor.addToSuccessors(successor);
	}
	
	private static BasicBlock getLabeledBasicBlock(Map<String, BasicBlock> labeledBasicBlocks, String label) {
		BasicBlock labeledBasicBlock = labeledBasicBlocks.get(label);
		if(labeledBasicBlock == null) {
			labeledBasicBlock = new BasicBlock();
			labeledBasicBlocks.put(label, labeledBasicBlock);
		}
		return labeledBasicBlock;
	}
}