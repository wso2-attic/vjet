/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advise properties and methods from an outer JstType. <br>
 * 
 * ProposalData.data IJstProperty and IjstMethod. <b4>
 * 
 * 
 * 
 */
public class VjoCcOuterPropMethodProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoCcOuterPropMethodProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getActingToken();
		IJstType callingType = ctx.getActingType();
		IJstType calledType = ctx.getCalledType();
		if (calledType != null && callingType != calledType) {
			return;
		}
		IJstNode outer = callingType.getParentNode();
		if (outer == null || !(outer instanceof IJstType)) {
			return;
		}
		String innerName = callingType.getName();
		boolean isStatic = callingType.getModifiers().isStatic();
		callingType = (IJstType)outer;
		calledType = callingType;
		appendStaticMembers(calledType, callingType, token, innerName, ctx);
		if (!isStatic) {
			appendInstanceMembers(calledType, callingType, token, innerName, ctx);
		}
		
		
		
	}

	private void appendInstanceMembers(IJstType calledType,
			IJstType callingType, String token, String innerName, VjoCcCtx ctx) {

		boolean isNative = CodeCompletionUtils.isNativeType(calledType);
		IJstType tempCalledType = calledType;
		int[] levels = getCallLevel(callingType, calledType);
		List<String> tempString = new ArrayList<String>();
		List<? extends IJstMethod> methods = JstTypeHelper.getSignatureMethods(calledType, false, true);
		Iterator<? extends IJstMethod> it = methods.iterator();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			if (tempCalledType != method.getOwnerType()) {
				tempCalledType = method.getOwnerType();
				if (tempCalledType == null) {
					tempCalledType = calledType;
				}
				levels = getCallLevel(callingType, tempCalledType);
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
			boolean exactMatch = exactMatch(method.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(method.getName().getName(), token);
			}
			if (exactMatch || basicMatch) {
				addMethod(method, levels, isNative, ctx, tempString, exactMatch);
			}
		}
		List<IJstProperty> properties = calledType.getAllPossibleProperties(false, true);
		Iterator<IJstProperty> it1 = properties.iterator();
		while (it1.hasNext()) {
			IJstProperty property = it1.next();
			if (tempCalledType != property.getOwnerType()) {
				tempCalledType = property.getOwnerType();
				levels = getCallLevel(callingType, tempCalledType);
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
			}
			boolean exactMatch = exactMatch(property.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(property.getName().getName(), token);
			}
			if (levelCheck(property.getModifiers(), levels)
					&& (exactMatch || basicMatch)
					&& (!isNative || !property.getName().getName().startsWith(
							"_"))) {
				appendData(ctx, property, (exactMatch && basicMatch));
			}
		}
		//Inner types
//		List<? extends IJstType> types = calledType.getInstanceEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//				IJstType type = it2.next();
//				if (innerName.equals(type.getName())) {
//					continue;
//				}
//				levels = getCallLevel(callingType, type);
//				boolean exactMatch = exactMatch(type.getSimpleName(), token);
//				boolean basicMatch = exactMatch;
//				if (!exactMatch) {
//					basicMatch = basicMatch(type.getSimpleName(), token);
//				}
//				if (levelCheck(type.getModifiers(), levels)
//						&& (exactMatch || basicMatch)
//						&& (!isNative || !type.getSimpleName().startsWith(
//								"_"))) {
//					appendData(ctx, type, (exactMatch && basicMatch));
//				}
//			}
//		}
	
		
	}

	private void appendStaticMembers(IJstType calledType,
			IJstType callingType, String token, String innerName, VjoCcCtx ctx) {
		IJstType tempCalledType = calledType;
		int[] levels = getCallLevel(callingType, calledType);
		List<? extends IJstMethod> methods = JstTypeHelper.getSignatureMethods(calledType, true, true);
		Iterator<? extends IJstMethod> it = methods.iterator();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			if (tempCalledType != method.getOwnerType()) {
				tempCalledType = method.getOwnerType();
				if (tempCalledType == null) {
					tempCalledType = calledType;
				}
				levels = getCallLevel(callingType, tempCalledType);
			}
			boolean exactMatch = exactMatch(method.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(method.getName().getName(), token);
			}
			if (levelCheck(method.getModifiers(), levels)
					&& (exactMatch || basicMatch)) {
				appendData(ctx, method, (exactMatch && basicMatch));
			}
		}
		List<IJstProperty> properties = calledType.getAllPossibleProperties(true, true);
		Iterator<IJstProperty> it1 = properties.iterator();
		while (it1.hasNext()) {
			IJstProperty property = it1.next();
			if (tempCalledType != property.getOwnerType()) {
				tempCalledType = property.getOwnerType();
				levels = getCallLevel(callingType, tempCalledType);
			}
			boolean exactMatch = exactMatch(property.getName().getName(), token);
			boolean basicMatch = exactMatch;
			if (!exactMatch) {
				basicMatch = basicMatch(property.getName().getName(), token);
			}
			if (levelCheck(property.getModifiers(), levels)
					&& (exactMatch || basicMatch)) {
				appendData(ctx, property, (exactMatch && basicMatch));
			}
		}
		//Inner static types
//		List<? extends IJstType> types = calledType.getStaticEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//				IJstType type = it2.next();
//				if (innerName.equals(type.getName())) {
//					continue;
//				}
//				levels = getCallLevel(callingType, type);
//				boolean exactMatch = exactMatch(type.getSimpleName(), token);
//				boolean basicMatch = exactMatch;
//				if (!exactMatch) {
//					basicMatch = basicMatch(type.getSimpleName(), token);
//				}
//				if (levelCheck(type.getModifiers(), levels)
//						&& (exactMatch || basicMatch)
//						&& (!type.getSimpleName().startsWith(
//								"_"))) {
//					appendData(ctx, type, (exactMatch && basicMatch));
//				}
//			}
//		}
	}

	private void addMethod(IJstMethod method, int[] levels, boolean isNative,
			VjoCcCtx ctx, List<String> tempString, boolean exactMatch) {
		if (!method.isConstructor()
				&& levelCheck(method.getModifiers(), levels)
				&& (!isNative || !method.getName().getName().startsWith("_"))) {
			String str = CodeCompletionUtils.getMthodsStr(method);
			if (!(tempString.contains(str))) {
				tempString.add(str);
				appendData(ctx, method, exactMatch);
			}
		}

	}

	/**
	 * the level should be public, protected, and private. current type (this.),
	 * should show all the method and propertyes. superType: public, and
	 * protected Type in same package: public, default. others: public
	 * 
	 * @param ctx
	 * @return TODO: it should return int[] based on the relationship between
	 *         called type and calling type
	 */
	protected int[] getCallLevel(IJstType callingType, IJstType calledType) {
		return CodeCompletionUtils.getGeneralFieldCallLevel(callingType,
				calledType);
	}

}
