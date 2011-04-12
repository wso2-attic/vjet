/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.launching;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.environment.EnvironmentManager;
import org.eclipse.dltk.mod.core.environment.IEnvironment;
import org.eclipse.dltk.mod.launching.AbstractScriptLaunchConfigurationDelegate;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.IInterpreterInstallType; //import org.eclipse.dltk.mod.launching.InterpreterStandin;
import org.eclipse.dltk.mod.launching.ScriptRuntime;
import org.eclipse.dltk.mod.launching.ScriptRuntime.DefaultInterpreterEntry;

import org.ebayopensource.vjet.eclipse.core.VjoNature;

public class VjetLaunchConfigurationDelegate extends
		AbstractScriptLaunchConfigurationDelegate {
	
	protected String getNatureId(ILaunchConfiguration configuration) {
		String natureId = null;
		try {
			natureId = super.getNatureId(configuration);
		} catch (CoreException ce) {
			// TODO: log this exception
			ce.printStackTrace();
		}

		return natureId == null ? VjoNature.NATURE_ID : natureId;
	}

	public String getLanguageId() {
		return VjoNature.NATURE_ID;
	}

	public IInterpreterInstall getInterpreterInstall(
			ILaunchConfiguration configuration) throws CoreException {

		// fix bug http://quickbugstage.arch.ebay.com/show_bug.cgi?id=1496
		// setting default interpreter if it isn't exist

		IScriptProject script = ScriptRuntime.getScriptProject(configuration);
		IEnvironment env = EnvironmentManager.getEnvironment(script);
		DefaultInterpreterEntry interpreterEntry = new DefaultInterpreterEntry(
				getLanguageId(), env.getId());

		// gather interpreter install types for current nature
		IInterpreterInstallType[] installTypes = ScriptRuntime
				.getInterpreterInstallTypes(getLanguageId());
		Assert.isTrue(installTypes.length > 0,
				"There is no configured interpreter types!");
		
		IInterpreterInstallType installType = installTypes[0];
		IInterpreterInstall[] installs = installType.getInterpreterInstalls();
		Assert.isTrue(installs.length > 0,
				"There is no configured interpreter installations!");
		
		IInterpreterInstall install = installs[0];
		if (ScriptRuntime.getDefaultInterpreterInstall(interpreterEntry) == null) {
			// set the first installation as default one
			ScriptRuntime.setDefaultInterpreterInstall(install, null);
		}

		return super.getInterpreterInstall(configuration);
	}
}
