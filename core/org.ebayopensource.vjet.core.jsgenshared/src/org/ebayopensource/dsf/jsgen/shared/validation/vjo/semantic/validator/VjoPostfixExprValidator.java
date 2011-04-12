/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr.Operator;
import org.ebayopensource.dsf.jst.token.IExpr;

public class VjoPostfixExprValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(PostfixExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof PostfixExpr)){
			return;
		}
		
		final PostfixExpr postfixExpr = (PostfixExpr)jstNode;
		//validating that bool expr's child expr are all boolean value types
		final IExpr expr = postfixExpr.getIdentifier();
		final Operator op = postfixExpr.getOperator();
		
		if(Operator.INCREMENT.equals(op)
			|| Operator.DECREMENT.equals(op)){
			//allow number
			
			final IJstType exprValue = expr.getResultType();
			if(exprValue != null){
				if(!TypeCheckUtil.isNumber(exprValue)){
					final BaseVjoSemanticRuleCtx ruleCtx = 
						new BaseVjoSemanticRuleCtx(postfixExpr, 
								ctx.getGroupId(), new String[]{expr.toExprText(), postfixExpr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().EXPR_SHOULD_BE_NUMBER, ruleCtx);
				}
			}
		}
	}
}
