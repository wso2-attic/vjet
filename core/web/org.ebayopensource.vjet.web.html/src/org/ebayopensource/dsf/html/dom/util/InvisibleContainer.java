/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DBr;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
/**
 * Base DSF component class. If there is no natural wrapping tag, and you wish
 * to reduce page weight, you can extend from <code>InvisibleContainer</code>.
 * <p>
 * Note that you can't perform certain functions, such as set an id, on this
 * type of wrapper. A component normally extends from some Html tag in order to 
 * return a type that can be embedded in the overall Html DOM tree being
 * constructed. The subgraph it generates is attached to the parent object. If
 * you want no extra parent object in the final html, you can extend from 
 * <code>InvisibleContainer</code>. 
 *
 */
public class InvisibleContainer extends DElement implements ISelfRender {
	private static final long serialVersionUID = -5879111452153585422L;

	public InvisibleContainer() {
		super("invisible");

	}
	public boolean render(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter,
		final INodeEmitter nodeEmitter)
	{
// MrPperf - don't create children unless needed
		if (!hasChildNodes()) return true;
		
		final NodeList kids = getChildNodes();
		for (int i=0; i < kids.getLength(); i++) {
			final Node node = kids.item(i);
			nodeEmitter.genEvents(node, xmlStreamWriter);
		}
		return true;
	}
	
	public InvisibleContainer addBr() {
		final DBr br = new DBr() ;
		add(br) ;
		return this ;
	}
	
//	public InvisibleContainer addTBr() {
//		final TBr br = new TBr();
//		add(br);
//		return this;
//	}
//	
//	public TBr addWithNl(final DNode node) {
//		super.add(node);
//		TBr br = new TBr();
//		super.add(br);
//		return br;
//	}
}
