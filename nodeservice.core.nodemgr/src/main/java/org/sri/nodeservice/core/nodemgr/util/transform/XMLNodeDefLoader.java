/*
 * ########################################################################################
 * Copyright (c) Mitsui Sumitomo Insurance (London Management) Ltd  All rights reserved.
 * ########################################################################################
 *
 * Author::   jkochhar
 * Date::   14 Jul 201110:32:11
 * Workfile::  EucNodeDefProvider.java
 *
 * @version $Id$
 *
 * ########################################################################################
 */
package org.sri.nodeservice.core.nodemgr.util.transform;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;

/**
 * @author jkochhar
 * 
 */
public class XMLNodeDefLoader {

	Logger log = Logger.getLogger(XMLNodeDefLoader.class);

	private NodeDefMgr nodeDefMgr;
	private NodeDefTOTransformer trans;

	/**
	 * This method should be called when initialising this service
	 */
	public XMLNodeDefLoader() {
		nodeDefMgr = NodeDefMgr.createDefaultInstance();
		trans = new NodeDefTOTransformer(nodeDefMgr.getTypeDefMgr());
	}

	public void loadNodeDefsFromClasspathResource(String classpath) {
		try {
			Resource resourceLangDef = new ClassPathResource(classpath);
			FileInputStream langDefStream = new FileInputStream(resourceLangDef.getFile());
			this.loadNodeDefinition(langDefStream);
		} catch (Throwable t) {
			throw new RuntimeException("Problem loading NodeDefs from classpath resource", t);
		}
	}

	public void loadNodeDefinition(FileInputStream xmlLanguage) {
		try {
			LanguageDefTO languageDefTO = XmlToLanguageDefTO.unmarshall(xmlLanguage);
			loadNodeDefinition(languageDefTO);
		} catch (Exception e) {
			log.error("Unable to load the language definition ", e);
			try {
				xmlLanguage.close();
			} catch (IOException e1) {
				e = e1;
			}
			throw new RuntimeException(e);
		}
	}

	private void loadNodeDefinition(LanguageDefTO languageDefTO) {
		LanguageLoader loader = new LanguageLoader(trans, nodeDefMgr);
		loader.loadLanguage(languageDefTO);
		log.info("Language succefully loaded with Node Type [" + languageDefTO.getDescription() + "]");
	}

	/*public NodeDef getNodeDefinition(String nodeType) {
		return nodeDefMgr.getNodeDefinition(nodeType);
	}*/

	public NodeDefMgr getNodeDefMgr() {
		return this.nodeDefMgr;
	}

	/*public List<NodeDef> getNodeDefinitions() {
		List<NodeDef> nodeDefs = new ArrayList<NodeDef>();
		Map<String, NodeDef> nodeDefinitionMap = nodeDefMgr.getNodeDefinitionMap();
		Collection<NodeDef> values = nodeDefinitionMap.values();
		for (NodeDef nodeDef : values) {
			Collection<NodeSetDef> childSetCollection = nodeDef.getChildSetCollection();
			if (childSetCollection != null && childSetCollection.size() == 1) {
				nodeDefs.add(nodeDef);
			}
		}
		return nodeDefs;
	}*/
}
