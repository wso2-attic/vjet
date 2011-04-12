/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;

public class NodeToDDocument {

	public static DDocument createDocumentContaining(Node node) {
		if (node instanceof DDocument) return (DDocument)node ;
		
		// Document can have Comment and ProcessingInstruction children but we
		// add them to an element
		if (
			// This check MUST come BEFORE Text - since CDATASectoin is a subtype of Text
			   node instanceof CDATASection
			|| node instanceof Text
			|| node instanceof EntityReference
			|| node instanceof Comment 
			|| node instanceof ProcessingInstruction
			|| node instanceof DocumentFragment)
		{
			return newElemAndAppend(node) ;
		}
		
		// everything needs a doc at this point - so create it
		DDocument doc = new DDocument() ;
		
		if (node instanceof DocumentType) {
			doc.appendChild(node) ;
			return doc ;
		}
		
		if (node instanceof Entity) {
			DocumentType doctype = doc.createDocumentType("qname", "pubId", "sysId") ;
			doctype.getEntities().setNamedItem(node) ;
			doc.appendChild(doctype) ;
			return doc ;
		}
		if (node instanceof Notation) {
			DocumentType doctype = doc.createDocumentType("qname", "pubId", "sysId") ;
			doctype.getNotations().setNamedItem(node) ;
			doc.appendChild(doctype) ;
			return doc ;
		}
		
		if (node instanceof Attr) {
			DElement elem = new DElement("v4") ;
			elem.getAttributes().setNamedItem(node) ;
			doc.appendChild(elem) ;
			return doc ;
		}
		
		// Must be some type of element at this point...
		doc.appendChild(node) ; 
		
		return doc ;
	}
	
	private static DDocument newElemAndAppend(Node child) {
		DDocument doc = new DDocument() ;
		DElement root = new DElement("v4") ;
		root.appendChild(child) ;
		doc.appendChild(root)  ;
		return doc ;
	}
}
