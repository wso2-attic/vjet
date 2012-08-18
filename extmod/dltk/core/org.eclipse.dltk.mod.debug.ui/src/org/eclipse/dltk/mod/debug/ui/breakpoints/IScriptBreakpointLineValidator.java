package org.eclipse.dltk.mod.debug.ui.breakpoints;

public interface IScriptBreakpointLineValidator {
	boolean isValid(String line, int number);
}
