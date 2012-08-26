/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.test.core.ecma.jst.validation;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * PrintStream that automatically maintains indentation level on linefeeds.<br>
 * The indentation level can be changed via  {@link incLevel()} {@link decLevel()} methods,
 * or via special escape symbols \1 (increase) and \2 (decrease).
 */
public class IndentedPrintStream extends PrintStream {
	int 		m_indentation_level;
	boolean 	m_was_newline = true;
	int			m_verbose_level;
	int			m_indentation_step;
	
	/**
	 * @param out output stream delegate
	 */
	public IndentedPrintStream(OutputStream out) {
		this(out, 0, 2);
	}
	/**
	 * @param out output stream delegate
	 * @param vl trigger verbose level
	 */
	public IndentedPrintStream(OutputStream out, int vl) {
		this(out, vl, 2);
	}
	/**
	 * 
	 * @param out  output stream delegate
	 * @param vl trigger verbose level
	 * @param indent num of speces per indent
	 */
	public IndentedPrintStream(OutputStream out, int vl, int indent) {
		super(out);
		m_verbose_level = vl;
		m_indentation_step = indent;
	}

	@Override
	public void write(byte[] b, int off, int len) {
		for (int i = 0 ; i < len ; i++) {
		    write(b[off + i]);
		}
	}
	@Override
	public void write(byte[] b) {
		for (int i = 0 ; i < b.length ; i++) {
		    write(b[i]);
		}
	}
	@Override
	public void write(int b) {
		if (b == '\1') {
			incLevel();
			return;
		}
		if (b == '\2') {
			decLevel();
			return;
		}
		if (m_was_newline) {
			m_was_newline = false;
			for (int i=0; i < m_indentation_level; i++) {
				if (m_indentation_step == 4)
					super.write('\t');
				else {
					for (int n=0; n < m_indentation_step; n++) {
						super.write(' ');
					}
				}
			}
		}
		super.write(b);
		if (b == '\n') {
			m_was_newline = true;
		}
	}
	
	public void incLevel() {
		m_indentation_level++;
	}
	
	public void decLevel() {
		if (m_indentation_level > 0)
			m_indentation_level--;
	}
	
	public static IndentedPrintStream getIndentedStream(OutputStream out) {
		if (out instanceof IndentedPrintStream) {
			return (IndentedPrintStream)out;
		}
		else {
			return new IndentedPrintStream(out);
		}
	}
	
	/**
	 * conditionally println based on stream's verbose level
	 * @param vl verbose level
	 * @param s string to output
	 */
	public void println(int vl, String s) {
		if (m_verbose_level >= vl)
			super.println(s);
	}
	
	public void print(int vl, String s) {
		if (m_verbose_level >= vl)
			super.print(s);
	}
	
	public boolean ok(int vl) {
		return m_verbose_level >= vl;
	}

	public int getVerboseLevel() {
		return m_verbose_level;
	}

	public void setVerboseLevel(int verbose_level) {
		m_verbose_level = verbose_level;
	}
}
