/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;

public class LiteralTranslator extends BaseTranslator {
		
	//
	// Package protected
	//
	SimpleLiteral toJstLiteral(final NumberLiteral astExpr, final BaseJstNode jstNode){

		// TODO
		String literal = astExpr.toString();
		String typeName = "";
		if ((literal.endsWith("f") || literal.endsWith("F")) && (!literal.startsWith("0x") && !literal.startsWith("0X"))){
			typeName = "float";
		}
		else if ((literal.endsWith("d") || literal.endsWith("D")) && (!literal.startsWith("0x") && !literal.startsWith("0X"))){
			typeName = "double";
		}
		else if (literal.endsWith("l") || literal.endsWith("L")){
			typeName = "long";
		}
		else if (literal.contains(".")){
			String actType = getTypeName((NumberLiteral)astExpr);
			if(actType != null){
				typeName = actType;
			}else{
				typeName = "float";
			}
		}
		else {
		
//			IJstType type = null;
//			if(jstNode instanceof JstProperty){
//				type = ((JstProperty)jstNode).getType();
//			}
//			
//			if(type!=null && type.getName().contains(Integer.class.getSimpleName())){
//				typeName= "Integer";
//			}else{
				typeName = "int";
//			}
			
			
		}
		ASTNode ast = astExpr.getParent();
		VarTable varTable = null;
		
		if (ast instanceof VariableDeclarationFragment) {
			VariableDeclarationFragment o = (VariableDeclarationFragment)ast;
			SimpleName n = o.getName();
			if (jstNode instanceof JstType) {
				JstType jstType = (JstType)jstNode;
				varTable = jstType.getVarTable();
			}
			if (jstNode instanceof JstBlock) {
				JstBlock jstBlock = (JstBlock)jstNode;
				varTable = jstBlock.getVarTable();
			}
			
			String name = getNameTranslator().processVarName(n,jstNode);
			if (varTable != null){					
				IJstType type = varTable.getVarType(name);
				//typeName = type.getSimpleName();
				
				//See bug #4728 vjo.test.junit.defects.tc6.ClassB
				if(!type.getSimpleName().equals("Object"))
				    typeName = type.getSimpleName();
			}				
		}
		
		if (typeName.equals("int")){
			return SimpleLiteral.getIntegerLiteral(literal);
		}
		
		if(typeName.equals("Integer")){
			return SimpleLiteral.getIntegerLiteral(literal);
		}
	
		
		if(typeName.equals("Long")||typeName.equals("long")){
			return SimpleLiteral.getLongLiteral(literal);
		}
		
		if (typeName.equals("short") || typeName.equals("Short")) {
			return SimpleLiteral.getShortLiteral(literal);
		}
		if (typeName.equals("byte") || typeName.equals("Byte")) {
			return SimpleLiteral.getByteLiteral(literal);
		}
		if (typeName.equals("float")|| typeName.equals("Float")) {
			return SimpleLiteral.getFloatLiteral(literal);
		}						
		return SimpleLiteral.getDoubleLiteral(literal);
	}
	
	SimpleLiteral toJstLiteral(final CharacterLiteral literal){
		String esval = literal.getEscapedValue();
		esval = esval.substring(1, esval.length()-1);
		return SimpleLiteral.getCharLiteral(esval);
	}
	
	SimpleLiteral toJstLiteral(final BooleanLiteral literal){
		return SimpleLiteral.getBooleanLiteral( literal.booleanValue());
	}
	
	SimpleLiteral toJstLiteral(final StringLiteral literal){
		String s = literal.toString();
		return SimpleLiteral.getStringLiteral(s.substring(1, s.length()-1));
		//return new SimpleLiteral(String.class, JstReservedTypes.JsNative.STRING, s.substring(1, s.length()-1));
	}
	
	SimpleLiteral toJstLiteral(final TypeLiteral literal){
		String s = literal.toString();
		return new SimpleLiteral(Object.class, JstReservedTypes.JsNative.OBJECT, s);
	}
	
	SimpleLiteral toJstLiteral(final NullLiteral literal){
		return new SimpleLiteral(Object.class, JstReservedTypes.JsNative.OBJECT, null);
	}
	
	private String getTypeName(NumberLiteral astExpr){
		ASTNode parent = astExpr.getParent();
		if(parent instanceof MethodInvocation && "equals".equals(((MethodInvocation)parent).getName().toString())){
			Expression expr = ((MethodInvocation)parent).getExpression();
			if(expr instanceof ClassInstanceCreation){
				Object type = ((ClassInstanceCreation)expr).getType();
				if(type!=null){
					if("Double".equals(type.toString())){
						return "double";
					}else if("Float".equals(type.toString())){
						return "float";
					}else if("Long".equals(type.toString())){
						return "long";
					}
				}
			}
		}
		return null;
	}
}