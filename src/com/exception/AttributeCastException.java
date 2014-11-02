package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Couldn't cast to FunctionNameAttribute")
public class AttributeCastException extends RuntimeException {
	
	public AttributeCastException(String msg) {
		super(msg);
	}
	
	public AttributeCastException() {
		super();
	}
}
