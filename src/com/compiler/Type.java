package com.compiler;


public enum Type {
  INT("int", true),
  FIXPT("fixedpt", true),
  VOID("void", false),
  ARRAY("array", true), //TODO isValidReturnType == true?
  TEMPORARY("temp", false /*should never be needed*/),
  
  INVALID("", false); //temporary for testing
  
  private String name;
  private boolean isValidVarType;
  Type(String name, boolean isValidVarType) {
	  this.name = name;
	  this.isValidVarType = isValidVarType; 
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
}