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
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnMemberAccess;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;

public class MethodDeclarationTranslator extends
		BaseAst2JstTranslator<MethodDeclaration, ExprStmt> {
	private static final char DOT = '.';

	private static final String EMPTY = "";

	@Override
	protected ExprStmt doTranslate(MethodDeclaration astNode) {
		final String methName = String.valueOf(astNode.selector);
		final List<IJsCommentMeta> metaArr = getCommentMeta(astNode);
		JstMethod jstMethod = createJstMethodFromMethodDeclAst(m_ctx, astNode, metaArr, methName);
		if (m_ctx.getCurrentScope() == ScopeIds.PROPS) {
			jstMethod.getModifiers().merge(JstModifiers.STATIC);
		}
		
		// return type setting
		if (metaArr != null) {
			for(IJsCommentMeta meta : metaArr){
				TranslateHelper.setReferenceSource(jstMethod, meta);
			}
		}
		
		final FuncExpr funcExpr = new FuncExpr(jstMethod);
		if(metaArr != null 
				&& metaArr.size() > 0
				&& !metaArr.get(0).isMethod()){
			final IJsCommentMeta mtdMeta = metaArr.get(0);
			final IJstType metaTyping = TranslateHelper.findType(m_ctx, mtdMeta.getTyping(), mtdMeta);
			if(metaTyping != null
					&& (metaTyping instanceof JstAttributedType||metaTyping instanceof JstFunctionRefType)){
				funcExpr.setType(metaTyping);
			}
		}
		return new ExprStmt(funcExpr);
		
	}
	

	public static JstMethod createJstMethodFromMethodDeclAst(
			final TranslateCtx ctx,
			final MethodDeclaration astMethodDeclaration,
			final List<IJsCommentMeta> metaArr, String name) {

		final JstMethod jstMethod = TranslateHelper.MethodTranslateHelper.createJstMethod(astMethodDeclaration, metaArr, ctx, name);
		//added by huzhou@ebay.com to create block (empty) for FuncExpr
		//in order to differ from vjo.NEEDS_IMPL
		JstBlock jstBlock = jstMethod.getBlock(true);
		
		// body
		final JstSource methodSource = TranslateHelper.getSource(astMethodDeclaration, ctx.getSourceUtil());
		if (!ctx.isSkiptImplementation()
				&& astMethodDeclaration.statements != null) {
			ctx.enterBlock(ScopeIds.METHOD);
			try {
				ctx.setPreviousNodeSourceEnd(astMethodDeclaration
						.sourceStart());
				jstBlock.setSource(methodSource);
				final JstType refType = JstCache.getInstance().getType("Arguments");
				// DO NOT ADD SOURCE INFOMATION TO THIS SINCE THIS IS IMPLIED
				JstVars jstVar = new JstVars(refType, new JstInitializer(
						new JstIdentifier("arguments"), null));
				jstBlock.addChild(jstVar);
				// jstBlock.getVarTable().addVarType(ARGUMENTS, refType);
				TranslateHelper.addStatementsToJstBlock(
						astMethodDeclaration.statements, jstBlock,
						astMethodDeclaration.sourceEnd(), ctx);
			} finally {
				ctx.exitBlock();

			}
		}
		TranslateHelper.MethodTranslateHelper.addMethodCommentsAndSource(ctx, astMethodDeclaration, jstMethod);
		return jstMethod;
	}
	
	public static boolean isNotCompleteExpression(TranslateCtx m_ctx,
			int statEnd) {
		return m_ctx.getOriginalSource()[statEnd] != ';';
	}

	private List<IJsCommentMeta> getCommentMeta(
			MethodDeclaration astMethodDeclaration) {
		int next = m_ctx.getNextNodeSourceStart();
		if (astMethodDeclaration != null){
			if (astMethodDeclaration.statements != null
					&& astMethodDeclaration.statements.length > 0) {
				next = astMethodDeclaration.statements[0].sourceStart();
			}
			//bugfix by huzhou@ebay.com, in some case when function defines no statements
			//next could be zero
			else if(next <= 0) {
				next = astMethodDeclaration.bodyEnd;
			}
		}
		
		return m_ctx.getCommentCollector().getCommentMeta(
//				 sourceStart is incorrect when there is js doc in front of it
				astMethodDeclaration.bodyStart, 
				m_ctx.getPreviousNodeSourceEnd(), next);
	}

	@Override
	protected JstCompletion createCompletion(MethodDeclaration astNode,
			boolean isAfterSource) {
		char c = m_ctx.getOriginalSource()[m_ctx.getCompletionPos() - 1];
		JstCompletion completion = null;
		JstType type = m_ctx.getCurrentType();
		if (c == DOT) {
			completion = new JstCompletionOnMemberAccess(type);
		} else {
			completion = new JstCompletionOnSingleNameReference(type);
		}
		completion.setToken(EMPTY);
		m_ctx.setCreatedCompletion(true);
		completion.setScopeStack(m_ctx.getScopeStack());
		completion.pushScope(ScopeIds.METHOD);
		return completion;
	}
}
