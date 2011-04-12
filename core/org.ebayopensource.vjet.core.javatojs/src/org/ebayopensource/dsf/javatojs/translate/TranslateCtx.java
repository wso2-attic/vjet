/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.javatojs.report.DefaultErrorReporter;
import org.ebayopensource.dsf.javatojs.report.ErrorReporter;
import org.ebayopensource.dsf.javatojs.trace.TranslateTraceMgr;
import org.ebayopensource.dsf.javatojs.translate.config.TranslateConfig;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;

/**
 * TranslateCtx is thread-local, however, members of TranslateCtx are 
 * shared with child threads.
 */
public class TranslateCtx {
	
	private static ThreadLocalContext s_context = new ThreadLocalContext();

	private TranslateConfig m_config;
	
	private boolean m_enableParallel = false;
	private boolean m_enableTrace = false;
	
	private Map<JstType,TranslateInfo> m_translateInfo = new HashMap<JstType,TranslateInfo>();
	private Map<IJstType,CustomInfo> m_customInfos = new HashMap<IJstType,CustomInfo>();
	private Map<String,IJstType> m_jsTypes = new HashMap<String,IJstType>();
	
	private TranslateLogger m_logger;
	private TranslateTraceMgr m_traceMgr;
	private ErrorReporter m_errorReporter = new DefaultErrorReporter();
	
	//
	// Constructor(s)
	//		
	private TranslateCtx() {
	}
	
	//
	// Static API
	//
	public static TranslateCtx ctx() {
		TranslateCtx ctx = s_context.get();
		if (ctx == null){
			synchronized (TranslateCtx.class){
				if (ctx == null){
					ctx = createCtx();
				}
			}
		}
		return ctx;
	}
	
	public static void setCtx(final TranslateCtx context) {
		s_context.set(context) ;
	}
	
	public static TranslateCtx createCtx() {
		TranslateCtx context = new TranslateCtx();
		s_context.set(context);

		return context;
	}
	
	public static TranslateCtx createChildCtx(TranslateCtx parentCtx) {
		
		TranslateCtx childCtx = createCtx();
		childCtx.m_config = parentCtx.m_config;		
		childCtx.m_translateInfo = parentCtx.m_translateInfo;
		childCtx.m_customInfos = parentCtx.m_customInfos;
		childCtx.m_jsTypes = parentCtx.m_jsTypes;
		childCtx.m_errorReporter = parentCtx.m_errorReporter;
		childCtx.m_enableParallel = parentCtx.m_enableParallel;
		childCtx.m_enableTrace = parentCtx.m_enableTrace;
		
		return childCtx;
	}
	
	public TranslateCtx reset() {
		m_config = null; 
		m_translateInfo.clear();
		m_customInfos.clear();
		m_jsTypes.clear();
		m_enableParallel = false;
		m_enableTrace = false;
		
		m_logger = null;
		m_traceMgr = null;
		
		m_errorReporter = null;

		return this;
	}

	//
	// API
	//
	public void setConfig(TranslateConfig config) {
		m_config = config;
	}
	
	public TranslateConfig getConfig() {
		if (m_config == null){
			m_config = new TranslateConfig();
		}
		return m_config;
	}
	
	public TranslatorProvider getProvider() {
		return getConfig().getProvider();
	}
	
	public void setTranslateInfo(Map<JstType,TranslateInfo> translateInfo){
		m_translateInfo = translateInfo;
	}
	
	public TranslateInfo getTranslateInfo(final JstType type){
		if (type == null){
			return null;
		}
		synchronized(TranslateInfo.class){
			TranslateInfo info = m_translateInfo.get(type);
			if (info == null){
				info = new TranslateInfo(type);
				m_translateInfo.put(type, info);
			}
			return info;
		}
	}
	
	public Collection<TranslateInfo> getAllTranslateInfos(){
		synchronized(TranslateInfo.class){
			return Collections.unmodifiableCollection(m_translateInfo.values());
		}
	}
	
	public boolean isExcludedType(final String fullName){
		if (fullName == null){
			return false;
		}		
		String mappedName = getConfig().getPackageMapping().mapTo(fullName);
		IJstType jstType = JstCache.getInstance().getType(mappedName);
		
		if(jstType!=null){
			return isExcluded(jstType);
		} else if (getConfig().getPolicy().isClassExcluded(mappedName)){
			return true;
		} 
		
		return false;
		
	}
	
	public boolean isExcluded(final IJstType jstType){
		if (jstType == null || jstType.getName() == null){
			return false;
		}
		if (!(jstType instanceof JstType)){
			return false;
		}
		// TODO - remove after fixing package mapping
		if (jstType.getName().startsWith("vjo.java")){
			return false;
		}
		CustomInfo cInfo = getCustomInfo(jstType, false);
		if (cInfo.isExcluded()){
			return true;
		}
		if (getConfig().getPolicy().isClassExcluded(getConfig().getPackageMapping().mapFrom(jstType.getName()))){
			return true;
		}
		if (cInfo.isNone() && getCustomInfo(jstType.getRootType(), false).isNone()){
			for (IJstType baseType: jstType.getExtends()){
				if (isExcluded(baseType)){
					return true;
				}
			}
			IJstType baseType = getTranslateInfo((JstType)jstType).getBaseType();
			if (isExcluded(baseType)){
				return true;
			}
		}
		return isExcluded(jstType.getOuterType());
	}
	
	public void setCustomAttr(IJstType jstType, CustomAttr attr){
		getCustomInfo(jstType, true).setAttr(attr);
	}
	
	public boolean isJavaOnly(final IJstType jstType){
		return getCustomInfo(jstType, false).isJavaOnly();
	}
	
	public boolean isJSProxy(final IJstType jstType){
		return getCustomInfo(jstType, false).isJSProxy();
	}
	
	public boolean isMappedToJS(final IJstType jstType){
		return getCustomInfo(jstType, false).isMappedToJS();
	}
	
	public boolean isMappedToVJO(final IJstType jstType){
		return getCustomInfo(jstType, false).isMappedToVJO();
	}
	
	public void setNewName(final IJstType jstType, final String newName){
		getCustomInfo(jstType, true).setName(newName);
	}
	
	public String getNewName(final IJstType jstType){
		CustomInfo cInfo = getCustomInfo(jstType, false);
		return cInfo == null ? null : cInfo.getName();
	}
	
	public void updateCustomInfo(final IJstType jstType, final CustomInfo cInfo){
		if (jstType == null || cInfo == null){
			return;
		}
		CustomInfo old = getCustomInfo(jstType, false);
		CustomInfo newInfo = cInfo;
		if (old != null){
			newInfo = CustomInfo.update(old, newInfo);
		}
		synchronized(CustomInfo.class){
			if (cInfo == CustomInfo.NONE){
				m_customInfos.remove(jstType);
			}
			else {
				m_customInfos.put(jstType, newInfo);
			}
		}
	}
	
	public void addJsType(String name, IJstType type){
		if (name == null || type == null){
			return;
		}
		synchronized(TranslateCtx.class){
			m_jsTypes.put(name, type);
		}
	}
	
	public boolean isJsType(String name){
		synchronized(TranslateCtx.class){
			return m_jsTypes.containsKey(name);
		}
	}
	
	public boolean isJsType(IJstType type){
		synchronized(TranslateCtx.class){
			return m_jsTypes.containsValue(type);
		}
	}
	
	public IJstType getJsType(String name){
		synchronized(TranslateCtx.class){
			return m_jsTypes.get(name);
		}
	}
	
	public TranslateCtx enableParallel(boolean enable){
		m_enableParallel = enable;
		return this;
	}
	
	public boolean isParallelEnabled(){
		return m_enableParallel;
	}
	
	public TranslateCtx enableTrace(boolean enable){
		m_enableTrace = enable;
		return this;
	}
	
	public boolean isTraceEnabled(){
		return m_enableTrace;
	}
	
	public void setLogger(TranslateLogger logger) {
		m_logger = logger;
	}
	
	public TranslateLogger getLogger() {
		if (m_logger == null){
			return m_logger = new TranslateLogger(getConfig().getErrorPolicy());
		}
		return m_logger;
	}
	
	public TranslateTraceMgr getTraceManager(){
		if (m_traceMgr == null){
			m_traceMgr = new TranslateTraceMgr(this);
		}
		return m_traceMgr;
	}
	
	public ErrorReporter getErrorReporter() {
		if (m_errorReporter == null) {
			m_errorReporter = new DefaultErrorReporter();
		}
		return m_errorReporter;
	}
	
	public void setErrorReporter(ErrorReporter reporter) {
		m_errorReporter = reporter;
	}
	
	//
	// Private
	//
	private CustomInfo getCustomInfo(final IJstType type, boolean create){
		if (type == null){
			return CustomInfo.NONE;
		}
		synchronized(CustomInfo.class){
			CustomInfo cInfo = m_customInfos.get(type);
			if (cInfo != null){
				return cInfo;
			}
			if (create){
				cInfo = new CustomInfo();
				m_customInfos.put(type, cInfo);
				return cInfo;
			}
			return CustomInfo.NONE;
		}
	}
	
	//
	// Embedded
	//
	private static class ThreadLocalContext extends InheritableThreadLocal<TranslateCtx> {
		protected TranslateCtx initialValue() {
			return new TranslateCtx();
		}
	
		protected TranslateCtx childValue(final TranslateCtx parentContext) {
			TranslateCtx childContext = new TranslateCtx();

			// TODO
													
			return childContext;
		}
	}
}
