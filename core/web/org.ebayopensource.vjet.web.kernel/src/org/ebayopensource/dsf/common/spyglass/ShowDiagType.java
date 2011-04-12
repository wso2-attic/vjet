/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.spyglass;

/**
 *  yin
 * @data Apr 29, 2010 GMT+08:00
 */
public enum ShowDiagType {

	BIZMO("Bizmo Information"), CUSTOMER("Customer Information"), PAGE(
			"Page Information");

	private String m_name;

	ShowDiagType(String name) {
		m_name = name;
	}

	public String getName() {
		return m_name;
	}

}
