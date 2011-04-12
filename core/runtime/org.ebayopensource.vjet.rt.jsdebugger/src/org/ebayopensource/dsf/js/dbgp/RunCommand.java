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

final class RunCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	RunCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String object = (String) options.get("-i");
		m_debugger.m_runTransctionId = object;
		while (!m_debugger.isInited) {
			Thread.yield();
		}
		synchronized (m_debugger) {
			m_debugger.notify();
		}
		m_debugger.m_stackmanager.resume();
		// printResponse("<response command=\"run\"\r\n"
		// + "status=\"starting\"" + " reason=\"ok\""
		// + " transaction_id=\"" + object + "\">\r\n"
		// + "</response>\r\n" + "");
	}
}