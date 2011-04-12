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
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr.Operator;
import org.ebayopensource.dsf.jst.token.IExpr;

public class VjoPrefixExprValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(PrefixExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof PrefixExpr)){
			return;
		}
		
		final PrefixExpr prefixExpr = (PrefixExpr)jstNode;
		//validating that bool expr's child expr are all boolean value types
		final IExpr expr = prefixExpr.getIdentifier();
		final Operator op = prefixExpr.getOperator();
		
		if(Operator.TYPEOF.equals(op)){
			//allow any type
		}
		else if(Operator.COMPLEMENT.equals(op)){
			//allow number/boolean
			final IJstType exprValue = expr.getResultType();
			if(exprValue != null){
				if(!TypeCheckUtil.isBoolean(exprValue)
						&& !TypeCheckUtil.isNumber(exprValue)){
					final BaseVjoSemanticRuleCtx ruleCtx = 
						new BaseVjoSemanticRuleCtx(prefixExpr, 
								ctx.getGroupId(), new String[]{expr.toExprText(), prefixExpr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().EXPR_SHOULD_BE_BOOL_OR_NUMBER, ruleCtx);
				}
			}
		}
		else if(Operator.NOT.equals(op)){
			//allow boolean
		}
		else if(Operator.PLUS.equals(op)
				|| Operator.MINUS.equals(op)
				|| Operator.INCREMENT.equals(op)
				|| Operator.DECREMENT.equals(op)
				|| Operator.COMPLEMENT.equals(op)){
			//allow number
			final IJstType exprValue = expr.getResultType();
			if(!TypeCheckUtil.isNumber(exprValue)){
				final BaseVjoSemanticRuleCtx ruleCtx = 
					new BaseVjoSemanticRuleCtx(
							prefixExpr, 
							ctx.getGroupId(), 
							new String[]{expr.toExprText(), prefixExpr.toExprText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().EXPR_SHOULD_BE_NUMBER, ruleCtx);
			}
		}
		else if(Operator.DELETE.equals(op)){
			//delete allows the operands to be
			//1) implicitly defined global variables
			//2) user-defined prototype properties
			//3) array element
			
			//1) implicitly defined global variables are not allowed in vjet
			//2) user-defined prototype properties as the following:
		}
	}
}
