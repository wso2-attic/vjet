/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;

public interface IJstGlobalVar extends IJstNode{
	
	/**
	 * Answers if type is a global function
	 */
	
	boolean isFunc();
	
	/**
	 *  Answers with global function meta data
	 */
	
	IJstGlobalFunc getFunction();
	

	
	/**
	 *  Answers with global property meta data
	 */
	
	IJstGlobalProp getProperty();
	

	

	/**
	 * Answer the type of the global variable
	 * @return IJstType
	 */
	IJstType getType();
	
	/**
	 * Answer the type of the global variable
	 * @return IJstTypeReference
	 */
	IJstTypeReference getTypeRef();
	
	/**
	 * Answer the name of the global variable
	 * @return JstName
	 */
	JstName getName();


	/**
	 * Answer the value of the global variable
	 * @return ISimpleTerm
	 */
	ISimpleTerm getValue();
	
	/**
	 * Answer the initializer of the global variable.
	 * @return IExpr
	 */
	IExpr getInitializer();
	
	/**
	 * Answer Javadoc style documentation of the property
	 * @return IJstDoc or <code>null</code> if property has no documentation
	 */
	IJstDoc getDoc();
	
	void setScopeForGlobal(String scopeForGlobal);

	String getScopeForGlobal();
	
}
