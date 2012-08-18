package org.eclipse.dltk.mod.javascript.internal.console.ui;

import java.io.IOException;

import org.eclipse.dltk.mod.console.IScriptConsoleShell;
import org.eclipse.dltk.mod.console.ui.IScriptConsoleViewer;
import org.eclipse.dltk.mod.console.ui.ScriptConsoleTextHover;
import org.eclipse.jface.text.IRegion;


public class JavaScriptConsoleTextHover extends ScriptConsoleTextHover {

	private IScriptConsoleShell interpreterShell;

	public JavaScriptConsoleTextHover(IScriptConsoleShell interpreterShell) {
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
