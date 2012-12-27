/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * AnnotationUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sourcetree.support.CommonsUtil;
import org.sourcetree.support.SessionAttributes;

/**
 * @author Venkaiah Chowdary Koneru
 */
public final class AnnotationUtil
{
	private static final Log LOG = LogFactory.getLog(AnnotationUtil.class);

	/**
	 * To set the session attributes for the request
	 * 
	 * @param arguments
	 * @param sessionAttributes
	 * @return array of objects containing method arguments
	 */
	public static Object[] setSessionAttributes(final Object[] arguments,
			final SessionAttributes sessionAttributes)
	{
		if (!CommonsUtil.isEmpty(arguments))
		{
			for (final Object argument : arguments)
			{
				if (argument instanceof SessionAttributes)
				{
					LOG.trace("Found the required session variables arguments");

					if (sessionAttributes != null)
					{
						SessionAttributes methodSessionArgument = (SessionAttributes) argument;
						methodSessionArgument.setEmail(sessionAttributes
								.getEmail());
						methodSessionArgument.setRole(sessionAttributes
								.getRole());
						methodSessionArgument.setUserId(sessionAttributes
								.getUserId());
					}

					LOG.debug("exiting the loop after setting the session variables");

					break;
				}
			}
		}

		return arguments;
	}
}
