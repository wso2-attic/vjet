/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/

package org.eclipse.dltk.mod.internal.debug.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.debug.core.model.IExpression;
import org.eclipse.debug.ui.DebugPopup;
import org.eclipse.debug.ui.InspectPopupDialog;
import org.eclipse.dltk.mod.debug.core.eval.EvaluatedScriptExpression;
import org.eclipse.dltk.mod.debug.core.eval.IScriptEvaluationResult;
import org.eclipse.dltk.mod.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;

public class PopupScriptInspectAction extends ScriptInspectAction {
	private class ScriptInspectPopupDialog extends InspectPopupDialog {
		private IHandler fCloseHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event)
					throws ExecutionException {
				persist();
				close();
				return null;
			}
		};

		private IHandlerActivation fActivation;
		private IHandlerService fHandlerService;

		/**
		 * @param shell
		 * @param anchor
		 * @param commandId
		 * @param expression
		 */
		public ScriptInspectPopupDialog(Shell shell, Point anchor,
				String commandId, IExpression expression) {
			super(shell, anchor, commandId, expression);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.debug.ui.DebugPopup#open()
		 */
		public int open() {
			int result = super.open();

			IWorkbench workbench = PlatformUI.getWorkbench();

			/**
			 * fix bug 3328, temporarily sign up a command handler for JDT
			 * inspect command. for bug 3328, when second press 'Ctrl-Shift-I',
			 * the context is 'In Dialog and Window', it will invoke the JDT
			 * inspect command. because, the context for our script inspect
			 * command binding is "Debug Script". So, to fix the bug,
			 * temporarily sign up a command handler for JDT inspect command.
			 * 
			 */

			String JDTCommandId = "org.eclipse.jdt.debug.ui.commands.Inspect";
			ICommandService commandService = (ICommandService) workbench
					.getAdapter(ICommandService.class);

			if (commandService.getCommand(JDTCommandId) != null) {
				this.fHandlerService = (IHandlerService) workbench
						.getAdapter(IHandlerService.class);
				this.fActivation = this.fHandlerService.activateHandler(
						JDTCommandId, this.fCloseHandler);
			}
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.debug.ui.InspectPopupDialog#close()
		 */
		public boolean close() {
			if (this.fActivation != null && this.fHandlerService != null)
				fHandlerService.deactivateHandler(this.fActivation);

			return super.close();
		}
	}

	/**
	 * specify the inspect command ID to fix bug 3328
	 */
	private static final String INSPECT_COMMAND_ID = "org.eclipse.dltk.mod.debug.ui.commands.ScriptInspect";

	private void showPopup(StyledText textWidget, IExpression expression) {
		DebugPopup displayPopup = new ScriptInspectPopupDialog(getShell(),
				getPopupAnchor(textWidget), INSPECT_COMMAND_ID, expression);
		displayPopup.open();
	}

	protected void displayResult(final IScriptEvaluationResult result) {
		IWorkbenchPart part = getPart();
		final StyledText styledText = getStyledText(part);

		if (styledText != null) {
			final IExpression expression = new EvaluatedScriptExpression(result);
			DLTKDebugUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
				public void run() {
					showPopup(styledText, expression);
				}
			});
		}

		evaluationCleanup();
	}

}
