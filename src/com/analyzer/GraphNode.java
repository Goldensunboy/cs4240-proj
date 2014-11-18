package com.analyzer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GraphNode {
	private String varName;
	private int color = -1;
	private Set<GraphNode> neighbors;
	public GraphNode(String varName) {
		this.varName = varName;
		neighbors = new HashSet<GraphNode>();
	}
	public void connect(GraphNode n) {
		neighbors.add(n);
		n.connect2(this);
	}
	private void connect2(GraphNode n) {
		neighbors.add(n);
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public Set<GraphNode> getNeighbors() {
		return neighbors;
	}
	public void populateStack(LinkedList<GraphNode> stack) {
		stack.addFirst(this);
		for(GraphNode n : neighbors) {
			if(!stack.contains(n)) {
				n.populateStack(stack);
			}
		}
	}
	public String toString() {
		String neighborsList = "[";
		boolean b = false;
		for(GraphNode n : neighbors) {
			if(b) {
				neighborsList += ", ";
			} else {
				b = true;
			}
			neighborsList += n.getColor();
		}
		neighborsList += "]";
		return varName + "\tColor: " + color + "\tNeighbors: " + neighborsList;
	}
}