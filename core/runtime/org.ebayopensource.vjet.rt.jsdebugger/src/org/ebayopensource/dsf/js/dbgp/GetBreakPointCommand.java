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

public class GetBreakPointCommand extends Command {

	private DBGPDebugger m_debugger;

	public GetBreakPointCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		String id = (String) options.get("-d");

		BreakPoint breakpoint = m_debugger.m_stackmanager.getBreakpoint(id);
		m_debugger.printResponse("<response command=\"breakpoint_get\"\r\n"
			+ " transaction_id=\""
			+ options.get("-i")
			+ "\">\r\n"
			+"<breakpoint"
			+ " id=\""
			+ id
			+ "\""
			+ " type=\""
			+ breakpoint.getType()
			+ "\""
			+ " state=\""
			+ breakpoint.getState()
			+ "\""
			+ " filename=\""
			+ breakpoint.m_file
			+ "\""
			+ " lineno=\""
			+ breakpoint.m_line
			+ "\""
			+ " function=\""
			+ breakpoint.m_method
			+ "\""
			+ " exception=\""
			+ "\""
			+ " hit_value=\""
			+ breakpoint.m_hitValue
			+ "\""
			+ " hit_condition=\""
			+ breakpoint.getHitCondition()
			+ "\""
			+ " hit_count=\""
			+ breakpoint.m_currentHitCount + "\""
			+" >\r\n"
			+"<expression>"+Base64Helper.encodeString(breakpoint.m_expression)+"</expression>"
			+ "</breakpoint>\r\n"
			+ "</response>\r\n" + "");
	}

}
