/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

import java.util.Enumeration;
import java.util.NoSuchElementException;

class JifTokenizer implements Enumeration<String> {
	private String m_data ;
	private int m_currentSemicolonIndex = 0 ;
	private int m_currentStartingPosition = 0;
	private boolean m_hasMoreElements = true ;
	
	public JifTokenizer(final String data) {
		if (data == null) {
			throw new IllegalArgumentException("Token data must not be null") ;
		}
		m_data = data ;
		m_currentSemicolonIndex = nextNonEscapedSemicolonIndex(m_currentStartingPosition) ;
	}
	
	public static void main(String[] args) {
		String s = "offset:100%; style:stop-color:rgb(0,0,255)//;stop-opacity:1" ;
//		s = "offset:100%; style:stop-color:rgb(0,0,255); stop-opacity:1" ;
//		s = " just a way" ;
		
		JifTokenizer toker = new JifTokenizer(s) ;
		while(toker.hasMoreElements()) {
			out(toker.nextElement()) ;
		}
	}
		
    /**
     * Answers if this Enumeration has more elements.
     * 
     * @return true if there are more elements, false otherwise
     * 
     * @see #nextElement
     */
    public boolean hasMoreElements() {
    	return m_hasMoreElements ;
    }

    /**
     * Answers the next element in this Enumeration.
     * 
     * @return the next element in this Enumeration
     * 
     * @exception NoSuchElementException
     *                when there are no more elements
     * 
     * @see #hasMoreElements
     */
    public String nextElement() {
    	if (m_hasMoreElements == false) {
    		throw new IllegalStateException("No more elements present") ;
    	}
    	
    	String answer ;
    	
    	if (m_currentSemicolonIndex == -1) {
    		m_hasMoreElements = false ;
    		answer = m_data.substring(m_currentStartingPosition) ;
    	}
    	else {
    		answer = m_data.substring(m_currentStartingPosition, m_currentSemicolonIndex) ;
    		
    		m_currentStartingPosition = m_currentSemicolonIndex + 1 ;
    		m_currentSemicolonIndex = nextNonEscapedSemicolonIndex(m_currentStartingPosition) ;
    		
    		// We have to handle special case where we have no more semicolons
    		// left AND the remainder of the data is equivently and empty String
    		if (m_currentSemicolonIndex == -1) {
    			if ("".equals(m_data.substring(m_currentStartingPosition).trim())) {
    				m_hasMoreElements = false ;
    			}
    		}
    	}
    	
    	return answer ;
    }
	
	/** RECURSIVE **/
	private int nextNonEscapedSemicolonIndex(final int startingPos) {
		if (startingPos >= m_data.length()) {
			return -1;  // already past end
		}
	
		int si = m_data.indexOf(";", startingPos) ;
		
		if (si == -1) {
			return -1 ; // no semicolon at all, we're done
		}
		
		if ((m_data.length() - startingPos) <= 2) {
			return si ;  // not enough chars to escape semicolon
		}
		
		if (m_data.charAt(si - 1) == '/' && m_data.charAt(si - 2) == '/') {
			// we need to skip and look past current semi position.
			return nextNonEscapedSemicolonIndex(si + 1) ; // *** RECURSION ***
		}
		else {
			return si ;
		}
	}
	
	private static void out(Object o) { System.out.println(o) ; }  //KEEPME
	
//	private void toke(String s, int startingPos) {
//		s = s.trim();
//		int si = nextNonEscapedSemiIndex(s, startingPos) ;
//		if (si == -1) {
//			out(s) ;
//			return ;
//		}
//		String token = s.substring(0, si).trim() ;
//		out(token) ;
//		if (si == (s.length() - 1)) return ; // nothing left to subset
//		toke(s.substring(si + 1), 0) ;
//	}
}
