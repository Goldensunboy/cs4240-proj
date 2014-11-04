package com.compiler;


public enum Type {
  INT("int", true, true),
  FIXPT("fixedpt", true, true),
  VOID("void", false, true),
  ARRAY("array", true, true), //TODO isValidReturnType == true?
  OTHER("user_defined", true, true),
  INVALID("", false, false); //temporary for testing
  
  private String name;
  private boolean isValidVarType, isValidReturnType;
  Type(String name, boolean isValidVarType, boolean isValidReturnType) {
	  this.name = name;
	  this.isValidReturnType = isValidReturnType;
	  this.isValidVarType = isValidVarType; 
  }
  
  public String getName() {
	  return name;
  }
  
  public boolean isValidReturnType() {
	  return isValidReturnType;
  }
  
  public boolean isValidVarType() {
	  return isValidVarType;
  }
}