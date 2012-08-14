/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import org.ebayopensource.dsf.jst.meta.JsType;
import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jst.meta.Token;

public class JsAttributed extends JsTypingMeta {
	
	String m_name;
	boolean m_instance;
	JsType m_attributor;
	
	public JsAttributed(Token t, Token attributedOp, JsType attributor) {
		super(newAttributedToken(t, attributedOp));

		m_name = super.getTypingToken().image;
		m_attributor = attributor;
	}
	
	private static Token newAttributedToken(Token t, Token attributeOp){
		if(t == null){
			t = new Token();
			t.image = "$missing$";
			t.beginOffset = attributeOp.endOffset;
			t.endOffset = t.beginOffset;
		}
		return t;
	}

//	public JsAttributed(Token attributor, Token attributed, boolean isInstance) {
//		this(attributed, (attributor == null) ? null : new JsType(attributor));
//		m_instance = isInstance;
//	}
	
	public JsAttributed(Token attributor, Token attributedOp, Token attributed, boolean isInstance) {
		this(attributed, attributedOp, (attributor == null) ? null : new JsType(attributor));
		m_instance = isInstance;
	}
	
	public String getName() {
		return m_name;
	}
	
	public void setName(String name) {
		m_name = name;
	}
	
	public boolean isInstance() {
		return m_instance;
	}
	
	public JsAttributed setInstance(boolean instance) {
		m_instance = instance;
		return this;
	}
	
	public JsType getAttributor() {
		return m_attributor;
	}
	
	public boolean isAttributedFromGlobal() {
		return (m_attributor == null);
	}

	@Override
	public String getType() {
		return null;
	}
	
	
}
