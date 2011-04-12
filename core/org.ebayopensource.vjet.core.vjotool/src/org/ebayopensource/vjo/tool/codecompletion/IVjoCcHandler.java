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
 * Handle the proposal calculation function
 * 
 */
public interface IVjoCcHandler {
	/**
	 * work on the ctx, and inject the ctx with all the proposals
	 * @param ctx
	 * @return the Advisor ID
	 */
	String[] handle(VjoCcCtx ctx);
}
