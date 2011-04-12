/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.jsnative.Comment;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AComment extends ACharacterData implements Comment {

	private static final long serialVersionUID = 1L;
	
	protected AComment(AHtmlDocument doc, DComment data) {
		super(doc, data);
		populateScriptable(AComment.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	@Override
	public String getNodeName() {
		return "#comment";
	}

}
