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

public class StackFrameInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int m_frameIndex;
	private final String m_sourceName;
	private final int m_lineNumber;
	private final boolean m_hasDifferentScope;
	
	
	public StackFrameInfo(int frameIndex, String sourceName, int lineNumber, boolean hasDifferentScope) {
		m_frameIndex = frameIndex;
		m_sourceName = sourceName;
		m_lineNumber = lineNumber;
		m_hasDifferentScope = hasDifferentScope;
	}
	
	public int getFrameIndex() {
		return m_frameIndex;
	}
	
	public int getLineNumber() {
		return m_lineNumber;
	}
	
	public String getSourceName() {
		return m_sourceName;
	}	

	public boolean hasDifferentScope() {
		return m_hasDifferentScope;
	}	
}
