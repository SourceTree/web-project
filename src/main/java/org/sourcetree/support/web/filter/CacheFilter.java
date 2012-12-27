/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * CacheFilter.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * Caching filter for the static files. refer to <code>
 * &lt;bean id="responseCachingFilter" .../&gt;
 * </code> in spring application context file.
 * 
 * @author Venkaiah Chowdary Koneru
 * @see WebContentInterceptor
 */
public class CacheFilter extends WebContentInterceptor implements Filter
{

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * placed into service.
	 * 
	 * The servlet container calls the init method exactly once after
	 * instantiating the filter. The init method must complete successfully
	 * before the filter is asked to do any filtering work.
	 * 
	 * @param arg0
	 *            Contains configuration data of this particular filter
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{

	}

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * taken out of service.
	 */
	@Override
	public void destroy()
	{

	}

	/**
	 * The doFilter method of the Filter is called by the container each time a
	 * request/response pair is passed through the chain due to a client request
	 * for a resource at the end of the chain.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @param chain
	 *            filter chain
	 * 
	 * @throws IOException
	 *             in case of errors
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		try
		{
			this.preHandle((HttpServletRequest) request,
					(HttpServletResponse) response, chain);
		}
		catch (Exception e)
		{
			throw new ServletException(e);
		}
		chain.doFilter(request, response);
	}
}
