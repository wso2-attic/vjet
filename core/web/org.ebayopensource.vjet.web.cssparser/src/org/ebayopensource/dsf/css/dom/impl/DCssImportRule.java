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

import org.ebayopensource.dsf.css.dom.ICssImportRule;
import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.dom.stylesheets.IMediaList;

/**
 * @see org.w3c.dom.css.CSSImportRule
 */
public class DCssImportRule
	extends DCssRule
	implements ICssImportRule, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String m_href = null;
	private IMediaList m_media = null;

	public DCssImportRule(
		ICssStyleSheet parentStyleSheet,
		ICssRule parentRule,
		String href,
		IMediaList media) {
			
		super(parentStyleSheet, parentRule);
		m_href = href;
		m_media = media;
	}

	public short getType() {
		return IMPORT_RULE;
	}

	public String getCssText() {
		StringBuffer sb = new StringBuffer();
		sb.append("@import url(").append(getHref()).append(")");
		if (getMedia().getLength() > 0) {
			sb.append(" ").append(getMedia().toString());
		}
		sb.append(";");
		return sb.toString();
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

			// The rule must be an import rule
			if (r.getType() == ICssRule.IMPORT_RULE) {
				m_href = ((DCssImportRule) r).m_href;
				m_media = ((DCssImportRule) r).m_media;
			} else {
				throw new DCssException(
					DOMException.INVALID_MODIFICATION_ERR,
					DCssException.EXPECTING_IMPORT_RULE);
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

	public String getHref() {
		return m_href;
	}

	public IMediaList getMedia() {
		return m_media;
	}

	public ICssStyleSheet getStyleSheet() {
		return null;
	}

	public String toString() {
		return getCssText();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
