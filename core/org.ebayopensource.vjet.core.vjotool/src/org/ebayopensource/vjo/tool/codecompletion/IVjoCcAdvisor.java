/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

/**
 * Calculate a certain type proposal data based on the vjo context
 * 
 * 
 * 
 */
public interface IVjoCcAdvisor {
	public static final int PRIORITY_GLOBAL = 10;
	public static final int PRIORITY_TYPE = 8;
	public static final int PRIORITY_METHOD = 6;
	public static final int PRIORITY_FUNCTION = 4;
	public static final int PRIORITY_DEFAULT = 0;

	/**
	 * advise process based on ctx
	 * 
	 * @param ctx
	 */
	void advise(VjoCcCtx ctx);

	/**
	 * @return Advisor ID
	 */
	String getId();

}
