package com.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A unifying exception handling
 * Give the handler "lineNumber, customMessage, expected, actual, exceptionClass"
 * and is will generate an exception for you
 * @author saman
 *
 */
public class ExceptionHandler {

	/**
	 * Throws an exception based on the information passed in 
	 * <b>NOTE: if any of these arguments is null, it will nut be a part of the exception
	 * message. 
	 * <b>NOTE: exceptionClass cannot be null 
	 * @param lineNumber
	 * @param customMessage
	 * @param expected
	 * @param actual
	 * @param exceptionClass
	 */
	public void handleException(Integer lineNumber, String customMessage, String expected, 
			String actual, Class<? extends RuntimeException> exceptionClass) {

		Constructor<? extends RuntimeException> constructor = getConstructor(exceptionClass);
		try {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append(lineNumber == null ? "" : "Line " + lineNumber);
			errorMessage.append(" :: ");
			errorMessage.append(customMessage == null ? exceptionClass.getAnnotation(DefaultErrorMessage.class).value() : customMessage);
			errorMessage.append(".");
			
			if(expected != null && actual != null) {				
				errorMessage.append(" :: ");
				errorMessage.append(" Expected: \"");
				errorMessage.append(expected);
				errorMessage.append("\" Actual: \"");
				errorMessage.append(actual);
				errorMessage.append("\"");
			}
			
			throw constructor.newInstance(errorMessage.toString());
			
		} catch ( SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * returns the appropriate constructor belonging to the exception passed in
	 */
	private Constructor<? extends RuntimeException> getConstructor(
			Class<? extends RuntimeException> exceptionClass) {
		try {
			return exceptionClass.getDeclaredConstructor(String.class);

		} catch (NoSuchMethodException | SecurityException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
