/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteralField;

public class JstCommentUtil {
	
	public static List<String> getNonMetaComments(ASTNode astNode, TranslateCtx ctx) {
		return ctx.getCommentCollector().getComments(ctx.getPreviousNodeSourceEnd(), 
				astNode.sourceStart());
	}

	private static void fillScope(ASTNode astNode, TranslateCtx ctx, JstCommentCompletion comp) {
		if (astNode instanceof MessageSend && 
				(astNode.toString().startsWith("vjo.ctype") || astNode.toString().startsWith("vjo.itype"))) {
			comp.pushScope(ScopeIds.TYPE);
		} else if (astNode instanceof MethodDeclaration) {
			comp.pushScope(ScopeIds.METHOD);
		} else if (astNode instanceof FunctionExpression) {
			MethodDeclaration astMethodDeclaration = 
				((FunctionExpression) astNode).getMethodDeclaration();
			boolean inBody = astMethodDeclaration.bodyStart <= ctx
					.getCompletionPos();
			if (inBody) {
				comp.pushScope(ScopeIds.METHOD);
			}
		} else if (astNode instanceof ObjectLiteralField) {
			if (((ObjectLiteralField)astNode).initializer instanceof FunctionExpression) {
				comp.pushScope(ScopeIds.METHOD);
			} else {
				comp.pushScope(ScopeIds.PROPERTY);
			}
//		} else if (astNode instanceof LocalDeclaration && astNode.toString().startsWith("var")) {
		} else {
			comp.pushScope(ScopeIds.VAR);
		} 
	}
	
	public static boolean isWithInComment(int start, int end, int completionPos) {
//		System.out.println("completionPos="+completionPos);
//		System.out.println("comment start="+start);
//		System.out.println("comment end="+end);
		if (completionPos <= end && completionPos >= start) {
			return true;
		}
		return false;
	}

	public static List<IJsCommentMeta> getCommentMeta(ASTNode astNode, TranslateCtx ctx) { 
		return ctx.getCommentCollector().getCommentMeta(
			astNode.sourceStart(),
			astNode.sourceEnd(),
			ctx.getPreviousNodeSourceEnd(),
			ctx.getNextNodeSourceStart(),
			true);
	}

	public static void fillCompletion(ASTNode astNode, TranslateCtx ctx,
			JstCompletion completion) {
		JstType jstType = ctx.getCurrentType();
		if (completion.getSource() == null) {
			JstSource jstSource;
			jstSource = createSource(astNode.sourceStart(), 
					astNode.sourceEnd(), ctx.getSourceUtil());
			completion.setSource(jstSource);
		}
		if (completion.isEmptyStack()) {
			completion.setScopeStack(ctx.getScopeStack());
		}
		completion.setParent(jstType);
	}
	
	public static void createComentCompletion(ASTNode astNode, IJsCommentMeta meta, TranslateCtx ctx) {
		JstType type = ctx.getCurrentType();
		int relativeCompletionPos;
		if (ctx.getCompletionPos() >= meta.getBeginOffset() && ctx.getCompletionPos() <= meta.getEndOffset()) {
			relativeCompletionPos = ctx.getCompletionPos() - meta.getBeginOffset();
		} else {
			relativeCompletionPos = meta.getEndOffset();
		}
		JstCommentCompletion commentCompletion = 
			new JstCommentCompletion(type, meta.getCommentSrc(), 
					relativeCompletionPos);
		commentCompletion.setScopeStack(ctx.getScopeStack());
		fillCompletion(astNode, ctx, commentCompletion);
		ctx.addSyntaxError(commentCompletion);
		ctx.setCreatedCompletion(true);
		fillScope(astNode, ctx, commentCompletion);
//		System.out.println("+++ commentString="+meta.toString()+", commentOffset="+relativeCompletionPos);
	}
	
	public static void createComentCompletion(ASTNode astNode, 
			String comment, int beginOffset, int endOffset, TranslateCtx ctx) {
		JstType type = ctx.getCurrentType();
		int relativeCompletionPos;
		if (ctx.getCompletionPos() >= beginOffset && ctx.getCompletionPos() <= endOffset) {
			relativeCompletionPos = ctx.getCompletionPos() - beginOffset;
		} else {
			relativeCompletionPos = endOffset;
		}
		JstCommentCompletion commentCompletion = 
			new JstCommentCompletion(type, comment, 
					relativeCompletionPos);
		commentCompletion.setScopeStack(ctx.getScopeStack());
		fillCompletion(astNode, ctx, commentCompletion);
		ctx.addSyntaxError(commentCompletion);
		ctx.setCreatedCompletion(true);
		fillScope(astNode, ctx, commentCompletion);
//		System.out.println("+++ commentString="+meta.toString()+", commentOffset="+relativeCompletionPos);
	}
	
	private static JstSource createSource(int start, int end, JstSourceUtil util) {
		return TranslateHelper.createJstSource(util, end - start, start, end);
	}

}
