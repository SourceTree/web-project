/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * RestricAspect.java
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
import org.aspectj.lang.reflect.MethodSignature;
import org.sourcetree.support.AuthorizationUtil;
import org.sourcetree.support.SessionAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This holds the Authorization checking logic which is triggered by
 * <code>Restrict</code> annotation.
 * 
 * @author Venkaiah Chowdary Koneru
 */
@Component
@Aspect
public class RestrictAspect
{
	private static final Log LOG = LogFactory.getLog(RestrictAspect.class);

	/**
	 * Pointcut for <code>Restrict</code> annotation
	 */
	@Pointcut("@annotation(org.sourcetree.support.annotation.Restrict)")
	public void restrictPointcut()
	{

	}

	/**
	 * advise for the <code>Restrict</code> annotation's pointcut
	 * 
	 * @param joinPoint
	 * @return joinpoint with args
	 * @throws Throwable
	 */
	@Around("restrictPointcut()")
	public Object processRequest(ProceedingJoinPoint joinPoint)
			throws Throwable
	{
		LOG.trace("Restricted Annotation is activated");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		final SessionAttributes sessionAttributes = AuthorizationUtil
				.retrieveSessionAttributes(request);

		Restrict methodAnnotation = ((MethodSignature) joinPoint.getSignature())
				.getMethod().getAnnotation(Restrict.class);

		if (!AuthorizationUtil.isUserInRole(sessionAttributes,
				methodAnnotation.rolesAllowed()))
		{
			AuthorizationUtil.throwUnauthorizedException(true);
		}

		if (methodAnnotation.setSessionAttributes())
		{
			return joinPoint.proceed(AnnotationUtil.setSessionAttributes(
					joinPoint.getArgs(), sessionAttributes));
		}

		LOG.trace("Restricted Annotation completed");
		return joinPoint.proceed();
	}
}
