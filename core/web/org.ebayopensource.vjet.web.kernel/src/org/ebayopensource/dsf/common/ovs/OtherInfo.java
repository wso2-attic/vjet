/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.ovs;

public class OtherInfo {
	private Object m_userData = null;
	
	//
	// Constructor(s)
	//
	public OtherInfo() {
		// empty on purpose
	}
	
	public OtherInfo(final Object data) {
		setUserData(data) ;
	}
	
	//
	// API
	//
	public void setUserData(final Object data){
		m_userData = data;
	}
	
	public Object getUserData(){
		return m_userData;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return "userdata: " 
			+ ((m_userData == null) ? "null" : m_userData.toString()) ;
	}
}
