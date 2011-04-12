/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * DirectAdjacentSelectorImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISiblingSelector;
import org.ebayopensource.dsf.css.sac.ISimpleSelector;

/** E + F {color:red} */
public class DDirectAdjacentSelector implements ISiblingSelector, Serializable {
	private static final long serialVersionUID = 1L;
	
	private short m_nodeType;
	private ISelector m_child;
	private ISimpleSelector m_directAdjacent;

	//
	// Constructor(s)
	//
	public DDirectAdjacentSelector(
		final short nodeType,
		final ISelector child,
		final ISimpleSelector directAdjacent)
	{
		m_nodeType = nodeType;
		m_child = child;
		m_directAdjacent = directAdjacent;
	}

	public short getNodeType() {
		return m_nodeType;
	}

	public short getSelectorType() {
		return ISelector.SAC_DIRECT_ADJACENT_SELECTOR;
	}

	public ISelector getSelector() {
		return m_child;
	}

	public ISimpleSelector getSiblingSelector() {
		return m_directAdjacent;
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return m_child.toString() + "+" + m_directAdjacent.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		DDirectAdjacentSelector clone = (DDirectAdjacentSelector)super.clone();
		clone.m_child = (ISelector)m_child.clone();
		clone.m_directAdjacent = (ISimpleSelector)m_directAdjacent.clone();
		return clone;
	}
}
