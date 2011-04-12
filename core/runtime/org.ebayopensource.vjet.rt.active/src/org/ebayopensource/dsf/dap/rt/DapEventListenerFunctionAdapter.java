/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventListener;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * Simple JS function adapter for EventListener.
 * 
 * It provides call and apply methods for Script Engine.
 */
public class DapEventListenerFunctionAdapter extends ScriptableObject {

	private static final long serialVersionUID = 1L;
	
	private static String[] METHOD_NAMES = {"call", "apply", "valueOf"};
	
	private final EventListener m_eventListener;
	
	public DapEventListenerFunctionAdapter(EventListener eventListener) {
		m_eventListener = eventListener;
		defineFunctionProperties(METHOD_NAMES,
			DapEventListenerFunctionAdapter.class,
			ScriptableObject.DONTENUM);
	}

	public Object call(Object scope, Event event) {
		m_eventListener.handleEvent(event);
		return null;
	}
	
	public Object apply(Object scope, Event event) {
		m_eventListener.handleEvent(event);
		return null;
	}
	
	@Override
	public String getClassName() {
		return getClass().getName();
	}
	
	/**
	 * for JS debugger usage
	 */
	public Object valueOf(String type) {
		if (type.equalsIgnoreCase("string")) {
			return getClass().getName();
		}
		if (type.equalsIgnoreCase("boolean")) {
			return Boolean.TRUE;
		}
		return null;
	}
}
