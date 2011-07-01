/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;

public class Vjet2CorePreferences {
	
	private final String propertyNamespace;

	private IEclipsePreferences preferences;
	
	private Vjet2CorePreferences(IProject project, String qualifier) {
		this.propertyNamespace = qualifier + '.';
		this.preferences = getEclipsePreferences(project, qualifier);
	}
	
	public static Vjet2CorePreferences getProjectPreferences(IProject project) {
		return getProjectPreferences(project, VjetPlugin.PLUGIN_ID);
	}

	public static Vjet2CorePreferences getProjectPreferences(IProject project, String qualifier) {
		return new Vjet2CorePreferences(project, qualifier);
	}
	
	private IEclipsePreferences getEclipsePreferences(IProject project, String qualifier) {
		IScopeContext context = new ProjectScope(project);
		IEclipsePreferences node = context.getNode(qualifier);
		return node;
	}
	
	public void putString(String key, String value) {
		try {
			this.preferences.put(propertyNamespace + key, value);
			this.preferences.flush();
		}
		catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public void putBoolean(String key, boolean value) {
		try {
			this.preferences.putBoolean(propertyNamespace + key, value);
			this.preferences.flush();
		}
		catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String key, String defaultValue) {
		return this.preferences.get(propertyNamespace + key, defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return this.preferences.getBoolean(propertyNamespace + key, defaultValue);
	}
	
	public IEclipsePreferences getProjectPreferences() {
		return this.preferences;
	}
	
}
