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
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.mod.core.PreferencesLookupDelegate;
import org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab;
import org.eclipse.dltk.mod.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import org.ebayopensource.vjet.eclipse.core.VjoNature;

/**
 * 
 *
 */
public class HtmlMainTab extends MainLaunchConfigurationTab {
	
	public HtmlMainTab(String mode) {
		super (mode);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab#createMainModuleEditor(org.eclipse.swt.widgets.Composite, java.lang.String)
	 */
	@Override
	protected void createMainModuleEditor(Composite parent, String text) {
		Group mainGroup = new Group(parent, SWT.NONE);
		mainGroup.setText("Main Class");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		mainGroup.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		mainGroup.setLayout(layout);

		Text mainClassText = new Text(mainGroup, SWT.SINGLE);
		mainClassText.setText("org.ebayopensource.vjo.runner.VjoRunner");
		mainClassText.setEditable(false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		mainClassText.setData(gridData);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab#doInitializeForm(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	protected void doInitializeForm(ILaunchConfiguration config) {
//		super.doInitializeForm(config);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab#validateScript()
	 */
	@Override
	protected boolean validateScript() {
		return true;
	}
	
	/**
	 * get selected project
	 * @return
	 */
	public IProject getSelectedProject() {
		String projectName = super.getProjectName();
		if (projectName == null)
			return null;
		
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.MainLaunchConfigurationTab#doPerformApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	protected void doPerformApply(ILaunchConfigurationWorkingCopy config) {
		String project = this.getProjectName();
		config.setAttribute(
				ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME, project);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab#breakOnFirstLinePrefEnabled(org.eclipse.dltk.mod.core.PreferencesLookupDelegate)
	 */
	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab#dbpgLoggingPrefEnabled(org.eclipse.dltk.mod.core.PreferencesLookupDelegate)
	 */
	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab#getNatureID()
	 */
	@Override
	protected String getNatureID() {
		return VjoNature.NATURE_ID;
	}

}
