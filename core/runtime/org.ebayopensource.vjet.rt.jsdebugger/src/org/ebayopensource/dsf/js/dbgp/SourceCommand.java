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

import org.ebayopensource.dsf.js.dbgp.DBGPDebugger.Command;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class SourceCommand extends Command {

	private DBGPDebugger		m_debugger;
	private ISourceProvider[]	m_providers;

	SourceCommand(DBGPDebugger debugger, ISourceProvider[] providers) {
		this.m_debugger = debugger;
		this.m_providers = providers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.js.dbgp.DBGPDebugger.Command#parseAndExecute(java.lang.String
	 * , java.util.Map)
	 */
	@Override
	void parseAndExecute(String command, Map<String, String> options) {
		int beginLine = -1;
		int endLine = -1;
		int success = 1;

		String filePath = options.get("-f");
		String beginLineStr = options.get("-b");
		String endLineStr = options.get("-e");

		// get the file associated with the top frame of current context
		if (filePath == null) {
			DBGPDebugFrame frame = m_debugger.m_stackmanager.getStackFrame(0);
			filePath = frame.getSourceName();
		}

		if (!isNullOrEmpty(beginLineStr)) {
			beginLine = Integer.parseInt(beginLineStr);
		}

		if (!isNullOrEmpty(endLineStr)) {
			endLine = Integer.parseInt(endLineStr);
		}

		String contents = getSource(filePath, beginLine, endLine);
		contents = Base64Helper.encodeString(contents);

		StringBuilder builder = new StringBuilder();
		builder.append(
				"<response command=\"source\"\r\n          transaction_id=\"")
				.append(options.get("-i")).append("\"\r\n          success=\"")
				.append(success).append("\">\r\n").append(contents).append(
						"\r\n</response>\r\n");
		m_debugger.printResponse(builder.toString());
	}

	private String getSource(String filePath, int beginLine, int endLine) {
		String contents = "";
		for (ISourceProvider provider : m_providers) {
			contents = provider.getSource(filePath, beginLine, endLine);
			if (contents != null && contents.length() > 0) {
				break;
			}
		}
		return contents;
	}

	private boolean isNullOrEmpty(String string) {
		return string == null || string.equals("");
	}

}
