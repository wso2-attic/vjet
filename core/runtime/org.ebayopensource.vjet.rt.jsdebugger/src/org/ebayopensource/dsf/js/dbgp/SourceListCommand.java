/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.js.dbgp.DBGPDebugger.Command;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class SourceListCommand extends Command {

	private DBGPDebugger m_debugger;
	private ISourceProvider[] m_sourcePoviders;

	public SourceListCommand(DBGPDebugger debugger,
			ISourceProvider[] sourceProviders) {
		this.m_debugger = debugger;
		this.m_sourcePoviders = new ISourceProvider[sourceProviders.length];
		System.arraycopy(sourceProviders, 0, m_sourcePoviders, 0,
				sourceProviders.length);
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
		Set<String> fileSet = new HashSet<String>();
		for (ISourceProvider provider : m_sourcePoviders) {
			String[] paths = provider.list();
			for (int i = 0; i < paths.length; i++) {
				fileSet.add(paths[i]);
			}
		}
		String[] files = fileSet.toArray(new String[0]);

		StringBuilder builder = new StringBuilder();
		builder
				.append(
						"<response command=\"source_list\"\r\n          transaction_id=\"")
				.append(options.get("-i")).append("\"\r\n          success=\"")
				.append(1).append("\">\r\n").append(
						createFilePathNodesAsString(files)).append(
						"\r\n</response>\r\n");
		m_debugger.printResponse(builder.toString());
	}

	private String createFilePathNodesAsString(String[] files) {
		StringBuilder b = new StringBuilder();
		for (String f : files) {
			b.append("<file name=\"" + f + "\" />\r\n");
		}
		return b.toString();
	}

}
