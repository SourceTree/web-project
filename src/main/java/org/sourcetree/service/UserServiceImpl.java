/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * UserServiceImpl.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.sourcetree.dao.user.UserDAO;
import org.sourcetree.dto.UserDTO;
import org.sourcetree.entity.User;
import org.sourcetree.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service implementation
 * 
 * @author Venkaiah Chowdary Koneru
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public void create(UserDTO userDTO)
	{
		User user = new User();

		user.setEmail(userDTO.getEmail().trim().toLowerCase());
		user.setPassword(hashPassword(userDTO.getEmail().trim(),
				userDTO.getPassword()));
		user.setRole(UserRoleEnum.ADMIN);
		user.setCreatedDate(new Date());
		user.setName(userDTO.getName());

		userDAO.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean usernameExists(String username)
	{
		return userDAO.existsByParameter("email", username.trim());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(String userName)
	{
		if (!StringUtils.isBlank(userName))
		{
			return userDAO.findByParameter("email", userName.trim());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidPassword(String email, String password,
			String requestPassword)
	{
		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(requestPassword))
		{
			return false;
		}

		if (password.equals(hashPassword(email.trim(), requestPassword)))
		{
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String hashPassword(String salt, String token)
	{
		return shaPasswordEncoder.encodePassword(token, salt);
	}
}
