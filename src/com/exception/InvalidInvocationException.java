package com.exception;

@SuppressWarnings("serial")
public class InvalidInvocationException extends RuntimeException{
	public InvalidInvocationException(String msg) {
		super(msg);
	}
	
	public InvalidInvocationException() {
		super();
	}
}
