/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.loader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.rt.BaseScriptable;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.NativeFunction;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class VjoLoader extends BaseScriptable {

	private static final long serialVersionUID = 1L;
	private static final String VJO_INSTANCE = "vjo";
	private static final String VJO_LOADER_PROP = "loader";
	private static final String[] MTD_NAMES = {"load"};
	private static final String[] PTY_NAMES = {"async"};

	private static final String ID_VJET = "VJET";
	private static final String ID_COMMON_JS = "CommonJS";
	
	private static final String PTY_REQUIRE = "require";
	
	private static final String PTY_GET_MODULE_MGR = "getModuleMgr";
	private static final String PTY_RESOLVER = "resolver";
	private static final String PTY_FETCHER = "fetcher";
	private static final String PTY_EVALUATOR = "evaluator";
	
	final Context m_cx;
	final Scriptable m_scope;
	private static boolean s_verbose = false;
	
	final private boolean m_async = false;
	
	public static void enable(Context cx, Scriptable scope) {
		
		// Common JS 
		Object require = ScriptableObject.getProperty(scope, PTY_REQUIRE);
		if (require != Scriptable.NOT_FOUND){
			
			Scriptable requireObj = (Scriptable)require;
			
			// Utils
			VjResolver resolver = new VjResolver();
			VjFetcher fetcher = new VjFetcher();
			VjEvaluator evaluator = new VjEvaluator(cx);
			
			NativeFunction mgrGetter = (NativeFunction)ScriptableObject.getProperty(requireObj, PTY_GET_MODULE_MGR);
			
			// VJET ModuleMgr
			Object[] vjetArgs = {ID_VJET};
			ScriptableObject vjetMgr = (ScriptableObject)mgrGetter.call(cx, scope, requireObj, vjetArgs);
			ScriptableObject.putProperty(vjetMgr, PTY_RESOLVER, resolver);
			ScriptableObject.putProperty(vjetMgr, PTY_FETCHER, fetcher);
			ScriptableObject.putProperty(vjetMgr, PTY_EVALUATOR, evaluator);
			
			// CommonJS ModuleMgr
			Object[] commonJsArgs = {ID_COMMON_JS};
			ScriptableObject commonJsMgr = (ScriptableObject)mgrGetter.call(cx, scope, requireObj, commonJsArgs);
			ScriptableObject.putProperty(commonJsMgr, PTY_RESOLVER, resolver);
			ScriptableObject.putProperty(commonJsMgr, PTY_FETCHER, fetcher);
			ScriptableObject.putProperty(commonJsMgr, PTY_EVALUATOR, evaluator);
		}
		else {
			// VJET
		Scriptable vjo = (Scriptable)ScriptableObject.getProperty(scope, VJO_INSTANCE);
			if (vjo != null){
				VjoLoader loader = new VjoLoader(cx, scope);
				Object scriptObj = Context.javaToJS(loader, scope);
		ScriptableObject.putProperty(vjo, VJO_LOADER_PROP, scriptObj);
			}
		}
	}
	
	public VjoLoader(Context cx, Scriptable scope) {
		m_cx = cx;
		m_scope = scope;
		defineFunctionProperties(MTD_NAMES);
		defineProperties(PTY_NAMES);
	}
	
	public void load(String uri) {
		if(s_verbose){
			System.out.println("loading: " + uri); //KEEPME
		}
		
		URL sourceUrl = null;
		String clzName = null;
		do {
			try {
				//load from source if available
				sourceUrl = JavaSourceLocator.getInstance().getSourceUrl(uri, ".js");
				clzName = uri;
			}
			catch (Throwable e) {
				//Do nothing //KEEPME
//				e.printStackTrace();
			}
			if (sourceUrl == null) {
				String path = uri.replace(".", "/") + ".js";
				int index = path.lastIndexOf("/");
				String resourceName = path;
				String relDir = "";
				if (index > 0) {
					relDir = path.substring(0, index);
					resourceName = path.substring(index + 1);
				}
				try {
					sourceUrl = ResourceUtil.getResource(relDir, resourceName);
					clzName = uri;
//					System.out.println(sourceUrl);
				} catch (IOException e) {
					//Do nothing
//					e.printStackTrace();
				}
			}
		} while((uri=getContainer(uri))!=null && sourceUrl==null);
			
		if (sourceUrl != null) {
			try {
				m_cx.evaluateReader(m_scope,
					new InputStreamReader(sourceUrl.openStream()), sourceUrl.toExternalForm(), 1, null);
				if (DapCtx.ctx().isActiveMode() && isJ2JType(clzName)) {
					m_cx.evaluateString(m_scope, clzName + "=Packages." + clzName + ";", clzName, 1, null);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else if(!isVjoType(clzName)){
			throw new DsfRuntimeException("could not find " + clzName);
		}
	}
	
	private boolean isVjoType(String clzName) {
		if(clzName.endsWith("Class")){
			return true;
		}
		if(clzName.endsWith("Object")){
			return true;
		}
		return false;
	}

	public boolean getAsync() {
		return m_async;
	}
	/**
	 * Returns the root class. Need to find the root class for nested
	 * types.
	 * @param uri
	 * @return
	 */
	private static String getContainer(String uri) {//resolve nested classes
		String[]parts =uri.split("\\.");
		int len = parts.length;
		//return null if class is lowercase
		if (len <= 1 || parts[len - 2].toLowerCase().equals(parts[len - 2])) {
			return null;
		}
		String clz = parts[0];
		for (int i=1;i<len-1;i++) {
			clz += "." + parts[i];
		}
		return clz;
	}
	
	private static boolean isJ2JType(String uri) {
		try {
			Class<?> clz = Class.forName(uri, false, VjoLoader.class.getClassLoader());
			return !IJsJavaProxy.class.isAssignableFrom(clz);
		} catch (Exception e) {
			// this is a tool and run by console / java main
			if(s_verbose){
				e.printStackTrace(); //KEEPME
			}
			return false;
		}
	}
	
	public static boolean isVerbose() {
		return s_verbose;
	}

	public static void setVerbose(boolean verbose) {
		s_verbose = verbose;
	}
}
