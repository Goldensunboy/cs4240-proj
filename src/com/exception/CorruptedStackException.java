package com.exception;

/**
 * This exception is thrown when the stack enters an invalid state
 * @author Risa
 *
 */

@SuppressWarnings("serial")
@DefaultErrorMessage("Stack is in an invalid state") 
public class CorruptedStackException extends RuntimeException {
	
	public CorruptedStackException(String msg) {
		super(msg);
	}
	
	public CorruptedStackException() {
		super();
	}
}
