package com.attribute;


public class DefinedConstantAttribute implements Attribute{
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