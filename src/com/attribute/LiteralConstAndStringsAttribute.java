package com.attribute;


public class LiteralConstAndStringsAttribute implements Attribute{

	public enum VariableType{
		INT_LIT, FIXPT_LIT, STRING
	}
	
	private String declaringProcedure;
	private VariableType type;
	private int storageClass;
	private int offsetInStorage;
	private boolean byReference;
	private int valueInt;
	private double valueFixed;
	private String valueString;
	
	public String getDeclaringProcedure() {
		return declaringProcedure;
	}
	public void setDeclaringProcedure(String declaringProcedure) {
		this.declaringProcedure = declaringProcedure;
	}
	public VariableType getType() {
		return type;
	}
	private void setType(VariableType type) {
		this.type = type;
	}
	public int getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(int storageClass) {
		this.storageClass = storageClass;
	}
	public int getOffsetInStorage() {
		return offsetInStorage;
	}
	public void setOffsetInStorage(int offsetInStorage) {
		this.offsetInStorage = offsetInStorage;
	}
	public boolean isByReference() {
		return byReference;
	}
	public void setByReference(boolean byReference) {
		this.byReference = byReference;
	}
	public int getValueInt() {
		if(type != VariableType.INT_LIT)
				throw new IllegalArgumentException();
		return valueInt;
	}
	public void setValue(int value) {
		setType(VariableType.INT_LIT);
		this.valueInt = value;
	}
	public double getValueFixed() {
		if(type != VariableType.FIXPT_LIT)
			throw new IllegalArgumentException();
		return valueFixed;
	}
	public void setValue(double value) {
		setType(VariableType.FIXPT_LIT);
		this.valueFixed = value;
	}
	public String getValueString() {
		if(type != VariableType.STRING)
			throw new IllegalArgumentException();
		return valueString;
	}
	public void setValue(String value) {
		setType(VariableType.STRING);
		this.valueString = value;
	}
	
}
