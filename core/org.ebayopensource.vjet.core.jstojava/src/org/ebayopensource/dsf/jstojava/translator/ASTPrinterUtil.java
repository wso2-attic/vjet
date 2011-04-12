/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.eclipse.mod.wst.jsdt.core.ast.ASTVisitor;
import org.eclipse.mod.wst.jsdt.core.ast.IAND_AND_Expression;
import org.eclipse.mod.wst.jsdt.core.ast.IAllocationExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IAnnotationFunctionDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.IArgument;
import org.eclipse.mod.wst.jsdt.core.ast.IArrayAllocationExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IArrayInitializer;
import org.eclipse.mod.wst.jsdt.core.ast.IArrayQualifiedTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IArrayReference;
import org.eclipse.mod.wst.jsdt.core.ast.IArrayTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IAssertStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IAssignment;
import org.eclipse.mod.wst.jsdt.core.ast.IBinaryExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IBlock;
import org.eclipse.mod.wst.jsdt.core.ast.IBreakStatement;
import org.eclipse.mod.wst.jsdt.core.ast.ICaseStatement;
import org.eclipse.mod.wst.jsdt.core.ast.ICastExpression;
import org.eclipse.mod.wst.jsdt.core.ast.ICharLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.ICompoundAssignment;
import org.eclipse.mod.wst.jsdt.core.ast.IConditionalExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IConstructorDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.IContinueStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IDoStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IDoubleLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IEmptyStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IEqualExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IExplicitConstructorCall;
import org.eclipse.mod.wst.jsdt.core.ast.IExtendedStringLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IFalseLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IFieldDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.IFieldReference;
import org.eclipse.mod.wst.jsdt.core.ast.IFloatLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IForInStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IForStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IForeachStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.mod.wst.jsdt.core.ast.IFunctionDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.IFunctionExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IIfStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IImportReference;
import org.eclipse.mod.wst.jsdt.core.ast.IInitializer;
import org.eclipse.mod.wst.jsdt.core.ast.IInstanceOfExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IIntLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDoc;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocAllocationExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocArgumentExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocArrayQualifiedTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocArraySingleTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocFieldReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocImplicitTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocMessageSend;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocQualifiedTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocReturnStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocSingleNameReference;
import org.eclipse.mod.wst.jsdt.core.ast.IJsDocSingleTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.ILabeledStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IListExpression;
import org.eclipse.mod.wst.jsdt.core.ast.ILocalDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.ILongLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IMarkerAnnotation;
import org.eclipse.mod.wst.jsdt.core.ast.IMemberValuePair;
import org.eclipse.mod.wst.jsdt.core.ast.INormalAnnotation;
import org.eclipse.mod.wst.jsdt.core.ast.INullLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IOR_OR_Expression;
import org.eclipse.mod.wst.jsdt.core.ast.IObjectLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IParameterizedQualifiedTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IParameterizedSingleTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IPostfixExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IPrefixExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IQualifiedAllocationExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IQualifiedNameReference;
import org.eclipse.mod.wst.jsdt.core.ast.IQualifiedSuperReference;
import org.eclipse.mod.wst.jsdt.core.ast.IQualifiedThisReference;
import org.eclipse.mod.wst.jsdt.core.ast.IQualifiedTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IRegExLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IReturnStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IScriptFileDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.ISingleMemberAnnotation;
import org.eclipse.mod.wst.jsdt.core.ast.ISingleNameReference;
import org.eclipse.mod.wst.jsdt.core.ast.ISingleTypeReference;
import org.eclipse.mod.wst.jsdt.core.ast.IStringLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IStringLiteralConcatenation;
import org.eclipse.mod.wst.jsdt.core.ast.ISuperReference;
import org.eclipse.mod.wst.jsdt.core.ast.ISwitchStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IThisReference;
import org.eclipse.mod.wst.jsdt.core.ast.IThrowStatement;
import org.eclipse.mod.wst.jsdt.core.ast.ITrueLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.ITryStatement;
import org.eclipse.mod.wst.jsdt.core.ast.ITypeDeclaration;
import org.eclipse.mod.wst.jsdt.core.ast.ITypeParameter;
import org.eclipse.mod.wst.jsdt.core.ast.IUnaryExpression;
import org.eclipse.mod.wst.jsdt.core.ast.IUndefinedLiteral;
import org.eclipse.mod.wst.jsdt.core.ast.IWhileStatement;
import org.eclipse.mod.wst.jsdt.core.ast.IWildcard;
import org.eclipse.mod.wst.jsdt.core.ast.IWithStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

public class ASTPrinterUtil {

	class PrintVisitor extends ASTVisitor {
		public boolean visit(IAllocationExpression allocationExpression) {
			emit(allocationExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IAND_AND_Expression and_and_Expression) {
			emit(and_and_Expression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(
				IAnnotationFunctionDeclaration annotationTypeDeclaration) {
			emit(annotationTypeDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IArgument argument) {
			emit(argument);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(
				IArrayAllocationExpression arrayAllocationExpression) {
			emit(arrayAllocationExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IArrayInitializer arrayInitializer) {
			emit(arrayInitializer);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(
				IArrayQualifiedTypeReference arrayQualifiedTypeReference) {
			emit(arrayQualifiedTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IArrayReference arrayReference) {
			emit(arrayReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IArrayTypeReference arrayTypeReference) {
			emit(arrayTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IAssertStatement assertStatement) {
			emit(assertStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IAssignment assignment) {
			emit(assignment);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IBinaryExpression binaryExpression) {
			emit(binaryExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IBlock block) {

			emit(block);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IBreakStatement breakStatement) {
			emit(breakStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ICaseStatement caseStatement) {
			emit(caseStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ICastExpression castExpression) {
			emit(castExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ICharLiteral charLiteral) {
			emit(charLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IScriptFileDeclaration compilationUnitDeclaration) {
			emit(compilationUnitDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ICompoundAssignment compoundAssignment) {
			emit(compoundAssignment);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IConditionalExpression conditionalExpression) {
			emit(conditionalExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IConstructorDeclaration constructorDeclaration) {
			emit(constructorDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IContinueStatement continueStatement) {
			emit(continueStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IDoStatement doStatement) {
			emit(doStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IDoubleLiteral doubleLiteral) {
			emit(doubleLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IEmptyStatement emptyStatement) {
			emit(emptyStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IEqualExpression equalExpression) {
			emit(equalExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IExplicitConstructorCall explicitConstructor) {
			emit(explicitConstructor);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IExtendedStringLiteral extendedStringLiteral) {
			emit(extendedStringLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IFalseLiteral falseLiteral) {
			emit(falseLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IFieldDeclaration fieldDeclaration) {
			emit(fieldDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IFieldReference fieldReference) {
			emit(fieldReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IFloatLiteral floatLiteral) {
			emit(floatLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IForeachStatement forStatement) {
			emit(forStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IForInStatement forInStatement) {
			emit(forInStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IForStatement forStatement) {
			emit(forStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IFunctionExpression functionExpression) {
			emit(functionExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IIfStatement ifStatement) {
			emit(ifStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IImportReference importRef) {
			emit(importRef);
			return true; // do nothing by default, keep traversing
		}

		// public boolean visit(InferredType inferredType) {
		// return true; // do nothing by default, keep traversing
		// }
		//
		// public boolean visit(InferredMethod inferredMethod) {
		// return true; // do nothing by default, keep traversing
		// }
		//
		// public boolean visit(InferredAttribute inferredField) {
		// return true; // do nothing by default, keep traversing
		// }
		public boolean visit(IInitializer initializer) {
			emit(initializer);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IInstanceOfExpression instanceOfExpression) {
			emit(instanceOfExpression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IIntLiteral intLiteral) {
			emit(intLiteral);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDoc javadoc) {
			emit(javadoc);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocAllocationExpression expression) {
			emit(expression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocArgumentExpression expression) {
			emit(expression);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocArrayQualifiedTypeReference typeRef) {
			emit(typeRef);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IObjectLiteral literal) {
			emit(literal);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocArraySingleTypeReference typeRef) {
			emit(typeRef);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocFieldReference fieldRef) {
			emit(fieldRef);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocImplicitTypeReference implicitTypeReference) {
			emit(implicitTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocMessageSend messageSend) {
			emit(messageSend);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocQualifiedTypeReference typeRef) {
			emit(typeRef);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocReturnStatement statement) {
			emit(statement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocSingleNameReference argument) {
			emit(argument);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IJsDocSingleTypeReference typeRef) {
			emit(typeRef);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ILabeledStatement labeledStatement) {
			emit(labeledStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ILocalDeclaration localDeclaration) {
			emit(localDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IListExpression listDeclaration) {
			emit(listDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ILongLiteral longLiteral) {
			emit(longLiteral);
			return true; // do nothing by default, keep traversing
		}
		
		
		public boolean visit(IMarkerAnnotation annotation) {
			emit(annotation);
			return true;
		}
		/**
		 * @param pair
		 * @param scope
		 * @since 3.1
		 */
		public boolean visit(IMemberValuePair pair) {
			emit(pair);
			return true;
		}
		public boolean visit(IFunctionCall functionCall) {
			emit(functionCall);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IFunctionDeclaration functionDeclaration) {
			emit(functionDeclaration);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IStringLiteralConcatenation literal) {
			emit(literal);
			return true; // do nothing by default, keep traversing
		}
		/**
		 * @param annotation
		 * @param scope
		 * @since 3.1
		 */
		public boolean visit(INormalAnnotation annotation) {
			emit(annotation);
			return true;
		}
		public boolean visit(INullLiteral nullLiteral) {
			emit(nullLiteral);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IOR_OR_Expression or_or_Expression) {
			emit(or_or_Expression);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference) {
			emit(parameterizedQualifiedTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IParameterizedSingleTypeReference parameterizedSingleTypeReference) {
			emit(parameterizedSingleTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IPostfixExpression postfixExpression) {
			emit(postfixExpression);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IPrefixExpression prefixExpression) {
			emit(prefixExpression);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IQualifiedAllocationExpression qualifiedAllocationExpression) {
			emit(qualifiedAllocationExpression);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IQualifiedNameReference qualifiedNameReference) {
			emit(qualifiedNameReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IQualifiedSuperReference qualifiedSuperReference) {
			emit(qualifiedSuperReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IQualifiedThisReference qualifiedThisReference) {
			emit(qualifiedThisReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IQualifiedTypeReference qualifiedTypeReference) {
			emit(qualifiedTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IRegExLiteral stringLiteral) {
			emit(stringLiteral);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IReturnStatement returnStatement) {
			emit(returnStatement);
			return true; // do nothing by default, keep traversing
		}
		/**
		 * @param annotation
		 * @param scope
		 * @since 3.1
		 */
		public boolean visit(ISingleMemberAnnotation annotation) {
			emit(annotation);
			return true;
		}
		public boolean visit(ISingleNameReference singleNameReference) {
			emit(singleNameReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ISingleTypeReference singleTypeReference) {
			emit(singleTypeReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IStringLiteral stringLiteral) {
			emit(stringLiteral);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(ISuperReference superReference) {
			emit(superReference);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(ISwitchStatement switchStatement) {
			emit(switchStatement);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IThisReference thisReference) {
			emit(thisReference);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IThrowStatement throwStatement) {
			emit(throwStatement);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(ITrueLiteral trueLiteral) {
			emit(trueLiteral);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(ITryStatement tryStatement) {
			emit(tryStatement);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(ITypeDeclaration localTypeDeclaration) {
			emit(localTypeDeclaration);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(ITypeParameter typeParameter) {
			emit(typeParameter);
			return true; // do nothing by default, keep traversing
		}

		public boolean visit(IUnaryExpression unaryExpression) {
			emit(unaryExpression);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IUndefinedLiteral undefined) {
			emit(undefined);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IWhileStatement whileStatement) {
			emit(whileStatement);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IWithStatement whileStatement) {
			emit(whileStatement);
			return true; // do nothing by default, keep traversing
		}
		public boolean visit(IWildcard wildcard) {
			emit(wildcard);
			return true; // do nothing by default, keep traversing
		}


	}

	private void emit(Object node) {

		
		System.out.println("--------------------------------");
		System.out.println(node.getClass());
		if (node instanceof ASTNode) {
			final ASTNode node2 = ((ASTNode) node);
			
			System.out.println(node2.toString());
			System.out.println("start:"+ node2.sourceStart);
			System.out.println("end:"+ node2.sourceEnd);
			
			
		}
		if(node instanceof MessageSend){
			MessageSend ms = (MessageSend)node;
			System.out.println(ms.selector);
			
		}
		System.out.println("--------------------------------");
	}

}
