/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * InjectSessionAttributeAspect.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support.annotation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sourcetree.support.AuthorizationUtil;
import org.sourcetree.support.SessionAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This holds the session initiation logic for the request which is triggered by
 * <code>InjectSessionAttributes</code> annotation.
 * 
 * @author Venkaiah Chowdary Koneru
 */
@Component
@Aspect
public class InjectSessionAttributeAspect
{
	private static final Log LOG = LogFactory
			.getLog(InjectSessionAttributeAspect.class);

	/**
	 * pointcut for <code>InjectSessionAttributes</code> annotation
	 */
	@Pointcut("@annotation(org.sourcetree.support.annotation.InjectSessionAttributes)")
	public void injectSessionAttributesPointcut()
	{

	}

	/**
	 * advise for the <code>InjectSessionAttributes</code> annotation's pointcut
	 * 
	 * @param joinPoint
	 * @return joinpoint with new arguments
	 * @throws Throwable
	 */
	@Around("injectSessionAttributesPointcut()")
	public Object processInjectRequest(ProceedingJoinPoint joinPoint)
			throws Throwable
	{
		LOG.trace("InjectSessionAttributes annotation is activated");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		final SessionAttributes sessionAttributes = AuthorizationUtil
				.retrieveSessionAttributes(request);

		if (sessionAttributes != null)
		{
			LOG.trace("InjectSessionAttributes annotation completed");
			return joinPoint.proceed(AnnotationUtil.setSessionAttributes(
					joinPoint.getArgs(), sessionAttributes));
		}

		LOG.trace("InjectSessionAttributes annotation is completed");
		return joinPoint.proceed();
	}
}
