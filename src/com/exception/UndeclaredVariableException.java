package com.exception;

@SuppressWarnings("serial")
public class UndeclaredVariableException extends RuntimeException {
	public UndeclaredVariableException(String msg) {
		super(msg);
	}
	
	public UndeclaredVariableException() {
		super();
	}
}
