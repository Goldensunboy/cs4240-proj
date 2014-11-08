package com.attribute;

import com.compiler.Type;


public class TypeAttribute implements Attribute{
	private boolean isTwoDimensionalArray = true;
	private boolean isArray = false;
	private int dim1, dim2;
	private String aliasName;
	private Type type, typeOfArray;
	
	public TypeAttribute(String aliasName, Type type, Type typeOfArray, boolean isTwoDimensionalArray, int dim1, int dim2) {
		this.aliasName = aliasName;
		this.type = type;
		this.typeOfArray = typeOfArray;
		this.isTwoDimensionalArray = isTwoDimensionalArray;
		this.dim1 = dim1;
		this.dim2 = dim2;
	}
	
	public boolean isTwoDimensionalArray() {
		return isArray && isTwoDimensionalArray;
	}

	@Override
	public Type getType() {
		return type;
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

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Type getTypeOfArray() {
		return typeOfArray;
	}

	public void setTypeOfArray(Type typeOfArray) {
		this.typeOfArray = typeOfArray;
	}
	
	public String toString() {
		String retVal = "aliasName: " + aliasName;
		retVal += ", type: " + type;
		retVal += ", isArray: " + isArray;
		retVal += ", isTwoDimensionalArray: " + isTwoDimensionalArray;
		retVal += ", typeOfArray: " + typeOfArray;
		retVal += ", dim1: " + dim1;
		retVal += ", dim2: " + dim2;
		
		return retVal;
	}

	@Override
	public String getTypeName() {
		return aliasName;
	}
}
