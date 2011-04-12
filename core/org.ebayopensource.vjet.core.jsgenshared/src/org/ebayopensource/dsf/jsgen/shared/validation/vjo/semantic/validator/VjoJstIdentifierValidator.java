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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.NV;

public class VjoJstIdentifierValidator 
	extends VjoSemanticValidator
	implements
		IVjoValidationPreAllChildrenListener,
		IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstIdentifier.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		
		if(jstNode instanceof JstIdentifier){
			if(jstNode.getParentNode() instanceof NV){
				// DO NOT VALIDATE IDENTIFIERS in OL
				return;
			}
			final JstIdentifier jstIdentifier = (JstIdentifier)jstNode;
			final String jstIdentifierName = jstIdentifier.getName();
			final InvalidIdentifierNameRuleCtx ruleCtx = new InvalidIdentifierNameRuleCtx(jstIdentifier, ctx.getGroupId(), new String[]{jstIdentifierName}, jstIdentifierName);
			
			satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().INVALID_IDENTIFIER, ruleCtx);
		}
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(jstNode instanceof JstIdentifier){
			//to validate jst identifier as a standalone expression
			//report problem
			final JstIdentifier jstIdentifier = (JstIdentifier)jstNode;
			//skip FieldAccessExpr/MtdInvocationExpr/ObjCreationExpr
			final IJstNode parentNode = jstIdentifier.getParentNode();
			if(parentNode != null){
				if((parentNode instanceof FieldAccessExpr 
						&& ((FieldAccessExpr)parentNode).getName() == jstIdentifier)
						|| parentNode instanceof LabeledStmt
						|| parentNode instanceof BreakStmt
						|| parentNode instanceof ContinueStmt
					//	|| (parentNode instanceof AssignExpr && parentNode.getParentNode() != null && parentNode.getParentNode() instanceof JstInitializer)
						|| (parentNode instanceof MtdInvocationExpr 
								&& ((MtdInvocationExpr)parentNode).getMethodIdentifier() == jstIdentifier
								&& !(parentNode.getParentNode() != null && parentNode.getParentNode() instanceof ObjCreationExpr && ((MtdInvocationExpr)parentNode).getQualifyExpr() == null))
						|| parentNode instanceof PrefixExpr && PrefixExpr.Operator.TYPEOF.equals(((PrefixExpr)parentNode).getOperator())){
					return;
				}
				if(parentNode instanceof NV){
					if (((NV)parentNode).getIdentifier() == jstNode) {
						// DO NOT VALIDATE IDENTIFIERS in name field
						return;
					}
				}
			}
			
			if(jstIdentifier.getJstBinding() == null){
				if(parentNode instanceof AssignExpr && (((AssignExpr)parentNode).getLHS() == jstIdentifier) ||
				   parentNode instanceof ForInStmt && (((ForInStmt)parentNode).getVar() == jstIdentifier)) {
					//report a different problem
				    //Added by Eric.Ma on 20100629 for cast statement type is empty; such ://<<
				    if(parentNode instanceof AssignExpr && ((AssignExpr) parentNode).getExpr() != null){
				        if(((AssignExpr) parentNode).getExpr() instanceof CastExpr){
				            return;
				        }
				    }
				    
				    if(parentNode.getParentNode() instanceof JstVar
				    		|| parentNode.getParentNode() instanceof JstVars){
				    	return;
				    }
				    //End of added.
					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstIdentifier, ctx.getGroupId(), new String[]{jstIdentifier.getName(), jstIdentifier.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().IMPLICIT_GLOBAL_VARIABLE_DECLARED, ruleCtx);
				}
				else{
					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstIdentifier, ctx.getGroupId(), new String[]{jstIdentifier.getName(), jstIdentifier.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().VARIABLE_SHOULD_BE_DEFINED, ruleCtx);
				}
			}
		}
	}
}
