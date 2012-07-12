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


/**
 * Interface for components that provides their own rendering. A subtype of a V4
 * DOM Type can control its own rendering and encoding via implementing this 
 * interface. It allows a subtype of a DSF DOM Type to control its own rendering 
 * and encoding. It is mainly useful for HTML optimization, such as large tables
 * where you do not want to produce a DOM for the entire structure.
 */
public interface ISelfRender {
	/**
	 * Defines the custom rendering and specify whether the subtree needs to be 
	 * processed by it.
	 * <p>
	 * If you only want to defer construction of your subtree utill render time 
	 * and do not want to do your own rendering, have your component build the 
	 * nodes as usual and return <code>false</code>, indicating you want the 
	 * usual render to take care of your subtree. If you want to perform your 
	 * own render, return <code>true</code>.
	 * 
	 * @param rawSaxHandler
	 *        a SAX handler defines the callback methods to response correlative
	 *        parsing event
	 * @param xmlStreamWriter
	 *        a object specified on how to output the document
	 * @param nodeEmitter
	 *        a object holds a single method to generate all handler events for 
	 *        the passed in node.
	 * @return
	 *        returns <code>true</code> if you want to take care of your own
	 *        children. Otherwise, returns <code>false</code>. 
	 */
	public boolean render(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter,
		final INodeEmitter nodeEmitter);
}
