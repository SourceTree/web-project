/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * WebLocaleChangeInterceptor.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.web.filter;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * LocaleChangeInterceptor helper class
 * 
 * @author Venkaiah Chowdary Koneru
 * @see LocaleChangeInterceptor
 */
public class WebLocaleChangeInterceptor extends LocaleChangeInterceptor
{
	private static final String SESS_LANGUAGE = "sess_language";
	private static final String SESS_LOCALE = "sess_locale";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException
	{
		if (super.preHandle(request, response, handler))

		{
			LocaleResolver localeResolver = RequestContextUtils
					.getLocaleResolver(request);

			Locale locale = localeResolver.resolveLocale(request);

			if (localeResolver != null && locale != null)
			{
				String language = locale.getLanguage();
				String country = locale.getCountry();

				if (!StringUtils.isBlank(country))
				{
					language += "_" + country;
				}

				boolean validLanguage = false;
				if (!validLanguage)
				{
					language = "en_US";
					localeResolver.setLocale(request, response, Locale.US);
					locale = localeResolver.resolveLocale(request);

					LocaleEditor editor = new LocaleEditor();
					editor.setAsText("en");
					localeResolver.setLocale(request, response,
							(Locale) editor.getValue());
				}

				request.getSession().setAttribute(SESS_LANGUAGE, language);
				request.getSession().setAttribute(SESS_LOCALE, locale);
			}
			return true;
		}
		return false;
	}
}
