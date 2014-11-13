package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Too many errors, couldn't recover\n")
public class UnrecoverableException extends RuntimeException {
	
	public UnrecoverableException(String msg) {
		super(msg);
	}
	
	public UnrecoverableException() {
		super();
	}
}
