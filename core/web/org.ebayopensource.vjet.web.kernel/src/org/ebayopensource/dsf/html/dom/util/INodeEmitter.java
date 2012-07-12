/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.w3c.dom.Node;

/**
 * Interface to generate all handler events for the passed in node. This is
 * useful when processing your own children. 
 */
//Note, too many classes are implements this interface.
//not worth to re-package it.
public interface INodeEmitter {
	/**
	 * Defines handler to generate events for the passed in node. 
	 *   
	 * @param node
	 *        the current node to be handled
	 * @param writer
	 *        the writer object that specifies how to output the document
	 */
	void genEvents(final Node node, final IXmlStreamWriter writer);
}
