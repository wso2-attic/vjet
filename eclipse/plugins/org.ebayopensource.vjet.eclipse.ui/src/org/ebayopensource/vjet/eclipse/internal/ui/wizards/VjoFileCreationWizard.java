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
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.dltk.mod.javascript.ui.JavaScriptImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;

/**
 * VJET wizard for creating a new vjo file.
 *
 */
public class VjoFileCreationWizard extends VjoSourceModuleWizard {

	public VjoFileCreationWizard() {
		setDefaultPageImageDescriptor(JavaScriptImages.DESC_WIZBAN_PROJECT_CREATION);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(VjetWizardMessages.FileCreationWizard_title);
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoSourceModuleWizard#createVjoSourceModulePage()
	 */
	protected VjoSourceModulePage createVjoSourceModulePage() {
		return new VjoSourceModulePage() {

			protected String getRequiredNature() {
				return VjoNature.NATURE_ID;
			}

			protected String getPageDescription() {
				return VjetWizardMessages.FileCreationWizard_page_description;
			}

			protected String getPageTitle() {
				return VjetWizardMessages.FileCreationWizard_page_title;
			}
		};
	}
}
