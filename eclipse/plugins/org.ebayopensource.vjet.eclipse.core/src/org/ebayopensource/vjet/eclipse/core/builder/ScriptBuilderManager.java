/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.builder.IScriptBuilder;
import org.eclipse.dltk.mod.utils.NatureExtensionManager;

public class ScriptBuilderManager {

	private final static String LANGUAGE_EXTPOINT = DLTKCore.PLUGIN_ID
			+ ".builder"; //$NON-NLS-1$

	private static NatureExtensionManager manager = null;

	private synchronized static NatureExtensionManager getManager() {
		if (manager == null) {
			manager = new NatureExtensionManager(LANGUAGE_EXTPOINT,
					IScriptBuilder.class, "#"); //$NON-NLS-1$
		}
		return manager;
	}

	/**
	 * Return merged with all elements with nature #
	 * 
	 * @param natureId
	 * @return
	 * @throws CoreException
	 */
	public static IScriptBuilder[] getScriptBuilders(String natureId) {
		return (IScriptBuilder[]) getManager().getInstances(natureId);
	}

	public static IScriptBuilder[] getAllScriptBuilders() {
		return (IScriptBuilder[]) getManager().getAllInstances();
	}
}
