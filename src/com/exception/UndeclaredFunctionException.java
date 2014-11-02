package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Invocation of undeclared function")
public class UndeclaredFunctionException extends RuntimeException {
	
	
	public UndeclaredFunctionException(String msg) {
		super(msg);
	}
	
	public UndeclaredFunctionException() {
		super();
	}
}
