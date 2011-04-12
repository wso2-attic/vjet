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

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public final class AssignExpr extends BaseJstNode implements IExpr, IStmt {

	private static final long serialVersionUID = 1L;
	
	private final ILHS m_lhs;
	private IExpr m_expr;
	private Operator m_op;
	
	//
	// Constructor
	//
	public AssignExpr(final ILHS lhs, final IExpr expr){
		this(lhs, expr, Operator.ASSIGN);
	}
	
	public AssignExpr(final ILHS lhs, final IExpr expr, final String op){
		this(lhs, expr, Operator.toOperator(op));
	}
	
	public AssignExpr(final ILHS lhs, final IExpr expr, final Operator op){
		m_lhs = lhs;
		m_expr = expr;
		m_op = op;
		addChild(lhs);
		addChild(expr);
	}
	
	//
	// Satisfy IExpr, IStmt
	//
	public IJstType getResultType(){
		if (m_lhs != null){
			return m_lhs.getType();
		}
		if (m_expr != null){
			return m_expr.getResultType();
		}
		return null;
	}
	
	public String toExprText(){
		StringBuilder sb = new StringBuilder();
		if (m_lhs != null){
			sb.append(m_lhs.toLHSText());
		}
		if (m_op != null){
			sb.append(m_op.toString());
		}
		if (m_expr != null){
			sb.append(m_expr.toExprText());
		}
		return sb.toString();
	}
	
	public String toStmtText(){
		return toExprText() + ";";
	}
	
	//
	// API
	//
	public ILHS getLHS(){
		return m_lhs;
	}
	
	public Operator getOprator(){
		return m_op;
	}
	
	public IExpr getExpr(){
		return m_expr;
	}
	public void setExpr(IExpr e){
		m_expr = e;
	}
	public void setOp(Operator op){
		m_op = op;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toExprText();
	}
	
	/**
 	 * Assignment operators (typesafe enumeration).
	 * <pre>
	 * AssignmentOperator:<code>
	 *    <b>=</b> ASSIGN
	 *    <b>+=</b> PLUS_ASSIGN
	 *    <b>-=</b> MINUS_ASSIGN
	 *    <b>*=</b> TIMES_ASSIGN
	 *    <b>/=</b> DIVIDE_ASSIGN
	 *    <b>&amp;=</b> BIT_AND_ASSIGN
	 *    <b>|=</b> BIT_OR_ASSIGN
	 *    <b>^=</b> BIT_XOR_ASSIGN
	 *    <b>%=</b> REMAINDER_ASSIGN
	 *    <b>&lt;&lt;=</b> LEFT_SHIFT_ASSIGN
	 *    <b>&gt;&gt;=</b> RIGHT_SHIFT_SIGNED_ASSIGN
	 *    <b>&gt;&gt;&gt;=</b> RIGHT_SHIFT_UNSIGNED_ASSIGN</code>
	 * </pre>
	 */
	public static class Operator implements Serializable{
	
		private static final long serialVersionUID = 1L;
		/**
		 * The name of the operator
		 */
		private String op;
		
		/**
		 * Creates a new assignment operator with the given name.
		 * <p>
		 * Note: this constructor is private. The only instances
		 * ever created are the ones for the standard operators.
		 * </p>
		 * 
		 * @param op the character sequence for the operator
		 */
		private Operator(String op) {
			this.op = op;
		}
		
		/**
		 * Returns the character sequence for the operator.
		 * 
		 * @return the character sequence for the operator
		 */
		public String toString() {
			return op;
		}
		
		/** = operator. */
		public static final Operator ASSIGN = new Operator("=");//$NON-NLS-1$
		/** += operator. */
		public static final Operator PLUS_ASSIGN = new Operator("+=");//$NON-NLS-1$
		/** -= operator. */
		public static final Operator MINUS_ASSIGN = new Operator("-=");//$NON-NLS-1$
		/** *= operator. */
		public static final Operator TIMES_ASSIGN = new Operator("*=");//$NON-NLS-1$
		/** /= operator. */
		public static final Operator DIVIDE_ASSIGN = new Operator("/=");//$NON-NLS-1$
		/** &amp;= operator. */
		public static final Operator BIT_AND_ASSIGN = new Operator("&=");//$NON-NLS-1$
		/** |= operator. */
		public static final Operator BIT_OR_ASSIGN = new Operator("|=");//$NON-NLS-1$
		/** ^= operator. */
		public static final Operator BIT_XOR_ASSIGN = new Operator("^=");//$NON-NLS-1$
		/** %= operator. */
		public static final Operator REMAINDER_ASSIGN = new Operator("%=");//$NON-NLS-1$
		/** &lt;&lt;== operator. */
		public static final Operator LEFT_SHIFT_ASSIGN =
			new Operator("<<=");//$NON-NLS-1$
		/** &gt;&gt;= operator. */
		public static final Operator RIGHT_SHIFT_SIGNED_ASSIGN =
			new Operator(">>=");//$NON-NLS-1$
		/** &gt;&gt;&gt;= operator. */
		public static final Operator RIGHT_SHIFT_UNSIGNED_ASSIGN =
			new Operator(">>>=");//$NON-NLS-1$
		
		/**
		 * Returns the assignment operator corresponding to the given string,
		 * or <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for all 
		 * operators <code>op</code>.
		 * </p>
		 * 
		 * @param token the character sequence for the operator
		 * @return the assignment operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}
		
		/**
		 * Map from token to operator (key type: <code>String</code>;
		 * value type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = {
					ASSIGN,
					PLUS_ASSIGN,
					MINUS_ASSIGN,
					TIMES_ASSIGN,
					DIVIDE_ASSIGN,
					BIT_AND_ASSIGN,
					BIT_OR_ASSIGN,
					BIT_XOR_ASSIGN,
					REMAINDER_ASSIGN,
					LEFT_SHIFT_ASSIGN,
					RIGHT_SHIFT_SIGNED_ASSIGN,
					RIGHT_SHIFT_UNSIGNED_ASSIGN
				};
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}
	}
}
