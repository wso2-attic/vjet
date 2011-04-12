/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
//import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
//import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator;
//import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnMemberAccess;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstFieldOrMethodCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;

public class InitsTranslator extends BasePropsProtosTranslator {

	public InitsTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.INITS;
	}

	@Override
	public void process(Expression expr, JstType jstType) {
		if (expr instanceof FunctionExpression) {
			m_ctx.enterBlock(ScopeIds.INITS);
			
			JstBlock block = new JstBlock();
			
			FunctionExpression funcExp = ((FunctionExpression)expr);
			MethodDeclaration astMethodDeclaration = funcExp.getMethodDeclaration();
			final JstType refType = JstCache.getInstance().getType("Arguments");
			JstVars jstVar = new JstVars(refType,new JstInitializer(new JstIdentifier("arguments"),null));
			block.addChild(jstVar);

			TranslateHelper.addStatementsToJstBlock(astMethodDeclaration.statements,
					block, astMethodDeclaration.sourceEnd(), m_ctx);
			
			
			final int declStart = astMethodDeclaration.declarationSourceStart;
			final int declEnd = astMethodDeclaration.declarationSourceEnd;
			final int bodyStart = astMethodDeclaration.bodyStart;
			final int bodyEnd = astMethodDeclaration.bodyEnd;
			final int completionPos = m_ctx.getCompletionPos();
			
			if(block.getStmts().size() > 0){
				for (IStmt stmt : block.getStmts()) {
					m_ctx.getCurrentType().addInit(stmt, true);
				}
				final JstSource blockSource = TranslateHelper.createJstSource(m_ctx.getSourceUtil(), declEnd - declStart, declStart, declEnd);
				m_ctx.getCurrentType().getInitBlock().setSource(blockSource);
			}
			
			if(!m_ctx.isCreatedCompletion() && completionPos <= declEnd + 1 && completionPos >= declStart){
				JstCompletion completion = null;
				boolean inMethod = completionPos>=bodyStart && completionPos <= bodyEnd + 1; 
				if (inMethod) {
					completion = createJstCompletion();
				} else {
					completion = new JstFieldOrMethodCompletion(m_ctx.getCurrentType(), true);
				}
				completion.setToken("");
				
				final JstSource completionSource = TranslateHelper.createJstSource(m_ctx.getSourceUtil(), 0, completionPos, completionPos);
				completion.setSource(completionSource);
				completion.setScopeStack(m_ctx.getScopeStack());
				if (inMethod) {
					completion.getScopeStack().push(ScopeIds.METHOD);
				}
				
				m_ctx.setCreatedCompletion(true);
				m_ctx.addSyntaxError(completion);
			}
			m_ctx.exitBlock();
			
		} else {
			System.err.println("Unprocessed type: " + expr.getClass() + " in "
					+ getClass().getName());
		}

	}
	
	private JstCompletion createJstCompletion() {
		char c = m_ctx.getOriginalSource()[m_ctx.getCompletionPos() - 1];
		JstType type = m_ctx.getCurrentType();
		JstCompletion completion = null;
		if (c == '.') {
			completion = new JstCompletionOnMemberAccess(type);
		} else {
			completion = new JstCompletionOnSingleNameReference(type);
		}
		return completion;
	}

}
