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
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssRuleList;

/**
 * @see org.w3c.dom.css.CSSRuleList
 */
public class DCssRuleList 
	implements ICssRuleList, Serializable, Cloneable {
    
	private static final long serialVersionUID = 1L;
	private List<ICssRule> m_rules = null;

    public DCssRuleList() {
    }

    public int getLength() {
        return (m_rules != null) ? m_rules.size() : 0;
    }

    public ICssRule item(int index) {
        return (m_rules != null) ? m_rules.get(index) : null;
    }

    public void add(ICssRule rule) {
        if (m_rules == null) {
            m_rules = new ArrayList<ICssRule>();
        }
        m_rules.add(rule);
    }
    
    public void insert(ICssRule rule, int index) {
        if (m_rules == null) {
            m_rules = new ArrayList<ICssRule>();
        }
        m_rules.add(index, rule);
    }
    
    public void delete(int index) {
        if (m_rules == null) {
            return;
        }
        m_rules.remove(index);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getLength(); i++ ) {
            sb.append(item(i).toString()).append("\r\n");
        }
        return sb.toString();
    }
    
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException {
		DCssRuleList copy = (DCssRuleList)super.clone();
		copy.m_rules = (List<ICssRule>)((ArrayList)copy.m_rules).clone();
		return copy;
	}
}
