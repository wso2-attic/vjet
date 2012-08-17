/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.debug;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.mod.dbgp.IDbgpProperty;
import org.eclipse.dltk.mod.dbgp.commands.IDbgpCoreCommands;
import org.eclipse.dltk.mod.dbgp.commands.IDbgpExtendedCommands;
import org.eclipse.dltk.mod.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.mod.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.mod.debug.core.model.IScriptThread;

import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class DebuggerTest extends AbstractVjetAppDebugTest {

	private static final String	SINGLE_QUOT	= "'";

	public void testEvalCommand() throws Exception {
		final String fileName = "SimpleMain1.js";
		final String packagePath = "debug";
		int line = 8;
		IDebugEventHandler handler = new IDebugEventHandler() {

			@Override
			public void handleDebugEvent(DebugEvent event)
					throws DbgpException, DebugException {
				if (DebugEvent.SUSPEND == event.getKind()) {
					if (event.getSource() instanceof IScriptThread) {
						IScriptThread st = (IScriptThread) event.getSource();
						IScriptStackFrame frame = (IScriptStackFrame) st
								.getTopStackFrame();

						IDbgpExtendedCommands extendedCommands = st
								.getDbgpSession().getExtendedCommands();
						int level = frame.getLevel();
						extendedCommands.evaluate("b='patrick'", level);

						resumeThread(st,
								getQualifiedName(fileName, packagePath));
					}

				}

			}
		};

		doDebuggerTest(fileName, packagePath, line, handler);
	}

	public void testPropertySetCommand() throws Exception {
		final String fileName = "PropertyMain.js";
		final String packagePath = "debug";
		int line = 10;

		IDebugEventHandler handler = new IDebugEventHandler() {

			@Override
			public void handleDebugEvent(DebugEvent event)
					throws DbgpException, DebugException {
				if (DebugEvent.SUSPEND == event.getKind()
						&& DebugEvent.BREAKPOINT == event.getDetail()
						&& event.getSource() instanceof IScriptThread) {
					IScriptThread st = (IScriptThread) event.getSource();
					IScriptStackFrame frame = (IScriptStackFrame) st
							.getTopStackFrame();

					// check set value to local variable
					setPropertyAndCheck("s", "'Obama'", frame);

					// check set value to fields of local variable
					setPropertyAndCheck("t.name", "'John'", frame);

					resumeThread(st, getQualifiedName(fileName, packagePath));

				}
			}

			private void setPropertyAndCheck(String propName, String value,
					IScriptStackFrame frame) throws DbgpException {
				int level = frame.getLevel();
				IDbgpCoreCommands coreCommands = frame.getScriptThread()
						.getDbgpSession().getCoreCommands();
				coreCommands.setProperty(propName, level, value);

				IDbgpProperty property = coreCommands.getProperty(propName,
						level);

				checkResult(propName, value, SINGLE_QUOT + property.getValue()
						+ SINGLE_QUOT);
			}

			private void checkResult(String propName, String expected,
					String actual) {
				assertEquals("Fail to set property: " + propName, expected,
						actual);
			}
		};

		doDebuggerTest(fileName, packagePath, line, handler);
	}

	public void testDebugHtml() throws Exception {
		final String fileName = "HtmlDebug.htm";
		final String packagePath = "debug";
		int line = 18;

		IDebugEventHandler handler = new IDebugEventHandler() {

			@Override
			public void handleDebugEvent(DebugEvent event)
					throws DbgpException, DebugException {
				if (DebugEvent.SUSPEND == event.getKind()
						&& DebugEvent.BREAKPOINT == event.getDetail()
						&& event.getSource() instanceof IScriptThread) {
					IScriptThread st = (IScriptThread) event.getSource();
					IDbgpCoreCommands coreCommands = st.getDbgpSession()
							.getCoreCommands();

					checkProperty("s", "StringValue", coreCommands);
					checkProperty("t.name", "David", coreCommands);

					resumeThread(st, fileName);
				}

			}

			private void checkProperty(String propName, String expected,
					IDbgpCoreCommands coreCommands) throws DbgpException {
				IDbgpProperty property = coreCommands.getProperty(propName);
				assertEquals("Failed to debug js in html file.", expected,
						property.getValue());
			}
		};
		doDebuggerTest(fileName, packagePath, line, handler);
	}

	private void doDebuggerTest(String fileName, String packagePath, int line,
			IDebugEventHandler handler) throws DebugException, CoreException,
			Exception, InterruptedException {
		String pathToProject = getQualifiedName(fileName, packagePath);
		FixtureManager m_fixtureManager = isJsFile(fileName) ? FixtureUtils
				.setUpFixture(this, pathToProject) : FixtureUtils
				.setUpFixture(this);
		IBreakpoint breakpoint = createLineBreakpoint(getMainScriptPath(
				fileName, packagePath), line);
		setDebugEventHandler(handler);
		try {
			launchVjetAppDebug(fileName, packagePath,
					new IBreakpoint[] { breakpoint });
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	private boolean isJsFile(String fileName) {
		return fileName.endsWith(".js");
	}

	private String getQualifiedName(String fileName, String packagePath) {
		return packagePath + IPath.SEPARATOR + fileName;
	}

	private void resumeThread(IScriptThread thread, String name)
			throws DebugException {
		thread.resume();
		System.out.println("Resume suspend thread: " + name);
	}

}
