/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.JstSource;
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
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

class JstUtilVisitor implements IJstNodeVisitor {

	private List<BaseJstNode> m_foundNodes = new ArrayList<BaseJstNode>();
	private int m_startOffset;
	private int m_endOffset;
	private boolean m_visitOverLoadedNodes = false;
	private boolean m_countIdentifierEndOffset = false;

	public JstUtilVisitor(int startOffset, int endOffset) {
		this(startOffset, endOffset, false);
	}
	
	public JstUtilVisitor(int startOffset, int endOffset, boolean visitOverLoadedNodes) {
		this(startOffset, endOffset, false, false);
	}
	
	/**
	 * @param startOffset
	 * @param endOffset
	 * @param visitOverLoadedNodes
	 * @param countIdentifierEndOffset check if the an node should be counted in if the end offset is after the JstIdentifier (the cursor is at the end of Identifer)
	 */
	public JstUtilVisitor(int startOffset, int endOffset, boolean visitOverLoadedNodes, boolean countIdentifierEndOffset) {
		this.m_startOffset = startOffset;
		this.m_endOffset = endOffset;
		this.m_visitOverLoadedNodes = visitOverLoadedNodes;
		this.m_countIdentifierEndOffset = countIdentifierEndOffset;
		if (m_countIdentifierEndOffset && m_endOffset > m_startOffset) {
			m_endOffset = m_endOffset - 1;
		}
	}
	
	private void checkNode(BaseJstNode node) {
		//TODO: we are excluding JsNaive type since the seems to be
		// a recursion happing when visiting the children nodes
		if (node instanceof JstType) {
			JstType jst = (JstType) node;
			if (jst.getAlias() != null &&
				jst.getAlias().startsWith("org.ebayopensource.dsf.jsnative")) {
					return;
			}
		}
//		System.out.println("JstUtilVisitor: checking " + node.getClass().getName());
		JstSource elementSource = node.getSource();
		if (elementSource != null &&
			includes(elementSource.getStartOffSet(), elementSource
					.getEndOffSet(), m_startOffset, m_endOffset)) {
//			System.out.println("JstUtilVisitor: found " + node.getClass().getName());
			m_foundNodes.add(node);
			
		}
		visitChildren(node);
	}
	
	
	static boolean includes(int elementStart, int elementEnd,
			int selectionStart, int selectionEnd) {
		return elementStart <= selectionStart && elementEnd >= selectionEnd;
	}

	private void visitChildren(IJstNode jstNode) {
		for (IJstNode child : jstNode.getChildren()) {
			child.accept(this);
		}
	}
	
	public void visit(BaseJstNode node) {
//		checkNode(node);
	}

	public void visit(JstAnnotation node) {
		checkNode(node);
	}

	public void visit(JstArg node) {
		checkNode(node);
	}

	public void visit(JstArrayInitializer node) {
		checkNode(node);

	}

	public void visit(JstBlock node) {
		checkNode(node);

	}

	public void visit(JstBlockInitializer node) {
		checkNode(node);

	}

	public void visit(JstRawBlock node) {
		checkNode(node);

	}

	public void visit(JstDoc node) {
		checkNode(node);

	}

	public void visit(JstIdentifier node) {
		checkNode(node);

	}

	public void visit(JstInitializer node) {
		checkNode(node);

	}

	public void visit(JstLiteral node) {
		checkNode(node);

	}

	public void visit(ArrayLiteral node) {
		checkNode(node);
	}

	public void visit(ObjLiteral node) {
		checkNode(node);

	}

	public void visit(RegexpLiteral node) {
		checkNode(node);

	}

	public void visit(SimpleLiteral node) {
		checkNode(node);

	}

	public void visit(JstMethod node) {
		checkNode(node);
		if (!m_visitOverLoadedNodes) {
			return;
		}
		if (node.isDispatcher()) {
			for (IJstMethod method : node.getOverloaded()) {
				visit((JstMethod)method);
			}
		}
	}

	public void visit(JstConstructor node) {
		checkNode(node);
		if (!m_visitOverLoadedNodes) {
			return;
		}
		if (node.isDispatcher()) {
			for (IJstMethod method : node.getOverloaded()) {
				visit((JstMethod)method);
			}
		}
	}

	public void visit(JstModifiers node) {
		checkNode(node);
	}

	public void visit(JstName node) {
		checkNode(node);

	}

	public void visit(JstPackage node) {
		checkNode(node);

	}

	public void visit(JstProperty node) {
		checkNode(node);

	}

	public void visit(JstType node) {
		checkNode(node);
	}

	public void visit(JstArray node) {
		checkNode(node);

	}

	public void visit(JstFunctionRefType node) {
		checkNode(node);

	}

	public void visit(JstObjectLiteralType node) {
		checkNode(node);

	}

	public void visit(JstRefType node) {
		checkNode(node);

	}

	public void visit(IJstRefType node) {
//		checkNode(node);
	}

	public void visit(JstTypeReference node) {
		checkNode(node);
	}

	public void visit(JstVar node) {
		checkNode(node);
	}

	public void visit(JstVars node) {
		checkNode(node);
	}

	public void visit(NV node) {
		checkNode(node);
	}

	public void visit(JstStmt node) {
		checkNode(node);
	}

	public void visit(BoolExpr node) {
		checkNode(node);
	}

	public void visit(InfixExpr node) {
		checkNode(node);

	}

	public void visit(ParenthesizedExpr node) {
		checkNode(node);
	}

	public void visit(PostfixExpr node) {
		checkNode(node);
	}

	public void visit(PrefixExpr node) {
		checkNode(node);
	}

	public void visit(ArrayAccessExpr node) {
		checkNode(node);
	}

	public void visit(ArrayCreationExpr node) {
		checkNode(node);
	}

	public void visit(AssignExpr node) {
		checkNode(node);
	}

	public void visit(CastExpr node) {
		checkNode(node);
	}

	public void visit(ConditionalExpr node) {
		checkNode(node);

	}

	public void visit(FieldAccessExpr node) {
		checkNode(node);

	}

	public void visit(FuncExpr node) {
		checkNode(node);

	}

	public void visit(ObjCreationExpr node) {
		checkNode(node);

	}

	public void visit(MtdInvocationExpr node) {
		checkNode(node);

	}

	public void visit(ExprStmt node) {
		checkNode(node);

	}

	public void visit(CaseStmt node) {
		checkNode(node);

	}

	public void visit(BlockStmt node) {
		checkNode(node);

	}

	public void visit(CatchStmt node) {
		checkNode(node);

	}

	public void visit(ForInStmt node) {
		checkNode(node);

	}

	public void visit(ForStmt node) {
		checkNode(node);

	}

	public void visit(IfStmt node) {
		checkNode(node);

	}

	public void visit(DispatchStmt node) {
		checkNode(node);

	}

	public void visit(SwitchStmt node) {
		checkNode(node);

	}

	public void visit(TryStmt node) {
		checkNode(node);

	}

	public void visit(WhileStmt node) {
		checkNode(node);

	}

	public void visit(DoStmt node) {
		checkNode(node);

	}

	public void visit(WithStmt node) {
		checkNode(node);

	}

	public void visit(BreakStmt node) {
		checkNode(node);

	}

	public void visit(ContinueStmt node) {
		checkNode(node);

	}

	public void visit(LabeledStmt node) {
		checkNode(node);

	}

	public void visit(RtnStmt node) {
		checkNode(node);

	}

	public void visit(TypeDeclStmt node) {
		checkNode(node);

	}

	public void visit(ListExpr node) {
		checkNode(node);

	}

	public void visit(ThisStmt node) {
		checkNode(node);

	}

	public void visit(TextExpr node) {
		checkNode(node);

	}

	public void visit(TextStmt node) {
		checkNode(node);

	}

	public void visit(ThrowStmt node) {
		checkNode(node);

	}

	public void visit(PtyGetter node) {
		checkNode(node);

	}

	public void visit(PtySetter node) {
		checkNode(node);

	}

	public void visit(JstProxyMethod node) {
//		checkNode(node);

	}

	public void visit(JstProxyProperty node) {
//		checkNode(node);

	}

	public void visit(JstParamType node) {
//		checkNode(node);

	}

	public void visit(JstWildcardType node) {
//		checkNode(node);

	}

	public void visit(JstTypeWithArgs node) {
//		checkNode(node);

	}

	public List<BaseJstNode> getFoundNodes() {
		return m_foundNodes;
	}

	@Override
	public void visit(JstGlobalVar node) {
		checkNode(node);
	}

	@Override
	public void visit(JstGlobalFunc node) {
		checkNode(node);
	}

	@Override
	public void visit(JstGlobalProp node) {
		checkNode(node);
	}

	@Override
	public void visit(JstProxyIdentifier node) {
		checkNode(node);
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		checkNode(node);
	}
}
