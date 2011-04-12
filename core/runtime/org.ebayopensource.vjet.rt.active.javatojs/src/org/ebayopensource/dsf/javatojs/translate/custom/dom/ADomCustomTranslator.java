/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.dom;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.mozilla.mod.javascript.Scriptable;

public class ADomCustomTranslator extends MetaDrivenCustomTranslator {
	
	//
	// Constructor
	//
	/**
	 * Constructor.
	 * @param customTypeMetaProvider ICustomTypeMetaProvider required
	 */
	public ADomCustomTranslator(){
		super(new ADomMeta(true));
	}
	
	@Override
	public boolean processMethodBody(MethodDeclaration astMtd, JstMethod jstMtd) {
		return false;
	}

	@Override
	protected CustomMethod getCustomMethod(
			final CustomType type, 
			final String mtdName, 
			final List<IExpr> args, 
			final IJstMethod jstMtd) {
		
		MethodKey key = getMethodKey(type, mtdName, jstMtd);
		CustomMethod cMtd = type.getCustomMethod(key);
		if (cMtd != null){
			return cMtd;
		}

		Collection<CustomMethod> cMtds = type.getCustomMethods(mtdName);
		if (cMtds != null && !cMtds.isEmpty()){
			return cMtds.iterator().next();
		}
		
		return null;
	}

	private MethodKey getMethodKey(CustomType type,
			String mtdName, IJstMethod jstMtd) {
		MethodKey key;
		String argTypeNames = getArgTypeNames(type.getJavaType(), mtdName, jstMtd);
		if (argTypeNames == null || argTypeNames.length() == 0) {
			key = new MethodKey(mtdName);
		} else {
			key = new MethodKey(mtdName, false, argTypeNames);
		}
		return key;
	}

	private String getArgTypeNames(Class<?> javaType, String mtdName, IJstMethod jstMtd) {
		StringBuffer argTypeNames = new StringBuffer();
		Method[] mtds = javaType.getMethods();
		for (Method m: mtds){
			if (m.getDeclaringClass().equals(Scriptable.class)) {
				continue;
			}
			if (!Modifier.isPublic(m.getModifiers())){
				continue;
			}
			if (isMatch(m, mtdName, jstMtd)) {
				int length = m.getParameterTypes().length;
				for (Class<?> c : m.getParameterTypes()) {
					argTypeNames.append(c.getName());
					if (--length > 0) {
						argTypeNames.append("#");
					}
				}
				break;
			}
		}
		return argTypeNames.toString();
	}

	private boolean isMatch(Method m, String mtdName, IJstMethod jstMtd) {
		if (jstMtd == null) {
			// If jstMtd is null all we can match on is method name
			return m.getName().equals(mtdName);
		}
		if (!m.getName().equals(jstMtd.getName().getName())) {
			return false;
		}
		Class<?>[] params = m.getParameterTypes();
		List<JstArg> args = jstMtd.getArgs();
		if (args.size() != params.length) {
			return false;
		}
		for (JstArg arg : args) {
			IJstType argType = arg.getType();
			if (argType == null) {
				return false;
			}
			if (!hasType(argType.getSimpleName(), params)) {
				return false;
			}
		}
		return true;
	}

	private boolean hasType(String simpleName, Class<?>[] params) {
		for (Class<?> c : params) {
			if (c.getSimpleName().equals(simpleName)) {
				return true;
			}
		}
		return false;
	}
}
