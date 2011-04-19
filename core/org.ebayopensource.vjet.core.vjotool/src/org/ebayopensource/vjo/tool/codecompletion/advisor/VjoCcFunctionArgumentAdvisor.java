/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyMethod;
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
public class VjoCcFunctionArgumentAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcFunctionArgumentAdvisor.class.getName();

	public void advise(final VjoCcCtx ctx) {
		final Object valueOfInfoKeyArgument = ctx.getInfo(VjoCcCtx.INFO_KEY_ARGUMENT);
		if (! (valueOfInfoKeyArgument instanceof IExpr)){
			return;
		}
		
		final IExpr argument = (IExpr)valueOfInfoKeyArgument;
		if(!(argument.getParentNode() instanceof MtdInvocationExpr)){
			return;
		}
		final MtdInvocationExpr mtdInvocationExpr = (MtdInvocationExpr) argument.getParentNode();
		final int position = mtdInvocationExpr.getArgs().indexOf(argument);
		final IJstNode node = mtdInvocationExpr.getMethod();
		if (node == null || !(node instanceof IJstMethod)) {
			return;
		}
		
		final IJstMethod method = (IJstMethod) node;
		IJstType calledType = method.getOwnerType();
		if (calledType == null) {
			return;
		}
		
		final List<JstArg> parameters = method.getArgs();
		if(parameters.size() <= position){
			return;
		}
		
		final JstArg parameterAtPos = parameters.get(position);
		final IJstType parameterType = parameterAtPos.getType();
		if(parameterType instanceof JstFuncType){
			appendData(ctx, getParamNamedMethodProposal(parameterAtPos, ((JstFuncType)parameterType).getFunction()), true);
		}
		else if(parameterType instanceof JstFunctionRefType){
			appendData(ctx, getParamNamedMethodProposal(parameterAtPos, ((JstFunctionRefType)parameterType).getMethodRef()), true);
		}
	}

	private IJstMethod getParamNamedMethodProposal(
			final JstArg parameterAtPos, final IJstMethod method) {
		if(parameterAtPos == null || parameterAtPos.getName() == null){
			return method;
		}
		
		return new RenameableSynthJstProxyMethod(method, parameterAtPos.getName());
	}
}
