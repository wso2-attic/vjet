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
package org.ebayopensource.vjet.eclipse.ui.viewsupport;

import org.ebayopensource.vjet.eclipse.ui.VjoElementImageProvider;
import org.ebayopensource.vjet.eclipse.ui.VjoElementLabels;
import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * 
 */
public class VjoLabelProvider extends AppearanceAwareLabelProvider {
	
	private ScriptElementLabels m_scriptElementLabels = null;

	/**
	 * @param textFlags
	 * @param imageFlags
	 * @param store
	 */
	public VjoLabelProvider(long textFlags, int imageFlags,
			IPreferenceStore store) {

		this(textFlags, imageFlags, store, new VjoElementLabels());
	}

	public VjoLabelProvider(long textFlags, int imageFlags,
			IPreferenceStore store, VjoElementLabels labels) {

		super(textFlags, imageFlags, store);
		
		m_scriptElementLabels = labels;
		fImageLabelProvider = new VjoElementImageProvider();

	}
	
	@Override
	public String getText(Object element) {
		
		String result = m_scriptElementLabels.getTextLabel(element,
				evaluateTextFlags(element));
		
		if (result.length() == 0 && (element instanceof IStorage)) {
			result = fStorageLabelProvider.getText(element);
		}

		return decorateText(result, element);
	}
}
