/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.DLTKCore;

/**
 * Job to loading changed types to type space.
 * 
 * 
 *
 */
public class TypeSpaceRefreshJob extends WorkspaceJob {

	private static final String TYPE_SPACE_REFRESH = "Type space refresh";

	public TypeSpaceRefreshJob() {
		super(TYPE_SPACE_REFRESH);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
		try {
			mgr.refresh(new EclipseTypeLoadMonitor(monitor), null);
		} catch (Exception e) {
			DLTKCore.error(e.getMessage(), e);
		}
		return Status.OK_STATUS;
	}

}
