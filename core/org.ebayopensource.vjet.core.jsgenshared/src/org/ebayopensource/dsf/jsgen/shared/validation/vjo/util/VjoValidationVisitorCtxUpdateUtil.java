/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationDriver.VjoDependencyVerifierDecorator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoScope;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.term.ObjLiteral;

public class VjoValidationVisitorCtxUpdateUtil {
	
	public static void updateCtxBeforeType(final VjoValidationCtx ctx,
			IJstType node){
//		ctx.setPackage(JstTypeToPackageUtil.lookUpPackage(ctx, node));
		ctx.getScope().addTypeNode(node);
		ctx.getScope().addScopeNode(node);

		//bugfix, introducing all outer/container type's dependencies into current's dependencies
		if(ctx.getMakeParentType(node) != null){
			ctx.setDependencyVerifier(node, new VjoDependencyVerifierDecorator(ctx.getDependencyVerifier(ctx.getMakeParentType(node))));
		}
		if(node.getOuterType() != null){
			ctx.setDependencyVerifier(node, new VjoDependencyVerifierDecorator(ctx.getDependencyVerifier(ctx.getTypeSpaceType(node.getOuterType()))));
		}
		
//		JstTypeSymbolLoadUtil.loadCompleteJstTypeSymbols(ctx, node);
		ThisKeywordUtil.supportThis(ctx, node, true);
	}
	
	public static void updateCtxAfterType(final VjoValidationCtx ctx, 
			IJstType node){
//		ThisKeywordUtil.unsupportThis(ctx, node, true);
		
		ctx.getScope().removeScopeNode(node);
		ctx.getScope().removeTypeNode(node);
		
//		JstTypeSymbolLoadUtil.unloadCompleteJstTypeSymbols(ctx, node);
	}
	
	public static void updateCtxBeforeObjLiteral(final VjoValidationCtx ctx,
			ObjLiteral node){
//		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
//		//support this in vjo.make
		final IJstType makeType = ctx.getMakeTypeByObjLiteral(node);
		if(makeType != null){
			//taken care in VjoMtdInvocationExpr#validateVjoMake
			return;
		}
//		
		IJstType typeScope = new JstObjectLiteralType("ObjLiteral");
		ctx.getScope().addTypeNode(typeScope);
		ctx.getScope().addScopeNode(typeScope);
//		
//		IVjoSymbol thisSymbol = new VjoSymbol();
//		thisSymbol.setName("this");
//		thisSymbol.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE);
//		thisSymbol.setDeclareType(typeScope);
//		thisSymbol.setStaticReference(false);
//		symbolTable.addSymbolInScope(typeScope, thisSymbol);
//		
//		for(NV nv : node.getNVs()){
//			final String name = nv.getName();
//			final IExpr value = nv.getValue();
//			
//			IVjoSymbol propertySymbol = new VjoSymbol();
//			propertySymbol.setName(name);
//			if(value instanceof FuncExpr){
//				propertySymbol.setSymbolType(EVjoSymbolType.INSTANCE_FUNCTION);
//			}
//			else{
//				propertySymbol.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE);
//			}
//			propertySymbol.setDeclareType(null);
//			propertySymbol.setDeclareNode(value);
//			propertySymbol.setStaticReference(false);
//			symbolTable.addSymbolInScope(typeScope, propertySymbol);
//		}
	}
	
	public static void updateCtxAfterObjLiteral(final VjoValidationCtx ctx,
			ObjLiteral node){
		//support this in vjo.make
		final IJstType makeType = ctx.getMakeTypeByObjLiteral(node);
		if(makeType != null){
			//taken care in VjoMtdInvocationExpr#validateVjoMake
			return;
		}
		
		final IJstType typeScope = ctx.getScope().getClosestTypeScopeNode();
		ctx.getScope().removeScopeNode(typeScope);
		ctx.getScope().removeTypeNode(typeScope);
	}
	
	public static void updateCtxBeforeMethod(final VjoValidationCtx ctx,
			JstMethod node){
		//bugfix, javascript doesn't have block scope, only function scope is available
		ctx.getScope().addScopeNode(node);
		
		final VjoScope ctxScope = ctx.getScope();
		if(node.isStatic()){
			ctxScope.setStatic(true);
		}
		else{
			ctxScope.setStatic(false);
			ThisKeywordUtil.supportThis(ctx, node, false);
		}
	}
	
	public static void updateCtxBeforeProperty(final VjoValidationCtx ctx,
			JstProperty node){
		
		if(!node.isStatic()){
			ctx.getScope().addScopeNode(node);
			ctx.getScope().setStatic(false);
			ThisKeywordUtil.supportThis(ctx, node, node.isStatic());
		}
		
		final IJstType ownerType = node.getOwnerType();
		final Set<IJstType> unintializedTypes = ctx.getDependencyVerifier(ownerType).getDirectDependenciesFilteredByGroup(ownerType);
		for(IJstType uninitializedType: unintializedTypes){
			ctx.addUnintializedType(uninitializedType);
		}
	}
	
	public static void updateCtxAfterProperty(final VjoValidationCtx ctx,
			JstProperty node){
		
		if(!node.isStatic()){
			ThisKeywordUtil.unsupportThis(ctx, node, node.isStatic());
			ctx.getScope().removeScopeNode(node);
			ctx.getSymbolTable().removeSymbolsInScope(node);
		}
		
		final IJstType ownerType = node.getOwnerType();
		final Set<IJstType> unintializedTypes = ctx.getDependencyVerifier(ownerType).getDirectDependenciesFilteredByGroup(ownerType);
		for(IJstType uninitializedType: unintializedTypes){
			ctx.removeUnintializedType(uninitializedType);
		}
	}

	public static void updateCtxAfterMethod(final VjoValidationCtx ctx,
			JstMethod node){
		
		if(!node.isStatic()){
			ThisKeywordUtil.unsupportThis(ctx, node, false);
		}
		
		//bugfix, javascript doesn't support block scope, only function scope
		ctx.getScope().removeScopeNode(node);
		ctx.getSymbolTable().removeSymbolsInScope(node);
	}
	
}
