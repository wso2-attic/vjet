/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * CharacterDataSelectorImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import java.io.Serializable;

import org.ebayopensource.dsf.css.sac.ICharacterDataSelector;
import org.ebayopensource.dsf.css.sac.ISelector;

public class DCharacterDataSelector
	implements ICharacterDataSelector, Serializable {

	private String m_data;

	public DCharacterDataSelector(String data) {
		m_data = data;
	}

	public short getSelectorType() {
		return ISelector.SAC_CDATA_SECTION_NODE_SELECTOR;
	}

	public String getData() {
		return m_data;
	}

	public String toString() {
		return getData();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
