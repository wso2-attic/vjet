/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;

public class JsFunctionEventHandlerAdaptor implements ISimpleJsEventHandler, IScopeableJsHandler{

	private IJsFunc m_func;
	public JsFunctionEventHandlerAdaptor (IJsFunc func) {
		m_func = func;
	}
	
//	public List<IJsContentGenerator> getAllJsContent() {
//		List<IJsContentGenerator> list = new ArrayList<IJsContentGenerator>(1);
//		list.add(m_func);
//		return list;
//		StringBuffer buf = new StringBuffer();
//			buf.append("function(event) { ")
//        	.append(m_func.generate()).append("}");
//		return buf.toString();
//	}

	public String asJsDefinition() {
		return null;
	}

	public String asJsHandler() {
		return asJsHandler(null);
	}

	public String asJsHandler(String scope) {
		StringBuffer buf = new StringBuffer();
		buf.append("function(event) { ").append(m_func.generate(scope)).append(" }");
		return buf.toString();
	}

	public String getScope() {
		return m_func.getScope();
	}
	
	public IJsFunc getJsFunc(){
		return m_func;
	}
}
