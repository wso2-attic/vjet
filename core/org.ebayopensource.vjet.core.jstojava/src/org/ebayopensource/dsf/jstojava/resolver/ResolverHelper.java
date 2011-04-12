/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jstojava.resolver;
//
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.IJstTypeReference;
//import org.ebayopensource.dsf.jst.JstSource;
//import org.ebayopensource.dsf.jst.declaration.JstArray;
//import org.ebayopensource.dsf.jst.declaration.JstCache;
//import org.ebayopensource.dsf.jst.declaration.JstFactory;
//import org.ebayopensource.dsf.jst.declaration.JstParamType;
//import org.ebayopensource.dsf.jst.declaration.JstType;
//import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
//import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
//import org.ebayopensource.dsf.jstojava.translator.JstUtil;
//import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
//import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
//import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
//
//public class ResolverHelper {
//
//	
//	public static IJstTypeReference getType(TranslateCtx ctx, Literal literal) {
//		assert literal != null;
//		String typeName = JstUtil.getCorrectName(literal);
//		// no beginLine and beginColumn in literal object => they are hardcoded
//		// to -1
//		JstSource source = new JstSource(JstSource.JS, -1, -1, typeName
//				.length(), literal.sourceStart + 1, literal.sourceStart
//				+ typeName.length());
//		
//		return getType(ctx,typeName,source);
//	}
//	
//	public static IJstTypeReference getType(TranslateCtx ctx, String typeName, JstSource source) {
//		IJstType type = TranslateHelper.getJstType(TranslateHelper.findType(ctx,typeName));		
//		// TODO - Justin refactor remove these logic breaks VJET
////		if (!type.getStatus().hasDecl()) {
////			try {
////				//tmp logic to on-demain load the missing type
////				URL url =ctx.getSourceLocator().getSourceUrl(type.getName());
////				if (url!=null) {
////					type = (JstType)new VjoParser()
////						.parse(ctx.getGroup(), url, ctx.isSkiptImplementation());
////				}
////			}
////			catch (Exception e) {
////			}
////		}
//		return TranslateHelper.createRef(type, source);
//	}
//	
//	
//
//	/**
//	 * Find unresolved type
//	 * 
//	 * @param ctx
//	 * @param name
//	 * @return
//	 */
//	
//	// TODO move this to Resolution Utilities should not be done during translation
//	public static IJstType findType(TranslateCtx ctx, String name) {
//		name = name.trim();
//		if (name.endsWith("[]")) {
//			return new JstArray(findType(ctx, name.substring(0,
//					name.length() - 2)));
//		}
//		String params = null;
//		int start = name.indexOf("<");
//		if (start>1 && name.charAt(name.length()-1)=='>') {
//			params = name.substring(start+1,name.length()-1);
//			name = name.substring(0, start);
//		}
//		IJstType type = null;
//		JstType currentType = ctx.getCurrentType();
//		String[] names = name.split("\\.");
//		if (name.indexOf(".") == -1) { // short name
//			if (currentType != null) {
//				if (name.equals(currentType.getSimpleName())) {
//					return currentType;
//				}
//				for (IJstType importedType : currentType.getImports()) {
//					if (name.equals(importedType.getSimpleName())) {
//						return importedType;
//					}
//				}
//				//Look for inner types
//				type = searchInnerTypes(currentType, name);
//				if (type != null) {
//					return type;
//				}
//			}
//		} else if (currentType != null && names.length==2) { //check in otypes
//			IJstType otype = TranslateHelper.getTypeFromInactive(name, currentType);
//			if (otype!=null) {
//				return otype;
//			}
//		}
//
//		
//		type = JstCache.getInstance().getType(name);
//		if (type == null && currentType != null) {
//			//Look up inner types
//			type = searchInnerTypes(currentType, name);
//			if (type == null) {
//				for (IJstType importedType : currentType.getImports()) {
//					type = searchInnerTypes(importedType, name);
//					if (type != null) {
//						break;
//					}
//				}
//			}
//		}
//		if (type == null) {
//			type = JstFactory.getInstance().createJstType(name, true);
//			if (params!=null) {
//				return getJstWithArgs(ctx, (JstType)type, params);
//			}
//			//addParamsToType(ctx, type, params);
//		}
//		return type;
//
//	
//	}
//	
//	public static JstTypeWithArgs getJstWithArgs(TranslateCtx ctx, JstType type, String params) {
//		if (params==null || "".equals(params)) 
//			return null;
//		JstTypeWithArgs jstType = new JstTypeWithArgs(type);
//		String[] pArr = params.split(",");
//		for (int i=0; i<pArr.length; i++) {
//			String param = pArr[i];
//			boolean isUpper = param.contains(" extends ");
//			boolean isLower = param.contains(" super ");
//			if (isUpper || isLower) {
//				String[] name = (isUpper)? param.split(" extends ") :  param.split(" super ");
//				if ("?".equals(name[0].trim())) {
//					jstType.addArgType(new JstWildcardType(findType(ctx, name[1]),isUpper));
//				} else {
//					JstParamType pType = new JstParamType(name[0]);
//					pType.addBound(new JstWildcardType(findType(ctx, name[1]),isUpper));
//					jstType.addArgType(pType);
//				}
//			} else {
//				jstType.addArgType(findType(ctx, param));
//			}
//		}
//		return jstType;
//	}
//
//	private static IJstType searchInnerTypes(IJstType currentType, String name) {
//		if (currentType != null) {
//			for (IJstType innType : currentType.getEmbededTypes()) {
//				String tmp;
//				boolean lookFurther = false;
//				if (name.indexOf(".") > -1) {
//					tmp = name.substring(0, name.indexOf("."));
//					lookFurther = true;
//				} else {
//					tmp = name;
//				}
//				if (tmp.equals(innType.getSimpleName())) {
//					if (lookFurther) {
//						IJstType type = searchInnerTypes(innType, name.substring(name.indexOf(".")+1, name.length()));
//						if (type != null) {
//							return type;
//						}
//					} else {
//						return innType;
//					}
//				}
//			}
//		}
//		return null;
//	}
//	
//}
