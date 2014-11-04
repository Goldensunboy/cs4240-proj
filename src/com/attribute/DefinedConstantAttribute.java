package com.attribute;

import com.compiler.Type;


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

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
