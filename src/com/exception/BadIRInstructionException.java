package com.exception;


@SuppressWarnings("serial")
@DefaultErrorMessage("Cannot understand bad IR instruction")
public class BadIRInstructionException extends RuntimeException {
	
	public BadIRInstructionException(String msg) {
		super(msg);
	}
	
	public BadIRInstructionException() {
		super();
	}
}
