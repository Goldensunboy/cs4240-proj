package com.analyzer.basic_block_approach.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.analyzer.IRGeneratorForMIPS.getAnnotatedIR;
import com.analyzer.InstructionDetail;
import com.analyzer.RegisterAllocator;
import com.analyzer.basic_block_approach.BasicBlock;
import com.analyzer.basic_block_approach.BasicBlockFactory;

// TODO why isn't this static 
public class CFGRegisterAllocator implements RegisterAllocator{
	
	private BasicBlock root;
	private boolean generateLoad = true; 
	private boolean generateStore = true; 
	
	public CFGRegisterAllocator (List<String> IRList) {
		this.root = BasicBlockFactory.makeBasicBlocks(IRList); 
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {

		List<String> annotateIR = new ArrayList<>();
		while(root != null) {
			Map<String, Integer> intVariableOccurances = root.getIntVariableOccurances();
			Map<String, Integer> floatVariableOccurances = root.getFloatVariableOccurances();
			List<InstructionDetail> instructionDetails = root.getInstructionDetails();
			annotateIR.addAll(getAnnotatedIR(intVariableOccurances, floatVariableOccurances, 
					instructionDetails, generateLoad, generateStore));
			root = root.getNextBasicBlock();
		}
		
		return annotateIR;
	}
}