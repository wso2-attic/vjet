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
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoArrayAccessExprValidator 
	extends VjoSemanticValidator
	implements IVjoValidationPostAllChildrenListener{
	
	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ArrayAccessExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		//do nothing
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(!(jstNode instanceof ArrayAccessExpr)){
			return;
		}
		
		final String groupId = ctx.getGroupId();
		final ArrayAccessExpr expr = (ArrayAccessExpr)jstNode;
		final IExpr arrayExpr = expr.getExpr();
		final IExpr arrayIndex = expr.getIndex();
		
		//a[i]
		if (arrayExpr != null){
			//look up value
			final IJstType arrayExprValue = arrayExpr.getResultType();
			//check variable
			if(arrayExprValue == null){
				return;//cannot do further validations
			}
			
			// check array index with int or string type
			// add error arguments
			String[] arguments = new String[2];
			arguments[0] = arrayIndex != null ? arrayIndex.toExprText() : "NULL";
			arguments[1] = expr.toExprText();
			
			if(arrayIndex != null){
				final IJstType indexValue = arrayIndex.getResultType();
				if(indexValue != null){
					if(!TypeCheckUtil.isNumber(indexValue) && 
							!TypeCheckUtil.isString(indexValue)){
						final BaseVjoSemanticRuleCtx arrayIndexShouldBeIntTypeRuleCtx = new BaseVjoSemanticRuleCtx(expr, groupId, arguments);
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ARRAY_INDEX_SHOULD_BE_INT_OR_STRING_TYPE, arrayIndexShouldBeIntTypeRuleCtx);
					}
				}
			}			
		}
	}
	
}
