/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.eclipse.dltk.mod.ui.wizards.NewPackageCreationWizard;
import org.eclipse.dltk.mod.ui.wizards.NewPackageWizardPage;

/**
 * VJET wizard for creating a new vjo package.
 * 
 */
public class VjoPackageCreationWizard extends NewPackageCreationWizard {

	
	public VjoPackageCreationWizard() {
		super();
		setWindowTitle(VjetWizardMessages.PackageCreationWizard_title);
	}

	@Override
	protected NewPackageWizardPage createNewPackageWizardPage() {
		return new VjoNewPackageWizardPage();
	}

}
