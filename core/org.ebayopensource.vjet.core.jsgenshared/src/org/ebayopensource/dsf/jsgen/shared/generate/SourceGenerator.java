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

import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;

/**
 * Generic source code writer that provides basic functionality.
 * It can be used directly or as a base for any more specific 
 * source writers.
 */
public class SourceGenerator {
	
	public static final String NEWLINE = System.getProperty("line.separator");
	
	public static final String SPACE = " ";
	public static final String COLON = ":";
	public static final String SEMI_COLON = ";";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String QUESTION_MARK = "?";
	public static final String EQUAL = "=";
	public static final String OPEN_BRACKET = "[";
	public static final String CLOSE_BRACKET = "]";
	public static final String OPEN_PARENTHESIS = "(";
	public static final String CLOSE_PARENTHESIS = ")";
	public static final String OPEN_ANGLE_BRACKET = "<";
	public static final String CLOSE_ANGLE_BRACKET = ">";
	
	private CodeStyle m_style;
	private PrintWriter m_writer;
	private final Indenter m_indenter;
	private String m_newline = NEWLINE;
	
	//
	// Constructor
	//
	/**
	 * @param writer PrintWriter 
	 * @exception RuntimeException if writer is null
	 */
	public SourceGenerator(final PrintWriter writer, final Indenter indenter, final CodeStyle style){
		if (writer == null){
			throw new RuntimeException("writer is null");
		}
		m_writer = writer;
		m_style = style;
		m_indenter = indenter;
	}
	
	//
	// API
	//	
	public void setStyle(CodeStyle style){
		m_style = style;
	}
	/**
	 * Answer the mode of writer
	 * @param CodeStyle
	 */
	public CodeStyle getStyle(){
		return m_style;
	}
	
	public void setNewline(String newline){
		m_newline = newline;
	}

	public String getNewline(){
		return m_newline;
	}
	
	/**
	 * @param increment the indent by 1
	 * @return SourceWriter
	 */
	public SourceGenerator indent(){
		m_indenter.indent();
		return this;
	}
	
	/**
	 * @param decrement the indent by 1
	 * @return SourceWriter
	 */
	public SourceGenerator outdent(){
		m_indenter.outdent();
		return this;
	}
	
	/**
	 * Write space tabs based on current value of indent
	 * @return SourceWriter
	 */
	public SourceGenerator writeIndent(){
		m_indenter.writeIndent();
		return this;
	}
	
	/**
	 * Write a carriage return if PRETTY style is selected
	 * @return SourceWriter
	 */
	public SourceGenerator writeNewline(){
		if (m_style == CodeStyle.PRETTY){
			m_writer.append(m_newline);
		}
		return this;
	}
	
	/**
	 * Force a carriage return
	 * @return
	 */
	public SourceGenerator forceNewline(){
		m_writer.append(m_newline);
		return this;
	}
	
	@Override
	public String toString(){
		return m_writer.toString();
	}
	
	//
	// Protected
	//
	protected PrintWriter getWriter(){
		return m_writer;
	}
	
	protected Indenter getIndenter(){
		return m_indenter;
	}
	
	protected void writeCodeGenMarker(Class<? extends SourceGenerator> generator) {
		// Annotation to prevent checkin to source control
		m_writer.append("@")
		.append(IClassR.CodeGen)
		.append("(\"")
		.append(generator.getSimpleName())
		.append("\")");
	}
}