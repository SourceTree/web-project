/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * UserDAOHibernate.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dao.user;

import org.sourcetree.dao.GenericDAOHibernate;
import org.sourcetree.entity.User;

/**
 * UserDAO interface implementation
 * 
 * @author Venkaiah Chowdary Koneru
 */
public class UserDAOHibernate extends GenericDAOHibernate<User, Long> implements
		UserDAO
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<User> getEntityClass()
	{
		return User.class;
	}

}
