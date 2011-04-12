/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DDocumentBuilderFactoryImpl extends DocumentBuilderFactory {	
	//
	// Constructor(s)
	//
	public DDocumentBuilderFactoryImpl() {
		// empty on purpose
	}
	
	//
	// Satisfy Abstract methods from DocumentBuilderFactory
	//
	@Override
	public Object getAttribute(String name) throws IllegalArgumentException {
		// No attributes support for now
		return null;
	}
	@Override
	public void setAttribute(String name, Object value) throws IllegalArgumentException {
		// no attribute support for now
	}

	@Override
	public boolean getFeature(String name) throws ParserConfigurationException {
		// No feature support for now
		return false;
	}
	@Override
	public void setFeature(String name, boolean value) throws ParserConfigurationException {
		// No feature support for now
	}
	
	@Override
	public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {	
		return new DDocumentBuilder() ;
	}
}

