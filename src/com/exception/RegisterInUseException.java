package com.exception;

/**
 * This exception should be thrown when a user attempts to make a register contain a variable
 * when the register already contains a variable. This will help us detect unhandled register 
 * spills.
 * @author Risa
 *
 */
@SuppressWarnings("serial")
@DefaultErrorMessage("Register is currently in use. You must first \"remove\" its contents.") 
public class RegisterInUseException extends RuntimeException {
	
	public RegisterInUseException(String msg) {
		super(msg);
	}
	
	public RegisterInUseException() {
		super();
	}
}
