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

import org.ebayopensource.dsf.css.dom.ICounter;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;

/**
 * @see org.w3c.dom.css.Counter
 */
public class DCssCounter implements ICounter, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String m_identifier;
    private String m_listStyle;
    private String m_separator;
    
    /** Creates new CounterImpl */
    public DCssCounter(boolean separatorSpecified, ILexicalUnit lu) {
        ILexicalUnit next = lu;
        m_identifier = next.getStringValue();
        next = next.getNextLexicalUnit();
        if (separatorSpecified && (next != null)) {
            next = next.getNextLexicalUnit();
            m_separator = next.getStringValue();
            next = next.getNextLexicalUnit();
        }
        if (next != null) {
            m_listStyle = next.getStringValue();
            next = next.getNextLexicalUnit();
        }
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public String getListStyle() {
        return m_listStyle;
    }

    public String getSeparator() {
        return m_separator;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        if (m_separator == null) {
            // This is a 'counter()' function
            sb.append("counter(");
        } else {
            // This is a 'counters()' function
            sb.append("counters(");
        }
        sb.append(m_identifier);
        if (m_separator != null) {
            sb.append(", \"").append(m_separator).append("\"");
        }
        if (m_listStyle != null) {
            sb.append(", ").append(m_listStyle);
        }
        sb.append(")");
        return sb.toString();
    }
    
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}