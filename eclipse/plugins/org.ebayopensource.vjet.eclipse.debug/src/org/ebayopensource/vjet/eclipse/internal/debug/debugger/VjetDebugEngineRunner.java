/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.debugger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.dltk.mod.core.PreferencesLookupDelegate;
import org.eclipse.dltk.mod.launching.DebuggingEngineRunner;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.InterpreterConfig;

import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;


public class VjetDebugEngineRunner extends DebuggingEngineRunner {

	public VjetDebugEngineRunner(IInterpreterInstall install) {
		super(install);

	}

	@Override
	public void run(InterpreterConfig config, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {

		initializeLaunch(launch, config,
				createPreferencesLookupDelegate(launch));

	}
	
	
	@Override
	protected InterpreterConfig addEngineConfig(InterpreterConfig config,
			PreferencesLookupDelegate delegate, ILaunch launch)
			throws CoreException {
		return config;
	}


	@Override
	protected String getDebuggingEngineId() {
		return VjetDebugPlugin.PLUGIN_ID;
	}

	@Override
	protected String getDebuggingEnginePreferenceQualifier() {
		return VjetDebugPlugin.PLUGIN_ID;
	}

	@Override
	protected String getDebugPreferenceQualifier() {
		return VjetDebugPlugin.PLUGIN_ID;
	}

	@Override
	protected String getLogFileNamePreferenceKey() {
		// TODO Auto-generated method stub
		return null;
	}

	protected InterpreterConfig addEngineConfig(InterpreterConfig config,
			PreferencesLookupDelegate delegate) throws CoreException {
		return null;
	}


}
