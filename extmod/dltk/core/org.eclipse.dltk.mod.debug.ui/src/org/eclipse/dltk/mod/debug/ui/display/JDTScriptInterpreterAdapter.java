/*******************************************************************************
 * Copyright (c) 2000-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     eBay Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.display;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.dltk.mod.compiler.util.Util;
import org.eclipse.dltk.mod.console.IScriptConsoleIO;
import org.eclipse.dltk.mod.console.IScriptExecResult;
import org.eclipse.dltk.mod.console.IScriptInterpreter;
import org.eclipse.dltk.mod.console.ScriptExecResult;
import org.eclipse.dltk.mod.internal.launching.LaunchConfigurationUtils;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaValue;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.debug.eval.IAstEvaluationEngine;
import org.eclipse.jdt.debug.eval.IEvaluationListener;
import org.eclipse.jdt.debug.eval.IEvaluationResult;
import org.eclipse.jdt.internal.debug.core.JDIDebugPlugin;

/**
 * EBAY MOD ADD Adapter for evaluating java objects in debug console.
 */
public class JDTScriptInterpreterAdapter implements IScriptInterpreter,
		IEvaluationListener {

	private IJavaStackFrame m_javaStackFrame;
	private IEvaluationResult m_jdtResult;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptInterpreter#addInitialListenerOperation
	 * (java.lang.Runnable)
	 */
	public void addInitialListenerOperation(Runnable runnable) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptInterpreter#getInitialOutputStream()
	 */
	public InputStream getInitialOutputStream() {
		return new ByteArrayInputStream(new byte[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.console.IScriptInterpreter#isValid()
	 */
	public boolean isValid() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.console.IScriptConsoleShell#close()
	 */
	public void close() throws IOException {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptConsoleShell#getCompletions(java.lang
	 * .String, int)
	 */
	public List getCompletions(String commandLine, int position)
			throws IOException {
		// TODO add code completon support
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptConsoleShell#getDescription(java.lang
	 * .String, int)
	 */
	public String getDescription(String commandLine, int position)
			throws IOException {
		// do nothing
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptConsoleShell#getNames(java.lang.String
	 * )
	 */
	public String[] getNames(String type) throws IOException {
		// do nothing
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.IScriptConsoleInterpreter#exec(java.lang
	 * .String)
	 */
	public IScriptExecResult exec(String command) throws IOException {
		IProject project = LaunchConfigurationUtils.getProject(m_javaStackFrame
				.getLaunch().getLaunchConfiguration());
		IJavaProject javaProject = JavaCore.create(project);
		IAstEvaluationEngine evaluationEngine = JDIDebugPlugin.getDefault()
				.getEvaluationEngine(javaProject,
						(IJavaDebugTarget) m_javaStackFrame.getDebugTarget());
		boolean hitBreakpoints = JDIDebugModel.getPreferences().getBoolean(
				JDIDebugModel.PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION);
		try {
			evaluationEngine.evaluate(command, m_javaStackFrame, this,
					DebugEvent.EVALUATION, hitBreakpoints);
			synchronized (this) {
				wait();
				return getResult();
			}
		} catch (Exception e) {
			return new ScriptExecResult(e.getLocalizedMessage()
					+ Util.LINE_SEPARATOR, true);
		}
	}

	private IScriptExecResult getResult() throws DebugException {
		IJavaValue value = m_jdtResult.getValue();
		if (m_jdtResult == null) {
			return new ScriptExecResult(
					Messages.DebugScriptInterpreter_NoDebugger
							+ Util.LINE_SEPARATOR, true);
		}
		if (value != null) {
			String output = MessageFormat.format(
					Messages.JDTScriptInterpreterAdapter_ValuePattern,
					new Object[] { value.getReferenceTypeName(),
							value.getValueString() });
			if (output == null) {
				output = Messages.DebugScriptInterpreter_null;
			}
			if (!output.endsWith(Util.LINE_SEPARATOR)) {
				output = output + Util.LINE_SEPARATOR;
			}
			return new ScriptExecResult(output);
		}
		final StringBuffer buffer = new StringBuffer();
		final String[] errors = m_jdtResult.getErrorMessages();
		for (int i = 0; i < errors.length; ++i) {
			buffer.append(errors[i]);
			buffer.append(Util.LINE_SEPARATOR);
		}
		if (errors.length == 0) {
			buffer.append(Messages.DebugScriptInterpreter_unknownEvaluationError);
			buffer.append(Util.LINE_SEPARATOR);
		}
		return new ScriptExecResult(buffer.toString(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.console.IScriptConsoleInterpreter#getState()
	 */
	public int getState() {
		return WAIT_NEW_COMMAND;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.mod.console.ConsoleRequest#consoleConnected(org.eclipse.
	 * dltk.console.IScriptConsoleIO)
	 */
	public void consoleConnected(IScriptConsoleIO protocol) {
		// do nothing
	}

	public IJavaStackFrame getStackFrame() {
		return m_javaStackFrame;
	}

	public void setStackFrame(IJavaStackFrame javaStackFrame) {
		m_javaStackFrame = javaStackFrame;
	}

	public synchronized void evaluationComplete(IEvaluationResult result) {
		this.m_jdtResult = result;
		notify();
	}
}
