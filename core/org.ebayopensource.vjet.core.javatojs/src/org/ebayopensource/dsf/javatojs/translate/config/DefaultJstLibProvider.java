/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.jsnative.global.ObjLiteral;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.JstLib;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.lib.IJstLibProvider;
import org.ebayopensource.vjo.VjoTypes;
import org.ebayopensource.vjo.lib.LibManager;
/**
 * Default library provider.  
 * 
 *
 */
public class DefaultJstLibProvider implements IJstLibProvider{
	
	
	private Map<String, IJstLib> m_jstLibMap = new HashMap<String,IJstLib>();


	
	/**
	 * TODO: re-factor this, don't have the logic for adding library 
	 * @param ctx
	 */
	public DefaultJstLibProvider(){

		LibManager libMgr = LibManager.getInstance();
		// ORDER IS VERY IMPORTANT HERE
		// MUST LOAD PRIMITIVES FIRST
		add(libMgr.getJavaPrimitiveLib());
		IJstLib jsnativeLib = libMgr.getJsNativeGlobalLib();
		IJstLib browserLib = libMgr.getBrowserTypesLib();
		IJstLib vjoLib = libMgr.getVjoSelfDescLib();
		IJstLib vjoJavaLib = libMgr.getVjoJavaLib();
		add(jsnativeLib);
		add(browserLib);
		add(vjoLib);
		add(vjoJavaLib);
		// NOW TYPES WHICH USE PRIMITIVES CAN BE LOADED
		// TODO this should be part of lib
		JstCache.getInstance().addType(VjoTypes.VJO_JAVA_LANG_UTIL);
		
		//Add Vjo base types(vjo.Object, vjo.Class & vjo.Enum)
		//add(libMgr.getVjoBaseTypesLib());
//		VjoBaseJstLib.getInstance().getTypes(); //This would add types to cache
	}
	
	/**
	 * Add library to library list
	 */
	public DefaultJstLibProvider add(IJstLib jstLib) {
		if(jstLib ==null) {
			return this;
		}
		processLibrary(jstLib);
		m_jstLibMap.put(jstLib.getName(),jstLib);	
		JstCache.getInstance().addLib(jstLib);
		return this;
	}
	
	
	/**
	 * Clear the library list.  removes all the library form the list
	 */
	public void clearAll() {		
		m_jstLibMap.clear();
	}

	/**
	 * Get all library
	 */
	public List<IJstLib> getAll() {		
		return new ArrayList<IJstLib>(m_jstLibMap.values());
		/*List<IJstLib> list = new ArrayList<IJstLib>();
		list.addAll(m_jstLibMap.values());
		return list;*/
		
	}

	/**
	 * Remove the given library name from the list
	 */
	public IJstLib remove(String libName) {	
		return m_jstLibMap.remove(libName);		
	}
	private void replace(IJstLib jstLib){
		m_jstLibMap.put(jstLib.getName(), jstLib);
	}
	
	private static final String ObjLiteralType = ObjLiteral.class.getName();
	/**
	 * Adds a library to the translator cache.
	 * @param lib IJstLib
	 * @return TranslationController
	 */
	private  void processLibrary(final IJstLib lib) {
		TranslateCtx ctx = TranslateCtx.ctx();
		if (lib != null) {
			JstLib jstLib = (JstLib) lib;
			// For JS native library we need to set the package name for 
			// JstTypes before loading in the cache since the JS native object
			// do not have package name. The package name in this case is saved in the 
			// alias field at the time JstType is created for JS native objects.
			if (LibManager.JS_NATIVE_LIB_NAME.equals(lib.getName()) || LibManager.JS_NATIVE_GLOBAL_LIB_NAME.equals(lib.getName())) {
				jstLib = new JstLib(lib.getName());
				for (JstType jstType : lib.getAllTypes(true)) {
					String fullName = jstType.getAlias();
					if (fullName != null && fullName.lastIndexOf('.') > 0) {
						if (jstType.getName().equals("String")) {
							modifyStringType(jstType);
						} else {
							jstType.setPackage(
									new JstPackage(fullName.substring(0, fullName.lastIndexOf('.'))));
						}
					} else {
						//TODO: How/where to handle error
						//m_directErrors.add(new TranslateError(TranslateMsgId.GENERAL_ERROR,
						//	"JstType for " + LibManager.JS_NATIVE_LIB_NAME + 
						//	" is invalid. Skipped adding to JstCache."));
						return;
					}
					for (IJstProperty pty : jstType.getProperties()) {
						JstMethod getter = getterMethod(pty);
						jstType.addMethod(getter);
						JstMethod setter = setterMethod(pty);
						jstType.addMethod(setter);
					}
					if (!jstType.getAlias().equals(jstType.getName())) {
//						String alias = jstType.getName();
						jstType.setSimpleName(TranslateHelper.getShortName(jstType.getAlias()));
//						jstType.setAlias(alias);
					}
//					System.out.println("+++ Adding JstType for " +  jstType.getName());
					jstLib.addType(jstType);
					if (ObjLiteralType.equals(jstType.getName())) {
						ctx.setCustomAttr(jstType, CustomAttr.JAVA_ONLY);
					}
					else {
						ctx.setCustomAttr(jstType, CustomAttr.MAPPED_TO_JS);
					}
//					replace(jstLib);					
				}
			}
		}
		return;
	}
	
	private void modifyStringType(JstType jstType) {
		/*
		for (IJstMethod mtd : jstType.getMethods()) {
			System.out.println(mtd.getOriginalName());
			if (mtd instanceof JstMethod) {
				String name = mtd.getOriginalName();
				JstMethod method = (JstMethod)mtd;
				JstType intType = JstCache.getInstance().getType("int");
				if (name.equals("charAt")) {
					//Update args
					method.updateArgType(0, intType);
					
					//Update return type
					method.setRtnType(JstCache.getInstance().getType("char"));
				} else if (name.equals("substring")) {
					int i = 0;
					for (JstArg arg : method.getArgs()) {
						//Update args
						method.updateArgType(i, intType);
						i++;
					}
					//Update return type
					//method.setRtnType(JstCache.getInstance().getType("char"));
				} else if (name.equals("concat")) {
					
				} 
			}
		}
		*/
	}

	private JstMethod getterMethod(IJstProperty pty) {
		JstMethod mtd = new JstMethod("get"+CapitalizeFirstLetter(pty.getName().toString()),
				new JstModifiers().setPublic());
		mtd.setRtnType(pty.getType());
		return mtd;
	}
	
	private JstMethod setterMethod(IJstProperty pty) {
		JstMethod mtd = new JstMethod("set"+CapitalizeFirstLetter(pty.getName().toString()),
				new JstModifiers().setPublic(), 
				new JstArg(pty.getType(), "arg1", false));
		return mtd;
	}
	private String CapitalizeFirstLetter(String name) {
		if (TranslateHelper.isNameAllCapitalized(name)) {
			return name;
		}
		String n = name.substring(0, 1).toUpperCase() + name.substring(1);
		return n;
	}

}