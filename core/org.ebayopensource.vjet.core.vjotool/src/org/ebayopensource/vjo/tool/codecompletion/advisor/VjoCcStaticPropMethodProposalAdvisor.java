/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcStaticPropMethodProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoCcStaticPropMethodProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getActingToken();
		IJstType callingType = ctx.getJstType();
		IJstType calledType = ctx.getCalledType();
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
//				List<IJstMethod> overloads = method.getOverloaded();
//				if (overloads != null) {
//					Iterator<IJstMethod> it1 = overloads.iterator();
//					while (it1.hasNext()) {
//						appendData(ctx, it1.next(), (exactMatch && basicMatch));
//					}
//				}
			}
		}
		if (calledType == null) {
			return;
		}
		List<IJstProperty> properties = calledType.getAllPossibleProperties(true, !(calledType instanceof IJstRefType));
	
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
					&& (exactMatch || basicMatch)
					&& specialRule(ctx, tempCalledType, property)) {
				appendData(ctx, property, (exactMatch && basicMatch));
			}
		}
		//Inner static types
//		List<? extends IJstType> types = calledType.getStaticEmbededTypes();
//		if (types != null && !types.isEmpty()) {
//			Iterator<? extends IJstType> it2 = types.iterator();
//			while (it2.hasNext()) {
//				IJstType type = it2.next();
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

	protected int[] getCallLevel(IJstType callingType, IJstType calledType) {
		return CodeCompletionUtils.getGeneralFieldCallLevel(callingType,
				calledType);
	}

	protected boolean specialRule(VjoCcCtx ctx, IJstType jstType,
			IJstProperty jstProperty) {
		// vjo.NEEDS_IMPL should not be proposed in function.
		Object info = ctx.getInfo(VjoCcCtx.INFO_KEY_IN_TYPE_SCOPE);
		boolean hideIt = false;
		if (info != null && (info instanceof Boolean)) {
			hideIt = true;
		}
		if (hideIt) {
			if ("vjo".equals(jstType.getSimpleName())
					&& "NEEDS_IMPL".equals(jstProperty.getName().getName())) {
				return false;
			}
		}

		return true;

	}

}
