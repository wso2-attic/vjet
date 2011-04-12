/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * LangConditionImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.ILangCondition;

public class DLangCondition implements ILangCondition, Serializable {

	private String m_lang;

	public DLangCondition(String lang) {
		m_lang = lang;
	}

	public short getConditionType() {
		return ICondition.SAC_LANG_CONDITION;
	}

	public String getLang() {
		return m_lang;
	}

	public String toString() {
		return ":lang(" + getLang() + ")";
	}
}
