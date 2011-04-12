/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.net.URI;
import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.dbgp.IDbgpSession;
import org.eclipse.dltk.mod.debug.core.DLTKDebugConstants;
import org.eclipse.dltk.mod.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.ScriptFolder;
import org.eclipse.dltk.mod.internal.core.ScriptProject;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptStackFrame;
import org.eclipse.dltk.mod.internal.launching.LaunchConfigurationUtils;
import org.eclipse.dltk.mod.launching.sourcelookup.Messages;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjetDBGPSourceLookupParticipant extends
		AbstractSourceLookupParticipant implements ISourceLookupParticipant {

	public String getSourceName(Object object) throws CoreException {
		if (!(object instanceof ScriptStackFrame)) {
			return null;
		}
		ScriptStackFrame frame = (ScriptStackFrame) object;

		URI uri = frame.getFileName();
		if (DLTKDebugConstants.DBGP_SCHEME.equals(uri.getScheme())) { //$NON-NLS-1$
			return MessageFormat.format(
					Messages.DBGPSourceLookupParticipant_debugResource,
					new Object[] { uri.getPath() });
		}
		return uri.toString();
	}

	public Object[] findSourceElements(Object object) throws CoreException {
		if (!(object instanceof ScriptStackFrame) && !(object instanceof URI)) {
			return new Object[0];
		}
		
		URI uri = null;
		IDbgpSession session = null;
		if(object instanceof ScriptStackFrame){
			ScriptStackFrame frame = (ScriptStackFrame) object;
			uri = frame.getFileName();
			session = frame.getScriptThread().getDbgpSession();
		}else{
			uri = (URI) object;
		}
		
		IProject project = getProject();
		ScriptProject scriptProject = (ScriptProject) DLTKCore.create(project);

		IScriptFolder[] folders = scriptProject.getScriptFolders();
		if (folders.length == 0) {
			return new Object[0];
		}
		IScriptFolder folder = folders[0];

		if (!(DLTKDebugConstants.DBGP_SCHEME.equals(uri.getScheme()))) { //$NON-NLS-1$
			return null;
		}
		return new Object[] { new VjoDBGPSourceModule((ScriptFolder) folder,
				uri.getPath(), DefaultWorkingCopyOwner.PRIMARY, true,
				new VjoDBGPSourceStorage(uri, session)) };
	}

	private IProject getProject() {
		ILaunchConfiguration launchConfiguration = this.getDirector()
				.getLaunchConfiguration();

		IProject project = LaunchConfigurationUtils
				.getProject(launchConfiguration);
		return project;
	}
}
