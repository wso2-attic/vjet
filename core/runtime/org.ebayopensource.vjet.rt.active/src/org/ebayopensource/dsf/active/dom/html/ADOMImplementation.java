/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.jsnative.DOMImplementation;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.DocumentType;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ADOMImplementation extends ActiveObject implements DOMImplementation {

	private static final long serialVersionUID = 1L;

	public ADOMImplementation() {
		super();
		populateScriptable(ANodeList.class, BrowserType.IE_6P);
	}

	public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) {
		throw new ADOMException(new DOMException(DOMException.NOT_SUPPORTED_ERR, "createDocument"));
	}

	public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) {
		AHtmlHelper.checkQualifiedName(qualifiedName);
		return new ADocumentType(new DDocumentType(qualifiedName, publicId, systemId));
	}

	public Object getFeature(String feature, String version) {
		throw new ADOMException(new DOMException(DOMException.NOT_SUPPORTED_ERR, "getFeature"));
	}

	public boolean hasFeature(String feature, String version) {
		boolean anyVersion = version == null || version.length() == 0;
		
		if (feature.startsWith("+")) {
	        feature = feature.substring(1);
	    }
		
		if (feature.equalsIgnoreCase("Core")) {
			if (anyVersion || version.equals("1.0")
	             || version.equals("2.0")) {
				return true;
			}
		} else if (feature.equalsIgnoreCase("HTML")) {
			if (anyVersion || version.equals("1.0")
		             || version.equals("2.0")) {
					return true;
			}
		} else if (feature.equalsIgnoreCase("Events")) {
			if (anyVersion || version.equals("2.0")) {
					return true;
			}
		}	
		return false;
	}



}
