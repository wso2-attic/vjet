/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.control.translate.DeclarationPhase;
import org.ebayopensource.dsf.javatojs.control.translate.DependencyPhase;
import org.ebayopensource.dsf.javatojs.control.translate.ImplementationPhase;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TraceTime;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.AstBinding;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.config.TranslateConfig;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomTranslateDelegator;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.javatojs.util.AstParserHelper;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.dsf.logger.LogLevel;

/**
 * Entry class for Java to JST translation
 */
public final class TranslationController {
	
	public static final TraceId CONTROLLER = TranslationTraceId.CONTROLLER;
	public static final TraceAttr TARGETED = new TraceAttr("method", "TargetedTranslation");
	public static final TraceAttr ON_DEMAND = new TraceAttr("method", "OnDemandTranslation");
	
	private static final JstCache s_jstCache = JstCache.getInstance();
	
	private ITranslationInitializer m_initializer;
	
	private TranslateCtx m_ctx;
	private List<TranslateError> m_directErrors = new ArrayList<TranslateError>();
	private List<TranslateError> m_phaseErrors = new ArrayList<TranslateError>();
	private Map<JstType,List<Throwable>> m_exceptions = new LinkedHashMap<JstType,List<Throwable>>();
	private boolean m_initialized = false;
	
	/**
	 * Constructor
	 */
	public TranslationController(){
		s_jstCache.clear();
		m_ctx = TranslateCtx.createCtx();	
	}
	
	/**
	 * Constructor
	 * @param configInitializer ITranslateConfigInitializer
	 */
	public TranslationController(ITranslationInitializer configInitializer){
		this();
		setInitializer(configInitializer);
	}

	//
	// API
	//
	/**
	 * Set initializer for the translation
	 * @param initializer ITranslateConfigInitializer
	 */
	public TranslationController setInitializer(ITranslationInitializer initializer) {
		m_initializer = initializer;
		return this;
	}
	
	public ITranslationInitializer getInitializer(){
//		if(m_initializer == null){
//			m_initializer = new DefaultTranslationInitializer();
//		}
		return m_initializer;
	}
	
	public boolean isParallelEnabled() {
		return m_ctx.isParallelEnabled();
	}

	public TranslationController enableParallel(boolean enableParallel) {
		m_ctx.enableParallel(enableParallel);
		return this;
	}

	public boolean isTraceEnabled() {
		return m_ctx.isTraceEnabled();
	}

	public TranslationController enableTrace(boolean enableTrace) {
		m_ctx.enableTrace(enableTrace);
		return this;
	}
	
	/**
	 * Answer the context for the translation
	 * @return TranslateCtx
	 */
	public TranslateCtx getCtx(){
		if (m_ctx == null){
			m_ctx = TranslateCtx.createCtx();	
		}
		return m_ctx;
	}
	
	/**
	 * Answer the config for the translation
	 * @return TranslateConfig
	 */
	public TranslateConfig getConfig(){
		return getCtx().getConfig();
	}
	
	/**
	 * Perform targeted translation on given java type.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param srcType Class
	 * @return JstType null if srcType is null.
	 */
	public JstType targetedTranslation(final Class<?> srcType){
		
		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, TARGETED);
		
		try {
			List<Class<?>> srcTypes = new ArrayList<Class<?>>(1);
			srcTypes.add(srcType);
			
			List<JstType> jstTypes = targetedTranslation(getSourceFiles(srcTypes, m_directErrors), m_directErrors);
			return (jstTypes.isEmpty()) ? null : jstTypes.get(0);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform targeted translation on given java types.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param srcTypes List<Class>
	 * @return List<JstType> empty list if srcTypes is null.
	 */
	public List<JstType> targetedTranslation(final List<Class<?>> srcTypes){

		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, TARGETED);
		
		try {
			return targetedTranslation(getSourceFiles(srcTypes, m_directErrors), m_directErrors);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform targeted translation on given input source stream.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param fileURI URI of the file
	 * @param inputSrc InputStreamReader
	 * @return JstType empty if inputSrc is null.
	 */
	public JstType targetedTranslation(final URI fileURI, final InputStreamReader inputSrc){

		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, TARGETED);
		
		try {
			if (inputSrc == null){
				m_directErrors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "inputSrc is null"));
				return null;
			}
			if (fileURI == null){
				m_directErrors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "fileName is null"));
				return null;
			}
			
			String src = JavaToJsHelper.readFromInputReader(inputSrc);
			if (src == null){
				m_directErrors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "Source is not found for the input stream"));
				return null;
			}
			
			Map<URI,String> srcFiles = new LinkedHashMap<URI,String>();
			srcFiles.put(fileURI, src); 
			
			List<JstType> jstTypes = targetedTranslation(srcFiles, m_directErrors);
			return (jstTypes.isEmpty()) ? null : jstTypes.get(0);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform targeted translation on given files.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param files Map<String,String> keyed by file path and value is 
	 * source code
	 * @return List<JstType> empty if files is null.
	 */
	public List<JstType> targetedTranslation(final Map<URI,String> files){

		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, TARGETED);
		
		try {
			return targetedTranslation(files, m_directErrors);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform on demand translation on given java type.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param srcCls Class
	 * @return List<JstType> empty if files is null.
	 */
	public List<JstType> onDemandTranslation(final Class<?> srcCls){

		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, ON_DEMAND);
		
		try {
			List<Class<?>> srcTypes = new ArrayList<Class<?>>(1);
			srcTypes.add(srcCls);
			
			return onDemandTranslation(getSourceFiles(srcTypes, m_directErrors), m_directErrors);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform on demand translation on given java types.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param srcTypes List<Class>
	 * @return List<JstType> empty if files is null.
	 */
	public List<JstType> onDemandTranslation(final List<Class<?>> srcTypes){
		
		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, ON_DEMAND);
		
		try {
			return onDemandTranslation(getSourceFiles(srcTypes, m_directErrors), m_directErrors);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Perform on demand translation on given source files.
	 * getErrors() answers the errors of this operation.
	 * The errors will be cleared out in the begining of
	 * next translation operation.
	 * @param files Map<String,String>
	 * @return List<JstType> empty if files is null.
	 */
	public List<JstType> onDemandTranslation(final Map<URI,String> files){
		
		initialize();
		
		TraceTime timer = m_ctx.getTraceManager().getTimer();
		getTracer().startGroup(CONTROLLER, timer, ON_DEMAND);
		
		try {
			return onDemandTranslation(files, m_directErrors);
		}
		finally {
			getTracer().endGroup(CONTROLLER, m_directErrors, timer);
		}
	}
	
	/**
	 * Answer all errors from previous translation. 
	 * The errors from translations before the previous one had been cleared out
	 * before previous translation started.
	 * @return List<TranslateError>
	 */
	public List<TranslateError> getErrors(){
		
		List<TranslateError> errors = new ArrayList<TranslateError>();
		
		errors.addAll(m_directErrors);
		
		errors.addAll(m_phaseErrors);
		
		Collection<TranslateInfo> tInfos = getCtx().getAllTranslateInfos();
		for (TranslateInfo info: tInfos){
			errors.addAll(info.getStatus().getErrors());
		}
		
		return errors;
	}

	/**
	 * Answer all exceptions from previous translation. 
	 * @return Map<JstType,List<Throwable>>
	 */
	public Map<JstType,List<Throwable>> getExceptions(){
		return Collections.unmodifiableMap(m_exceptions);
	}
	
	/**
	 * Clear out controller state to be its initial state, 
	 * also reset translation context and clear JstCache
	 */
	public void reset(){
		m_ctx.reset();
		s_jstCache.clear();
		m_directErrors.clear();
		m_phaseErrors.clear();
		m_exceptions.clear();
		m_initialized = false;
	}
	
	//
	// Private
	//
	private void initialize(){
		if(m_initialized) {
			return;
		}
		
		CustomTranslateDelegator customTranslator = getCtx().getProvider().getCustomTranslator();

		// Init config
		Initializer.getInstance().initialize(getInitializer());
		
		// Load JST libs into JstCache
		List<IJstLib> list = m_ctx.getConfig().getJstLibProvider().getAll();
		for(IJstLib lib: list){
			// Already put in cache by libprovider
//			s_jstCache.addLib(lib);		
			for (JstType jstType: lib.getAllTypes(true)){
				customTranslator.initialize(jstType);
			}
		}
		
		m_initialized = true;
	}

	private List<JstType> createJstTypes(final Map<URI,String> files, final List<TranslateError> errors){
		
		if (files == null){
			errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, "files is null"));
			return Collections.emptyList();
		}

		List<JstType> jstTypes = new ArrayList<JstType>();
		
		JstType jstType;
		String filePath;
		String pkgPath = null;
		String pkgName = null;
		String clsName = null;
		String src;
		
		for (Entry<URI,String> entry: files.entrySet()){
			filePath = entry.getKey().toString();
			src = entry.getValue();
			if (src == null){
				errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "Source value is null for: " + filePath));
				continue;
			}
			if (filePath != null){
				int index = filePath.lastIndexOf("\\");
				if (index < 0){
					index = filePath.lastIndexOf("/");
				}
				if (index < 0){
					errors.add(new TranslateError(TranslateMsgId.INVALID_PATH, "Invalid path: " + filePath));
					continue;
				}
				pkgPath = filePath.substring(0, index);
				clsName = filePath.substring(index+1);
				index = clsName.indexOf(".");
				if (index > 0){
					clsName = clsName.substring(0, index);
				}
				pkgName = JavaToJsHelper.getPkgNameFromSrc(src);
				if (pkgName == null) {
					errors.add(new TranslateError(TranslateMsgId.INVALID_TYPE, "package is null"));
					continue;
				}
			}
			else {
				errors.add(new TranslateError(TranslateMsgId.INVALID_PATH, "path is null"));
				continue;
			}
			
			try {
				jstType = createJstType(new URL(pkgPath), pkgName, clsName, src, errors);
				if (jstType != null && !m_ctx.isExcluded(jstType)){	
					jstTypes.add(jstType);
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return jstTypes;
	}
	
	private JstType createJstType(
			final URL pkgPath, 
			final String pkgName, 
			final String clsName, 
			final String src, 
			final List<TranslateError> errors){
		
		String clsFullName;
		if (pkgName == null){
			clsFullName = clsName;
		}
		else {
			//Don't map it if the type is native type.
			if (pkgName != null && pkgName.length() > 0) {
				clsFullName = pkgName + "." + clsName;
			} else {
				clsFullName = clsName;
			}
			if (null == DataTypeHelper.getNativeType(clsFullName)) {
				clsFullName = m_ctx.getConfig().getPackageMapping().mapTo(pkgName) + "." + clsName;
			}
		}
		if (m_ctx.isExcludedType(clsFullName)) {
			errors.add(new TranslateError(LogLevel.WARN, TranslateMsgId.EXCLUDED_TYPE, 
					clsFullName + " is disabled in policy."));
			return null;
		}
		JstType jstType = s_jstCache.getType(clsFullName, true);

		if (src != null){
			ASTParser astParser = AstParserHelper.newParser(
					m_ctx.getConfig().shouldParseComments());
			astParser.setSource(src.toCharArray());
			CompilationUnit cu = (CompilationUnit)astParser.createAST(null);		
			jstType.setSource(new JstSource(new AstBinding(pkgPath, pkgName, clsName, cu)));
		}
//		m_ctx.getProvider().getCustomTranslator().initialize(jstType);
		return jstType;
	}
	
	private Map<URI,String> getSourceFiles(final List<Class<?>> srcTypes, final List<TranslateError> errors){
		
		if (srcTypes == null){
			errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, "srcTypes is null"));
			return Collections.emptyMap();
		}

		Map<URI,String> srcFiles = new LinkedHashMap<URI,String>();
		String src;
		for (Class<?> cls: srcTypes){	
			if (cls == null){
				errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, "cls is null"));
				continue;
			}
			URL url = JavaSourceLocator.getInstance().getSourceUrl(cls);
			if (url == null){
				errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, 
					"url for " + cls.getName() + " is null"));
				continue;
			}
		//	path = url.getPath();//JavaToJsHelper.getFileName(cls, ".java");
			try {
				src = JavaToJsHelper.readFromInputReader(new InputStreamReader(url.openStream()));
				if (src == null){
					errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "Source is not found at: " + url.toString()));
					continue;
				}
				/*if (!m_ctx.getConfig().getFilter().accept(src)){
					m_ctx.addExcludedType(TranslateHelper.getTypeName(path));
					continue;
				}*/
				srcFiles.put(url.toURI(), src);
			} catch (IOException e) {
				errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "Can not read source from ["+e.getMessage()+"]: " + url.toString()));
				continue;
			} catch (URISyntaxException e) {
				errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "Can not read source from ["+e.getMessage()+"]: " + url.toString()));
				continue;
			}

		}
		
		return srcFiles;
	}
	
	private List<JstType> targetedTranslation(final Map<URI,String> files, final List<TranslateError> errors){
		
		if (files == null){
			errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, "files is null"));
			return Collections.emptyList();
		}
		
		List<JstType> allList = new ArrayList<JstType>();
		List<JstType> removeList = new ArrayList<JstType>();

		// Parsing Phase
		List<JstType> entryTypes = createJstTypes(files, errors);
		allList.addAll(entryTypes);
		
		// Pre-processing
		preProcess(entryTypes, errors);
		
		// Dependency Phase
		DependencyPhase dependencyPhase = new DependencyPhase(entryTypes, true);
		List<JstType> dependentList = dependencyPhase.translate();
		m_phaseErrors.addAll(dependencyPhase.getErrors());
		addExceptions(dependencyPhase.getExceptions());
		
		// Declaration Phase
		DeclarationPhase declarationPhase;
		while (!dependentList.isEmpty()){
			addToList(allList, dependentList);
			removeList.addAll(preProcess(dependentList, errors));
			declarationPhase = new DeclarationPhase(dependentList, true);
			dependentList = declarationPhase.translate();
			m_phaseErrors.addAll(declarationPhase.getErrors());
			addExceptions(declarationPhase.getExceptions());
		}
		
		// Implementation Phase
		ImplementationPhase implPhase = new ImplementationPhase(dependencyPhase.getStartingTypes());
		List<JstType> jstTypes = implPhase.translate();
		m_phaseErrors.addAll(implPhase.getErrors());
		addExceptions(implPhase.getExceptions());
		
		// Post-processing
		postProcess(allList);

		// Cleanup cache
		JstCache cache = JstCache.getInstance();
		for (JstType type: removeList){
			cache.removeJstType(type);
		}
		
		return jstTypes;
	}
	
	private List<JstType> onDemandTranslation(final Map<URI,String> files, final List<TranslateError> errors){
		
		List<JstType> implList = new ArrayList<JstType>();
		List<JstType> removeList = new ArrayList<JstType>();
		
		// Parsing Phase
		List<JstType> entryTypes = createJstTypes(files, errors);
		implList.addAll(entryTypes);
		
		// Pre-processing
		preProcess(entryTypes, errors);
		
		// Dependency Phase
		DependencyPhase dependencyPhase = new DependencyPhase(entryTypes, true);
		List<JstType> dependentList = dependencyPhase.translate();
		m_phaseErrors.addAll(dependencyPhase.getErrors());
		addExceptions(dependencyPhase.getExceptions());
		
		// Declaration Phase
		DeclarationPhase declarationPhase;
		while (!dependentList.isEmpty()){
			removeList.addAll(preProcess(dependentList, errors));
			declarationPhase = new DeclarationPhase(dependentList, true);
			addToList(implList, dependentList);
			dependentList = declarationPhase.translate();
			m_phaseErrors.addAll(declarationPhase.getErrors());
			addExceptions(declarationPhase.getExceptions());
		}
		
		// Implementation Phase
		ImplementationPhase implPhase = new ImplementationPhase(implList);
		List<JstType> jstTypes = implPhase.translate();
		m_phaseErrors.addAll(implPhase.getErrors());
		addExceptions(implPhase.getExceptions());

		// Post-processing
		postProcess(jstTypes);
		
		// Cleanup cache
		JstCache cache = JstCache.getInstance();
		for (JstType type: removeList){
			cache.removeJstType(type);
		}
		
		return jstTypes;
	}
	
	private List<JstType> preProcess(List<JstType> types, List<TranslateError> errors){
		
		String jstName;
		String srcName;
		URL srcUrl;
		String srcPath;
		String src;
		CompilationUnit cu;
		String pkgPath;
		String pkgName;
		List<JstType> removeList = new ArrayList<JstType>();
		
		for (JstType jstType: types){
			cu = AstBindingHelper.getCompilationUnit(jstType);
			srcName = AstBindingHelper.getSourceName(jstType);
			if (cu == null){
				jstName = jstType.getName();
				if (jstName == null){
					removeList.add(jstType);
					errors.add(new TranslateError(TranslateMsgId.NULL_INPUT, "jstName is null"));
					continue;
				}
				srcName = m_ctx.getConfig().getPackageMapping().mapFrom(jstName);
				srcUrl = JavaSourceLocator.getInstance().getSourceUrl(srcName);
				
				// commented because javascourcelocator has to provide source url
//				if (srcUrl != null){
//					srcPath = srcUrl.getPath();
//				}
//				else {
//					srcPath = JavaToJsHelper.getFileName(srcName, ".java");
//				}
				if (srcUrl == null){
					removeList.add(jstType);
					errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, srcName));
					continue;
				}
				srcPath = srcUrl.toString();
				int index = srcPath.indexOf(srcName.replace(".", "/"));
				pkgPath = srcPath.substring(0, index);
				pkgName = srcPath.substring(index);
				
				index = pkgName.lastIndexOf("/");
				if (index < 0){
					pkgName = null;
				}
				else {
					pkgName = pkgName.substring(0, index).replace("/", ".");
				} 
				pkgPath += pkgName.replace(".", "/");
				
				if (pkgName == null){
					removeList.add(jstType);
					errors.add(new TranslateError(TranslateMsgId.INVALID_PATH, "Fail to extract pkgName: " + pkgPath));
					continue;
				}
				src = JavaSourceLocator.getInstance().getSource(srcUrl);
				if (src == null){
					removeList.add(jstType);
					errors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "failed to read source at " + srcPath));
					continue;
				}
				cu = JavaToJsHelper.toAst(src); 
				if (cu == null){
					removeList.add(jstType);
					errors.add(new TranslateError(TranslateMsgId.FAILED_TO_PARSE, "Failed to parse source at:" + srcPath));
					continue;
				}
				try {
					jstType.setSource(new JstSource(new AstBinding(new URL(pkgPath), pkgName, jstType.getSimpleName(), cu)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return removeList;
	}
	
	private void postProcess(List<JstType> jstTypes){
		List<JstVisitorAdapter> visitors = m_ctx.getConfig().getPostTranslationVisitors();
		for (JstType type: jstTypes){	
			JstDepthFirstTraversal.accept(type, visitors);
		}
	}
	
	private void addToList(final List<JstType> toList, final List<JstType> fromList){
		for(JstType d: fromList){
			if (!toList.contains(d)){
				toList.add(d);
			}
		}
	}
	
	private void addExceptions(Map<JstType,List<Throwable>> exceptions){
		if (exceptions.isEmpty()){
			return;
		}
		List<Throwable> list;
		for (Entry<JstType,List<Throwable>> entry: exceptions.entrySet()){
			if (entry.getValue().isEmpty()){
				continue;
			}
			list = m_exceptions.get(entry.getKey());
			if (list == null){
				list = new ArrayList<Throwable>();
				m_exceptions.put(entry.getKey(), list);
			}
			list.addAll(entry.getValue());
		}
	}

	private ITranslateTracer getTracer(){
		return m_ctx.getTraceManager().getTracer();
	}
	
	private static class Initializer {
		
		private static Initializer s_instance = new Initializer();
		private Initializer(){}
		private static Initializer getInstance(){
			return s_instance;
		}
		
		/**
		 * Collect all dependent initializers, then execute them in reversed order.
		 * Duplicates are ignored.
		 * @param topInitializer ITranslateConfigInitializer
		 */
		private void initialize(ITranslationInitializer topInitializer){
			if (topInitializer == null){
				return;
			}
			Stack<ITranslationInitializer> stack = new Stack<ITranslationInitializer>();
			stack.push(topInitializer);
			collectDependents(topInitializer, stack);
			
			while (!stack.isEmpty()){
				ITranslationInitializer initializer = stack.pop();
				try {
					initializer.initialize();
				}
				catch(Throwable t){
					throw new RuntimeException("Exception when initialize " + initializer.getClass().getName(), t);
				}
			}
		}
		
		/**
		 * Depth First. Skip dependents that have been added to the stack
		 * @param initializer ITranslateConfigInitializer
		 * @param stack Stack<ITranslateConfigInitializer>
		 */
		private void collectDependents(
				final ITranslationInitializer initializer, 
				final Stack<ITranslationInitializer> stack){
			
			if (initializer == null){
				return;
			}
			
			List<ITranslationInitializer> dependents = initializer.getDependents();
			if (dependents == null || dependents.isEmpty()){
				return;
			}
			
			for (ITranslationInitializer d: dependents){
				if (inStackAlready(d, stack)){
					continue;
				}
				stack.push(d);
				collectDependents(d, stack);
			}
		}
		
		private boolean inStackAlready(
				final ITranslationInitializer initializer,
				Stack<ITranslationInitializer> stack){
			
			Iterator<ITranslationInitializer> itr = stack.iterator();
			while (itr.hasNext()){
				if (itr.next().getClass() == initializer.getClass()){
					return true;
				}
			}
			
			return false;
		}
	}
}