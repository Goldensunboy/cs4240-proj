package com.exception;

@SuppressWarnings("serial")
public class UndeclaredFunctionException extends RuntimeException {
	public UndeclaredFunctionException(String msg) {
		super(msg);
	}
	
	public UndeclaredFunctionException() {
		super();
	}
}
