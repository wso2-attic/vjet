/*******************************************************************************
 * Copyright (c) 2005-2011 xored software, Inc., and eBay Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.display;

import org.eclipse.dltk.mod.console.IScriptInterpreter;
import org.eclipse.dltk.mod.console.ui.ScriptConsole;
import org.eclipse.dltk.mod.console.ui.internal.ScriptConsolePage;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.console.IConsoleView;

public class DebugConsole extends ScriptConsole {

	// EBAY MOD START
	private IScriptInterpreter interpreter;

	// EBAY MOD END

	/**
	 * @param consoleName
	 * @param consoleType
	 */
	public DebugConsole(String consoleName, String consoleType,
			IScriptInterpreter interpreter) {
		super(consoleName, consoleType);
		setInterpreter(interpreter);
		// EBAY MOD START
		this.interpreter = interpreter;
		// EBAY MOD END
	}

	protected ScriptConsolePage createPage(IConsoleView view,
			SourceViewerConfiguration cfg) {
		return new DebugConsolePage(this, view, cfg);
	}

	// EBAY MOD START
	/**
	 * @return the interpreter
	 */
	public IScriptInterpreter getInterpreter() {
		return interpreter;
	}

	/**
	 * @param interpreter
	 *            the interpreter to set
	 */
	public void setInterpreter(IScriptInterpreter interpreter) {
		super.setInterpreter(interpreter);
		this.interpreter = interpreter;
	}
	// EBAY MOD END
}
