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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.JstLoopUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class VjoBreakStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener{
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(BreakStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
			
		if(!(jstNode instanceof BreakStmt)){
			return;
		}
		
		final BreakStmt breakStmt = (BreakStmt)jstNode;
		//break statement could appear in switch case body, and covered by VjoSwitchStmtValidator already, skipped here
		if(JstLoopUtil.withinSwitchStmt(breakStmt, ctx.getClosestScope())){
			return;
		}
		
		if(!JstLoopUtil.withinLoopStmt(breakStmt, ctx.getClosestScope())){
			//report problem
			//break/continue must appear within loop statements
		}

		final JstIdentifier breakLabelId = breakStmt.getIdentifier();
		if(breakLabelId != null){
			//break has label in use, validate if label exists
			final IVjoSymbol labelSymbol = ctx.getSymbolTable().getSymbolInScope(ctx.getClosestScope(), breakLabelId.getName(), EVjoSymbolType.LOCAL_VARIABLE);
			if(labelSymbol == null){
				//report problem as break label doesn't exist
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(breakLabelId, ctx.getGroupId(), new String[]{breakLabelId.getName()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().BREAK_NONE_EXIST_LABEL, ruleCtx);
			}
		}
	}
}
