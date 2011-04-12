/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VjoSemanticRuleSet implements IVjoSemanticRuleSet {
	private String m_name;
	private String m_desc;
	
	private Map<String, IVjoSemanticRule<?>> m_ruleMap;
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#getRuleSetName()
	 */
	public String getRuleSetName(){
		return m_name;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#setRuleSetName(java.lang.String)
	 */
	public void setRuleSetName(String ruleSetName){
		m_name = ruleSetName;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#getRuleSetDescription()
	 */
	public String getRuleSetDescription(){
		return m_desc;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#setRuleSetDesription(java.lang.String)
	 */
	public void setRuleSetDesription(String ruleSetDesc){
		m_desc = ruleSetDesc;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#addRule(org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule)
	 */
	public void addRule(IVjoSemanticRule<?> rule){
		if(m_ruleMap == null){
			m_ruleMap = new HashMap<String, IVjoSemanticRule<?>>();
		}
		m_ruleMap.put(rule.getRuleName(), rule);
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#getRules()
	 */
	public List<IVjoSemanticRule<?>> getRules(){
		if(m_ruleMap == null){
			return Collections.emptyList();
		}
		
		final List<IVjoSemanticRule<?>> toReturn = new ArrayList<IVjoSemanticRule<?>>();
		toReturn.addAll(m_ruleMap.values());
		return toReturn;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet#getRule(java.lang.String)
	 */
	public IVjoSemanticRule<?> getRule(String ruleName){
		if(m_ruleMap != null){
			return m_ruleMap.get(ruleName);
		}
		return null;
	}
}
