/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * Defines common interface for instrumenting nodes during render.
 *
 */
public interface IInstrumentDElement {
	/**
	 * Allow for starting instrumentation of this node.
	 * @param node DElement
	 */
	void startElement(final DElement node, final IXmlStreamWriter writer);
	
	/**
	 * Allow for ending instrumentation of this node.
	 * @param node DElement
	 */
	void endElement(final DElement node, final IXmlStreamWriter writer);
	
	/*
	 * Allow for instrumenting a self-render node before it renders.
	 */
	void startSelfRender(final DElement node, final IXmlStreamWriter writer);
	
	/*
	 * Allow for instrumenting a self-render node after it renders.
	 */
	void endSelfRender(final DElement node, final IXmlStreamWriter writer);
	
	/**
	 * Allow for instrumenting the node just before it is appended to the DOM.
	 * @param node DElement
	 */
	void appendElement(final Node parent, final Node node);
	
	/**
	 * Allow the instrumenting of attribute value change of this node
	 */
	void setAttributeValue(DElement node, DAttr attr, String value);
	
}
