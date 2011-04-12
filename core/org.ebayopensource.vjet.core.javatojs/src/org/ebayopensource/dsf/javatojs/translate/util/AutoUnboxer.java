/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.ArithExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

/**
 * A utility class that helps on auto-unboxing for JST expressions.
 */
public class AutoUnboxer {
	
	// Singleton
	private static AutoUnboxer s_instance = new AutoUnboxer();
	private AutoUnboxer(){};
	public static AutoUnboxer getInstance(){
		return s_instance;
	}
	
	//
	// API
	//
	/**
	 * Answer whether the given expression needs unboxing with given expected type.
	 * @param expr IExpr
	 * @param expectedType IJstType
	 * @return boolean
	 */
	public boolean needAutoUnboxing(
			final IExpr expr,
			final IJstType expectedType) {
		
		if (expr == null){
			return false;
		}
		
		if (expr instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)expr;
			return needAutoUnboxing(assignExpr.getExpr(), assignExpr.getResultType());
		}
		
		if (expr instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdExpr = (MtdInvocationExpr)expr;
			IExpr mtdIdentifier = mtdExpr.getMethodIdentifier();
			if (mtdIdentifier != null && mtdIdentifier instanceof JstIdentifier){
				IJstNode binding = ((JstIdentifier)mtdIdentifier).getJstBinding();
				if (binding instanceof IJstMethod){
					IJstMethod jstMtd = (IJstMethod)binding;
					IExpr actualArg;
					JstArg expectedArg = null;
					for (int i=0; i<mtdExpr.getArgs().size(); i++){
						actualArg = mtdExpr.getArgs().get(i);
						if (i < jstMtd.getArgs().size()){
							expectedArg = jstMtd.getArgs().get(i);
						}
						if (expectedArg == null){
							continue;
						}
						if (needAutoUnboxing(actualArg, expectedArg.getType())){
							return true;
						}
					}
				}
			}
		}

		IJstType toType = getExpectedType(expectedType);
		if (forceUnboxing(expr, toType)){
			return true;
		}
		
		return needAutoUnboxing(expr.getResultType(), toType);
	}	
	
	/**
	 * Answer whether the given actual type needs unboxing with given expected type.
	 * @param actualType IJstType
	 * @param expectedType IJstType
	 * @return boolean
	 */
	public boolean needAutoUnboxing(
			final IJstType actualType,
			final IJstType expectedType) {
		
		if (actualType == null 
				|| actualType.getSimpleName() == null
				|| expectedType == null
				|| expectedType.getSimpleName() == null){
			return false;
		}
		
		if ((DataTypeHelper.isNumericPrimitiveType(expectedType)
				|| DataTypeHelper.isCharPrimitiveType(expectedType)) 
			&& ((actualType instanceof JstParamType) 
				|| DataTypeHelper.isNumericWrapperType(actualType))){
			return true;
		}

		if (DataTypeHelper.isBooleanPrimitiveType(expectedType) 
				&& ((actualType instanceof JstParamType) 
					|| DataTypeHelper.isBooleanWrapperType(actualType))){
			return true;
		}

		if ((DataTypeHelper.isNumericPrimitiveType(expectedType)
				|| DataTypeHelper.isCharPrimitiveType(expectedType)) 
			&& ((actualType instanceof JstParamType) 
				|| DataTypeHelper.isCharWrapperType(actualType))){
			return true;
		}
		
		return false;
	}	
	
	/**
	 * First it checks whether the given expression needs unboxing with given expected type.
	 * If answer is YES, it does auto-unboxing for the given expression with expected type.
	 * @param expr IExpr
	 * @param expectedType IJstType
	 * @return IExpr the new expression after unboxing, or the original expression if unboxing
	 * is not performed
	 */
	public IExpr autoUnboxing(
			final IExpr expr, 
			final IJstType expectedType){
		
		if (!needAutoUnboxing(expr, expectedType)){
			return expr;
		}
		else {	
			return unbox(expr, expectedType);
		}
	}	
	
	//
	// Private
	//
	private boolean forceUnboxing(final IExpr expr, final IJstType expectedType){
		if (expr == null || expectedType == null){
			return false;
		}
		
		if (expr instanceof JstArrayInitializer){
			return true;
		}
		else if (expr instanceof InfixExpr){
			InfixExpr infix = (InfixExpr)expr;
			IExpr l = infix.getLeft();
			IExpr r = infix.getRight();
			if ((l != null && isStringType(l.getResultType()))
				|| (r != null && isStringType(r.getResultType()))){
				return false;
			}
			return true;
		}
		else if (expr instanceof PrefixExpr){
			PrefixExpr prefix = (PrefixExpr)expr;
			return (prefix.getIdentifier() != null);
		}
		else if (expr instanceof PostfixExpr){
			PostfixExpr postfix = (PostfixExpr)expr;
			return (postfix.getIdentifier() != null);
		}
		else if (expr instanceof ParenthesizedExpr){
			return forceUnboxing(((ParenthesizedExpr)expr).getExpression(), expectedType);
		}
		return false;
	}
	
	private IExpr unbox(
			final IExpr expr, 
			final IJstType expectedType){
		
		if (expr == null || expectedType == null){
			return expr;
		}
		
		IJstType toType = getExpectedType(expectedType);
		
		if (expr instanceof SimpleLiteral){
			return expr;			
		}
		else if (expr instanceof InfixExpr){
			return unbox((InfixExpr)expr,toType);
		} 
		else if (expr instanceof PrefixExpr){
			return unbox((PrefixExpr)expr,toType);
		} 
		else if (expr instanceof PostfixExpr){
			return unbox((PostfixExpr)expr,toType);
		} 
		else if (expr instanceof ParenthesizedExpr){
			return unbox((ParenthesizedExpr)expr,toType);		
		}
		else if (expr instanceof JstArrayInitializer){
			return unbox((JstArrayInitializer)expr, toType);
		} 
		else if (expr instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)expr;
			toType = getExpectedType(assignExpr.getResultType());
			IExpr e = assignExpr.getExpr();
			if (needAutoUnboxing(e, toType)){
				IExpr newExpr = unbox(e,toType);
				if (newExpr != e){
					assignExpr.setExpr(newExpr);
				}
			}	
			return expr;
		}
		else if (expr instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdExpr = (MtdInvocationExpr)expr;
			if (mtdExpr.getMethodIdentifier() instanceof JstIdentifier){
				IJstNode binding = ((JstIdentifier)mtdExpr.getMethodIdentifier()).getJstBinding();
				if (binding instanceof JstMethod){
					JstMethod jstMtd = (JstMethod)binding;
					IExpr actualArg;
					JstArg expectedArg = null;
					for (int i=0; i<mtdExpr.getArgs().size(); i++){
						actualArg = mtdExpr.getArgs().get(i);
						if (i < jstMtd.getArgs().size()){
							expectedArg = jstMtd.getArgs().get(i);
						}
						if (expectedArg == null){
							continue;
						}
						if (needAutoUnboxing(actualArg, expectedArg.getType())){
							IExpr newExpr = unbox(actualArg, expectedArg.getType());
							if (newExpr != actualArg){
								mtdExpr.setArg(i, newExpr);
							}
						}
					}
					
				}
			}
			if (needAutoUnboxing(expr.getResultType(), expectedType)){
				return doIt(expr, expectedType);
			}
			else {
				return expr;
			}
		}
		else if (expr instanceof JstProperty){
			JstProperty pty = (JstProperty)expr;
			toType = getExpectedType(pty.getType());
			IExpr e = pty.getInitializer();
			if (needAutoUnboxing(e, toType)){
				IExpr newExpr = unbox(e, toType);
				if (newExpr != e){
					pty.setInitializer(newExpr);
				}
			}	
			return expr;
		}
		else if (expr instanceof JstIdentifier
				|| expr instanceof ObjCreationExpr 
				|| expr instanceof MtdInvocationExpr){
			return doIt(expr, toType);	
		}
		else if (expr instanceof CastExpr) {
			return doIt((CastExpr)expr, toType);
		}
		return expr;
	}	
	
	private JstArrayInitializer unbox(
			final JstArrayInitializer arrayInitializer, 
			final IJstType expectedType){
		
		if (arrayInitializer == null){
			return null;
		}
		
		List<IExpr> exprs = arrayInitializer.getExprs();
		if (exprs != null){
			IExpr expr;
			IExpr newExpr;
			for (int i=0; i<exprs.size(); i++){
				expr = exprs.get(i);
				if (needAutoUnboxing(expr, expectedType)){
					newExpr = unbox(expr, expectedType);
					if (newExpr != expr){
						exprs.set(i, newExpr);
					}
				}
			}
		}
		return arrayInitializer;
	}
	
	private InfixExpr unbox(
			final InfixExpr infix, 
			final IJstType expectedType){

		IExpr left = infix.getLeft();
		IExpr right = infix.getRight();
		if (left != null && isStringType(left.getResultType()) 
				|| right != null && isStringType(right.getResultType())){
			return infix;
		}
		
		if (left != null){
			if (left instanceof ArithExpr){
				if (left instanceof ParenthesizedExpr 
					&& ((ParenthesizedExpr)left).getExpression() instanceof AssignExpr){
					
					ParenthesizedExpr pExpr = (ParenthesizedExpr)left;
					AssignExpr assignExpr = (AssignExpr)pExpr.getExpression();
					ILHS lhs = assignExpr.getLHS();
					if (lhs != null){
						IJstType t = lhs.getType();
						if (DataTypeHelper.isBooleanWrapperType(t) 
							|| DataTypeHelper.isNumericWrapperType(t) 
							|| DataTypeHelper.isCharWrapperType(t)){
							
							IExpr newExpr = doIt(pExpr, DataTypeHelper.getPrimitivetype(t));
							if (newExpr != left){
								infix.setLeft(newExpr);
							}
						}
					}
				}
				else {
					unbox(left, expectedType);
				}
			}
			else if (needUnbox(infix, left)){
				
				IExpr newExpr = unbox(left, expectedType);
				if (newExpr != left){
					infix.setLeft(newExpr);
				}
			}
		}
		if (right != null){
			if (right instanceof ArithExpr){
				if (right instanceof ParenthesizedExpr 
						&& ((ParenthesizedExpr)right).getExpression() instanceof AssignExpr){
						
					ParenthesizedExpr pExpr = (ParenthesizedExpr)right;
					AssignExpr assignExpr = (AssignExpr)pExpr.getExpression();
					ILHS lhs = assignExpr.getLHS();
					if (lhs != null){
						IJstType t = lhs.getType();
						if (DataTypeHelper.isBooleanWrapperType(t) 
							|| DataTypeHelper.isNumericWrapperType(t) 
							|| DataTypeHelper.isCharWrapperType(t)){
							
							IExpr newExpr = doIt(pExpr, DataTypeHelper.getPrimitivetype(t));
							if (newExpr != right){
								infix.setRight(newExpr);
							}
						}
					}
				}
				else {
					unbox(right, expectedType);
				}
			}
			else if (needUnbox(infix, right)){
				
				IExpr newExpr = unbox(right, expectedType);
				if (newExpr != right){
					infix.setRight(newExpr);
				}
			}
		}
		return infix;
	}
	
	private PrefixExpr unbox(
			final PrefixExpr prefix, 
			final IJstType expectedType){

		IExpr expr = prefix.getIdentifier();
		if (expr != null){
			if (expr instanceof ArithExpr){
				unbox(expr, expectedType);
			}
			else if (needUnbox(prefix, expr)){
				IExpr newExpr = unbox(expr, expectedType);
				if (newExpr != expr){
					prefix.setOperand(newExpr);
				}
			}
		}
		return prefix;
	}
	
	private PostfixExpr unbox(
			final PostfixExpr postfix, 
			final IJstType expectedType){

		IExpr expr = postfix.getIdentifier();
		if (expr != null){
			if (expr instanceof ArithExpr){
				unbox(expr, expectedType);
			}
			else if (needUnbox(postfix, expr)){
				IExpr newExpr = unbox(expr, expectedType);
				if (newExpr != expr){
					postfix.setOperand(newExpr);
				}
			}
		}
		return postfix;
	}
	
	private ParenthesizedExpr unbox(
			final ParenthesizedExpr parenthesized, 
			final IJstType expectedType){
		
		IExpr expr = parenthesized.getExpression();
		if (expr != null){
			if (expr instanceof ArithExpr){
				unbox(expr, expectedType);
			}
			else if (needAutoUnboxing(expr, expectedType)){
				IExpr newExpr = unbox(expr, expectedType);
				if (newExpr != expr){
					parenthesized.setExpression(newExpr);
				}
			}
		}
		return parenthesized;
	}
	
	private boolean needUnbox(ArithExpr arithExpr, IExpr operand){
		IJstType actualType = operand.getResultType();
		if (actualType == null){
			return false;
		}
		if (isArithOperation(arithExpr)){
			return !DataTypeHelper.isCharPrimitiveType(actualType)
				&& !DataTypeHelper.isNumericPrimitiveType(actualType);
		}
		else if(isBoolOperation(arithExpr)){
			return !DataTypeHelper.isBooleanPrimitiveType(actualType);
		}
		else {
			return false;
		}
	}
	
	private IExpr doIt(final IExpr expr, final IJstType toType){
		
		IJstType primitiveType = expr.getResultType();
		if (primitiveType == null 
				|| primitiveType instanceof JstParamType
				|| TranslateHelper.isObjectType(primitiveType)){
			primitiveType = toType;
		}
		if (!DataTypeHelper.isPrimitiveType(primitiveType)){
			primitiveType = DataTypeHelper.getPrimitivetype(primitiveType);
		}
		String mtdName = getMtdName(primitiveType);
		if (mtdName == null){
			return expr;
		}
		IJstNode parentNode = expr.getParentNode();
		MtdInvocationExpr mtdExpr = null;
		if(mtdName.equals("booleanValue")) {
			JstIdentifier identifier = new JstIdentifier("vjo.java.lang.BooleanUtil.booleanValue");
			mtdExpr = new MtdInvocationExpr(identifier);
			mtdExpr.setResultType(primitiveType);
			List<IExpr> args = new ArrayList<IExpr>();
			args.add(expr);
			mtdExpr.setArgs(args);
			mtdExpr.setParent(parentNode);

			if (parentNode.getRootType() instanceof JstType){
				JstType ownerType = (JstType)parentNode.getRootType();
				JstType type = JstCache.getInstance().getType("vjo.java.lang.BooleanUtil");
				ownerType.addImport(type);
				TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(ownerType);
				tInfo.addActiveImport(type);
			}
		} else {
			JstIdentifier identifier = new JstIdentifier(mtdName);
			mtdExpr = new MtdInvocationExpr(identifier);
			mtdExpr.setQualifyExpr(expr);
			mtdExpr.setResultType(primitiveType);		
			mtdExpr.setParent(parentNode);
		}

		return mtdExpr;
	}
	
	private IJstType getExpectedType(final IJstType expectedType){
		if (expectedType == null){
			return null;
		}
		
		if (expectedType instanceof JstArray){ 
			return ((JstArray)expectedType).getComponentType();
		}
		
		return expectedType;
	}
	
	private boolean isStringType(final IJstType jstType){
		return DataTypeHelper.isString(jstType);
	}
	
	private static Map<String,String> s_mtdNames = new HashMap<String,String>();
	static {
		s_mtdNames.put("boolean", "booleanValue");
		s_mtdNames.put("byte", "byteValue");
		s_mtdNames.put("short", "shortValue");
		s_mtdNames.put("int", "intValue");
		s_mtdNames.put("float", "floatValue");
		s_mtdNames.put("long", "longValue");
		s_mtdNames.put("double", "doubleValue");
		s_mtdNames.put("char", "charValue");
	}
	
	private static String getMtdName(final IJstType primitiveType){
		if (primitiveType == null){
			return null;
		}
		return s_mtdNames.get(primitiveType.getSimpleName());
	}
	
	private static List<InfixExpr.Operator> s_arithInfixOperators = 
		new ArrayList<InfixExpr.Operator>();
	static {
		s_arithInfixOperators.add(InfixExpr.Operator.PLUS);
		s_arithInfixOperators.add(InfixExpr.Operator.MINUS);
		s_arithInfixOperators.add(InfixExpr.Operator.TIMES);
		s_arithInfixOperators.add(InfixExpr.Operator.DIVIDE);
		s_arithInfixOperators.add(InfixExpr.Operator.AND);
		s_arithInfixOperators.add(InfixExpr.Operator.OR);
		s_arithInfixOperators.add(InfixExpr.Operator.XOR);
	}
	
	private static List<PrefixExpr.Operator> s_arithPrefixOperators = 
		new ArrayList<PrefixExpr.Operator>();
	static {
		s_arithPrefixOperators.add(PrefixExpr.Operator.PLUS);
		s_arithPrefixOperators.add(PrefixExpr.Operator.MINUS);
		s_arithPrefixOperators.add(PrefixExpr.Operator.INCREMENT);
		s_arithPrefixOperators.add(PrefixExpr.Operator.DECREMENT);
		s_arithPrefixOperators.add(PrefixExpr.Operator.COMPLEMENT);
	}
	
	private static List<PostfixExpr.Operator> s_arithPostfixOperators = 
		new ArrayList<PostfixExpr.Operator>();
	static {
		s_arithPostfixOperators.add(PostfixExpr.Operator.INCREMENT);
		s_arithPostfixOperators.add(PostfixExpr.Operator.DECREMENT);
	}
	
	private static boolean isArithOperation(ArithExpr expr){
		if (expr == null){
			return false;
		}
		if (expr instanceof InfixExpr){
			return s_arithInfixOperators.contains(((InfixExpr)expr).getOperator());
		}
		else if (expr instanceof PrefixExpr){
			return s_arithPrefixOperators.contains(((PrefixExpr)expr).getOperator());
		}
		else if (expr instanceof PostfixExpr){
			return s_arithPostfixOperators.contains(((PostfixExpr)expr).getOperator());
		}
		return false;
	}
	
	private static List<InfixExpr.Operator> s_boolInfixOperators = new ArrayList<InfixExpr.Operator>();
	static {
		s_boolInfixOperators.add(InfixExpr.Operator.CONDITIONAL_AND);
		s_boolInfixOperators.add(InfixExpr.Operator.CONDITIONAL_OR);
	}
	
	private static List<PrefixExpr.Operator> s_boolPrefixOperators = new ArrayList<PrefixExpr.Operator>();
	static {
		s_boolPrefixOperators.add(PrefixExpr.Operator.NOT);
	}
	
	private static boolean isBoolOperation(ArithExpr expr){
		if (expr == null){
			return false;
		}
		if (expr instanceof InfixExpr){
			return s_boolInfixOperators.contains(((InfixExpr)expr).getOperator());
		}
		else if (expr instanceof PrefixExpr){
			return s_boolPrefixOperators.contains(((PrefixExpr)expr).getOperator());
		}
		return false;
	}
}
