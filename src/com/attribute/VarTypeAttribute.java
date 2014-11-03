package com.attribute;


public class VarTypeAttribute {
	public enum Datatype {
		Int,
		FixPt
	};
	private boolean isArray;
	private int arrayDimensions;
	private int dim1size, dim2size;
	private String alias;
	
	public VarTypeAttribute(boolean isArray, int arrayDimensions, int dim1size, int dim2size, String alias){
		this.isArray = isArray;
		this.arrayDimensions = arrayDimensions;
		this.dim1size = dim1size;
		this.dim2size = dim2size;
		this.alias = alias;	
	}
	
	public int getArrayDimensions() {
		return arrayDimensions;
	}
	public void setArrDimensions(int arrDimensions) {
		this.arrayDimensions = arrDimensions;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getDim1size() {
		return dim1size;
	}
	public void setDim1size(int dim1size) {
		this.dim1size = dim1size;
	}
	public int getDim2size() {
		return dim2size;
	}
	public void setDim2size(int dim2size) {
		this.dim2size = dim2size;
	}
}
