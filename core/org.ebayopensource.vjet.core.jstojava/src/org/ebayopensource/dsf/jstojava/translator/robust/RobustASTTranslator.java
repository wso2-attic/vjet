/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstDefaultConstructor;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.TopLevelJstBlock;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.translator.BaseTranslator;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.TypeTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.eclipse.mod.wst.jsdt.core.ast.IProgramElement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Statement;

public class RobustASTTranslator extends BaseTranslator {

	public RobustASTTranslator(final TranslateCtx ctx) {
		super(ctx);
	}

	public JstType translate(CompilationUnitDeclaration astUnit) {

		String name = String.valueOf(astUnit.getFileName());
		IJstType jstType = TranslateHelper.findType(m_ctx, name);
		JstType jst = (JstType)TranslateHelper.getJstType(jstType);
		if(jst==null){
			return null;
		}
		
		synchronized(jst) {
			jst.clearAll();
			
			//clear modifiers
			JstModifiers modifiers = jst.getModifiers();
			modifiers.setPublic();
			modifiers.setStatic(false);
			modifiers.setFinal(false);
			modifiers.setAbstract(false);
	
			if (RootRobustTranslator.preTransform(jst, m_ctx,
				m_ctx.getCompletionPos())) {
				m_ctx.setCompletionPos(-1);
			}
	
			IntegerHolder completionPos = new IntegerHolder(m_ctx.getCompletionPos());
	
			StatementGroup group = getStatementGroup(astUnit);
			if (group.m_needsDeclarations.size() > 0) {
				//floating vjo.needs statements
				for (Stack<IProgramElement> elements : group.m_needsDeclarations) {
					new RootRobustTranslator(elements, jst, this, completionPos)
						.transform();
				}
			}
			if (group.m_typeDeclaration != null) { //VJOTYPE
				new RootRobustTranslator(group.m_typeDeclaration, jst, this, completionPos)
						.transform();
				
				// Add a default constructor to the ctype if no constructor is
				// defined.
				if (jst.isClass()) {
					JstMethod constructor = jst.getConstructor();
					if (constructor == null) {
						jst.setConstructor(new JstDefaultConstructor(jst));
					}
				}
				
				JstPackage jstPackage = jst.getPackage();
				if (jstPackage == null) {
					jstPackage = new JstPackage();
					jstPackage.setGroupName(m_ctx.getGroup());
					jst.setPackage(jstPackage);
				}
			}		
			else if (group.m_extraStatements.size() >= 0) {
				//no real type, process all statements as an init block
				Statement[] statements = new Statement[group.m_extraStatements
						.size()];
				statements = group.m_extraStatements.toArray(statements);
				JstSource oldSrc = jst.getSource();
				// Adding 2 to endOffset due to fake block we no longer assume that code assist /validation can't happen on last character
				JstSource src = new JstSource(JstSource.JS, oldSrc == null ? null
						: oldSrc.getBinding(), -1, -1, astUnit.sourceEnd()+2, 0,
						astUnit.sourceEnd()+2);
	
				int index = name.lastIndexOf(".");
				if (index != -1) {
					jst.setPackage(new JstPackage(name.substring(0, index)));
					jst.setSimpleName(name.substring(index+1));
				} else {
					jst.setSimpleName(name);
				}
				jst.setFakeType(true);
				jst.setSource(src);
				TypeTranslator.addInactiveNeeds(jst, m_ctx);
				if (jst.getImports().size() > 0 || jst.getInactiveImports().size() > 0) {
					TopLevelJstBlock proxy = new TopLevelJstBlock();
					jst.setInitBlock(proxy);
					for (IJstType importedType : jst.getImports()) {
						if (importedType.isFakeType()) {
							proxy.addIncludedType(importedType);
						}
					}
					for (IJstType importedType : jst.getInactiveImports()) {
						if (importedType.isFakeType()) {
							proxy.addIncludedType(importedType);
						}
					}
				}
				
				jst.getInitBlock().setSource(src);
	
				m_ctx.setCurrentType(jst);
				m_ctx.enterBlock(ScopeIds.INITS);
	
				JstBlock block = new JstBlock();
				block.setSource(src);
	
				TranslateHelper.addStatementsToJstBlock(statements, block, astUnit
						.sourceEnd(), m_ctx);
				if (m_ctx.getJstErrors().size() == 0) {
					
					JstCompletion completion = null;
					int[] commentOffsets = inCommentRange(astUnit.comments, m_ctx.getCompletionPos());
					if (commentOffsets!=null) {
						char[] source = m_ctx.getOriginalSource();
						StringBuilder commentchars = new StringBuilder();
						for(int i = Math.abs(commentOffsets[0]); i<Math.abs(commentOffsets[1]); i++){
							commentchars.append(source[i]);
						}
						completion = new JstCommentCompletion(
								jst,commentchars.toString(), m_ctx.getCompletionPos() - Math.abs(commentOffsets[0])  );
					}else{
						completion = new JstCompletionOnSingleNameReference(
								jst);
						completion.setToken("");
					}
					final JstSource completionSource = TranslateHelper
							.createJstSource(m_ctx.getSourceUtil(), 0, m_ctx
									.getCompletionPos(), m_ctx.getCompletionPos());
					completion.setSource(completionSource);
					completion.setScopeStack(m_ctx.getScopeStack());
					m_ctx.setCreatedCompletion(true);
					m_ctx.addSyntaxError(completion);
				}
				for (IStmt stmt : block.getStmts()) {
					jst.addInit(stmt, true);			
				}		
				m_ctx.exitBlock();
			}
		}
		return jst;
	}
	
	private int[] inCommentRange(int[][] comments, int completionPos) {
		for(int[] location: comments){
			if(Math.abs(location[0])<completionPos && Math.abs(location[1])+1>completionPos){
				return location;
			}
		}
		return null;
	}

	private static StatementGroup getStatementGroup(CompilationUnitDeclaration astUnit) {		
		StatementGroup group = new StatementGroup();		
		IProgramElement[] statements = astUnit.getStatements();
		for (IProgramElement statement : statements) {
			IProgramElement receiver = statement;
			Stack<IProgramElement> elements = new Stack<IProgramElement>();
			IProgramElement pre = null;
			boolean isExtra = true;
			while (receiver != null) {				
				elements.push(receiver); // put element to the stack
				if (receiver instanceof MessageSend) {
					pre = receiver;
					receiver = ((MessageSend) receiver).receiver;
				} else if (receiver instanceof FieldReference) {
					pre = receiver;
					receiver = ((FieldReference) receiver).receiver;
				} else if (receiver instanceof SingleNameReference) {
					if (VjoKeywords.VJO.equals(((SingleNameReference)receiver).toString())) {
						String name = null;
						if (pre instanceof MessageSend) {
							name = new String(((MessageSend)pre).getSelector());
						}
						else if (pre instanceof FieldReference) {
							name = new String(((FieldReference)pre).getToken());
						}
						if (name != null && isValidTypeDef(name)) {
							group.m_typeDeclaration = elements;
							isExtra = false;
						}
						else if (VjoKeywords.NEEDS.equals(name)) {
							group.m_needsDeclarations.add(elements);
							isExtra = false;
						}
					}
					break;
				} else {
					break;
				}				
			}
			if (isExtra) {
				if (statement instanceof Statement) {
					group.m_extraStatements.add((Statement)statement);
				}
			}
		}
		return group;
	}
	
	private static class StatementGroup {
		Stack<IProgramElement> m_typeDeclaration = null;
		List<Stack<IProgramElement>> m_needsDeclarations = new ArrayList<Stack<IProgramElement>>(1);
		List<Statement> m_extraStatements = new ArrayList<Statement>();
	}
	
	private static Set<String> VJO_TYPE_DEF_KEYS = new HashSet<String>();
	static {
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.TYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.CTYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.ITYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.FTYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.MTYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.OTYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.ETYPE);
		VJO_TYPE_DEF_KEYS.add(VjoKeywords.LTYPE);
	}
	
	private static boolean isValidTypeDef(String name) {
		return VJO_TYPE_DEF_KEYS.contains(name);
	}

}