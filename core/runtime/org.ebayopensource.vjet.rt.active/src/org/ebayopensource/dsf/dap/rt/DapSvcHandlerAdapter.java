/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.text.MessageFormat;

import org.ebayopensource.dsf.html.js.IJsContentGenerator;


public final class DapSvcHandlerAdapter implements IJsContentGenerator {
	
	public static final String DEFAULT_SVC_NAME = "onResponse";
	
	private String m_scope;
	private String m_svcName;

	public DapSvcHandlerAdapter(String svcId, int index){
		this(svcId, DEFAULT_SVC_NAME, index);
	}
	
	public DapSvcHandlerAdapter(String svcId, String svcName, int index){
		m_scope = MessageFormat.format(DapHost.DAP_HOSTED_SVC_HANDLER, new Object[]{svcId, new Integer(index)});
		m_svcName = svcName;
	}
	
	public String generate() {
		return m_scope + "." + m_svcName + "(message);";
	}
}
