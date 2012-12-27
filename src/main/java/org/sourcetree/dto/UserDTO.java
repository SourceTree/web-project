/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * UserDTO.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.sourcetree.support.validation.SecondGroup;

/**
 * @author Venkaiah Chowdary Koneru
 */
public class UserDTO extends BaseDTO
{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "name.null")
	@Size(min = 3, max = 80, message = "name.length",
			groups = SecondGroup.class)
	private String name;

	@NotEmpty(message = "email.null")
	@Pattern(
			regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
			message = "email.not.valid", groups = SecondGroup.class)
	private String email;

	@NotEmpty(message = "password.null")
	@Size(min = 3, max = 80, message = "password.length",
			groups = SecondGroup.class)
	private String password;

	@NotEmpty(message = "confirmPassword.null")
	private String confirmPassword;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

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
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}
}
