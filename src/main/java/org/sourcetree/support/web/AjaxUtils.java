/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * AjaxUtils.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web;

import org.springframework.web.context.request.WebRequest;

/**
 * Utility class to handle Ajax-related features.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class AjaxUtils
{
	/**
	 * Determines if the given request is an AJAX request. A request is an ajax
	 * request if the X-Requested-With header is set and its value is
	 * XMLHttpRequest. Since it's not sure if this header is standard and always
	 * set, a request containing a parameter name starting with ajax is also
	 * considered as an AJAX request.
	 * 
	 * @param webRequest
	 *            the request to test
	 * @return <code>true</code> if the request is an AJAX request,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAjaxRequest(WebRequest webRequest)
	{
		String pageHead = webRequest.getHeader("X-Requested-With");

		if ("XMLHttpRequest".equalsIgnoreCase(pageHead))
		{
			return true;
		}

		// safe check
		boolean ajaxFound = false;
		for (String paramName : webRequest.getParameterMap().keySet())
		{
			if (paramName.startsWith("ajax"))
			{
				ajaxFound = true;
				break;
			}
		}

		return ajaxFound;
	}

	/**
	 * Determines if the given request is an AJAX upload request.
	 * 
	 * @param webRequest
	 *            Spring's web request object
	 * @return <code>true</code> if the request is an ajax upload request,
	 *         <code>false</code> otherwise
	 */
	public static boolean isAjaxUploadRequest(WebRequest webRequest)
	{
		return webRequest.getParameter("ajaxUpload") != null;
	}

	/**
	 * Constructor. Private to prevent unnecessary instantiation.
	 */
	private AjaxUtils()
	{
	}
}
