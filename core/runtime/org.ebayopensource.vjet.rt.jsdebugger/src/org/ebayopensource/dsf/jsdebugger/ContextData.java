/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ObjArray;

/**
 * Class to store information about a stack.
 */
public class ContextData {

	/**
	 * The stack frames.
	 */
	private ObjArray m_frameStack = new ObjArray();

	/**
	 * Whether the debugger should break at the next line in this context.
	 */
	private boolean m_breakNextLine;

	/**
	 * The frame depth the debugger should stop at. Used to implement "step
	 * over" and "step out".
	 */
	private int m_stopAtFrameDepth = -1;

	/**
	 * Whether this context is in the event thread.
	 */
	private boolean m_eventThreadFlag;

	/**
	 * The last exception that was processed.
	 */
	private Throwable m_lastProcessedException;

	/**
	 * Returns the ContextData for the given Context.
	 */
	public static ContextData get(Context cx) {
		return (ContextData) cx.getDebuggerContextData();
	}

	/**
	 * Returns the number of stack frames.
	 */
	public int frameCount() {
		return m_frameStack.size();
	}

	/**
	 * Returns the stack frame with the given index.
	 */
	public StackFrame getFrame(int frameNumber) {
		int num = m_frameStack.size() - frameNumber - 1;
		return (StackFrame) m_frameStack.get(num);
	}

	/**
	 * Pushes a stack frame on to the stack.
	 */
	public void pushFrame(StackFrame frame) {
		m_frameStack.push(frame);
	}

	/**
	 * Pops a stack frame from the stack.
	 */
	public void popFrame() {
		m_frameStack.pop();
	}

	public boolean isBreakNextLine() {
		return m_breakNextLine;
	}

	public void setBreakNextLine(boolean breakNextLine) {
		this.m_breakNextLine = breakNextLine;
	}

	public boolean getEventThreadFlag() {
		return m_eventThreadFlag;
	}

	public void setEventThreadFlag(boolean eventThreadFlag) {
		m_eventThreadFlag = eventThreadFlag;
	}

	public Throwable getLastProcessedException() {
		return m_lastProcessedException;
	}

	public void setLastProcessedException(Throwable lastProcessedException) {
		m_lastProcessedException = lastProcessedException;
	}

	public int getStopAtFrameDepth() {
		return m_stopAtFrameDepth;
	}

	public void setStopAtFrameDepth(int stopAtFrameDepth) {
		m_stopAtFrameDepth = stopAtFrameDepth;
	}
}