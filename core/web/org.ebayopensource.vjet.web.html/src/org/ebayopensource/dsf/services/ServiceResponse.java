/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceResponse {
	private List<ServiceError> m_errors;
	private Object m_data;
	private Map<String,String> m_sysMap = new HashMap<String, String>(3);
	
	public Object getData() {
		return m_data;
	}
	
	public void setData(final Object data) {
		m_data = data;
	}
	
	public List<ServiceError> getErrors() {
		return m_errors;
	}
	
	public void setErrors(final List<ServiceError> errors) {
		m_errors = errors;
	}

	public Map<String,String> getDataMap(){
		return m_sysMap;
	}
	public void setDataMap(Map<String,String> map){
		m_sysMap = map;
	}
}
