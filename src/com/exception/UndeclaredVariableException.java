package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Use of undeclared variable")
public class UndeclaredVariableException extends RuntimeException {
	
	
	public UndeclaredVariableException(String msg) {
		super(msg);
	}
	
	public UndeclaredVariableException() {
		super();
	}
}
