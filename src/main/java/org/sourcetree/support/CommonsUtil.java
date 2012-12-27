/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * CommonsUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support;

import java.util.Collection;
import java.util.Map;

/**
 * Common utility methods.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class CommonsUtil
{
	/**
	 * Constructor. Private to prevent unnecessary instantiation.
	 */
	private CommonsUtil()
	{
	}

	/**
	 * This method returns true if the collection is null or is empty.
	 * 
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty(Collection<?> collection)
	{
		if (collection == null || collection.isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * This method returns true of the map is null or is empty.
	 * 
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty(Map<?, ?> map)
	{
		if (map == null || map.isEmpty())
		{
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is
	 * zero.
	 * 
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty(Object[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}
		return false;
	}
}
