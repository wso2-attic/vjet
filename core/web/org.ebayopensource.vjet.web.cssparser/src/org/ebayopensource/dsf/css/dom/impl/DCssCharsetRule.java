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

import org.ebayopensource.dsf.css.dom.ICssCharsetRule;
import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.sac.InputSource;

/**
 * @see org.w3c.dom.css.CSSStyleSheet
 */

public class DCssCharsetRule 
	implements ICssCharsetRule, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private ICssStyleSheet m_parentStyleSheet = null;
    private ICssRule m_parentRule = null;
    private String m_encoding = null;

    public DCssCharsetRule(
            ICssStyleSheet parentStyleSheet,
            ICssRule parentRule,
            String encoding) {
        m_parentStyleSheet = parentStyleSheet;
        m_parentRule = parentRule;
        m_encoding = encoding;
    }

    public short getType() {
        return CHARSET_RULE;
    }

    public String getCssText() {
        return "@charset \"" + getEncoding() + "\";";
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

            // The rule must be a charset rule
            if (r.getType() == ICssRule.CHARSET_RULE) {
                m_encoding = ((DCssCharsetRule)r).m_encoding;
            } else {
                throw new DCssException(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DCssException.EXPECTING_CHARSET_RULE);
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

    public String getEncoding() {
        return m_encoding;
    }

    public ICssCharsetRule setEncoding(String encoding) throws DOMException {
        m_encoding = encoding;
        return this ;
    }
    
	//
	// Override(s) from Object
	//
	/** 
	 * DO NOT CHANGE THIS.  Unfortunately the def code relies on toString()
	 * for values.  Will need to fix this.
	 */
	@Override
	public String toString() {
		return getCssText() ;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
