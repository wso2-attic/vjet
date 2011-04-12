/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;

import org.ebayopensource.dsf.dom.support.DNamespace;


public final class DomUtil {
	private DomUtil() {
		// empty on purpose
	}
	
	public static void fillFrom(DNode node, Node orig) {
		node.setNodeValue(orig.getNodeValue()) ;
		node.setPrefix(orig.getPrefix()) ;
		
		orig.getPrefix();
		orig.getLocalName();
		
		NamedNodeMap map = orig.getAttributes() ;
		final int len = map.getLength() ;
		for(int i = 0; i < len; i++) {
			Attr attr = (Attr)map.item(i) ;
			Attr newAttr = from(attr) ;
			node.getAttributes().setNamedItem(newAttr) ;
		}
	}
	
	public static DAttr from (final Attr orig) {
		DAttr answer = new DAttr(orig.getName(), orig.getValue()) ;
		
		fillFrom(answer, orig) ;
		
		return answer ;
	}
	
	public static DNode from(Node orig) {
		return null ;
	}
	
	public static DNotation from(final Notation orig) {
		final DNotation answer = new DNotation(null, orig.getNodeName()) ;
		
		fillFrom(answer, orig) ;
		
		answer.setPublicId(orig.getPublicId()) ;
		answer.setSystemId(orig.getSystemId()) ;
		
		return answer ;
	}
	
	/**
	 * Answers a new DDocumentType from the information in the passed
	 * in document type.  No association with owning document or originals
	 * children or attributes is copied.
	 */
	public static DDocumentType from (final DocumentType orig) {
		final DDocumentType answer = new DDocumentType(
			orig.getName(),
			orig.getPublicId(),
			orig.getSystemId()) ;
		
		fillFrom(answer, orig) ;
		
		answer.setInternalSubset(orig.getInternalSubset()) ;
		
		// Handle entities
		NamedNodeMap map = orig.getEntities() ;
		int len = map.getLength() ;
		for(int i = 0; i < len; i++) {
			Entity entity = (Entity)map.item(i) ;
			Entity newEntity = from(entity) ;
			answer.getEntities().setNamedItem(newEntity) ;
		}
		
		// Handle notations
		map = orig.getNotations() ;
		len = map.getLength() ;
		for(int i = 0; i < len; i++) {
			Notation notation = (Notation)map.item(i) ;
			Notation newNotation = from(notation) ;
			answer.getNotations().setNamedItem(newNotation) ;
		}
		
		return answer ;		
	}
	
	/**
	 * Answer an
	 * @param orig
	 * @return
	 */
	public static DEntity from (final Entity orig) {
		final DEntity answer = new DEntity(null, orig.getNodeName()) ;
		
		fillFrom(answer, orig) ;
		
		answer.setInputEncoding(orig.getInputEncoding()) ;
		answer.setXmlEncoding(orig.getXmlEncoding()) ;
		answer.setXmlVersion(orig.getXmlVersion()) ;
		
		answer.setPublicId(orig.getPublicId()) ;
		answer.setSystemId(orig.getSystemId()) ;
		
		return answer ;
	}
		
	public static DNamespace getNamespace(
		final String uri, final String possibleQualifiedName)
	{
		final int colonIndex = possibleQualifiedName.indexOf(":") ;
		if (colonIndex == -1) {
			return DNamespace.getNamespace(uri, possibleQualifiedName) ;
		}
		String prefix = possibleQualifiedName.substring(0, colonIndex) ;
		return DNamespace.getNamespace(uri, prefix) ;
	}
	
	public static String getUnqualifedName( final String possibleQualifiedName){
		final int colonIndex = possibleQualifiedName.indexOf(":") ;
		if (colonIndex == -1) {
			return possibleQualifiedName ;
		}
		return possibleQualifiedName.substring(colonIndex + 1) ;
	}
}
