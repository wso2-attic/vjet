/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;

import org.eclipse.dltk.mod.javascript.internal.launching.JavaScriptInterpreterRunner;

import org.eclipse.dltk.mod.launching.AbstractInterpreterRunner;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.InterpreterConfig;

public class VjetDelegateJSInterpreterRunner extends AbstractInterpreterRunner{
	
	public VjetDelegateJSInterpreterRunner(IInterpreterInstall install) {
		super(install);
	}

	public void run(InterpreterConfig config, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		doRunImpl(config, launch);
	}

	public static void doRunImpl(InterpreterConfig config, ILaunch launch)
		throws CoreException {
		
		// delegate call to java script interpreter runner
		JavaScriptInterpreterRunner.doRunImpl(config, launch,
				JavaScriptInterpreterRunner.DEFAULT_CONFIG);
		
	}

	
}
