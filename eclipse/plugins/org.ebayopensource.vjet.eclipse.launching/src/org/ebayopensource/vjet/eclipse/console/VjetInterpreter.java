/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.console;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.dltk.mod.console.ConsoleRequest;
import org.eclipse.dltk.mod.console.IScriptConsoleIO;
import org.eclipse.dltk.mod.console.IScriptExecResult;
import org.eclipse.dltk.mod.console.IScriptInterpreter;
import org.eclipse.dltk.mod.console.InterpreterResponse;
import org.eclipse.dltk.mod.console.ScriptExecResult;
import org.eclipse.dltk.mod.console.ShellResponse;

public class VjetInterpreter implements IScriptInterpreter, ConsoleRequest {

	private static final String COMPLETE_COMMAND = "complete";

	private static final String DESCRIBE_COMMAND = "describe";

	private static final String CLOSE_COMMAND = "close";
		
	private IScriptConsoleIO protocol;

	private int state;

	// IScriptInterpreter
	public IScriptExecResult exec(String command) throws IOException {
		InterpreterResponse response = protocol.execInterpreter(command);

		state = response.getState();
		return new ScriptExecResult(response.getContent());
	}

	public int getState() {
		return state;
	}

	// IScriptInterpreterShell
	public List getCompletions(String commandLine, int position)
			throws IOException {

		String[] args = new String[] { commandLine, Integer.toString(position) };

		ShellResponse response = protocol.execShell(COMPLETE_COMMAND, args);
		
		return response.getCompletions();
	}

	public String getDescription(String commandLine, int position)
			throws IOException {
		String[] args = new String[] { commandLine, Integer.toString(position) };

		ShellResponse response = protocol.execShell(DESCRIBE_COMMAND, args);
		
		return response.getDescription();
	}

	public String[] getNames(String type) throws IOException {
		return null;
	}

	public void close() throws IOException {
		protocol.execShell(CLOSE_COMMAND, new String[] {});
		protocol.close();
	}

	// IScriptConsoleProtocol
	public void consoleConnected(IScriptConsoleIO protocol) {
		this.protocol = protocol;
	}

	public String getInitialOuput() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addInitialListenerOperation(Runnable runnable) {
		// TODO Auto-generated method stub

	}

	public InputStream getInitialOutputStream() {
		return null;
	}
	
	public boolean isValid() {
		return protocol != null;
	}
}
