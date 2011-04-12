/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import org.ebayopensource.dsf.jsgen.shared.jstvalidator.DefaultJstValidationPolicy;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class VjoSemanticRulePolicy extends DefaultJstValidationPolicy {
	
	public static final VjoSemanticRulePolicy GLOBAL_ERROR_POLICY = new VjoSemanticRulePolicy();
	
	public static final VjoSemanticRulePolicy GLOBAL_FATAL_POLICY = new VjoSemanticRulePolicy(){
		@Override
		public boolean stopOnFirstError(){
			return true;
		}
		
		@Override
		public ProblemSeverity getProblemSeverity(JstProblemId id) {
			return ProblemSeverity.error;
		} 
	};
	
	public static final VjoSemanticRulePolicy GLOBAL_WARNING_POLICY = new VjoSemanticRulePolicy(){
		public ProblemSeverity getProblemSeverity(JstProblemId id) {
			return ProblemSeverity.warning;
		} 
	};
	
	public static final VjoSemanticRulePolicy GLOBAL_IGNORE_POLICY = new VjoSemanticRulePolicy(){
		public ProblemSeverity getProblemSeverity(JstProblemId id) {
			return ProblemSeverity.ignore;
		}
	};
}
