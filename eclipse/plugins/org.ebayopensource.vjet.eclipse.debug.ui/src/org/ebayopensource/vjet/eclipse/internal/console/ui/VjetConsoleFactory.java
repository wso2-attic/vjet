/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.console.ui;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.dltk.mod.console.IScriptInterpreter;
import org.eclipse.dltk.mod.console.ScriptConsolePrompt;
import org.eclipse.dltk.mod.console.ui.IScriptConsoleFactory;
import org.eclipse.dltk.mod.console.ui.ScriptConsole;
import org.eclipse.dltk.mod.console.ui.ScriptConsoleFactoryBase;
import org.eclipse.jface.preference.IPreferenceStore;

import org.ebayopensource.vjet.eclipse.console.VjetConsoleConstants;
import org.ebayopensource.vjet.eclipse.console.VjetInterpreter;
import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

public class VjetConsoleFactory extends ScriptConsoleFactoryBase implements IScriptConsoleFactory {

	protected IPreferenceStore getPreferenceStore() {
		return VjetDebugUIPlugin.getDefault().getPreferenceStore();
	}

	protected ScriptConsolePrompt makeInvitation() {
		IPreferenceStore store = getPreferenceStore();
		return new ScriptConsolePrompt(store
				.getString(VjetConsoleConstants.PREF_NEW_PROMPT), store
				.getString(VjetConsoleConstants.PREF_CONTINUE_PROMPT));
	}

	protected VjetConsole makeConsole(VjetInterpreter interpreter, String id) {
		VjetConsole console = new VjetConsole(interpreter, id);
		console.setPrompt(makeInvitation());
		return console;
	}

	private VjetConsole createConsoleInstance(IScriptInterpreter interpreter, String id) {

		return makeConsole((VjetInterpreter)interpreter, id);
	}

	protected ScriptConsole createConsoleInstance() {
		return createConsoleInstance(null, null);
	}

	public void openConsole(IScriptInterpreter interpreter, String id, ILaunch launch) {
		registerAndOpenConsole(createConsoleInstance(interpreter, id));
	}


}
