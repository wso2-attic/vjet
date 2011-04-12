/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * ConditionalSelectorImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.IConditionalSelector;
import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISimpleSelector;

public class DConditionalSelector
	implements IConditionalSelector, Serializable {

	private ISimpleSelector m_simpleSelector;
	private ICondition m_condition;

	public DConditionalSelector(
		ISimpleSelector simpleSelector,
		ICondition condition) {
		m_simpleSelector = simpleSelector;
		m_condition = condition;
	}

	public short getSelectorType() {
		return ISelector.SAC_CONDITIONAL_SELECTOR;
	}

	public ISimpleSelector getSimpleSelector() {
		return m_simpleSelector;
	}

	public ICondition getCondition() {
		return m_condition;
	}

	public String toString() {
		String nodeSelector = m_simpleSelector.toString();
		
		if((nodeSelector.length() == 1) && (nodeSelector.charAt(0) == '*')) {
			return m_condition.toString();
		} 
		return nodeSelector + m_condition.toString();
		
	}
	
	public Object clone() throws CloneNotSupportedException {
		DConditionalSelector clone = (DConditionalSelector)super.clone();
		clone.m_simpleSelector = (ISimpleSelector)m_simpleSelector.clone();
		return clone;
	}
}
