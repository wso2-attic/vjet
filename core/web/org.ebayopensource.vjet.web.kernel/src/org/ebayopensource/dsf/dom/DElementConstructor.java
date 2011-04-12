/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.ebayopensource.dsf.dom.support.DNamespace;

public abstract class DElementConstructor {

	public DElement domCreateDynamicElement(final String inTagName) {
		return new DElement(inTagName);
	}
	
	public DElement domCreateDynamicElement(
		final String namespaceUri, final String possiblyQualifiedName)
	{
		return new DElement(namespaceUri, possiblyQualifiedName);
	}
	
	public DElement domCreateDynamicElement(
		final DNamespace namespace, final String inTagName)
	{
		return new DElement(namespace, inTagName);
	}
	
	public DElement domCreateDynamicElement(
		final DDocument owner, final String inTagName)
	{
		return new DElement(owner, inTagName);
	}
	public DElement domCreateDynamicElement(
		final DDocument owner, final DNamespace namespace, final String inTagName)
	{
		return new DElement(owner, namespace, inTagName);
	}
	
	protected static void setOwnerDocument(DDocument doc, DNode element) {
		element.setDsfOwnerDocument(doc);
	}
}
