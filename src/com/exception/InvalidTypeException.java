package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("")
public class InvalidTypeException extends RuntimeException{
	
	
	public InvalidTypeException(String msg) {
		super(msg);
	}
	
	public InvalidTypeException() {
		super();
	}
}
