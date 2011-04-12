/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.common;

import java.util.List;

import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;

/**
 * Policy for validator to honor
 *  
 * 
 *
 */
public interface IJstValidationPolicy {
	/**
	 * proceed on errors if true keeps going unless there is an internal error that is unrecoverable
	 * @return
	 */
	boolean proceedOnErrors();
	
	/**
	 * stop on first error if true will find first error and stop validation
	 * @return
	 */
	boolean stopOnFirstError();
	
	/**
	 * ability to override the problem severity
	 * @param id You can implement your own map to convert one problem to a specific priority overriding
	 * the default ie if the system made a certain problem an error and you want to ignore it you can override that
	 * here.
	 * @return
	 */
	ProblemSeverity getProblemSeverity(JstProblemId id);
	
	/**
	 * Provide all rule specification instances 
	 * @return
	 */
	//TODO remove
	List<IJstValidationRuleSpec> getAllValidationRuleSpecs();
	
	/**
	 * Provide all validation rule specifications for a specific scope of code.
	 * We can run validation that must run for a specific scope much faster than if we require
	 * @param identifer 
	 * @return
	 */
	//TODO remove
	List<IJstValidationRuleSpec> getValidationRuleSpecs(ScopeId identifer);
	
}
