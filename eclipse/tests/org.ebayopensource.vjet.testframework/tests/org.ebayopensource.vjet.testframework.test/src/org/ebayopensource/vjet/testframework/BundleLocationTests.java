/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class BundleLocationTests extends TestCase {

	public BundleLocationTests() {
		// TODO Auto-generated constructor stub
	}

	public BundleLocationTests(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void testFeatureBundleId() {
		
		String platformLocation = Platform.getInstallLocation().getURL().toString();
		System.out.println(platformLocation);
		
		String fragmentId = "org.ebayopensource.vjet.testframework.test";
		String hostPluginId  = "org.ebayopensource.vjet.testframework";
		
		Bundle fragmentBundle = Platform.getBundle(fragmentId);
		Bundle hostPluginBundle = Platform.getBundle(hostPluginId);

		
		
		assertNotNull(hostPluginBundle);
		assertNotNull(fragmentBundle);
		
	}


}
