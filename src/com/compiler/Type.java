package com.compiler;


public enum Type {
  INT("int", true, "%i", "i"),
  FIXPT("fixedpt", true, "%f", "f"),
  VOID("void", false, "%NA", "a"),
  ARRAY("array", true, "%a", "a"), //TODO isValidReturnType == true?
  TEMPORARY("temp", false /*should never be needed*/,"%TEMP", "TEMP"),
  
  INVALID("", false, "%ERR", "ERR"); //temporary for testing
  
  private String name, suffix, noPercentSuffix;
  private boolean isValidVarType;
  Type(String name, boolean isValidVarType, String suffix, String noPercentSuffix) {
	  this.suffix = suffix;
	  this.name = name;
	  this.isValidVarType = isValidVarType; 
	  this.noPercentSuffix = noPercentSuffix;
  }
  
  public String getName() {
	  return name;
  }
  
  public boolean isValidVarType() {
	  return isValidVarType;
  }
  
  public static Type getType(String typeName) {
	  if(typeName.equals(INT.getName())) {
		  return INT;
	  }
	  
	  if(typeName.equals(FIXPT.getName())) {
		  return FIXPT;
	  }
	  
	  if(typeName.equals(ARRAY.getName())) {
		  return ARRAY;
	  }

	  if(typeName.equals(VOID.getName())) {
		  return VOID;
	  }
	  
	  if(typeName.equals(VOID.getName())) {
		  return VOID;
	  }
	  
	  return INVALID;
  }

	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getNoPercentSuffix() {
		return noPercentSuffix;
	}
	
	public void setNoPrefixSuffix(String noPrefixSuffix) {
		this.noPercentSuffix = noPrefixSuffix;
	}
}