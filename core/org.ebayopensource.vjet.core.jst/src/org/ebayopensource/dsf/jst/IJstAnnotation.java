/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.List;

import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * 
 * JST annotation declaration
 *
 */
public interface IJstAnnotation extends IJstNode {
	
	/**
	 * Returns the name of the annotation
	 * @return String
	 */
	JstName getName();
	
	/**
	 * Return a list of the values (name-value pairs represented by <code>AssignExpr</code>)
	 * @return a list of <code>AssignExpr</code>
	 */
	List<IExpr> values();

}
