/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import org.ebayopensource.dsf.dom.DNode;

/**
 * ComponentHandlers work with DsfComponents.  During a Visitor pattern traversal
 * of a DsfComponent graph, an accepted visitor will have the methods contained
 * in this interface called in this order:
 * <li> preProcess(...)
 * <li> process(...)
 * <li> postProcess(...)
 * 
 * The benefit of this multiple method handler becomes apparent when it is needed
 * for simple things like emitting output to a target such as XML.  You really want
 * to have the ability to start a tag, do what you need with your children and
 * then close the tag.
 */
public interface IDNodeHandler {
	void preProcess(DNode component);
	void process(DNode component);
	void postProcess(DNode component);
}
