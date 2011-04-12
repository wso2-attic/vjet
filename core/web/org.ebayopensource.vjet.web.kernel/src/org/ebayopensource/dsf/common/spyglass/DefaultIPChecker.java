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
 * This IPChecker is just provided for develop or test dsf application.
 * It throws exception for PROD environment and returns true in DEV environment.
 *  Yin
 * @data 2010 May 26, 2010 GMT+08:00
 */
public class DefaultIPChecker implements IIPChecker {

	public static final String SPYGLASS_DEBUG_FLAG = "com.ebay.spyglass.debug";
	
	private static final DefaultIPChecker s_instance = new DefaultIPChecker();

	public static DefaultIPChecker getInstance() {
		return s_instance;
	}

	private DefaultIPChecker() {
	}

	@Override
	public boolean check(final HttpServletRequest request) {

		if (null == request) {
			return false;
		}
	
		if("true".equalsIgnoreCase(System.getProperty(SPYGLASS_DEBUG_FLAG))){
			// dev env
			return true;
		}else{
			//produce env
			throw new RuntimeException("IPChecker must be provided for production environment.");
		}		
	}

}
