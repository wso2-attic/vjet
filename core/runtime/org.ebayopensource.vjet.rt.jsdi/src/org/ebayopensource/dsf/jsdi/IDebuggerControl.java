/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for accessing debugger runtime info
 * as well as controlling the JS runtime engine via
 * its debug API.
 */
public interface IDebuggerControl extends ISession, Remote {
	
	// Constants for instructing the debugger what action to perform
	// to end interruption. Used by 'returnValue'.
	public static final int STEP_OVER = 0;
	public static final int STEP_INTO = 1;
	public static final int STEP_OUT = 2;
	public static final int GO = 3;
	public static final int BREAK = 4;
	public static final int EXIT = 5;
	
	/**
	 * Tells the debugger to break at the next opportunity.
	 */
	void setBreak() throws RemoteException;
	boolean shouldBreak() throws RemoteException;
	
	/**
	 * Sets whether the debugger should break on exceptions.
	 */
	void setBreakOnExceptions(boolean breakOnExceptions) throws RemoteException;
	boolean isBreakOnExceptions() throws RemoteException;

	/**
	 * Sets whether the debugger should break on function entering.
	 */
	void setBreakOnEnter(boolean breakOnEnter) throws RemoteException;
	boolean isBreakOnEnter() throws RemoteException;

	/**
	 * Sets whether the debugger should break on function return.
	 */
	public void setBreakOnReturn(boolean breakOnReturn) throws RemoteException;
	boolean isBreakOnReturn() throws RemoteException;
	
	/**
	 * Switches context to the stack frame with the given index.
	 */
	void contextSwitch(int frameIndex) throws RemoteException;
	
	/**
	 * Evaluates the given script.
	 */
	String eval(String expr) throws RemoteException;
	
	/**
	 * Evaluates the given script.
	 */
	void evalScript(final String url, final String text) throws RemoteException;
	
	/**
	 * Compiles the given script.
	 */
	public void compileScript(String url, String text) throws RemoteException;
	
	/**
	 * Returns whether the given string is syntactically valid script.
	 */
	boolean stringIsCompilableUnit(String str) throws RemoteException;
	
	/**
	 * Sets the action to perform to end interruption.
	 */
	void setReturnValue(int returnValue) throws RemoteException;
	
	/**
	 * Returns the FunctionSource object for the function with the given name.
	 */
	FunctionSource functionSourceByName(String functionName) throws RemoteException;

	/**
	 * Returns the SourceInfo object for the given URL.
	 */
	ISourceInfo getSourceInfo(String url) throws RemoteException;
	
	/**
	 * Returns an array of all function names.
	 */
	String[] functionNames() throws RemoteException;
	
	/**
	 * Returns the current stack frame count.
	 */
	int getFrameCount() throws RemoteException;
	
	/**
	 * Returns the info gor a stack frame.
	 */
	StackFrameInfo getFrameInfo(int frameIndex) throws RemoteException;
	
	/**
	 * register the IGuiCallback
	 */
	void setGuiCallback(IGuiCallback callback) throws RemoteException;
}
