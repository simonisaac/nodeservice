/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   14 Jul 201111:36:11
 * Workfile::  CommonUtils.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.transform.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author jkochhar
 * 
 */
public class CommonUtils {

	public static String convertStreamToString(InputStream is)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString();
	}

}
