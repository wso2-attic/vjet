/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblemFactory;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameRuleCtx;

public class VjoInvalidIdentifierRule extends VjoSemanticRule<InvalidIdentifierNameRuleCtx>{

	private static Pattern s_idPattern;
	static{
		//objLiteral's jstIdentifier has quote prefix/postfix
		//all other jstIdentifiers should start with a-Z|_|$
		s_idPattern = Pattern.compile("('[^']+')|(\"[^\"]+\")|([a-zA-Z_$][0-9a-zA-Z_$]*)");
	}
	
	public VjoSemanticProblem doFire(InvalidIdentifierNameRuleCtx ctx){
		final String identifierName = ctx.getIdentifierName();
		
		if(identifierName != null){
			final Matcher m = s_idPattern.matcher(identifierName);
			if(!m.matches()){
				final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
				return problem;
			}
		}
		
		return null;
	}
}
