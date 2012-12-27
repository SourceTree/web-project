/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * XssRequestWrapper.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wraps the {@code HttpServletRequestWrapper} in order to sanitized input
 * parameters.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class XssRequestWrapper extends HttpServletRequestWrapper
{
	/**
	 * Constructor that will parse and sanitize all input parameters.
	 * 
	 * @param httpservletrequest
	 *            the HttpServletRequest to wrap
	 */
	public XssRequestWrapper(HttpServletRequest httpservletrequest)
	{
		super(httpservletrequest);
	}

	/**
	 * retrieves parameter values and suppress the special characters for the
	 * given parameter
	 */
	@Override
	public String[] getParameterValues(String parameter)
	{
		String paramValArray[] = super.getParameterValues(parameter);
		if (paramValArray == null)
		{
			return null;
		}

		int i = paramValArray.length;

		String paramValArrayCleaned[] = new String[i];
		for (int j = 0; j < i; j++)
		{
			paramValArrayCleaned[j] = cleanXSS(paramValArray[j]);
		}

		return paramValArrayCleaned;
	}

	/**
	 * retrieves parameter values and suppress the special characters for the
	 * given parameter.
	 * 
	 * @return Return getParameter(String name) on the wrapped request object.
	 */
	@Override
	public String getParameter(String parameter)
	{
		String paramVal = super.getParameter(parameter);
		if (paramVal == null)
		{
			return null;
		}
		else
		{
			return cleanXSS(paramVal);
		}
	}

	/**
	 * retrieves header values and suppress the special characters for the given
	 * parameter
	 */
	@Override
	public String getHeader(String header)
	{
		String paramVal = super.getHeader(header);
		if (paramVal == null)
		{
			return null;
		}
		else
		{
			return cleanXSS(paramVal);
		}
	}

	/**
	 * Suppress the predefined characters
	 * 
	 * @param value
	 * 
	 * @return suppressed string
	 */
	private String cleanXSS(String value)
	{
		String retValue = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		retValue = retValue.replaceAll("\\(", "&#40;").replaceAll("\\)",
				"&#41;");
		retValue = retValue.replaceAll("'", "&#39;");
		retValue = retValue.replaceAll("eval\\((.*)\\)", "");
		retValue = retValue.replaceAll(
				"[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
		retValue = retValue.replaceAll("script", "");

		return retValue;
	}
}
