/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * FieldMatchValidator.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.validation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Validator to check that 2 properties are matched or not.
 * 
 * @author Venkaiah Chowdary Koneru
 * 
 */
public class FieldMatchValidator implements
		ConstraintValidator<FieldMatch, Object>
{
	private String firstFieldName;
	private String secondFieldName;

	/**
	 * Initialize the validator in preparation for isValid calls. The constraint
	 * annotation for a given constraint declaration is passed. This method is
	 * guaranteed to be called before any use of this instance for validation.
	 * 
	 * @param constraintAnnotation
	 *            annotation instance for a given constraint declaration
	 */
	@Override
	public void initialize(final FieldMatch constraintAnnotation)
	{
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	/**
	 * Implements the validation logic. The state of value must not be altered.
	 * This method can be accessed concurrently.
	 * 
	 * @param value
	 *            object to validate
	 * @param context
	 *            context in which the constraint is evaluated
	 * @return false if <code>value</code> does not pass the constraint
	 */
	@Override
	public boolean isValid(final Object value,
			final ConstraintValidatorContext context)
	{
		boolean matches = true;
		try
		{
			final Object firstObj = PropertyUtils.getProperty(value,
					firstFieldName);
			final Object secondObj = PropertyUtils.getProperty(value,
					secondFieldName);

			matches = firstObj == null && secondObj == null || firstObj != null
					&& firstObj.equals(secondObj);
		}
		catch (NoSuchMethodException nsme)
		{
			matches = false;
		}
		catch (InvocationTargetException ite)
		{
			matches = false;
		}
		catch (IllegalAccessException e)
		{
			matches = false;
		}

		if (!matches)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate())
					.addNode(secondFieldName).addConstraintViolation();
		}
		return matches;
	}
}