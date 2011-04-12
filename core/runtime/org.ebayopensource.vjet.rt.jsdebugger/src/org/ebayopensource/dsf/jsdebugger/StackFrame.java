/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import org.ebayopensource.dsf.jsdi.FunctionSource;
import org.ebayopensource.dsf.jsdi.IValue;
import org.ebayopensource.dsf.jsdi.SourceInfo;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.debug.DebugFrame;

/**
 * Object to represent one stack frame.
 */
public class StackFrame implements DebugFrame {

	/**
	 * The debugger.
	 */
	private DebuggerAdapter m_debugger;

	/**
	 * The ContextData for the Context being debugged.
	 */
	private ContextData m_contextData;

	/**
	 * The scope.
	 */
	private Scriptable m_scope;

	/**
	 * The 'this' object.
	 */
	private Scriptable m_thisObj;

	/**
	 * Information about the function.
	 */
	private FunctionSource m_fsource;

	/**
	 * Array of breakpoint state for each source line.
	 */
	private boolean[] m_breakpoints;

	/**
	 * Current line number.
	 */
	private int m_lineNumber;
	
	private ObjectIdMapper m_objCache = new ObjectIdMapper();

	/**
	 * Creates a new StackFrame.
	 */
	StackFrame(Context cx, DebuggerAdapter dim, FunctionSource fsource) {
		m_debugger = dim;
		m_contextData = ContextData.get(cx);
		m_fsource = fsource;
		m_breakpoints = fsource.sourceInfo().getBreakpoints();
		m_lineNumber = fsource.firstLine();
	}

	/**
	 * Called when the stack frame is entered.
	 */
	public void onEnter(Context cx, Scriptable scope, Scriptable thisObj,
			Object[] args) {
		m_contextData.pushFrame(this);
		m_scope = scope;
		m_thisObj = thisObj;
		if (m_debugger.isBreakOnEnter()) {
			m_debugger.handleBreakpointHit(this, cx);
		}
	}

	/**
	 * Called when the current position has changed.
	 */
	public void onLineChange(Context cx, int lineno) {
		m_lineNumber = lineno;

		if (!m_breakpoints[lineno] && !m_debugger.shouldBreak()) {
			boolean lineBreak = m_contextData.isBreakNextLine();
			if (lineBreak && m_contextData.getStopAtFrameDepth() >= 0) {
				lineBreak = (m_contextData.frameCount() <= m_contextData.getStopAtFrameDepth());
			}
			if (!lineBreak) {
				return;
			}
			m_contextData.setStopAtFrameDepth(-1);
			m_contextData.setBreakNextLine(false);
		}

		m_debugger.handleBreakpointHit(this, cx);
	}

	/**
	 * Called when an exception has been thrown.
	 */
	public void onExceptionThrown(Context cx, Throwable exception) {
		m_debugger.handleExceptionThrown(cx, exception, this);
	}

	/**
	 * Called when the stack frame has been left.
	 */
	public void onExit(Context cx, boolean byThrow, Object resultOrException) {
		if (m_debugger.isBreakOnReturn() && !byThrow) {
			m_debugger.handleBreakpointHit(this, cx);
		}
		m_contextData.popFrame();
	}

	/**
	 * Called when a 'debugger' statement is executed.
	 */
	public void onDebuggerStatement(Context cx) {
		m_debugger.handleBreakpointHit(this, cx);
	}

	/**
	 * Returns the SourceInfo object for the function.
	 */
	public SourceInfo sourceInfo() {
		return m_fsource.sourceInfo();
	}

	/**
	 * Returns the ContextData object for the Context.
	 */
	public ContextData contextData() {
		return m_contextData;
	}

	/**
	 * Returns the scope object for this frame.
	 */
	public Scriptable scope() {
		return m_scope;
	}

	/**
	 * Returns the 'this' object for this frame.
	 */
	public Scriptable thisObj() {
		return m_thisObj;
	}

	/**
	 * Returns the source URL.
	 */
	public String getUrl() {
		return m_fsource.sourceInfo().getUri();
	}

	/**
	 * Returns the current line number.
	 */
	public int getLineNumber() {
		return m_lineNumber;
	}
	
	public long getId(Object obj) {
		if (obj == m_thisObj) {
			return IValue.THIS_ID;
		}
		if (obj == m_scope) {
			return IValue.SCOPE_ID;
		}
		return m_objCache.getId(obj);
	}
	
	public Object getObject(long id) {
		if (id == IValue.THIS_ID) {
			return m_thisObj;
		}
		if (id == IValue.SCOPE_ID) {
			return m_scope;
		}
		return m_objCache.getObject(id);
	}
	
	public void clearObjectCache() {
		m_objCache.clear();
	}
}
