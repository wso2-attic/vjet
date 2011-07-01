/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
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

public class GenericVisitor implements IJstNodeVisitor {

	protected void visitNode(IJstNode jstNode) {
		visitBefore(jstNode);
		for (IJstNode child : jstNode.getChildren()) {
			visitChild(child);
			visitInMiddle(jstNode, child);
		}
		visitAfter(jstNode);
	}

	private void visitChild(IJstNode child) {
		child.accept(this);
	}

	protected void visitBefore(IJstNode jstNode) {
//		System.out.println("[to validate] ------" +"type is :" +jstNode.getClass().getName()+" content is: " +jstNode.toString());

	}

	protected void visitInMiddle(IJstNode jstNode, IJstNode childJstNode) {
		//System.out.println("[validating:]" + jstNode.toString());

	}

	protected void visitAfter(IJstNode jstNode) {

		//System.out.println("[validated:]" + jstNode.toString());
	}

	public void visit(BaseJstNode arg0) {
		visitNode(arg0);

	}

	public void visit(JstAnnotation arg0) {
		visitNode(arg0);

	}

	public void visit(JstArg arg0) {
		visitNode(arg0);

	}

	public void visit(JstArrayInitializer arg0) {
		visitNode(arg0);

	}

	public void visit(JstBlock arg0) {
		visitNode(arg0);

	}

	public void visit(JstBlockInitializer arg0) {
		visitNode(arg0);

	}

	public void visit(JstRawBlock arg0) {
		visitNode(arg0);

	}

	public void visit(JstDoc arg0) {
		visitNode(arg0);

	}

	public void visit(JstIdentifier arg0) {
		visitNode(arg0);

	}

	public void visit(JstInitializer arg0) {
		visitNode(arg0);

	}

	public void visit(JstLiteral arg0) {
		visitNode(arg0);

	}

	public void visit(ArrayLiteral arg0) {
		visitNode(arg0);

	}

	public void visit(ObjLiteral arg0) {
		visitNode(arg0);

	}

	public void visit(RegexpLiteral arg0) {
		visitNode(arg0);

	}

	public void visit(SimpleLiteral arg0) {
		visitNode(arg0);

	}

	public void visit(JstMethod arg0) {
		visitNode(arg0);

	}

	public void visit(JstConstructor arg0) {
		visitNode(arg0);

	}

	public void visit(JstModifiers arg0) {
		visitNode(arg0);

	}

	public void visit(JstName arg0) {
		visitNode(arg0);

	}

	public void visit(JstPackage arg0) {
		visitNode(arg0);

	}

	public void visit(JstProperty arg0) {
		visitNode(arg0);

	}

	public void visit(JstType arg0) {
		visitNode(arg0);

	}

	public void visit(JstArray arg0) {
		visitNode(arg0);

	}

	public void visit(JstFunctionRefType arg0) {
		visitNode(arg0);

	}

	public void visit(JstObjectLiteralType arg0) {
		visitNode(arg0);

	}

	public void visit(JstRefType arg0) {
		visitNode(arg0);

	}

	public void visit(IJstRefType arg0) {
		visitNode(arg0);

	}

	public void visit(JstTypeReference arg0) {
		visitNode(arg0);

	}

	public void visit(JstVar arg0) {
		visitNode(arg0);

	}

	public void visit(JstVars arg0) {
		visitNode(arg0);

	}

	public void visit(NV arg0) {
		visitNode(arg0);

	}

	public void visit(JstStmt arg0) {
		visitNode(arg0);

	}

	public void visit(BoolExpr arg0) {
		visitNode(arg0);

	}

	public void visit(InfixExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ParenthesizedExpr arg0) {
		visitNode(arg0);

	}

	public void visit(PostfixExpr arg0) {
		visitNode(arg0);

	}

	public void visit(PrefixExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ArrayAccessExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ArrayCreationExpr arg0) {
		visitNode(arg0);

	}

	public void visit(AssignExpr arg0) {
		visitNode(arg0);

	}

	public void visit(CastExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ConditionalExpr arg0) {
		visitNode(arg0);

	}

	public void visit(FieldAccessExpr arg0) {
		visitNode(arg0);

	}

	public void visit(FuncExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ObjCreationExpr arg0) {
		visitNode(arg0);

	}

	public void visit(MtdInvocationExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ExprStmt arg0) {
		visitNode(arg0);

	}

	public void visit(CaseStmt arg0) {
		visitNode(arg0);

	}

	public void visit(BlockStmt arg0) {
		visitNode(arg0);

	}

	public void visit(CatchStmt arg0) {
		visitNode(arg0);

	}

	public void visit(ForInStmt arg0) {
		visitNode(arg0);

	}

	public void visit(ForStmt arg0) {
		visitNode(arg0);

	}

	public void visit(IfStmt arg0) {
		visitNode(arg0);

	}

	public void visit(DispatchStmt arg0) {
		visitNode(arg0);

	}

	public void visit(SwitchStmt arg0) {
		visitNode(arg0);

	}

	public void visit(TryStmt arg0) {
		visitNode(arg0);

	}

	public void visit(WhileStmt arg0) {
		visitNode(arg0);

	}

	public void visit(DoStmt arg0) {
		visitNode(arg0);

	}

	public void visit(WithStmt arg0) {
		visitNode(arg0);

	}

	public void visit(BreakStmt arg0) {
		visitNode(arg0);

	}

	public void visit(ContinueStmt arg0) {
		visitNode(arg0);

	}

	public void visit(LabeledStmt arg0) {
		visitNode(arg0);

	}

	public void visit(RtnStmt arg0) {
		visitNode(arg0);

	}

	public void visit(TypeDeclStmt arg0) {
		visitNode(arg0);

	}

	public void visit(ListExpr arg0) {
		visitNode(arg0);

	}

	public void visit(ThisStmt arg0) {
		visitNode(arg0);

	}

	public void visit(TextExpr arg0) {
		visitNode(arg0);

	}

	public void visit(TextStmt arg0) {
		visitNode(arg0);

	}

	public void visit(ThrowStmt arg0) {
		visitNode(arg0);

	}

	public void visit(PtyGetter arg0) {
		visitNode(arg0);

	}

	public void visit(PtySetter arg0) {
		visitNode(arg0);

	}

	public void visit(JstProxyMethod arg0) {
		visitNode(arg0);

	}

	public void visit(JstProxyProperty arg0) {
		visitNode(arg0);

	}

	public void visit(JstParamType arg0) {
		visitNode(arg0);

	}

	public void visit(JstWildcardType arg0) {
		visitNode(arg0);

	}

	public void visit(JstTypeWithArgs arg0) {
		visitNode(arg0);

	}

	@Override
	public void visit(JstGlobalVar node) {
		visitNode(node);
	}

	@Override
	public void visit(JstGlobalFunc node) {
		visitNode(node);
	}

	@Override
	public void visit(JstGlobalProp node) {
		visitNode(node);		
	}

	@Override
	public void visit(JstProxyIdentifier node) {
		visitNode(node);
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		// TODO Auto-generated method stub
		
	}

}
