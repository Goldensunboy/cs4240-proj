package com.attribute;

public class DefinedConstantsAttribute {
	public enum Constants {
		Int, 
		FixPt
	};
	
	private Constants constant;

	public Constants getConstant() {
		return constant;
	}

	public void setConstant(Constants constant) {
		this.constant = constant;
	}
}
