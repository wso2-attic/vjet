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

import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationRuleSpec;
import org.ebayopensource.dsf.jsgen.shared.validation.common.JstProcessingState;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.JstProblemId;

public class BaseJstRuleSpec implements IJstValidationRuleSpec {

	private JstProcessingState m_state = JstProcessingState.RESOLVED;
	private List<JstProblemId> m_problemIds = new ArrayList<JstProblemId>();
	private List<ScopeId> m_scopeId = new ArrayList<ScopeId>();
	
	public BaseJstRuleSpec addScope(ScopeId s){
		m_scopeId.add(s);
		return this;
	}
	public BaseJstRuleSpec addProblem(JstProblemId p){
		m_problemIds.add(p);
		return this;
	}
	
	public BaseJstRuleSpec setState(JstProcessingState s){
		m_state = s;
		return this;
	}
	
	
	public JstProcessingState getMinimumLevel() {
		return m_state;
	}

	public List<JstProblemId> getProblemIds() {
		return m_problemIds;
	}

	public List<ScopeId> getScopeId() {
		return m_scopeId;
	}

}
