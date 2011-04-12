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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoForInStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPreAllChildrenListener {
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ForInStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		//logic for validating jst var defined is in VjoJstIdentifierValidator now
//		final VjoValidationCtx ctx = event.getValidationCtx();
//		final IJstNode jstNode = event.getVisitNode();
//		
//		if(jstNode instanceof ForInStmt && ((ForInStmt)jstNode).getVar() instanceof JstIdentifier){
//			//report a different problem
//			JstIdentifier jstIdentifier = (JstIdentifier)((ForInStmt)jstNode).getVar();
//			if(jstIdentifier.getJstBinding() == null){
//				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstIdentifier, ctx.getGroupId(), new String[]{jstIdentifier.getName(), jstIdentifier.toExprText()});
//				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().IMPLICIT_GLOBAL_VARIABLE_DECLARED, ruleCtx);
//			}
//		}
		
		
	}
}
