/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.mod.debug.ui.display;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.dltk.mod.console.IScriptInterpreter;
import org.eclipse.dltk.mod.debug.core.DLTKDebugPlugin;
import org.eclipse.dltk.mod.debug.core.model.IScriptDebugElement;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.PageSite;
import org.eclipse.ui.part.ViewPart;

public class ScriptDisplayView extends ViewPart implements IConsoleView,
		ISelectionListener, IDebugEventSetListener {

	private DebugConsole console;

	private PageSite pageSite;
	private IPageBookViewPage page;

	// EBAY MOD START
	private DebugScriptInterpreter debugScriptInterpreter;
	private JDTScriptInterpreterAdapter jdtScriptInterpreter;

	// EBAY MOD END

	public void init(IViewSite site) throws PartInitException {
		// EBAY MOD START
		super.init(site);
		debugScriptInterpreter = new DebugScriptInterpreter(this);
		jdtScriptInterpreter = new JDTScriptInterpreterAdapter();
		console = new DebugConsole(Messages.ScriptDisplayView_consoleName,
				DebugConsole.class.getName(), debugScriptInterpreter);
		page = console.createPage(this);
		pageSite = new PageSite(getViewSite());
		page.init(pageSite);
		getSite().getWorkbenchWindow().getSelectionService()
				.addPostSelectionListener(this);
		DebugPlugin.getDefault().addDebugEventListener(this);
		// EBAY MOD END
	}

	public void dispose() {
		// EBAY MOD START
		getSite().getWorkbenchWindow().getSelectionService()
				.removePostSelectionListener(this);
		DebugPlugin.getDefault().removeDebugEventListener(this);
		// EBAY MOD END
		if (page != null) {
			page.dispose();
			page = null;
		}
		if (console != null) {
			console.dispose();
			console = null;
		}
		super.dispose();
	}

	public void createPartControl(Composite parent) {
		IToolBarManager toolBarManager = pageSite.getActionBars()
				.getToolBarManager();
		toolBarManager.add(new GroupMarker(IConsoleConstants.OUTPUT_GROUP));
		toolBarManager.add(new GroupMarker(IConsoleConstants.LAUNCH_GROUP));
		page.createControl(parent);
		((SubActionBars) pageSite.getActionBars()).activate();
	}

	public void setFocus() {
		page.setFocus();
	}

	public void display(IConsole console) {
		// NOP
	}

	public IConsole getConsole() {
		return console;
	}

	public boolean getScrollLock() {
		return false;
	}

	public boolean isPinned() {
		return false;
	}

	public void pin(IConsole console) {
		// NOP
	}

	public void setPinned(boolean pin) {
		// NOP
	}

	public void setScrollLock(boolean scrollLock) {
		// NOP
	}

	public void warnOfContentChange(IConsole console) {
		// NOP
	}

	// EBAY MOD START
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		handleSelectionChangedEvent(part, selection);
	}

	private void handleSelectionChangedEvent(IWorkbenchPart part,
			ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (!(element instanceof IDebugElement)) {
			return;
		}

		setInterpreterByDebugElement(element);
	}

	private void setInterpreterByDebugElement(Object element) {
		IScriptInterpreter interpreter = null;
		if (element instanceof IScriptDebugElement) {
			interpreter = debugScriptInterpreter;
		} else if (element instanceof IJavaThread) {
			IJavaThread javaThread = (IJavaThread) element;
			try {
				if (javaThread.isSuspended() && javaThread.hasStackFrames()) {
					interpreter = jdtScriptInterpreter;
					jdtScriptInterpreter
							.setStackFrame((IJavaStackFrame) javaThread
									.getStackFrames()[0]);
				}
			} catch (DebugException e) {
				DLTKDebugPlugin.log(e);
			}
		} else if (element instanceof IJavaStackFrame) {
			interpreter = jdtScriptInterpreter;
			jdtScriptInterpreter.setStackFrame((IJavaStackFrame) element);
		}
		if (interpreter != null) {
			console.setInterpreter(interpreter);
		}
	}

	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			switch (events[i].getKind()) {
			case DebugEvent.SUSPEND:
				switch (events[i].getDetail()) {
				case DebugEvent.BREAKPOINT:
				case DebugEvent.STEP_END:
					setInterpreterByDebugElement(events[i].getSource());
					break;
				}
				break;
			default:
				break;
			}
		}
	}
	// EBAY MOD END
}
