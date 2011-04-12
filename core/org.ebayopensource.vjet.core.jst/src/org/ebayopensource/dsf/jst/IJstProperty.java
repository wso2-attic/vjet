/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;

/**
 * JST property declaration (as opposite to usage).
 * 
 * 
 */
public interface IJstProperty extends IJstNode {
	
	/**
	 * Answer the type of the property
	 * @return IJstType
	 */
	IJstType getType();
	
	/**
	 * Answer the type of the property
	 * @return IJstTypeReference
	 */
	IJstTypeReference getTypeRef();
	
	/**
	 * Answer the name of the property
	 * @return JstName
	 */
	JstName getName();

	/**
	 * Answer the modifies of the property
	 * @return JstModifiers
	 */
	JstModifiers getModifiers();

	/**
	 * Answer whether the property is public access
	 * @return boolean
	 */
	boolean isPublic();
	
	/**
	 * Answer whether the property is protected access
	 * @return boolean
	 */
	boolean isProtected();
	
	/**
	 * Answer whether the property is internally accessible
	 * only from types within the same package
	 * @return boolean
	 */
	boolean isInternal();
	
	/**
	 * Answer whether the property is private access
	 * @return boolean
	 */
	boolean isPrivate();

	/**
	 * Answer whether the property is static
	 * @return boolean
	 */
	boolean isStatic();
	
	/**
	 * Answer whether the property is final
	 * @return boolean
	 */
	boolean isFinal();

	/**
	 * Answer the value of the property
	 * @return ISimpleTerm
	 */
	ISimpleTerm getValue();
	
	/**
	 * Answer the initializer of the property.
	 * Please note it is a child node of constructor 
	 * or the init block, depending on whether this is an instance or
	 * static property
	 * @return IExpr
	 */
	IExpr getInitializer();
	
	/**
	 * Answer Javadoc style documentation of the property
	 * @return IJstDoc or <code>null</code> if property has no documentation
	 */
	IJstDoc getDoc();
}
