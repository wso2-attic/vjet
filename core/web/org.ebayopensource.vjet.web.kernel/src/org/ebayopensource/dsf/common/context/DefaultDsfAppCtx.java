/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

public class DefaultDsfAppCtx implements IDsfAppCtx {
	
	private IInputParamValueProvider m_ip;
	private IDsfApp m_app;
	
	public static DefaultDsfAppCtx ctx() {
		 return (DefaultDsfAppCtx)DsfCtx.ctx().getAppCtx() ;
	}
	
	//
	// Satisfy IDsfAppCtx
	//
	public IInputParamValueProvider getInputDataProvider(){
		return m_ip;
	}
	
	public IDsfApp getApp() {
		return m_app;
	}
	
	//
	// API
	//	
	public void setApp(final IDsfApp app) {
		m_app = app;
	}
	
	public void setInputDataProvider(final IInputParamValueProvider ip){
		m_ip = ip;
	}
}

