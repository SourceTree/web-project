/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * BaseDTO.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sourcetree.enums.OutcomeStatus;
import org.sourcetree.support.marshalling.JaxbDateSerializer;
import org.sourcetree.support.marshalling.JsonDateSerializer;

/**
 * Contains the base level properties like status, errors, createdDate and
 * modifiedDate
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class BaseDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private OutcomeStatus status = OutcomeStatus.SUCCESS;
	private Map<String, String> errors;
	private Date createdDate;
	private Date modifiedDate;

	/**
	 * @return returns the created date
	 */
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatedDate()
	{
		return createdDate != null ? new Date(createdDate.getTime()) : null;
	}

	/**
	 * @param createdDate
	 *            the created date to set
	 */
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate != null ? new Date(createdDate.getTime())
				: null;
	}

	/**
	 * @return returns the modified date
	 */
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getModifiedDate()
	{
		return modifiedDate != null ? new Date(modifiedDate.getTime()) : null;
	}

	/**
	 * @param modifiedDate
	 *            the modified date to set
	 */
	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate != null ? new Date(
				modifiedDate.getTime()) : null;
	}

	/**
	 * @return returns the status
	 */
	public OutcomeStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(OutcomeStatus status)
	{
		this.status = status;
	}

	/**
	 * @return returns the errors
	 */
	public Map<String, String> getErrors()
	{
		return errors;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(Map<String, String> errors)
	{
		this.errors = errors;
	}
}
