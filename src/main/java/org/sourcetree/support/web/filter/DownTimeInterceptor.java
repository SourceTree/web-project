/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * DownTimeInterceptor.java
 * Modification History
 * ***********************************************************
 * Date				Author			Comment
 * Dec 27, 2012			Venkaiah Chowdary Koneru			Created
 * ***********************************************************
 */
package org.sourcetree.support.web.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Use this for redirecting the traffic during downtime
 * 
 * @author Venkaiah Chowdary Koneru
 * @see HandlerInterceptorAdapter
 */
public class DownTimeInterceptor extends HandlerInterceptorAdapter
{
	/**
	 * Intercept the execution of a handler. Called after HandlerMapping
	 * determined an appropriate handler object, but before HandlerAdapter
	 * invokes the handler.
	 * 
	 * With this method, it can redirect traffic to to a specific page.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @param handler
	 *            chosen handler to execute, for type and/or instance
	 *            examination
	 * @throws IOException
	 *             in case of errors
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException
	{
		boolean isDown = false;
		if (isDown)
		{
			response.sendRedirect("");
			return false;
		}

		return true;
	}
}
