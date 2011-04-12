/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import org.ebayopensource.dsf.jst.IJstType;

public class ProcessResult {

	private boolean isStatic = false;

	private IJstType nodeType;

	public ProcessResult(boolean isStatic, IJstType nodeType) {
		super();
		this.isStatic = isStatic;
		this.nodeType = nodeType;
	}

	/**
	 * @return the isStatic
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * @return the nodeType
	 */
	public IJstType getNodeType() {
		return nodeType;
	}

	
}
