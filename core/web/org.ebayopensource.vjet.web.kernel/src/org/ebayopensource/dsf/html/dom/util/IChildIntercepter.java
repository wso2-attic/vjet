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
import org.ebayopensource.dsf.dom.DElement;

/**
 * Interface to generate the handler events for passed in node and its children.
 * The parser can ignore to generate the events for the subtree if it is already 
 * generated by the method defined in this interface.
 * 
 */
public interface IChildIntercepter {
	/**
	 * Defines handler to generate the event of the passed node and its children.
	 *  
	 * @param node
	 *        the current node to be handled
	 * @param nodeEmitter
	 *        default node emitter to generate the handler events for the passed
	 *        in node and its children  
	 * @param writer
	 *        a writer object specifies on how to output the document
	 * @return
	 * 		  returns <code>true</code> when it has generated events for the 
	 *        child. Otherwise, return <code>false</code>
	 */
	boolean genEvents(final DElement node, final INodeEmitter nodeEmitter, final IXmlStreamWriter writer);
	
	
	boolean genDebugEvents(final DElement node, final INodeEmitter nodeEmitter, final IXmlStreamWriter writer);
}
