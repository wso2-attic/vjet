/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;


import org.ebayopensource.dsf.jst.JstProblemId;

/**
 * 
 * @author huzhou
 * <p>
 * semantic rule is defined to be associated with some problem
 * and will allow validators to fire them when conditions are met
 * semantic rules are registered in {@link: VjoSemanticRuleRepo}
 * semantic rules are initialized in VjoSemanticRuleRepo as well
 * </p>
 * @param <T>
 */
public interface IVjoSemanticRule <T extends IVjoSemanticRuleCtx> {
	
	String getRuleName();
	String getRuleDescription();
	JstProblemId getProblemId();
	String getErrMsg();
	VjoSemanticRulePolicy getGlobalRulePolicy();
	void setGlobalPolicy(VjoSemanticRulePolicy policy);
	VjoSemanticRulePolicy getDefaultPolicy();
	VjoSemanticRulePolicy getGroupRulePolicy(String groupId);
	void setGroupRulePolicy(String groupId, VjoSemanticRulePolicy policy);
	void setRuleName(String ruleName);
	void setRuleDescription(String ruleDesc);
	void setProblemId(JstProblemId problemId);
	void setErrMsg(String msg);
	
	//for grouping rules based on different user perspectives
	//for example
	//java developer will have certain rules active
	//js developer will have some java conventional rules inactivated instead
	boolean isActive();
	void setActive(boolean active);
	
	VjoSemanticProblem fire(final T ctx);
	boolean preFire(final T ctx);
	VjoSemanticProblem doFire(final T ctx);
	void postFire(final T ctx);
}