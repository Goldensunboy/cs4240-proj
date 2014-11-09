package com.compiler;


public enum Type {
  INT("int", true, true),
  FIXPT("fixedpt", true, true),
  VOID("void", false, true),
  ARRAY("array", true, true), //TODO isValidReturnType == true?
//  OTHER("user_defined", true, true),
  TEMPORARY("temp", false /*should never be needed*/, false),
  
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
	  
	  return INVALID;
  }
}