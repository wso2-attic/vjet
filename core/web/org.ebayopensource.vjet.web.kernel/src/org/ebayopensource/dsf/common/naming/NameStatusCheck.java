/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

public class NameStatusCheck {

	public static final NameStatusCheck OK = new NameStatusCheck(true, null);

	private final boolean m_isOk;
	private final String m_message;
	public NameStatusCheck(final boolean isOk, final String message) {
		m_isOk = isOk;
		m_message = message;
	}
	public boolean isOk() {
		return m_isOk;
	}
	public String getErrorMessage() {
		return m_message;
	}
}
