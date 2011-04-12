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
import org.ebayopensource.dsf.css.sac.INthOfTypeCondition;

/** E:nth-of-type(n) {color:red} */
public class DNthOfTypeCondition implements INthOfTypeCondition, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String m_positionExpr;

	//
	// Constructor(s)
	//
	public DNthOfTypeCondition(final String positionExpr) {
		m_positionExpr = positionExpr ;
	}

	//
	// Satisfy ICondition
	//
	public short getConditionType() {
		return ICondition.SAC_NTH_OF_TYPE_CONDITION ;
	}

	//
	// Satisfy INthOfTypeCondition
	//
	public String getPositionExpr() {
		return m_positionExpr ;
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return ":nth-of-type(" + getPositionExpr() + ")";
	}
}

