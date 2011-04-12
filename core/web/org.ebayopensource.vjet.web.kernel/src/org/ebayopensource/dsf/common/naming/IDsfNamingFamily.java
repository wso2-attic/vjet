/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;
/**
 * Provides the methods to verify whether the given DSF name is valid for use.
 */
public interface IDsfNamingFamily {
	/** 
     * Verifies that the local name meet the criteria for this family.
     * 
     * @param localName
     *        the string name to be verified
     * @return 
     *        the <code>NameStatusCheck</code> object that holds the check result
	 * @throws DsfInvalidNameException 
	 * 		  when the local name does not meet the criteria for a local name
	 */
	NameStatusCheck verifyLocalName(final String localName);

	/** 
	 * Verifies that the scope name meet the criteria for this family.
	 * 
	 * @param scopeName
	 *        the string name to be verified
	 * @return 
     *        the <code>NameStatusCheck</code> object that holds the check result
	 * @throws DsfInvalidNameException 
	 *        when the scope name does not meet the criteria for a local name
	 */
	NameStatusCheck verifyScopeName(final String scopeName);

	/** 
	 * Decomposes the fullyQualifiedName into its respected parts
	 * as specified by a ResolvedNamePath type.
	 * <p>
	 * Note: it does not do any verification to make sure that the
	 * fully qualified name (or returned ResolvedNamePath) is valid
	 * against your graph.
	 * 
	 * @param fullyQualifiedName
	 *        the string name to be decomposed
	 * @return
	 *        decomposed name object which holds scope name and local name as
	 *        its attributes
	 */
	ResolvedNamePath decomposeName(final String fullyQualifiedName);

	/** 
	 * Takes a <code>ResolvedNamePath</code> object and constructs a String that
	 * is the fully qualified name.
	 * <p>
	 * Note: it does not do any verification to make sure that the
	 * <code>ResolvedNamePath</code> (or returned fully qualified name) is valid
	 * against your graph.
	 * 
	 * @param resolvedNamePath
	 *        the object which holds scope name and local name as its attributes
	 * @return String 
	 *        the fully qualified string name. If there is nothing to resolve to, 
	 *        i.e. no scope and no local name; then return null
	 */
	String compose(final ResolvedNamePath resolvedNamePath);
}
