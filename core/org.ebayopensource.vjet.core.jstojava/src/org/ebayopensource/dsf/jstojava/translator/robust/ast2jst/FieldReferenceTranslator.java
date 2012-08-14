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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.JsCommentMetaNode;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnQualifiedNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;

public class FieldReferenceTranslator extends
		BaseAst2JstTranslator<FieldReference, FieldAccessExpr> {

	@Override
	protected FieldAccessExpr doTranslate(FieldReference expr) {
		String identifierStr = String.valueOf(expr.token);
		if(TranslateHelper.MISSING_TOKEN.equals(identifierStr)) {
			identifierStr = "";
		}
		JstIdentifier identifier = new JstIdentifier(identifierStr);
		int nameLength = identifier.getName().length();
		JstSource sourceIden = TranslateHelper.getSource(expr.sourceEnd,
				nameLength);
		identifier.setSource(sourceIden);

		FieldAccessExpr far = new FieldAccessExpr(identifier);

		IExpr qualifier = (IExpr) getTranslatorAndTranslate(expr.receiver, far);
		far.setExpr(qualifier);
		if (qualifier instanceof JstIdentifier) {
			IJstType type = ((JstIdentifier)qualifier).getType();
			if (type!=null && type.getProperty(identifierStr)!=null) {
				
				IJstType fnType = JstCache.getInstance().getType("Function");
				
				
				IJstType propType = type.getProperty(identifierStr).getType();
				if (fnType != null && fnType.equals(propType)) {
					List<IJsCommentMeta> metaArr= m_ctx.getCommentCollector().getCommentMeta(
//							 sourceStart is incorrect when there is js doc in front of it
							expr.sourceStart, 
							m_ctx.getPreviousNodeSourceEnd(), m_ctx.getNextNodeSourceStart(),true);
					if(metaArr!=null && metaArr.size()>0){
						final JsCommentMetaNode metaJstNode = new JsCommentMetaNode();
						metaJstNode.setJsCommentMetas(metaArr);
						far.addChild(metaJstNode);
					}
				}
				
				identifier.setType(propType);
			}
		}
		JstSource source = TranslateHelper.getSource(expr, m_ctx.getSourceUtil());
		far.setSource(source);
		this.setParent(far);

		return far;
	}
	


	@Override
	protected JstCompletion createCompletion(FieldReference astNode,
			boolean isAfterSource) {
		// As it was
		 JstCompletion completion = new JstCompletionOnQualifiedNameReference(
		 m_parent);
		 setCompletionParams(completion, astNode);
		 m_ctx.setCompletionPos(-1);
		 
		 //VS
		 // JstCompletionOnMemberAccess
		 
//		JstCompletion completion = new JstCompletionOnMemberAccess(m_ctx.getCurrentType());
//		completion.setToken("");
//		m_ctx.setCreatedCompletion(true);
//		completion.setBlockStack(m_ctx.getBlockStack());
//		completion.pushBlock(BlockIdentifier.METHOD);

		return completion;
	}

	@Override
	protected void setCompletionParams(JstCompletion completion,
			FieldReference astNode) {
		int sourceStart = astNode.sourceStart();
		String token = astNode.toString();
		if(token.endsWith(TranslateHelper.MISSING_TOKEN)) {
			token = token.substring(0, token.length() - TranslateHelper.MISSING_TOKEN.length());
		}
		String prefix = TranslateHelper.calculatePrefix(m_ctx.getOriginalSource(),m_ctx.getCompletionPos(),  sourceStart);
		completion.setSource(createSource(m_ctx.getCompletionPos()
				- prefix.length(), m_ctx.getCompletionPos(), m_ctx.getSourceUtil()));

		completion.setCompositeToken(token);
		completion.setToken(prefix);
	}

}
