/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.IGeneralSiblingSelector;
import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISimpleSelector;

public class DGeneralSiblingSelector
	implements IGeneralSiblingSelector, Serializable {

	private short m_nodeType;
	private ISelector m_child;
	private ISimpleSelector m_generalSibling;

	public DGeneralSiblingSelector(
		short nodeType,
		ISelector child,
		ISimpleSelector generalSibling)
	{
		m_nodeType = nodeType;
		m_child = child;
		m_generalSibling = generalSibling;
	}

	public short getNodeType() {
		return m_nodeType;
	}

	public short getSelectorType() {
		return ISelector.SAC_GENERAL_SIBLING_SELECTOR;
	}

	public ISelector getSelector() {
		return m_child;
	}

	public ISimpleSelector getSiblingSelector() {
		return m_generalSibling;
	}

	public String toString() {
		return m_child.toString() + "~" + m_generalSibling.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		DGeneralSiblingSelector clone = (DGeneralSiblingSelector)super.clone();
		clone.m_child = (ISelector)m_child.clone();
		clone.m_generalSibling = (ISimpleSelector)m_generalSibling.clone();
		return clone;
	}
}
