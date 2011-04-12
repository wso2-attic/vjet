/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 *
 * Used as the ID management repository for:
 * 
 * a. As a style sheet selector. 
 * Must follow limits of characters that are used 
 * #idattribute {style...}
 * 
 * b. As a target anchor for hypertext links.
 * <A href="#section2">Section Two</A>.
 *       ….. Some content ……
 * <span id="section2"> text</span>
 * c. As a means to reference a particular element from a script. 
 * document.getElementById("myid").innerHTML("insert this text");
 * document.getElementById("myid").onclick=myeventhandler;
 * 
 * portability is needed to handle old and new ways of access dom elements by id. 
 * Modern browser do it differently as well. Thanks to the browser wars.
 * 
 * Example Ids are
 * normal mode = scopename-child
 */
package org.ebayopensource.dsf.common.naming;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ebayopensource.dsf.common.DsfVerifierConfig;

public final class DElementId implements IDsfId {
	private static final long serialVersionUID = 1L;

	// Pattern is threadsafe
	private static final Pattern s_pattern = Pattern.compile("[^\\w]*");
	
	private String m_shortName = "";
	private String m_longName = "";
	private Mode m_idMode = SINGLE;
	private int m_counter ;
	
	public DElementId(final String name) {
		assertStandardId(name);
		setShortName(name);
		setLongName(name);
	}

	public DElementId(
		final String longName, final String shortName,final Mode mode)
	{
		assertStandardId(longName);
		assertStandardId(shortName);
		setShortName(shortName);
		setLongName(longName);
		m_idMode = mode;
		if(m_idMode.equals(MULTIPLE)) {
			m_counter = 0;
		}	
	}

	public DElementId(final String name, final Mode mode) {
		assertStandardId(name);
		m_shortName = name;
		m_longName = name;
		m_idMode = mode;
		
		if(m_idMode.equals(MULTIPLE)) {
			m_counter = 0;
		}
	}

	public void assertStandardId(final String s) {
		if (!DsfVerifierConfig.getInstance().isVerifyNaming()) {
			return;
		}
		
		if(s.indexOf("\\")!=-1) {
			chuckInvalidNameException("Not a valid id - " + s 
				+ "\n dom id attributes can not contain backslashes. ");
		}
		
		if(s.indexOf("_")!=-1) {
			chuckInvalidNameException("Not a valid id - " + s 
				+	" dom id attributes can not have underscore values. ");
		}
		
		if(s.indexOf("-")!=-1) {
			chuckInvalidNameException("Not a valid id - " + s +
				" dom id attributes can not have hyphen in the id. ");
		}
		
		if(s.indexOf("#")!=-1){
			chuckInvalidNameException("Not a valid id - " + s
				+ " dom id attributes can not contain hash symbols (#) values. " 
				+ "Conflicts with CSS and anchors ");
		}
		
		if(s.indexOf(":")!=-1) {
			chuckInvalidNameException("Not a valid id - " + s
				+ " dom id attributes can not contain colons (:). " 
				+	"Conflicts with CSS 1 id selectors ");
		}
		
		final Matcher m = s_pattern.matcher(s);
		final boolean b = m.matches();
		
		if(b) {
			throw new DsfInvalidNameException("Not a valid id - " + s +
				" dom  - special characters are not allowed.");
		}
	}

	public String getLongName() {
		String longName = m_longName;
		if(m_idMode.equals(MULTIPLE)) {
			longName  = longName + m_counter ;
		}
		return longName;
	}

	public String getShortName() {
		String shortName = m_shortName;
		if(m_idMode.equals(MULTIPLE)) {
			shortName  = shortName + m_counter ;
		}
		return shortName;
	}
	
	public DElementId next() {
		m_counter++ ;
		return this;
	}
	@Override
	public String toString() {
		return m_shortName;
	}

//	public Integer getCounter() {
//		return m_counter;
//	}

	public Mode getIdMode() {
		return m_idMode;
	}

//	public void setCounter(final Integer integer) {
//		m_counter = integer;
//	}

	public void setIdMode(final Mode string) {
		m_idMode = string;
	}

	public void setLongName(final String string) {
		m_longName = string;
	}

	public void setShortName(final String string) {
		m_shortName = string;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null){
			return false;
		}
		
		if (DElementId.class.isInstance(obj)) {
			DElementId id = (DElementId)obj;
			if (
				getLongName().equals(id.getLongName()) &&
				getShortName().equals(id.getShortName()) 
			)	 {return true;}	
			return false;	
		}
		
		if (String.class.isInstance(obj)){		
			if (
				this.getLongName().equals((String)obj) ||
				this.getShortName().equals((String)obj) 
			)	 {return true;}	
			return false;	
		}
		
		return false;		
	}

	private void chuckInvalidNameException(final String msg) {
		throw new DsfInvalidNameException(msg) ;
	}
	
	public DElementId clone() throws CloneNotSupportedException{
		DElementId cloned = (DElementId) super.clone();
		return cloned;
	}
}
