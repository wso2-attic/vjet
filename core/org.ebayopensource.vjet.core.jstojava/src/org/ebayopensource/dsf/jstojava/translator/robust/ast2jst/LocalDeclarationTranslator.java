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

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.util.JstBindingUtil;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstInferredType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.JsCommentMetaNode;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentCollector;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil.LineInfo;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LocalDeclaration;

public class LocalDeclarationTranslator extends
		BaseAst2JstTranslator<LocalDeclaration, JstVars> {

	@Override
	protected JstVars doTranslate(LocalDeclaration statement) {
		// TODO Should we return JstVar or JstVars
		List<AssignExpr> list = new ArrayList<AssignExpr>();
		LocalDeclaration origStatement = statement;
		IJstType jstType = null;
		IJsCommentMeta meta = null;
		
		final CommentCollector commentCollector = m_ctx.getCommentCollector();
		do {
			String name = String.valueOf(statement.getName());
			JstIdentifier jstIdentifier = new JstIdentifier(name);
			JstSource source = TranslateHelper.getIdentifierSource(statement, m_ctx.getSourceUtil());
			jstIdentifier.setSource(source);
			
			//translate the children 1st
			IExpr init = null;
			List<IJsCommentMeta> metaList = null;
			
			if (statement.initialization != null) {
				init = (IExpr) getTranslatorAndTranslate(statement.initialization);
				BaseJstNode initBaseNode = (BaseJstNode)init;
				if(init != null && init instanceof BaseJstNode){//check if init consumes the comment
					metaList = TranslateHelper.findMetaFromExpr(initBaseNode);
					
				}
				if(metaList != null){
					init = TranslateHelper.getCastable(init, metaList, m_ctx);
					// done with meta array removing from tree
					
//					metaList = null;
//					List<BaseJstNode> children = initBaseNode.getChildren();
//					for (IJstNode child : children) {
//						if (child instanceof JsCommentMetaNode) {
//							initBaseNode.removeChild(child);
//							break;
//						}
//					}
					
				}
				else{
					init = TranslateHelper.getCastable(init, statement, m_ctx);
				}
			}
			
			final int exprStartOffset = statement.declarationSourceStart;
			final int exprEndOffset = findSourceEnd(statement);
			metaList = commentCollector.getCommentMeta(exprStartOffset, exprEndOffset, 
					m_ctx.getPreviousNodeSourceEnd(),
					m_ctx.getNextNodeSourceStart());
			if(metaList != null && metaList.size() > 0){
				meta = metaList.get(0);
			}
			else if(init != null && init instanceof BaseJstNode){//check if init consumes the comment
				BaseJstNode initBaseNode = (BaseJstNode)init;
				metaList = TranslateHelper.findMetaFromExpr(initBaseNode);
				if(metaList != null && metaList.size() > 0){
					meta = metaList.iterator().next();
				}
			}
		
			if (meta != null) {
				if (meta.isCast() && init!=null) {
					jstType = init.getResultType();
				} 
				else if (meta.getTyping() != null){
					jstType = TranslateHelper.findType(m_ctx, meta.getTyping(), meta);
					
					//TODO combine this logic to TranslateHelper#findType
					if(jstType instanceof JstFuncType){
						IJstMethod replacement = null;
						//further check rhs of the assignment
						boolean rhsMethodFound = false;
						if(init != null){
							final IJstNode initBinding = JstBindingUtil.getJstBinding(init);
							rhsMethodFound = initBinding != null && initBinding instanceof JstMethod;
							if(rhsMethodFound){
								replacement = (IJstMethod)initBinding;
								jstIdentifier.setJstBinding(initBinding);
								jstType = init.getResultType() != null ? init.getResultType() : jstType;
							}
						}
						if(!rhsMethodFound){
							// to solve the local function no overloading problem
							// we need to obtain the whole JsCommentMeta list declared for the local variable
							replacement = TranslateHelper.MethodTranslateHelper.createJstSynthesizedMethod(metaList, m_ctx, "");
							jstType = TranslateHelper.replaceSynthesizedMethodBinding(jstIdentifier,
									replacement);
						}
					}
				}
			}
			AssignExpr assignExpr = new AssignExpr(jstIdentifier, init);
			assignExpr.setSource(TranslateHelper.getSource(statement, m_ctx.getSourceUtil()));
			list.add(assignExpr);
		} while ((statement = (LocalDeclaration) statement.nextLocal) != null);

		boolean typeFound = true;
		if (jstType == null) {
			typeFound = false;
			jstType = JstCache.getInstance().getType("Object");
			
			if (jstType == null) {
				jstType = JstFactory.getInstance().createJstType("Object", true);
			}
			//it is an inferred type, not an explicitly declared type
			jstType = new JstInferredType(jstType);
		}
		JstVars vars = new JstVars(jstType);
		if (meta != null) {
			if (meta.getModifiers().isFinal()) {
				vars.setIsFinal(true);
			}
		}
		
		if (origStatement!=null) {
			vars.setSource(getStatementSource(origStatement, m_ctx.getSourceUtil()));
			vars.setComments(commentCollector.getComments(
					m_ctx.getPreviousNodeSourceEnd(),
					findSourceEnd(origStatement)));
		}
		for (AssignExpr assignExpr: list){
			vars.addAssignment(assignExpr);
		}
		if (meta != null && typeFound) {
			IJstTypeReference typeRef = vars.getTypeRef();
			TranslateHelper.setTypeRefSource((BaseJstNode) typeRef, meta);
		}
		
		return vars;
	}
	
	private static int findSourceEnd(LocalDeclaration statement) {
//		final IExpression initialization = statement.getInitialization();
//		if(initialization != null
//				&& initialization instanceof FunctionExpression){
//			return initialization.sourceStart();
//		}
		return statement.declarationSourceEnd;
	}
	
	private static JstSource getStatementSource(LocalDeclaration statement, JstSourceUtil util) {
		LineInfo info = util.lineInfo(statement.declarationSourceStart);
		return new JstSource(JstSource.JS, info.line(), info.colStart(), findSourceEnd(statement)
				- statement.declarationSourceStart + 1, statement.declarationSourceStart, findSourceEnd(statement));
	}
}
