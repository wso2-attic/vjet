/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.Serializable;

import org.ebayopensource.dsf.css.dom.ICssRule;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;

public abstract class DCssRule implements ICssRule, Serializable, Cloneable {

	protected ICssStyleSheet m_parentStyleSheet = null;
	protected ICssRule m_parentRule = null;
	
	protected DCssRule
		(ICssStyleSheet parentStyleSheet, ICssRule parentRule) {
		m_parentStyleSheet = parentStyleSheet;
		m_parentRule = parentRule;
	}
	
	public ICssRule getParentRule() {
		return m_parentRule;
	}

	public ICssStyleSheet getParentStyleSheet() {
		return m_parentStyleSheet;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
