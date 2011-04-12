/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.console.ui;

import java.io.IOException;

import org.eclipse.dltk.mod.console.IScriptConsoleShell;
import org.eclipse.dltk.mod.console.ui.IScriptConsoleViewer;
import org.eclipse.dltk.mod.console.ui.ScriptConsoleTextHover;
import org.eclipse.jface.text.IRegion;

public class VjetConsoleTextHover extends ScriptConsoleTextHover {

	private IScriptConsoleShell interpreterShell;

	public VjetConsoleTextHover(IScriptConsoleShell interpreterShell) {
		this.interpreterShell = interpreterShell;
	}

	protected String getHoverInfoImpl(IScriptConsoleViewer viewer,
			IRegion hoverRegion) {
		try {
			int cursorPosition = hoverRegion.getOffset()
					- viewer.getCommandLineOffset();

			String commandLine = viewer.getCommandLine();

			return interpreterShell.getDescription(commandLine, cursorPosition);
		} catch (IOException e) {
			//TODO: log exception
			e.printStackTrace();
			return null;
		}
	}
	
}
