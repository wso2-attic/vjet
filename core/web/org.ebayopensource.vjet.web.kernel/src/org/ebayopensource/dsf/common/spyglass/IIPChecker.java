/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.spyglass;

import javax.servlet.http.HttpServletRequest;

/**
 * SpyGlass use it to check the request before the SpyGlass handler respond it.
 * 
 *  Yin
 * @data 2010 May 26, 2010 GMT+08:00
 */
public interface IIPChecker {
	
	public boolean check(final HttpServletRequest request);

}
