/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.fixture;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ebayopensource.vjet.testframework.artifact.ArtifactConstants;
import org.ebayopensource.vjet.testframework.artifact.ArtifactDef;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




/**
 * 
 * @author ddodd
 *
 */
public class FixtureUtils {

	static public FixtureDefManager createFixtureDefManagerFromXml(String fixtureFilename, Object testSource, ISandbox sandbox) {
		
		FixtureDefManager fixtureDefManager = new FixtureDefManager();
		
		try {
			final InputStream fixtureXmlInput = testSource.getClass()
					.getClassLoader().getResourceAsStream(fixtureFilename);

			if (fixtureXmlInput != null) {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();

				Document d = builder.parse(fixtureXmlInput);

				Node root = d.getFirstChild();
				if (root != null && root.hasChildNodes()) {
					NodeList nodeList = root.getChildNodes();
					
					for (int idx=0; idx<nodeList.getLength(); idx++) {
						Node node = nodeList.item(idx);
						String nodeName = node.getNodeName();
						
						if (nodeName.equalsIgnoreCase(FixtureContstants.FIXTURE_ELEMENT_NAME)) {
							fixtureDefManager.addFixtureDef(getFixtureDef(node));
						}
					}
				}
			}
			

			return fixtureDefManager;

		} catch (Exception e) {
			// Just means there is no fixtures.xml file...
			System.out.println("Exception occured:" + e.getMessage() + "what?");
			return fixtureDefManager;
		}
		
		
	}

	
	
	/**
	 * 
	 * @param testCase
	 * @param sandbox
	 * @return
	 */
	static public FixtureDefManager createFixtureDefManagerFromXml(Object testSource, ISandbox sandbox) {
		return createFixtureDefManagerFromXml(FixtureContstants.FIXTURE_FILENAME, testSource, sandbox);
	}


	/**
	 * 
	 * @param fixtureNode
	 * @return
	 */
	static FixtureDef getFixtureDef(Node fixtureNode) {
		
		FixtureDef fixtureDef = new FixtureDef(getNodeMap(fixtureNode.getAttributes()));

		loadArtifactDefs(fixtureNode, fixtureDef);
		
		return fixtureDef;
	}
	
	
	/**
	 * 
	 * @param fixtureDom
	 * @param fixtureDef
	 * @return
	 */
	static private List<ArtifactDef> loadArtifactDefs(Node fixtureDom, FixtureDef fixtureDef) {
		
		List<ArtifactDef> artifactDefs = new ArrayList<ArtifactDef>();
		
		if (fixtureDom.hasChildNodes() == true) {
			NodeList children = fixtureDom.getChildNodes();
			for (int idx=0; idx<children.getLength(); idx++) {
				Node childNode = children.item(idx);
				if (childNode.getNodeName().equalsIgnoreCase(ArtifactConstants.ARTIFACT_ELEMENT_NAME)) {
					NamedNodeMap artifactAttributes = childNode.getAttributes();
					fixtureDef.addArtifactDef(new ArtifactDef(getNodeMap(artifactAttributes)));
				}
			}
		}
		
		return artifactDefs;
	}

	
	/*
	 * 
	 */
	static Map<String, String> getNodeMap(NamedNodeMap artifactAttributes) {

		Map<String, String> attributeMap = new HashMap<String, String>();
		
		if (artifactAttributes != null) {
			int attributeCount = artifactAttributes.getLength();
			for (int idx=0; idx<attributeCount; idx++) {
				Node attributeNode = artifactAttributes.item(idx);
				attributeMap.put(attributeNode.getNodeName(), attributeNode.getNodeValue());
			}
		}
		return attributeMap;
	}
	
	
	
}
