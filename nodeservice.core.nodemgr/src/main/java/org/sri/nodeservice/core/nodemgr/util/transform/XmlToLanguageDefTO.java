/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   13 Jul 201113:59:32
 * Workfile::  LanguageTOUnmarshaller.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.core.nodemgr.util.transform;

import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;

/**
 * @author jkochhar
 * 
 */
public class XmlToLanguageDefTO {

	public static LanguageDefTO unmarshall(FileInputStream xmlLanguage)
			throws JAXBException {

		JAXBContext jaxbContext;
		LanguageDefTO defTO = null;
		jaxbContext = JAXBContext
				.newInstance("org.sri.nodeservice.core.nodedef.service.model");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<LanguageDefTO> element = (JAXBElement<LanguageDefTO>) unmarshaller
				.unmarshal(xmlLanguage);
		defTO = element.getValue();
		return defTO;
	}

}
