/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * JsonDateSerializer.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.marshalling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Custom serialization to serialize Java.util.Date, which is not a common JSON
 * type.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class JsonDateSerializer extends JsonSerializer<Date>
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM-dd-yyyy hh:mm:ss a");

	/**
	 * converts the date object to string format
	 * 
	 * @param date
	 * @param gen
	 * @param provider
	 * 
	 * @throws IOException
	 *             in case of errors
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException
	{
		String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);
	}

}
