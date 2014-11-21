package com.analyzer.cfg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Each basic block should know about its predecessors and successors. 
 * Also it should know about the 'in, 'use' and 'def' to be able to construct
 * the 'out' set 
 * @author saman
 * 
 */
public class BasicBlock {

	private List<BasicBlock> predecessors, successors;
	private List<String> use, def; 
	private Set<String> in, out; 
	
	public BasicBlock() {
		predecessors = new ArrayList<>();
		successors = new ArrayList<>();
		use = new ArrayList<>();
		def = new ArrayList<>();
		in = new HashSet<>();
		out = new HashSet<>();
	}

	public void calculateIn() {
		//in[I] = (out[I] - def[i]) U use[I]
		Set<String> tempOut = out;
		tempOut.removeAll(def);
		tempOut.addAll(use);
		setIn(tempOut);
	}
	
	public boolean hasPredecessor() {
		return predecessors.size() != 0;
	}
	
	public boolean hasSuccessor() {
		return successors.size() != 0;
	}
	
	public void addToPredecessors(BasicBlock predecessor) {
		predecessors.add(predecessor);
	}
	
	public void addToSuccessors(BasicBlock successor) {
		successors.add(successor);
	}
	
	public List<BasicBlock> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(List<BasicBlock> predecessors) {
		this.predecessors = predecessors;
	}

	public List<BasicBlock> getsuccessors() {
		return successors;
	}

	public void setsuccessors(List<BasicBlock> successors) {
		this.successors = successors;
	}

	public List<String> getUse() {
		return use;
	}

	public void setUse(List<String> use) {
		this.use = use;
	}

	public List<String> getDef() {
		return def;
	}

	public void setDef(List<String> def) {
		this.def = def;
	}

	public Set<String> getIn() {
		return in;
	}

	public void setIn(Set<String> in) {
		this.in = in;
	}

	public Set<String> getOut() {
		return out;
	}

	public void setOut(Set<String> out) {
		this.out = out;
	}
	
	
	
}
