/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class VjetProblemSeveritiesPreferencePage extends
AbstractConfigurationBlockPropertyAndPreferencePage {

	private static VjetProblemSeveritiesConfigurationBlock fConfigurationBlock;
	public static final String PREF_ID = "org.ebayopensource.vjet.eclipse.ui.preferences.ProblemSeveritiesPreferencePage";
	public static final String PROP_ID = "org.ebayopensource.vjet.eclipse.ui.propertyPages.ProblemSeveritiesPreferencePage";

	public VjetProblemSeveritiesPreferencePage() {
		setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
		setTitle("Problem Severities");
	}
	
	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener changeListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		fConfigurationBlock = new VjetProblemSeveritiesConfigurationBlock(
				getNewStatusChangedListener(), getProject(), container);
		return fConfigurationBlock;
	}

	@Override
	protected String getPreferencePageId() {
		return PREF_ID;
	}

	@Override
	protected String getPropertyPageId() {
		return PROP_ID;
	}

	// public static String getDuplicateFieldValue(){
	// if(fConfigurationBlock== null)
	// return "";
	// return
	// fConfigurationBlock.getValue(VjetProblemSeveritiesConfigurationBlock.PREF_PB_DuplicateField);
	//		
	// }
	//	
	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performDefaults()
	 */
	// protected void performDefaults() {
	// super.performDefaults();
	// if (fConfigurationBlock != null) {
	// fConfigurationBlock.performDefaults();
	// }
	// }
	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */


	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setPreferenceStore() {
		// TODO Auto-generated method stub
		
	}

}
