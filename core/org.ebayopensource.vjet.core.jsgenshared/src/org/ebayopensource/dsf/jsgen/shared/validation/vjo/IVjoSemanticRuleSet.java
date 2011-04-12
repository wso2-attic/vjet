/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.List;

public interface IVjoSemanticRuleSet {

	public abstract String getRuleSetName();

	public abstract void setRuleSetName(String ruleSetName);

	public abstract String getRuleSetDescription();

	public abstract void setRuleSetDesription(String ruleSetDesc);

	public abstract void addRule(IVjoSemanticRule<?> rule);

	public abstract List<IVjoSemanticRule<?>> getRules();

	public abstract IVjoSemanticRule<?> getRule(String ruleName);

}