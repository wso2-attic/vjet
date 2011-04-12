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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advise parameters as tooltip when cursor is in MtdInvocationExpression
 * example1:<br>
 * function() { xxxx(<cursor>) }<br>
 * example2:<br>
 * function() { xx(a, <cursor>b }<br>
 * 
 * Needs attribute:<br>
 * ctx.actingType,<br>
 * 
 * ProposalData.data IjstMethod. <b4>
 * 
 * 
 * 
 */
public class VjoCcParameterHintAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcParameterHintAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		IJstType callingType = ctx.getActingType();
		Object obj = ctx.getInfo(VjoCcCtx.INFO_KEY_PARAMETER_HINT);
		if (obj == null || !(obj instanceof MtdInvocationExpr)) {
			return;
		}
		MtdInvocationExpr mtd = (MtdInvocationExpr) obj;
		int paraPos = ctx.getRelativePosInMtdCall(mtd);
		IJstNode node = mtd.getMethod();
		if (node == null || !(node instanceof IJstMethod)) {
			return;
		}
		
		
		IJstMethod method = (IJstMethod) node;
		
		if(!method.isDispatcher() && method.getParentNode() instanceof IJstMethod){
			method = (IJstMethod)method.getParentNode();
		}
		IJstType calledType = method.getOwnerType();
		if (calledType == null) {
			return;
		}
		boolean isNative = CodeCompletionUtils.isNativeType(calledType);
		int[] levels = getCallLevel(callingType, calledType);
		List<String> tempString = new ArrayList<String>();
		List<? extends IJstMethod> overloads = JstTypeHelper.getSignatureMethods(method);
		if (overloads != null) {
			Iterator<? extends IJstMethod> it1 = overloads.iterator();
			while (it1.hasNext()) {
				method = it1.next();
				if (method.getArgs().size() >= paraPos) {
					addMethod(method, levels, isNative, ctx, tempString, true);
				}
			}
		}
	}

	public static boolean isAvailable(MtdInvocationExpr mtd, VjoCcCtx ctx) {
		IJstType callingType = ctx.getActingType();
		int paraPos = ctx.getRelativePosInMtdCall(mtd);
		IJstNode node = mtd.getMethod();
		if (node == null || !(node instanceof IJstMethod)) {
			return false;
		}
		IJstMethod method = (IJstMethod) node;
		IJstType calledType = method.getOwnerType();
		if (calledType == null) {
			return false;
		}
		int[] levels = CodeCompletionUtils.getGeneralFieldCallLevel(
				callingType, calledType);
		List<? extends IJstMethod> overloads = JstTypeHelper.getSignatureMethods(method);
		if (overloads != null) {
			Iterator<? extends IJstMethod> it1 = overloads.iterator();
			while (it1.hasNext()) {
				method = it1.next();
				if (method.getArgs().size() >= paraPos
						&& CodeCompletionUtils.levelCheck(method.getModifiers(), levels)) {
					return true;
				}
			}
		}
			return false;
	}

	private void addMethod(IJstMethod method, int[] levels, boolean isNative,
			VjoCcCtx ctx, List<String> tempString, boolean exactMatch) {
		if (levelCheck(method.getModifiers(), levels)
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
