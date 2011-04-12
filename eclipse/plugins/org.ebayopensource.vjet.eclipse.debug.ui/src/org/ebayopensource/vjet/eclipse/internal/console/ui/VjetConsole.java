/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.console.ui;

import org.eclipse.dltk.mod.console.ui.ScriptConsole;

import org.ebayopensource.vjet.eclipse.console.VjetInterpreter;

public class VjetConsole extends ScriptConsole{

	public static final String CONSOLE_TYPE = "tcl_console";

	public static final String CONSOLE_NAME = "Tcl Console";
	
	public VjetConsole(VjetInterpreter interpreter, String id) {
		super(CONSOLE_NAME + " [" + id + "]", CONSOLE_TYPE);

		setInterpreter(interpreter);
		setTextHover(new VjetConsoleTextHover(interpreter));
		//setContentAssistProcessor(new JavaScriptConsoleCompletionProcessor(interpreter));
	}	

	
}
