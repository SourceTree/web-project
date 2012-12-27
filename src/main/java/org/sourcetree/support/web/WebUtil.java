/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * WebUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web;

import org.apache.commons.lang3.StringUtils;
import org.sourcetree.dto.ListProp;
import org.sourcetree.enums.SortOrderEnum;

/**
 * Utility for the web related functionality
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class WebUtil
{
	/**
	 * Constructor. Private to prevent unnecessary instantiation.
	 */
	private WebUtil()
	{
	}

	/**
	 * initiate a new ListProp object
	 * 
	 * @param page
	 *            page number
	 * @param pageSize
	 *            number of records per page.
	 * @param defaultPageSize
	 *            default page setting from properties file. only used when
	 *            pageSize is null/zero
	 * @param sortProperty
	 *            sort property
	 * @param sortOrder
	 *            sorting order ASC- for ascending and DESC - for descending
	 * @return ListProp object
	 */
	public static ListProp initListProp(final String page, int pageSize,
			final String sortProperty, final SortOrderEnum sortOrder)
	{
		ListProp listProp = new ListProp();

		// Current page
		long startIndexLong = 0;
		if (!StringUtils.isEmpty(page) || !page.equalsIgnoreCase("0"))
		{
			listProp.setPage(Long.parseLong(page));
		}
		else
		{
			listProp.setPage(Long.parseLong("1"));
		}

		startIndexLong = (pageSize * listProp.getPage()) - pageSize;
		long endIndexLong = startIndexLong + pageSize - 1;

		listProp.setPageSize(pageSize);
		listProp.setStartIndex(startIndexLong);
		listProp.setEndIndex(endIndexLong);

		// sort property
		if (!StringUtils.isBlank(sortProperty))
		{
			// sort order
			boolean sortAscending = (sortOrder != null && sortOrder
					.equals(SortOrderEnum.DESC)) ? false : true;

			listProp.setSortProperty(sortProperty);
			listProp.setSortAscending(sortAscending);
		}

		return listProp;
	}
}