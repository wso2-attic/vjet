/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser;

public class Location {
	private int m_beginLine;
	private int m_endLine;
	private int m_beginColumn;
	private int m_endColumn;
	private int m_beginOffset;
	private int m_endOffset;
	public int getBeginColumn() {
		return m_beginColumn;
	}
	public void setBeginColumn(int column) {
		m_beginColumn = column;
	}
	public int getBeginLine() {
		return m_beginLine;
	}
	public void setBeginLine(int line) {
		m_beginLine = line;
	}
	public int getEndColumn() {
		return m_endColumn;
	}
	public void setEndColumn(int column) {
		m_endColumn = column;
	}
	public int getEndLine() {
		return m_endLine;
	}
	public void setEndLine(int line) {
		m_endLine = line;
	}
	public void setBeginOffset(int beginOffset) {
		m_beginOffset = beginOffset;
	}
	public void setEndOffset(int endOffset) {
		m_endOffset = endOffset;
	}
	public int getBeginOffset() {
		return m_beginOffset;
	}
	public int getEndOffset() {
		return m_endOffset;
	}
}
