/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * AppConstants.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree;

import java.util.regex.Pattern;

import org.sourcetree.enums.UserRoleEnum;

/**
 * holds all the application wide Constants.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class AppConstants
{
	/**
	 * 
	 */
	public static final String SESS_ROLE = "sess_role";

	/**
	 * 
	 */
	public static final String SESS_EMAIL = "sess_email";

	/**
	 * 
	 */
	public static final String SESS_USERID = "sess_userid";

	/**
	 * 
	 */
	public static final String SESS_VARS = "sess_vars";

	/**
	 * to use in the DAO HQL queries
	 */
	public static final String FROM = "from ";

	/**
	 * to use in <code>@SuppressWarnings</code> annotations.
	 */
	public static final String SUPPRESS_WARNINGS_UNCHECKED = "unchecked";

	/**
	 * to use in <code>@SuppressWarnings</code> annotations.
	 */
	public static final String SUPPRESS_WARNINGS_RAWTYPES = "rawtypes";

	/**
	 * 
	 */
	public static final String ROLE_ADMIN = "ROLE_" + UserRoleEnum.ADMIN;

	/**
	 * 
	 */
	public static final String ROLE_SECONDARY_ADMIN = "ROLE_"
			+ UserRoleEnum.SECONDARY_ADMIN;

	/**
	 * 
	 */
	public static final String ROLE_USER = "ROLE_USER";

	/**
	 * UTF-8
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * maximum file upload size constant
	 */
	public static final long MAX_FILE_UPLOAD_SIZE = 512000;

	/**
	 * alphanumeric with size 3-80 characters validation constant
	 */
	public static final Pattern VALIDATION_PATTERN_ALPHANUMERIC_SIZE_3_TO_80 = Pattern
			.compile("[A-Za-z0-9]{3,80}+$");

	/**
	 * alphanumeric with size 3-100 characters validation constant
	 */
	public static final Pattern VALIDATION_PATTERN_ALPHANUMERIC_SIZE_3_TO_100 = Pattern
			.compile("[A-Za-z0-9]{3,100}+$");

	/**
	 * url validation constant
	 */
	public static final Pattern VALIDATION_URL = Pattern
			.compile("^(https?|ftp|file)://.+$");

	/**
	 * 
	 */
	public static final String EXCEL_SHEET_NAME = "questions";

	/**
	 * Constructor. Private to prevent unnecessary instantiation.
	 */
	private AppConstants()
	{
	}
}
