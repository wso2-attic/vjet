/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.ICssStyleRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.parser.DSelectorList;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.css.sac.ISelectorList;

/**
 * @see org.w3c.dom.css.CSSStyleRule
 */
public class DCssStyleRule
	extends DCssRule
	implements ICssStyleRule, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private ISelectorList m_selectors = null;
	private ICssStyleDeclaration m_style = null;

	public DCssStyleRule(
		ICssStyleSheet parentStyleSheet,
		ICssRule parentRule,
		ISelectorList selectors) {

		super(parentStyleSheet, parentRule);
		m_selectors = selectors;
	}

	public short getType() {
		return STYLE_RULE;
	}

	public String getCssText() {
		return getSelectorText() + " " + getStyle().toString();
	}

	public ICssRule setCssText(String cssText) throws DOMException {
		if (m_parentStyleSheet != null && m_parentStyleSheet.isReadOnly()) {
			throw new DCssException(
				DOMException.NO_MODIFICATION_ALLOWED_ERR,
				DCssException.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(cssText));
			DCssBuilder parser = new DCssBuilder();
			ICssRule r = parser.parseRule(is);

			// The rule must be a style rule
			if (r.getType() == ICssRule.STYLE_RULE) {
				m_selectors = ((DCssStyleRule) r).m_selectors;
				m_style = ((DCssStyleRule) r).m_style;
			} else {
				throw new DCssException(
					DOMException.INVALID_MODIFICATION_ERR,
					DCssException.EXPECTING_STYLE_RULE);
			}
		} catch (DCssException e) {
			throw new DCssException(
				DOMException.SYNTAX_ERR,
				DCssException.SYNTAX_ERROR,
				e.getMessage());
		} catch (IOException e) {
			throw new DCssException(
				DOMException.SYNTAX_ERR,
				DCssException.SYNTAX_ERROR,
				e.getMessage());
		}
		return this ;
	}

	public ICssStyleSheet getParentStyleSheet() {
		return m_parentStyleSheet;
	}

	public ICssRule getParentRule() {
		return m_parentRule;
	}

	public String getSelectorText() {
		return m_selectors.toString();
	}

	public ICssStyleRule setSelectorText(String selectorText) throws DOMException {
		if (m_parentStyleSheet != null && m_parentStyleSheet.isReadOnly()) {
			throw new DCssException(
				DOMException.NO_MODIFICATION_ALLOWED_ERR,
				DCssException.READ_ONLY_STYLE_SHEET);
		}

		try {
			InputSource is = new InputSource(new StringReader(selectorText));
			DCssBuilder parser = new DCssBuilder();
			m_selectors = parser.parseSelectors(is);
		} catch (DCssException e) {
			throw new DCssException(
				DOMException.SYNTAX_ERR,
				DCssException.SYNTAX_ERROR,
				e.getMessage());
		} catch (IOException e) {
			throw new DCssException(
				DOMException.SYNTAX_ERR,
				DCssException.SYNTAX_ERROR,
				e.getMessage());
		}
		return this ;
	}

	public ICssStyleDeclaration getStyle() {
		return m_style;
	}

	public ICssStyleRule setStyle(ICssStyleDeclaration style) {
		m_style = style;
		return this ;
	}

	public String toString() {
		return getCssText();
	}

	public ISelectorList getSelectors() {
		return m_selectors;
	}

	public Object clone() throws CloneNotSupportedException {
		DCssStyleRule clone = (DCssStyleRule) super.clone();
		clone.m_style = (ICssStyleDeclaration)
			((DCssStyleDeclaration)m_style).clone();
		clone.m_selectors = (ISelectorList)
			((DSelectorList)m_selectors).clone();
		return clone;
	}
}
