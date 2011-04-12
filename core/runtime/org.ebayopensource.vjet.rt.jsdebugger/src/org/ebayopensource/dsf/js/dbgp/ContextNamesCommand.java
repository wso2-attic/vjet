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

final class ContextNamesCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	/**
	 * @param debugger
	 */
	ContextNamesCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String object = (String) options.get("-i");
		m_debugger.m_runTransctionId = object;

		m_debugger.printResponse("<response command=\"context_names\"\r\n"
				+ "          transaction_id=\"" + options.get("-i") + "\">"
				+ "    <context name=\"Local\" id=\"0\"/>\r\n"
				+ "    <context name=\"Global\" id=\"1\"/>\r\n"
				+ "    <context name=\"Class\" id=\"2\"/>\r\n" + ""
				+ "</response>\r\n" + "");
	}
}