/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation;

import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoGroupRulesCache;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRulePolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;

public class DefaultValidator {

	private static VjoSemanticRuleRepo ruleRepo;
 
	private VjoGroupRulesCache m_projectRulesCache = VjoGroupRulesCache
			.getInstance();

	private DefaultValidator() {
		loadRuleRepo();
	}

	public static DefaultValidator validator = null;

	/**
	 * return valiator
	 * 
	 * @return {@link DefaultValidator}
	 */
	public synchronized static DefaultValidator getValidator() {
		if (validator == null) {
			return new DefaultValidator();
		}
		return validator;
	}

	public Set<IResource> deriveResources(Object object) {
		return null;
	}

	private void loadRuleRepo() {
		if (ruleRepo == null) {
			ruleRepo = VjoSemanticRuleRepo.getInstance();
			loadCustomedValue();
			loadCustomedPrjoectValue();
		}
	}

	private void loadCustomedPrjoectValue() {
		if (ruleRepo == null) {
			return;
		}
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		IProject project = null;
		IProjectNature nature = null;
		for (int i = 0; i < projects.length; i++) {
			project = projects[i];
			if (project == null) {
				continue;
			}
			try {
				nature = project.getNature(VjoNature.NATURE_ID);
			} catch (CoreException e) {
				continue;
			}
			if (nature == null) {
				continue;
			}
			innerClearCustomedProjectValue(project);
			innerLoadProjectPreference(project);
		}
	}

	private void innerLoadProjectPreference(IProject project) {
		IScopeContext projectContext = new ProjectScope(project);
		if (!projectContext.getLocation().toFile().exists()) {
			return;
		}
		String groupName = project.getName();
		IEclipsePreferences prefs = projectContext
				.getNode(VjetPlugin.PLUGIN_ID);
		if (prefs == null) {
			return;
		}
		try {
			if (prefs.keys().length != 0) {
				loadProjectPre(groupName, prefs);
			}
		} catch (BackingStoreException e) {
			return;
		}
	}

	/**
	 * @param groupName
	 * @param prefs
	 */
	private void loadProjectPre(String groupName, IEclipsePreferences prefs) {
		String ruleSetName = "";
		String propertyValue = null;
		for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
			ruleSetName = ruleSet.getRuleSetName();
			propertyValue = prefs.get(ruleSetName, null);

			for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
				if (propertyValue == null) {
					continue;
				}
				VjoSemanticRulePolicy policy = getRulePolicy(propertyValue);
				if (policy ==null ) {
					rule.setGroupRulePolicy(groupName, rule.getDefaultPolicy());
					//m_projectRulesCache.addGroupPolicy(groupName, rule, policy);
				}else{
					rule.setGroupRulePolicy(groupName, policy);
				}
					
			}
		}
	}

	private void loadCustomedValue() {
		Preferences preferenceStore = VjetPlugin.getDefault()
				.getPluginPreferences();

		String ruleSetName = "";
		String propertyValue = null;
		for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
			ruleSetName = ruleSet.getRuleSetName();
			propertyValue = preferenceStore.getString(ruleSetName);
			if(propertyValue.equalsIgnoreCase("default")){
				for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
					rule.setGlobalPolicy(rule.getDefaultPolicy());
				}
			}else{
				for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
					rule.setGlobalPolicy(getRulePolicy(propertyValue));
				}
			}
		}
	}

	private VjoSemanticRulePolicy getRulePolicy(String severity) {
		if ("error".equalsIgnoreCase(severity)) {
			return VjoSemanticRulePolicy.GLOBAL_ERROR_POLICY;
		} else if ("warning".equalsIgnoreCase(severity)) {
			return VjoSemanticRulePolicy.GLOBAL_WARNING_POLICY;
		} else if ("ignore".equalsIgnoreCase(severity)) {
			return VjoSemanticRulePolicy.GLOBAL_IGNORE_POLICY;
		}
		return null;
	}

	public void ruleChanged(String groupName) {
		if (groupName == null) {
			VjoSemanticRuleRepo.getInstance().restoreDefaultPolicies();
			loadCustomedValue();
		} else {
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(groupName);
			if (project == null) {
				return;
			}
			innerClearCustomedProjectValue(project);
			innerLoadProjectPreference(project);
		}
	}

	public void innerClearCustomedProjectValue(IProject project) {
		IScopeContext projectContext = new ProjectScope(project);
		if (!projectContext.getLocation().toFile().exists()) {
			return;
		}
		String groupName = project.getName();
		if (groupName != null) {
			m_projectRulesCache.clearGroupCache(groupName);
		}
	}
}
