/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * NotEmptyPropertyValidator.java
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
import org.apache.commons.lang3.StringUtils;

/**
 * Validator to check that given property is not empty.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class NotEmptyPropertyValidator implements
		ConstraintValidator<NotEmptyProperty, Object>
{
	private String property;
	private String node;
	private boolean objectCanbeNull;

	/**
	 * Initialize the validator in preparation for isValid calls. The constraint
	 * annotation for a given constraint declaration is passed. This method is
	 * guaranteed to be called before any use of this instance for validation.
	 * 
	 * @param constraintAnnotation
	 *            annotation instance for a given constraint declaration
	 */
	@Override
	public void initialize(NotEmptyProperty constraintAnnotation)
	{
		property = constraintAnnotation.property();
		node = constraintAnnotation.node();
		objectCanbeNull = constraintAnnotation.objectCanbeNull();
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
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		boolean matches = true;

		if (value == null)
		{
			if (objectCanbeNull)
			{
				matches = true;
			}
			else
			{
				matches = false;
			}
		}
		else
		{
			try
			{
				String propValue = (String) PropertyUtils.getProperty(value,
						property);

				matches = !StringUtils.isBlank(propValue);
			}
			catch (IllegalAccessException e)
			{
				matches = false;
			}
			catch (InvocationTargetException e)
			{
				matches = false;
			}
			catch (NoSuchMethodException e)
			{
				matches = false;
			}
		}

		if (!matches)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					context.getDefaultConstraintMessageTemplate())
					.addNode(node).addConstraintViolation();
		}
		return matches;
	}
}
