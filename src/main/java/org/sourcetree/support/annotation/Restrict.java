/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * Restrict.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.annotation;

import org.sourcetree.enums.UserRoleEnum;

/**
 * Use it to check the current user role and authorization.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public @interface Restrict
{

	/**
	 * Allowed Roles
	 * 
	 * @return array of UserRoleEnum values
	 */
	UserRoleEnum[] rolesAllowed();

	/**
	 * set whether to inject current session values into sessionAttributes
	 * argument
	 * 
	 * @return true or false
	 */
	boolean setSessionAttributes() default true;

}
