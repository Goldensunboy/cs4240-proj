tree grammar TigerTree;

options {
  language = Java;
  tokenVocab = Tiger;
  ASTLabelType = CommonTree;
}

@header {
  package com.antlr.generated;
  
  import java.util.Map;
  import java.util.TreeMap;
}

@members {
  //Initializing Maps to hold functions and variables
  private Map<String, Function> functionMap = new TreeMap<String, Function>();
  private Map<String, Double> variableMap = new TreeMap<String, Double>();
  
  //Adds a functino to the function map: <name, function> 
  private void define(Function function) {
    functionMap.put(function.getName(), function);
  }
  
  // executes get operation on the functionMap. If nameNode doesn't exist throws exception
  private Function getFunction(CommonTree nameNode) {
    String name = nameNode.getText();
    Function function = functionMap.get(name);
    if (function == null) {
      String msg = "The function \"" + name + "\" is not defined.";
      throw new RuntimeException(msg);
    }
    return function;
  }
  
  
}