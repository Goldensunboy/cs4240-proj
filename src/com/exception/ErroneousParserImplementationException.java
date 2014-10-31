package com.exception;

@SuppressWarnings("serial")
public class ErroneousParserImplementationException extends RuntimeException {
	public ErroneousParserImplementationException(String msg) {
		super(msg);
	}
	
	public ErroneousParserImplementationException() {
		super();
	}
}
