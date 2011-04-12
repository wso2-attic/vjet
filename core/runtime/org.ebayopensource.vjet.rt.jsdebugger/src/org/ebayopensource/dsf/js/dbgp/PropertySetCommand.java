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

final class PropertySetCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	PropertySetCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String name = options.get("-n");
		int num = Integer.parseInt(options.get("-d"));
		String value = Base64Helper.decodeString(options.get("--"));
		if (num >= 0) {
			DBGPDebugFrame fr = m_debugger.m_stackmanager.getStackFrame(num);
			fr.setValue(name, value);
		} else {
			if (name.equals("suspendOnEntry")) {
				boolean parseBoolean = Boolean.valueOf(value);
				m_debugger.setSuspendOnEntry(parseBoolean);
			}
			if (name.equals("suspendOnExit")) {
				boolean parseBoolean = Boolean.valueOf(value);
				m_debugger.setSuspendOnExit(parseBoolean);
			}
			m_debugger.setProperty(name, value);
		}
		m_debugger.printResponse("<response command=\"property_set\"\r\n"
			+ " transaction_id=\"" + options.get("-i")
			+ "\" success=\"1\" " + ">\r\n" + "</response>\r\n" + "");
	}
}