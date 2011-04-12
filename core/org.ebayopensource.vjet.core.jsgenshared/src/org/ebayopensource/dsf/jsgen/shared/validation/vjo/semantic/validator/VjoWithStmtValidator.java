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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.token.IExpr;

public class VjoWithStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPreAllChildrenListener{

	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(WithStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final IJstNode jstNode = event.getVisitNode();
		final SingleWithStmtValidator singleWithValidator = new SingleWithStmtValidator();
		VjoSemanticValidatorRepo.getInstance().registerPostChildListener(jstNode, singleWithValidator);
		VjoSemanticValidatorRepo.getInstance().registerPostAllChildrenListener(jstNode, singleWithValidator);
	}
	
	/**
	 * <p>
	 * There're essentially two responsibilities of SingleWithStmtValidator
	 * 1) validate single with statement as old logic does
	 * 2) swap with statement validation to NestedWithStmtValidator which reports only 1 issue generally regarding the discouraged nested with rather than every conflicting name introduced into local scope
	 * </p>
	 * 
	 *
	 */
	protected class SingleWithStmtValidator 
		extends VjoSemanticValidator 
		implements IVjoValidationPreAllChildrenListener,
			IVjoValidationPostChildListener, 
			IVjoValidationPostAllChildrenListener{
		
		@Override
		public List<Class<? extends IJstNode>> getTargetNodeTypes() {
			return s_targetTypes;
		}
		
		@Override
		public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
			final IJstNode jstNode = event.getVisitNode();
			final NestedWithStmtValidator nestedWithStmtValidator = new NestedWithStmtValidator();
			VjoSemanticValidatorRepo.getInstance().registerPostChildListener(jstNode, nestedWithStmtValidator);
			VjoSemanticValidatorRepo.getInstance().registerPostAllChildrenListener(jstNode, nestedWithStmtValidator);
		}
		
		@Override
		public void onPostChildEvent(final IVjoValidationVisitorEvent event){
			final VjoValidationCtx ctx = event.getValidationCtx();
			final IJstNode jstNode = event.getVisitNode();
			final IJstNode child = event.getVisitChildNode();
			
			if(jstNode instanceof WithStmt
					&& child instanceof IExpr){
				final WithStmt withStmt = (WithStmt)jstNode;
				final IJstNode withBlock = withStmt.getBody();

				//entered with statement, swap the with pre all listener to this
				VjoSemanticValidatorRepo.getInstance().removePreAllChildrenListener(VjoWithStmtValidator.this);
				VjoSemanticValidatorRepo.getInstance().registerPreAllChildrenListener(this);
				
				VjoSemanticValidatorRepo.getInstance().deactivateListeners();
				VjoSemanticValidatorRepo.getInstance().activateListener(WithStmt.class);
				//Added By Eric.Ma 2020100629 for add rtn stmt validation logic in with block
				VjoSemanticValidatorRepo.getInstance().activateListener(RtnStmt.class);
				//End of added.
				//bugfix, scope deferred to be added here
				ctx.getScope().addScopeNode(withBlock);
			}	
		}
			
		@Override
		public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
			final VjoValidationCtx ctx = event.getValidationCtx();
			final IJstNode jstNode = event.getVisitNode();
			if(jstNode instanceof WithStmt){
				final WithStmt withStmt = (WithStmt)jstNode;
				final IJstNode withBlock = withStmt.getBody();
				ctx.getScope().removeScopeNode(withBlock);
			}
			
			VjoSemanticValidatorRepo.getInstance().activateListeners();
			
			VjoSemanticValidatorRepo.getInstance().removePreAllChildrenListener(this);
			VjoSemanticValidatorRepo.getInstance().registerPreAllChildrenListener(VjoWithStmtValidator.this);
		}
	}
	
	protected static class NestedWithStmtValidator 
		extends VjoSemanticValidator 
		implements IVjoValidationPostChildListener, 
			IVjoValidationPostAllChildrenListener{
		
		@Override
		public List<Class<? extends IJstNode>> getTargetNodeTypes() {
			return s_targetTypes;
		}
		
		@Override
		public void onPostChildEvent(final IVjoValidationVisitorEvent event){
			final VjoValidationCtx ctx = event.getValidationCtx();
			final IJstNode jstNode = event.getVisitNode();
			final IJstNode child = event.getVisitChildNode();
			
			if(jstNode instanceof WithStmt
					&& child instanceof IExpr){
				final WithStmt withStmt = (WithStmt)jstNode;
				final IJstNode withBlock = withStmt.getBody();
				
				//bugfix, scope deferred to be added here
				ctx.getScope().addScopeNode(withBlock);
			}	
		}
			
		@Override
		public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
			final VjoValidationCtx ctx = event.getValidationCtx();
			final IJstNode jstNode = event.getVisitNode();
			if(jstNode instanceof WithStmt){
				final WithStmt withStmt = (WithStmt)jstNode;
				final IJstNode withBlock = withStmt.getBody();
				ctx.getScope().removeScopeNode(withBlock);
				
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(withStmt, ctx.getGroupId(), new String[]{withStmt.toStmtText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().DISCOURAGED_NESTED_WITH, ruleCtx);
			}
		}
	}
}
