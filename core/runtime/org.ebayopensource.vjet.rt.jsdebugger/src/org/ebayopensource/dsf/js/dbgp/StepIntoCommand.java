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

final class StepIntoCommand extends DBGPDebugger.Command {

	private final DBGPDebugger m_debugger;

	StepIntoCommand(DBGPDebugger debugger) {
		m_debugger = debugger;
	}

	void parseAndExecute(String command, Map<String, String> options) {
		m_debugger.m_runTransctionId = options.get("-i");
		if (m_debugger.m_stackmanager.getStackDepth() > 0) {
			m_debugger.m_stackmanager.stepIn();
		} else {
			while (!m_debugger.isInited) {
				Thread.yield();
			}
			synchronized (m_debugger) {
				m_debugger.notify();
			}
			m_debugger.m_stackmanager.resume();
		}
	}
}