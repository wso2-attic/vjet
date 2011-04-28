/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
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
import org.ebayopensource.dsf.jst.declaration.JstInferredType;
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

public class JstAstInfoVisitor implements IJstNodeVisitor {
	private String value = "";
	
	private List<String> type = new ArrayList<String>();
	
	public List<String> getType(){
		return type;
	}
	
	public String getValue(){
		return value;
	}

	@Override
	public void visit(BaseJstNode node) {
		value = node.toString();
		type.add(node.getRootType().getSimpleName());
	}

	@Override
	public void visit(JstAnnotation node) {
		value = node.getName().getName();
	}

	@Override
	public void visit(JstArg node) {
		value = node.getName();
		List<IJstTypeReference> actualTypeRef = node.getTypesRef();
		for (IJstTypeReference jstTypeReference : actualTypeRef) {
			type.add(jstTypeReference.getReferencedType().getSimpleName());	
		}
	}

	@Override
	public void visit(JstArrayInitializer node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(JstBlock node) {
		value = node.toBlockText();
		type.add(node.getClass().getSimpleName());
	}

	@Override
	public void visit(JstBlockInitializer node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(JstRawBlock node) {
		value = node.toBlockText();
	}

	@Override
	public void visit(JstDoc node) {
		value = node.getComment();
	}

	@Override
	public void visit(JstIdentifier node) {
		value = node.getName();
	}
	
	@Override
	public void visit(JstProxyIdentifier node) {
		value = node.getName();
	}

	@Override
	public void visit(JstInitializer node) {
		value = node.toText();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstLiteral node) {
		value = node.toSimpleTermText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ArrayLiteral node) {
		value = node.toValueText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ObjLiteral node) {
		value = node.toValueText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(RegexpLiteral node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(SimpleLiteral node) {
		value = node.getValue();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(JstMethod node) {
		value = node.getName().getName();
	}

	@Override
	public void visit(JstConstructor node) {
		value = node.getName().getName();
	}

	@Override
	public void visit(JstModifiers node) {
		value = node.getAccessScope();
	}

	@Override
	public void visit(JstName node) {
		value = node.getName();
	}

	@Override
	public void visit(JstPackage node) {
		value = node.getName();
	}

	@Override
	public void visit(JstProperty node) {
		value = node.getName().getName();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstType node) {
		value = node.getSimpleName();
		type.add(node.getSimpleName());
	}

	@Override
	public void visit(JstArray node) {
		value = node.getSimpleName();
		type.add(node.getElementType().getSimpleName());
	}

	@Override
	public void visit(JstFunctionRefType node) {
		value = node.getSimpleName();
	}

	@Override
	public void visit(JstObjectLiteralType node) {
		value = node.getSimpleName();
	}

	@Override
	public void visit(JstRefType node) {
		value = node.getName();
		try{
			type.add(node.getRefType().getSimpleName());
		}catch(Exception e){}
	}

	@Override
	public void visit(IJstRefType node) {
		value = node.getSimpleName();
		type.add(node.getReferencedNode().getSimpleName());
	}

	@Override
	public void visit(JstTypeReference node) {
		value = node.toString();
		type.add(node.getReferencedType().getSimpleName());
	}

	@Override
	public void visit(JstVar node) {
		value = node.getName();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstVars node) {
		value = node.getAssignments().get(0).getLHS().toLHSText();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(NV node) {
		value = node.getName();
	}

	@Override
	public void visit(JstStmt node) {
		value = node.toString();
	}

	@Override
	public void visit(BoolExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(InfixExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ParenthesizedExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(PostfixExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(PrefixExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ArrayAccessExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ArrayCreationExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(AssignExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(CastExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ConditionalExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(FieldAccessExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(FuncExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ObjCreationExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(MtdInvocationExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ExprStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(CaseStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(BlockStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(CatchStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(ForInStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(ForStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(IfStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(DispatchStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(SwitchStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(TryStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(WhileStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(DoStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(WithStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(BreakStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(ContinueStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(LabeledStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(RtnStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(TypeDeclStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(ListExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(ThisStmt node) {
		value = node.toExprText();
	}

	@Override
	public void visit(TextExpr node) {
		value = node.toExprText();
		type.add(node.getResultType().getSimpleName());
	}

	@Override
	public void visit(TextStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(ThrowStmt node) {
		value = node.toStmtText();
	}

	@Override
	public void visit(PtyGetter node) {
		value = node.toExprText();
	}

	@Override
	public void visit(PtySetter node) {
		value = node.getPtyValue();
	}

	@Override
	public void visit(JstProxyMethod node) {
		value = node.getName().getName();
	}

	@Override
	public void visit(JstProxyProperty node) {
		value = node.getName().getName();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstParamType node) {
		value = node.getName();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstWildcardType node) {
		value = node.getName();
		type.add(node.getType().getSimpleName());
	}

	@Override
	public void visit(JstTypeWithArgs node) {
		value = node.getName();
		type.add(node.getArgType().getSimpleName());
	}

	@Override
	public void visit(JstGlobalVar node) {
		
	}

	@Override
	public void visit(JstGlobalFunc node) {
		value = node.getName().getName();
		
	}

	@Override
	public void visit(JstGlobalProp node) {
		value = node.getName().getName();
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		// TODO Auto-generated method stub
		
	}
	
	public void visit(JstInferredType node) {
		// TODO Auto-generated method stub
		
	}
}
