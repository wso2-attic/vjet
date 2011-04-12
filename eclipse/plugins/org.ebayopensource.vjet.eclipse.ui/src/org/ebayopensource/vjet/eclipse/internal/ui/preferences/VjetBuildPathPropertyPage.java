/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.mod.ui.preferences.BuildPathsPropertyPage;
import org.eclipse.dltk.mod.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.dltk.mod.ui.wizards.BuildpathsBlock;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class VjetBuildPathPropertyPage extends BuildPathsPropertyPage implements
		IWorkbenchPropertyPage {
	private static final String IJAVA_PROJECT_INTERFACE = "org.eclipse.jdt.core.IJavaProject";

	protected BuildpathsBlock createBuildPathBlock(
			IWorkbenchPreferenceContainer pageContainer) {
		return new VjetBuildpathBlock(new BusyIndicatorRunnableContext(), this,
				getSettings().getInt(INDEX), false, pageContainer);
	}

	@Override
	protected IProject getProject() {
		IProject project = super.getProject();
		if (project == null) {
			IAdaptable adaptable = getElement();
			if (isJavaProject(adaptable)) {
				project = (IProject) adaptable.getAdapter(IProject.class);
			}
		}
		return project;
	}

	private boolean isJavaProject(IAdaptable adaptable) {
		if (adaptable != null) {
			Class clazz = adaptable.getClass();
			Class[] interfaces = clazz.getInterfaces();
			for (Class interfaceClazz : interfaces) {
				if (IJAVA_PROJECT_INTERFACE.equals(interfaceClazz.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
