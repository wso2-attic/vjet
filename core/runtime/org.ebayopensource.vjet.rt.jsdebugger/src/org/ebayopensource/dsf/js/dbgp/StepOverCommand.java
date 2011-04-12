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

final class StepOverCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	StepOverCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		m_debugger.m_runTransctionId = options.get("-i");
		if (m_debugger.m_stackmanager.getStackDepth() > 0) {
			m_debugger.m_stackmanager.stepOver();
		} else {
			synchronized (m_debugger) {
				while (!m_debugger.isInited) {
					Thread.yield();
				}
				m_debugger.notify();
			}
			m_debugger.m_stackmanager.resume();
		}
	}
}