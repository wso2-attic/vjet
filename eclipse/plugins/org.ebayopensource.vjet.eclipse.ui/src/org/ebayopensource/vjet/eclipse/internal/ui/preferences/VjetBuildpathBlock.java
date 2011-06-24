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
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.wizards.BuildpathsBlock;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * 
 * 
 */
public class VjetBuildpathBlock extends BuildpathsBlock {

	public VjetBuildpathBlock(IRunnableContext runnableContext,
			IStatusChangeListener context, int pageToShow, boolean useNewPage,
			IWorkbenchPreferenceContainer pageContainer) {
		super(runnableContext, context, pageToShow, useNewPage, pageContainer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.BuildpathsBlock#getPreferenceStore()
	 */
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return VjetUIPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.BuildpathsBlock#supportZips()
	 */
	@Override
	protected boolean supportZips() {
		return true;
	}
}
