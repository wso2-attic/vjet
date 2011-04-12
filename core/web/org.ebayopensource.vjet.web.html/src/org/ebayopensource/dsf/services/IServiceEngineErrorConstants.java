/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.services;

public interface IServiceEngineErrorConstants {
	String SCOPE = "SYS.";
	String SEVERITY = IErrorSeverity.FATAL;
	String INVALID_REQUEST_ERROR = SCOPE + "INVALID_REQUEST_ERROR";
	String INVALID_PARAMETER_ERROR = SCOPE + "INVALID_PARAMETER_ERROR";
	String MISSING_PARAMETER_ERROR = SCOPE + "MISSING_PARAMETER_ERROR";
	String INVALID_PARAMETER_TYPE_ERROR = SCOPE + "INVALID_PARAMETER_TYPE_ERROR";
	String SERVICE_NOT_FOUND = SCOPE + "SERVICE_NOT_FOUND";
	String SERVICE_ERROR = SCOPE + "SERVICE_ERROR";
	String GLOBAL_CHAIN_ERROR = SCOPE + "GLOBAL_CHAIN_ERROR";
	String TRANSPORT_CHAIN_ERROR = SCOPE + "TRANSPORT_CHAIN_ERROR";
	String SERVICE_CHAIN_ERROR = SCOPE + "SERVICE_CHAIN_ERROR";
}
