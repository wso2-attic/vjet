/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.ArrayList;
import java.util.List;

public class ResolutionResult {

	private List<IScriptProblem> m_problems = new ArrayList<IScriptProblem>();
	private IJstType m_type;
	
	public List<IScriptProblem> getProblems(){
		return m_problems;
	}
	
	public ResolutionResult addProblem(IScriptProblem prob) {
		m_problems.add(prob);
		return this;
	}

	public IJstType getType() {
		return m_type;
	}

	public void setType(IJstType type) {
		m_type = type;
	}
}
