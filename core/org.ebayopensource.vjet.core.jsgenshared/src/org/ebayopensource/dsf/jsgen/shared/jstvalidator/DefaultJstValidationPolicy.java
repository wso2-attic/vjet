/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationPolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.common.IJstValidationRuleSpec;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class DefaultJstValidationPolicy implements IJstValidationPolicy{

	private static DefaultJstValidationPolicy s_instance = new DefaultJstValidationPolicy(); 
	
	public static DefaultJstValidationPolicy getInstance(){
		return s_instance;
	}
	
	public boolean proceedOnErrors() {
		return true;
	}

	public boolean stopOnFirstError() {
		return false;
	}
	
	
	public ProblemSeverity getProblemSeverity(JstProblemId id) {
		return ProblemSeverity.error;
	}

	public List<IJstValidationRuleSpec> getAllValidationRuleSpecs() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IJstValidationRuleSpec> getValidationRuleSpecs(ScopeId identifer) {
		// TODO Auto-generated method stub
		return null;
	}

}
