/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.dom.util.XmlWriterHelper;
import org.ebayopensource.dsf.common.xml.IIndenter;

public class DDocumentBuilderTest {
	
	public static void main(String[] args) {
		DsfVerifierConfig cfg = DsfVerifierConfig.getInstance();
		cfg.setVerifyInstantiation(false) ;
		cfg.setVerifyNaming(false) ;
		cfg.setVerifyRelationship(false) ;
		// configure DOM-implementation we want to use:
//		System.setProperty(
//			"javax.xml.parsers.DocumentBuilderFactory",
//			"org.apache.crimson.jaxp.DocumentBuilderFactoryImpl");

		System.setProperty(
			"javax.xml.parsers.DocumentBuilderFactory",
			"org.ebayopensource.dsf.dom.DDocumentBuilderFactoryImpl") ;

		// now use JAXP interface to instantiate DOM-implementation:
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			// then we have to create document-loader:
			DDocumentBuilder builder = (DDocumentBuilder)factory.newDocumentBuilder();
			String xml = "<page><container><column><TABBEDMENU/></column></container></page>" ;
			StringBufferInputStream sis = new StringBufferInputStream(xml) ;
			DDocument doc = (DDocument)builder.parse(sis) ;
			String xmlAgain = XmlWriterHelper.asString(doc, new IIndenter.Pretty()) ;
			System.out.println(xmlAgain) ;
			DElement menu = (DElement)doc.getElementsByTagName("TABBEDMENU").item(0) ;
			System.out.println(menu) ;
//			System.out.println(XmlWriterHelper.asString(doc, new IIndenter.Pretty()) ;
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}
