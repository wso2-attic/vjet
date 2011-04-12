/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.interpreters;

import org.eclipse.dltk.mod.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.mod.internal.debug.ui.interpreters.InterpretersBlock;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.ScriptRuntime;

import org.ebayopensource.vjet.eclipse.core.VjoNature;

public class VjetInterpreterBlock extends InterpretersBlock {

	protected AddScriptInterpreterDialog createInterpreterDialog(
			IInterpreterInstall standin) {

		return new AddVjetInterpreterDialog(this, getShell(), ScriptRuntime.
				getInterpreterInstallTypes(getCurrentNature()), standin);
	}

	@Override
	protected String getCurrentNature() {
		return VjoNature.NATURE_ID;
	}

}
