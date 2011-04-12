/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.js.dbgp;

import java.util.Map;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.VMBridge;
import org.mozilla.mod.javascript.debug.Debugger;

final class ContextGetCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	/**
	 * @param debugger
	 */
	ContextGetCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		StringBuffer properties = new StringBuffer();
		int level = Integer.parseInt(options.get("-d"));
		DBGPDebugFrame stackFrame = m_debugger.m_stackmanager
				.getStackFrame(level);
		// modify by patrick
		// setup context in debugger thread
		if (Context.getCurrentContext() == null) {
			Object helper = VMBridge.instance.getThreadContextHelper();
			VMBridge.instance.setContext(helper, stackFrame.getContext());
		}
		Context currentContext = Context.getCurrentContext();
		Debugger debugger = currentContext.getDebugger();
		Object contextData = currentContext.getDebuggerContextData();
		currentContext.setDebugger(null, null);
		
		m_debugger.setCurrentScope(stackFrame.getThis());

		String[] propertyIds = stackFrame.getParametersAndVars();
		for (int a = 0; a < propertyIds.length; a++) {
			String id = propertyIds[a];
			Object value = stackFrame.getValue(a);
			m_debugger.printProperty(id, id, value, properties, 0, true);

		}
		Scriptable this1 = stackFrame.getThis();
		if (this1 != null) {
			String id = "this";
			m_debugger.printProperty(id, id, this1, properties, 0, false);
		}
		
		currentContext.setDebugger(debugger, contextData);
		// end modify
		m_debugger.printResponse("<response command=\"context_get\"\r\n"
				+ "status=\"starting\"" + " reason=\"ok\""
				+ " transaction_id=\"" + options.get("-i") + "\">\r\n"
				+ properties + "</response>\r\n" + "");
	}
}