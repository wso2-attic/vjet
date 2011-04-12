/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * SelectorListImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISelectorList;

public class DSelectorList implements ISelectorList, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private ArrayList<ISelector> m_selectors = new ArrayList<ISelector>(5);

	public int getLength() {
		return m_selectors.size();
	}

	public ISelector item(int index) {
		return m_selectors.get(index);
	}

	public void add(ISelector sel) {
		m_selectors.add(sel);
	}
	
	public void set(int index, ISelector sel) {
		if (index < 0 || index >= m_selectors.size()) {
			throw new DsfRuntimeException("index " + index 
				+ " is out of range for size " + m_selectors.size());
		}
		m_selectors.set(index, sel);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		int len = getLength();
		for (int i = 0; i < len; i++) {
			sb.append(item(i).toString());
			if (i < len - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException {
		DSelectorList clone = (DSelectorList)super.clone();
		clone.m_selectors = (ArrayList)m_selectors.clone();
		for (int i = 0; i < clone.m_selectors.size(); i++) {
			clone.m_selectors.set
				(i, (ISelector)clone.m_selectors.get(i).clone());
		}
		return clone;
	}
}
