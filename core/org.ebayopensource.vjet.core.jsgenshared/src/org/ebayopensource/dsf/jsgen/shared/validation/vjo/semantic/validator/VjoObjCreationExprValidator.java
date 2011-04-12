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

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.lib.LibManager;

/**
 * refactored by huzhou@ebay.com to handle JstIdentifier and FieldAccessExpr objCreationExpr consistently
 * added VjoSemanticValidator#validateComplexType for constructor type validation
 * added missing import/unresolved type check prior to constructor validation
 * 
 *
 */
public class VjoObjCreationExprValidator 
	extends VjoMtdInvocationExprValidator
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(ObjCreationExpr.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		//skipping  validations
		if(!(jstNode instanceof ObjCreationExpr)){
			return;
		}
		
		final ObjCreationExpr expr = (ObjCreationExpr)jstNode;
		final MtdInvocationExpr mtdInvocationExpr = expr.getInvocationExpr();
		
		if (mtdInvocationExpr != null){
			IExpr identifier = mtdInvocationExpr.getMethodIdentifier();
			if (identifier != null){
				if(identifier instanceof JstIdentifier){
					final JstIdentifier mtdIdentifier = (JstIdentifier)identifier;
					final IJstNode mtdBinding = mtdIdentifier.getJstBinding();
					verifyConstructorBindings(ctx, expr, identifier, mtdIdentifier.getResultType(),
							mtdBinding);
				}
				else if (identifier instanceof FieldAccessExpr) {
					final IJstType fieldAccessExprResultType = ((FieldAccessExpr)identifier).getResultType();
					final IJstNode mtdBinding = ((FieldAccessExpr)identifier).getName().getJstBinding();
					// check access level of constructor
					verifyConstructorBindings(ctx, expr, identifier, fieldAccessExprResultType,
							mtdBinding);
				}
			}
		}
	}

	private void verifyConstructorBindings(final VjoValidationCtx ctx,
			final ObjCreationExpr expr, 
			final IExpr identifier,
			final IJstType bindingType,
			final IJstNode mtdBinding) {
		if(bindingType == null 
				|| bindingType instanceof IInferred 
				|| "Object".equals(bindingType.getName()) 
				|| "type::Object".equals(bindingType.getName())) {
			// skip validation if result type is Object or type::Object
			return;
		}
		else if("vjo.Enum".equals(identifier.toExprText())){
			//report problem;
			final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(expr, ctx.getGroupId(), new String[]{
					identifier.toExprText(), expr.toExprText()});
			satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().CLASS_SHOULD_BE_INSTANTIATABLE, ruleCtx);
			return;
		}
		else if(bindingType != null
				&& (bindingType instanceof IJstRefType
						|| bindingType instanceof JstFuncType
						|| bindingType instanceof JstFunctionRefType
						|| bindingType.isFType())){
			if(!verifyTypeCategory(bindingType)){
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(expr, ctx.getGroupId(), new String[]{
					bindingType.getName(), expr.toExprText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().OBJ_SHOULD_BE_CLASS_TYPE, ruleCtx);
				return;
			}
		} 
		if (mtdBinding instanceof IJstType) {
			if(mtdBinding instanceof IJstRefType){
				validateComplexType(ctx, identifier, identifier.toExprText(), (IJstRefType)mtdBinding);
				if (!ctx.getMissingImportTypes().contains(((IJstRefType)mtdBinding).getReferencedNode())
						&& !ctx.getUnresolvedTypes().contains(((IJstRefType)mtdBinding).getReferencedNode().getName())) {
					
					// constructor is not defined
					final BaseVjoSemanticRuleCtx methodShouldBeDefinedCtx = 
						new BaseVjoSemanticRuleCtx(identifier, ctx.getGroupId(), new String[]{"constructs", expr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_SHOULD_BE_DEFINED, methodShouldBeDefinedCtx);
					return;
				} 
			}
			else{
				validateComplexType(ctx, identifier, identifier.toExprText(), (IJstType)mtdBinding);
				if (!ctx.getMissingImportTypes().contains(mtdBinding)
						&& !ctx.getUnresolvedTypes().contains(((IJstType)mtdBinding).getName())) {
					
					// constructor is not defined
					final BaseVjoSemanticRuleCtx methodShouldBeDefinedCtx = 
						new BaseVjoSemanticRuleCtx(identifier, ctx.getGroupId(), new String[]{"constructs", expr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_SHOULD_BE_DEFINED, methodShouldBeDefinedCtx);
					return;
				}
			}
		} 
		else if (mtdBinding instanceof JstConstructor) {
			
			if (isAbstract(bindingType)) {
				boolean isValid = false;
				IJstType callerType = expr.getOwnerType();
				if (callerType == bindingType ||
					JstTypeHelper.isTypeOf(callerType, bindingType)) {
					if (isInsideConstructor(identifier)) {
						isValid = true;
					}
				}
				if (!isValid) {
					BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(expr, ctx.getGroupId(), new String[]{bindingType.getName(), expr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().CLASS_SHOULD_BE_INSTANTIATABLE, ruleCtx);
					return;
				}
			}
			JstMethod method = (JstMethod)mtdBinding;
			JstModifiers mtdModifiers = method.getModifiers();
			if (!mtdModifiers.isPublic()) {
				//METHOD_SHOULD_BE_VISIBLE
				IJstType callerType = expr.getOwnerType();
				IJstType mtdOwnerType = method.getOwnerType();
				
				boolean isVisible = false;
				if (mtdModifiers.isPrivate()) {
					if (callerType == mtdOwnerType || 
						JstTypeHelper.hasSameRootType(callerType, mtdOwnerType)) {
						isVisible = true;
					}
				}
				else if (mtdModifiers.isProtected()){
					if (callerType == mtdOwnerType ||
						JstTypeHelper.isTypeOf(callerType, mtdOwnerType)) {
						isVisible = true;
					}
				}
				else {
					JstPackage callerPackage = JstTypeHelper.getTruePackage(callerType);
					JstPackage fieldOwnerPackage = JstTypeHelper.getTruePackage(mtdOwnerType);
					if (fieldOwnerPackage == null) {
						isVisible = true;
					}
					else if (callerPackage != null) {
						if (callerPackage.getName().equals(fieldOwnerPackage.getName())) {
							isVisible = true;
						}
					}
				}
				if (!isVisible) {
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().CONSTRUCTOR_SHOULD_BE_VISIBLE,
						new BaseVjoSemanticRuleCtx
							(identifier, ctx.getGroupId(), 
							new String[]{((IExpr)identifier).toExprText(), expr.toExprText()}));
					return;
				}
			}
		}
		else if(mtdBinding == null){
			if(bindingType instanceof IJstRefType){
				if(bindingType.getPackage() != null
						&& "VjoSelfDescribed".equals(bindingType.getPackage().getGroupName())){
					return;
				}
				validateComplexType(ctx, identifier, identifier.toExprText(), (IJstRefType)bindingType);
				if (!ctx.getMissingImportTypes().contains(((IJstRefType)bindingType).getReferencedNode())
						&& !ctx.getUnresolvedTypes().contains(((IJstRefType)bindingType).getReferencedNode().getName())) {
					
					// constructor is not defined
					final BaseVjoSemanticRuleCtx methodShouldBeDefinedCtx = 
						new BaseVjoSemanticRuleCtx(identifier, ctx.getGroupId(), new String[]{"constructs", expr.toExprText()});
					satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().METHOD_SHOULD_BE_DEFINED, methodShouldBeDefinedCtx);
					return;
				} 
			}
			else{
				BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(expr, ctx.getGroupId(), new String[]{bindingType.getName(), expr.toExprText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().CLASS_SHOULD_BE_INSTANTIATABLE, ruleCtx);
				return;
			}
		}
	}

	/**
	 * added by huzhou as a lookup table for type instantiation validities
	 * @param bindingTypeCheck
	 * @return
	 */
	private boolean verifyTypeCategory(final IJstType bindingTypeCheck) {
		return bindingTypeCheck.isClass()
			|| bindingTypeCheck.isEnum();
	}

	private boolean isAbstract(IJstType mtdOwnerType) {
		JstPackage pkg = mtdOwnerType.getPackage();
		if (pkg != null && LibManager.JS_NATIVE_LIB_NAME.equals(pkg.getGroupName())) {
			return false;
		}
		return mtdOwnerType.getModifiers().isAbstract();
	}

	private boolean isInsideConstructor(IExpr mtdInvocationExpr) {
		IJstNode parent = mtdInvocationExpr.getParentNode();
		while (parent != null) {
			if (parent instanceof JstConstructor) {
				return true;
			} else if (parent instanceof IJstType) {
				return false;
			}
			parent = parent.getParentNode();
		}
		return false;
	}
	
}
