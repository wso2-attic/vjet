/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core;

import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceModuleFactory;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.ArchiveEntryFile;
import org.eclipse.dltk.mod.internal.core.ExternalEntryFile;
import org.eclipse.dltk.mod.internal.core.ExternalJSSourceModule;
import org.eclipse.dltk.mod.internal.core.ExternalSourceModule;
import org.eclipse.dltk.mod.internal.core.ScriptFolder;
import org.eclipse.dltk.mod.internal.core.VjoExternalSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

/**
 * 
 * 
 */
public class JSSourceModuleFactory implements ISourceModuleFactory {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.core.ISourceModuleFactory#createSourceModule(org.eclipse.dltk.mod.core.IScriptFolder,
	 *      java.lang.String, org.eclipse.dltk.mod.core.WorkingCopyOwner)
	 */
	public ISourceModule createSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner) {
//		TypeSpaceMgr.getInstance().waitUntilLoaded();
		return new VjoSourceModule(parent, name, owner);
	}

	public ExternalSourceModule createExternalSourceModule(ScriptFolder parent,
			String name, WorkingCopyOwner owner, boolean readOnly,
			IStorage storage) {
		if (storage instanceof ArchiveEntryFile) {
			return new VjoExternalSourceModule(parent, name, owner, readOnly,
					(ArchiveEntryFile)storage);
		} else if (storage instanceof ExternalEntryFile && Util.isNativeCacheDir(parent.getProjectFragment().getPath())){
			return new VjoExternalSourceModule(parent, name, owner, readOnly,
					storage);
	} else {
		return new ExternalJSSourceModule(parent, name, owner, readOnly,
				storage);
	}
	}
}
