/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Argument;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;

public abstract class BaseAst2JstTranslator<T extends IASTNode, E> {
	private static final String LT = "<";

	private static final String GT = ">";

	private static final String DOUBLE_SLASH = "//";

	protected TranslateCtx m_ctx;

	protected BaseJstNode m_parent;

	protected List<IASTNode> m_recoveredNodes = new ArrayList<IASTNode>();
	
	protected static final String CLOSE_BRACKET = ")";

	protected static final String OPEN_BRACKET = "(";

	public final E translate(T astNode) {
		if (m_ctx == null) {
			throw new NullPointerException("Translate context is null!");
		}
		E jstResult = doTranslate(astNode);
		checkForCompletion(astNode);
		checkForCommentCompletion(astNode);
		return jstResult;
	}

	protected abstract E doTranslate(T astNode);
	
	protected void checkForCompletion(T astNode) {
		if (m_ctx.isCreatedCompletion()) {
			return;
		}
		int startPos = astNode.sourceStart();
//		int endPos = astNode.sourceEnd();

		int completionPos = m_ctx.getCompletionPos();
		boolean insideSource = completionPos >= startPos
				&& completionPos <= getSourceEnd(astNode);

		boolean isAfterSource = completionPos == getSourceEnd(astNode) + 1;

//		if ( isAfterSource || closeToSource(astNode, completionPos)) {
		if (insideSource || isAfterSource || closeToSource(astNode, completionPos)) {
			JstCompletion completion = createCompletion(astNode, isAfterSource);
			if (completion != null) {
				JstCommentUtil.fillCompletion((ASTNode) astNode, m_ctx, completion);
				m_ctx.addSyntaxError(completion);
			}
		}
	}
	
	protected void checkForCommentCompletion(T astNode) {
		if (m_ctx.isCreatedCompletion()) {
			return;
		}
		int completionPos = m_ctx.getCompletionPos();
		if (completionPos == -1) {
			return;
		}
		if (astNode instanceof Argument || astNode instanceof SingleNameReference 
			|| astNode instanceof AllocationExpression || astNode instanceof Literal) {
			return; 
		}
		List<IJsCommentMeta> metas = 
			JstCommentUtil.getCommentMeta((ASTNode) astNode, m_ctx);
		for (IJsCommentMeta meta : metas) {
			if (JstCommentUtil.isWithInComment(meta.getBeginOffset(), meta.getEndOffset(), completionPos)) {
				JstCommentUtil.createComentCompletion((ASTNode) astNode, meta, m_ctx);
			}
		}
		
		List<String> comments = 
			JstCommentUtil.getNonMetaComments((ASTNode) astNode, m_ctx);
		for (String comment : comments) {
			if (isCommentMeta(comment)) {
				int beginOffset = m_ctx.getCommentCollector().getCommentNonMetaBeginOffset(comment);
				if (beginOffset == -1) {
					continue;
				}
				if (JstCommentUtil.isWithInComment(beginOffset, beginOffset+comment.length(), completionPos)) {
					JstCommentUtil.createComentCompletion(
						(ASTNode) astNode, comment, beginOffset, beginOffset+comment.length(), m_ctx);
				}
			}
		}
	}

	private boolean isCommentMeta(String comment) {
		return comment.contains(DOUBLE_SLASH) && (comment.contains(GT) || comment.contains(LT));
	}
	
	private boolean closeToSource(T astNode, int completionPos) {
		// check for offset of 10 for closest node
		if (completionPos == -1) {
			return false;
		}
		if(!astNode.getClass().equals(SingleNameReference.class)){
			return false;
		}
		
		if(astNode.sourceEnd()!=astNode.sourceStart()){
			return false;
		}
		
		int delta = astNode.sourceStart() - completionPos;
		if(delta > 0){
			String s = new String(m_ctx.getOriginalSource(), completionPos, delta);
			return s.trim().length() == 0;
		}
		
		return false;
	}

	protected int getSourceEnd(T astNode) {		
		return astNode.sourceEnd();
	}

	protected JstCompletion createCompletion(T astNode, boolean isAfterSource) {
		// default implementation
		return null;
	}
	

	public static JstSource createSource(int start, int end, JstSourceUtil util) {
		return TranslateHelper.createJstSource(util, end - start, start, end);
	}

	protected void setTranslateCtx(final TranslateCtx ctx) {
		m_ctx = ctx;
	}

	protected BaseAst2JstTranslator getTranslator(IASTNode astNode) {
		return TranslatorFactory.getTranslator(astNode, m_ctx);
	}

	public void setParent(BaseJstNode parent) {
		this.m_parent = parent;
	}

	protected Object getTranslatorAndTranslate(IASTNode astNode) {
		BaseAst2JstTranslator translator;
//		if (astNode instanceof ObjectLiteral && isVjoContext()) {
//			translator = new VjoOLTranslator(m_ctx);
//		} else {
			translator = getTranslator(astNode);
//		}

		Object object = null;

		if (translator != null) {
			translator.setParent(this.m_parent);
			object = translator.translate(astNode);
		} else {
			System.err.println("Not found translator for node :" + astNode);
		}

		return object;
	}

	private boolean isVjoContext() {
		ScopeId scopeId = m_ctx.getCurrentScope();
		return (scopeId == ScopeIds.PROPS
				|| scopeId == ScopeIds.PROTOS
				|| scopeId == ScopeIds.VALUES
				|| scopeId == ScopeIds.DEFS
				|| m_ctx.getCurrentType() instanceof JstObjectLiteralType);
	}

	protected Object getTranslatorAndTranslate(IASTNode astNode,
			BaseJstNode parent) {
		BaseAst2JstTranslator translator = getTranslator(astNode);
		translator.setParent(parent);

		return translator.translate(astNode);
	}

	protected BaseAst2JstTranslator getProblemTranslator(IASTNode astNode,
			int problemStart) {
		return TranslatorFactory.getProblemTranslator(astNode, m_ctx,
				problemStart);
	}

	protected Object getProblemTranslatorAndTranslate(IASTNode astNode,
			int problemStart) {
		BaseAst2JstTranslator translator = getProblemTranslator(astNode,
				problemStart);
		return translator.translate(astNode);
	}

	protected List<IASTNode> getRecoveredNodes() {
		List<IASTNode> copy = new ArrayList(m_recoveredNodes);
		m_recoveredNodes.clear();
		return copy;
	}

	protected void setCompletionParams(JstCompletion completion, T astNode) {
		int sourceStart = astNode.sourceStart();
		int sourceEnd = getSourceEnd(astNode);

		completion.setSource(createSource(sourceStart, sourceEnd + 1, m_ctx.getSourceUtil()));

		String prefix = new String(m_ctx.getOriginalSource(), sourceStart,
				m_ctx.getCompletionPos() - sourceStart);
		String wholeWord = new String(m_ctx.getOriginalSource(), sourceStart,
				sourceEnd + 1 - sourceStart);

		completion.setCompositeToken(wholeWord);
		completion.setToken(prefix);
	}
	
	protected boolean isJavaIdentifier(String token) {
		char[] chs = token.toCharArray();
		for (char ch : chs) {
			if (Character.isJavaIdentifierPart(ch)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	protected boolean isJavaPackageIdentifier(String token) {
		if (isJavaIdentifier(token)) {
			return true;
		}
		String[] ss = token.split("\\.");
		for (String s : ss) {
			if (isJavaIdentifier(s)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}
