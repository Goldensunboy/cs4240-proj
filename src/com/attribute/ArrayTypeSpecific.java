package com.attribute;


public class ArrayTypeSpecific {
	private boolean isTwoDimensionalArray;
	private int dim1, dim2;
	
	public ArrayTypeSpecific(boolean isTwoDimensionalArray, int dim1, int dim2) {
		this.isTwoDimensionalArray = isTwoDimensionalArray ;
		this.dim1 = dim1;
		this.dim2 = dim2;
	}
	
	public ArrayTypeSpecific() {
		this(false, -1, -1);
	}
	
	public boolean isTwoDimensionalArray() {
		return isTwoDimensionalArray;
	}
	public void setTwoDimensionalArray(boolean isTwoDimensionalArray) {
		this.isTwoDimensionalArray = isTwoDimensionalArray;
	}
	public int getDim1() {
		return dim1;
	}
	public void setDim1(int dim1) {
		this.dim1 = dim1;
	}
	public int getDim2() {
		return dim2;
	}
	public void setDim2(int dim2) {
		this.dim2 = dim2;
	}
	
	public String toString() {
		String temp = "Is 2D: ";
		temp += isTwoDimensionalArray;
		temp += " dim1: " + dim1;
		temp += " dim2: " + dim2;
		return temp;
	}
}
