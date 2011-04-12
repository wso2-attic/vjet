/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.core.ast.IExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;
import org.ebayopensource.vjo.meta.VjoKeywords;

/**
 * 
 * 
 */
public class JstUtil {

	private static final String SPACE = "";

	private static final String QUOTE = "\'";

	private static final String DOUBLE_QUOTE = "\"";

	private static final String EMPTY = SPACE;
	
	private static boolean s_debug = false;
	
	/**
	 * Returns a the most leaf node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @return BaseJstNode a node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 * or null if no such node is found.
	 */
	public static BaseJstNode getLeafNode(IJstType type, int startOffset, int endOffset) {
		return getLeafNode(type, startOffset, endOffset, false);
	}
	
	/**
	 * Returns a the most leaf node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @param visitOverlodedNodes boolean to visit overloaded method nodes.
	 * @param countIdentifierEndOffset check if the an node should be counted in if the end offset is after the JstIdentifier (the cursor is at the end of Identifer) 
	 * @return BaseJstNode a node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 * or null if no such node is found.
	 */
	public static BaseJstNode getLeafNode(IJstType type, int startOffset, int endOffset, boolean visitOverlodedNodes) {
		return getLeafNode(type, startOffset, endOffset, visitOverlodedNodes, false);
	}
	/**
	 * Returns a the most leaf node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @param visitOverlodedNodes boolean to visit overloaded method nodes.
	 * @param countIdentifierEndOffset check if the an node should be counted in if the end offset is after the JstIdentifier (the cursor is at the end of Identifer) 
	 * @return BaseJstNode a node in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 * or null if no such node is found.
	 */
	public static BaseJstNode getLeafNode(IJstType type, int startOffset, int endOffset, boolean visitOverlodedNodes, boolean countIdentifierEndOffset) {
		if (type == null) {
			return null;
		}
		debug("JstUtil: looking for  " + type.getName() + ", startOffset=" + startOffset + " , endOffset=" + endOffset);
		BaseJstNode node = null;
		List<BaseJstNode> nodeList = getAllNodes(type, startOffset, endOffset, visitOverlodedNodes, countIdentifierEndOffset);
		if (!nodeList.isEmpty()) {
			for (BaseJstNode n : nodeList){
				if (node == null) {
					node = n;
				} else {
					JstSource source = n.getSource();
					int range = source.getEndOffSet() - source.getStartOffSet();
					JstSource curSrc = node.getSource();
					int curRange = curSrc.getEndOffSet() - curSrc.getStartOffSet();
					if (range <= curRange) {
						node = n;
					}
				}
			}
			debug("JstUtil: found " + node.getClass().getName());
			print(node);
		}
		return node;
	}

	/**
	 * Returns all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @return List of all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 */
	public static List<BaseJstNode> getAllNodes(IJstType type, int startOffset, int endOffset) {
		return getAllNodes(type, startOffset, endOffset, false);
	}
	
	/**
	 * Returns all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @param visitOverlodedNodes boolean to visit overloaded method nodes.
	 * @return List of all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 */
	public static List<BaseJstNode> getAllNodes(IJstType type, int startOffset, int endOffset, boolean visitOverlodedNodes) {
		return getAllNodes(type, startOffset, endOffset, visitOverlodedNodes, false);
	}
	/**
	 * Returns all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset
	 * @param type IJstType
	 * @param startOffset int source start offset
	 * @param endOffset	int source end offset
	 * @param visitOverlodedNodes boolean to visit overloaded method nodes.
	 * @param countIdentifierEndOffset check if the an node should be counted in if the end offset is after the JstIdentifier (the cursor is at the end of Identifer)
	 * @return List of all nodes in JstType tree with source binding that matches the following:<br>
	 * sourceStart <= startOffset && sourceEnd >= endOffset<br>
	 */
	public static List<BaseJstNode> getAllNodes(IJstType type, int startOffset, int endOffset, boolean visitOverlodedNodes, boolean countIdentifierEndOffset) {
		if (type == null) {
			return Collections.EMPTY_LIST;
		}
		JstUtilVisitor visitor = new JstUtilVisitor(startOffset, endOffset, visitOverlodedNodes, countIdentifierEndOffset);
		type.accept(visitor);
		return visitor.getFoundNodes();
	}

	@Deprecated 
	public static Object getNode(IJstType type, int startOffset, int endOffset) {

		if (includes(type.getSource(), startOffset, endOffset)) {
			// this type name is selected
			return type;
		}

		Object node = null;
		if ((node = processImports(type, startOffset, endOffset)) != null) {
			return node;
		}

		if ((node = processExtends(type, startOffset, endOffset)) != null) {
			return node;
		}

		if ((node = processImplements(type, startOffset, endOffset)) != null) {
			return node;
		}

		if ((node = processConstructor(type, startOffset, endOffset)) != null) {
			return node;
		}

		// fields declaration and type
		if ((node = processFields(type, startOffset, endOffset)) != null) {
			return node;
		}

		// methods
		if ((node = processMethods(type, startOffset, endOffset)) != null) {
			return node;
		}

		return node;
	}

	private static Object processFields(IJstType type, int startOffset,
			int endOffset) {
		Collection<IJstProperty> fields = type.getInstanceProperties();
		Object field = processFields(fields, startOffset, endOffset);
		if (field != null) {
			return field;
		}
		fields = type.getStaticProperties();
		return processFields(fields, startOffset, endOffset);
	}

	private static Object processFields(Collection<IJstProperty> fields,
			int startOffset, int endOffset) {
		for (IJstProperty field : fields) {
			// declaration
			if (includes(field.getName().getSource(), startOffset, endOffset)) {
				return field;
			}
			IJstTypeReference type = field.getTypeRef();
			if (type != null) {
				if (includes(type.getSource(), startOffset, endOffset)) {
					return type;
				}
			}
			if (field.getInitializer() != null) {
				IExpr expr = field.getInitializer();
				return processExpression(expr, startOffset, endOffset);
			}
		}
		return null;
	}

	private static Object processStatements(JstBlock block, int startOffset,
			int endOffset) {
		if (block != null) {
			List<IStmt> statements = block.getStmts();
			for (IStmt statement : statements) {
				Object node = processStatement(statement, startOffset,
						endOffset);
				if (node != null) {
					return node;
				}
			}
		}
		return null;
	}

	private static Object processStatement(IStmt statement, int startOffset,
			int endOffset) {
		if (statement instanceof AssignExpr) {
			return processAssignExpr((AssignExpr) statement, startOffset,
					endOffset);
		} else if (statement instanceof ForStmt) {
			ForStmt forStatement = (ForStmt) statement;
			Object node;

			// initializers first
			IInitializer initializer = forStatement.getInitializers();
			if (initializer instanceof JstVars) {
				node = processStatement((JstVars) initializer, startOffset,
						endOffset);
				if (node != null) {
					return node;
				}
			} else if (initializer != null) {
				List<AssignExpr> assignments = initializer.getAssignments();
				// TODO process assignments
			}

			// condition next
			node = processExpression(forStatement.getCondition(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}

			// updaters
			List<IExpr> updaters = forStatement.getUpdaters();
			for (IExpr updater : updaters) {
				node = processExpression(updater, startOffset, endOffset);
				if (node != null) {
					return node;
				}
			}
			// block last
			return processStatements(forStatement.getBody(), startOffset,
					endOffset);
		} else if (statement instanceof ForInStmt) {
			ForInStmt forInStmt = (ForInStmt) statement;
			Object node;

			// initializers first
			ILHS var = forInStmt.getVar();
			if (var instanceof IStmt) {
				node = processStatement((IStmt) var, startOffset, endOffset);
				if (node != null) {
					return node;
				}
			}
			// Expr
			IExpr expr = forInStmt.getExpr();
			node = processExpression(expr, startOffset, endOffset);
			if (node != null) {
				return node;
			}

			// block last
			return processStatements(forInStmt.getBody(), startOffset,
					endOffset);
		} else if (statement instanceof WhileStmt) {
			WhileStmt whileStmt = (WhileStmt) statement;
			// condition first
			Object node = processExpression(whileStmt.getCondition(),
					startOffset, endOffset);
			if (node != null) {
				return node;
			}
			// body next
			return processStatements(whileStmt.getBody(), startOffset,
					endOffset);
		} else if (statement instanceof TryStmt) {
			TryStmt tryStmt = (TryStmt) statement;
			// try block
			Object node = processStatements(tryStmt.getBody(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}
			// catch statements
			JstBlock catchBlock = tryStmt.getCatchBlock(false);
			if (catchBlock != null) {
				for (IStmt catchStmt : catchBlock.getStmts()) {
					node = processStatement(catchStmt, startOffset, endOffset);
					if (node != null) {
						return node;
					}
				}
			}
			// finally block
			return processStatements(tryStmt.getFinallyBlock(false),
					startOffset, endOffset);
		} else if (statement instanceof CatchStmt) {
			CatchStmt catchStmt = (CatchStmt) statement;
			JstVar exception = catchStmt.getException();
			if (includes(exception.getSource(), startOffset, endOffset)) {
				return exception;
			}
			return processStatements(catchStmt.getBody(), startOffset,
					endOffset);
		} else if (statement instanceof SwitchStmt) {
			SwitchStmt switchStmt = (SwitchStmt) statement;
			Object node = processExpression(switchStmt.getExpr(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}

			return processStatements(switchStmt.getBody(), startOffset,
					endOffset);
		} else if (statement instanceof CaseStmt) {
			return processExpression(((CaseStmt) statement).getExpr(),
					startOffset, endOffset);
		} else if (statement instanceof IfStmt) {
			IfStmt ifStmt = (IfStmt) statement;
			// condition
			Object node = processExpression(ifStmt.getCondition(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}
			// if block
			node = processStatements(ifStmt.getBody(), startOffset, endOffset);
			if (node != null) {
				return node;
			}
			// else-if statements
			if (ifStmt.getElseIfBlock(false) != null) {
				for (IStmt elseIfStmt : ifStmt.getElseIfBlock(false).getStmts()) {
					node = processStatement(elseIfStmt, startOffset, endOffset);
					if (node != null) {
						return node;
					}
				}
			}
			// else block
			return processStatements(ifStmt.getElseBlock(false), startOffset,
					endOffset);
		} else if (statement instanceof ThrowStmt) {
			ThrowStmt throwStmt = (ThrowStmt) statement;
			return processExpression(throwStmt.getExpression(), startOffset,
					endOffset);
		} else if (statement instanceof RtnStmt) {
			RtnStmt returnStmt = (RtnStmt) statement;
			return processExpression(returnStmt.getExpression(), startOffset,
					endOffset);
		} else if (statement instanceof MtdInvocationExpr) {
			return processExpression((IExpr) statement, startOffset, endOffset);
		} else if (statement instanceof JstVars) {
			IJstTypeReference type = ((JstVars) statement).getTypeRef();
			if (type != null
					&& includes(type.getSource(), startOffset, endOffset)) {
				return type;
			}
			List<AssignExpr> initializers = ((JstVars) statement)
					.getAssignments();
			if (initializers != null && initializers.size() > 0) {
				for (AssignExpr jstInitializer : initializers) {
					Object node = processAssignExpr(jstInitializer,
							startOffset, endOffset);
					if (node != null) {
						return node;
					}
				}
			}
		}
		return null;
	}

	private static Object processExpression(IExpr expression, int startOffset,
			int endOffset) {
		if (expression == null) {
			return null;
		}
		if (expression instanceof JstIdentifier) {
			if (includes(((JstIdentifier) expression).getSource(), startOffset,
					endOffset)) {
				return expression;
			}
		} else if (expression instanceof MtdInvocationExpr) {
			MtdInvocationExpr methodInvocation = (MtdInvocationExpr) expression;
			// process method name part
			if (processExpression(methodInvocation.getMethodIdentifier(),
					startOffset, endOffset) != null) {
				return methodInvocation;
			}
			List<IExpr> args = methodInvocation.getArgs();
			for (IExpr arg : args) {
				Object result = processExpression(arg, startOffset, endOffset);
				if (result != null) {
					return result;
				}
			}
			// process qualifier expression
			return processExpression(methodInvocation.getQualifyExpr(),
					startOffset, endOffset);
		} else if (expression instanceof BoolExpr) {
			BoolExpr boolExpr = (BoolExpr) expression;
			Object node = processExpression(boolExpr.getLeft(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}
			return processExpression(boolExpr.getRight(), startOffset,
					endOffset);
		} else if (expression instanceof PostfixExpr) {
			return processExpression(
					((PostfixExpr) expression).getIdentifier(), startOffset,
					endOffset);
		} else if (expression instanceof InfixExpr) {
			InfixExpr infixExpr = (InfixExpr) expression;
			Object node = processExpression(infixExpr.getLeft(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}
			return processExpression(infixExpr.getRight(), startOffset,
					endOffset);
		} else if (expression instanceof ParenthesizedExpr) {
			return processExpression(((ParenthesizedExpr) expression)
					.getExpression(), startOffset, endOffset);
		} else if (expression instanceof PrefixExpr) {
			return processExpression(((PrefixExpr) expression).getIdentifier(),
					startOffset, endOffset);
		} else if (expression instanceof FieldAccessExpr) {
			FieldAccessExpr fieldExpr = (FieldAccessExpr) expression;
			// process name part
			if (processExpression(fieldExpr.getName(), startOffset, endOffset) != null) {
				return fieldExpr;
			}

			// rpocess qualifier expression
			return processExpression(fieldExpr.getExpr(), startOffset,
					endOffset);
			// } else if (expression instanceof JstInitializer) {
			// // lhs TODO
			// Object node = processExpression(
			// (IExpr) ((JstInitializer) expression).getLHS(),
			// startOffset, endOffset);
			// if (node != null) {
			// return node;
			// }
			// // rhs
			// return processExpression(((JstInitializer) expression)
			// .getExpression(), startOffset, endOffset);
		} else if (expression instanceof ArrayAccessExpr) {
			ArrayAccessExpr arrayExpr = (ArrayAccessExpr) expression;
			Object node = processExpression(arrayExpr.getIndex(), startOffset,
					endOffset);
			if (node != null) {
				return node;
			}
			return processExpression(arrayExpr.getExpr(), startOffset,
					endOffset);
		} else if (expression instanceof ObjCreationExpr) {
			ObjCreationExpr expr = (ObjCreationExpr) expression;
			int end = expr.getSource().getEndOffSet();
			Object node = processExpression(expr.getInvocationExpr(),
					startOffset, endOffset);
			if (node == null
					&& includes(expr.getSource(), startOffset, endOffset)) {
				return expr;
			}
			return node;
		}

		return null;
	}

	private static Object processAssignExpr(AssignExpr assignExpr,
			int startOffset, int endOffset) {
		ILHS lhs = assignExpr.getLHS();
		if (lhs instanceof IExpr) {
			Object node = processExpression((IExpr) lhs, startOffset, endOffset);
			if (node != null) {
				return node;
			}
		} else if (lhs instanceof JstVar) {
			if (includes(((JstVar) lhs).getSource(), startOffset, endOffset)) {
				return lhs;
			}
		}

		return processExpression(assignExpr.getExpr(), startOffset, endOffset);
	}

	private static Object processConstructor(IJstType type, int startOffset,
			int endOffset) {
		IJstMethod constructor = type.getConstructor();
		if (constructor != null && !(constructor instanceof ISynthesized)) {
			return processMethod(constructor, startOffset, endOffset);
		}
		return null;
	}

	private static IJstTypeReference processImports(IJstType type,
			int startOffset, int endOffset) {
		List<? extends IJstTypeReference> importsMap = type.getImportsRef();
		for (IJstTypeReference importedType : importsMap) {
			JstSource source = importedType.getSource();
			if (includes(source, startOffset, endOffset)) {
				return importedType;
			}
		}
		return null;
	}

	private static IJstTypeReference processExtends(IJstType type,
			int startOffset, int endOffset) {
		IJstTypeReference extendedType = type.getExtendRef();
		if (extendedType != null) {
			JstSource source = extendedType.getSource();
			if (includes(source, startOffset, endOffset)) {
				return extendedType;
			}
		}
		return null;
	}

	private static IJstTypeReference processImplements(IJstType type,
			int startOffset, int endOffset) {
		List<? extends IJstTypeReference> implementedTypes = type
				.getSatisfiesRef();
		return processTypes(implementedTypes, startOffset, endOffset);
	}

	private static IJstTypeReference processTypes(
			List<? extends IJstTypeReference> types, int startOffset,
			int endOffset) {
		for (IJstTypeReference type : types) {
			JstSource source = type.getSource();
			if (includes(source, startOffset, endOffset)) {
				return type;
			}
		}
		return null;
	}

	public static boolean includes(JstSource elementSource, int selectionStart,
			int selectionEnd) {
		if (elementSource != null) {
			return includes(elementSource.getStartOffSet(), elementSource
					.getEndOffSet(), selectionStart, selectionEnd);
		} else {
			return false;
		}
	}

	private static Object processMethods(IJstType type, int startOffset,
			int endOffset) {
		// instance methods
		List<? extends IJstMethod> methods = type.getInstanceMethods();
		Object node = processMethods(methods, startOffset, endOffset);
		if (node != null) {
			return node;
		}
		// static methods
		methods = type.getStaticMethods();
		return processMethods(methods, startOffset, endOffset);
	}

	private static Object processMethods(List<? extends IJstMethod> methods,
			int startOffset, int endOffset) {
		for (IJstMethod method : methods) {
			Object node = processMethod(method, startOffset, endOffset);
			if (node != null) {
				return node;
			}
		}
		return null;
	}

	private static Object processMethod(IJstMethod method, int startOffset,
			int endOffset) {
		// declaration
		if (includes(method.getName().getSource(), startOffset, endOffset)) {
			return method;
		}

		// method's return type
		IJstTypeReference retType = method.getRtnTypeRef();
		if (retType != null
				&& includes(retType.getSource(), startOffset, endOffset)) {
			return retType;
		}
		// one of method's param types
		List<JstArg> params = method.getArgs();
		for (JstArg param : params) {
			JstSource paramSource = param.getSource();
			if (includes(paramSource, startOffset, endOffset)) {
				return param;
			}
			IJstTypeReference paramType = param.getTypeRef();
			if (includes(paramType.getSource(), startOffset, endOffset)) {
				return paramType;
			}
		}

		Object node = processStatements(method.getBlock(), startOffset,
				endOffset);

		if (node != null) {
			return node;
		}

		// fields, variables and method call references inside method body
		return processChildren(method.getBlock(), startOffset, endOffset);
	}

	private static Object processChildren(JstBlock block, int startOffset,
			int endOffset) {
		if (block != null) {
			List<BaseJstNode> statements = block.getChildren();
			for (BaseJstNode statement : statements) {
				Object node = null;
				if (statement instanceof IExpr) {
					IExpr expr = (IExpr) statement;
					node = processExpression(expr, startOffset, endOffset);
				}

				if (node != null) {
					return node;
				}
			}
		}
		return null;
	}

	private static boolean includes(int elementStart, int elementEnd,
			int selectionStart, int selectionEnd) {
		return elementStart <= selectionStart && elementEnd >= selectionEnd;
	}

	private static boolean intersects(int range1Start, int range1End,
			int range2Start, int range2End) {
		return range1Start <= range2Start && range1End > range2Start
				|| range1Start < range2End && range1End >= range2End;
	}

	public static boolean include(JstSource source, int position) {
		return source.getStartOffSet() <= position
				&& source.getEndOffSet() >= position;
	}

	public static IJstMethod getMethod(int position,
			Collection<? extends IJstMethod> ms) {
		IJstMethod jstMethod = null;

		for (IJstMethod method : ms) {

			if (include(method.getSource(), position)) {
				jstMethod = method;
			}
		}

		return jstMethod;
	}

	public static IJstMethod getMethod(int position, IJstType jstType) {

		Collection<? extends IJstMethod> collection;
		collection = jstType.getInstanceMethods();
		IJstMethod jstMethod = getMethod(position, collection);

		if (jstMethod == null) {
			collection = jstType.getStaticMethods();
			jstMethod = getMethod(position, collection);
		}

		return jstMethod;
	}

	public static JstType findType(String name) {
		JstType jst = JstCache.getInstance().getType(name);
		if (jst == null) {
			jst = JstFactory.getInstance().createJstType(name, true);
		}
		return jst;
	}

	public static String getCorrectName(Literal literal) {
		String s = String.valueOf(literal.source());
		s = getCorrectName(s);
		return s;
	}

	public static String getCorrectName(String s) {
		s = s.replace(DOUBLE_QUOTE, EMPTY).replace(QUOTE, EMPTY);
		return s;
	}

	public static boolean isType(String method) {
		return VjoKeywords.TYPE.equals(method)
//				|| VjoKeywords.ATYPE.equals(method)
				|| VjoKeywords.ITYPE.equals(method)
				|| VjoKeywords.LTYPE.equals(method)
				|| VjoKeywords.CTYPE.equals(method)
				|| VjoKeywords.ETYPE.equals(method)
				|| VjoKeywords.OTYPE.equals(method)
				|| VjoKeywords.MTYPE.equals(method)
				|| VjoKeywords.FTYPE.equals(method);
	}

	public static boolean isVjo(String name) {
		return VjoKeywords.VJO.equals(name);
	}

	public static boolean isInnerType(Stack<IASTNode> list) {
		boolean isInnerType = false;
		if (list.size() > 2) {
			isInnerType = VjoKeywords.VJO.equals(getName(list.get(0)))
					&& isType(getName(list.get(1)));
		}
		return isInnerType;
	}
	
	public static String getInnerTypeName(Stack<IASTNode> list) {
		if (list.size() > 2) {
			//if (VjoKeywords.VJO.equals(getName(list.get(0))) && isType(getName(list.get(1))))
			IASTNode node = list.get(1);
			if (node instanceof MessageSend) {
				MessageSend send = (MessageSend)node;
				Expression[] args = send.arguments;
				if (args != null && args.length == 1) {
					Expression arg = args[0];
					if (arg instanceof StringLiteral || arg instanceof CharLiteral) {
						return JstUtil.getCorrectName((Literal)arg);
					}
				}
			}
		}
		return null;
	}

	public static String getName(IASTNode node) {
		String s = SPACE;

		if (node instanceof MessageSend) {
			MessageSend send = (MessageSend) node;
			s = String.valueOf(send.selector);
		}

		if (node instanceof SingleNameReference) {
			SingleNameReference reference = (SingleNameReference) node;
			s = String.valueOf(reference.token);
		}

		return s;
	}

	public static Stack<IASTNode> createMessageSendStack(IExpression expr) {

		Stack<IASTNode> stack = new Stack<IASTNode>();

		IASTNode node = expr;

		while (true) {

			stack.add(0, node);

			if (node instanceof MessageSend) {
				MessageSend send = (MessageSend) node;
				node = send.receiver;
			} else {
				break;
			}
		}

		return stack;
	}

	public static BaseJstNode findScope(IJstType type, int startOffset,
			int endOffset) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static void debug(String msg) {
		if (s_debug ) {
			System.out.println(msg);
		}
	}
	
	private static void print(Object node) {
		if (s_debug && node instanceof IJstNode) {
			IJstNode  jstNode = (IJstNode) node;
			if (jstNode.getSource() != null) {
				System.out.println("start="+ jstNode.getSource().getStartOffSet() + 
					" end=" + jstNode.getSource().getEndOffSet());
			}
		}
	}
}