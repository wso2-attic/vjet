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
import org.ebayopensource.dsf.jst.util.JstTypeHelper;

public class PrefixExpr extends ArithExpr implements IJstResultTypeModifier {

	private static final long serialVersionUID = 1L;

	private IExpr m_term;
	private Operator m_op;
	
	private IJstType m_type;
	
	private boolean m_isFirstTerm = true;

	//
	// Constructor
	//
	public PrefixExpr(final IExpr term, final Operator op) {
		assert term != null : "term cannot be null";
		assert op != null : "op cannot be null";

		m_term = term;
		m_op = op;
		addChild(term);
	}

	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType() {
		if (m_type == null) {
			m_type = m_term.getResultType();
			if (m_op == Operator.INCREMENT || m_op == Operator.DECREMENT) {
				IJstType nativeNumber = JstCache.getInstance().getType("Number");
				if (nativeNumber != null) {
					if (m_type != null && m_type != nativeNumber && m_type.getExtend() != nativeNumber) {
						m_type = nativeNumber;
					}
				} else { //unboxing for java wrapper type - Number, Integer, ...
					m_type = JstTypeHelper.getPrimitiveType(m_term);
				}
			} else if (m_op == Operator.NOT) {
				IJstType nativeBoolean = JstCache.getInstance().getType("PrimitiveBoolean");
				if (nativeBoolean != null) {
					if (m_type != null && m_type != nativeBoolean && m_type.getExtend() != nativeBoolean) {
						m_type = nativeBoolean;
					}
				} else { //unboxing for java wrapper type - Boolean
					m_type = JstTypeHelper.getPrimitiveType(m_term);
				}
			}
		}
		return m_type;
	}

	public String toExprText() {
		StringBuilder sb = new StringBuilder();
		if (!m_isFirstTerm){
			sb.append(" ");
		}
		sb.append(m_op.toString());
		if (m_term != null) {
			sb.append(m_term.toExprText());
		}
		return sb.toString();
	}

	public String toStmtText() {
		return toExprText() + ";";
	}
	
	//
	// API
	//
	public void setOperand(IExpr operand){
		removeChild(m_term);
		m_term = operand;
		addChild(m_term);
	}
	
	public IExpr getIdentifier() {
		return m_term;
	}
	
	public Operator getOperator(){
		return m_op;
	}
	
	public boolean isFirstTerm() {
		return m_isFirstTerm;
	}

	public void setIsFirstTerm(boolean isFirstTerm) {
		m_isFirstTerm = isFirstTerm;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return toExprText();
	}

	/**
	 * Prefix operators (typesafe enumeration).
	 * 
	 * <pre>
	 * PrefixOperator:
	 *    &lt;b&gt;
	 * <code>
	 * ++
	 * </code>
	 * &lt;/b&gt;
	 * <code>
	 * INCREMENT
	 * </code>
	 *    &lt;b&gt;
	 * <code>
	 * --
	 * </code>
	 * &lt;/b&gt;
	 * <code>
	 * DECREMENT
	 * </code>
	 *    &lt;b&gt;
	 * <code>
	 * +</code>
	 * &lt;/b&gt;
	 * <code>
	 * PLUS
	 * </code>
	 *    &lt;b&gt;
	 * <code>
	 * -</code>
	 * &lt;/b&gt;
	 * <code>
	 * MINUS
	 * </code>
	 *    &lt;b&gt;
	 * <code>
	 * &tilde;</code>
	 * &lt;/b&gt;
	 * <code>
	 * COMPLEMENT
	 * </code>
	 *    &lt;b&gt;
	 * <code>
	 * !</code>
	 * &lt;/b&gt;
	 * <code>
	 * NOT
	 * </code>
	 * 	 &lt;b&gt;
	 * <code>
	 * typeof</code>
	 * &lt;/b&gt;
	 * <code>
	 * TYPEOF
	 * </code>
	 * 	 &lt;b&gt;
	 * <code>
	 * delete</code>
	 * &lt;/b&gt;
	 * <code>
	 * DELETE
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
		 * Creates a new prefix operator with the given token.
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

		/** Prefix increment "++" operator. */
		public static final Operator INCREMENT = new Operator("++");//$NON-NLS-1$

		/** Prefix decrement "--" operator. */
		public static final Operator DECREMENT = new Operator("--");//$NON-NLS-1$

		/** Unary plus "+" operator. */
		public static final Operator PLUS = new Operator("+");//$NON-NLS-1$

		/** Unary minus "-" operator. */
		public static final Operator MINUS = new Operator("-");//$NON-NLS-1$

		/** Bitwise complement "~" operator. */
		public static final Operator COMPLEMENT = new Operator("~");//$NON-NLS-1$

		/** Logical complement "!" operator. */
		public static final Operator NOT = new Operator("!");//$NON-NLS-1$
		
		/** typeof operator. */
		public static final Operator TYPEOF = new Operator("typeof ");//$NON-NLS-1$
		
		/** typeof operator. */
		public static final Operator DELETE = new Operator("delete ");//$NON-NLS-1$
		
		/** void operator. */
		public static final Operator VOID = new Operator("void ");//$NON-NLS-1$

		/**
		 * Map from token to operator (key type: <code>String</code>; value
		 * type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = { INCREMENT, DECREMENT, PLUS, MINUS, COMPLEMENT,
					NOT, TYPEOF, DELETE, VOID};
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}

		/**
		 * Returns the prefix operator corresponding to the given string, or
		 * <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 * @return the prefix operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}
	}

	@Override
	public void setType(IJstType type) {
		m_type = type;
	}
}
