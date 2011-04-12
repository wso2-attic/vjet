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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.expr.FuncExpr;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoFuncExprValidator 
	extends VjoSemanticValidator 
	implements //IVjoValidationPreAllChildrenListener,
		IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(FuncExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	//no longer support dynamic scope change for event binding
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		//not in use of symbol table now, funcExpr should have its own binding 
		
//		final VjoValidationCtx ctx = event.getValidationCtx();
//		final IJstNode currentNode = event.getVisitNode();
//		if(currentNode instanceof FuncExpr){
//			final IJstMethod func = ((FuncExpr)currentNode).getFunc();
//			if(func != null){
//				
//				final String[] arguments = new String[2];
//				if (func.getName()!= null && func.getName().getName() != null){
//					arguments[0] = func.getName().getName();
//				}
//				else{
//					arguments[0] = "NULL";
//				}
//				
//				final MethodAndReturnFlowRuleCtx ruleCtx = new MethodAndReturnFlowRuleCtx(func.getName(), ctx.getGroupId(), arguments, func, ctx.getMethodControlFlowTable());
//				final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
//				if(flowTable != null){
//					if(func.getRtnType() != null && !"void".equals(func.getRtnType().getSimpleName())){
//		                final List<IStmt> rtnStmts = flowTable.lookUpStmt(func);
//						boolean allReturned = rtnStmts.size() > 0;
//		                for (IStmt stmt : rtnStmts) {
//							if(stmt == null){
//								//return flow issue
//								allReturned = false;
//								break;
//							}
//						}
//						
//						if(!allReturned){
//							satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().NONE_VOID_METHOD_SHOULD_HAVE_RETURN, ruleCtx);
//						}
//					}
//				}
//			}
//		}
	}

}
