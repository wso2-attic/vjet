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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * advise override proposals, UI side can create override proposal based on the
 * proposal data example: .protos { xx<cursor> }
 * 
 * need attributes: 1. ctx.actingToken 2. ctx.calledType
 * 
 * ProposalData: IJstMethod
 * 
 * 
 * 
 */
public class VjoCcOverrideProposalAdvisor extends AbstractVjoCcAdvisor {

	public static final String ID = VjoCcOverrideProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getToken();
		IJstType callingType = ctx.getJstType();
		List<String> tempList = getMethodsString(callingType);
		List<IJstType> interfaceList = new ArrayList<IJstType>();
		int[] levels = getCallLevel(null, null);
		// parent type
		IJstType parentType = callingType.getExtend();
		
		appendInterface(interfaceList, callingType);
		if (parentType != null) {
			addMethods(parentType, levels, tempList, token, ctx, interfaceList);
		}
		// interface
		if (!interfaceList.isEmpty()) {
			Iterator<? extends IJstType> it = interfaceList.iterator();
			while (it.hasNext()) {
				parentType = it.next();
				addMethods(parentType, levels, tempList, token, ctx, interfaceList);
			}
		}
	}

	private void appendInterface(List<IJstType> interfaceList,
			IJstType callingType) {
		if (callingType.isInterface()) {
			return;
		}
		List<? extends IJstType> list = callingType.getSatisfies();
		if (list == null || list.isEmpty()) {
			return;
		}
		Iterator<? extends IJstType> it = list.iterator();
		while (it.hasNext()) {
			IJstType temp = it.next();
			if (!interfaceList.contains(temp)) {
				interfaceList.add(temp);
			}
		}
	}

	private void addMethods(IJstType parentType, int[] levels,
			List<String> tempList, String token, VjoCcCtx ctx, List<IJstType> interfaceList) {
		IJstType tempCalledType = parentType;
		appendInterface(interfaceList, tempCalledType);
		boolean isNative = CodeCompletionUtils.isNativeType(tempCalledType);
//		List<? extends IJstMethod> methods = parentType.getMethods(false, true);
		List<? extends IJstMethod> methods = JstTypeHelper.getSignatureMethods(parentType, false, true);
		Iterator<? extends IJstMethod> it = methods.iterator();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			if (tempCalledType != method.getOwnerType()) {
				tempCalledType = method.getOwnerType();
				isNative = CodeCompletionUtils.isNativeType(tempCalledType);
				appendInterface(interfaceList, tempCalledType);
			}
			if (method.getName().getName().startsWith(token)
					&& !method.isFinal() && !method.isStatic()) {
				addMethod(method, tempList, levels, token, ctx, isNative);
			}
		}
	}

	private void addMethod(IJstMethod method, List<String> tempList,
			int[] levels, String token, VjoCcCtx ctx, boolean isNative) {
		if (!method.isConstructor()
				&& levelCheck(method.getModifiers(), levels)
				&& (!isNative || !method.getName().getName().startsWith("_"))) {
			String tempStr = CodeCompletionUtils.getMthodsStr(method);
			if (!tempList.contains(tempStr)) {
				tempList.add(tempStr);
				appendData(ctx, method);
				if (token.equals(method.getName().getName())) {
					ctx.exactMatch(ID);
				}
			}

		}
	}

	private List<String> getMethodsString(IJstType type) {
		List<? extends IJstMethod> methods = type.getMethods(false, false);
		Iterator<? extends IJstMethod> it = methods.iterator();
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			IJstMethod method = it.next();
			String methodStr = CodeCompletionUtils.getMthodsStr(method);
			if (!list.contains(methodStr)) {
				list.add(methodStr);
			}
			List<IJstMethod> overloads = method.getOverloaded();
			if (overloads != null) {
				Iterator<IJstMethod> it1 = overloads.iterator();
				while (it1.hasNext()) {
					methodStr = CodeCompletionUtils.getMthodsStr(it1.next());
					if (!list.contains(methodStr)) {
						list.add(methodStr);
					}
				}
			}
		}
		return list;
	}

	@Override
	protected int[] getCallLevel(IJstType callingType, IJstType calledType) {
		return new int[] { JstModifiers.PUBLIC, JstModifiers.PROTECTED };

	}

}
