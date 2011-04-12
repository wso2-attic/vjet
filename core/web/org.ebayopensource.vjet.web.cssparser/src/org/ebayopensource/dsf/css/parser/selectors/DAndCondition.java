/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * AndConditionImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.ICombinatorCondition;
import org.ebayopensource.dsf.css.sac.ICondition;

/** E F {color:red} */
public class DAndCondition implements ICombinatorCondition, Serializable {
	private static final long serialVersionUID = 1L;
	
	private ICondition m_c1;
	private ICondition m_c2;

	//
	// Constructor(s)
	//
	public DAndCondition(final ICondition c1, final ICondition c2) {
		m_c1 = c1;
		m_c2 = c2;
	}

	//
	// Satisfy ICondition from IAttributeCondition
	//
	public short getConditionType() {
		return ICondition.SAC_AND_CONDITION;
	}

	//
	// Satisfy ICombinatorCondition
	//
	public ICondition getFirstCondition() {
		return m_c1;
	}

	public ICondition getSecondCondition() {
		return m_c2;
	}

	//
	// Override(s) from String
	//
	@Override
	public String toString() {
		return getFirstCondition().toString() + getSecondCondition().toString();
	}
}
