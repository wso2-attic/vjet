/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.typehierarchy;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoOpenTypeSelectionDialog;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoTypeSelectionDialog;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Refocuses the type hierarchy on a type selection from a all types dialog.
 */
public class FocusOnTypeAction extends Action {

	private VJOTypeHierarchyViewPart fViewPart;

	public FocusOnTypeAction(VJOTypeHierarchyViewPart part) {
		super(TypeHierarchyMessages.FocusOnTypeAction_label);
		setDescription(TypeHierarchyMessages.FocusOnTypeAction_description);
		setToolTipText(TypeHierarchyMessages.FocusOnTypeAction_tooltip);

		fViewPart = part;
	}

	public void run() {
		Shell parent = fViewPart.getSite().getShell();
		VjoOpenTypeSelectionDialog dialog = new VjoOpenTypeSelectionDialog(
				parent, true, PlatformUI.getWorkbench().getProgressService(),
				null, IDLTKSearchConstants.TYPE, DLTKUILanguageManager
						.getLanguageToolkit(VjoNature.NATURE_ID));

		dialog.setTitle(TypeHierarchyMessages.FocusOnTypeAction_dialog_title);
		dialog
				.setMessage(TypeHierarchyMessages.FocusOnTypeAction_dialog_message);
		// Add by Oliver. Reset the shown type flag to all type.
		VjoTypeSelectionDialog.typeFlag = VjoOpenTypeSelectionDialog.SHOWALLTYPE;

		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		Object[] types = dialog.getResult();
		if (types != null && types.length > 0) {
			IType type = (IType) types[0];
			fViewPart.setInputElement(type);
		}
	}
}
