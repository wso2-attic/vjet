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

import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.IMozPseudoCondition;

public class DMozPseudoCondition implements IMozPseudoCondition, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String m_pseudo ;
	private String m_argValue;

	//
	// Constructor(s)
	//
	public DMozPseudoCondition(String pseudo, String argValue) {
		m_pseudo = pseudo ;
		m_argValue = argValue ;
	}

	//
	// Satisfy ICondition
	//
	public short getConditionType() {
		return ICondition.SAC_MOZ_PSEUDO_CONDITION;
	}

	//
	// Satisfy IMozPseudoCondition
	//
	public String getPseudo() {
		return m_pseudo ;
	}
	public String getArgValue() {
		return m_argValue ;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return ":" + getPseudo() + "(" + getArgValue() + ")";
	}
}

