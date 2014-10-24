package com.attribute;

public class TypeAttribute {
	public enum Datatype {
		Int,
		FixPt
	};
	private boolean isArray;
	private int arrDimensions;
	int dim1size, dim2size;
	private String alias;
}
