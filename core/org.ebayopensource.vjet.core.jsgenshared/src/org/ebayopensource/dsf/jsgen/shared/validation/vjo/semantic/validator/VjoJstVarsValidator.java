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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameWithKeywordRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.ILHS;

/**
 * refactored @ Oct/11/2009
 * all symbol tables logic in validation is to be removed
 * 
 *
 */
public class VjoJstVarsValidator 
	extends VjoSemanticValidator
	implements IVjoValidationPreAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstVars.class);
		s_targetTypes.add(JstVar.class);
	}
	
	private static final String[] VJO_KEYWORDS = {
		"vjo"
	};
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
		if(jstNode instanceof JstVars){
			final JstVars jstVars = (JstVars)jstNode;
			for(int i = 0, len = jstVars.getAssignments().size(); i < len; i++){
				AssignExpr assignement = jstVars.getAssignments().get(i);
				
				final IJstNode jstInitializerNode = assignement.getParentNode();
				final IJstNode jstVarsNode2 = jstInitializerNode != null ? jstInitializerNode.getParentNode() : null;
				final IJstNode jstBlock = jstVarsNode2 != null ? jstVarsNode2.getParentNode() : null;
				final IJstNode jstMethodNode = jstBlock != null ? jstBlock.getParentNode() : null;
				
				//skip arguments assignment error
				if(jstMethodNode != null){
					if(jstMethodNode instanceof IJstMethod){
						if(jstBlock.getChildren().size() > 0 &&
								jstVarsNode2.equals(jstBlock.getChildren().get(0))){
							continue;//check next assignment
						}
					}
				}
				
				ILHS lhs = (ILHS)assignement.getLHS();
				if(lhs instanceof JstIdentifier){
					final String lhsName = ((JstIdentifier)lhs).getName();
					final InvalidIdentifierNameWithKeywordRuleCtx idRuleCtx = new InvalidIdentifierNameWithKeywordRuleCtx(
							lhs, ctx.getGroupId(), 
							new String[]{lhsName, lhsName}, 
							lhsName, 
							lhs.getOwnerType() != null && lhs.getOwnerType().isEnum());
					
					final InvalidIdentifierNameWithKeywordRuleCtx idRuleCtx2 = new InvalidIdentifierNameWithKeywordRuleCtx(
							lhs, ctx.getGroupId(), 
							new String[]{lhsName, lhsName}, 
							lhsName, 
							VJO_KEYWORDS);
					
					if(satisfyRule(ctx, ruleRepo.INVALID_IDENTIFIER_WITH_KEYWORD, idRuleCtx)){
						satisfyRule(ctx, ruleRepo.INVALID_IDENTIFIER_WITH_KEYWORD, idRuleCtx2);
					}
					
					final JstMethod jstMethod = lookUpMethod(jstNode);
					final List<IJstNode> scopedVars = ctx.getMethodControlFlowTable().lookUpScopedVars(jstMethod);
					boolean addScopedVar = true;
					for(IJstNode scopedVar : scopedVars){
						if(scopedVar != null){
							String scopedVarName = null;
							IJstType scopedVarType = null;
							if(scopedVar instanceof JstIdentifier){
								scopedVarName = ((JstIdentifier)scopedVar).getName();
								scopedVarType = ((JstIdentifier)scopedVar).getResultType();
							}
							else if(scopedVar instanceof JstVar){
								scopedVarName = ((JstVar)scopedVar).getName();
								scopedVarType = ((JstVar)scopedVar).getType();
							}
							else if(scopedVar instanceof JstArg){
								scopedVarName = ((JstArg)scopedVar).getName();
								scopedVarType = ((JstArg)scopedVar).getType();
							}
							else if(scopedVar instanceof JstName){
								scopedVarName = ((JstName)scopedVar).getName();
							}
							

							
							if(lhsName.equals(scopedVarName)) {
								IJstType varType = jstVars.getType();
								if (varType instanceof IInferred) {
									varType = JstCache.getInstance().getType("Object");
								}
								if (scopedVarType instanceof IInferred) {
									scopedVarType = JstCache.getInstance().getType("Object");
								}
								//bugfix by roy, report redeclared local only if the declared type doesn't comply with previous declared type
								if (scopedVarType != null
									&& varType != null
									&& scopedVarType.getName() != null
									&& !"ERROR_UNDEFINED_TYPE".equals(scopedVarType.getName())//in case scoped var is a jstvar
									&& !scopedVarType.getName().equals(varType.getName())){
									//fire rules, duplicate symbols
									final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
									satisfyRule(ctx, ruleRepo.VARIABLE_ALREADY_DEFINED, ruleCtx);
									addScopedVar = false;
									break;
								}
							}
						}
					}
					if(addScopedVar){
						ctx.getMethodControlFlowTable().addScopedVar(jstMethod, (JstIdentifier)lhs);
					}
					
					//if it's the last var, we need to check the annotation for type
					IJstType declaredType = ((JstIdentifier)lhs).getType();
					if(i == len - 1 && jstVars.getType() != null){
						declaredType = jstVars.getType();
						if (declaredType instanceof IInferred) {
							continue; //skip inferred type
						}
						
						//enhanced by huzhou to validate JstAttributedType, JstTypeWithArgs etc.
						validateComplexType(ctx, (JstIdentifier)lhs, ((JstIdentifier)lhs).toExprText(), declaredType);
					}
				}
			}
		}
		else if(jstNode instanceof JstVar){
			final JstVar jstVar = (JstVar)jstNode;
			final String lhsName = jstVar.getName();
			
			final InvalidIdentifierNameWithKeywordRuleCtx idRuleCtx = new InvalidIdentifierNameWithKeywordRuleCtx(
					jstVar, ctx.getGroupId(), 
					new String[]{lhsName, lhsName}, 
					lhsName, 
					jstVar.getOwnerType() != null && jstVar.getOwnerType().isEnum());
			
			final InvalidIdentifierNameWithKeywordRuleCtx idRuleCtx2 = new InvalidIdentifierNameWithKeywordRuleCtx(
					jstVar, ctx.getGroupId(), 
					new String[]{lhsName, lhsName}, 
					lhsName, 
					VJO_KEYWORDS);
			
			if(satisfyRule(ctx, ruleRepo.INVALID_IDENTIFIER_WITH_KEYWORD, idRuleCtx)){
				satisfyRule(ctx, ruleRepo.INVALID_IDENTIFIER_WITH_KEYWORD, idRuleCtx2);
			}
			
			final JstMethod jstMethod = lookUpMethod(jstNode);
			final List<IJstNode> scopedVars = ctx.getMethodControlFlowTable().lookUpScopedVars(jstMethod);
			
			boolean addScopedVar = true;
			for(IJstNode scopedVar : scopedVars){
				if(scopedVar != null){
					String scopedVarName = null;
					IJstType scopedVarType = null;
					if(scopedVar instanceof JstIdentifier){
						scopedVarName = ((JstIdentifier)scopedVar).getName();
					}
					else if(scopedVar instanceof JstVar){
						scopedVarName = ((JstVar)scopedVar).getName();
						scopedVarType = ((JstVar)scopedVar).getType();
					}
					else if(scopedVar instanceof JstArg){
						scopedVarName = ((JstArg)scopedVar).getName();
					}
					else if(scopedVar instanceof JstName){
						scopedVarName = ((JstName)scopedVar).getName();
					}
					
					if(lhsName.equals(scopedVarName)
							&& jstVar.getType() != null
							&& !"ERROR_UNDEFINED_TYPE".equals(jstVar.getType().getName())
							&&  scopedVarType != null
							&& !scopedVarType.getName().equals(jstVar.getType().getName())){
						//fire rules, duplicate symbols
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(jstVar, ctx.getGroupId(), new String[]{jstVar.toLHSText()});
						satisfyRule(ctx, ruleRepo.VARIABLE_ALREADY_DEFINED, ruleCtx);
						addScopedVar = false;
						break;
					}
				}
			}
			if(addScopedVar){
				ctx.getMethodControlFlowTable().addScopedVar(jstMethod, jstVar);
			}
			
			//if it's the last var, we need to check the annotation for type
			validateComplexType(ctx, jstVar, jstVar.getName(), jstVar.getType());
		}
	}
}
