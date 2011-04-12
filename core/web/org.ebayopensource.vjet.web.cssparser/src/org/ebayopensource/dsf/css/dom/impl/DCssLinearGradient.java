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

import org.ebayopensource.dsf.css.sac.ILexicalUnit;

/**
 * @see http://dev.w3.org/csswg/css3-images/#radial-gradient
 */
// linear-gradient([<bg-position> || <angle>,]? <color-stop>, <color-stop>[, <color-stop>]*);
public class DCssLinearGradient implements /*IRgbaColor,*/ Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	
    private ILexicalUnit m_lexicalUnit ;

    //
    // Constructor(s)
    //
    public DCssLinearGradient(final ILexicalUnit lu) {
    	m_lexicalUnit = lu ;
        
//        next = next.getNextLexicalUnit();	// skip comma
//        next = next.getNextLexicalUnit();	// skip green
//
//        
//        next = next.getNextLexicalUnit();	// skip comma
//        next = next.getNextLexicalUnit();	// skip blue
//        
//        next = next.getNextLexicalUnit();	// skip comma
//        next = next.getNextLexicalUnit();	// we have opacity
//        
//        m_opacity = new DCssValue(next, true);
    }

    protected DCssLinearGradient() {
    	// empty on purpose
    }
    
    //
    // API
    //
//    public ICssPrimitiveValue getOpacity() {
//    	return m_opacity ;
//    }
//    public void setOpacity(final ICssPrimitiveValue opacity) {
//    	m_opacity = opacity ;
//    }
    
    //
    // Override(s) from Object
    //
    @Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public String toString() {
		return m_lexicalUnit.toString();
	}
//    public String toStringx() {
//    	final String r = m_red.toString();
//    	final String g = m_green.toString();
//    	final String b = m_blue.toString();
//    	final String o = m_opacity.toString() ;
//    	
//    	StringBuilder sb = new StringBuilder(20);
//    	
//    	if(isPercentage(r) && isPercentage(g) && isPercentage(b)) {
//    		sb.append("rgba(");
//    		appendPercentage(sb, r);
//    		sb.append(',');
//    		appendPercentage(sb, g);
//    		sb.append(',');
//    		appendPercentage(sb, b);
//    		sb.append(',') ;
//    		sb.append(o) ;
//    		sb.append(')');
//    	} 
//    	else {   
//    		// MrP - there is no hex notation for RGBA value...
//    		sb.append("rgba(");
//    		sb.append(r);
//    		sb.append(',');
//    		sb.append(g);
//    		sb.append(',');
//    		sb.append(b);
//    		sb.append(',') ;
//    		sb.append(o) ;
//    		sb.append(')');   		
//    	}
//
//		return sb.toString();
//    }
}
