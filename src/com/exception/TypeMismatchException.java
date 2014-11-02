package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Type mismatch")
public class TypeMismatchException extends RuntimeException {
	
	public TypeMismatchException(String msg) {
		super(msg);
	}
	
	public TypeMismatchException() {
		super();
	}
}
