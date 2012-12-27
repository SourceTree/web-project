/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * WebContextListener.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This performs the initialization and termination (shut-down) work required by
 * the system, such as initializing the logging framework. This class receives
 * notifications about changes to the servlet context of the web application,
 * e.g., when the servlet context is loaded, or is about to be shut down.
 * 
 * @author Venkaiah Chowdary Koneru
 * @see ServletContextListener
 */
public class WebContextListener implements ServletContextListener
{

	private static final Log LOG = LogFactory.getLog(WebContextListener.class);

	private static final long SLEEP_TIME = 1000;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent ctxEvent)
	{
		LOG.info("application shutting down \n######################################");

		logContextInitParams(ctxEvent.getServletContext());

		try
		{
			cleanupDrivers();
		}
		catch (InterruptedException e)
		{
			LOG.error("Exception while cleanup drivers.", e);
		}

		LogFactory.releaseAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent ctxEvent)
	{
		ServletContext ctx = ctxEvent.getServletContext();

		LOG.info("Starting application \n#######################################");

		if (LOG.isDebugEnabled())
		{
			logContextInitParams(ctx);
		}
	}

	/**
	 * Find all SQL Drivers and deregister them
	 * 
	 * @throws InterruptedException
	 */
	private void cleanupDrivers() throws InterruptedException
	{
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements())
		{
			Driver driver = drivers.nextElement();
			try
			{
				DriverManager.deregisterDriver(driver);
				LOG.trace("deregistering jdbc driver: " + driver);
			}
			catch (SQLException e)
			{
				LOG.error("Exception while deregister SQL Driver ", e);
			}
		}

		Thread.sleep(SLEEP_TIME);
	}

	/**
	 * to log the configuration parameters
	 * 
	 * @param ctx
	 *            the servlet context
	 */
	private void logContextInitParams(ServletContext ctx)
	{
		LOG.trace("Logging application context init-parameters:");
		Enumeration<?> e = ctx.getInitParameterNames();

		while (e.hasMoreElements())
		{
			String param = (String) e.nextElement();
			LOG.debug(param + "=" + ctx.getInitParameter(param));
		}
	}
}
