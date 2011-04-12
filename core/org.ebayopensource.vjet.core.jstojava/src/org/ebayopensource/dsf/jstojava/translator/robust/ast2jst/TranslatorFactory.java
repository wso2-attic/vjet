/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AND_AND_Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Argument;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ArrayReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Assignment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.BinaryExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Block;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.BreakStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CaseStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CombinedBinaryExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompoundAssignment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ContinueStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.DoStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.DoubleLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.EmptyStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.EqualExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ExtendedStringLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FalseLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FieldReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FloatLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ForInStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ForStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IfStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IntLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IntLiteralMinValue;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LabeledStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ListExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LongLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.LongLiteralMinValue;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.NullLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.OR_OR_Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteralField;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.PostfixExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.PrefixExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.RegExLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ReturnStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SwitchStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ThisReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ThrowStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.TrueLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.TryStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.UnaryExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.UndefinedLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.WhileStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.WithStatement;

public final class TranslatorFactory {
	private static Map<Class<? extends IASTNode>, Class<? extends BaseAst2JstTranslator>> s_translatorMap = new HashMap<Class<? extends IASTNode>, Class<? extends BaseAst2JstTranslator>>();

	private static Map<Class<? extends IASTNode>, Class<? extends BaseAst2JstProblemTranslator>> s_problemTranslatorMap = new HashMap<Class<? extends IASTNode>, Class<? extends BaseAst2JstProblemTranslator>>();

	static {
		s_translatorMap.put(EmptyStatement.class,
				EmptyStatementTranslator.class);

		s_translatorMap.put(ObjectLiteral.class, ObjectLiteralTranslator.class);
		s_translatorMap.put(ObjectLiteralField.class,
				ObjectLiteralFieldTranslator.class);
		s_translatorMap.put(FunctionExpression.class,
				FunctionExpressionTranslator.class);
		s_translatorMap.put(MethodDeclaration.class,
				MethodDeclarationTranslator.class);

		s_translatorMap.put(Argument.class, ArgumentTranslator.class);
		s_translatorMap.put(MessageSend.class, MessageSendTranslator.class);
		s_translatorMap.put(ForStatement.class, ForStatementTranslator.class);
		s_translatorMap.put(LocalDeclaration.class,
				LocalDeclarationTranslator.class);
		s_translatorMap.put(WhileStatement.class,
				WhileStatementTranslator.class);

		s_translatorMap.put(Assignment.class,
				AssignmentExpressionTranslator.class);
		s_translatorMap.put(CompoundAssignment.class,
				AssignmentExpressionTranslator.class);

		s_translatorMap.put(IntLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(IntLiteralMinValue.class, LiteralTranslator.class);
		s_translatorMap.put(LongLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(LongLiteralMinValue.class, LiteralTranslator.class);
		s_translatorMap.put(FloatLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(DoubleLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(StringLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(RegExLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(Literal.class, LiteralTranslator.class);
		s_translatorMap.put(TrueLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(FalseLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(CharLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(NullLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(UndefinedLiteral.class, LiteralTranslator.class);
		s_translatorMap.put(ExtendedStringLiteral.class,
				LiteralTranslator.class);

		s_translatorMap.put(IfStatement.class, IfStatementTranslator.class);
		s_translatorMap.put(TryStatement.class, TryStatementTranslator.class);
		s_translatorMap.put(Block.class, BlockTranslator.class);
		s_translatorMap.put(LabeledStatement.class,
				LabeledStatementTranslator.class);
		s_translatorMap.put(ContinueStatement.class,
				ContinueStatementTranslator.class);
		s_translatorMap.put(BreakStatement.class,
				BreakStatementTranslator.class);
		s_translatorMap.put(PrefixExpression.class,
				PrefixExpressionTranslator.class);
		s_translatorMap.put(PostfixExpression.class,
				PostfixExpressionTranslator.class);
		s_translatorMap.put(SwitchStatement.class,
				SwitchStatementTranslator.class);
		s_translatorMap.put(CaseStatement.class, CaseStatementTranslator.class);
		s_translatorMap.put(ForInStatement.class,
				ForInStatementTranslator.class);
		s_translatorMap.put(DoStatement.class, DoStatementTranslator.class);
		s_translatorMap.put(ReturnStatement.class,
				ReturnStatementTranslator.class);
		s_translatorMap.put(ThrowStatement.class,
				ThrowStatementTranslator.class);
		s_translatorMap.put(SingleNameReference.class,
				SingleNameReferenceTranslator.class);
		s_translatorMap.put(ThisReference.class, ThisReferenceTranslator.class);
		s_translatorMap.put(FieldReference.class,
				FieldReferenceTranslator.class);
		s_translatorMap.put(ArrayReference.class,
				ArrayReferenceTranslator.class);

		s_translatorMap.put(BinaryExpression.class,
				BinaryExpressionTranslator.class);
		s_translatorMap.put(OR_OR_Expression.class,
				BinaryExpressionTranslator.class);
		s_translatorMap.put(AND_AND_Expression.class,
				BinaryExpressionTranslator.class);
		// TODO what to do with combined expression? convert to parathentized
		// expr?
		s_translatorMap.put(CombinedBinaryExpression.class,
				BinaryExpressionTranslator.class);

		s_translatorMap.put(UnaryExpression.class,
				UnaryExpressionTranslator.class);

		s_translatorMap.put(AllocationExpression.class,
				AllocationExpressionTranslator.class);
		s_translatorMap.put(ArrayInitializer.class,
				ArrayInitializerTranslator.class);
		s_translatorMap.put(ConditionalExpression.class,
				ConditionalExpressionTranslator.class);
		s_translatorMap.put(EqualExpression.class,
				BinaryExpressionTranslator.class);
		// "with" statement translator
		s_translatorMap.put(WithStatement.class, WithStatementTranslator.class);
		// problem translators

		// "with" statement translator
		s_translatorMap.put(ListExpression.class, ListExpressionTranslator.class);

		
		s_problemTranslatorMap.put(FieldReference.class,
				ProblemFieldReferenceTranslator.class);
		s_problemTranslatorMap.put(ConditionalExpression.class,
				ProblemConditionalExpressionTranslator.class);
	}

	public static BaseAst2JstTranslator getTranslator(IASTNode node,
			TranslateCtx translateCtx) {
		Class<? extends BaseAst2JstTranslator> translatorClass = s_translatorMap
				.get(node.getClass());

		BaseAst2JstTranslator translator = null;
		if (translatorClass != null) {
			try {
				translator = translatorClass.newInstance();
				translator.setTranslateCtx(translateCtx);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Add translator for node " + node.getClass());
		}

		return translator;
	}

	public static BaseAst2JstProblemTranslator getProblemTranslator(
			IASTNode node, TranslateCtx translateCtx, int problemStart) {
		Class<? extends BaseAst2JstProblemTranslator> translatorClass = s_problemTranslatorMap
				.get(node.getClass());

		BaseAst2JstProblemTranslator translator = null;
		if (translatorClass != null) {
			try {
				translator = translatorClass.newInstance();
				translator.setTranslateCtx(translateCtx);
				translator.setProblemStart(problemStart);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return translator;
	}
}
