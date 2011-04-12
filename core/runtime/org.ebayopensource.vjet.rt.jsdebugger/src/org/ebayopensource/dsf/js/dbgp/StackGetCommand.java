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

final class StackGetCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	StackGetCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, final Map<String, String> options) {
		String string = options.get("-d");
		int level = -1;
		if (string != null) {
			level = Integer.parseInt(string);
		}
		StringBuffer stack = new StringBuffer();
		if (m_debugger.m_stackmanager.getStackDepth() >= level) {
			if (level == -1) {
				for (int a = 0; a < m_debugger.m_stackmanager.getStackDepth(); a++) {
					appendLevel(a, stack);
				}
			} else {
				appendLevel(level, stack);
			}
			m_debugger.printResponse("<response command=\"stack_get\"\r\n"
				+ "\r\n" + "          transaction_id=\""
				+ options.get("-i") + "\">\r\n"
				+ stack + "</response>\r\n" + "");
		}
	}

	private void appendLevel(int level, StringBuffer stack) {

		DBGPDebugFrame stackFrame = m_debugger.m_stackmanager
				.getStackFrame(level);

		String sourceFilePath = stackFrame.getSourceName();

		// modify by patrick
		sourceFilePath = PathUtil.normalize(sourceFilePath);
		// end modify
		
		stack.append("<stack level=\"" + level + "\"\r\n"
			+ "           type=\"file\"\r\n" + "           filename=\""
			+ sourceFilePath + "\"\r\n" + "           lineno=\""
			+ (stackFrame.getLineNumber()) + "\"\r\n"
			+ "           where=\"" + stackFrame.getWhere() + "\"\r\n"
			+ "           cmdbegin=\"1:0\"\r\n" + "           cmdend=\""
			+ "1" + ":10\"/>");
	}
}