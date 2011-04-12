/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.traversal;

import org.ebayopensource.dsf.jst.BaseJstNode;
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
import org.ebayopensource.dsf.jst.stmt.TextStmt;
import org.ebayopensource.dsf.jst.stmt.ThisStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.TypeDeclStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.JstProxyIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.RegexpLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;

public interface IJstNodeVisitor {

	//fundamental jst nodes
	void visit(final BaseJstNode node);
	void visit(final JstAnnotation node);
	void visit(final JstArg node);
	void visit(final JstArrayInitializer node);
	void visit(final JstBlock node);
	void visit(final JstBlockInitializer node);
	void visit(final JstRawBlock node);
	void visit(final JstDoc node);
	void visit(final JstIdentifier node);
	void visit(final JstInitializer node);
	void visit(final JstLiteral node);
	void visit(final ArrayLiteral node);
	void visit(final ObjLiteral node);
	void visit(final RegexpLiteral node);
	void visit(final SimpleLiteral node);
	void visit(final JstMethod node);
	void visit(final JstConstructor node);
	void visit(final JstModifiers node);
	void visit(final JstName node);
	void visit(final JstPackage node);
	void visit(final JstProperty node);
	void visit(final JstType node);
	void visit(final JstArray node);
	void visit(final JstFunctionRefType node);
	void visit(final JstObjectLiteralType node);
	void visit(final JstRefType node);
	void visit(final IJstRefType node);
	void visit(final JstTypeReference node);
	void visit(final JstVar node);
	void visit(final JstVars node);
	void visit(final NV node);
	void visit(final JstStmt node);
	void visit(final JstGlobalVar node);
	void visit(final JstGlobalFunc node);
	void visit(final JstGlobalProp node);
	
	
	//expressions
	void visit(final BoolExpr node);
	void visit(final InfixExpr node);
	void visit(final ParenthesizedExpr node);
	void visit(final PostfixExpr node);
	void visit(final PrefixExpr node);
	void visit(final ArrayAccessExpr node);
	void visit(final ArrayCreationExpr node);
	void visit(final AssignExpr node);
	void visit(final CastExpr node);
	void visit(final ConditionalExpr node);
	void visit(final FieldAccessExpr node);
	void visit(final FuncExpr node);
	void visit(final ObjCreationExpr node);
	void visit(final MtdInvocationExpr node);
	
	//statements
	void visit(final ExprStmt node);
	void visit(final CaseStmt node);
	void visit(final BlockStmt node);
	void visit(final CatchStmt node);
	void visit(final ForInStmt node);
	void visit(final ForStmt node);
	void visit(final IfStmt node);
	void visit(final DispatchStmt node);
	void visit(final SwitchStmt node);
	void visit(final TryStmt node);
	void visit(final WhileStmt node);
	void visit(final DoStmt node);
	void visit(final WithStmt node);
	void visit(final BreakStmt node);
	void visit(final ContinueStmt node);
	void visit(final LabeledStmt node);
	void visit(final RtnStmt node);
	void visit(final TypeDeclStmt node);
	void visit(final ListExpr node);
	void visit(final ThisStmt node);
	void visit(final TextExpr node);
	void visit(final TextStmt node);
	void visit(final ThrowStmt node);

	//others
	void visit(final PtyGetter node);
	void visit(final PtySetter node);
	void visit(final JstProxyMethod node);
	void visit(final JstProxyProperty node);
	void visit(final JstProxyIdentifier node);
	void visit(final JstParamType node);
	void visit(final JstWildcardType node);
	void visit(final JstTypeWithArgs node);
	void visit(final BaseJsCommentMetaNode<?> node);
}
