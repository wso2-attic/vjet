/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.lib;

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Utility class to load JST libraries into the JST type space
 * 
 */
public class TsLibLoader {

	/**
	 * Load the default libraries into the JST type space
	 * Note: Any libs loaded by this function should also answer isDefaultLibName()
	 * with the lib name
	 * 
	 * @param tsMgr
	 *            JstTypeSpaceMgr
	 */
	public static void loadDefaultLibs(final JstTypeSpaceMgr tsMgr) {

	
		// load Java primitive JST types
		loadJavaPrimitiveLib(tsMgr);

		loadJsNativeGlobalLib(tsMgr);
		
		// load native objects and client-side objects
		loadBrowserTypesLib(tsMgr);
		
		loadVjoLib(tsMgr);

//		loadVjoLangLib(tsMgr);
		
		promoteGlobals(tsMgr);

	}
	
	
	private static void promoteGlobals(JstTypeSpaceMgr jstTypeSpaceMgr) {
		ITypeSpace<IJstType, IJstNode> typeSpace = jstTypeSpaceMgr.getTypeSpace();
		typeSpace.addAllGlobalTypeMembers(new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,"Global"));
		typeSpace.addAllGlobalTypeMembers(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,"Window"));
		
		
		IJstType window = typeSpace.getType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,"Window"));
		
//		if(!window.hasGlobalVars()){
//			continue;
//		}
		for (IJstGlobalVar gvar : window.getGlobalVars()) {
			final String groupName = window.getPackage().getGroupName();
			
			//TODO replace gvar symbol with attributed type bound updates
			typeSpace.addToGlobalSymbolMap(groupName,
					gvar.getName().getName(), gvar.getOwnerType().getName(), gvar);
		}
		
	}

	
	
	/**
	 * Check if the lib name is among the default libs loaded by loadDefaultLibs
	 * @param libName
	 * @return true if the libName is among the default libs
	 */
	public static boolean isDefaultLibName(final String libName) {
		
		if (libName.equals(JstTypeSpaceMgr.JS_NATIVE_GRP) ||
			libName.equals(JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP) ||
			libName.equals(JstTypeSpaceMgr.JS_BROWSER_GRP) ||
			libName.equals(LibManager.VJO_SELF_DESCRIBED)) {
			
			return true;
		}
		else {
			return false;
		}
	}

	private static void loadVjoLib(JstTypeSpaceMgr tsMgr) {
		IJstLib lib = LibManager.getInstance().getVjoSelfDescLib();
		JstCache.getInstance().addLib(lib);
		tsMgr.loadLibrary(lib, LibManager.VJO_SELF_DESCRIBED);
		
	}
	private static void loadVjoLangLib(JstTypeSpaceMgr tsMgr) {
		IJstLib lib = LibManager.getInstance().getVjoSelfDescLib();
		JstCache.getInstance().addLib(lib);
		tsMgr.loadLibrary(lib, LibManager.VJO_JAVA_LIB_NAME);
		
	}



	/**
	 * Load Java primitive JST types into the JST type space This is to resolve
	 * Java types specified as JS doc domments in the form of //> public int
	 * doIt(float a, double b)
	 * 
	 * @param tsMgr
	 *            JstTypeSpaceMgr
	 */
	public static void loadJavaPrimitiveLib(JstTypeSpaceMgr tsMgr) {
		IJstLib lib = LibManager.getInstance().getJavaPrimitiveLib();
		JstCache.getInstance().addLib(lib);
		tsMgr.loadLibrary(lib, JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP);
	}

	/**
	 * Load JST types for browser objects. This includes JS client-side, DOM,
	 * events, and JS global objects.
	 * 
	 * @param tsMgr
	 *            JstTypeSpaceMgr
	 */
	public static void loadBrowserTypesLib(final JstTypeSpaceMgr tsMgr) {
		IJstLib lib = LibManager.getInstance().getBrowserTypesLib();
		JstCache.getInstance().addLib(lib);
		tsMgr.loadLibrary(lib, JstTypeSpaceMgr.JS_BROWSER_GRP);
	}

	/**
	 * Load JST types for browser objects. This includes JS client-side, DOM,
	 * events, and JS global objects.
	 * 
	 * @param tsMgr
	 *            JstTypeSpaceMgr
	 */
	public static void loadJsNativeGlobalLib(final JstTypeSpaceMgr tsMgr) {
		IJstLib lib = LibManager.getInstance().getJsNativeGlobalLib();
		JstCache.getInstance().addLib(lib);
		tsMgr.loadLibrary(lib, JstTypeSpaceMgr.JS_NATIVE_GRP);
	}


	public static String[] getDefaultLibNames() {
		return new String[]{JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP, JstTypeSpaceMgr.JS_NATIVE_GRP, JstTypeSpaceMgr.JS_BROWSER_GRP, LibManager.VJO_SELF_DESCRIBED};
	}
		
	

/* 
 * Since group info is not serialized using this api as temporary measure.
 */
	public static String[] getJsNativeGroups() {
		return new String[]{JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP, JstTypeSpaceMgr.JS_NATIVE_GRP};
	}


	public static String[] getBrowserGroups() {
		return new String[]{JstTypeSpaceMgr.JS_BROWSER_GRP};
	}


	public static String[] getVjoGroups() {
		return new String[]{JstTypeSpaceMgr.VJO_SELF_DESCRIBED};
	}

}
