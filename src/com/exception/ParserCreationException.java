package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Compiler couldn't generate the praser") 
public class ParserCreationException extends RuntimeException {
	
	public ParserCreationException(String msg) {
		super(msg);
	}
	
	public ParserCreationException() {
		super();
	}
}
