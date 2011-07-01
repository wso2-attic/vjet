/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.ui.actions;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoOpenTypeSelectionDialog;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoTypeSelectionDialog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.actions.OpenTypeAction;
import org.eclipse.dltk.mod.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This action calls {@link VjoOpenTypeSelectionDialog#open()} method.
 * 
 * 
 * 
 */
public class VjoOpenTypeAction extends OpenTypeAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.actions.OpenTypeAction#getUILanguageToolkit()
	 */
	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return DLTKUILanguageManager.getLanguageToolkit(VjoNature.NATURE_ID);
	}

	public void run() {
		Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
		VjoOpenTypeSelectionDialog dialog = new VjoOpenTypeSelectionDialog(parent, true,
				PlatformUI.getWorkbench().getProgressService(), null,
				IDLTKSearchConstants.TYPE, this.getUILanguageToolkit());
		dialog.setTitle(getOpenTypeDialogTitle());
		dialog.setMessage(getOpenTypeDialogMessage());
		
		// Add by Oliver. Reset the shown type flag to all type.
		VjoTypeSelectionDialog.typeFlag=VjoOpenTypeSelectionDialog.SHOWALLTYPE;
		
		int result = dialog.open();
		if (result != IDialogConstants.OK_ID)
			return;

		Object[] types = dialog.getResult();
		if (types != null && types.length > 0) {
			IModelElement type = null;
			for (int i = 0; i < types.length; i++) {
				type = (IModelElement) types[i];
				try {
					DLTKUIPlugin.openInEditor(type, true, true);
				} catch (CoreException x) {
					ExceptionHandler.handle(x, getOpenTypeErrorTitle(),
							getOpenTypeErrorMessage());
				}
			}
		}
	}
}
