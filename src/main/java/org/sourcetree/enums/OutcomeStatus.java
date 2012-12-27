/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * OutcomeStatus.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.enums;

/**
 * Java 5 enumeration of outcome status types. Intended for setting response
 * status code
 * 
 * @author Venkaiah Chowdary Koneru
 */
public enum OutcomeStatus
{
	/**
	 * Success status
	 */
	SUCCESS,

	/**
	 * Error/Failure status
	 */
	FAILURE,

	/**
	 * Exception status
	 */
	EXCEPTION
}
