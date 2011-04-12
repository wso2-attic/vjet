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

import org.ebayopensource.dsf.css.dom.ICssMediaRule;
import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssRuleList;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.dom.stylesheets.IMediaList;

/**
 * @see org.w3c.dom.css.CSSMediaRule
 */
public class DCssMediaRule extends DCssRule
	implements ICssMediaRule, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private IMediaList m_media = null;
    private ICssRuleList m_rules = null;

    public DCssMediaRule(
            ICssStyleSheet parentStyleSheet,
            ICssRule parentRule,
            IMediaList media) {
        super(parentStyleSheet, parentRule);
        m_media = media;
    }

    public short getType() {
        return MEDIA_RULE;
    }

    public String getCssText() {
        StringBuffer sb = new StringBuffer("@media ");
        sb.append(getMedia().toString()).append(" {");
        for (int i = 0; i < getCssRules().getLength(); i++) {
            ICssRule rule = getCssRules().item(i);
            sb.append(rule.getCssText()).append(" ");
        }
        sb.append("}");
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

            // The rule must be a media rule
            if (r.getType() == ICssRule.MEDIA_RULE) {
                m_media = ((DCssMediaRule)r).m_media;
                m_rules = ((DCssMediaRule)r).m_rules;
            } else {
                throw new DCssException(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DCssException.EXPECTING_MEDIA_RULE);
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

    public IMediaList getMedia() {
        return m_media;
    }

    public ICssRuleList getCssRules() {
        return m_rules;
    }

    public int insertRule(String rule, int index) throws DOMException {
        if (m_parentStyleSheet != null && m_parentStyleSheet.isReadOnly()) {
            throw new DCssException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DCssException.READ_ONLY_STYLE_SHEET);
        }

        try {
            InputSource is = new InputSource(new StringReader(rule));
            DCssBuilder parser = new DCssBuilder();
            parser.setParentStyleSheet(m_parentStyleSheet);
            parser.setParentRule(m_parentRule);
            ICssRule r = parser.parseRule(is);

            // Insert the rule into the list of rules
            ((DCssRuleList)getCssRules()).insert(r, index);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DCssException(
                DOMException.INDEX_SIZE_ERR,
                DCssException.ARRAY_OUT_OF_BOUNDS,
                e.getMessage());
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
        return index;
    }

    public void deleteRule(int index) throws DOMException {
        if (m_parentStyleSheet != null && m_parentStyleSheet.isReadOnly()) {
            throw new DCssException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DCssException.READ_ONLY_STYLE_SHEET);
        }
        try {
            ((DCssRuleList)getCssRules()).delete(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DCssException(
                DOMException.INDEX_SIZE_ERR,
                DCssException.ARRAY_OUT_OF_BOUNDS,
                e.getMessage());
        }
    }

    public void setRuleList(ICssRuleList rules) {
        m_rules = rules;
    }
    
    public String toString() {
        return getCssText();
    }
    
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
