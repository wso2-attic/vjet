/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;

public class OtherTranslator extends BaseTranslator {
	
	//
	// API
	//
	public void processBlock(final Block astBlock, final JstBlock block){

		StatementTranslator stmtTranslator = getStmtTranslator();
		
		for (Object o:astBlock.statements()){
			if (o instanceof Statement){
				IStmt s = stmtTranslator.processStatement((Statement)o, block);
				if (s != null){
					block.addStmt(s);
				}
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, block);
			}
		}
	}
	
	public void processAnnotation(final Annotation astAnnot, final BaseJstNode jstNode){
		JstAnnotation jstAnnotation = new JstAnnotation();
		jstAnnotation.setName(astAnnot.getTypeName().toString());
		jstNode.addAnnotation(jstAnnotation);
		if (astAnnot.isSingleMemberAnnotation()) {
			SingleMemberAnnotation sma = (SingleMemberAnnotation) astAnnot;
			IExpr expr = getExprTranslator().processExpression(sma.getValue(), jstAnnotation);
			if (expr != null) {
				jstAnnotation.addExpr(expr);
			}
		} else if (astAnnot.isNormalAnnotation()) {
			NormalAnnotation na = (NormalAnnotation) astAnnot;
			List<MemberValuePair> values = na.values();
			for (MemberValuePair mvp : values) {
				IExpr expr = getExprTranslator().processExpression(mvp.getValue(), jstAnnotation);
				if (expr != null) {
					AssignExpr ae = new AssignExpr(new JstIdentifier(mvp.getName().toString()),
							expr);
					jstAnnotation.addExpr(ae);
				}
			}		
		}
	}
	
	//
	// Package protected
	//
	JstVar toJstVar(final SingleVariableDeclaration var, JstType jstType){
		IJstType type = getDataTypeTranslator().processType(var.getType(), jstType);
		return new JstVar(type, var.getName().toString());
	}

	JstVar toJstVar(final SingleVariableDeclaration var, JstType jstType, String name){
		IJstType type = getDataTypeTranslator().processType(var.getType(), jstType);
		return new JstVar(type, name);
	}
}
