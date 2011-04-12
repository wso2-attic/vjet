/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AbstractInterpreterLibraryBlock;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.LibraryLabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IBaseLabelProvider;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

public class VjetInterpreterLibraryBlock extends AbstractInterpreterLibraryBlock{

	public VjetInterpreterLibraryBlock(AddScriptInterpreterDialog dialog) {
		super(dialog);
	}
	
	@Override
	protected IDialogSettings getDialogSettions() {
		return VjetDebugUIPlugin.getDefault().getDialogSettings();
	}

	@Override
	protected IBaseLabelProvider getLabelProvider() {
		return new LibraryLabelProvider();
	}

}
