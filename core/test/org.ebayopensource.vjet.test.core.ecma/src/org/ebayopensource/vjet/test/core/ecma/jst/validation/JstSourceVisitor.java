/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.test.core.ecma.jst.validation;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstGlobalFunc;
import org.ebayopensource.dsf.jst.declaration.JstGlobalProp;
import org.ebayopensource.dsf.jst.declaration.JstGlobalVar;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstRawBlock;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.ArrayCreationExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.expr.ListExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.meta.BaseJsCommentMetaNode;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.DispatchStmt;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;
import org.ebayopensource.dsf.jst.stmt.JstStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.stmt.TextStmt;
import org.ebayopensource.dsf.jst.stmt.ThisStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.TypeDeclStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.JstProxyIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.RegexpLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstSourceVisitor implements IJstNodeVisitor {

	public List<String> m_failures = new ArrayList<String>();
	
	public void visit(BaseJstNode node) {
		validateJstSource(node, "");
	}

	private void validateJstSource(IJstNode node){
		validateJstSource(node, "");
	}
	
	
	private void validateJstSource(IJstNode node, String message) {
		if (node instanceof ISynthesized) {
			return;
		}
		if(node.getSource()==null){
			m_failures.add(node.getClass().getName() + "\t" + message );
		}
		visitChildren(node);
	}
	
	
	private void visitChildren(IJstNode jstNode) {
		for (IJstNode child : jstNode.getChildren()) {
			visitChild(child);
		}
	}
	
	private void visitChild(IJstNode child) {
		child.accept(this);
	}
	

	public void visit(JstAnnotation node) {
		validateJstSource(node);
	}

	public void visit(JstArg node) {
		validateJstSource(node);
	}

	public void visit(JstArrayInitializer node) {
		validateJstSource(node);

	}

	public void visit(JstBlock node) {
		validateJstSource(node);

	}

	public void visit(JstBlockInitializer node) {
		validateJstSource(node);

	}

	public void visit(JstRawBlock node) {
		validateJstSource(node);

	}

	public void visit(JstDoc node) {
		validateJstSource(node);

	}

	public void visit(JstIdentifier node) {
		if("arguments".equals(node.toExprText())){
			visitChildren(node);
			return;
		}
		validateJstSource(node, node.toExprText());

	}

	public void visit(JstInitializer node) {
		if(node.toText().contains("arguments")){
			visitChildren(node);
			return;
		}
		validateJstSource(node, node.toText());

	}

	public void visit(JstLiteral node) {
		validateJstSource(node);

	}

	public void visit(ArrayLiteral node) {
		validateJstSource(node);
	}

	public void visit(ObjLiteral node) {
		validateJstSource(node);

	}

	public void visit(RegexpLiteral node) {
		validateJstSource(node);

	}

	public void visit(SimpleLiteral node) {
		validateJstSource(node);

	}

	public void visit(JstMethod node) {
		validateJstSource(node);

	}

	public void visit(JstConstructor node) {
		validateJstSource(node);

	}

	public void visit(JstModifiers node) {
		validateJstSource(node);
	}

	public void visit(JstName node) {
		validateJstSource(node);

	}

	public void visit(JstPackage node) {
		validateJstSource(node);

	}

	public void visit(JstProperty node) {
		validateJstSource(node);

	}

	public void visit(JstType node) {
		validateJstSource(node);

	}

	public void visit(JstArray node) {
		validateJstSource(node);

	}

	public void visit(JstFunctionRefType node) {
		validateJstSource(node);

	}

	public void visit(JstObjectLiteralType node) {
		validateJstSource(node);

	}

	public void visit(JstRefType node) {
		validateJstSource(node);

	}

	public void visit(IJstRefType node) {
		validateJstSource(node);

	}
	
//	public void visit(JstPrototypeType node) {
//		validateJstSource(node);
//
//	}

	public void visit(JstTypeReference node) {
//		validateJstSource(node);
	}

	public void visit(JstVar node) {
		validateJstSource(node);

	}

	public void visit(JstVars node) {
		if(node.toText().contains("arguments")){
			visitChildren(node);
			return;
			
		}
		validateJstSource(node);
	}

	public void visit(NV node) {
		validateJstSource(node);

	}

	public void visit(JstStmt node) {
		validateJstSource(node);

	}

	public void visit(BoolExpr node) {
		validateJstSource(node);

	}

	public void visit(InfixExpr node) {
		validateJstSource(node);

	}

	public void visit(ParenthesizedExpr node) {
		validateJstSource(node);

	}

	public void visit(PostfixExpr node) {
		validateJstSource(node);

	}

	public void visit(PrefixExpr node) {
		validateJstSource(node);

	}

	public void visit(ArrayAccessExpr node) {
		validateJstSource(node);

	}

	public void visit(ArrayCreationExpr node) {
		validateJstSource(node);

	}

	public void visit(AssignExpr node) {
		if(node.toExprText().equals("arguments=")){
			visitChildren(node);
			return;
		}
		validateJstSource(node, node.toExprText());

	}

	public void visit(CastExpr node) {
		validateJstSource(node);

	}

	public void visit(ConditionalExpr node) {
		validateJstSource(node);

	}

	public void visit(FieldAccessExpr node) {
		validateJstSource(node);

	}

	public void visit(FuncExpr node) {
		validateJstSource(node);

	}

	public void visit(ObjCreationExpr node) {
		validateJstSource(node);

	}

	public void visit(MtdInvocationExpr node) {
		validateJstSource(node);

	}

	public void visit(ExprStmt node) {
		validateJstSource(node);

	}

	public void visit(CaseStmt node) {
		validateJstSource(node);

	}

	public void visit(BlockStmt node) {
		validateJstSource(node);

	}

	public void visit(CatchStmt node) {
		validateJstSource(node);

	}

	public void visit(ForInStmt node) {
		validateJstSource(node);

	}

	public void visit(ForStmt node) {
		validateJstSource(node);

	}

	public void visit(IfStmt node) {
		validateJstSource(node);

	}

	public void visit(DispatchStmt node) {
		validateJstSource(node);

	}

	public void visit(SwitchStmt node) {
		validateJstSource(node);

	}

	public void visit(TryStmt node) {
		validateJstSource(node);

	}

	public void visit(WhileStmt node) {
		validateJstSource(node);

	}

	public void visit(DoStmt node) {
		validateJstSource(node);

	}

	public void visit(WithStmt node) {
		validateJstSource(node);

	}

	public void visit(BreakStmt node) {
		validateJstSource(node);

	}

	public void visit(ContinueStmt node) {
		validateJstSource(node);

	}

	public void visit(LabeledStmt node) {
		validateJstSource(node);

	}

	public void visit(RtnStmt node) {
		validateJstSource(node);

	}

	public void visit(TypeDeclStmt node) {
		validateJstSource(node);

	}

	public void visit(ListExpr node) {
		validateJstSource(node);

	}

	public void visit(ThisStmt node) {
		validateJstSource(node);

	}

	public void visit(TextExpr node) {
		validateJstSource(node);

	}

	public void visit(TextStmt node) {
		validateJstSource(node);

	}

	public void visit(ThrowStmt node) {
		validateJstSource(node);

	}

	public void visit(PtyGetter node) {
		validateJstSource(node);

	}

	public void visit(PtySetter node) {
		validateJstSource(node);

	}

	public void visit(JstProxyMethod node) {
//		validateJstSource(node);

	}

	public void visit(JstProxyProperty node) {
//		validateJstSource(node);

	}

	public void visit(JstParamType node) {
//		validateJstSource(node);

	}

	public void visit(JstWildcardType node) {
//		validateJstSource(node);

	}

	public void visit(JstTypeWithArgs node) {
//		validateJstSource(node);

	}

	@Override
	public void visit(JstGlobalVar node) {
		validateJstSource(node);
	}

	@Override
	public void visit(JstGlobalFunc node) {
		validateJstSource(node);
	}

	@Override
	public void visit(JstGlobalProp node) {
		validateJstSource(node);
	}

	@Override
	public void visit(JstProxyIdentifier node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		validateJstSource(node);
	}
}
