/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.services;

public class ServiceError {
	private String m_id;
	private String m_message;
	
	public ServiceError(){}
	
	public ServiceError(String id, String message) {
		m_id = id;
	}
	public String getId() {
		return m_id;
	}
	public void setId(String id) {
		this.m_id = id;
	}
	public String getMessage() {
		return m_message;
	}
	public void setMessage(String message) {
		this.m_message = message;
	}
}
