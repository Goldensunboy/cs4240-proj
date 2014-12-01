package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.List;

public class EBB {
	private List<BasicBlock> basicBlocks;
	
	public EBB() {
		basicBlocks = new ArrayList<>();
	}
	
	public void addToBasicBlocks(BasicBlock basicBlock) {
		basicBlocks.add(basicBlock);
	}
}
