/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import junit.framework.Assert;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;


/**
 * 
 * @author ddodd
 * 
 * The default dialog process is used to close any dialogs that popup
 * while running tests.
 *
 */
public class DefaultDialogProcessor implements IDialogProcessor {

	public void processDialog(Object dialog) {

		/**
		 * If this is a ProgressMonitorDialog, then ignore.  The ProgressMonitorDialog does
		 * not block the UI from running.
		 */
		if (dialog instanceof ProgressMonitorDialog) {
			return;
		}

		System.out.println("Processing dialog: " + dialog.getClass().getName());

		// Handle jface dialog
		if (dialog instanceof org.eclipse.jface.dialogs.Dialog) {
			org.eclipse.jface.dialogs.Dialog jfaceDialog = (org.eclipse.jface.dialogs.Dialog) dialog;
			jfaceDialog.close();
			return;
		}

		// Handle swt dialog
		if (dialog instanceof org.eclipse.swt.widgets.Dialog) {

			org.eclipse.swt.widgets.Dialog swtDialog = (org.eclipse.swt.widgets.Dialog) dialog;
			Assert
					.fail("org.eclipse.swt.widgets.Dialog is currently not supported");

			return;
		}

		// Handle dialogPage. These are typically some sort of wizard
		if (dialog instanceof DialogPage) {
			DialogPage dialogPage = (DialogPage) dialog;
			Assert.fail("DialogPage is currently not supported");
			return;
		}

	}

}
