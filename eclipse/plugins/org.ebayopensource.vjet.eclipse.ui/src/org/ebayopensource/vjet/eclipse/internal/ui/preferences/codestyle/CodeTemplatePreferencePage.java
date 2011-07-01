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
package org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.internal.ui.preferences.PropertyAndPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Code Style -> Code Template
 * 
 * 
 *
 */
public class CodeTemplatePreferencePage extends PropertyAndPreferencePage {
	public static final String PREF_ID= "org.eclipse.vjet.ui.preferences.CodeTemplatePreferencePage"; //$NON-NLS-1$
	public static final String PROP_ID= "org.eclipse.vjet.ui.propertyPages.CodeTemplatePreferencePage"; //$NON-NLS-1$
	
	public static final String DATA_SELECT_TEMPLATE= "CodeTemplatePreferencePage.select_template"; //$NON-NLS-1$
	
	private CodeTemplateBlock fCodeTemplateConfigurationBlock;
	
	public CodeTemplatePreferencePage() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
		setTitle("------------------------");		 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		this.fCodeTemplateConfigurationBlock = new CodeTemplateBlock(getProject());
		super.createControl(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.internal.ui.preferences.PropertyAndPreferencePage#createPreferenceContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createPreferenceContent(Composite composite) {
		return this.fCodeTemplateConfigurationBlock.createContents(composite);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.internal.ui.preferences.PropertyAndPreferencePage#getPreferencePageId()
	 */
	@Override
	protected String getPreferencePageId() {
		return PREF_ID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.internal.ui.preferences.PropertyAndPreferencePage#getPropertyPageId()
	 */
	@Override
	protected String getPropertyPageId() {
		return PROP_ID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.internal.ui.preferences.PropertyAndPreferencePage#hasProjectSpecificOptions(org.eclipse.core.resources.IProject)
	 */
	@Override
	protected boolean hasProjectSpecificOptions(IProject project) {
//		return fCodeTemplateConfigurationBlock.hasProjectSpecificOptions(project);
		return false;
	}

}
