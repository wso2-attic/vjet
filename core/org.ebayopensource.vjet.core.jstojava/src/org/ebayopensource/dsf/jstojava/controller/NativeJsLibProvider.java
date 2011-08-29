/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsgroup.bootstrap.JsLibBootstrapLoader;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.lib.IJstLibProvider;
import org.ebayopensource.vjo.lib.LibManager;

public class NativeJsLibProvider implements IJstLibProvider {
	
	private Map<String, IJstLib> m_jstLibMap = new HashMap<String,IJstLib>();
	
	private boolean m_primitivesFixed = false;
	
	public NativeJsLibProvider() {
		IJstLib javaPrimitivelib = LibManager.getInstance().getJavaPrimitiveLib();
		add(javaPrimitivelib);
		
		IJstLib jsGlobals = LibManager.getInstance().getJsNativeGlobalLib();
		add(jsGlobals);
		
		// fix primitives to extend number here
		fixPrimitives(jsGlobals, javaPrimitivelib);
		
		
		
		IJstLib browserLib = LibManager.getInstance().getBrowserTypesLib();
		
		addBrowserExtensions(browserLib.getName());
		
		add(browserLib);
		
		
		
		
		// commented out since system must be able to build jsnative before vjo lib resource
		//IJstLib vjoSelfDescribedLib = LibManager.getInstance().getVjoSelfDescLib();
		//add(vjoSelfDescribedLib);
		
	}

	private void addBrowserExtensions(String name) {
		try {
			InputStream resource = NativeJsLibProvider.class.getResourceAsStream("browserbootstrap.js");
		
			String bootstrapJS = getFileContents(resource);
			JsLibBootstrapLoader.load(bootstrapJS, LibManager.JS_NATIVE_LIB_NAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	protected String getFileContents(InputStream stream)
			throws IOException, URISyntaxException {

	
		byte[] bs = new byte[stream.available()];
		stream.read(bs);
		stream.close();
		
		
	return new String(bs);



	}
	
	

	private void fixPrimitives(IJstLib jsGlobals, IJstLib javaPrimitivelib) {
		if(m_primitivesFixed){
			return;
		}
	
		JstType arguments = (JstType)jsGlobals.getType("Arguments", false);
		arguments.setMetaType(true);
		
		JstType global = (JstType)jsGlobals.getType("Global", false);
		global.setMetaType(true);
		JstType charT = (JstType)javaPrimitivelib.getType("char", false);
		charT.setMetaType(true);
		JstType voidT = (JstType)javaPrimitivelib.getType("void", false);
		voidT.setMetaType(true);
	
		JstType booleanT = javaPrimitivelib.getType("boolean", false);
		booleanT.setMetaType(true);
		booleanT.removeExtend(booleanT.getExtend());
		booleanT.addExtend(jsGlobals.getType("PrimitiveBoolean", false));
		
		IJstType number = jsGlobals.getType("Number", false);		
		
		String[] dataTypesToExtendNumber = {"int", "double", "float", "short", "long", "byte"};
				
		for(String needsToExtend: dataTypesToExtendNumber){			
			JstType type = javaPrimitivelib.getType(needsToExtend, false);		
			type.setMetaType(true);
			IJstType extend = type.getExtend();
			if(extend ==null || number.getName().equals(extend.getName())){
				type.removeExtend(extend);
				type.addExtend(number);
			}		
		}		
		m_primitivesFixed = true;
		
	}

	public IJstLibProvider add(IJstLib jstLib) {
		if(jstLib ==null) {
			return this;
		}
		
		m_jstLibMap.put(jstLib.getName(),jstLib);	
		// LibProvoder is responsible for adding the libraries to the cache
		JstCache.getInstance().addLib(jstLib);
		return this;
	}

	public void clearAll() {
		m_jstLibMap.clear();

	}

	public List<IJstLib> getAll() {
		return new ArrayList<IJstLib>(m_jstLibMap.values());
	}

	public IJstLib remove(String lib) {
		return m_jstLibMap.remove(lib);	
	}

}
