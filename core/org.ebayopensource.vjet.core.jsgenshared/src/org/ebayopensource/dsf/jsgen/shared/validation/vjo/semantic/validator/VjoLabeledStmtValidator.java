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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationRuntimeException;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.EVjoSymbolType;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.IVjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbol;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.JstLoopUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class VjoLabeledStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPreAllChildrenListener{
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(LabeledStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
			
		if(!(jstNode instanceof LabeledStmt)){
			return;
		}
		
		final LabeledStmt labeledStmt = (LabeledStmt)jstNode;
		final JstIdentifier labelId = labeledStmt.getLabel();
		
		//create the label symbol
		final IJstNode closestScope = ctx.getClosestScope();
		IVjoSymbol labelSymbol = ctx.getSymbolTable().getSymbolInScope(closestScope, labelId.getName(), EVjoSymbolType.LOCAL_VARIABLE);
		if(labelSymbol != null){
			//report problem, label symbol should be unique
			final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(labelId, ctx.getGroupId(), new String[]{labelId.getName()});
			satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().DUPLICATE_LABEL, ruleCtx);
		}
		else{
			labelSymbol = new VjoSymbol()
				.setName(labelId.getName())
				.setDeclareNode(labelId)
				.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
			ctx.getSymbolTable().addSymbolInScope(closestScope, labelSymbol);
			
			final IVjoSymbol cleanUpSymbol = labelSymbol;
			final IVjoValidationPostAllChildrenListener postAllChildrenListener = new IVjoValidationPostAllChildrenListener(){
				public void onPostAllChildrenEvent(
						IVjoValidationVisitorEvent event)
						throws VjoValidationRuntimeException {
					ctx.getSymbolTable().removeSymbolInScope(closestScope, cleanUpSymbol);
				}

				public List<Class<? extends IJstNode>> getTargetNodeTypes() {
					return s_targetTypes;
				}

				public void onEvent(IVjoValidationVisitorEvent event)
						throws VjoValidationRuntimeException {
					onPostAllChildrenEvent(event);
				}
			};
			
			VjoSemanticValidatorRepo.getInstance().appendPostAllChildrenListener(labeledStmt, postAllChildrenListener);
		}
		
		if(!JstLoopUtil.isLoopStmt(labeledStmt.getStmt())){
			//report problem, label should only be applied to loop statements
		}
	}
}
