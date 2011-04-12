/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

import java.io.Serializable;


/**
 * Class to store information about a function.
 */
public class FunctionSource implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Information about the source of the function.
	 */
	private SourceInfo m_sourceInfo;

	/**
	 * Line number of the first line of the function.
	 */
	private int m_firstLine;

	/**
	 * The function name.
	 */
	private String m_name;

	/**
	 * Creates a new FunctionSource.
	 */
	public FunctionSource(SourceInfo sourceInfo, int firstLine, String name) {
		if (name == null)
			throw new IllegalArgumentException();
		m_sourceInfo = sourceInfo;
		m_firstLine = firstLine;
		m_name = name;
	}

	/**
	 * Returns the SourceInfo object that describes the source of the
	 * function.
	 */
	public SourceInfo sourceInfo() {
		return m_sourceInfo;
	}

	/**
	 * Returns the line number of the first line of the function.
	 */
	public int firstLine() {
		return m_firstLine;
	}

	/**
	 * Returns the name of the function.
	 */
	public String name() {
		return m_name;
	}
}