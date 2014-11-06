package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Couldn't cast to FunctionNameAttribute")
public class BadDeveloperException extends RuntimeException {
	
	public BadDeveloperException(String msg) {
		super(msg);
	}
	
	public BadDeveloperException() {
		super();
	}
}
