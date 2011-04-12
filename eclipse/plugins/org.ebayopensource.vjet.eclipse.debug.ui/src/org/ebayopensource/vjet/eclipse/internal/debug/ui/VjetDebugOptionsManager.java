/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.internal.core.StepFilterManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.mod.debug.core.model.IScriptDebugTarget;
import org.eclipse.dltk.mod.debug.ui.IDLTKDebugUIPreferenceConstants;
import org.eclipse.dltk.mod.internal.debug.ui.ScriptDebugOptionsManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjetDebugOptionsManager implements IPropertyChangeListener,
		IDebugEventSetListener,
		org.eclipse.core.runtime.Preferences.IPropertyChangeListener {

	private static VjetDebugOptionsManager	instance;
	private String[]						m_activeStepFilters;

	public static VjetDebugOptionsManager getDefault() {
		if (instance == null) {
			instance = new VjetDebugOptionsManager();
		}
		return instance;
	}

	private VjetDebugOptionsManager() {

	}

	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (isFilterListProperty(property)) {
			refreshActiveStepFilters();
			notifyTargetsOfFilters();
		}
	}

	private boolean isFilterListProperty(String property) {
		return IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST
				.equals(property)
				|| IDLTKDebugUIPreferenceConstants.PREF_INACTIVE_FILTERS_LIST
						.equals(property);
	}

	/**
	 * Notifies all targets of current filter specifications.
	 */
	protected void notifyTargetsOfFilters() {
		IDebugTarget[] targets = getAllDebugTargets();
		for (int i = 0; i < targets.length; i++) {
			if (targets[i] instanceof IScriptDebugTarget) {
				IScriptDebugTarget target = (IScriptDebugTarget) targets[i];
				notifyTargetOfFilters(target);
			}
		}
	}

	private IDebugTarget[] getAllDebugTargets() {
		return DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
	}

	private void notifyTargetOfFilters(IScriptDebugTarget target) {
		target.setUseStepFilters(DebugUITools.isUseStepFilters());
		target.setFilters(getActiveStepFilters());
	}

	/**
	 * Returns the current list of active step filters.
	 * 
	 * @return current list of active step filters
	 */
	private String[] getActiveStepFilters() {
		if (m_activeStepFilters == null) {
			refreshActiveStepFilters();

			registerListener();
		}
		return m_activeStepFilters;
	}

	private void registerListener() {
		getPreferenceStore().addPropertyChangeListener(this);
		DebugPlugin.getDefault().getPluginPreferences()
				.addPropertyChangeListener(this);
	}

	private void refreshActiveStepFilters() {
		String[] storedActiveStepFilters = ScriptDebugOptionsManager
				.parseList(getPreferenceStore()
						.getString(
								IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST));

		storedActiveStepFilters = convert2RegExp(storedActiveStepFilters);

		m_activeStepFilters = storedActiveStepFilters;
	}

	public static String[] convert2RegExp(String[] filters) {
		for (int i = 0; i < filters.length; i++) {
			String filter = filters[i];
			filter = filter.replace('\\', '/');
			filter = filter.replace(".", "\\.");
			filters[i] = filter.replace("*", ".*");
		}
		return filters;
	}

	private IPreferenceStore getPreferenceStore() {
		return VjetDebugUIPlugin.getDefault().getPreferenceStore();
	}

	public void start() {
		DebugPlugin.getDefault().addDebugEventListener(this);
	}

	public void stop() {
		DebugPlugin.getDefault().removeDebugEventListener(this);
	}

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (DebugEvent event : events) {
			Object source = event.getSource();
			switch (event.getKind()) {
			case DebugEvent.CREATE:
				if (source instanceof IScriptDebugTarget) {
					notifyTargetOfFilters((IScriptDebugTarget) source);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void propertyChange(
			org.eclipse.core.runtime.Preferences.PropertyChangeEvent event) {
		String property = event.getProperty();
		if (StepFilterManager.PREF_USE_STEP_FILTERS.equals(property)) {
			IDebugTarget[] debugTargets = getAllDebugTargets();
			for (IDebugTarget target : debugTargets) {
				if (target instanceof IScriptDebugTarget) {
					((IScriptDebugTarget) target)
							.setUseStepFilters(DebugUITools.isUseStepFilters());
				}
			}
		}
	}
}
