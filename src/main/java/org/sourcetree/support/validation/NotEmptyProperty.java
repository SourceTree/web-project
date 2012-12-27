/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * NotEmptyProperty.java
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
 * Validation annotation to validate that given property of the object is not
 * null or blank. Works only for String properties. An array of properties can
 * be supplied.
 * 
 * <p>
 * Example, validate one property
 * 
 * <pre>
 * <code>
 * {@linkplain NotEmptyProperty}(message = "This field is required", property = "locationCode", 
 * objectCanbeNull = true, node="locationCode")
 * </code>
 * </pre>
 * 
 * Example, compare more than 1 pair of fields:
 * 
 * <pre>
 * <code>
 * {@linkplain NotEmptyProperty.List}({{@linkplain NotEmptyProperty} (message = "This field is required", 
 * property = "locationCode", objectCanbeNull = true, node="locationCode), 
 * {@linkplain NotEmptyProperty}(message = "This field is required",  property = "locationName", 
 * objectCanbeNull = true, node="locationName")})
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Venkaiah Chowdary Koneru
 */
@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyPropertyValidator.class)
public @interface NotEmptyProperty
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
	 * validation annotation belongs to the default payload
	 * 
	 * @return payload class array
	 */
	@SuppressWarnings(AppConstants.SUPPRESS_WARNINGS_RAWTYPES)
	Class[] payload() default {};

	/**
	 * @return returns the property
	 */
	String property();

	/**
	 * @return returns the node
	 */
	String node();

	/**
	 * @return returns whether the given object can be null.
	 */
	boolean objectCanbeNull();

	/**
	 * Defines several <code>@NotEmptyProperty</code> annotations on the same
	 * element
	 * 
	 * @see NotEmptyProperty
	 */
	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List
	{
		/**
		 * 
		 * @return returns the list of {@linkplain NotEmptyProperty} criteria
		 */
		NotEmptyProperty[] value();
	}
}
