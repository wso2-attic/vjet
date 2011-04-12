/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.mod.core.environment.IDeployment;
import org.eclipse.dltk.mod.core.environment.IEnvironment;
import org.eclipse.dltk.mod.core.environment.IFileHandle;
import org.eclipse.dltk.mod.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.mod.internal.launching.AbstractInterpreterInstallType;
import org.eclipse.dltk.mod.launching.EnvironmentVariable;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.LibraryLocation;
import org.osgi.framework.Bundle;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;

/**
 * Vjet implementation of a interpreter install type.
 */
public class GenericVjetInstallType extends AbstractInterpreterInstallType {

	private static final String INTERPRETER_BYTE_BYTE_CODE_RES_NAME = "org/mozilla/mod/classfile/ByteCode.class";

	private static final String INTERPRETER_BUNDLE = "org.ebayopensource.vjet.core.wrapper";

	@Override
	protected IPath createPathFile(IDeployment deployment) throws IOException {
		// this method should not be used
		throw new RuntimeException("This method should not be used");
	}

	@Override
	protected IInterpreterInstall doCreateInterpreterInstall(String id) {
		return new GenericVjetInstall(this, id);
	}

	public IStatus validateInstallLocation(IFileHandle installLocation) {
		return Status.OK_STATUS;
	}
	
	public LibraryLocation[] getDefaultLibraryLocations(
			IFileHandle installLocation, EnvironmentVariable[] variables,
			IProgressMonitor monitor) {

		Bundle bundle = Platform.getBundle(INTERPRETER_BUNDLE);

			try {

			String byteCodeFilePath = FileLocator.toFileURL(
					bundle.getResource(INTERPRETER_BYTE_BYTE_CODE_RES_NAME))
					.getPath();

			String libLocPath = byteCodeFilePath.substring(0, byteCodeFilePath
					.indexOf(INTERPRETER_BYTE_BYTE_CODE_RES_NAME));

				IEnvironment env = LocalEnvironment.getInstance();
			LibraryLocation location = new LibraryLocation(EnvironmentPathUtils
					.getFullPath(env, new Path(libLocPath)));

				return new LibraryLocation[] { location  };

		} catch (IOException ioe) {
			// TODO Log this error
			ioe.printStackTrace();
		}

		return new LibraryLocation[0];
	}

	@Override
	protected ILog getLog() {
		return VjetLaunchingPlugin.getDefault().getLog();
	}

	@Override
	protected String getPluginId() {
		return VjetLaunchingPlugin.PLUGIN_ID;
	}

	@Override
	protected String[] getPossibleInterpreterNames() {
		return new String[]{"vjo"};
	}

	public String getName() {
		return "Generic VJET Install Type";
	}

	public String getNatureId() {
		return VjoNature.NATURE_ID;
	}

}
