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

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssRuleList;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.stylesheets.IMediaList;
import org.ebayopensource.dsf.dom.stylesheets.IStyleSheet;

/**
 * @see org.w3c.dom.css.CSSStyleSheet
 */
public class DCssStyleSheet 
	implements ICssStyleSheet, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private boolean m_disabled = false;
    private DNode m_ownerNode = null;
    private ICssStyleSheet m_parentStyleSheet = null;
    private String m_href = null;
    private String m_title = null;
    private IMediaList m_media = null;
    private ICssRule m_ownerRule = null;
    private boolean m_readOnly = false;
    private ICssRuleList m_rules = null;

    public DCssStyleSheet() {
		m_rules = new DCssRuleList();
    }

    public String getType() {
        return "text/css";
    }

    public boolean getDisabled() {
        return m_disabled;
    }

    /**
     * We will need to respond more fully if a stylesheet is disabled, probably
     * by generating an event for the main application.
     */
    public void setDisabled(boolean disabled) {
        m_disabled = disabled;
    }

    public DNode getOwnerNode() {
        return m_ownerNode;
    }

    public IStyleSheet getParentStyleSheet() {
        return m_parentStyleSheet;
    }

    public String getHref() {
        return m_href;
    }

    public String getTitle() {
        return m_title;
    }

    public IMediaList getMedia() {
        return m_media;
    }

    public ICssRule getOwnerRule() {
        return m_ownerRule;
    }

    public ICssRuleList getCssRules() {
        return m_rules;
    }

    public int insertRule(String rule, int index) throws DOMException {
        if (m_readOnly) {
            throw new DCssException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DCssException.READ_ONLY_STYLE_SHEET);
        }

        try {
        	if(rule==null) {
        		throw new DsfRuntimeException("Error: rule is null");
        	}  
            InputSource is = new InputSource(new StringReader(rule));
            DCssBuilder parser = new DCssBuilder();
            parser.setParentStyleSheet(this);
            ICssRule r = parser.parseRule(is);

            if (getCssRules().getLength() > 0) {

                // We need to check that this type of rule can legally go into
                // the requested position.
                int msg = -1;
                if (r.getType() == ICssRule.CHARSET_RULE) {

                    // Index must be 0, and there can be only one charset rule
                    if (index != 0) {
                        msg = DCssException.CHARSET_NOT_FIRST;
                    } else if (getCssRules().item(0).getType()
                            == ICssRule.CHARSET_RULE) {
                        msg = DCssException.CHARSET_NOT_UNIQUE;
                    }
                } else if (r.getType() == ICssRule.IMPORT_RULE) {

                    // Import rules must preceed all other rules (except
                    // charset rules)
                    if (index <= getCssRules().getLength()) {
                        for (int i = 0; i < index; i++) {
                            int rt = getCssRules().item(i).getType();
                            if ((rt != ICssRule.CHARSET_RULE)
                                    || (rt != ICssRule.IMPORT_RULE)) {
                                msg = DCssException.IMPORT_NOT_FIRST;
                                break;
                            }
                        }
                    }
                }

                if (msg > -1) {
                    throw new DCssException(
                        DOMException.HIERARCHY_REQUEST_ERR,
                        msg);
                }
            }

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
        if (m_readOnly) {
            throw new DCssException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DCssException.READ_ONLY_STYLE_SHEET);
        }

        try {
            ((DCssRuleList)getCssRules()).delete(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DCssException(
                DOMException.INDEX_SIZE_ERR,
                DCssException.ARRAY_OUT_OF_BOUNDS,
                e.getMessage());
        }
    }

    public boolean isReadOnly() {
        return m_readOnly;
    }

    public ICssStyleSheet setReadOnly(boolean b) {
        m_readOnly = b;
        return this ;
    }

    public void setOwnerNode(DNode ownerNode) {
        m_ownerNode = ownerNode;
    }

    public void setParentStyleSheet(IStyleSheet parentStyleSheet) {
        m_parentStyleSheet = (ICssStyleSheet)parentStyleSheet;
    }

    public void setHref(String href) {
        m_href = href;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public void setMedia(String mediaText) {
        // MediaList _media = null;
    }

    public void setOwnerRule(ICssRule ownerRule) {
        m_ownerRule = ownerRule;
    }
    
    public void setRuleList(ICssRuleList rules) {
        m_rules = rules;
    }
    
    public String toString() {
        return m_rules.toString();
    }
    
	public Object clone() throws CloneNotSupportedException {
		DCssStyleSheet copy = (DCssStyleSheet)super.clone();
		copy.m_rules = (DCssRuleList) ((DCssRuleList)copy.m_rules).clone();
		return copy;
	}
}
