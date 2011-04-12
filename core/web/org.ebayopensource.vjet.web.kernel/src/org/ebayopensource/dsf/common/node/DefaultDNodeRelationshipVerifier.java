/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import org.ebayopensource.dsf.dom.DNode;

public class DefaultDNodeRelationshipVerifier 
	implements IDNodeRelationshipVerifier {

	private static final long serialVersionUID = 1L;

	private static IDNodeRelationshipVerifier s_instance = 
		new DefaultDNodeRelationshipVerifier();
		
	private static Status s_status = new OkStatus();

	public static IDNodeRelationshipVerifier getInstance() {
		return s_instance;
	}

	//
	// Satisfy ComponentRelationshipVerifier
	//
	public Status acceptableAsChild(final DNode parent, final DNode child) {
		return s_status;
	}
	
	public Status acceptableAsParent(final DNode child, final DNode parent) {
		return s_status;
	}

	public Status acceptableAsFacet(
		final DNode parent, final String facetName, final DNode facet)
	{
		return s_status;		
	}
	
	public Status acceptableAsAttribute(
		final DNode parent, final String attributeName, final Object attr)
	{
		return s_status;		
	}

	//
	// Helper class(es)
	//		
	private static class OkStatus implements Status {	

		private static final long serialVersionUID = 1L;

		public boolean isOk() {
			return true;
		}
		
		public String getErrorMessage() {
			return null;		
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
