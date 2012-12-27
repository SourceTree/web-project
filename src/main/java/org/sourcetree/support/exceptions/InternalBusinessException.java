/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * InternalBusinessException.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.exceptions;

/**
 * This exception is thrown to indicate problems while processing events data
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class InternalBusinessException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public InternalBusinessException()
	{
		super();
	}

	/**
	 * Constructor to pass in exception message
	 * 
	 * @param message
	 */
	public InternalBusinessException(String message)
	{
		super(message);
	}

	/**
	 * constructor to pass in exception message and throwable
	 * 
	 * @param message
	 * @param cause
	 */
	public InternalBusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
