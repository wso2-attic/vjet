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
import org.ebayopensource.dsf.css.dom.IHslColor;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;

/**
 * @see org.w3c.dom.css.HSLColor
 */

public class DCssHslColor 
	extends DCssBaseColor
	implements IHslColor, Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	
	protected ICssPrimitiveValue m_hue ;
    protected ICssPrimitiveValue m_saturation ;
    protected ICssPrimitiveValue m_lightness ;

    //
    // Constructor(s)
    //
    public DCssHslColor(final ILexicalUnit lu) {
        ILexicalUnit next = lu;
        m_hue = new DCssValue(next, true);
        
        next = next.getNextLexicalUnit();	// skip comma
        next = next.getNextLexicalUnit();
        m_saturation = new DCssValue(next, true);
        
        next = next.getNextLexicalUnit();	// skip comma
        next = next.getNextLexicalUnit();
        m_lightness = new DCssValue(next, true);
    }

    protected DCssHslColor() {
    	// empty on purpose
    }
    
    //
    // API
    //
    public ICssPrimitiveValue getHue() {
        return m_hue;
    }
    public void setHue(final ICssPrimitiveValue hue) {
        m_hue = hue;
    }

    public ICssPrimitiveValue getSaturation() {
        return m_saturation;
    }
    public void setGreen(final ICssPrimitiveValue saturation) {
        m_saturation = saturation;
    }

    public ICssPrimitiveValue getLightness() {
        return m_lightness;
    }
    public void setLightness(final ICssPrimitiveValue lightness) {
        m_lightness = lightness;
    }

    //
    // Override(s) from Object
    //
    @Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
    @Override
    public String toString() {
    	final String h = m_hue.toString();
    	final String s = m_saturation.toString();
    	final String l = m_lightness.toString();
    	
    	final StringBuilder sb = new StringBuilder(20);;
		sb.append("hsl(");
    	if (isPercentage(h)) appendPercentage(sb, h); else sb.append(h) ;
    	sb.append(',') ;
    	if (isPercentage(s)) appendPercentage(sb, s); else sb.append(s) ;
    	sb.append(',') ;
    	if (isPercentage(l)) appendPercentage(sb, l); else sb.append(l) ;
    	sb.append(')');

		return sb.toString();
    }
}
