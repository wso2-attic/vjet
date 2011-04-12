/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.util;

import java.util.List;

import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.VjoTranslateHelper;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class AutoBoxer {
	
	// Singleton
	private static AutoBoxer s_instance = new AutoBoxer();
	private AutoBoxer(){};
	public static AutoBoxer getInstance(){
		return s_instance;
	}
	
	//
	// API
	//
	/**
	 * Answer whether the given expression needs boxing with given expected type.
	 * @param expr IExpr
	 * @param expectedType IJstType
	 * @return boolean
	 */
	public boolean needAutoBoxing(
			final IExpr expr, 
			final IJstType expectedType) {
		
		if (expr == null){
			return false;
		}
		
		if (expr instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)expr;
			return needAutoBoxing(assignExpr.getExpr(), assignExpr.getResultType());
		}
		
		if (expr instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdExpr = (MtdInvocationExpr)expr;
			if (mtdExpr.getMethodIdentifier() instanceof JstIdentifier){
				IJstNode binding = ((JstIdentifier)mtdExpr.getMethodIdentifier()).getJstBinding();
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
						if (needAutoBoxing(actualArg, expectedArg.getType())){
							return true;
						}
					}
				}
			}
		}

		if (expr instanceof ObjCreationExpr
				|| isNull(expr)) {
			return false;
		}

		IJstType toType = getExpectedType(expectedType);
		if (forceBoxing(expr, toType)){
			return true;
		}
		
		return needAutoBoxing(expr.getResultType(), toType);
	}
	
	/**
	 * Answer whether the given expression needs boxing with given expected type.
	 * @param expr IExpr
	 * @param expectedType IJstType
	 * @return boolean
	 */
	public boolean needAutoBoxing(
			final IJstType actualType, 
			final IJstType expectedType) {
		
		if (actualType == null 
				|| expectedType == null
				|| actualType.getSimpleName() == null
				|| expectedType.getSimpleName() == null){
			return false;
		}
		
		if ((DataTypeHelper.isNumericPrimitiveType(actualType)
				|| DataTypeHelper.isCharPrimitiveType(actualType))
			&& (DataTypeHelper.isNumericWrapperType(expectedType)
				|| TranslateHelper.isObjectType(expectedType)
				|| expectedType instanceof JstParamType)){
			return true;
		}

		if (DataTypeHelper.isBooleanPrimitiveType(actualType)
				&& (DataTypeHelper.isBooleanWrapperType(expectedType)
					|| TranslateHelper.isObjectType(expectedType)
					|| expectedType instanceof JstParamType)){
			return true;
		}

		if ((DataTypeHelper.isNumericPrimitiveType(actualType)
				|| DataTypeHelper.isCharPrimitiveType(actualType))
			&& (DataTypeHelper.isCharWrapperType(expectedType)
				|| TranslateHelper.isObjectType(expectedType)
				|| expectedType instanceof JstParamType)){
			return true;
		}

		return false;
	}
	
	/**
	 * First it checks whether the given expression needs boxing with given expected type.
	 * If answer is YES, it does auto-boxing for the given expression with expected type.
	 * @param expr IExpr
	 * @param expectedType IJstType
	 * @return IExpr the new expression after boxing, or the original expression 
	 * if boxing is not performed
	 */
	public IExpr autoBoxing(
			final IExpr expr, 
			final IJstType expectedType){
		
		if (!needAutoBoxing(expr, expectedType)){
			return expr;
		}
		else {
			return box(expr, expectedType);
		}
	}
	
	//
	// Private
	//
	private boolean forceBoxing(final IExpr expr, final IJstType expectedType){
		if (expr == null || expectedType == null){
			return false;
		}
		
		if (expr instanceof JstArrayInitializer){
			return true;
		}
		
		return false;
	}
	
	private IExpr box(
			final IExpr expr, 
			final IJstType expectedType){
		
		if (expr instanceof JstArrayInitializer){
			return box((JstArrayInitializer)expr, expectedType);
		}
		else if (expr instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)expr;
			IExpr e = assignExpr.getExpr();
			if (needAutoBoxing(e, expectedType)){
				IExpr newExpr = box(e,expectedType);
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
						if (needAutoBoxing(actualArg, expectedArg.getType())){
							IExpr newExpr = box(actualArg, expectedArg.getType());
							if (newExpr != actualArg){
								mtdExpr.setArg(i, newExpr);
							}
						}
					}
					
				}
			}
			if (needAutoBoxing(expr.getResultType(), expectedType)){
				return doIt(expr, expectedType);
			}
			else {
				return expr;
			}
		}
		else {
			return doIt(expr, expectedType);
		}
	}
	
	private IExpr box(final JstArrayInitializer arrayInitializer, final IJstType expectedType ){
		List<IExpr> exprs = arrayInitializer.getExprs();
		if (exprs == null){
			return arrayInitializer;
		}
		
		IExpr expr;
		for (int i=0; i<exprs.size(); i++){
			expr = exprs.get(i);
			IExpr newExpr = autoBoxing(expr,expectedType);
			if (newExpr != expr){
				exprs.set(i, newExpr);
			}
		}
		
		return arrayInitializer;
	}
	
	private IExpr doIt(final IExpr expr, final IJstType expectedType){
		
		IJstNode parentNode = expr.getParentNode();
		
		IJstType toType = getExpectedType(expectedType);
		if (toType instanceof JstParamType
			|| TranslateHelper.isObjectType(toType)){
			String name = DataTypeHelper.getWrapperTypeName(expr.getResultType().getSimpleName(), true);
			if (parentNode.getRootType() instanceof JstType){
				toType = JstCache.getInstance().getType(name, true);
				JstType ownerType = (JstType)parentNode.getRootType();
				ownerType.addImport(toType);
			}
		}
		
		String name = null;
		//Check for native type mapping
		String origialName = toType.getName();
		if (DataTypeHelper.getNativeType(origialName) != null 
				|| DataTypeHelper.isJavaMappedToNative(origialName)) {
			name = DataTypeHelper.getTypeName(origialName);
		} else if (parentNode instanceof BaseJstNode){
			JstIdentifier jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(toType, (BaseJstNode)parentNode);
			name = ( (jstQualifier==null) ? "":(jstQualifier.getName() + ".") ) + toType.getSimpleName();
		}

		if (name == null){
			return expr;
		}

		JstIdentifier mtdIdentifier = new JstIdentifier(name);
		// TODO get constructor from Java Wrapper type when it is available
		mtdIdentifier.setJstBinding(new JstConstructor(
			new JstArg(expr.getResultType(), "value", false)));
		MtdInvocationExpr mtdCall = new MtdInvocationExpr(mtdIdentifier);
		mtdCall.setResultType(toType);
		mtdCall.addArg(expr);
		
		ObjCreationExpr objCreationExpr = new ObjCreationExpr(mtdCall);
		objCreationExpr.setParent(parentNode);	
		
		return objCreationExpr;
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
	
	private boolean isNull(final IExpr expr){
		return expr == null || "null".equals(expr.toExprText());
	}
}
