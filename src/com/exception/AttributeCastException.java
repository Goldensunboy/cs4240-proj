package com.exception;

@SuppressWarnings("serial")
public class AttributeCastException extends RuntimeException {
	public AttributeCastException(String msg) {
		super(msg);
	}
	
	public AttributeCastException() {
		super();
	}
}
