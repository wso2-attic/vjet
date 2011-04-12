/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

public class DNodeRelationshipVerifierStatus 
	implements IDNodeRelationshipVerifier.Status
{
	private static final long serialVersionUID = 1L;
	private final boolean m_ok ;
	private final String m_errorMessage ;
	
	//
	// Constructor(s)
	//
	public DNodeRelationshipVerifierStatus(
		final boolean ok, final String errorMessage)
	{
		m_ok = ok ;
		m_errorMessage = errorMessage ;
	}
	
	//
	// Satisfy IDNodeRelationshipVerifier.Status
	//
	public boolean isOk() {
		return m_ok ;
	}
	
	public String getErrorMessage() {
		return m_errorMessage ;
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return "ok: " + m_ok + " msg: " + m_errorMessage ;
	}
}
