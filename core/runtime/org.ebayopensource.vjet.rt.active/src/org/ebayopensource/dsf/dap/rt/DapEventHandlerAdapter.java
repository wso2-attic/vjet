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

import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.html.js.IScopeableJsHandler;

/**
 * DAP event handler adapter to integrate with JS runtime.
 * 
 * 
 */
public final class DapEventHandlerAdapter implements ISimpleJsEventHandler, IScopeableJsHandler{

	private IJsFunc m_func;
	private String m_scope;
	
	//
	// Constructors
	//
	DapEventHandlerAdapter (final String elemId, final int index) {
		m_scope = MessageFormat.format(DapHost.DAP_HOSTED_EVENT_HANDLER, new Object[]{elemId, new Integer(index)});
	}
	
	DapEventHandlerAdapter (final IJsFunc func){
		m_func = func;
	}

	//
	// Satisfying ISimpleJsEventHandler
	//
	/**
	 * @see ISimpleJsEventHandler#asJsDefinition()
	 */
	public String asJsDefinition() {
		return null;
	}

	/**
	 * @see ISimpleJsEventHandler#asJsHandler()
	 */
	public String asJsHandler() {
		return asJsHandler(null);
	}

	//
	// Satisfying IScopeableJsHandler
	//
	/**
	 * @see IScopeableJsHandler#asJsHandler()
	 */
	public String asJsHandler(String scope) {
		StringBuffer buf = new StringBuffer();
		if (DapCtx.ctx().isActiveMode()){
//			buf.append("function(event) { return this.process(event.src, event.eventType, event.nativeEvent.clientX, event.nativeEvent.clientY, event.nativeEvent.screenX, event.nativeEvent.screenY, event.nativeEvent.altKey, event.nativeEvent.shiftKey, event.nativeEvent.ctrlKey);}");
			buf.append("function(event) { return this.process(event.src, event.eventType, event.nativeEvent);}");
		}
		else {
			buf.append("function(event) { ").append(m_func.generate(scope)).append(" }");
		}
		return buf.toString();
	}

	/**
	 * @see IScopeableJsHandler#getScope()
	 */
	public String getScope() {
		if (DapCtx.ctx().isActiveMode()){
			return m_scope;
		}
		else if (m_func != null){
			return m_func.getScope();
		}
		return null;
	}
	
	//
	// API
	//
	/**
	 * Answer the instance of <code>IJsFunc</code> associated with this adapter
	 * @return IJsFunc
	 */
	public IJsFunc getJsFunc(){
		return m_func;
	}
}
