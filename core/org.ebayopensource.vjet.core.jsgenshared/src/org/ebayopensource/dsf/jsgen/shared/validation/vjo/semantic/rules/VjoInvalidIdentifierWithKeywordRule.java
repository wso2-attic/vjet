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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoConstants;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameWithKeywordRuleCtx;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class VjoInvalidIdentifierWithKeywordRule extends VjoSemanticRule<InvalidIdentifierNameWithKeywordRuleCtx>{

	private static Pattern s_idPattern;
	static{
		//objLiteral's jstIdentifier has quote prefix/postfix
		//all other jstIdentifiers should start with a-Z|_|$
		s_idPattern = Pattern.compile("[a-zA-Z_$][0-9a-zA-Z_$]*|\\$anonymous\\$");
	}
	private static final String[] VJO_KEYWORDS = {"vj$", "base", "_getBase"};
	private static final String[] VJO_KEYWORDS_4_ETYPE = {"vj$", "base", "_getBase", "name", "ordinal", "values"};
//	private static final String[] JAVA_ONLY_KEYWORDS = VjoConstants.JAVA_ONLY_KEYWORDS.toArray(new String[VjoConstants.JAVA_ONLY_KEYWORDS.size()]);
	private static final String[] JAVA_FULL_KEYWORDS = VjoConstants.JAVA_FULL_KEYWORDS.toArray(new String[VjoConstants.JAVA_FULL_KEYWORDS.size()]);
	
	public VjoSemanticProblem doFire(InvalidIdentifierNameWithKeywordRuleCtx ctx){
		final String identifierName = ctx.getIdentifierName();
		
		
		if(identifierName != null){
			final Matcher m = s_idPattern.matcher(identifierName);
			if(!m.matches()){
				final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
				return problem;
			}
			else{
				//check keywords 
				final String[] keywordsInCtx = ctx.getKeywordsInContext();
				if(keywordsInCtx != null){
					if(isKeyword(identifierName, keywordsInCtx)){
						final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
						return problem;
					}
				}
				else{
//					
					
					
					if(isCorrectCtx(ctx.getNode()) && isKeyword(identifierName, ctx.isEnumContexted()?VJO_KEYWORDS_4_ETYPE:VJO_KEYWORDS)){
						final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
						return problem;
					}
					else if(isKeyword(identifierName, JAVA_FULL_KEYWORDS)){
						final VjoSemanticProblem problem = VjoSemanticProblemFactory.getInstance().createProblem(ctx.getArguments(), ctx.getGroupId(), getProblemId(), getErrMsg(), ctx.getNode(), this);
						return problem;
					}
				}
			}
		}
		
		return null;
	}
	
	private boolean isCorrectCtx(IJstNode node) {
		if(node instanceof JstIdentifier){
			JstIdentifier id = (JstIdentifier)node;
			if(id.getQualifier()==null){
				return false;
			}
		}
		return true;
	}

	private boolean isKeyword(final String name, final String[] keywords){
		for(String vjoKeyword : keywords){
			if(vjoKeyword.equals(name)){
				return true;
			}
			else if(name != null 
					&& name.startsWith("\"") 
					&& name.endsWith("\"") 
					&& name.length() > 1
					&& vjoKeyword.equals(name.substring(1, name.length() - 1))){
				return true;
			}
		}
		return false;
	}
}
