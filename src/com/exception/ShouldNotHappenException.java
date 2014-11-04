package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("This error should never happen")
public class ShouldNotHappenException extends RuntimeException {
	
	public ShouldNotHappenException(String msg) {
		super(msg);
	}
	
	public ShouldNotHappenException() {
		super();
	}
}
