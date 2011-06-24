/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnMemberAccess;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

public class MemberAccessHandler implements ICompletionHandler {

	public static final int SPACE_AND_DOT_LENGTH = 2;
	public static final int DOT_LENGTH = 1;
	private static StaticMemberAccessHandler staticHandler = new StaticMemberAccessHandler();
	private static TypeMemberAccessHandler typeHandler = new TypeMemberAccessHandler();
	private static FieldExpressionHandler exprHandler = new FieldExpressionHandler();

	// private static FieldExpressionHandler nonForcedExprHandler =
	// FieldExpressionHandler
	// .getNonForcedHandler();

	private static MethodCompletionHandler methodHandler = new MethodCompletionHandler();

	private ISourceModule module;
	private int position;
	private JstCompletion completion;
	private List<CompletionProposal> list;

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		init(module, position, completion, list);
		int pos = getNodeEndOffset(module, position - SPACE_AND_DOT_LENGTH);
		Object node = JstUtil.getNode(completion.getOwnerType(), pos, pos);		
		complete(node);
	}

	public void complete(Object node) {

		CompletionContext.setInstanceContext(true);
		IJstType type = completion.getOwnerType();

		if (node instanceof ObjCreationExpr) {
			ObjCreationExpr expr = (ObjCreationExpr) node;	
			CompletionContext.setVariableContext(true);
			completeMethodExpr(type, expr.getInvocationExpr());
		}
		else if (node instanceof MtdInvocationExpr) {
			completeMethodExpr(type, node);
		} else if (node instanceof FieldAccessExpr) {
			completeFieldExpr(node, type);
		} else if (node != null && !node.toString().equals(VjoKeywords.VJO)) {

			if (thisInStaticCntx((IJstNode) node)) {

				staticHandler.setJstType(type);
				staticHandler.setTypeName(type.getSimpleName());

				staticHandler.complete(module, position, completion, list);

			} else {
				exprHandler.complete(module, position, completion, list, node);
			}
		} else if (node == null || !node.toString().equals(VjoKeywords.VJO)) {
			methodHandler.complete(module, position, completion, list);
		}

	}

	private boolean thisInStaticCntx(IJstNode identifier) {

		String name = getName(identifier);

		if (name.equals(VjoKeywordFactory.KWD_THIS.getName())) {

			JstMethod enclosedMethod = getEnclosedMethod(identifier);

			if (enclosedMethod != null) {
				return enclosedMethod.isStatic();
			}

		}

		return false;
	}

	private String getName(IJstNode node) {
		String name = node.toString();
		if (node instanceof JstIdentifier) {
			JstIdentifier jstIdentifier = (JstIdentifier) node;
			name = jstIdentifier.getName();
		}
		return name;
	}

	private JstMethod getEnclosedMethod(IJstNode node) {

		while ((node != null) && !(node instanceof JstMethod)) {
			node = node.getParentNode();
		}

		return (JstMethod) node;
	}

	private void completeFieldExpr(Object node, IJstType type) {
		FieldAccessExpr fieldAccessExpr = (FieldAccessExpr) node;

		complete(type, fieldAccessExpr);
	}

	public void init(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		this.position = position;
		this.module = module;
		this.completion = completion;
		this.list = list;
	}

	private void completeMethodExpr(IJstType type, Object node) {
		exprHandler.complete(module, position, completion, list, node);
	}

	private void complete(IJstType type, FieldAccessExpr invocationExpr) {
		CompletionContext.setInstanceContext(true);
		exprHandler.complete(module, position, completion, list,
			invocationExpr);
	}

	private int getNodeEndOffset(ISourceModule module, int pos) {
		int index = pos;
		String string = module.getSourceContents();
		for (int i = pos; i >= 0; i--) {
			char c = string.charAt(i);
			if (!Character.isWhitespace(c)) {
				index = i;
				break;
			}
		}
		return index;
	}


	public Class<?> getCompletionClass() {
		return JstCompletionOnMemberAccess.class;
	}

}
