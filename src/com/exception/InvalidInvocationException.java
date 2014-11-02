package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Invalid invocation")
public class InvalidInvocationException extends RuntimeException{	
	
	public InvalidInvocationException(String msg) {
		super(msg);
	}
	
	public InvalidInvocationException() {
		super();
	}
}
