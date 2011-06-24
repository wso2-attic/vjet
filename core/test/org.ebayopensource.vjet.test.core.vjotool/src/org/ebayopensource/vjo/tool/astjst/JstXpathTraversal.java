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
import java.util.Stack;

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
import org.ebayopensource.vjo.tool.astjst.AstJstTestUtil.AstJstInput;


public class JstXpathTraversal implements IJstNodeVisitor, Cloneable {
	private List<IJstNode> listJstNode = new ArrayList<IJstNode>();
	private Stack<AstJstInput> stackInput = new Stack<AstJstInput>();
	private AstJstInput resultNode;
	private boolean isContinue = true;
	private AstJstInput expectedNode;
	private List<IJstNode> listXPathNodes = new ArrayList<IJstNode>();

	public JstXpathTraversal(List<AstJstInput> inputList) {
		int i = inputList.size() - 1;
		for (; i >= 0; i--) {
			stackInput.push(inputList.get(i));
		}
		resultNode = stackInput.firstElement();
	}
	
	public List<IJstNode> getFoundNodes() {
		return listJstNode;
	}

	private void traverse(IJstNode node) {
		expectedNode = stackInput.lastElement();
		
		boolean isLastNode = checkIsLastNode(node);
		boolean isNodeFound;
		
		if(!isLastNode){
			isNodeFound = validateNonLastNode(node);
			if(isNodeFound){
				stackInput.pop();
				visitChildren(node);
			}else{
				prefixTraversal(node);
			}
		}else if(checkParent(node) && isContinue){
			isNodeFound = validateLastNode(node);
			if(isNodeFound){
				System.err.println(expectedNode.getNode());
				addToList(node);
			}else{
				prefixTraversal(node);
			}
		}
	}

	private boolean checkParent(IJstNode node){
		boolean check = false;
		while(node!=null){
			if(node.equals(listXPathNodes.get(listXPathNodes.size()-1))){
				check = true;
				break;
			}
			else{
				node = node.getParentNode();
			}
		}
		return check;
	}
	
	private boolean validateNonLastNode(IJstNode node){
		boolean check = false;
		if(expectedNode.getNode().equals(node.getClass().getSimpleName())){
			if((expectedNode.getNodeName()!=null && 
				expectedNode.getNodeName().equals(AstJstTestUtil.getNodeName(node))) ||
					(expectedNode.getNodeName()== null)){
				if(expectedNode.getNodePosition()==1){
					check = true;
					System.err.print(expectedNode.getNode()+" --> ");
					listXPathNodes.add(node);
				}
				else{
					expectedNode.setNodePosition(expectedNode.getNodePosition()-1);
				}
			}
		}
		return check;
	}
	
	private boolean validateLastNode(IJstNode node){
		boolean check = false;
		if(expectedNode.getNode().equals(node.getClass().getSimpleName())){
			if((expectedNode.getNodeName().equals(AstJstTestUtil.getNodeName(node))) ||
					(expectedNode.getNodeName().equals("*"))){
				if(expectedNode.getNodePosition() == -1){
					check = true;
					//listXPathNodes.add(node);
				}
				else if(expectedNode.getNodePosition() == 1){
					check = true;
					//listXPathNodes.add(node);
					isContinue = false;
				}
				else{
					expectedNode.setNodePosition(expectedNode.getNodePosition() - 1);
				}
			}
		}
		return check;
	}
	
	private boolean checkIsLastNode(IJstNode node){
		boolean check = false;
		if(expectedNode.equals(resultNode)){
			check = true;
		}
		return check;
	}
	
	private void prefixTraversal(IJstNode node){
		String prefix = expectedNode.getNodePrefix();
		if(prefix.equals("/")){
			return;
		}else if(prefix.equals("//")){
			visitChildren(node);
		}else if(prefix.equals("*")){
			expectedNode.setNodePrefix("/");
			visitGrandChildren(node.getParentNode());
		}
	}
	
	private void visitChildren(IJstNode jstNode) {
		for (IJstNode child : jstNode.getChildren()) {
			child.accept(this);
		}
	}

	private void visitGrandChildren(IJstNode jstNode) {
		for (IJstNode child : jstNode.getChildren()) {
			visitChildren(child);
		}
	}
	
	private void addToList(IJstNode node){
		listJstNode.add(node);
	}
	
	@Override
	public void visit(BaseJstNode node) {
		traverse(node);
	}

	@Override
	public void visit(JstAnnotation node) {
		traverse(node);
	}

	@Override
	public void visit(JstArg node) {
		traverse(node);
	}

	@Override
	public void visit(JstArrayInitializer node) {
		traverse(node);
	}

	@Override
	public void visit(JstBlock node) {
		traverse(node);
	}

	@Override
	public void visit(JstBlockInitializer node) {
		traverse(node);
	}

	@Override
	public void visit(JstRawBlock node) {
		traverse(node);
	}

	@Override
	public void visit(JstDoc node) {
		traverse(node);
	}

	@Override
	public void visit(JstIdentifier node) {
		traverse(node);
	}

	@Override
	public void visit(JstInitializer node) {
		traverse(node);
	}

	@Override
	public void visit(JstLiteral node) {
		traverse(node);
	}

	@Override
	public void visit(ArrayLiteral node) {
		traverse(node);
	}

	@Override
	public void visit(ObjLiteral node) {
		traverse(node);
	}

	@Override
	public void visit(RegexpLiteral node) {
		traverse(node);
	}

	@Override
	public void visit(SimpleLiteral node) {
		traverse(node);
	}

	@Override
	public void visit(JstMethod node) {
		traverse(node);
	}

	@Override
	public void visit(JstConstructor node) {
		traverse(node);
	}

	@Override
	public void visit(JstModifiers node) {
		traverse(node);
	}

	@Override
	public void visit(JstName node) {
		traverse(node);
	}

	@Override
	public void visit(JstPackage node) {
		traverse(node);
	}

	@Override
	public void visit(JstProperty node) {
		traverse(node);
	}

	@Override
	public void visit(JstType node) {
		traverse(node);
	}

	@Override
	public void visit(JstArray node) {
		traverse(node);
	}

	@Override
	public void visit(JstFunctionRefType node) {
		traverse(node);
	}

	@Override
	public void visit(JstObjectLiteralType node) {
		traverse(node);
	}

	@Override
	public void visit(JstRefType node) {
		traverse(node);
	}

	@Override
	public void visit(IJstRefType node) {
	}

	@Override
	public void visit(JstTypeReference node) {
		traverse(node);
	}

	@Override
	public void visit(JstVar node) {
		traverse(node);
	}

	@Override
	public void visit(JstVars node) {
		traverse(node);
	}

	@Override
	public void visit(NV node) {
		traverse(node);
	}

	@Override
	public void visit(JstStmt node) {
		traverse(node);
	}

	@Override
	public void visit(BoolExpr node) {
		traverse(node);
	}

	@Override
	public void visit(InfixExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ParenthesizedExpr node) {
		traverse(node);
	}

	@Override
	public void visit(PostfixExpr node) {
		traverse(node);
	}

	@Override
	public void visit(PrefixExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ArrayAccessExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ArrayCreationExpr node) {
		traverse(node);
	}

	@Override
	public void visit(AssignExpr node) {
		traverse(node);
	}

	@Override
	public void visit(CastExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ConditionalExpr node) {
		traverse(node);
	}

	@Override
	public void visit(FieldAccessExpr node) {
		traverse(node);
	}

	@Override
	public void visit(FuncExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ObjCreationExpr node) {
		traverse(node);
	}

	@Override
	public void visit(MtdInvocationExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ExprStmt node) {
		traverse(node);
	}

	@Override
	public void visit(CaseStmt node) {
		traverse(node);
	}

	@Override
	public void visit(BlockStmt node) {
		traverse(node);
	}

	@Override
	public void visit(CatchStmt node) {
		traverse(node);
	}

	@Override
	public void visit(ForInStmt node) {
		traverse(node);
	}

	@Override
	public void visit(ForStmt node) {
		traverse(node);
	}

	@Override
	public void visit(IfStmt node) {
		traverse(node);
	}

	@Override
	public void visit(DispatchStmt node) {
		traverse(node);
	}

	@Override
	public void visit(SwitchStmt node) {
		traverse(node);
	}

	@Override
	public void visit(TryStmt node) {
		traverse(node);
	}

	@Override
	public void visit(WhileStmt node) {
		traverse(node);
	}

	@Override
	public void visit(DoStmt node) {
		traverse(node);
	}

	@Override
	public void visit(WithStmt node) {
		traverse(node);
	}

	@Override
	public void visit(BreakStmt node) {
		traverse(node);
	}

	@Override
	public void visit(ContinueStmt node) {
		traverse(node);
	}

	@Override
	public void visit(LabeledStmt node) {
		traverse(node);
	}

	@Override
	public void visit(RtnStmt node) {
		traverse(node);
	}

	@Override
	public void visit(TypeDeclStmt node) {
		traverse(node);
	}

	@Override
	public void visit(ListExpr node) {
		traverse(node);
	}

	@Override
	public void visit(ThisStmt node) {
		traverse(node);
	}

	@Override
	public void visit(TextExpr node) {
		traverse(node);
	}

	@Override
	public void visit(TextStmt node) {
		traverse(node);
	}

	@Override
	public void visit(ThrowStmt node) {
		traverse(node);
	}

	@Override
	public void visit(PtyGetter node) {
		traverse(node);
	}

	@Override
	public void visit(PtySetter node) {
		traverse(node);
	}

	@Override
	public void visit(JstProxyMethod node) {
	}

	@Override
	public void visit(JstProxyProperty node) {
	}

	@Override
	public void visit(JstParamType node) {
	}

	@Override
	public void visit(JstWildcardType node) {
	}

	@Override
	public void visit(JstTypeWithArgs node) {
	}

	@Override
	public void visit(JstGlobalVar node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JstGlobalFunc node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JstGlobalProp node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JstProxyIdentifier node) {
		traverse(node);
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		traverse(node);
	}
}
