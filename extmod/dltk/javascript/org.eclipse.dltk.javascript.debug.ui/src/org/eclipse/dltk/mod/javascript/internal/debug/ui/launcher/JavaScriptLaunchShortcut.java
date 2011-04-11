/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.htmls
 ******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.debug.ui.launcher;

import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.dltk.mod.internal.debug.ui.launcher.AbstractScriptLaunchShortcut;
import org.eclipse.dltk.mod.javascript.core.JavaScriptNature;
import org.eclipse.dltk.mod.javascript.launching.JavaScriptLaunchConfigurationConstants;

public class JavaScriptLaunchShortcut extends AbstractScriptLaunchShortcut {
	protected ILaunchConfigurationType getConfigurationType() {
		return getLaunchManager().getLaunchConfigurationType(
				JavaScriptLaunchConfigurationConstants.ID_JAVASCRIPT_SCRIPT);
	}

	protected String getNatureId() {
		return JavaScriptNature.NATURE_ID;
	}
}
