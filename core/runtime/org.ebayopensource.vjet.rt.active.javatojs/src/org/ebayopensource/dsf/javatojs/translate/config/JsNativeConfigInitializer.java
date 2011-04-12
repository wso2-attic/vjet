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

import org.ebayopensource.dsf.javatojs.control.BaseTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.custom.dom.JsNativeCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.policy.TranslationPolicy;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.lib.IJstLibProvider;
import org.ebayopensource.vjo.lib.LibManager;

public class JsNativeConfigInitializer extends BaseTranslationInitializer
	implements ITranslationInitializer{

	/**
	 * TODO:  re-factor, conditional statement should be removed
	 */
	
	public void initialize() {
		JstCache.getInstance().clear();
		LibManager.getInstance().clear();
		
		TranslateCtx ctx = TranslateCtx.ctx(); //.reset();
		ctx.getConfig().addCustomTranslator(new JsNativeCustomTranslator());
		ctx.getConfig().setJstLibProvider(new JsNativeLibProvider());
		ctx.getConfig().setParseComments(true);
		ctx.getConfig().setPolicy(getPolicy());
	}
	
	private ITranslationPolicy getPolicy() {
		return new TranslationPolicy (){			
			@Override
			public boolean isClassExcluded(String clsName) {
				if (clsName.startsWith("org.mozilla.mod.javascript") ||
					clsName.startsWith("org.ebayopensource.dsf.jsnative.anno") ||
					clsName.endsWith("Scriptable")) {
					return true;
				}
				return false;
			}
		};
	}
}
class JsNativeLibProvider implements IJstLibProvider {
	
	private Map<String, IJstLib> m_jstLibMap = new HashMap<String,IJstLib>();

	JsNativeLibProvider() {
		LibManager libMgr = LibManager.getInstance();
		add(libMgr.getJavaPrimitiveLib());
	}
	
	public List<IJstLib> getAll() {
		return new ArrayList<IJstLib>(m_jstLibMap.values());
	}
	
	public IJstLibProvider add(IJstLib jstLib) {
		if(jstLib ==null) {
			return this;
		}
		
		m_jstLibMap.put(jstLib.getName(),jstLib);	
		JstCache.getInstance().addLib(jstLib);
		return this;
	}
	
	public IJstLib remove(String lib) {
		return m_jstLibMap.remove(lib);	
	}
	
	public void clearAll() {
		m_jstLibMap.clear();
	}
}