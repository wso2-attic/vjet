/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css;

import java.io.Serializable;

/*
 * Simple Type for CSS id
 */
public class CssIdConstant implements Serializable, Cloneable {

	private final String m_name;
	
	public CssIdConstant(final String name) {
		m_name = name;
	}
	
	public CssIdConstant(final String scope, final String name) {
		this(CssHelper.determineName(scope, name));
	}	

	public String getName() {
		return m_name;
	}

	private static final long serialVersionUID = 1L;
}
