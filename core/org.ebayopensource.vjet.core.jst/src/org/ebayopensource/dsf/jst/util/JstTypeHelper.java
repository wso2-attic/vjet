/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstMixedType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.token.IExpr;

public class JstTypeHelper {

	public static JstType getJstType(IJstType type) {
		if (type == null) {
			return null;
		}
		if (type instanceof JstType) {
			return (JstType) type;
		}
		if (type instanceof JstTypeWithArgs) {
			return getJstType(((JstTypeWithArgs) type).getType());
		}
		return null;
	}

	public static IJstType getPrimitiveType(final IExpr expr) {
		if (expr == null || expr.getResultType() == null) {
			return null;
		}
		IJstType type = expr.getResultType();
		if (DataTypeHelper.isPrimitiveType(type)) {
			return type;
		}
		return DataTypeHelper.getPrimitivetype(type);
	}

	public static IJstType getExprType(IExpr expr) {

		if (expr == null || expr.getResultType() == null) {
			return null;
		}

		IJstType actualType = expr.getResultType();
		if (expr instanceof MtdInvocationExpr) {
			MtdInvocationExpr mtdInvocation = ((MtdInvocationExpr) expr);
			IExpr mtdIdentifier = mtdInvocation.getMethodIdentifier();
			if (mtdIdentifier != null) {
				actualType = mtdIdentifier.getResultType();
				if (actualType instanceof JstParamType) {
					IExpr qualifier = mtdInvocation.getQualifyExpr();
					if (qualifier != null) {
						actualType = qualifier.getResultType();
						if (actualType instanceof JstTypeWithArgs) {
							actualType = ((JstTypeWithArgs) actualType)
									.getArgType();
						}
					}
				}
			}
		}
		// TODO
		return actualType;
	}

	/**
	 * Answer true if bType is a super type or interface of aType
	 * 
	 * @param aType
	 * @param bType
	 * @return boolean
	 */
	public static boolean isTypeOf(IJstType aType, IJstType bType) {
		if (aType instanceof IJstRefType && "Function".equals(bType.getName())) {
			return true;
		}
		return isSubTypeOf(aType, bType) || isImplementerOf(aType, bType)
				|| "Object".equals(bType.getName())
				|| "Object".equals(bType.getSimpleName());
	}

	//
	// Private
	//
	/**
	 * Answer true if bType is a super type of aType
	 * 
	 * @param aType
	 * @param bType
	 * @return boolean
	 */
	// TODO: make it complete
	private static boolean isSubTypeOf(IJstType aType, IJstType bType) {
		for (IJstType t : aType.getExtends()) {
			if (t == bType) {
				return true;
			}
			if (isSubTypeOf(t, bType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Answer true if bType is an interface of aType
	 * 
	 * @param aType
	 * @param bType
	 * @return boolean
	 */
	// TODO: make it complete
	private static boolean isImplementerOf(IJstType type, IJstType bType) {
		if (type == null || bType == null) {
			return false;
		}
		IJstType aType = type;
		if (type instanceof JstTypeWithArgs) {
			aType = ((JstTypeWithArgs) type).getType();
		}
		for (IJstType t : aType.getSatisfies()) {
			if (t == bType) {
				return true;
			}
			if (isImplementerOf(t, bType)) {
				return true;
			}
		}
		for (IJstType t : aType.getExtends()) {
			if (isImplementerOf(t, bType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTemplateType(IJstType type, IJstMethod method) {
		if (type == null || method == null) {
			return false;
		}

		if (isTemplateType(type, method.getParamTypes())) {
			return true;
		} else if (method.getRootType() != null) {
			return isTemplateType(type, method.getRootType().getParamTypes());
		}

		return false;
	}

	public static boolean isTemplateType(IJstType type,
			List<JstParamType> paramTypes) {
		if (type == null || type.getName() == null || paramTypes == null) {
			return false;
		}
		for (JstParamType ptype : paramTypes) {
			if (type.getName().equals(ptype.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Resolve parameter type e.g. E in ArrayList<E> to real type, e.g. String
	 * in ArrayList<String>
	 * 
	 * @param paramType
	 *            - parameter type, e.g. <E>
	 * @param parentType
	 *            - type with arguments, e.g. ArrayList<String>
	 * @return IJstType - resolved param type, e.g. String
	 * 
	 *         paramType can be a method or property in the future and this
	 *         function will get the method return type or property type.
	 * 
	 *         -- TODO: Future implementation should handle the following
	 *         scenario:
	 * 
	 *         For example:
	 * 
	 *         1. Two generic class B<X, Y, Z> and A<E, F> 2. B<X, Y, Z>
	 *         inherits A<Y, Z> or A<String, Integer> 3. A has a method foo()
	 *         returns E type 4. B<boolean, String, Integer> is instanciated to
	 *         call foo() 5. How to resolve E? E should be resolved to String
	 *         type
	 */
	public static IJstType resolveTypeWithArgs(IJstNode mtdOrPty,
			IJstType parentType) {

		IJstType paramType = null;

		if (mtdOrPty instanceof IJstMethod) {
			paramType = ((IJstMethod) mtdOrPty).getRtnType();
		} else if (mtdOrPty instanceof IJstProperty) {
			paramType = ((IJstProperty) mtdOrPty).getType();
		}

		if (paramType instanceof JstParamType) {
			if (parentType instanceof JstTypeWithArgs) {
				return getParamType((JstParamType) paramType,
						(JstTypeWithArgs) parentType);
			} else {
				List<JstParamType> paramTypeList = parentType.getParamTypes();

				// missing actual type argument, default to Object
				if (paramTypeList != null && !paramTypeList.isEmpty()) {
					return JstCache.getInstance().getType("Object");
				}
			}
		}

		return paramType;
	}

	private static IJstType getParamType(JstParamType paramType,
			JstTypeWithArgs typeWithArgs) {

		IJstType type = typeWithArgs.getParamArgType(paramType);

		if (type != null) {
			return type;
		} else {
			return paramType;
		}

		/*
		 * int index = next.getType().getParamTypes().indexOf(paramType); if
		 * (index >= 0 && next.getArgTypes().size() > 0) { return
		 * next.getArgTypes().get(index); } else if(typeWithArgs.getExtend() !=
		 * null && typeWithArgs.getExtend() instanceof JstTypeWithArgs){ final
		 * IJstType resolvedNextType = getParamType(paramType, typeWithArgs,
		 * (JstTypeWithArgs)typeWithArgs.getExtend()); //modification by roy
		 * //if the ext resolved type is a JstParamType, the root
		 * JstTypeWithArgs should have the correct mapping //otherwise, returns
		 * anyway if(resolvedNextType instanceof JstParamType){ index =
		 * typeWithArgs
		 * .getType().getParamTypes().indexOf((JstParamType)resolvedNextType);
		 * if (index >= 0 && typeWithArgs.getArgTypes().size() > 0) { return
		 * typeWithArgs.getArgTypes().get(index); } } else{ return
		 * resolvedNextType; } } //TODO should interfaces param types be
		 * considered as well?
		 * 
		 * return paramType;
		 */
	}

	/**
	 * Return the actual IJstType for input method argument. If the method
	 * argument type is defined as generic type param, then the parameter type
	 * e.g. 'E' will be replaced by the corresponding real type e.g. 'String'
	 * from the typeWithArgs type.
	 * 
	 * @param arg
	 *            - JstARg
	 * @param typeWithArgs
	 *            - JstTypeWithArgs
	 * @return List<IJstType>
	 * 
	 *         For example, type GenericTest<E> has method bar(int, E, boolean)
	 *         as mtd parameter,
	 * 
	 *         vjo.ctype('vjo.vjet.GenericTest<E>') //< public .props({ })
	 *         .protos({ >>public E bar(int i, E a, boolean t) bar:function(i,
	 *         a, t) { return null; } }) .endType();
	 * 
	 *         With GenericTest<String> as typeWithArgs parameter, the return
	 *         array is [int, String, boolean]
	 */
	public static IJstType resolveJstArgType(JstArg arg, IJstType typeWithArgs) {

		IJstType argType = arg.getType();

		if (argType instanceof JstParamType
				&& typeWithArgs instanceof JstTypeWithArgs) {
			argType = getParamType((JstParamType) argType,
					(JstTypeWithArgs) typeWithArgs);
			// Modified by Eric.Ma 20100408
		} else if (argType instanceof JstTypeWithArgs
				|| argType instanceof JstType
				|| argType instanceof JstParamType) {
			return argType;
			// End of Modification
		} else {
			List<JstParamType> paramTypeList = typeWithArgs.getParamTypes();

			// missing actual type argument, default to Object
			if (paramTypeList != null && !paramTypeList.isEmpty()) {
				argType = JstCache.getInstance().getType("Object");
			}
		}

		return argType;
	}

	public static List<IJstProperty> getDeclaredProperties(
			List<IJstProperty> properties) {
		List<IJstProperty> newList = new ArrayList<IJstProperty>();
		boolean useNew = false;
		for (IJstProperty itm : properties) {
			if (itm instanceof ISynthesized) {
				useNew = true;
			} else {
				newList.add(itm);
			}
		}
		return (useNew) ? newList : properties;
	}

	public static List<? extends IJstMethod> getDeclaredMethods(
			List<? extends IJstMethod> mtds) {
		List<IJstMethod> newList = new ArrayList<IJstMethod>();
		boolean useNew = false;
		for (IJstMethod itm : mtds) {
			if (itm instanceof ISynthesized) {
				useNew = true;
			} else {
				newList.add(itm);
			}
		}
		return (useNew) ? newList : mtds;
	}

	/**
	 * Answer a list of all constructors defined via signature The list includes
	 * overloaded constructors but not the dispatcher constructor.
	 * 
	 * @param IJstType
	 * @return List<? extends IJstMethod>
	 */
	public static List<? extends IJstMethod> getSignatureConstructors(
			IJstType type) {
		if (type == null || type.getConstructor() == null) {
			return Collections.emptyList();
		}

		List<IJstMethod> constructors = new ArrayList<IJstMethod>();
		IJstMethod constructor = type.getConstructor();
		if (constructor.isDispatcher()) {
			constructors.addAll(constructor.getOverloaded());
		} else {
			constructors.add(constructor);
		}
		return constructors;
	}

	/**
	 * Answer a list of all methods defined via signature. The list includes
	 * overloaded methods but not the dispatch method.
	 * 
	 * @param IJstType
	 * @return List<? extends IJstMethod>
	 */
	public static List<? extends IJstMethod> getSignatureMethods(IJstType type) {
		if (type == null) {
			return Collections.emptyList();
		}
		List<IJstMethod> methods = new ArrayList<IJstMethod>();
		for (IJstMethod mtd : type.getMethods()) {
			if (mtd.isDispatcher()) {
				methods.addAll(mtd.getOverloaded());
			} else {
				methods.add(mtd);
			}
		}
		return methods;
	}

	/**
	 * Answer a list of all methods defined via signature. The list includes
	 * overloaded methods but not the dispatch method.
	 * 
	 * @param IJstMethod
	 * @return List<? extends IJstMethod>
	 */
	public static List<? extends IJstMethod> getSignatureMethods(IJstMethod mtd) {
		if (mtd == null) {
			return Collections.emptyList();
		}
		List<IJstMethod> methods = new ArrayList<IJstMethod>();
		if (mtd.isDispatcher()) {
			methods.addAll(mtd.getOverloaded());
		} else {
			methods.add(mtd);
		}
		return methods;
	}

	/**
	 * Answer a list of all methods defined via signature The list includes
	 * overloaded methods but not the dispatch method. The list includes base
	 * type(s) methods if "recursive" is true.
	 * 
	 * @param type
	 *            IJstType
	 * @param recursive
	 *            boolean if true, list includes base type(s) signature methods
	 * @return a list of all methods defined via signature
	 */
	public static List<? extends IJstMethod> getSignatureMethods(IJstType type,
			boolean isStatic, boolean recursive) {
		if (type == null) {
			return Collections.emptyList();
		}
		List<IJstMethod> methods = new ArrayList<IJstMethod>();
		if (type instanceof JstMixedType) {
			JstMixedType m = (JstMixedType) type;
			for(IJstType t: m.getMixedTypes()){
				 getMethodSignatures(t, isStatic,
							recursive, methods);
			}
		} else {
			 getMethodSignatures(type, isStatic,
					recursive, methods);
		}
		return methods;
	}

	private static void getMethodSignatures(IJstType type,
			boolean isStatic, boolean recursive, List<IJstMethod> methods) {
		
		for (IJstMethod mtd : type.getMethods(isStatic, recursive)) {
			if (mtd.isDispatcher()) {
				methods.addAll(mtd.getOverloaded());
			} else {
				methods.add(mtd);
			}
		}
	}

	public static boolean hasSameRootType(IJstType type1, IJstType type2) {
		return getRootType(type1) == getRootType(type2);
	}

	/**
	 * Temp helper method to get the package for inner types since JstType
	 * returns incorrect package for inner type .
	 * 
	 * @param type
	 * @return
	 */
	public static JstPackage getTruePackage(IJstType type) {

		return getRootType(type).getPackage();
	}

	private static IJstType getRootType(IJstType type) {
		IJstType parent = type;
		while (parent != null) {
			type = parent;
			parent = type.getOuterType();
		}
		return type;
	}

	public static List<IJstType> getRtnTypes(JstMethod meth) {
		List<IJstType> list;
		if (!meth.isDispatcher()) {
			list = new ArrayList<IJstType>(1);
			list.add(meth.getRtnType());
		} else {
			List<IJstMethod> methods = meth.getOverloaded();
			list = new ArrayList<IJstType>(methods.size());
			for (IJstMethod mtd : methods) {
				list.add(mtd.getRtnType());
			}
		}
		return list;
	}

	public static IJstType mixin(IJstType type, IJstType mtype) {
		// if (!mtype.isMixin()) {
		// return type;
		// }
		JstType newType = JstFactory.getInstance().createJstType(
				type.getPackage(), type.getSimpleName(), false);

		newType.addExtend(type);
		newType.addMixin(new JstTypeReference(mtype));

		return newType;
	}

	public static IJstType make(IJstType type) {

		JstType newType = JstFactory.getInstance().createJstType(
				type.getPackage(), type.getSimpleName(), false);

		if (type.isClass()) {
			newType.addExtend(new JstTypeReference(type));
		} else if (type.isInterface()) {
			newType.addSatisfy(new JstTypeReference(type));
		}

		return newType;
	}

	public static boolean isConstructor(IJstNode node) {

		if (node instanceof JstConstructor) {
			return true;
		} else if (node instanceof JstMethod) {
			JstMethod method = (JstMethod) node;
			if (method != null) {
				String mtdName = method.getName().getName();
				if (mtdName != null
						&& mtdName.startsWith(JstConstructor.CONSTRUCTS)
						&& mtdName.endsWith(JstMethod.OVLD)) {
					return true;
				}
			}
		}

		return false;
	}

	public static void populateMethod(JstMethod to, IJstMethod from) {
		List<IJstAnnotation> annos = from.getAnnotations();
		for (IJstAnnotation anno : annos) {
			to.addAnnotation((JstAnnotation) anno);
		}
		if (to.getArgs() != null) {
			to.getArgs().clear();
		}
		List<JstArg> args = from.getArgs();
		for (JstArg arg : args) {
			to.addArg(new JstArg(arg.getType(), arg.getName(),
					arg.isVariable(), arg.isOptional(), arg.isFinal()));
		}
		if (to.getParamTypes() != null) {
			to.getParamTypes().clear();
		}
		List<String> params = from.getParamNames();
		for (int i = 0; i < params.size(); i++) {
			to.addParam(params.get(i));
		}
		to.setRtnRefType(from.getRtnTypeRef());
		to.setReturnOptional(from.isReturnTypeOptional());
	}

	public static void populateMethod(JstMethod to, IJstMethod from,
			boolean keepArgNames) {
		if (!keepArgNames) {
			populateMethod(to, from);
			return;
		}

		List<IJstAnnotation> annos = from.getAnnotations();
		for (IJstAnnotation anno : annos) {
			to.addAnnotation((JstAnnotation) anno);
		}

		List<String> argNameList = new ArrayList<String>();
		List<JstArg> toArgs = to.getArgs();

		if (toArgs != null) {
			for (JstArg arg : toArgs) {
				argNameList.add(arg.getName());
			}

			toArgs.clear();
		}
		List<JstArg> args = from.getArgs();
		int idx = 0;
		for (JstArg arg : args) {

			String argName = null;

			if (idx < argNameList.size()) {
				argName = argNameList.get(idx);
			} else {
				break;
			}

			if (argName == null || argName.length() == 0) {
				argName = arg.getName();
			}
			to.addArg(new JstArg(arg.getType(), argName, arg.isVariable(), arg
					.isOptional(), arg.isFinal()));
			idx++;
		}
		if (to.getParamTypes() != null) {
			to.getParamTypes().clear();
		}
		List<String> params = from.getParamNames();
		for (int i = 0; i < params.size(); i++) {
			to.addParam(params.get(i));
		}
		to.setRtnRefType(from.getRtnTypeRef());
		to.setReturnOptional(from.isReturnTypeOptional());
	}

	public static IJstRefType getJstTypeRefType(IJstType type) {
		IJstRefType typeRefType = JstCache.getInstance().getTypeRefType(type);
		if (typeRefType == null) {
			typeRefType = JstFactory.getInstance().createJstTypeRefType(type);
		}
		return typeRefType;
	}

}
