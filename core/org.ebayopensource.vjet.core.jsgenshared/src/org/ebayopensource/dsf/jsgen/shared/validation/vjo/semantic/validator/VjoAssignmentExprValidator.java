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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.JstBindingUtil;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;

public class VjoAssignmentExprValidator 
	extends VjoSemanticValidator 
	implements 
//	IVjoValidationPreChildListener,
//		IVjoValidationPostChildListener,
		IVjoValidationPostAllChildrenListener {
	
	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(AssignExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		final AssignExpr assignExpr = (AssignExpr)jstNode;
		
		if(assignExpr.getExpr() == null){//TODO JstVar could have no RHS;
			return;
		}
		
		// add error arguments
		String[] arguments = new String[3];
		arguments[0] = assignExpr.getExpr() != null ? assignExpr.getExpr().toExprText() : "NULL";
		arguments[1] = assignExpr.getLHS() != null ? assignExpr.getLHS().toLHSText() : "NULL";
		arguments[2] = assignExpr.toExprText();
		
		final ILHS lhs = assignExpr.getLHS();
		final IExpr rhs = assignExpr.getExpr();
		
		if(rhs instanceof BoolExpr){
//			System.out.println("rhs =" + rhs);
		}
		
		IJstType lhsValue = null;
		if(lhs instanceof IExpr){
			lhsValue = ((IExpr)lhs).getResultType();
			if(lhsValue != null && lhsValue != null){
				arguments[0] = displayType(lhsValue);
			}
		}
		else if(lhs instanceof JstVar){
			lhsValue = null;
		}
		IJstType rhsValue = rhs.getResultType();
		if(rhsValue != null && rhsValue != null){
			arguments[1] = displayType(rhsValue);
		}
		
		if(lhsValue != null && lhsValue != null){
			if(lhsValue.isEnum()){
				//check binding
				final IJstNode jstBinding = JstBindingUtil.getJstBinding(lhs);
				if(jstBinding != null && jstBinding instanceof IJstProperty){
					final IJstProperty jstBindingProperty = (IJstProperty)jstBinding;
					if(lhsValue.equals(jstBindingProperty.getOwnerType())
							&& lhsValue.getEnumValues().contains(jstBindingProperty)){//bugfix by roy, add check that the enum is actually enum values in the etype
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_ENUM, ruleCtx);
						return;
					}
				}
			}
		}
		
		final List<IJstType> lhsTypes = new ArrayList<IJstType>();
		final List<IJstType> rhsTypes = new ArrayList<IJstType>();
		final IJstNode lhsBinding = JstBindingUtil.getJstBinding(lhs);
		if(lhsBinding != null && lhsBinding instanceof JstArg){
			lhsTypes.addAll(((JstArg)lhsBinding).getTypes());
		}
		else if(lhsValue != null){
			lhsTypes.add(lhsValue);
		}
		final IJstNode rhsBinding = JstBindingUtil.getJstBinding(rhs);
		if(rhsBinding != null && rhsBinding instanceof JstArg){
			for (IJstType argType : ((JstArg)rhsBinding).getTypes()) {
				if (argType instanceof JstProxyType) {
					rhsTypes.add(((JstProxyType)argType).getType());
				}
				else {
					rhsTypes.add(argType);
				}				
			}
		}
		else if(rhsValue != null && !isBoolean(rhsValue)){
			rhsTypes.add(rhsValue);
		}
		
		if(lhsTypes.size() > 0 && rhsTypes.size() > 0){
			if(!TypeCheckUtil.isAssignable(lhsTypes, rhsTypes)){//bugfix 5351 to narrow down the error target to lhs 
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), arguments);
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ASSIGNABLE, ruleCtx);
			}
			//TODO check assignment of 2 function types, signature must satisfy the following
			//let lhs = f1, rhs = f2
			//{f2 and overload(f2)} >= {f1 and overload(f1)}
//			if(lhsValue != null && "Function".equals(lhsValue.getName())
//					&& rhsValue != null && "Function".equals(rhsValue.getName())
//					&& lhsBinding instanceof IJstMethod 
//					&& rhsBinding instanceof IJstMethod){
//				final IJstMethod lhsMethod = (IJstMethod)lhsBinding;
//				final IJstMethod rhsMethod = (IJstMethod)rhsBinding;
//				if(!TypeCheckUtil.isAssignable(lhsMethod, rhsMethod)){
//					final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), arguments);
//					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().ASSIGNABLE, ruleCtx);
//				}
//			}
		}
		
		//TODO remove the assignment post-impact logic
		//update the symbol's assigned type and hold expression value
		final IJstNode jstBinding = lhsBinding;
		if(jstBinding != null){
			if(jstBinding instanceof IJstProperty){
				final IJstProperty jstPtyBinding = (IJstProperty)jstBinding;
				//check if it's a final field being changed
				if(jstPtyBinding.getModifiers().isFinal()){
					//report problem
					if(!isTypeInitialization(assignExpr, ctx.getScope().getClosestTypeScopeNode(), jstPtyBinding.getModifiers().isStatic())){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
					}
					else {
						if (isAssignmentInConstructor(assignExpr, ctx.getScope().getClosestTypeScopeNode())) {
							// Yubin 11/9/09 don't check assignment if inside constructor since we don't know the branching of assignment
							//if(ctx.getPropertyStatesTable().hasAssigned(jstPtyBinding)){
								//final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
								//satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
							//} else {
								JstMethod method = getParentConstMethod(assignExpr);
								if (method != null) {
									ctx.addFinalProperty(jstPtyBinding, method);
								} else {
									ctx.getPropertyStatesTable().assign(jstPtyBinding);
								}
							//}
						}else {
							// Yubin 11/9/09 don't check assignment if inside class initializer
							//if(ctx.getPropertyStatesTable().hasAssigned(jstPtyBinding)){
								//final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
								//satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
							//}
							//else{
								ctx.getPropertyStatesTable().assign(jstPtyBinding);
							//}
						}
					}
				}
			}
			else if(jstBinding instanceof JstVars){
				final JstVars jstVarsBinding = (JstVars)jstBinding;
				if(jstVarsBinding.isFinal()){
					if(!isSonOf(assignExpr, jstVarsBinding)){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
					}
				}
			}
			else if(jstBinding instanceof JstArg){
				final JstArg jstArgBinding = (JstArg)jstBinding;
				if(jstArgBinding.isFinal()){
					if(!isSonOf(assignExpr, jstArgBinding)){
						final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
						satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
					}
				}
			}
			else if(jstBinding instanceof ILHS){
				final IJstNode parent = jstBinding.getParentNode();
				if(parent instanceof AssignExpr){
					final IJstNode grandParent = parent.getParentNode();
					if(grandParent instanceof JstVars){
						final JstVars jstVarsBinding = (JstVars)grandParent;
						if(jstVarsBinding.isFinal()){
							if(!isSonOf(assignExpr, jstVarsBinding)){
								final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(lhs, ctx.getGroupId(), new String[]{lhs.toLHSText()});
								satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().SHOULD_NOT_ASSIGN_TO_FINAL, ruleCtx);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isBoolean(IJstType rhsValue) {
		return rhsValue.getName().equals("Boolean");
	}

	private boolean isTypeInitialization(IJstNode assignExpr, IJstType ownerType, boolean isStaticProperty){
		if(isStaticProperty){
			for(IStmt stmt : ownerType.getStaticInitializers()){
				if(isSonOf(assignExpr, stmt)){
					return true;
				}
			}
		}
		else{
			for(IStmt stmt : ownerType.getInstanceInitializers()){
				if(isSonOf(assignExpr, stmt)){
					return true;
				}
			}
			
			if(isAssignmentInConstructor(assignExpr, ownerType)) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isAssignmentInConstructor(IJstNode assignExpr,
			IJstType ownerType) {
		if(ownerType.getConstructor() != null){
			if(isSonOf(assignExpr, ownerType.getConstructor())){
				return true;
			}
		}
		
		// If assignment is done in the overloaded dispatched constructor,
		// then we consider that a type initialization in constructor
		JstMethod method = getParentConstMethod(assignExpr);
		if (method != null) {
			return JstTypeHelper.isConstructor(method);
		}
		return false;
	}
	
	private JstMethod getParentConstMethod(IJstNode assignExpr) {
		IJstNode parent = assignExpr.getParentNode();
		while (parent != null) {
			if (parent instanceof JstMethod) {
				JstMethod mtd = (JstMethod) parent;
				if (mtd.getModifiers().isPrivate()) {
					return mtd;
				}
			} else if (parent instanceof IJstType) {
				return null;
			}
			parent = parent.getParentNode();
		}
		return null;
	}

	private boolean isSonOf(IJstNode child, IJstNode parent){
		if(child == null){
			return false;
		}
		else if(child.equals(parent)){
			return true;
		}
		
		return isSonOf(child.getParentNode(), parent);
	}
}
