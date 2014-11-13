package com.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.Token;

import com.antlr.generated.TigerParser;

/**
 * A unifying exception handling
 * Give the handler "lineNumber, customMessage, expected, actual, exceptionClass"
 * and is will generate an exception for you
 * @author saman
 *
 */
public class ExceptionHandler {

	private TigerParser parser;
	
	public ExceptionHandler(TigerParser parser) {
		this.parser = parser;
	}
	public ExceptionHandler() {
	}
	
	public void setTigerParser(TigerParser parser) {
		this.parser = parser;
	}
	
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
	public void handleException(int lineNumber, String customMessage, String expected, 
			String actual, Class<? extends RuntimeException> exceptionClass) {

		Constructor<? extends RuntimeException> constructor = getConstructor(exceptionClass);
		try {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append(lineNumber == -1 ? "" : "Line " + lineNumber + " :: ");			
			errorMessage.append(customMessage == null ? exceptionClass.getAnnotation(DefaultErrorMessage.class).value() : customMessage);
			
			if(expected != null && actual != null) {				
				errorMessage.append(" :: ");
				errorMessage.append(" Expected: \"");
				errorMessage.append(expected);
				errorMessage.append("\" Actual: \"");
				errorMessage.append(actual);
				errorMessage.append("\"");
			}
			RuntimeException exception = constructor.newInstance(errorMessage.toString());
			System.err.println(exception.getClass()+ " :: " + exception.getMessage() + "\n");
			if(parser != null) {
				parser.invalidateIRCode();				
			}
//			exception.printStackTrace();
//			System.out.println();
//			throw constructor.newInstance(errorMessage.toString());
			
		} catch ( SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleException(ParserRuleReturnScope tokenForLineNumber, String customMessage, String expected, 
			String actual, Class<? extends RuntimeException> exceptionClass) {
		handleException(tokenForLineNumber.start, customMessage, expected, actual, exceptionClass);
	}
		
	public void handleException(Token tokenForLineNumber, String customMessage, String expected, 
			String actual, Class<? extends RuntimeException> exceptionClass) {
		handleException(tokenForLineNumber.getLine(), customMessage, expected, actual, exceptionClass);
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
