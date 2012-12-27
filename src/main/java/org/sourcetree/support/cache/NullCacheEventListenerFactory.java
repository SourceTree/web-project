/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * NullCacheEventListenerFactory.java
 * Modification History
 * ***********************************************************
 * Date				Author			Comment
 * Dec 27, 2012			Venkaiah Chowdary Koneru			Created
 * ***********************************************************
 */
package org.sourcetree.support.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * EhCache event listener factory for preventing caching of null values in cache
 * data store.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class NullCacheEventListenerFactory extends CacheEventListenerFactory
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CacheEventListener createCacheEventListener(
			final Properties properties)
	{
		return NullCacheEventListener.INSTANCE;
	}
}
