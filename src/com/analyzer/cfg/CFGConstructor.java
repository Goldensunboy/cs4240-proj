package com.analyzer.cfg;

import java.util.List;

import com.analyzer.RegisterAllocator;

public class CFGConstructor implements RegisterAllocator{
	List<String> IRList;
	public CFGConstructor(List<String> IRList) {
		this.IRList = IRList;
	}
	
	@Override
	public List<String> getAnnotatedIRCode() {
		for(String line : IRList) {
		}
		// TODO Auto-generated method stub
		return null;
	}
	
}
