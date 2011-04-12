/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.util.xml.rt;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 */
public class Sax2DefaultEventHandler
	extends DefaultHandler
	implements Sax2EventHandler {

//	private String m_lastCharacters = null;

	public void comment(char[] ch, int start, int length) {
		// Report an XML comment anywhere in the document.
	}
	public void endCDATA() {
		// Report the end of a CDATA section.
	}
	public void endDTD() {
		// Report the end of DTD declarations.
	}
	public void endEntity(java.lang.String name) {
		// Report the end of an entity.
	}
	public void startCDATA() {
		// Report the start of a CDATA section.
	}
	public void startDTD(
		java.lang.String name,
		java.lang.String publicId,
		java.lang.String systemId) {
		// Report the start of DTD declarations, if any.
	}
	public void startEntity(java.lang.String name) {
		// Report the beginning of some internal and external XML entities.
	}

	public void attributeDecl(
		java.lang.String eName,
		java.lang.String aName,
		java.lang.String type,
		java.lang.String valueDefault,
		java.lang.String value) {
		// Report an attribute type declaration.
	}
	public void elementDecl(java.lang.String name, java.lang.String model) {
		// Report an element type declaration.
	}
	public void externalEntityDecl(
		java.lang.String name,
		java.lang.String publicId,
		java.lang.String systemId) {
		// Report a parsed external entity declaration.
	}
	public void internalEntityDecl(
		java.lang.String name,
		java.lang.String value) {
		// Report an internal entity declaration.
	}

	/*	public void characters(char ch[], int offset, int length)
			throws SAXException
		{
			m_lastCharacters = new String(ch, offset, length);
		}
	*/


	//
	// ErrorHandler methods
	//

	/** Warning. */
	public void warning(SAXParseException e) throws SAXException {
		throw e;
	} // warning(SAXParseException)

	/** Error. */
	public void error(SAXParseException e) throws SAXException {
		throw e;
	} // error(SAXParseException)

	/** Fatal error. */
	public void fatalError(SAXParseException e) throws SAXException {
		throw e;
	} // fatalError(SAXParseException)

}
