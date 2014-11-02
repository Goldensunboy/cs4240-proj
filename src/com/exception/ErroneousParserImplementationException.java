package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("")
public class ErroneousParserImplementationException extends RuntimeException {
	public ErroneousParserImplementationException(String msg) {
		super(msg);
	}
	
	public ErroneousParserImplementationException() {
		super();
	}
}
