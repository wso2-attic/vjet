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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.javatojs.trace.ITranslateTracer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.trace.TranslationTraceId;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.javatojs.util.VjoFiler;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.logger.LogLevel;

/**
 * This class provides entry point and process control of java to js 
 * translation. It should be the integration point with prebuild process 
 * as well as eclipse plugin.
 */
public final class BuildController {
	
	private TranslationController m_tController;
	private List<JstType> m_jstTypes;
	private List<TranslateError> m_directErrors = new ArrayList<TranslateError>();
	
	private boolean m_verbose = false;
	private boolean m_genVjo = true;
	private boolean m_genJsr = false;
	private boolean m_useOnDemand = true;
	private boolean m_includeChildPkgs = true;
	
	private ICodeGenPathResolver m_codeGenPathResolver = ICodeGenPathResolver.DEFAULT;
	
	private static final TraceId PKG = TranslationTraceId.PKG;
	private static final TraceId FILE = TranslationTraceId.FILE;
	
    
    private DapJsrGenListener m_jsrListener = new DapJsrGenListener();

	/**
	 * Constructor
	 */
	public BuildController(){
		this(null);
	}
	
	/**
	 * Constructor
	 * @param configInitializer ITranslateConfigInitializer
	 */
	public BuildController(ITranslationInitializer configInitializer){
		if (configInitializer == null){
			m_tController = new TranslationController(new DefaultTranslationInitializer());
		}
		else {
			m_tController = new TranslationController(configInitializer);
		}
	}
	
	//
    // API
    //
	public boolean isParallelEnabled() {
		return m_tController.isParallelEnabled();
	}

	public BuildController enableParallel(boolean enableParallel) {
		m_tController.enableParallel(enableParallel);
		return this;
	}

	public boolean isEnableTrace() {
		return m_tController.isTraceEnabled();
	}

	public BuildController enableTrace(boolean enableTrace) {
		m_tController.enableTrace(enableTrace);
		return this;
	}
	
	/**
	 * Answer the controller for Java to JST translation
	 * @return TranslationController
	 */
	public TranslationController getTranslateController(){
		return m_tController;
	}
	
	/**
	 * Answer whether VJO will be generated as part of the build
	 * @return boolean
	 */
	public boolean isGenVjo() {
		return m_genVjo;
	}

	/**
	 * Set whether to gen VJO as part of the build
	 * @param genVjo boolean
	 */
	public BuildController setGenVjo(boolean genVjo) {
		m_genVjo = genVjo;
		return this;
	}

	/**
	 * Answer whether JSR will be generated as part of the build
	 * @return boolean
	 */
	public boolean isGenJsr() {
		return m_genJsr;
	}

	/**
	 * Set whether to gen JSR as part of the build
	 * @param genJsr boolean
	 */
	public BuildController setGenJsr(boolean genJsr) {
		m_genJsr = genJsr;
		return this;
	}

	/**
	 * Answer whether there will be verbose msg in stdout
	 * @return boolean
	 */
	public boolean isVerbose() {
		return m_verbose;
	}
	
	/**
	 * Answer whether to use on-demand translation for build
	 * @return
	 */
	public boolean shouldUseOnDemand() {
		return m_useOnDemand;
	}

	public BuildController setUseOnDemand(boolean onDemand) {
		m_useOnDemand = onDemand;
		return this;
	}

	/**
	 * Set whether to produce verbose msg in stdout
	 * @param verbose boolean
	 */
	public BuildController setVerbose(boolean verbose) {
		m_verbose = verbose;
		return this;
	}
	
	/**
	 * Answers whether to translate the child packages recursively if builder is
	 * given a package directory path
	 * @return boolean 
	 */
	public boolean shouldIncludeChildPkgs() {
		return m_includeChildPkgs;
	}

	/**
	 * Set whether to translate the child packages recursively
	 * @param value if true, include files in the child packages recursively.
	 * @return BuildController
	 */
	public BuildController setIncludeChildPkgs(boolean value) {
		m_includeChildPkgs = value;
		return this;
	}
	
	public void setCodeGenPathResolver(ICodeGenPathResolver resolver) {
		m_codeGenPathResolver = resolver;
	}
	
	/**
	 * Translate java files and their dependent files to VJO
	 * @param fileList
	 */
	public void buildFiles(List<URL> fileList) {
		
		ITranslateTracer tracer = getTracer();

		tracer.startGroup(FILE, new TraceAttr("name", getClass().getSimpleName()));
		
		try {
			translateSource(fileList);
			if (shouldGenerateFiles()) {
				try {
					generateFiles();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		finally {
			tracer.endGroup(PKG);
			getCtx().getTraceManager().close();
		}
	}

	/**
     * Translate java files in the given package and their dependent files to VJO
     * 
     * @param String pkgPath full path of the package
     */
	public void buildPackage(URL pkgPath){
		
		if (pkgPath == null){
			return;
		}
		
		ITranslateTracer tracer = getTracer();

		tracer.startGroup(PKG, new TraceAttr("name", pkgPath.toString()));
		
		try {
			
			out("-> Get java files ...");

			// Get files
			// 
			List<URL> fileList = new ArrayList<URL>();
			try {
				getFiles(pkgPath, fileList);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if (fileList.size() == 0) {
				out("<- No java files for Java2Js translation found");
				return;
			}
			
			translateSource(fileList);
			if (shouldGenerateFiles()) {
				try {
					generateFiles();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		finally {
			tracer.endGroup(PKG);
			getCtx().getTraceManager().close();
		}
	}

	public List<TranslateError> getDirectErrors(){
		return Collections.unmodifiableList(m_directErrors);
	}
	
	public void clearDirectErrors(){
		m_directErrors.clear();
	}
	
	public List<TranslateError> getTranslateLog(LogLevel logLevel){
		List<TranslateError> errors = new ArrayList<TranslateError>();
		for (TranslateError e : getAllErrors()) {
			if (e.getLevel() == logLevel) {
				errors.add(e);
			}
		}
		return errors;
	}
	
	public List<TranslateError> getAllErrors(){
		
		List<TranslateError> errors = new ArrayList<TranslateError>(m_directErrors);
		
		if (m_tController != null){
			errors.addAll(m_tController.getErrors());
		}
		
		return errors;
	}
	
	public Map<JstType,List<Throwable>> getAllExceptions(){
		
		if (m_tController == null){
			return Collections.emptyMap();
		}

		return m_tController.getExceptions();
	}
	
	/**
	 * Returns a list of JstTypes translated as part of the last build
	 * method invocation
	 * @return List<JstType>
	 */
	public List<JstType> getTranslatedJstTypes() {
		if (m_jstTypes == null) {
			return Collections.emptyList();
		}
		return m_jstTypes;
	}
	
	public void reset() {
		m_tController.reset();
	}
	
	//
	// Package Protected
	//
	
	//
	// Private
	//
	private TranslateCtx getCtx(){
		return m_tController.getCtx();
	}
	
	private void translateSource(List<URL> fileList) {
		if (fileList == null || fileList.size() == 0) {
			return;
		}
		
		out("-> Read java sources ...");
		
		// Get Source
		Map<URI,String> files = new LinkedHashMap<URI,String>();
		String src;
		for (URL url: fileList){
			try {
								
				if (!getCtx().getConfig().getFileFilter().accept(url)) {
					out("Skipped code generation for codegen'd file " + url.toString());
					continue;
				}
				out(url.toString());
				src = JavaToJsHelper.readFromInputReader(new InputStreamReader(url.openStream()));
				/*if (!m_ctx.getConfig().getFilter().accept(src)){
					m_ctx.addExcludedType(TranslateHelper.getTypeName(f.getPath()));
					continue;
				}*/

					files.put(url.toURI(),src);
	
			} catch (IOException e) {
				m_directErrors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "IO Error while reading source : "+url.toString()));
			} catch (URISyntaxException e) {
				m_directErrors.add(new TranslateError(TranslateMsgId.SRC_NOT_FOUND, "URISyntaxException, Error while reading source : "+url.toString()));
			}
		}
		
		out("-> Translate java sources to JST ...");
		
		// Translate to JST
		List<JstType> jstTypes = null;
		if (m_useOnDemand) {
			jstTypes = m_tController.onDemandTranslation(files);
		} else {
			jstTypes = m_tController.targetedTranslation(files);
		}
		
		m_jstTypes = new ArrayList<JstType>(jstTypes.size());
		TranslateCtx ctx = getCtx();
		for (JstType jstType: jstTypes){
			if (ctx.isJavaOnly(jstType)
					|| ctx.isMappedToJS(jstType)
					|| ctx.isExcluded(jstType)
					|| ctx.isJSProxy(jstType)){
				continue;
			}
			m_jstTypes.add(jstType);
		}		
	}
	
	private void generateFiles() throws MalformedURLException {
		// Generate VJO/JSR
		GeneratorCtx gCtx;
		VjoGenerator vjoGenerator;
		JsrGenerator jsrGenerator;
		URL filePath;
		for (JstType jstType: m_jstTypes){
			gCtx = new GeneratorCtx(CodeStyle.PRETTY); // TODO need to fix writer, shouldn't create ctx each time.
			if (m_genVjo) {
				out("-> Generate VJO  for: " + jstType.getName() + "...");
				vjoGenerator = gCtx.getProvider().getTypeGenerator();
				vjoGenerator.writeVjo(jstType);
				filePath = m_codeGenPathResolver.getVjoFilePath(jstType);
				VjoFiler.writeToFile(filePath, vjoGenerator.getGeneratedText());
				out("Write " + filePath);
			}
			if (m_genJsr) {
				out("-> Generate JSR for: " + jstType.getName() + "...");
				StringWriter buffer = new StringWriter();
				jsrGenerator = 
					new JsrGenerator(new PrintWriter(buffer), gCtx.getStyle());
				jsrGenerator.addListener(m_jsrListener);
				jsrGenerator.writeJsr(jstType, true);
				filePath = m_codeGenPathResolver.getJsrFilePath(jstType);;
				VjoFiler.writeToFile(filePath, buffer.toString());
				out("Write " + filePath);
			}
		}	
	}
	
	private boolean shouldGenerateFiles() {
		if (m_genVjo || m_genJsr) {
			return true;
		}
		return false;
	}
    
	private void getFiles(URL dir, List<URL> list) throws IOException, URISyntaxException{
    	if (dir == null) {
    		return;
    	}

    	if (!shouldIncludeChildPkgs()) {
        	JavaToJsHelper.getDirectFiles(dir, list, getCtx().getConfig().getFileFilter());
    	}else{
        	JavaToJsHelper.getFiles(dir, list, getCtx().getConfig().getFileFilter(),true);
    	}
	    
	}

	private ITranslateTracer getTracer(){
		return getCtx().getTraceManager().getTracer();
	}
	
	private void out(String s) {
		if (m_verbose) {
			System.out.println(s);	// KEEPME
		}
	}
}