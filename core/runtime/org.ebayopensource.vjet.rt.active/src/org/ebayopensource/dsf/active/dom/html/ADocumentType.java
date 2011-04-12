/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.jsnative.DocumentType;
import org.ebayopensource.dsf.jsnative.NamedNodeMap;

public class ADocumentType extends ANode implements DocumentType {
	
	private static final long serialVersionUID = 1L;
	
	private NamedNodeMap m_entities = new ANamedNodeMap(Node.ENTITY_NODE);
	private NamedNodeMap m_notations = new ANamedNodeMap(Node.NOTATION_NODE);
	
	protected ADocumentType(DDocumentType node) {
		this(null, node);
	}
	
	protected ADocumentType(AHtmlDocument doc, DDocumentType node) {
		super(doc, node);
		populateScriptable(ADocumentType.class, doc.getBrowserType());
	}

	public NamedNodeMap getEntities() {
		return m_entities;
	}

	public String getInternalSubset() {
		return getDDocumentType().getInternalSubset();
	}

	public String getName() {
		return getDDocumentType().getName();
	}

	public NamedNodeMap getNotations() {
		return m_notations;
	}

	public String getPublicId() {
		return getDDocumentType().getPublicId();
	}

	public String getSystemId() {
		return getDDocumentType().getSystemId();
	}
	
	private DDocumentType getDDocumentType() {
		return (DDocumentType) getDNode();
	}

}
