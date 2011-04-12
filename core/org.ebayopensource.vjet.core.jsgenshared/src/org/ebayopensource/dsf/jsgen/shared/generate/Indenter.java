/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.io.PrintWriter;


public class Indenter {
	
	public static final String TAB = "    ";

	private int m_indent;
	private CodeStyle m_codeStyle;
	private PrintWriter m_writer;
	
	//
	// Constructor
	//
	public Indenter(final PrintWriter writer, final CodeStyle style){
		m_writer = writer;
		m_codeStyle = style;
	}
	
	/**
	 * increment the indent by 1
	 */
	public void indent(){
		m_indent++;
	}
	
	/**
	 * decrement the indent by 1
	 */
	public void outdent(){
		m_indent--;
	}
	
	/**
	 * Write space tabs based on current value of indent if PRETTY style is selected
	 */
	public void writeIndent(){
		if (m_codeStyle == CodeStyle.PRETTY){
			for (int j = 0; j < m_indent; ++j) {
				m_writer.append(TAB);
		    }
		}
	}
}
