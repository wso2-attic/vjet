/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import org.ebayopensource.dsf.dom.DomUtil;

class DHtmlDOMImplementation implements DOMImplementation {

	//
	// Constructor(s)
	//
	DHtmlDOMImplementation() {
		// empty on purpose
	}
	
	//
	// Satisfy DOMImplementation
	//
	/**
	 * Answers a new DDocument using the passed in DocumentType.  If a DDocumentType
	 * is passed in it is used directly.  If a DocumentType (w3c) is passed in, we
	 * internally create a DDocumentType from it.
	 */
	public Document createDocument(
		final String namespaceURI, 
		final String qualifiedName,
		final DocumentType doctype) throws DOMException
	{
		// Just using for validation
		DomUtil.getNamespace(namespaceURI, qualifiedName) ;
		
		DHtmlDocType usedDocType = null;
		if (doctype != null) {		
			if (doctype instanceof DHtmlDocType) {
				usedDocType = (DHtmlDocType)doctype ;
			}
			else {
				usedDocType = new DHtmlDocType(
					null,
					doctype.getName(),
					doctype.getPublicId(),
					doctype.getSystemId()) ;
			}
		}
		
		DHtmlDocument doc = new DHtmlDocument() ;
		doc.add(usedDocType) ;
		doc.setDOMImplementation(this) ;
		return doc ;
	}

	/**
	 * Creates an empty DocumentType node.
	 */
	public DocumentType createDocumentType(
		final String qualifiedName,
		final String publicId,
		final String systemId) throws DOMException
	{
		DHtmlDocType dt = new DHtmlDocType(null, qualifiedName, publicId, systemId);
		return dt ;
	}

	/**
	 * Default is to answer null
	 */
	public Object getFeature(final String feature, final String version) {
		return null;
	}
	
	/**
	 * Our limited support at this point will only support one Document type,
	 * HTML 5.0.  We define these features as only HTML.  Thus a feature of 
	 * "HTML +Events 2.0 + DOM 2.0" will simply be treated as the feature string 
	 * "HTML".  We are ignoring the version string in this first release
	 */
	public boolean hasFeature(final String feature, final String version) {
		if (feature == null) return false ;
		return feature.toLowerCase().contains("html") ;
	}
}

