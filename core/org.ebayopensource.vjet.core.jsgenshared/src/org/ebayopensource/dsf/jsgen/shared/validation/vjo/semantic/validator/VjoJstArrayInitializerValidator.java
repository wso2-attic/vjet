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
import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoJstArrayInitializerValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstArrayInitializer.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		// no more symbol table uses
		
		//no more symbol table uses
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();

		if(!(jstNode instanceof JstArrayInitializer)){
			return;
		}
		
		final JstArrayInitializer arrayInit = (JstArrayInitializer)jstNode;
		final IJstType arrayType = arrayInit.getResultType();
		if(arrayType instanceof JstArray){
			final IJstType arrayElemExpectType = ((JstArray)arrayType).getComponentType();
			if(arrayElemExpectType != null
				&& !(arrayElemExpectType instanceof IInferred)){
				if(arrayInit.getExprs() != null){
					for(IExpr arrayElem : arrayInit.getExprs()){
						if(arrayElem == null){
							continue;
						}
						final IJstType arrayElemActualType = arrayElem.getResultType();
						if(arrayElemActualType != null
								&& !TypeCheckUtil.isAssignable(arrayElemExpectType, arrayElemActualType)){
							final String[] errorMessages = {
									arrayElemExpectType.getName(),
									arrayElemActualType.getName(),
									arrayInit.toExprText()
							};
							final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(arrayElem, ctx.getGroupId(), errorMessages);
							satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ASSIGNABLE, ruleCtx);
							break;
						}
					}
				}
			}
		}
	}
}
