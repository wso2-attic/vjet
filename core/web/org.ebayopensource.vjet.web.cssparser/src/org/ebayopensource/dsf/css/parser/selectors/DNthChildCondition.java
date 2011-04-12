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
import org.ebayopensource.dsf.css.sac.INthChildCondition;

/** E:nth-child(n) */
public class DNthChildCondition implements INthChildCondition, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String m_positionExpr ;

	//
	// Constructor(s)
	//
	public DNthChildCondition(String positionExpr) {
		m_positionExpr = positionExpr ;
	}

	//
	// Satisfy ICondition
	//
	public short getConditionType() {
		return ICondition.SAC_NTH_CHILD_CONDITION;
	}

	//
	// Satisfy INthChildCondition
	//
	public String getPositionExpr() {
		return m_positionExpr ;
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return ":nth-child(" + getPositionExpr() + ")";
	}
}

