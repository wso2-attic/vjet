/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.expr;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class BoolExpr extends ArithExpr implements IBoolExpr {
	
	private static final long serialVersionUID = 1L;
	
	private final IExpr m_left;
	private final IExpr m_right;
	private final Operator m_op;
	
	//
	// Constructor
	//
	public BoolExpr(final IExpr left, final IExpr right, final String op){
		this(left, right, Operator.toOperator(op));
	}
	
	public BoolExpr(final IExpr left){
		this(left, null, (Operator)null);
	}
	
	public BoolExpr(final IExpr left, final IExpr right, final Operator op){
		assert left != null : "left cannot be null";
		
		m_left = left;
		m_right = right;
		m_op = op;
		addChild(left);
		addChild(right);
	}
	
	//
	// Satisfy IBoolExpr, IExpr, IStmt
	//
	public String toBoolExprText(){
		StringBuilder sb = new StringBuilder();
		if (m_left != null){
			sb.append(m_left.toExprText());
		}
		if (m_op != null){
			sb.append(m_op.toString());
		}
		if (m_right != null){
			sb.append(m_right.toExprText());
		}
		return sb.toString();
	}
	
	public IJstType getResultType() {		
		IJstType booleanType = JstCache.getInstance().getType("boolean");
		return booleanType;
	}
	
	public String toExprText(){
		return toBoolExprText();
	}
	
	public String toStmtText(){
		return toBoolExprText() + ";";
	}
	
	//
	// API
	//
	public IExpr getLeft() {
		return m_left;
	}

	public IExpr getRight() {
		return m_right;
	}
	
	public Operator getOperator(){
		return m_op;
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
	
	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}

	/**
	 	 * Comparison operators (typesafe enumeration).
	 	 * <pre>
		 * InfixOperator:<code>
//		 *    <b>*</b>	TIMES
//		 *    <b>/</b>  DIVIDE
//		 *    <b>%</b>  REMAINDER
//		 *    <b>+</b>  PLUS
//		 *    <b>-</b>  MINUS
//		 *    <b>&lt;&lt;</b>  LEFT_SHIFT
//		 *    <b>&gt;&gt;</b>  RIGHT_SHIFT_SIGNED
//		 *    <b>&gt;&gt;&gt;</b>  RIGHT_SHIFT_UNSIGNED
		 *    <b>&lt;</b>  LESS
		 *    <b>&gt;</b>  GREATER
		 *    <b>&lt;=</b>  LESS_EQUALS
		 *    <b>&gt;=</b>  GREATER_EQUALS
		 *    <b>==</b>  EQUALS
		 *    <b>!=</b>  NOT_EQUALS
//		 *    <b>^</b>  XOR
//		 *    <b>&amp;</b>  AND
//		 *    <b>|</b>  OR
		 *    <b>&amp;&amp;</b>  CONDITIONAL_AND
		 *    <b>||</b>  CONDITIONAL_OR</code>
		 * </pre>
		 */
	public static class Operator implements Serializable {
	
		private static final long serialVersionUID = 1L;
		
		/**
		 * The token for the operator.
		 */
		private String token;
		
		/**
		 * Creates a new infix operator with the given token.
		 * <p>
		 * Note: this constructor is private. The only instances
		 * ever created are the ones for the standard operators.
		 * </p>
		 * 
		 * @param token the character sequence for the operator
		 */
		private Operator(String token) {
			this.token = token;
		}
		
		/**
		 * Returns the character sequence for the operator.
		 * 
		 * @return the character sequence for the operator
		 */
		public String toString() {
			return token;
		}
		
		/** Multiplication "*" operator. */
//			public static final Operator TIMES = new Operator("*");//$NON-NLS-1$
//			/** Division "/" operator. */
//			public static final Operator DIVIDE = new Operator("/");//$NON-NLS-1$
//			/** Remainder "%" operator. */
//			public static final Operator REMAINDER = new Operator("%");//$NON-NLS-1$
//			/** Addition (or string concatenation) "+" operator. */
//			public static final Operator PLUS = new Operator("+");//$NON-NLS-1$
//			/** Subtraction "-" operator. */
//			public static final Operator MINUS = new Operator("-");//$NON-NLS-1$
//			/** Left shift "&lt;&lt;" operator. */
//			public static final Operator LEFT_SHIFT = new Operator("<<");//$NON-NLS-1$
//			/** Signed right shift "&gt;&gt;" operator. */
//			public static final Operator RIGHT_SHIFT_SIGNED = new Operator(">>");//$NON-NLS-1$
//			/** Unsigned right shift "&gt;&gt;&gt;" operator. */
//			public static final Operator RIGHT_SHIFT_UNSIGNED = 
//				new Operator(">>>");//$NON-NLS-1$
		/** Less than "&lt;" operator. */
		public static final Operator LESS = new Operator("<");//$NON-NLS-1$
		/** Greater than "&gt;" operator. */
		public static final Operator GREATER = new Operator(">");//$NON-NLS-1$
		/** Less than or equals "&lt;=" operator. */
		public static final Operator LESS_EQUALS = new Operator("<=");//$NON-NLS-1$
		/** Greater than or equals "&gt=;" operator. */
		public static final Operator GREATER_EQUALS = new Operator(">=");//$NON-NLS-1$
		/** Equals "==" operator. */
		public static final Operator EQUALS = new Operator("==");//$NON-NLS-1$
		/** Not equals "!=" operator. */
		public static final Operator NOT_EQUALS = new Operator("!=");//$NON-NLS-1$
		/** Identical "===" operator. */
		public static final Operator IDENTICAL = new Operator("===");
		/** Not Identical "!==" operator. */
		public static final Operator NOT_IDENTICAL = new Operator("!==");
		/** Exclusive OR "^" operator. */
//		public static final Operator XOR = new Operator("^");//$NON-NLS-1$
//		/** Inclusive OR "|" operator. */
//		public static final Operator OR = new Operator("|");//$NON-NLS-1$
//		/** AND "&amp;" operator. */
//		public static final Operator AND = new Operator("&");//$NON-NLS-1$
		/** Conditional OR "||" operator. */
		public static final Operator CONDITIONAL_OR = new Operator(" || ");//$NON-NLS-1$
		/** Conditional AND "&amp;&amp;" operator. */
		public static final Operator CONDITIONAL_AND = new Operator(" && ");//$NON-NLS-1$
		/** Instanceof "instanceof" operator. */
		public static final Operator INSTANCE_OF = new Operator(" " + JsCoreKeywords.INSTANCEOF + " ");//$NON-NLS-1$
		
		/**
		 * Map from token to operator (key type: <code>String</code>;
		 * value type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = {
//						TIMES,
//						DIVIDE,
//						REMAINDER,
//						PLUS,
//						MINUS,
//						LEFT_SHIFT,
//						RIGHT_SHIFT_SIGNED,
//						RIGHT_SHIFT_UNSIGNED,
					LESS,
					GREATER,
					LESS_EQUALS,
					GREATER_EQUALS,
					EQUALS,
					NOT_EQUALS,
//					XOR,
//					OR,
//					AND,
					CONDITIONAL_OR,
					CONDITIONAL_AND,
					INSTANCE_OF,
					IDENTICAL,
					NOT_IDENTICAL,
				};
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString().trim(), ops[i]);
			}
		}

		/**
		 * Returns the infix operator corresponding to the given string,
		 * or <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for 
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token the character sequence for the operator
		 * @return the infix operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}	
	}
}
