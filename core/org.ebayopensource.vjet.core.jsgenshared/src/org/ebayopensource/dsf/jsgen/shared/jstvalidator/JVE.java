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
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstProblemFactory;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationPolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationRule;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IProblemMessageProvider;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IRuleSet;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;


public class JVE implements IJstValidator{

	
	private IJstProblemFactory m_factory;
	private List<IJstValidationRule> m_rules;
	private IJstValidationPolicy m_policy;
	


	public void validate(ValidationCtx ctx) {
		
		if(ctx.getNode()==null && ctx.getFilePath()==null){
			throw new RuntimeException("Policy, Type and FilePath must be specified");
		}
		if(m_policy==null){
			// use default policy
			m_policy = DefaultJstValidationPolicy.getInstance();
		}

		IProblemMessageProvider messages = new DefaultProblemMessages();
		
		if(m_factory==null){
			m_factory = new DefaultJstProblemFactory(messages);
		}
		JstDepthFirstTraversal.accept(ctx.getNode(), new DefaultJstValidationVisitor(ctx, this));
		
	}



	public void addRule(IJstValidationRule rule) {
		if(m_rules==null){
			m_rules = new ArrayList<IJstValidationRule>();
		}
		m_rules.add(rule);
		
	}
	public void addRuleSet(IRuleSet rules) {
		if(m_rules==null){
			m_rules = new ArrayList<IJstValidationRule>();
		}
		m_rules.addAll(rules.getRules());
		
	}


	public void setValidationPolicy(IJstValidationPolicy p) {
		m_policy = p;
		
	}



	public IJstProblemFactory getProblemFactory() {
		// TODO Auto-generated method stub
		return null;
	}



	public List<IJstValidationRule> getRules() {
		return Collections.unmodifiableList(m_rules);
	}



	public IJstValidationPolicy getValidationPolicy() {
		if(m_policy==null){
			m_policy = DefaultJstValidationPolicy.getInstance();
		}
		return m_policy;
	}





	
	
}
