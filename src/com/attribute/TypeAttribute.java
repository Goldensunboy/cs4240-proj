package com.attribute;

import com.compiler.Type;


public class TypeAttribute implements Attribute{
	private boolean isTwoDimensionalArray = true;
	private boolean isArray = false;
	private int dim1size, dim2size;
	private String aliasName;
	private Type type;
	
	public TypeAttribute(Type type, String aliasName) {
		this.type = type;
		this.aliasName =aliasName;
	}
	
	public TypeAttribute(Type type, String aliasName, int dim1Size, int dim2Size) {
		this.type = type;
		this.dim1size = dim1Size;
		this.dim2size = dim2Size;
		this.aliasName = aliasName;
		isArray = true;
	}
	
	public TypeAttribute(Type type, String aliasName, int dim1Size) {
		this(type, aliasName, dim1Size, 0);
		isTwoDimensionalArray = false;
	}
	
	
	public boolean isTwoDimensionalArray() {
		return isArray && isTwoDimensionalArray;
	}

	@Override
	public Type getType() {
		return type;
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

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}
