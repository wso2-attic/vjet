/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.actions;

import org.ebayopensource.vjet.eclipse.core.PiggyBackClassPathUtil;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class update vjet build path from java class path.
 * 
 * 
 * 
 */
public class UpdateVjetBuildPathFromJavaClasspathAction implements
		IExecutableExtension, IObjectActionDelegate {

	ISelection selection;

	public UpdateVjetBuildPathFromJavaClasspathAction() {
	}

	public void run(IAction action) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.isEmpty() || structuredSelection.size() > 1) {
			return;
		}
		Object selectionElement = structuredSelection.getFirstElement();
		if (selectionElement instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) selectionElement;
			IProject project = (IProject) adaptable.getAdapter(IProject.class);
			if (project != null) {
				doUpdate(project);
			}
		}
	}

	private void doUpdate(IProject project) {
		PiggyBackClassPathUtil.initializeScriptProjectFromJavProject(DLTKCore.create(project));
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		IAdaptable adaptable = (IAdaptable) ((IStructuredSelection) selection)
				.getFirstElement();

		// Modify by Eirc.MA on 20090623
		if (adaptable == null)
			return;
		// End of modification
		IProject project = (IProject) adaptable.getAdapter(IProject.class);

		boolean hasVJONature = this.hasVJONature(project);
		boolean hasVJOBuilder = this.hasVJOBuilder(project);
		if (hasVJONature && hasVJOBuilder) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}

	private boolean hasVJONature(IProject project) {
		final String builderID = "org.eclipse.dltk.mod.core.scriptbuilder";
		try {
			IProjectDescription description = project.getDescription();
			ICommand[] buildCommands = description.getBuildSpec();
			for (int i = 0; i < buildCommands.length; i++) {
				if (builderID.equals(buildCommands[i].getBuilderName()))
					return true;
			}
			return false;
		} catch (CoreException e) {
			return false;
		}
	}

	private boolean hasVJOBuilder(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			return description.hasNature(VjoNature.NATURE_ID);
		} catch (CoreException e) {
			return false;
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		// TODO Auto-generated method stub
	}
}
