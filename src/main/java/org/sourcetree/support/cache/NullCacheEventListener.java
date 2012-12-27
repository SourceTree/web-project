/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * NullCacheEventListener.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Venkaiah Chowdary Koneru
 * 
 */
public class NullCacheEventListener implements CacheEventListener
{
	/**
	 * Singleton instance for this listener class.
	 */
	static final CacheEventListener INSTANCE = new NullCacheEventListener();

	private static final Log LOG = LogFactory
			.getLog(NullCacheEventListener.class);

	private static final String LOG_STR = "element with key";
	private static final String LOG_OPEN_BRACE = "[";
	private static final String LOG_CLOSE_BRACE = "]";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose()
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyElementEvicted(Ehcache cache, Element element)
	{
		LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
				.append(element.getKey()).append(LOG_CLOSE_BRACE)
				.append(" is evicted from cache ").append(LOG_OPEN_BRACE)
				.append(cache.getName()).append(LOG_CLOSE_BRACE).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyElementExpired(Ehcache cache, Element element)
	{
		LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
				.append(element.getKey()).append(LOG_CLOSE_BRACE)
				.append(" is expired from cache ").append(LOG_OPEN_BRACE)
				.append(cache.getName()).append(LOG_CLOSE_BRACE).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyElementPut(Ehcache cache, Element element)
	{
		LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
				.append(element.getKey()).append(LOG_CLOSE_BRACE)
				.append(" is added into cache ").append(LOG_OPEN_BRACE)
				.append(cache.getName()).append(LOG_CLOSE_BRACE).toString());

		removeIfNull(cache, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyElementRemoved(Ehcache cache, Element element)
	{
		LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
				.append(element.getKey()).append(LOG_CLOSE_BRACE)
				.append(" is removed from cache ").append(LOG_OPEN_BRACE)
				.append(cache.getName()).append(LOG_CLOSE_BRACE).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyElementUpdated(Ehcache cache, Element element)
	{
		LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
				.append(element.getKey()).append(LOG_CLOSE_BRACE)
				.append(" is updated to cache ").append(LOG_OPEN_BRACE)
				.append(cache.getName()).append(LOG_CLOSE_BRACE).toString());
		removeIfNull(cache, element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyRemoveAll(Ehcache cache)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException("Singleton instance");
	}

	private void removeIfNull(final Ehcache cache, final Element element)
	{
		if (element.getObjectValue() == null)
		{

			LOG.debug(new StringBuilder(LOG_STR).append(LOG_OPEN_BRACE)
					.append(element.getKey()).append(LOG_CLOSE_BRACE)
					.append(" is having null referrence in cache ")
					.append(LOG_OPEN_BRACE).append(cache.getName())
					.append(LOG_CLOSE_BRACE).toString());

			LOG.trace("removeing the null key...");
			cache.remove(element.getKey());
			LOG.trace("removed null key from cache...");
		}
	}
}
