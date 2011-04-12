/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.parser.selectors;

import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.INegativeCondition;

public class DNegativeCondition implements INegativeCondition {
	private ICondition m_condition;
	public DNegativeCondition(ICondition condition) {
		m_condition = condition;
	}
	public ICondition getCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	public short getConditionType() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String toString() {
		return ":not(" + m_condition + ")";
	}
}
