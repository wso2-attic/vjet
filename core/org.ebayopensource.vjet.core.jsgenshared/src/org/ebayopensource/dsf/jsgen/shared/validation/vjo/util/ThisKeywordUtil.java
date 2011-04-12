/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

public class ThisKeywordUtil {
	
	public static void supportThis(final VjoValidationCtx ctx, 
			final IJstNode node, boolean isStatic) {
		//support this
//		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
//		final IJstType thisType = ctx.getScope().getClosestTypeScopeNode();
//		
//		IVjoSymbol thisSymbol = symbolTable.getSymbolInScope(ctx.getClosestScope(), "this", EVjoSymbolType.LOCAL_VARIABLE);
//		if(thisSymbol != null){
//			return; //already defined
//		}
//		
//		thisSymbol = new VjoSymbol()
//			.setName("this")
//			.setSymbolType(EVjoSymbolType.LOCAL_VARIABLE)
//			.setDeclareType(isStatic ? new JstTypeRefType(thisType) : thisType)
//			.setStaticReference(isStatic);
//		symbolTable.addSymbolInScope(ctx.getClosestScope(), thisSymbol);
		
//		//support vj$
//		IVjoSymbol vjSymbol = symbolTable.getSymbolInScope(thisType, "vj$", isStatic?EVjoSymbolType.STATIC_VARIABLE: EVjoSymbolType.INSTANCE_VARIABLE);
//		IJstType vjType = vjSymbol != null ? vjSymbol.getDeclareType() : null;
//		if(vjType == null){
//			vjType = new JstObjectLiteralType("vj$");
//			vjSymbol = new VjoSymbol()
//			.setName("vj$")
//			.setSymbolType(isStatic ? EVjoSymbolType.STATIC_VARIABLE : EVjoSymbolType.INSTANCE_VARIABLE)
//			.setDeclareType(vjType)
//			.setStaticReference(false);
//			symbolTable.addSymbolInScope(thisType, vjSymbol);
//		}
//		
//		for(IJstType dependentType : ctx.getDependencies(thisType)){
//			//get simple name could give the alias
//			final String dependentTypeName = getSimpleName(thisType, dependentType);
//			if(dependentTypeName != null){
//				IVjoSymbol dependentTypeSymbol = symbolTable.getSymbolInScope(vjType, dependentTypeName, EVjoSymbolType.INSTANCE_VARIABLE);
//				if(dependentTypeSymbol == null){
//					dependentTypeSymbol = new VjoSymbol()
//						.setName(dependentTypeName)
//						.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//						.setDeclareType(new JstTypeRefType(dependentType))
//						.setStaticReference(true);
//					symbolTable.addSymbolInScope(vjType, dependentTypeSymbol);
//				}
//				else{//bugfix 7832, vj$ type resolved by backend still have issue in it
//					dependentTypeSymbol.setDeclareType(new JstTypeRefType(dependentType))
//						.setStaticReference(true);;
//				}
//			}
//		}
//		
//		//support local type
//		if("$anonymous_vjo_make$".equals(thisType.getName())){
//			final IJstType anonymousParentType = ctx.getMakeParentType(thisType);
//			final IJstType anonymousScopeType = ctx.getMakeScopeType(thisType);
//			final IJstType anonymousSourceType = ctx.getMakeSourceType(thisType);
//			
//			JstSymbolReplicateUtil.replicate(ctx, anonymousScopeType, thisType, EVjoSymbolType.INSTANCE_VARIABLE);
//			JstSymbolReplicateUtil.replicate(ctx, anonymousSourceType, thisType, EVjoSymbolType.INSTANCE_VARIABLE);
//			
//			IVjoSymbol parentSymbol = symbolTable.getSymbolInScope(vjType, "parent", EVjoSymbolType.INSTANCE_VARIABLE);
//			if(parentSymbol == null){
//				parentSymbol = new VjoSymbol()
//				.setName("parent")
//				.setDeclareType(anonymousParentType)
//				.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//				.setStaticReference(false);
//				
//				symbolTable.addSymbolInScope(vjType, parentSymbol);
//			}
//		}
//		
//		//support inner type's outer type
//		if(thisType.getOuterType() != null){
//			final IJstType outerType = thisType.getOuterType();
//			
//			//to support outer type's instance reference
//			if(!thisType.getModifiers().isStatic()){
//				IVjoSymbol outerTypeSymbolShort = symbolTable.getSymbolInScope(vjType, "parent", EVjoSymbolType.INSTANCE_VARIABLE);
//				if(outerTypeSymbolShort == null){
//					outerTypeSymbolShort = new VjoSymbol()
//						.setName("outer")
//						.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//						.setDeclareType(ctx.getTypeSpaceType(outerType))
//						.setStaticReference(false);
//					symbolTable.addSymbolInScope(vjType, outerTypeSymbolShort);
//				}
//			}
//			
//			final IVjoSymbol staticVjSymbolUnderOuterType = 
//				symbolTable.getSymbolInScope(ctx.getTypeSpaceType(outerType), "vj$", EVjoSymbolType.STATIC_VARIABLE);
//			if(staticVjSymbolUnderOuterType != null){
//				IVjoSymbol instanceVjSymbolUnderOuterType = 
//					symbolTable.getSymbolInScope(ctx.getTypeSpaceType(outerType), "vj$", EVjoSymbolType.INSTANCE_VARIABLE);
//				if(instanceVjSymbolUnderOuterType == null){
//					instanceVjSymbolUnderOuterType = new VjoSymbol()
//						.setName(staticVjSymbolUnderOuterType.getName())
//						.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//						.setDeclareNode(staticVjSymbolUnderOuterType.getDeclareNode())
//						.setDeclareType(staticVjSymbolUnderOuterType.getDeclareType())
//						.setAssignedType(staticVjSymbolUnderOuterType.getAssignedType())
//						.setStaticReference(staticVjSymbolUnderOuterType.isStaticReference())
//						.setVisible(staticVjSymbolUnderOuterType.isVisible());
//					symbolTable.addSymbolInScope(ctx.getTypeSpaceType(outerType), instanceVjSymbolUnderOuterType);
//				}
//			}
//		}
//		
//		//to support inner types
//		for(IJstType innerType : thisType.getEmbededTypes()){
//			final String innerTypeName = innerType.getSimpleName();
//			if(innerTypeName != null){
//				final IVjoSymbol innerTypeSymbol = new VjoSymbol()
//					.setName(innerTypeName)
//					.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//					.setDeclareType(new JstTypeRefType(innerType))
//					.setStaticReference(true);
//				symbolTable.addSymbolInScope(vjType, innerTypeSymbol);
//			}
//		}
//		
//		//to support base
//		if(!isStatic && thisType.getExtend() != null){
//			final IVjoSymbol baseSymbol = new VjoSymbol()
//				.setName("base")
//				.setSymbolType(EVjoSymbolType.INSTANCE_VARIABLE)
//				.setDeclareType(thisType.getExtend())
//				.setStaticReference(false);
//			symbolTable.addSymbolInScope(thisType, baseSymbol);
//		}
	}

	public static void unsupportThis(final VjoValidationCtx ctx, 
			final IJstNode node, boolean isStatic) {
		//support this
//		final VjoSymbolTable symbolTable = ctx.getSymbolTable();
//		final IJstType thisType = ctx.getScope().getClosestTypeScopeNode();
		
//		//support vj$
//		final IVjoSymbol vjSymbol = symbolTable.getSymbolInScope(thisType, "vj$", isStatic ? EVjoSymbolType.STATIC_VARIABLE : EVjoSymbolType.INSTANCE_VARIABLE);
//		if(vjSymbol != null){
//			symbolTable.removeSymbolInScope(thisType, vjSymbol);
//		}
	}

	private static String getSimpleName(final IJstType ownerType, final IJstType depType){
		final Map<String, ? extends IJstType> candidateAliasMap = ownerType.getImportsMap();
		if(candidateAliasMap != null){
			for(Map.Entry<String, ? extends IJstType> entry : candidateAliasMap.entrySet()){
				if(depType.getName() != null 
						&& depType.getName().equals(entry.getValue().getName())){
					return entry.getKey();
				}
			}
		}
		return depType.getSimpleName();
	}
}
