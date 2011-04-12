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
import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoConditionalExprValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ConditionalExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof ConditionalExpr)){
			return;
		}
		
		final ConditionalExpr conditionalExpr = (ConditionalExpr)jstNode;
		final IExpr ifExpr = conditionalExpr.getCondition();
//		final IExpr thenExpr = conditionalExpr.getThenExpr();
//		final IExpr elseExpr = conditionalExpr.getElseExpr();
		
		final IJstType ifValue = ifExpr.getResultType();
//		final IJstType thenValue = thenExpr.getResultType();
//		final IJstType elseValue = elseExpr.getResultType();
		
		if(ifValue != null){
			if(!TypeCheckUtil.isBoolean(ifValue)){
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(conditionalExpr, ctx.getGroupId(), new String[]{ifExpr.toExprText(), conditionalExpr.toExprText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().EXPR_SHOULD_BE_BOOL, ruleCtx);	
			}
		}
		
//		if(thenValue != null && elseValue != null){
//			//compare types
//			final List<IJstType> thenValueTypes = new ArrayList<IJstType>();
//			final List<IJstType> elseValueTypes = new ArrayList<IJstType>();
//			final IJstNode thenValueBinding = JstBindingUtil.getJstBinding(thenExpr);
//			final IJstNode elseValueBinding = JstBindingUtil.getJstBinding(elseExpr);
//			if(thenValueBinding != null && thenValueBinding instanceof JstArg){
//				thenValueTypes.addAll(((JstArg)thenValueBinding).getTypes());
//			}
//			else{
//				thenValueTypes.add(thenValue);
//			}
//			
//			if(elseValueBinding != null && elseValueBinding instanceof JstArg){
//				elseValueTypes.addAll(((JstArg)elseValueBinding).getTypes());
//			}
//			else{
//				elseValueTypes.add(elseValue);
//			}
//			
//				
//			for(IJstType thenValueType: thenValueTypes){
//				for(IJstType elseValueType: elseValueTypes){
//					if(TypeCheckUtil.isAssignable(thenValueType, elseValueType)
//							||TypeCheckUtil.isAssignable(elseValueType, thenValueType)){
//						return;
//					}
//				}
//			}
//			//no type match found in any overloading possibilities
//			final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(conditionalExpr, ctx.getGroupId(), new String[]{thenExpr.toExprText(), elseExpr.toExprText(), conditionalExpr.toExprText()});
//			satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().TWO_EXPRS_SHOULD_BE_CONSISTENT, ruleCtx);
//		}
	}

}
