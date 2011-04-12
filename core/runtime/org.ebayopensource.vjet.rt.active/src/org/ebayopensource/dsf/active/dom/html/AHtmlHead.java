/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.html.dom.DHead;
import org.ebayopensource.dsf.html.dom.DTitle;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlHead;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlHead extends AHtmlElement implements HtmlHead {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlHead(AHtmlDocument doc, DHead head) {
		super(doc, head);
		populateScriptable(AHtmlHead.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getProfile() {
		return getDHead().getHtmlProfile();
	}

	public void setProfile(String profile) {
		getDHead().setHtmlProfile(profile);
		onAttrChange(EHtmlAttr.profile, profile);
	}

	public void setTitle(String title) {
		if (title==null) {
			return;
		}
		DTitle tNode = getDTitle();
		if (tNode!=null) {
			tNode.setHtmlText(title);
		} else {
			getDHead().add(new DTitle(title));
		}
	}
	
	
	public String getTitle() {
		DTitle tNode = getDTitle();
		return (tNode==null) ? null : tNode.getHtmlText();
	}

	private DTitle getDTitle() {
		Node node = getDHead().getFirstChild();
		while (node!=null) {
			if (node instanceof DTitle) {
				return (DTitle)node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	private DHead getDHead() {
		return (DHead) getDNode();
	}

}
