/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.meta;


public abstract class JsTypingMeta {

	private Token m_typingToken;
	private boolean m_isVariable = false;
	private boolean m_isOptional = false;
	private int m_dimensions = 0;	
	private boolean m_isFinal = false;
	
	public JsTypingMeta() {
	}
	
	public JsTypingMeta(Token typingToken) {
		setTypingToken(typingToken);
	}
	
	public Token getTypingToken() {
		return m_typingToken;
	}
	
	public boolean isVariable() {
		return m_isVariable;
	}

	public void setVariable(boolean isVariable) {
		m_isVariable = isVariable;
	}
	
	public void setDimension(int dim) {
		m_dimensions = dim;
	}
	
	public int getDimension() {
		return m_dimensions;
	}
	
	public void setOptional(boolean isOptional) {
		m_isOptional = isOptional;
	}
	
	public boolean isOptional() {
		return m_isOptional;
	}
	
	public void setFinal(boolean isFinal) {
		m_isFinal = isFinal;
	}
	
	public boolean isFinal() {
		return m_isFinal;
	}
	
	public int getBeginOffset() {
		return getTypingToken().beginOffset;
	}

	public int getEndOffset() {
		return getTypingToken().endOffset;
	}
	
	public int getBeginColumn() {
		return getTypingToken().beginColumn;
	}

	public int getEndColumn() {
		return getTypingToken().endColumn;
	}
	
	public abstract String getType();

	public void setTypingToken(Token m_typingToken) {
		this.m_typingToken = m_typingToken;
	}
}
