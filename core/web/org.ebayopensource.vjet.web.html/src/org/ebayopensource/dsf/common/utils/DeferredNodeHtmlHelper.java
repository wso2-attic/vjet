/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.utils;

import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.DefaultDNodeVisitor;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.dom.util.IDeferConstruction;

public class DeferredNodeHtmlHelper{
	/**
	 * Helper method to traverse the tree of DNode.
	 * Calls finish() for child nodes that implement IDeferConstruction.
	 * Return the html content as string for the given component.
	 * It is created for Ajax/JS to retrive html as string before render time.
	 * @param component
	 * @return String
	 */
		
	//fix BUGDB00521566
	public static String finishAndGetHtml(DNode component) {
		if (component == null) {
			return null;
		}
		DeferredNodeVisitor visitor = new DeferredNodeVisitor();	
		component.dsfAccept(visitor);
		visitor.visit(component);
		return HtmlWriterHelper.asString(component);		
	}		
}


class DeferredNodeVisitor extends DefaultDNodeVisitor {	
	public DNodeVisitStatus preVisit(final DNode component)
		throws AbortDNodeTraversalException
	{
		if (component instanceof IDeferConstruction) {
			IDeferConstruction o = (IDeferConstruction)component;
			o.finish();
			DsfCtx.ctx().getDeferConstructionCollector().remove(o);
		}
		return DNodeVisitStatus.CONTINUE ;	
	}
}
