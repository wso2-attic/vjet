/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.ui.filters;


import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.team.core.RepositoryProvider;


/**
 * Filters non-shared projects and Script projects. Non-shared projects are
 * projects that are not controlled by a team provider.
 * 
	 *
 */
public class NonSharedProjectFilter extends ViewerFilter {

	/*
	 * @see ViewerFilter
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IProject)
			return isSharedProject((IProject)element);
		
		if (element instanceof IScriptProject)
			return isSharedProject(((IScriptProject)element).getProject());

		return true;
	}
	
	private boolean isSharedProject(IProject project) {
		return !project.isAccessible() || RepositoryProvider.isShared(project);
	}
}
