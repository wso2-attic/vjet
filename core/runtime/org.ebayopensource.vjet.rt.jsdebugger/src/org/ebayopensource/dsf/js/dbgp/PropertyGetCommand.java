/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.util.Map;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.VMBridge;
import org.mozilla.mod.javascript.debug.Debugger;

final class PropertyGetCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	PropertyGetCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String longName = options.get("-n");
		int level = 0;
		String depth = options.get("-d");
		if (depth != null) {
			level = Integer.parseInt(depth);
		}
		Object value = null;
		int shName = longName.indexOf('.');
		if (shName == -1) {
			shName = longName.length();
		}
		String shortName = longName.substring(0, shName);
		StringBuffer properties = new StringBuffer();
		DBGPDebugFrame stackFrame = m_debugger.m_stackmanager
				.getStackFrame(level);
		// modify by patrick
		if (stackFrame != null) {
			if (Context.getCurrentContext() == null) {
				Object helper = VMBridge.instance.getThreadContextHelper();
				VMBridge.instance.setContext(helper, stackFrame.getContext());
			}
			Context currentContext = Context.getCurrentContext();
			Debugger debugger = currentContext.getDebugger();
			Object contextData = currentContext.getDebuggerContextData();
			currentContext.setDebugger(null, null);
			
			m_debugger.setCurrentScope(stackFrame.getThis());
			
			value = stackFrame.getValue(longName);
			m_debugger.printProperty(shortName, longName, value, properties, 0, true);
			
			currentContext.setDebugger(debugger, contextData);
		}
		// end modify
		m_debugger.printResponse("<response command=\"property_get\"\r\n"
				+ " transaction_id=\"" + options.get("-i") + "\">\r\n"
				+ properties + "</response>\r\n" + "");
	}
}