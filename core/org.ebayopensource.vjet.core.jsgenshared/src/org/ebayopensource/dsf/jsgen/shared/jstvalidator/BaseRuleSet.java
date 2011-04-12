/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationRule;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IRuleSet;

public class BaseRuleSet implements IRuleSet {
	private List<IJstValidationRule> m_rules = new ArrayList<IJstValidationRule>();
	
	
	public BaseRuleSet addRule(IJstValidationRule rule){
		m_rules.add(rule);
		return this;
	}
	
	public List<IJstValidationRule> getRules() {
		return m_rules;
	}

}
