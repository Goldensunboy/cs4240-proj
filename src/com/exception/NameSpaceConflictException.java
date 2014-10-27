package com.exception;

@SuppressWarnings("serial")
public class NameSpaceConflictException extends RuntimeException{
	public NameSpaceConflictException(String msg) {
		super(msg);
	}
	
	public NameSpaceConflictException() {
		super();
	}
}
