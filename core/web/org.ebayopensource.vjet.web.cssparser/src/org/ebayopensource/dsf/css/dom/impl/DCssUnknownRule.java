/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.Serializable;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.dom.ICssUnknownRule;

/**
 * @see org.w3c.dom.css.CSSUnknownRule
 */
public class DCssUnknownRule
	extends DCssRule
	implements ICssUnknownRule, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String m_text = null;

	public DCssUnknownRule(
		ICssStyleSheet parentStyleSheet,
		ICssRule parentRule,
		String text) {

		super(parentStyleSheet, parentRule);
		m_text = text;
	}

	public short getType() {
		return UNKNOWN_RULE;
	}

	public String getCssText() {
		return m_text;
	}

	public ICssRule setCssText(String cssText) throws DOMException {
		// TODO: Implementation?
		return this ;
	}

	public ICssStyleSheet getParentStyleSheet() {
		return m_parentStyleSheet;
	}

	public ICssRule getParentRule() {
		return m_parentRule;
	}

	public String toString() {
		return getCssText();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
