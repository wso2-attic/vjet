/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.util;

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

public class PrintJstBindingTree implements IJstNodeVisitor {

	private static final String TAB = "\t";
	private int m_indentLevel;

	public void visit(BaseJstNode node) {
//		printNode(node);

	}

	public void visit(JstAnnotation node) {
	}

	public void visit(JstArg node) {
		
	}

	public void visit(JstArrayInitializer node) {

	}

	public void visit(JstBlock node) {
//		printNode(node);
		m_indentLevel++;
		visitChildren(node);
		m_indentLevel--;
	}

	public void visit(JstBlockInitializer node) {
		

	}

	public void visit(JstRawBlock node) {
		
	}

	public void visit(JstDoc node) {
		

	}

	public void visit(JstIdentifier node) {
		printBinding(node.getJstBinding());
	}

	private void printBinding(IJstNode jstBinding) {
		if(jstBinding!=null)
		System.out.println("BINDING:" + jstBinding.toString()); //KEEPME
		
	}

	public void visit(JstInitializer node) {
		

	}

	public void visit(JstLiteral node) {
		

	}

	public void visit(ArrayLiteral node) {
		

	}

	public void visit(ObjLiteral node) {
		
		m_indentLevel++;
		visitChildren(node);
		m_indentLevel--;

	}

	public void visit(RegexpLiteral node) {
		

	}

	public void visit(SimpleLiteral node) {
		
		m_indentLevel++;
		visitChildren(node);
		m_indentLevel--;

	}

	public void visit(JstMethod node) {
		
		m_indentLevel++;
		visitChildren(node);
		m_indentLevel--;

	}

	public void visit(JstConstructor node) {
	

	}

	public void visit(JstModifiers node) {
		

	}

	public void visit(JstName node) {
		

	}

	public void visit(JstPackage node) {
		

	}

	public void visit(JstProperty node) {
		
	}

	public void visit(JstType node) {
			
			
			m_indentLevel++;
			visitChildren(node);
			m_indentLevel--;
			

	}

	public void visit(JstArray node) {
			

	}

	public void visit(JstFunctionRefType node) {
		

	}

	public void visit(JstObjectLiteralType node) {
			

	}

	public void visit(JstRefType node) {
			

	}

	public void visit(IJstRefType node) {
			

	}

	public void visit(JstTypeReference node) {
			

	}

	public void visit(JstVar node) {
			

	}

	public void visit(JstVars node) {
		
			System.out.println(printIndent() +"["+node.getClass().getSimpleName()+":"+node.toString().replaceAll("\\s+", " ") +" vars type:"+ node.getType().getName() +" ]"); //KEEPME 
		
			m_indentLevel++;
			visitChildren(node);
			m_indentLevel--;

	}

	public void visit(NV node) {
			printNode(node);
			m_indentLevel++;
			visitChildren(node);
			m_indentLevel--;

	}

	public void visit(JstStmt node) {
			

	}

	public void visit(BoolExpr node) {
			

	}

	public void visit(InfixExpr node) {
			

	}

	public void visit(ParenthesizedExpr node) {
			

	}

	public void visit(PostfixExpr node) {
			

	}

	public void visit(PrefixExpr node) {
			

	}

	public void visit(ArrayAccessExpr node) {
			

	}

	public void visit(ArrayCreationExpr node) {
			

	}

	public void visit(AssignExpr node) {
			

	}

	public void visit(CastExpr node) {
			

	}

	public void visit(ConditionalExpr node) {
			

	}

	public void visit(FieldAccessExpr node) {
		System.out.print(printIndent() +"["+node.getClass().getSimpleName()+":"+node.toString().replaceAll("\\s+", " ") ); //KEEPME 
		if(node.getType()!=null){
		System.out.print(",Type = " +node.getType().getName()); //KEEPME 
		}
		System.out.println(" ]"); //KEEPME 

	}

	public void visit(FuncExpr node) {
			printNode(node);

	}

	public void visit(ObjCreationExpr node) {
			printNode(node);

	}

	public void visit(MtdInvocationExpr node) {
			printNode(node);
			m_indentLevel++;
			visitChildren(node);
			m_indentLevel--;

	}

	public void visit(ExprStmt node) {
		

	}

	public void visit(CaseStmt node) {
		

	}

	public void visit(BlockStmt node) {
		

	}

	public void visit(CatchStmt node) {
		

	}

	public void visit(ForInStmt node) {
		

	}

	public void visit(ForStmt node) {
		
	}

	public void visit(IfStmt node) {
		
	}

	public void visit(DispatchStmt node) {
		

	}

	public void visit(SwitchStmt node) {
		

	}

	public void visit(TryStmt node) {
		

	}

	public void visit(WhileStmt node) {
		

	}

	public void visit(DoStmt node) {
		

	}

	public void visit(WithStmt node) {
		

	}

	public void visit(BreakStmt node) {
		

	}

	public void visit(ContinueStmt node) {
		

	}

	public void visit(LabeledStmt node) {
		

	}

	public void visit(RtnStmt node) {
		

	}

	public void visit(TypeDeclStmt node) {
		

	}

	public void visit(ListExpr node) {
		

	}

	public void visit(ThisStmt node) {
		

	}

	public void visit(TextExpr node) {
		

	}

	public void visit(TextStmt node) {
		

	}

	public void visit(ThrowStmt node) {
		

	}

	public void visit(PtyGetter node) {
		

	}

	public void visit(PtySetter node) {
		

	}

	public void visit(JstProxyMethod node) {
		

	}

	public void visit(JstProxyProperty node) {
		

	}

	public void visit(JstParamType node) {
		

	}

	public void visit(JstWildcardType node) {
		

	}

	public void visit(JstTypeWithArgs node) {
			

	}
	
//	public void visit(JstPrototypeType node) {
//		printNode(node);
//	
//	}
	
	private void printNode(IJstNode node){
		String space = printIndent();
		
		
	
		System.out.print(space +"["+node.getClass().getSimpleName()+":"+node.toString().replaceAll("\\s+", " ") ); //KEEPME 
		
		
		System.out.println(" ]"); //KEEPME 
		
	
		
		
	}

	private String printIndent() {
		String space = "";
		for(int i=0;i<m_indentLevel;i++){
			space += TAB;
		}
		return space;
	}
	
	private void visitChildren(IJstNode node){
		for(IJstNode child : node.getChildren()){
			child.accept(this);
		}
	}

	@Override
	public void visit(JstGlobalVar node) {
		// TODO Auto-generated method stub
		printNode(node);

	}

	@Override
	public void visit(JstGlobalFunc node) {
		// TODO Auto-generated method stub
		printNode(node);

	}

	@Override
	public void visit(JstGlobalProp node) {
		// TODO Auto-generated method stub
		printNode(node);

	}

	@Override
	public void visit(JstProxyIdentifier node) {
		printNode(node);
	}

	@Override
	public void visit(BaseJsCommentMetaNode<?> node) {
		printNode(node);
	}

}
