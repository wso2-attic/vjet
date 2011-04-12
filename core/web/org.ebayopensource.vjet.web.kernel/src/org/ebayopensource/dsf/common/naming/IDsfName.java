/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import java.io.Serializable;

/**
 * A local name is the name used in its parent or inner most scope.
 * A scope name is similar to directory name; it can contain other 
 * nodes with either a scope or local name.  For if a scope name "A:B:C" 
 * has a node in its subtree with a locale name of "foo".  Then its fully
 * qualified name would be "A:B:C#foo" (assuming that ":" is scope separator
 * and "#" is seprator between scope and local names).  
 *
 */
public interface IDsfName extends Serializable, Cloneable{
	/**
	 * LocalName can be used by a scoped parent to uniquely address
	 * the child or facet component.
	 */
	String getLocalName();
	void setLocalName(String localName);
	
	/**
	 * ScopeName provides scope for all its decedent components.
	 */	
	String getScopeName();
	void setScopeName(String scopeName);
	
	/**
	 * Get parent scopes
	 */
	ParentScopes getParentScopes();
	
	String getFullName();
	String getFullName(IDsfNamingFamily namingFamily);
}
