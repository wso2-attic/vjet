/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

public class ResolvedNamePath {

	public static final ResolvedNamePath EMPTY =new ResolvedNamePath(null,null);
	
	private final ParentScopes m_scopes;
	private final String m_localName;

	public ResolvedNamePath(final ParentScopes scopes, final String localName) {
		m_scopes = scopes;
		m_localName = localName;
	}
	
	public String getLocalName() {
		return m_localName;
	}
	
	public ParentScopes getScopes() {
		return m_scopes;
	}
}
