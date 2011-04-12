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

import org.ebayopensource.dsf.css.dom.ICssPrimitiveValue;
import org.ebayopensource.dsf.css.dom.IRect;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;

/**
 * @see org.w3c.dom.css.Rect
 */
public class DCssRect implements IRect, Serializable, Cloneable {
   
	private static final long serialVersionUID = 1L;
	private ICssPrimitiveValue m_left;
    private ICssPrimitiveValue m_top;
    private ICssPrimitiveValue m_right;
    private ICssPrimitiveValue m_bottom;

    /** Creates new RectImpl */
    public DCssRect(ILexicalUnit lu) {
        ILexicalUnit next = lu;
        m_left = new DCssValue(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        m_top = new DCssValue(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        m_right = new DCssValue(next, true);
        next = next.getNextLexicalUnit();
        next = next.getNextLexicalUnit();
        m_bottom = new DCssValue(next, true);
    }
  
    public ICssPrimitiveValue getTop() {
        return m_top;
    }

    public ICssPrimitiveValue getRight() {
        return m_right;
    }

    public ICssPrimitiveValue getBottom() {
        return m_bottom;
    }

    public ICssPrimitiveValue getLeft() {
        return m_left;
    }
    
    public String toString() {
        return new StringBuffer()
            .append("rect(")
            .append(m_left.toString())
            .append(", ")
            .append(m_top.toString())
            .append(", ")
            .append(m_right.toString())
            .append(", ")
            .append(m_bottom.toString())
            .append(")")
            .toString();
    }
    
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}