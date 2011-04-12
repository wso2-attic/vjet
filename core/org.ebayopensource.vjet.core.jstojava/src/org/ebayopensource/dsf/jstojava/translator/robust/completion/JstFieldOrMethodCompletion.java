/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import org.ebayopensource.dsf.jst.BaseJstNode;

/**
 * 
 * 
 */
public class JstFieldOrMethodCompletion extends JstCompletion {
	
	private static final long serialVersionUID = 1L;
	
	private boolean m_isStatic;

	public JstFieldOrMethodCompletion(BaseJstNode parent, boolean isStatic) {
		super(parent, new String[0]);
		this.m_isStatic = isStatic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.jstojava.translator2.robust.completion.JstCompletion#getIncompletePart()
	 */
	@Override
	public String getIncompletePart() {
		return getToken();
	}

	/**
	 * @return true if completion was performed inside props block
	 */
	public boolean isStatic() {
		return m_isStatic;
	}
}
