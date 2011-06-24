/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.internal.ui.text.folding.VjetFoldingPreferenceBlock;
import org.eclipse.dltk.mod.ui.preferences.FoldingConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.mod.ui.text.folding.IFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;

/**
 * Configures VJET Script Editor folding preferences.
 * 
 * 
 *
 */
public class VjetFoldingConfigurationBlock extends FoldingConfigurationBlock {

	public VjetFoldingConfigurationBlock(OverlayPreferenceStore store,
			PreferencePage p) {
		super(store, p);
	}

	protected IFoldingPreferenceBlock createFoldingPreferenceBlock() {
		return new VjetFoldingPreferenceBlock(fStore);
	}
}
