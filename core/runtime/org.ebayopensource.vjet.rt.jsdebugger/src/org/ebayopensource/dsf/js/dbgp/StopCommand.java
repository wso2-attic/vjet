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

final class StopCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	StopCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		m_debugger.printResponse("<response command=\"run\"\r\n"
			+ "status=\"stopped\"" + " reason=\"ok\""
			+ " transaction_id=\"" + options.get("-i") + "\">\r\n"
			+ "</response>\r\n" + "");
		System.exit(0); //KEEPME
	}
}