package com.compiler;


public enum ReturnType {
  INT("int"),
  FIXPT("fixedpt"),
  VOID("void"),
  OTHER(""),
  INVALID(""); //temporary for testing
  
  private String name;
  ReturnType(String name) {
	  this.name = name;
  }
  
  public String getName() {
	  return name;
  }
}