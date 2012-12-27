/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * FieldMattch.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import org.sourcetree.AppConstants;

/**
 * Validation annotation to validate that 2 fields have the same value. An array
 * of fields and their matching confirmation fields can be supplied.
 * <p>
 * Example, compare 1 pair of fields:
 * 
 * <pre>
 * <code>
 * {@linkplain FieldMatch}(first = "password", second = "confirmPassword", message =
 * "The password fields must match")
 * </code>
 * </pre>
 * 
 * </p>
 * Example, compare more than 1 pair of fields:
 * 
 * <pre>
 * <code>
 * {@linkplain FieldMatch.List}({{@linkplain FieldMatch}(first = "password", second = "confirmPassword",
 * message = "The password fields must match"), {@linkplain FieldMatch}(first = "email",
 * second = "confirmEmail", message = "The email fields must match")})
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Venkaiah Chowdary Koneru
 * 
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch
{
	/**
	 * message for the violation
	 * 
	 * @return the message
	 */
	String message() default "";

	/**
	 * validation annotation belongs to the default group
	 * 
	 * @return group class array
	 */
	@SuppressWarnings(AppConstants.SUPPRESS_WARNINGS_RAWTYPES)
	Class[] groups() default {};

	/**
	 * Validation annotation belongs to the default payload
	 * 
	 * @return payload class array
	 */
	@SuppressWarnings(AppConstants.SUPPRESS_WARNINGS_RAWTYPES)
	Class[] payload() default {};

	/**
	 * @return The first field
	 */
	String first();

	/**
	 * @return The second field
	 */
	String second();

	/**
	 * Defines several <code>@FieldMatch</code> annotations on the same element
	 * 
	 * @see FieldMatch
	 */
	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List
	{
		/**
		 * 
		 * @return returns the list of {@linkplain FieldMatch} criteria
		 */
		FieldMatch[] value();
	}
}
