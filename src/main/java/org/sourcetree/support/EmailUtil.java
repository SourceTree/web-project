/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * EmailUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Utility class that uses the commons-email library to send email.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public final class EmailUtil
{
	/**
	 * mail.server
	 */
	public static final String EMAIL_HOST = "email.server";

	/**
	 * mail.login.id
	 */
	public static final String EMAIL_ID = "email.login.id";

	/**
	 * mail.login.password
	 */
	public static final String EMAIL_PASSWORD = "email.login.password";

	/**
	 * mail.port
	 */
	public static final String EMAIL_SERVER_PORT = "email.port";

	/**
	 * mail.starttls
	 */
	public static final String EMAIL_STARTTLS = "email.starttls";

	/**
	 * mail.origin
	 */
	public static final String EMAIL_ORIGIN = "email.origin";

	/**
	 * mail.origin.name
	 */
	public static final String EMAIL_ORIGIN_NAME = "email.origin.name";

	/**
	 * mail.signature
	 */
	public static final String EMAIL_SIGNATURE = "email.signature";

	/**
	 * admin.mail.enabled
	 */
	public static final String ADMIN_EMAIL_ENABLED = "admin.email.enabled";

	/**
	 * admin.email
	 */
	public static final String ADMIN_EMAIL = "admin.email";

	/**
	 * admin.email.prefix
	 */
	public static final String ADMIN_EMAIL_PREFIX = "admin.email.prefix";

	private static final String NL = "\n";
	private static final Log LOG = LogFactory.getLog(EmailUtil.class);

	/**
	 * Sends email.
	 * 
	 * @param config
	 * 
	 * @param toEmail
	 *            The destination email address
	 * @param msg
	 *            The text message
	 * @param subject
	 *            The subject of the email
	 * @param toName
	 *            The name of the recipient
	 * @throws EmailException
	 */
	public static void sendEmail(Map<String, String> config, String toEmail,
			StringBuilder msg, String subject, String toName)
			throws EmailException
	{
		if (toEmail == null)
		{
			throw new IllegalArgumentException(
					"Your destination email addresses are missing");
		}

		HtmlEmail email = null;

		String emailHost = config.get(EMAIL_HOST);
		String emailOrigin = config.get(EMAIL_ORIGIN);
		String emailOriginName = config.get(EMAIL_ORIGIN_NAME);
		String emailSignature = config.get(EMAIL_SIGNATURE);
		String emailLoginId = config.get(EMAIL_ID);
		String emailLoginPassword = config.get(EMAIL_PASSWORD);

		try
		{
			email = new HtmlEmail();
			email.setHostName(emailHost);
			email.setSmtpPort(Integer.parseInt(config.get(EMAIL_SERVER_PORT)));

			email.setAuthentication(emailLoginId, emailLoginPassword);
			email.setTLS(Boolean.parseBoolean(config.get(EMAIL_STARTTLS)));

			email.addTo(toEmail, toName);
			email.setFrom(emailOrigin, emailOriginName);
			email.setSubject(subject);

			if (emailSignature != null)
			{
				msg.append(NL).append(emailSignature);
			}

			email.setMsg(msg.toString());

			email.send();

			LOG.debug("Emailing user: " + toName + " about " + subject
					+ " to address: " + toEmail);
		}
		catch (EmailException e)
		{
			LOG.error("Unexpected Exception when trying to notify user: "
					+ toEmail + " about " + subject, e);
		}
	}

	/**
	 * Sends email.
	 * 
	 * @param config
	 * 
	 * @param toEmail
	 *            The destination email address
	 * @param msg
	 *            The text message
	 * @param subject
	 *            The subject of the email
	 * @throws EmailException
	 */
	public static void sendEmail(Map<String, String> config, String toEmail,
			StringBuilder msg, String subject) throws EmailException
	{
		sendEmail(config, toEmail, msg, subject, toEmail);
	}

	/**
	 * Email's the administrator, possibly about the occurrence of an important
	 * error.
	 * 
	 * @param config
	 * 
	 * @param feedback
	 *            a string with a feedback message
	 * @return a boolean flag indicating whether the email was sent successfully
	 *         or not
	 */
	public static boolean emailAdminAboutError(Map<String, String> config,
			String feedback)
	{
		return emailAdminAboutError(config, feedback,
				"There has been an error during execution. Details: ", null);
	}

	/**
	 * Email's the administrator, possibly about the occurrence of an important
	 * error.
	 * 
	 * @param config
	 * 
	 * @param feedback
	 *            a string with a feedback message
	 * @param introduction
	 *            a string with an introductory feedback message
	 * @param e
	 *            The exception involved in the error situation
	 * @return a boolean flag indicating whether the email was sent successfully
	 *         or not
	 */
	public static boolean emailAdminAboutError(Map<String, String> config,
			String feedback, String introduction, Throwable e)
	{
		boolean result = true;

		StringBuilder adminErrMsg = new StringBuilder(introduction)
				.append(feedback);

		if (e != null)
		{
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			adminErrMsg.append(NL).append(writer.toString());
		}

		String emailPrefix = config.get(ADMIN_EMAIL_PREFIX);
		String adminEmail = config.get(ADMIN_EMAIL);

		adminErrMsg
				.append(" Please contact developer to ensure that the error is taken care of.");
		String emailSubject = "Error during program execution: PLEASE READ";

		if (emailPrefix != null)
		{
			emailSubject = (new StringBuilder("[").append(emailPrefix).append(
					"] ").append(emailSubject)).toString();
		}

		if (adminEmail != null)
		{
			try
			{
				EmailUtil.sendEmail(config, adminEmail, adminErrMsg,
						emailSubject, adminEmail);
			}
			catch (EmailException ex)
			{
				LOG.error(
						"EmailException when trying to notify administrator: "
								+ adminEmail + " about a program error ", ex);
				result = false;
			}
		}
		else
		{
			LOG.error("Could not obtain email address to notify administrator: "
					+ adminEmail + " about an error during program execution ");
			result = false;
		}

		return result;
	}
}
