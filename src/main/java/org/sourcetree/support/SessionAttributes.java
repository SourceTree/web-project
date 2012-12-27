/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * SessionAttributes.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support;

import org.sourcetree.enums.UserRoleEnum;

/**
 * This holds the session related values
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class SessionAttributes
{
	private String email;
	private Long userId;
	private UserRoleEnum role;

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the role
	 */
	public UserRoleEnum getRole()
	{
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(UserRoleEnum role)
	{
		this.role = role;
	}

	/**
	 * Constructor
	 * 
	 * @param email
	 * @param userId
	 * @param role
	 */
	public SessionAttributes(String email, Long userId, UserRoleEnum role)
	{
		this.email = email;
		this.userId = userId;
		this.role = role;
	}

	/**
	 * Default Constructor
	 */
	public SessionAttributes()
	{

	}
}
