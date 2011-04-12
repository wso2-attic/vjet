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

public interface IJstValidationRuleSpec {

	/**
	 * Scope which you would like your rule to run
	 * When we are in batch mode and validating everything validator can validate everything
	 * When We are in type mode we can use the scope id based on an offset and validate a subset of the rules.
	 * for example if you are in global scope we can run global validation routines.
	 * @return
	 */
	List<ScopeId> getScopeId();
	
	/**
	 * Problem IDS that could be reported
	 * If a policy is ignoring all the problem ids we do not want to have to process this rule.
	 * If no problem ids are specified then the rule will use scope id only
	 * @return
	 */
	List<JstProblemId> getProblemIds();
	
	/**
	 * State of JST processing required to run the rule
	 * Headers only for example are required for some rules
	 * Headers + Statements are required for full internal type validation
	 * Headers + Statements + Resolved types - required for external validation.
	 * 
	 * This rule will not be run if the state of the JST is not complete based on what the rule requires.
	 * 
	 */
	JstProcessingState getMinimumLevel();
	
	
	
	
	
	
}
