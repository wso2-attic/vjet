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

public class Variable implements IVariable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String m_name;	
	private final String m_definingClass;
	private IValue m_value;
	
	public Variable(String name, IValue value) {
		this(name, value, null);
	}
	
	public Variable(String name, IValue value, String definingClass) {
		m_name = name;
		m_value = value;
		m_definingClass = definingClass;
	}
	
	public String getDefiningClass() {
		return m_definingClass;
	}

	public String getName() {
		return m_name;
	}

	public IValue getValue() {
		return m_value;
	}
	
	public String toString() {
		return m_name;
	}
}
