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
import org.ebayopensource.dsf.jst.expr.ArrayCreationExpr;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoArrayCreationExprValidator 
	extends VjoSemanticValidator
	implements IVjoValidationPostAllChildrenListener {

    private static List<Class<? extends IJstNode>> s_targetTypes;
	
    static{
    	s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ArrayCreationExpr.class);
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
		
		if(!(jstNode instanceof ArrayCreationExpr)){
			return;
		}
		
		final String groupId = ctx.getGroupId();
		final ArrayCreationExpr expr = (ArrayCreationExpr)jstNode;
		final List<IExpr> dimensions = expr.getDimensions();
		
		// refactored to check dimension should be integer only
		// array val expression is to be validated by other validators
		// fieldaccess/jstidentifer etc.
		for (IExpr dimension : dimensions){
			// add error arguments
			String[] args = new String[2];
			args[0] = dimension != null ? dimension.toExprText() : "NULL";
			args[1] = expr.toExprText();
			
			IJstType dimensionValue = dimension != null ? dimension.getResultType() : null;
			if(dimensionValue != null){
				if(!TypeCheckUtil.isNumber(dimensionValue)){
					final BaseVjoSemanticRuleCtx arrayCreationDimensionTypeShouldBeIntegerRuleCtx = new BaseVjoSemanticRuleCtx(expr, groupId, args);
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ARRAY_CREATION_DIMENSION_TYPE_SHOULD_BE_INTEGER, arrayCreationDimensionTypeShouldBeIntegerRuleCtx);
				}
			}
		}
	}

}
