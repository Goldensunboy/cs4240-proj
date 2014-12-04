package com.analyzer.basic_block_approach.ebb;

import static com.analyzer.IRGeneratorForMIPS.getAnnotatedIR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.analyzer.InstructionDetail;
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
		List<String> annotateIR = new ArrayList<>();
		boolean generateLoad = false;
		boolean generateStore = false;

		BasicBlock currentBasicBlock = root;
		
		EBB currentEBB = currentBasicBlock.getEnclosingEBB();
		EBB nextEBB;
		
		while(currentBasicBlock != null) {
			
			currentEBB.buildOccuranceMaps();
			
			Map<String, Integer> intVariableOccurances = currentBasicBlock.getEnclosingEBB().getIntVariableOccurances();
			Map<String, Integer> floatVariableOccurances = currentBasicBlock.getEnclosingEBB().getFloatVariableOccurances();
			List<InstructionDetail> instructionDetails = currentBasicBlock.getInstructionDetails();
			
			if(currentBasicBlock.isStartOfEBB()) {
				generateLoad = true;
			} else {
				generateLoad = false;
			}
			
			if(currentBasicBlock.isEndOfEBB()) {
				generateStore = true;
			} else {
				generateStore = false;
			} 
			
			currentBasicBlock = currentBasicBlock.getNextBasicBlock();
			if(currentBasicBlock != null) {
				nextEBB = currentBasicBlock.getEnclosingEBB();
				
				if(!currentEBB.equals(nextEBB)) {
					generateStore = true;
				}
				
				currentEBB = nextEBB;				
			}
			
			annotateIR.addAll(getAnnotatedIR(intVariableOccurances, floatVariableOccurances, 
					instructionDetails, generateLoad, generateStore));

		}
		
		return annotateIR;
	}
	
	private EBB makeEBB() {
		List<BasicBlock> leaders = new ArrayList<>();
		List<BasicBlock> ebbElements = new ArrayList<>();
		List<EBB> ebbs = new ArrayList<>();
		
		BasicBlock currentRoot = root;
		
		leaders.add(currentRoot);
		
		while(!leaders.isEmpty()) {
			BasicBlock leader = leaders.remove(0);			
			ebbElements.add(leader);
			EBB enclosingEBB = new EBB();
			
			leader.setAsStartOfEBB();
			
			while(!ebbElements.isEmpty()) {
				BasicBlock currentBasicBlock = ebbElements.remove(0);
				currentBasicBlock.setEnclosingEBB(enclosingEBB);
				
				enclosingEBB.addToBasicBlocks(currentBasicBlock);
				for(BasicBlock successor : currentBasicBlock.getSuccessors()) {
					if(successor.getPredecessors().size() > 1) {
						leaders.add(successor);
					} else {
						ebbElements.add(successor);
					}
				}

				if(ebbElements.isEmpty()) {
					currentBasicBlock.setAsEndOfEBB();
				}
				
			}
			
			ebbs.add(enclosingEBB);
		}
		return ebbs.get(0);
	}
}