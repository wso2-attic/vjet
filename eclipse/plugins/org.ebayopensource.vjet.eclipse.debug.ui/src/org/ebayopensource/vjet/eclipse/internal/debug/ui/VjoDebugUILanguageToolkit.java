/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import org.eclipse.dltk.mod.debug.ui.AbstractDebugUILanguageToolkit;
import org.eclipse.jface.preference.IPreferenceStore;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugConstants;
import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

public class VjoDebugUILanguageToolkit extends
		AbstractDebugUILanguageToolkit {

	/*
	 * @see
	 * org.eclipse.dltk.mod.debug.ui.IDLTKDebugUILanguageToolkit#getDebugModelId()
	 */
	public String getDebugModelId() {
		return VjetDebugConstants.DEBUG_MODEL_ID;
	}

	/*
	 * @see
	 * org.eclipse.dltk.mod.debug.ui.IDLTKDebugUILanguageToolkit#getPreferenceStore
	 * ()
	 */
	public IPreferenceStore getPreferenceStore() {
		return VjetDebugUIPlugin.getDefault().getPreferenceStore();
	}

	public String[] getVariablesViewPreferencePages() {
		return new String[] { "org.ebayopensource.vjet.eclipse.preferences.debug.detailFormatters" };
	}

}