/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.declaration.JstArg;

/**
 * Immutable key for method lookup.
 */
public class MethodKey {
	private final String m_name;
	private final List<String> m_argTypeNames;
	private final boolean m_isStatic;
	private final boolean m_isVarArgs;
	private final String m_key;
	
	//
	// Constructors
	//
	/**
	 * Constructor for instance methods with 0 parameters
	 */
	public MethodKey(final String name){
		this(name, false, false);
	}
	
	/**
	 * Constructor for methods with 0 parameters
	 * @param name String
	 * @param isStatic boolean
	 */
	public MethodKey(final String name, boolean isStatic){
		this(name, isStatic, false);
	}
	
	/**
	 * Constructor for methods with fixed parameters
	 * @param name String
	 * @param isStatic boolean
	 * @param argTypeNames String...
	 */
	public MethodKey(final String name, boolean isStatic, final String... argTypeNames){
		this(name, isStatic, false, argTypeNames);
	}
	
	/**
	 * Constructor for methods with parameters
	 * @param name String
	 * @param isStatic boolean
	 * @param isVarArgs boolean
	 * @param argTypeNames String...
	 */
	public MethodKey(final String name, boolean isStatic, boolean isVarArgs, final String... argTypeNames){
		if (name == null){
			throw new AssertionError("name canot be null");
		}
		m_name = name;
		m_isStatic = isStatic;
		m_isVarArgs = isVarArgs;
		if (argTypeNames != null){
			m_argTypeNames = new ArrayList<String>(argTypeNames.length);
			for (String arg: argTypeNames){
				m_argTypeNames.add(arg);
			}
		}
		else {
			m_argTypeNames = Collections.emptyList();;
		}
		m_key = toString(this);
	}
	
	//
	// API
	//
	/**
	 * Answer the method name
	 * @return String
	 */
	public String getName(){
		return m_name;
	}
	
	public boolean isStatic(){
		return m_isStatic;
	}
	
	public boolean isVarArg(){
		return m_isVarArgs;
	}
	
	public List<String> getArgTypeNames(){
		if (m_argTypeNames == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_argTypeNames);
	}
	
	/**
	 * Helper method. Generate method key based on given <code>Method</code>
	 * @param mtd Method
	 * @return MethodKey
	 */
	public static MethodKey genMethodKey(final Method mtd){
		String[] params = new String[mtd.getParameterTypes().length];
		for (int i=0; i< mtd.getParameterTypes().length; i++){
			params[i] = mtd.getParameterTypes()[i].getSimpleName();
		}
		return new MethodKey(mtd.getName(), 
				Modifier.isStatic(mtd.getModifiers()), 
				mtd.isVarArgs(),
				params);
	}
	
	/**
	 * Helper method. Generate method key based on given <code>Class</code> 
	 * and method name. For non-overloaded methods only.
	 * @param type Class<?>
	 * @param mtdName String
	 * @return MethodKey
	 */
	public static MethodKey genMethodKey(final Class<?> type, final String mtdName){
		for (Method mtd: type.getMethods()){		
			if (mtd.getName().equals(mtdName)){
				return MethodKey.genMethodKey(mtd);
			}
		}
		throw new RuntimeException(String.format("Method %s not found in type %s", type.getName(), mtdName));
	}
	
	/**
	 * Helper method. Generate method key based on given <code>Class</code>, 
	 * method name and parameter types. 
	 * @param type Class<?>
	 * @param mtdName String
	 * @param paramTypes Class<?>
	 * @return MethodKey
	 */
	public static MethodKey genMethodKey(
			final Class<?> type, 
			final String mtdName, 
			final Class<?>... paramTypes){
		
		for (Method mtd: type.getMethods()){		
			if (mtd.getName().equals(mtdName) && 
				paramTypes.length == mtd.getParameterTypes().length){
				int i=0;
				boolean match=true;
				for(Class<?> classtype: mtd.getParameterTypes()){						
					if(!classtype.getName().equals(paramTypes[i].getName())){
						match = false;
					}
					i++;
				}
				if(match) {
					return MethodKey.genMethodKey(mtd);
				}
			}
		}
		throw new RuntimeException(String.format("Method %s not found in type %s", type.getName(), mtdName));
	}
	
	/**
	 * Helper method. Generate method key based on given <code>IJstMethod</code>
	 * @param IJstMethod IJstMethod
	 * @return MethodKey
	 */
	public static MethodKey genMethodKey(final IJstMethod jstMtd){
		
		if (jstMtd == null){
			return null;
		}
		
		MethodDeclaration astMtd = AstBindingHelper.getAstMethod(jstMtd);
		if (astMtd != null){
			return genMethodKey(astMtd);
		}

		String[] params = new String[jstMtd.getArgs().size()];
		JstArg arg;
		boolean hasVarArg = false;
		for (int i=0; i<params.length; i++){
			arg = jstMtd.getArgs().get(i);		
			params[i] = arg.getType().getName();
			if (arg.isVariable()){
				hasVarArg = true;
			}
		}

		return new MethodKey(jstMtd.getName().getName(), 
				jstMtd.isStatic(),
				hasVarArg,
				params);
	}

	/**
	 * Helper method. Generate method key based on given <code>MethodDeclaration</code>
	 * @param mtd MethodDeclaration
	 * @return MethodKey
	 */
	public static MethodKey genMethodKey(final MethodDeclaration mtd){
		if (mtd == null){
			return null;
		}
		String[] params = new String[mtd.parameters().size()];
		SingleVariableDeclaration varDec;
		boolean hasVarArg = false;
		for (int i=0; i<mtd.parameters().size(); i++){
			if(mtd.parameters().get(i) instanceof SingleVariableDeclaration){
				varDec = (SingleVariableDeclaration)mtd.parameters().get(i);			
				params[i] = varDec.getType().toString();
				if (varDec.isVarargs()){
					hasVarArg = true;
				}
			}else{
				TranslateCtx ctx = TranslateCtx.ctx();
				ctx.getLogger().logError(ctx.getErrorReporter(), 
					"Unknown type found while getting MethodKey from MethodDeclaration", 
					mtd);
			}
		}

		return new MethodKey(mtd.getName().getFullyQualifiedName(), 
				TranslateHelper.isStatic(mtd.modifiers()),
				hasVarArg,
				params);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != this.getClass()){
			return false;
		}
		return m_key.equals(toString((MethodKey)obj));
	}
	
	// DO NOT CHANGE since this value is used as the key in 
	// in equals(...)
	@Override
	public String toString(){
		return m_key;
	}
	
	@Override
	public int hashCode(){
		return m_key.hashCode();
	}
	
	public static String toString(MethodKey key){
		if (key == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (key.m_isStatic){
			sb.append("S:");
		}
		sb.append(key.m_name).append(":");
		for (String arg: key.m_argTypeNames){
			sb.append(arg).append(",");
		}
		if (key.m_isVarArgs){
			sb.append("V");
		}
		return sb.toString();
	}
}
