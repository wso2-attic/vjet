/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.common.Id;

public enum Enum {
	//TODO - Use Scope.Global in place of null when etype supports expressions
	A (1, null), //Scope.Global),
	B (2, null) //Scope.Local);
	{
		protected int id = -1;
		protected int intValue(){
			return getId().getId();
		}
	};
	
	private ID m_id;
	private Scope m_scope;

	Enum(int id, Scope scope){
		m_id = new ID(id);
		m_scope = scope;
	}
	
	protected int intValue(){
		return -1;
	}
	
	public ID getId(){
		return m_id;
	}
	
	static class ID extends Id {
		public ID(final int enumId) {
			super(enumId, String.valueOf(enumId), true) ;
		}
	}
	
	static enum Scope {
		Global,
		Local
	}
}
