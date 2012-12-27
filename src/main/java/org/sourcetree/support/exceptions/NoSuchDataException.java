/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * NoSuchDataException.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.exceptions;

/**
 * Exception thrown when the controller is invoked with invalid data.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class NoSuchDataException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private final String data;
	private final boolean userRelated;

	/**
	 * Constructor
	 * 
	 * @param data
	 *            error message
	 * @param userRelated
	 *            is user unauthorised ?
	 */
	public NoSuchDataException(String data, boolean userRelated)
	{
		super("Error Message: " + data);
		this.data = data;
		this.userRelated = userRelated;
	}

	/**
	 * Returns the data
	 * 
	 * @return returns the data
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * @return the userRelated
	 */
	public boolean isUserRelated()
	{
		return userRelated;
	}
}
