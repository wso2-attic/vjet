/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.dltk.mod.core.environment.EnvironmentManager;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptStackFrame;
import org.eclipse.dltk.mod.internal.launching.LaunchConfigurationUtils;

import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjetSourceLookupParticipant extends
		AbstractSourceLookupParticipant {

	@Override
	public Object[] findSourceElements(Object element) throws CoreException {
		Object source = null;
		URI uri = null;

		if (element instanceof ScriptStackFrame) {
			ScriptStackFrame sf = (ScriptStackFrame) element;
			uri = sf.getFileName();
		} else if (element instanceof URI) {
			uri = (URI) element;
		}

		if (uri == null) {
			return new Object[0];
		}
		uri = uri.normalize();
		String scheme = uri.getScheme();
		if (LauncherUtil.isFileScheme(scheme)) {
			source = getSourceFromFileURI(uri);
		} else if (LauncherUtil.isZipScheme(scheme)) {
			if (source == null) {
				try {
					source = LauncherUtil.createZipEntryFile(uri);
				} catch (Exception e) {
					VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
				}
			}
		}
		if (source != null) {
			return new Object[] { source };
		}

		return new Object[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant#getSourceName
	 * (java.lang.Object)
	 */
	@Override
	public String getSourceName(Object object) throws CoreException {
		ScriptStackFrame frame = (ScriptStackFrame) object;

		String path = frame.getFileName().getPath();
		if (path.length() == 0) {
			return null;
		}

		String root = getProjectRoot();

		// strip off the project root
		if (path.indexOf(root) != -1) {
			return path.substring(root.length() + 1);
		}

		IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
				.findFilesForLocation(new Path(path));

		IProject project = getProject();
		for (int i = 0; i < files.length; i++) {
			IFile file = files[i];
			if (file.exists()) {
				if (file.getProject().equals(project)) {
					return file.getProjectRelativePath().toString();
				}
			}
		}
		return path;
	}

	protected String getProjectRoot() throws CoreException {
		IProject project = getProject();
		return project.getLocationURI().getPath();
	}

	private IProject getProject() {
		return LaunchConfigurationUtils.getProject(getDirector()
				.getLaunchConfiguration());
	}

	private Object getSourceFromFileURI(URI uri) {
		String pathname = uri.getPath();
		try {
			pathname = uri.toURL().getPath();
		} catch (MalformedURLException e) {
			VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
		}
		if (pathname == null) {
			return null;
		}
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			pathname = pathname.substring(1);
		}

		return findFileElement(pathname);
	}

	public static Object findFileElement(String path) {

		File file = new File(path);

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IContainer container = root.getContainerForLocation(new Path(file
				.getParent()));

		if (container != null) {
			IResource resource = container.findMember(file.getName());

			if (resource instanceof IFile) {
				return resource;
			}

		} else {

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath location = Path.fromOSString(file.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFile(location);
			if (ifile.exists()) {
				return ifile;
			} else if (file.exists()) {
				return EnvironmentPathUtils.getFile(EnvironmentManager
						.getLocalEnvironment(), location);
			}
		}
		return null;
	}
}
