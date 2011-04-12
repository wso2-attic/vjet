/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblemFactory;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.MTypeShouldNotBeAsInnerTypeRuleCtx;
import org.ebayopensource.dsf.jst.IJstType;

public class MTypeShouldNotBeAsInnerTypeRule extends VjoSemanticRule<MTypeShouldNotBeAsInnerTypeRuleCtx> {

	public VjoSemanticProblem doFire(MTypeShouldNotBeAsInnerTypeRuleCtx ctx) {
		final IJstType mtype = ctx.getMType();
		if(mtype != null 
				&& (mtype.getOuterType() != null
						||mtype.getContainingType() != null)){
			final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
			return problem;
		}
		
		return null;
	}
}
