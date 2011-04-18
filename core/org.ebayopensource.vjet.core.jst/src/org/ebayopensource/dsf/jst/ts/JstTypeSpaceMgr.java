/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.io.InputStream;

import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.ITypeSpaceEventListener;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent.EventId;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.event.dispatch.SourceEventDispatcher;
import org.ebayopensource.dsf.ts.event.dispatch.TypeSpaceEventDispatcher;

/**
 * JstTypeSpaceMgr is the entry point for all type space related operations.
 * 
 * 
 */
public final class JstTypeSpaceMgr {
	
	public static final String JS_BROWSER_GRP = "JsBrowserLib";
	public static final String JS_NATIVE_GRP = "JsNativeLib";
	public static final String JAVA_PRIMITIVE_GRP = "JavaPrimitive";
	public static final String VJO_BASE_LIB_NAME = "VjoBaseLib";
	public static final String VJO_SELF_DESCRIBED = "VjoSelfDescribed";

	private boolean m_initialized = false;
	private final TypeSpace<IJstType,IJstNode> m_ts;
	private final JstQueryExecutor m_queryExecutor;
	
	private final ITypeSpaceLoader m_tsLoader;
	private final IJstParseController m_jstParseController;
	
	private final GroupMgr m_groupMgr;
	private final TypeDependencyMgr m_typeDependencyMgr;
	private final PropertyIndexMgr m_ptyIndexMgr;
	private final MethodIndexMgr m_mtdIndexMgr;
	private final JstResourceMgr m_resourceMgr;
	
	private TypeSpaceConfig m_config;
	
	private final SourceEventDispatcher m_srcEventDispatcher;
	private final TypeSpaceEventDispatcher m_tsEventDispatcher;
	
	//
	// Constructor
	//	
	/**
	 * Constructor
	 * @param controller IJstParseController
	 * @param loader IJstTypeLoader
	 */
	public JstTypeSpaceMgr(IJstParseController controller, IJstTypeLoader loader){
						
		m_ts = new TypeSpace<IJstType,IJstNode>();
				
		m_groupMgr = new GroupMgr(this);
		m_typeDependencyMgr = new TypeDependencyMgr(this);		
		m_resourceMgr = new JstResourceMgr(this);
		
		m_srcEventDispatcher = new SourceEventDispatcher();
		m_srcEventDispatcher.addListener(new JstEventListener(this));
		m_queryExecutor = new JstQueryExecutor(m_ts);
		
		m_mtdIndexMgr = new MethodIndexMgr(m_ts, m_queryExecutor);
		m_ptyIndexMgr = new PropertyIndexMgr(m_ts, m_queryExecutor);		
		m_ts.setMethodSymbolTableManager(m_mtdIndexMgr);
		m_ts.setPropertySymbolTableManager(m_ptyIndexMgr);
		
		m_tsEventDispatcher = new TypeSpaceEventDispatcher();
		
		m_tsLoader = new JstTypeSpaceLoader(this, loader);
		m_jstParseController = controller;
		if (m_jstParseController != null) {
			m_jstParseController.setJstTSMgr(this); // set this TS mgr to the input controller
		}
	}
	
	// temp for backward compatibility, 
	// 	remove it after code migration to use the above constructor
	//
//	@Deprecated
//	public JstTypeSpaceMgr(IJstParser parser) {
//		this(new JstParseController(parser), new DefaultJstTypeLoader());		
//	}
	
	
	//
	// API 
	//
	/**
	 * Initialize the type space by loading groups in the work space,
	 * build type dependency graphs and property/method indexes if configured so.
	 * @return JstTypeSpaceMgr
	 */
	public JstTypeSpaceMgr initialize(){
		if (!m_initialized){
			m_srcEventDispatcher.setConfig(getConfig());
			m_tsEventDispatcher.setConfig(getConfig());
			m_resourceMgr.initialize();
			m_initialized = true;
			m_tsEventDispatcher.dispatch(
					new TypeSpaceEvent(EventId.Init), 
					new EventListenerStatus<IJstType>(EventListenerStatus.Code.Successful));
		}
		return this;
	}
	
	/**
	 * Answer whether the type space has been initialized
	 * @return boolean
	 */
	public boolean isInitialized(){
		return m_initialized;
	}
	
	/**
	 * Set the config for the type space
	 * @param config TypeSpaceConfig
	 * @return JstTypeSpaceMgr
	 */
	public JstTypeSpaceMgr setConfig(TypeSpaceConfig config){
		m_config = config;
		return this;
	}
	
	/**
	 * Answer the config for the type space
	 * @return TypeSpaceConfig
	 */
	public TypeSpaceConfig getConfig(){
		if (m_config == null){
			m_config = new TypeSpaceConfig();
		}
		return m_config;
	}
	
	/**
	 * Answer the type space
	 * @return ITypeSpace<IJstType,IJstNode>
	 */
	public ITypeSpace<IJstType,IJstNode> getTypeSpace(){
		return m_ts;
	}
	
	/**
	 * Answer the query executor
	 * @return JstQueryExecutor
	 */
	public JstQueryExecutor getQueryExecutor(){
		return m_queryExecutor;
	}
	
	/**
	 * Process source related event and update type space accordingly.
	 * The call is synchronous.
	 * @param event ISourceEvent Required
	 */
	public void processEvent(ISourceEvent event){
		if (!m_initialized || event == null){
			return;
		}
		m_srcEventDispatcher.dispatch(event);
	}
	
	/**
	 * Process source related event and update type space accordingly.
	 * The call is asynchronous. Callback will be invoked at the end of
	 * processing if it's not null.
	 * @param event ISourceEvent Required
	 * @param callback ISourceEventCallback<IJstType> Optional
	 * @return IEventHandle
	 */
	public IEventListenerHandle processEvent(ISourceEvent<IEventListenerHandle> event, ISourceEventCallback<IJstType> callback){
		if (!m_initialized || event == null){
			return null;
		}
		return m_srcEventDispatcher.dispatch(event, callback);
	}
	
	public JstTypeSpaceMgr registerTypeSpaceEventListener(ITypeSpaceEventListener listener) {
		m_tsEventDispatcher.addListener(listener);
		return this;
	}
	
	public JstTypeSpaceMgr registerSourceEventListener(ISourceEventListener listener) {
		m_srcEventDispatcher.addListener(listener);
		return this;
	}
	
	/**
	 * Close the type space and persist dependency graphs and indexes
	 * if configured so.
	 */
	public void close(){
		if (!m_initialized){
			return;
		}
		
		m_initialized = false;
		m_tsEventDispatcher.dispatch(
				new TypeSpaceEvent(EventId.Unloaded), 
				new EventListenerStatus<IJstType>(EventListenerStatus.Code.Successful));
		m_resourceMgr.persistTypeSpace();
	}
	
	/**
	 * Load JST types library into the type space from an input stream
	 * @param is InputStream for a serialized JstType resource library
	 * @param grpName type space group name for the library
	 */
	public void loadLibrary(InputStream is, String grpName) {
		if (is == null) {
			throw new RuntimeException("InputStream is null");
		}
		if (grpName == null) {
			throw new RuntimeException("grpName is null");
		}
		m_resourceMgr.loadLibrary(is, grpName);
	}
	
	/**
	 * Load JST types into the type space from given IJstLib
	 * @param lib IJstLib JST type library
	 * @param grpName type space group name for the library
	 */
	public void loadLibrary(IJstLib lib, String grpName) {
		if (lib == null) {
			throw new RuntimeException("lib is null");
		}
		if (grpName == null) {
			throw new RuntimeException("grpName is null");
		}
		m_resourceMgr.loadLibrary(lib, grpName);
	}
	
	public static boolean isDefaultLibName(final String libName) {
		
		if (libName.equals(JS_NATIVE_GRP) ||
			libName.equals(JAVA_PRIMITIVE_GRP) ||
			libName.equals(VJO_BASE_LIB_NAME) ||
			libName.equals(JS_BROWSER_GRP) ||
			libName.equals(VJO_SELF_DESCRIBED)) {
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Get the jst type space loader to load group types into type space
	 * @return ITypeSpaceLoader
	 */
	public ITypeSpaceLoader getJstTypeSpaceLoader() {
		return m_tsLoader;
	}
	
	//
	// Package protected
	//
	TypeSpace<IJstType,IJstNode> getTypeSpaceImpl(){
		return m_ts;
	}

	GroupMgr getGroupMgr(){
		return m_groupMgr;
	}
	
	JstResourceMgr getResourceMgr() {
		return m_resourceMgr;
	}
	
	TypeDependencyMgr getTypeDependencyMgr(){
		return m_typeDependencyMgr;
	}
	
	PropertyIndexMgr getPropertyIndexMgr(){
		return m_ptyIndexMgr;
	}
	
	MethodIndexMgr getMethodIndexMgr(){
		return m_mtdIndexMgr;
	}
	
	TypeSpaceEventDispatcher getTypeSpaceEventDispatcher(){
		return m_tsEventDispatcher;
	}
	
	IJstParseController getJstParseController() {
		return m_jstParseController;
	}
}
