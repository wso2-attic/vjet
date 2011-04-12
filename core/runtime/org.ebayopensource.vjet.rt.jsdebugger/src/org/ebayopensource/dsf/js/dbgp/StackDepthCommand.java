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

final class StackDepthCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	StackDepthCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, final Map<String, String> options) {
		m_debugger.printResponse("<response command=\"stack_depth\"\r\n"
			+ "          depth=\""
			+ (m_debugger.m_stackmanager.getStackDepth()) + "\"\r\n"
			+ "          transaction_id=\"" + options.get("-i") + "\">\r\n"
			+ "</response>\r\n" + "");
	}
}