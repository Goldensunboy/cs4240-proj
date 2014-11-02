package com.exception;

@SuppressWarnings("serial")
public class NameSpaceConflictException extends RuntimeException{
	
	static final String defaultMessage = "Name is already in the name space";
	
	public NameSpaceConflictException(String msg) {
		super(msg);
	}
	
	public NameSpaceConflictException() {
		super();
	}
}
