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

final class UpdateBreakPointCommand extends DBGPDebugger.Command {
	
	private final DBGPDebugger m_debugger;

	UpdateBreakPointCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {

		String id = options.get("-d");
		String newState = options.get("-s");
		String newLine = options.get("-n");
		String hitValue = options.get("-h");
		String hitCondition = options.get("-o");
		String condEString = options.get("--");

		if (condEString != null) {
			condEString = Base64Helper.decodeString(condEString);
		}

		m_debugger.m_stackmanager.updateBreakpoint(id, newState, newLine,
				hitValue, hitCondition, condEString);
		String enabled = newState;
		m_debugger.printResponse("<response command=\"breakpoint_update\"\r\n"
			+ " transaction_id=\"" + options.get("-i") + "\">\r\n"
			+ " id=\"" + id + "\" state=\"" + enabled + "\" "
			+ "</response>\r\n" + "");
	}
}