/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   11 Oct 201112:51:05
 * Workfile::  NSAuthenticationSuccessHandler.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author jkochhar
 *
 */
public class NSAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	
	public static final String BASIC_AUTH_KEY = "nsBasicAuth";
	
	private String defaultTargetUrl = "/";

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		User user = (User)authentication.getPrincipal();
		
		byte[] basicAuthDetails = Base64.encodeBase64((user.getUsername()+":"+user.getPassword()).getBytes());
		request.getSession().setAttribute(BASIC_AUTH_KEY, new String(basicAuthDetails));

		response.sendRedirect(request.getContextPath().concat(defaultTargetUrl));
	}
	
	/**
	 * @param defaultTargetUrl the defaultTargetUrl to set
	 */
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

}
