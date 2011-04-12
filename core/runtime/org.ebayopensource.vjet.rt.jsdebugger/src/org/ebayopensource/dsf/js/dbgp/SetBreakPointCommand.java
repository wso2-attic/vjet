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

final class SetBreakPointCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	SetBreakPointCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		BreakPoint p = new BreakPoint(options);
		m_debugger.m_stackmanager.registerBreakPoint(p);
		m_debugger.printResponse("<response command=\"breakpoint_set\"\r\n"
			+ " transaction_id=\"" + options.get("-i") + "\" " + " id=\"p"
			+ p.m_id + "\" state=\"enabled\" > " + "</response>\r\n" + "");
	}
}