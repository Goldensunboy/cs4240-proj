package com.compiler;


public enum VarType {
  INT("int"),
  FIXPT("fixedpt"),
  OTHER("user_defined"),
  INVALID(""); //temporary for testing
  
  private String name;
  VarType(String name) {
	  this.name = name;
  }
  
  public String getName() {
	  return name;
  }
}