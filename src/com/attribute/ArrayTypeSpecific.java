package com.attribute;


public class ArrayTypeSpecific {
	private boolean dim1, dim2;
	
	public ArrayTypeSpecific(boolean dim1, boolean dim2) {
		this.dim1 = dim1;
		this.dim2 = dim2;
	}
	
	public ArrayTypeSpecific() {
		this(false, false);
	}
	
	public boolean isTwoDimensionalArray() {
		return dim1 && dim2;
	}
	
	public boolean isOneDimensionalArray() {
		return dim1 && !dim2;
	}
	
	public boolean hasDimension() {
		return dim1 || dim2;
	}
	
	public boolean getDim1() {
		return dim1;
	}
	
	public void setDim1(boolean dim1) {
		this.dim1 = dim1;
	}
	
	public boolean getDim2() {
		return dim2;
	}
	
	public void setDim2(boolean dim2) {
		this.dim2 = dim2;
	}
	
	public boolean dimensionsMatch(ArrayTypeSpecific secondArrayTypeSpecific) {
		return dim1 == secondArrayTypeSpecific.getDim1() && 
				dim2 == secondArrayTypeSpecific.getDim2();  
	}
	
	public String toString() {
		String temp = "Is 2D: ";
		temp += isTwoDimensionalArray();
		temp += " dim1: " + dim1;
		temp += " dim2: " + dim2;
		return temp;
	}
}
