/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * PseudoClassConditionImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.IAttributeCondition;
import org.ebayopensource.dsf.css.sac.ICondition;

/** E:somePseudo {color:red} */
public class DPseudoClassCondition
	implements IAttributeCondition, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String m_value;

	//
	// Constructor(s)
	//
	public DPseudoClassCondition(final String value) {
		m_value = value;
	}

	//
	// Satisfy ICondition from IAttributeCondition
	//
	public short getConditionType() {
		return ICondition.SAC_PSEUDO_CLASS_CONDITION;
	}

	//
	// Satisfy IAttributeCondition
	//
	public String getNamespaceURI() {
		return null;
	}

	public String getLocalName() {
		return null;
	}

	public boolean getSpecified() {
		return true;
	}

	public String getValue() {
		return m_value;
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return ":" + getValue();
	}
}
