/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.debugger;

import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.launching.IInterpreterRunner;
import org.eclipse.dltk.mod.launching.IInterpreterRunnerFactory;






public class VjetDebuggerRunnerFactory implements IInterpreterRunnerFactory{

	public IInterpreterRunner createRunner(IInterpreterInstall install) {
		return new VjetDebuggerRunner(install);
	}

}
