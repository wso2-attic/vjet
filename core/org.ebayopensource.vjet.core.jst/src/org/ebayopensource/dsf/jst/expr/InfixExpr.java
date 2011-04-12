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

import org.ebayopensource.dsf.jst.IJstResultTypeModifier;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;

public class InfixExpr extends ArithExpr implements IJstResultTypeModifier  {

	private static final long serialVersionUID = 1L;

	private IExpr m_left;
	private IExpr m_right;
	private final Operator m_op;
	
	private IJstType m_type;

	//
	// Constructor
	//
	public InfixExpr(final IExpr left, final IExpr right, final Operator op) {
		if (op == null) {
			throw new RuntimeException("Operator can't be null");
		}

		m_left = left;
		m_right = right;
		m_op = op;
		addChild(left);
		addChild(right);
	}

	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType() {
		if (m_type == null) {
			boolean useRight = true;
			if (m_op == Operator.CONDITIONAL_OR) {
				IJstType leftType = JstTypeHelper.getExprType(m_left);
				IJstType rightType = JstTypeHelper.getExprType(m_right);
				if (rightType == leftType || leftType == null){
					m_type = rightType;
				}
				else if (rightType == null){
					m_type = leftType;
					useRight = false;
				} else if (leftType.getName().equals(rightType.getName())) {
					m_type = rightType;
				}
			}
			else {
				IJstType leftType = m_left.getResultType();
				IJstType rightType = m_right.getResultType();
				if (leftType == null){
					m_type = rightType;
				}
				else if (rightType == null){
					m_type = leftType;
					useRight = false;
				}
				else if (DataTypeHelper.canPromote(leftType, rightType)) {
					m_type = rightType;
				}
				else {
					m_type =leftType;
					useRight = false;
				}
			}
			if ((m_op == Operator.PLUS && m_type != null && !"String".equals(m_type.getName()))
				|| m_op == Operator.MINUS) {
				IJstType nativeNumber = JstCache.getInstance().getType("Number");
				if (nativeNumber != null) {
					if (m_type != null && m_type != nativeNumber && m_type.getExtend() != nativeNumber) {
						m_type = nativeNumber;
					}
				} else {//unboxing for java wrapper type - Number, Integer, ...
					m_type = JstTypeHelper.getPrimitiveType(useRight? m_right : m_left);
				}
			}
			if (m_op == Operator.CONDITIONAL_AND || m_op == Operator.CONDITIONAL_OR) {
				IJstType nativeBoolean = JstCache.getInstance().getType("PrimitiveBoolean");
				if (nativeBoolean != null) {
					if (m_type != null && m_type != nativeBoolean && m_type.getExtend() != nativeBoolean) {
						m_type = nativeBoolean;
					}
				} else {//unboxing for java wrapper type - Boolean
					m_type = JstTypeHelper.getPrimitiveType(useRight? m_right : m_left);
				}
			}
		}
		return m_type;
	}

	public String toExprText() {
		StringBuilder sb = new StringBuilder();
		if (m_left != null) {
			sb.append(m_left.toExprText());
		}
		//For in operator add space around the operator
		if(m_op.equals(Operator.IN)){
			sb.append(" ");
		}
		sb.append(m_op.toString());
		if(m_op.equals(Operator.IN)){
			sb.append(" ");
		}

		if (m_right != null) {
			sb.append(m_right.toExprText());
		}
		return sb.toString();
	}

	public String toStmtText() {
		return toExprText() + ";";
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
	
	public void setLeft(IExpr e){
		removeChild(m_left);
		m_left = e;
		addChild(m_left);
	}
	
	public void setRight(IExpr e){
		removeChild(m_right);
		m_right = e;
		addChild(m_right);
	}
	
	public Operator getOperator(){
		return m_op;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return toExprText();
	}
	
	@Override
	public void setType(IJstType type) {
		m_type = type;
	}

	/**
	 * Infix operators (typesafe enumeration).
	 * 
	 * <pre>
	 * InfixOperator:
	 * <code>
	 *    &lt;b&gt;*&lt;/b&gt;	TIMES
	 *    &lt;b&gt;/&lt;/b&gt;  DIVIDE
	 *    &lt;b&gt;%&lt;/b&gt;  REMAINDER
	 *    &lt;b&gt;+&lt;/b&gt;  PLUS
	 *    &lt;b&gt;-&lt;/b&gt;  MINUS
	 *    &lt;b&gt;&lt;&lt;&lt;/b&gt;  LEFT_SHIFT
	 *    &lt;b&gt;&gt;&gt;&lt;/b&gt;  RIGHT_SHIFT_SIGNED
	 *    &lt;b&gt;&gt;&gt;&gt;&lt;/b&gt;  RIGHT_SHIFT_UNSIGNED
	 *    &lt;b&gt;&lt;&lt;/b&gt;  LESS
	 *    &lt;b&gt;&gt;&lt;/b&gt;  GREATER
	 *    &lt;b&gt;&lt;=&lt;/b&gt;  LESS_EQUALS
	 *    &lt;b&gt;&gt;=&lt;/b&gt;  GREATER_EQUALS
	 *    &lt;b&gt;==&lt;/b&gt;  EQUALS
	 *    &lt;b&gt;!=&lt;/b&gt;  NOT_EQUALS
	 *    &lt;b&gt;&circ;&lt;/b&gt;  XOR
	 *    &lt;b&gt;&amp;&lt;/b&gt;  AND
	 *    &lt;b&gt;|&lt;/b&gt;  OR
	 *    &lt;b&gt;&amp;&amp;&lt;/b&gt;  CONDITIONAL_AND
	 *    &lt;b&gt;||&lt;/b&gt;  CONDITIONAL_OR
	 * </code>
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
		 * Note: this constructor is private. The only instances ever created
		 * are the ones for the standard operators.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
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
		public static final Operator TIMES = new Operator("*");//$NON-NLS-1$

		/** Division "/" operator. */
		public static final Operator DIVIDE = new Operator("/");//$NON-NLS-1$

		/** Remainder "%" operator. */
		public static final Operator REMAINDER = new Operator("%");//$NON-NLS-1$

		/** Addition (or string concatenation) "+" operator. */
		public static final Operator PLUS = new Operator("+");//$NON-NLS-1$

		/** Subtraction "-" operator. */
		public static final Operator MINUS = new Operator("-");//$NON-NLS-1$

		/** Left shift "&lt;&lt;" operator. */
		public static final Operator LEFT_SHIFT = new Operator("<<");//$NON-NLS-1$

		/** Signed right shift "&gt;&gt;" operator. */
		public static final Operator RIGHT_SHIFT_SIGNED = new Operator(">>");//$NON-NLS-1$

		/** Unsigned right shift "&gt;&gt;&gt;" operator. */
		public static final Operator RIGHT_SHIFT_UNSIGNED = new Operator(">>>");//$NON-NLS-1$

		/** Less than "&lt;" operator. */
		public static final Operator LESS = new Operator("<");//$NON-NLS-1$

		/** Greater than "&gt;" operator. */
		public static final Operator GREATER = new Operator(">");//$NON-NLS-1$

		/** Less than or equals "&lt;=" operator. */
		public static final Operator LESS_EQUALS = new Operator("<=");//$NON-NLS-1$

		/** Greater than or equals "&gt=;" operator. */
		public static final Operator GREATER_EQUALS = new Operator(">=");//$NON-NLS-1$
		
		/** Exactly equals "===" operator. */
		public static final Operator EXACTLY_EQUALS = new Operator("===");//$NON-NLS-1$
		
		/** Not exactly equals "!==" operator. */
		public static final Operator NOT_EXACTLY_EQUALS = new Operator("!==");//$NON-NLS-1$

		/** Equals "==" operator. */
		public static final Operator EQUALS = new Operator("==");//$NON-NLS-1$

		/** Not equals "!=" operator. */
		public static final Operator NOT_EQUALS = new Operator("!=");//$NON-NLS-1$

		/** Exclusive OR "^" operator. */
		public static final Operator XOR = new Operator("^");//$NON-NLS-1$

		/** Inclusive OR "|" operator. */
		public static final Operator OR = new Operator("|");//$NON-NLS-1$

		/** AND "&amp;" operator. */
		public static final Operator AND = new Operator("&");//$NON-NLS-1$

		/** Conditional OR "||" operator. */
		public static final Operator CONDITIONAL_OR = new Operator("||");//$NON-NLS-1$

		/** Conditional AND "&amp;&amp;" operator. */
		public static final Operator CONDITIONAL_AND = new Operator("&&");//$NON-NLS-1$

		/** Conditional AND "in" operator. */
		public static final Operator IN = new Operator("in");//$NON-NLS-1$

		/**
		 * Map from token to operator (key type: <code>String</code>; value
		 * type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = { TIMES, DIVIDE, REMAINDER, PLUS, MINUS,
					LEFT_SHIFT, RIGHT_SHIFT_SIGNED, RIGHT_SHIFT_UNSIGNED, LESS,
					GREATER, LESS_EQUALS, GREATER_EQUALS,
					EXACTLY_EQUALS, NOT_EXACTLY_EQUALS, EQUALS, NOT_EQUALS,
					XOR, OR, AND, CONDITIONAL_OR, CONDITIONAL_AND,IN };
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}

		/**
		 * Returns the infix operator corresponding to the given string, or
		 * <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 * @return the infix operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}
	}
}
