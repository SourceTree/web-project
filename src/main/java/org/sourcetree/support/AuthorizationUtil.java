/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * AuthorizationUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sourcetree.AppConstants;
import org.sourcetree.enums.UserRoleEnum;
import org.sourcetree.support.exceptions.NoSuchDataException;

/**
 * Utility class for all the Authorization related tasks
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class AuthorizationUtil
{
	/**
	 * Constructor. Private to prevent unnecessary instantiation.
	 */
	private AuthorizationUtil()
	{

	}

	/**
	 * creates a new session for the authorized request.
	 * 
	 * @param user
	 *            user entity object
	 * @param session
	 *            current HTTP session object
	 * @param inactiveTimeOutInMinutes
	 *            session inactive timeout value
	 */
	public static void createNewAuthorizedSession(final String email,
			final Long userId, final UserRoleEnum userRole,
			HttpSession session, Integer inactiveTimeOutInMinutes)
	{
		SessionAttributes sessionAttributes = new SessionAttributes(email,
				userId, userRole);

		session.setAttribute(AppConstants.SESS_VARS, sessionAttributes);

		if (inactiveTimeOutInMinutes.intValue() > 0)
		{
			session.setMaxInactiveInterval(inactiveTimeOutInMinutes.intValue() * 60);
		}
		else
		{
			session.setMaxInactiveInterval(-1);
		}
	}

	/**
	 * removes session variables object from the session and invalidates the
	 * current session.
	 * 
	 * @param session
	 *            current HTTP session object
	 */
	public static void removeAuthorizedSession(HttpSession session)
	{
		session.removeAttribute(AppConstants.SESS_VARS);
		session.invalidate();
	}

	/**
	 * use to check the permission of the session user
	 * 
	 * @param sessionAttributes
	 *            current user HTTP session object
	 * @param userRoleEnums
	 *            role type(s) to be checked
	 */
	public static void authorizeUser(final SessionAttributes sessionAttributes,
			final UserRoleEnum... userRoleEnums)
	{
		boolean userInRole = isUserInRole(sessionAttributes, userRoleEnums);

		if (!userInRole)
		{
			throwUnauthorizedException(true);
		}
	}

	/**
	 * to check whether the request is validated previously
	 * 
	 * @param session
	 *            current HTTP session object
	 * @return returns true if authorized session otherwise false
	 */
	public static boolean isRequestInValidSession(HttpSession session)
	{
		SessionAttributes sessionAttributes = retrieveSessionAttributes(session);

		return isSessionNotEmpty(sessionAttributes);
	}

	/**
	 * to retrieve the SessionVariables object from request object
	 * 
	 * @param request
	 *            current HTTP request object
	 * @return {@linkplain SessionAttributes}
	 */
	public static SessionAttributes retrieveSessionAttributes(
			HttpServletRequest request)
	{
		return retrieveSessionAttributes(request.getSession());
	}

	/**
	 * to retrieve the SessionVariables object from session
	 * 
	 * @param session
	 *            current HTTP session object
	 * @return {@linkplain SessionAttributes}
	 */
	public static SessionAttributes retrieveSessionAttributes(
			HttpSession session)
	{
		final Object sessionAttribute = session != null ? session
				.getAttribute(AppConstants.SESS_VARS) : null;

		return sessionAttribute != null ? (SessionAttributes) sessionAttribute
				: null;
	}

	/**
	 * to check the user is one of the specified roles
	 * 
	 * @param sessionAttributes
	 *            current user HTTP session object
	 * @param userRoleEnums
	 *            role type(s) to be checked
	 * @return returns true if user is one of the passed in role otherwise false
	 */
	public static boolean isUserInRole(
			final SessionAttributes sessionAttributes,
			final UserRoleEnum... userRoleEnums)
	{
		boolean userInRole = false;
		if (sessionAttributes != null && sessionAttributes.getRole() != null)
		{
			for (UserRoleEnum userRole : userRoleEnums)
			{
				if (sessionAttributes.getRole().name()
						.equalsIgnoreCase(userRole.name()))
				{
					userInRole = true;
					break;
				}
			}
		}
		return userInRole;
	}

	/**
	 * Utility method for throwing the unauthorised exception
	 * 
	 * @param isUserRelated
	 *            is related to unauthorised usage ?
	 */
	public static void throwUnauthorizedException(boolean isUserRelated)
	{
		throw new NoSuchDataException("unauthorized", isUserRelated);
	}

	/**
	 * to check if the session is empty or not.
	 * 
	 * @param sessionAttributes
	 * @return true or false
	 */
	public static boolean isSessionNotEmpty(SessionAttributes sessionAttributes)
	{
		return sessionAttributes != null
				&& sessionAttributes.getUserId() != null;
	}
}
