package com.exception;

@SuppressWarnings("serial")
@DefaultErrorMessage("Name is already in the name space")
public class NameSpaceConflictException extends RuntimeException{
	
	
	public NameSpaceConflictException(String msg) {
		super(msg);
	}
	
	public NameSpaceConflictException() {
		super();
	}
}
