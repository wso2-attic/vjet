/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.active.dom.html.ANodeInternal;
import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.XHtmlWriterHelper;
import org.ebayopensource.dsf.common.xml.IIndenter;

public final class ANodeHelper extends ANodeInternal {
	
	public static String getHtmlText(ANode node) {
		return HtmlWriterHelper.asString(ANodeInternal.getInternalNode(node));
	}
	
	public static String getHtmlText(ANode node, IIndenter indenter) {
		return HtmlWriterHelper.asString(ANodeInternal.getInternalNode(node),indenter);
	}
	
	public static String getXHtmlText(ANode node) {
		return XHtmlWriterHelper.asString(ANodeInternal.getInternalNode(node));
	}
	
	public static String getXHtmlText(ANode node, IIndenter indenter) {
		return XHtmlWriterHelper.asString(ANodeInternal.getInternalNode(node),indenter);
	}
	

	
}
