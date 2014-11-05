package com.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation can be added to exception classes to insert a default error message
 * @author saman
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultErrorMessage {

	String value();

}
