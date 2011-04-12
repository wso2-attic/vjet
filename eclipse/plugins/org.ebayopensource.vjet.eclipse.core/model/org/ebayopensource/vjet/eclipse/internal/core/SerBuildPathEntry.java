/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.IAccessRule;
import org.eclipse.dltk.mod.core.IBuildpathAttribute;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.internal.core.BuildpathEntry;

public class SerBuildPathEntry extends BuildpathEntry implements
		IBuildpathEntry {
	/**
	 * Entry kind constant describing a buildpath entry identifying a
	 * library. A library is a ...
	 */
	int BPE_SER = 6;
	public final static String VJET_SER_STR = "#VJET#Ser#"; //$NON-NLS-1$
	public final static IPath VJET_SER_ENTRY = new Path(VJET_SER_STR);
	public final static String SERDEVICE = "VJET.SER/SER:";
	
	public SerBuildPathEntry(int contentKind, int entryKind, IPath path,
			boolean isExported, IPath[] inclusionPatterns,
			IPath[] exclusionPatterns, IAccessRule[] accessRules,
			boolean combineAccessRules, IBuildpathAttribute[] extraAttributes,
			boolean externalLib) {
		super(contentKind, entryKind, path.setDevice(SERDEVICE), isExported, inclusionPatterns,
				exclusionPatterns, accessRules, combineAccessRules, extraAttributes,
				externalLib);
	}
	
	@Override
	public IPath getPath() {
		return super.getPath();
	}

}
