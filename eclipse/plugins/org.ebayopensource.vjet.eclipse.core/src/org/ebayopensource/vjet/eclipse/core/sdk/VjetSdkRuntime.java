/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.sdk;

import org.ebayopensource.dsf.jst.ts.util.ISdkEnvironment;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuildpathEntry;

public class VjetSdkRuntime {

	public static ISdkEnvironmentType[] getSdkEnviromentTypes() {
		return new ISdkEnvironmentType[0];
	}

	public static boolean isContributedVMInstall(String sdkName) {
		return false;
	}

	public static ISdkEnvironment getDefaultSdkInstall() {
		// TODO Auto-generated method stub
		return null;
	}

	public static IBuildpathEntry getDefaultSdkContainerEntry() {
		return DLTKCore.newContainerEntry(newDefaultSdkContainerPath());
	}
	
	public static IBuildpathEntry getJsSdkContainerEntry() {
		return DLTKCore.newContainerEntry(jsSdkContainerPath());
	}

	private static IPath newDefaultSdkContainerPath() {
		return new Path(VjetPlugin.BROWSERSDK_ID).append(VjetPlugin.BROWSERSDK_LABEL);
	}
	
	private static IPath jsSdkContainerPath() {
		return new Path(VjetPlugin.JS_DEFAULT_SDK).append(VjetPlugin.JS_DEFAULT_SDK_LABEL);
	}

}
