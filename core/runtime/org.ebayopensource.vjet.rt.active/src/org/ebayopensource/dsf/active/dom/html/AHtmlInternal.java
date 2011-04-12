/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;

/**
 * For framework only. Application should not use it.
 */
public class AHtmlInternal {

	protected static DHtmlDocument getInternalDocument(AHtmlDocument adoc){
		return adoc == null ? null : adoc.getDHtmlDocument();
	}
	
	protected static DNode getInternalNode(ANode node) {
		if (node == null) return null;
		return node.getDNode();
	}
}
