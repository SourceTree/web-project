/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * JaxbDateSerializer.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.marshalling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Used to serialize Java.util.Date to create a custom serialize method;
 * 
 * @author Venkaiah Chowdary Koneru
 * @see XmlAdapter
 */
public class JaxbDateSerializer extends XmlAdapter<String, Date>
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM-dd-yyyy hh:mm:ss a");

	/**
	 * converts date type to string
	 * 
	 * @param date
	 *            date object to be converted to String
	 */
	@Override
	public String marshal(Date date)
	{
		return dateFormat.format(date);
	}

	/**
	 * converts string value to date object
	 * 
	 * @param date
	 *            string date value to be converted to Date object
	 */
	@Override
	public Date unmarshal(String date) throws ParseException
	{
		return dateFormat.parse(date);
	}
}
