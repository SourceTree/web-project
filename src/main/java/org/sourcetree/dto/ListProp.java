/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * ListProp.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dto;

import java.io.Serializable;

/**
 * Listing Properties Object.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class ListProp implements Serializable
{
	private static final long serialVersionUID = 1L;

	private long startIndex = -2;
	private long endIndex = -2;
	private long totalRecords = -1;
	private long page;
	private String sortProperty = null;
	private boolean sortAscending = true;
	private int pageSize;

	/**
	 * @return the startIndex
	 */
	public long getStartIndex()
	{
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(long startIndex)
	{
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public long getEndIndex()
	{
		return endIndex;
	}

	/**
	 * @param endIndex
	 *            the endIndex to set
	 */
	public void setEndIndex(long endIndex)
	{
		this.endIndex = endIndex;
	}

	/**
	 * @return the totalRecords
	 */
	public long getTotalRecords()
	{
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(long totalRecords)
	{
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the page
	 */
	public long getPage()
	{
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(long page)
	{
		this.page = page;
	}

	/**
	 * @return the sortProperty
	 */
	public String getSortProperty()
	{
		return sortProperty;
	}

	/**
	 * @param sortProperty
	 *            the sortProperty to set
	 */
	public void setSortProperty(String sortProperty)
	{
		this.sortProperty = ((sortProperty == null || sortProperty.trim()
				.length() == 0) ? null : sortProperty);
	}

	/**
	 * @return the sortAscending
	 */
	public boolean isSortAscending()
	{
		return sortAscending;
	}

	/**
	 * @param sortAscending
	 *            the sortAscending to set
	 */
	public void setSortAscending(boolean sortAscending)
	{
		this.sortAscending = sortAscending;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize()
	{
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	/**
	 * calculates the total pages
	 * 
	 * @return total pages
	 */
	public long getTotalPages()
	{
		if (this.totalRecords % this.pageSize != 0)
		{
			return (long) (Math.floor(this.totalRecords / this.pageSize) + 1);
		}

		return this.totalRecords / this.pageSize;
	}
}
