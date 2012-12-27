/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * SecurityFilter.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sourcetree.support.CommonsUtil;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * @author Venkaiah Chowdary Koneru
 * 
 */
public class SecurityFilter extends WebContentInterceptor implements Filter
{
	private static final Log LOG = LogFactory.getLog(SecurityFilter.class);

	private FilterConfig filterConfig = null;
	private String errorPage = null;
	private Integer ajaxErrorStatusCode = null;

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * placed into service.
	 * 
	 * The servlet container calls the init method exactly once after
	 * instantiating the filter. The init method must complete successfully
	 * before the filter is asked to do any filtering work.
	 * 
	 * @param filterConfig
	 *            Contains configuration data of this particular filter
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
		this.errorPage = filterConfig.getInitParameter("errorPage");
		this.ajaxErrorStatusCode = Integer.valueOf(filterConfig
				.getInitParameter("ajaxErrorStatusCode"));
	}

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * taken out of service.
	 */
	@Override
	public void destroy()
	{
		LOG.debug("Destroying Security Filter...");
		filterConfig = null;
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
			FilterChain filterChain) throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest)
		{
			boolean authorized = true;

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			// Prepare the request parameter map.
			Map<String, String[]> parameterMap = new HashMap<String, String[]>();

			if (!ServletFileUpload.isMultipartContent(httpServletRequest))
			{
				parameterMap = httpServletRequest.getParameterMap();
			}

			if (!CommonsUtil.isEmpty(parameterMap))
			{
				for (Map.Entry<String, String[]> entry : parameterMap
						.entrySet())
				{
					if (containsSpecial(entry.getValue()))
					{
						LOG.error("request parameter contains special characters: "
								+ entry.getValue()
								+ " for URL "
								+ httpServletRequest.getRequestURL());
						authorized = false;
						break;
					}
				}
			}

			if (authorized)
			{
				filterChain.doFilter(new XssRequestWrapper(
						(HttpServletRequest) request), response);
				return;
			}
			else
			{
				if (isAjaxRequest(httpServletRequest))
				{
					LOG.error("found ajax request with special characters. Throwing Servlet exception so that ajax request will redirect to error page");
					HttpServletResponse servletResponse = (HttpServletResponse) response;
					servletResponse.setStatus(ajaxErrorStatusCode.intValue());
					return;
				}
				else
				{
					filterConfig
							.getServletContext()
							.getRequestDispatcher(errorPage)
							.forward(
									new XssRequestWrapper(
											(HttpServletRequest) request),
									response);

					return;
				}
			}
		}
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean containsSpecial(String... value)
	{
		// contain between a to z small cap --> [a-z]
		// contain between a to z big cap --> [A-Z]
		// contain between 0 to 9 --> [0-9]
		// *[^ --> and NOT (all the following symbol)
		for (String val : value)
		{
			// checking for ==> * $ < > | " ^
			if (!val.matches("(a-z|A-Z|0-9)*[^\\*$<>\"^|]*$"))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isAjaxRequest(HttpServletRequest httpServletRequest)
	{
		final String flag = httpServletRequest.getHeader("X-Requested-With");

		if (flag != null && flag.equalsIgnoreCase("XMLHttpRequest"))
		{
			return true;
		}

		return false;
	}
}
