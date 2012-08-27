/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is use to store user specified rules
 * 
 * Data structure as below:
 * 
 * Key Value ProjectName HashMap use to store rules name and user specified
 * value Key Value RuleName user specified value
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjoGroupRulesCache {

	private static VjoGroupRulesCache CACHE = new VjoGroupRulesCache();

	private Map<String, Map<String, VjoSemanticRulePolicy>> m_groupPolicyMap = new HashMap<String, Map<String, VjoSemanticRulePolicy>>();

	private VjoSemanticRulePolicy m_globalErrorPolicy = VjoSemanticRulePolicy.GLOBAL_ERROR_POLICY;

	private VjoGroupRulesCache() {
	}

	/**
	 * 
	 * Get vjo project and validation rule caches
	 * 
	 * @return VjoProjectRuleCache
	 */
	public static VjoGroupRulesCache getInstance() {
		return CACHE;
	}

	/**
	 * Get rule policy with project name and rule
	 * 
	 * @param projectName
	 *            {@link String}
	 * @param rule
	 *            {@link IVjoSemanticRule}
	 * @return VjoSemanticRulePolicy
	 */
	public VjoSemanticRulePolicy getRulePolicy(String projectName,
			IVjoSemanticRule<?> rule) {
		if (projectName == null ) {
			return rule.getGlobalRulePolicy();
		}
		VjoSemanticRulePolicy groupRulePolicy = rule.getGroupRulePolicy(projectName);
		return groupRulePolicy;
	}

	/**
	 * @param projectName
	 *            {@link String}
	 * @param rule
	 *            {@link IVjoSemanticRule}
	 * @return VjoSemanticRulePolicy
	 */
	private VjoSemanticRulePolicy getRulePolicyFromRuleMap(String projectName,
			IVjoSemanticRule<?> rule) {
		Map<String, VjoSemanticRulePolicy> ruleMap = m_groupPolicyMap
				.get(projectName);
		if (ruleMap == null) {
			return m_globalErrorPolicy;
		}
		VjoSemanticRulePolicy resultPolicy = null;
		String ruleName = rule.getRuleName();
		if (ruleMap.containsKey(ruleName)) {
			resultPolicy = ruleMap.get(ruleName);
			if (resultPolicy != null) {
				return resultPolicy;
			}
		}
		return rule.getGlobalRulePolicy();
	}

	/**
	 * Judge is cache have stored specified rules for project
	 * 
	 * @param projectName
	 *            {@link String}
	 * @return boolean
	 */
	public boolean hasGroupPolicy(String projectName) {
		if (m_groupPolicyMap == null) {
			return false;
		}
		return m_groupPolicyMap.containsKey(projectName);
	}

	/**
	 * Add project policy with existed map
	 * 
	 * @param groupId
	 * @param policy
	 */
	public void addGroupPolicy(String groupId, IVjoSemanticRule<?> rule,
			VjoSemanticRulePolicy policy) {
		if (m_groupPolicyMap == null) {
			m_groupPolicyMap = new HashMap<String, Map<String, VjoSemanticRulePolicy>>();
		}
		Map<String, VjoSemanticRulePolicy> rulePolicyMap = null;
		if (m_groupPolicyMap.containsKey(groupId)) {
			rulePolicyMap = (Map<String, VjoSemanticRulePolicy>) m_groupPolicyMap
					.get(groupId);
		} else {
			rulePolicyMap = new HashMap<String, VjoSemanticRulePolicy>();
		}
		rulePolicyMap.put(rule.getRuleName(), policy);
		m_groupPolicyMap.put(groupId, rulePolicyMap);
	}

	/**
	 * Clear all project validation rules cache
	 */
	public void clearAllGroupCache() {
		m_groupPolicyMap.clear();
	}

	/**
	 * Clear single project validation rules cache
	 */
	public void clearGroupCache(String projectName) {
		Map ruleMap = m_groupPolicyMap.get(projectName);
		if (ruleMap != null) {
			ruleMap.clear();
		}
	}
}
