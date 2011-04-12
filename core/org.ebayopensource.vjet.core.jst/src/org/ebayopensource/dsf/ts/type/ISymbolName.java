/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.type;

/**
 * common interface that Property and Method symbols should satisfy. Used by SymbolTableManager.
 * FIXME: why PropertyName and MethodName should be 2 distinct types? 
 * They are exactly the same except the name, and having 2 types prevents tighter code sharing in SymbolTableManager.
 */
public interface ISymbolName {
	/**
	 * Answers Group (i.e. project) that contain type definition for this symbol.
	 * @return
	 */
	String getGroupName();	
	
	/**
	 * Answers type name that declares this symbol
	 * @return
	 */
	String getOwnerTypeName();
	
	/**
	 * Answers the local symbol name, either a method or property name.
	 * @return
	 */
	String getLocalName();
}
