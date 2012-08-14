/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.JsCommentMetaNode;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnMemberAccess;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;

public class FunctionExpressionTranslator extends
		BaseAst2JstTranslator<FunctionExpression, FuncExpr> {

	private static final char DOT = '.';
	private static final char LBRACKET = '.';

	private static final String EMPTY = "";

	public static final String DUMMY_METHOD_NAME = "anonymous_function";

	@Override
	protected FuncExpr doTranslate(FunctionExpression astFunctionExpression) {
	 return doTranslate(astFunctionExpression, null);
	}
	
	
	protected FuncExpr doTranslate(FunctionExpression astFunctionExpression, String name) {
		
		List<IJsCommentMeta> metaArr = getCommentMeta(astFunctionExpression);
		if (metaArr != null) {

			final MethodDeclaration astMethodDeclaration = (astFunctionExpression instanceof FunctionExpression) ? ((FunctionExpression) astFunctionExpression)
					.getMethodDeclaration()
					: null;
					
			final JstMethod jstMethod = MethodDeclarationTranslator.createJstMethodFromMethodDeclAst(m_ctx, astMethodDeclaration, metaArr, name);
			return createFuncExpr(jstMethod, metaArr);
		}
		return null;
	}

	/**
	 * by huzhou@ebay.com to set the FuncExpr result type correctly to JstFuncType
	 * @param jstMethod
	 * @return
	 */
	private FuncExpr createFuncExpr(final JstMethod jstMethod, 
			final List<IJsCommentMeta> metaArr) {
		final FuncExpr expr = new FuncExpr(jstMethod);
		expr.setType(TranslateHelper.createJstFuncType(m_ctx, jstMethod));

		if(DUMMY_METHOD_NAME.equals(jstMethod.getName().getName())
				&& metaArr.size() > 0){
			final IJsCommentMeta originalMeta = metaArr.get(0);
			//check if meta is attributed or otype function ref
			final IJstType metaTyping = TranslateHelper.findType(m_ctx, originalMeta.getTyping(), originalMeta);
			if(metaTyping != null
					&& (metaTyping instanceof JstAttributedType||metaTyping instanceof JstFunctionRefType)){
				expr.setType(metaTyping);
			}
		}
		final JsCommentMetaNode metaJstNode = new JsCommentMetaNode();
		metaJstNode.setJsCommentMetas(metaArr);
		expr.addChild(metaJstNode);
		return expr;
	}


	public static boolean isNotCompleteExpression(TranslateCtx m_ctx,
			int statEnd) {
		return m_ctx.getOriginalSource()[statEnd] != ';';
	}

	private List<IJsCommentMeta> getCommentMeta(
			FunctionExpression astFunctionExpression) {
		MethodDeclaration astMethodDeclaration = astFunctionExpression
				.getMethodDeclaration();
		int next = m_ctx.getNextNodeSourceStart();
		if (astMethodDeclaration != null){
			if (astMethodDeclaration.statements != null
					&& astMethodDeclaration.statements.length > 0) {
				next = astMethodDeclaration.statements[0].sourceStart();
			}
			//bugfix by huzhou@ebay.com, in some case when function defines no statements
			//next could be zero
			else if(next <= 0) {
				next = astFunctionExpression.getMethodDeclaration().bodyEnd;
			}
		}
		
		return m_ctx.getCommentCollector().getCommentMeta(
//				 sourceStart is incorrect when there is js doc in front of it
				astFunctionExpression.getMethodDeclaration().bodyStart, 
				m_ctx.getPreviousNodeSourceEnd(), next);
	}

	@Override
	protected JstCompletion createCompletion(FunctionExpression astNode,
			boolean isAfterSource) {
		MethodDeclaration astMethodDeclaration = astNode.getMethodDeclaration();
		boolean inBody = astMethodDeclaration.bodyStart <= m_ctx
				.getCompletionPos();
		String token = EMPTY;
		if (!inBody) {
			String preStr = new String(m_ctx.getOriginalSource(), astNode.sourceStart,m_ctx.getCompletionPos() - astNode.sourceStart);
			if (preStr.indexOf("(") < 0) {
				token = preStr;
			} else {
				return null;
			}
		}
		char c = m_ctx.getOriginalSource()[m_ctx.getCompletionPos() - 1];
		JstCompletion completion = null;
		JstType type = m_ctx.getCurrentType();
		if (c == DOT) {
			completion = new JstCompletionOnMemberAccess(type);
		}
//		else if (c == LBRACKET) {
//			completion = new JstCompletionOnMemberAccess(type);
//		} 
		else {
			completion = new JstCompletionOnSingleNameReference(type);
		}
		completion.setToken(token);
		m_ctx.setCreatedCompletion(true);
		completion.setScopeStack(m_ctx.getScopeStack());
		if (inBody) {
			completion.pushScope(ScopeIds.METHOD);
		}
		return completion;
	}
}
