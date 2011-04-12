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
package org.ebayopensource.vjet.eclipse.ui.actions.nature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * 
 *
 */
public class AddVjoNaturePolicyManager {
	private static final IAddVjoNaturePolicy defaultPolicy = new DefaultAddVjoNaturePolicy();
	
	private List<IAddVjoNaturePolicy> polices = new ArrayList<IAddVjoNaturePolicy>();
	private static AddVjoNaturePolicyManager instance;
	
	private static final String EXTENSION_POINT_ID = "org.ebayopensource.vjet.eclipse.ui.addVjoNaturePolicy";
	
	/**
	 * the marker for whether the extensions have been loaded or not
	 */
	private boolean extensionAdded = false;
	
	/**
	 * singleton
	 */
	private AddVjoNaturePolicyManager() {}
	
	/**
	 * get the singleton instance
	 * 
	 * @return
	 */
	public static AddVjoNaturePolicyManager getInstance() {
		if (instance == null)
			instance = new AddVjoNaturePolicyManager();
		return instance;
	}
	
	/**
	 * get the corresponding adding nature policy for the project
	 * 
	 * @param project eclipse resource project
	 * @return if not corresponding policy, return the default policy implementation (DefaultAddVjoNaturePolicy).
	 */
	public IAddVjoNaturePolicy getPolicy(IProject project) {
		if (!extensionAdded)
			this.addExtensions();
		
		for (Iterator<IAddVjoNaturePolicy> iterator = this.polices.iterator(); iterator.hasNext();) {
			IAddVjoNaturePolicy policy = iterator.next();
			if (policy.accept(project))
				return policy;
		}
		
		//by default, return the default adapter for IAddCjoNaturePolicy
		return defaultPolicy;
	}
	
	private void addExtensions() {
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (int i = 0; i < configurationElements.length; i++) {
			try {
				Object instance = configurationElements[i].createExecutableExtension("class");
				if (instance instanceof IAddVjoNaturePolicy)
					this.polices.add((IAddVjoNaturePolicy)instance);
			} catch (Exception e) {
				//TODO log
			}
		}
		
		//after loading extensions, update the marker statue
		this.extensionAdded = true;
	}
	
}
