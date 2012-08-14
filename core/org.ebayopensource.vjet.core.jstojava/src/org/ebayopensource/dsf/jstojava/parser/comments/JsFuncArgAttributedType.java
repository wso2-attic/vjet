/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jst.meta.Token;

/**
 * Representing an attributed type from function argument at invocation time.
 */
public class JsFuncArgAttributedType extends JsTypingMeta {

	private final int m_argIndex;
	
	public JsFuncArgAttributedType(Token typingToken) {
		super(typingToken);
		String name = typingToken.image;
		m_argIndex = Integer.parseInt(name.substring(1));
	}
	
	public int getArgIndex() {
		return m_argIndex;
	}
	
	@Override
	public String getType() {
		return null;
	}

}
