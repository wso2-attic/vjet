/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.launcher;

import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.dltk.mod.internal.debug.ui.launcher.AbstractScriptLaunchShortcut;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchConfigurationConstants;

public class VjetWebLaunchShortcut extends AbstractScriptLaunchShortcut {

	@Override
	protected String getNatureId() {
		return VjoNature.NATURE_ID;
	}

	@Override
	protected ILaunchConfigurationType getConfigurationType() {
		return getLaunchManager().getLaunchConfigurationType(
				VjetLaunchConfigurationConstants.ID_VJET_WEB_CONF_TYPE);
	}
}