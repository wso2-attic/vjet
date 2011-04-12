/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service;


/**
 * Static definition for server-side service handlers
 */
public interface IServiceSpec {
	
	/**
	 * Answer the name of the service handler
	 * @return String
	 */
	String getServiceName();
	
	/**
	 * Answer the service config
	 * @return ServiceConfig
	 */
	ServiceConfig getServiceConfig();
	
	/**
	 * Answer whether the response should be gzipped
	 * when client device supports it.
	 * @return boolean
	 */
	boolean shouldGzip();
}
