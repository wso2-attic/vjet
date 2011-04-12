/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jsgen.shared.generate.NativeJsProxyGenerator;
import org.ebayopensource.dsf.jsgen.shared.util.CodeGenCleaner;
import org.ebayopensource.dsf.jst.FileBinding;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.loader.OnDemandJstTypeLoader;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader.FileSuffix;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateConfig;
import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.lib.TsLibLoader;

/**
 * The build controller perform the following stages
 * 
 * If required 
 * 	Load typespace
 * else
 * 	Use existing typespace
 * Parse the Js into JST
 * Resolve the JST based on the typespace
 * For each type generate JSR + NJP -- done
 * 
 * For each VJO package generate generate Package Spec
 * 
 * 
 *
 */


public class BuildController {

	private final static String JS_FILE_EXTENSION = ".js";

	private final static String JSR_FILE_EXTENSION = "Jsr.java";

	// API
	
	private Collection<IJstType> m_typesToProcess;
	
	private GeneratedResult m_result;
	
	private FileSuffix[] m_suffix = {FileSuffix.js};
	
	private IJstTypeLoader m_jstTypeLoader;


	public JstTypeSpaceMgr loadTypes(String group, String groupPath, String srcDir){
		List<String> srcDirs = new ArrayList<String>(1);
		srcDirs.add(srcDir);
		return loadTypes(group, groupPath, srcDirs);
	}
	
	public JstTypeSpaceMgr loadTypes(String group, String groupPath, String[] srcDirs){
		List<String> list = new ArrayList<String>(srcDirs.length);
		for(String f:srcDirs){
			list.add(f);
		}
		
		return loadTypes(group, groupPath, list);
	}
	
	/**
	 * This is required for loading types for command line build
	 * 
	 * @param group
	 * @param groupPath
	 * @param srcDirs
	 * @return TODO
	 */
	public JstTypeSpaceMgr loadTypes(String group, String groupPath, List<String> srcDirs){
		
		TranslateConfig cfg = new TranslateConfig();
		cfg.setSkiptImplementation(true);
		
		VjoParser p = new VjoParser(cfg);
		
		// TODO jearly
//		ctx.setAllowPartialJST(false);  // DO NOT ALLOW SYNTAX ERRORS IN GEN CODE
//		VjoParseAndReportErrors p = new VjoParseAndReportErrors(ctx);
//		
//		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
//		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
//		p.addLib(LibManager.getInstance().getBrowserTypesLib());
//		p.addLib(LibManager.getInstance().getVjoBaseTypesLib());
		
		JstParseController c = new JstParseController(p);
		// Load only .js files into typespace .vjo files are excluded from code gen
//		m_suffix = {FileSuffix.js};
		
		JstTypeSpaceMgr mgr = new JstTypeSpaceMgr(c, getJstTypeLoader());
		TsLibLoader.loadDefaultLibs(mgr);
		mgr.initialize();
		
		
		// problem with srcDirs being sourceFiles not source directories...
		// 
		
		mgr.processEvent(createBatchGroupEvent(group, groupPath, srcDirs));
		m_typesToProcess = mgr.getTypeSpace().getGroup(group).getEntities().values();
		
		processMissingTypes(mgr, mgr.getQueryExecutor().findMissingTypes());

		return mgr;
	
	}

	private void processMissingTypes(JstTypeSpaceMgr mgr, List<TypeName> findMissingTypes) {
		IJstType OBJECT = JstCache.getInstance().getType("vjo.Object");
		JavaSourceLocator locator = JavaSourceLocator.getInstance();
		for(TypeName type: findMissingTypes){
			System.out.print(type.typeName() + " : ");
			URL url = locator.getSourceUrl(type.typeName(), ".js");
			IJstType typeFromCache = JstCache.getInstance().getType(type.typeName());
			if(typeFromCache instanceof JstType){
				JstType wcJstType = (JstType)typeFromCache;
				wcJstType.addExtend(OBJECT);
				System.out.println(url);
			}
		}
	}

	public JstTypeSpaceMgr loadType(String group, IJstType type){
		TranslateConfig cfg = new TranslateConfig();
		cfg.setSkiptImplementation(true);
		
		VjoParser p = new VjoParser(cfg);
		
		// removed controller already does this
//		p.addLib(LibManager.getInstance().getJsNativeGlobalLib());
//		p.addLib(LibManager.getInstance().getJavaPrimitiveLib());
//		p.addLib(LibManager.getInstance().getBrowserTypesLib());
//		p.addLib(LibManager.getInstance().getVjoBaseTypesLib());
//		p.addLib(LibManager.getInstance().getVjoSelfDescLib());
		
		JstParseController c = new JstParseController(p);
		
		JstTypeSpaceMgr mgr = new JstTypeSpaceMgr(c,new OnDemandJstTypeLoader(group,type));
		TsLibLoader.loadDefaultLibs(mgr);
		mgr.initialize();
				
		mgr.processEvent(createBatchGroupEvent(group, null, null));
		
		if(type!=null){
			m_typesToProcess = mgr.getTypeSpace().getGroup(group).getEntities().values();
		}
		return mgr;
	
	}
	// TODO
	public BuildController validate(IJstType type){
		
		return this;
		
	}
	//TODO
	public BuildController validateAll(Collection<IJstType>  type){
		
		return this;
		
	}
	

	public FileSuffix[] getSuffixes() {
		return m_suffix;
	}
	
	public void setSuffixes(FileSuffix[] suffixes) {
		m_suffix = suffixes;
	}

	public BuildController generate(File file){
		
		return this;
	}
	
	public BuildController generate(File file, GenerationConfig config) throws IOException{
		IJstType type = lookUpFromFile(file);
		return generate(type, config);
	}
	
	/* this is problematic method if js file has
	 * more than one type only works if one type in file
	 */
	
	private IJstType lookUpFromFile(File file) {
//		for(File srcPath  )
		return null;
	}

	public BuildController generate(IJstType type, GenerationConfig config)throws IOException{
		m_result = new GeneratedResult();
		BuildInfo info = createBuildInfo(type,config);
		generateJava(m_result, type, info,config);
		return this;
	}
	


	/**
	 * Generation by List of IJstTypes
	 * 	
	 * @throws IOException 
	 */
	public BuildController generateAll() throws IOException{
		return generateAll(new GenerationConfig());
		
	}
	public BuildController generateAll(GenerationConfig config) throws IOException{
		Collection<IJstType> types = getTypesToProcess();
		if(types.size()==0){
			System.err.println("Found nothing to generateJs 2 Jsr/Native Proxy ");
			return this;
		}
		
		
		
		m_result = new GeneratedResult();
		for(IJstType t : types){
			BuildInfo info = createBuildInfo(t,config);
			if(info!=null){
				generateJava(m_result, t, info, config);			
				
			}
			
		}
		generatePkgSpecs(config);
		return this;
	
	}
	
	private BuildController generatePkgSpecs(GenerationConfig config){
		Collection<IJstType> types = getTypesToProcess();
		Map<String,String> processedPkgs = new HashMap<String, String>();
		for(IJstType t:types){
			
			// skip inner types
			//
			if (t.isEmbededType()) {
				continue;
			}
			
			if (t.getSource() == null || t.getSource().getBinding() == null) {
				System.out.println("JstType " + t.getName() + " has null source binding!");
				continue;
			}
			
			if (t.getPackage() == null) {
				System.out.println("JstType " + t.getName() + " has empty package!");
				continue;
			}
			String parentDir;
			if (config.getOutputDir() != null) {
				parentDir = config.getOutputDir().getAbsolutePath();
			} else {
				parentDir = ((FileBinding)t.getSource().getBinding()).getFile().getParent();
			}
//			String pkg = t.getPackage().getName();
//			if(processedPkgs.get(pkg)==null){
//				try {
//					new JsPackageSpecGenerator(parentDir, pkg).generate();
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				processedPkgs.put(pkg, parentDir);
//			}
		}
		return this;
	}
	
	/**
	 * 
	 * @param groupName
	 * @param groupPath
	 * @param srcDirs
	 * @return
	 */
	
	private ISourceEvent createBatchGroupEvent(String groupName, String groupPath, List<String> srcDirs) {
		return new AddGroupEvent(groupName, groupPath, srcDirs, null);
	}
	
	private BuildInfo createBuildInfo(IJstType t, GenerationConfig config) {
		
		if(t.getSource()==null){
			return null;
		}
		FileBinding fileBinding = ((FileBinding) t.getSource().getBinding());
		if(fileBinding==null){
			return null;
		}
		
		File jsFile = fileBinding.getFile();
		String pkg = t.getPackage().getName();
		File outputDir = fileBinding.getFile().getParentFile();
		if(config.getOutputDir()!=null){
			String rootOutput = config.getOutputDir().getAbsolutePath();
			String pkgPath = t.getPackage().getName().replace('.', File.separatorChar);
			outputDir = new File(rootOutput+File.separatorChar+pkgPath);
		}
		
		File jsrFile = getVjo3OutputFile(t.getPackage().getName(),jsFile, outputDir);
		File nativeJsProxyFile = new File(outputDir, t.getSimpleName()
				+ ".java");
		return new BuildInfo(fileBinding, jsFile, pkg, outputDir, jsrFile, nativeJsProxyFile);
	}

	static class BuildInfo{
		
		private final File m_file;
		private final FileBinding m_fileBinding;
		private final String m_pkg;
		private final File m_outputDir;
		private File m_jsrFile;
		private File m_njpFile;
		
		BuildInfo(FileBinding fileBinding, File jsFile, String pkg, File outputDir, File jsrFile, File njpFile){
			m_file = jsFile;
			m_fileBinding = fileBinding;
			m_pkg = pkg;
			m_outputDir = outputDir;
			m_jsrFile = jsrFile;
			m_njpFile = njpFile;
		}

		File getFile() {
			return m_file;
		}


		FileBinding getFileBinding() {
			return m_fileBinding;
		}

	

		String getPkg() {
			return m_pkg;
		}


		File getOutputDir() {
			return m_outputDir;
		}

		private File getJsrFile() {
			return m_jsrFile;
		}
		private File getNJPFile() {
			return m_njpFile;
		}

		
	}

	/**
	 * 
	 * @param jstType
	 * @param config 
	 * @return
	 * @throws IOException
	 */

	private static IJstType generateJava(GeneratedResult result, IJstType jstType, BuildInfo info, GenerationConfig config)
			throws IOException {

		// If the input .js file was codegen'd, we skip codegen of JSR and 
		// native proxy.
		boolean isInputFileCodeGened = CodeGenCleaner.isCodeGened(info.getFile(), false);
		
		if(config.isGenJsr()){
			// For JSR code generation the input file must not be codegen'd 
			// and output file if exists must be codegen'd.
			if (!isInputFileCodeGened && 
					isOutputFileCodeGened(info.getJsrFile())) {
				FileWriter jsrWriter = new FileWriter(info.getJsrFile());
				generateJsr(jstType, jsrWriter);
				result.addFile(info.getFile(), info.getJsrFile());
			}
		}

		if(config.isGenNJP()){
			// For NJP code generation the input file must not be codegen'd 
			// and output file if exists must be codegen'd.
			if (!isInputFileCodeGened &&
					isOutputFileCodeGened(info.getNJPFile())) {
				generateNativeProxy(jstType, info);
				result.addFile(info.getFile(), info.getNJPFile());
			}
		}
		return jstType;
	}

	private static boolean isOutputFileCodeGened(File file) {
		if (!file.exists()) {
			return true;
		}
		return CodeGenCleaner.isCodeGened(file, true);
	}

	private static void generateJsr(IJstType jstType, FileWriter jsrWriter) {
		
		JsrGenerator generator = new JsrGenerator(new PrintWriter(jsrWriter), CodeStyle.PRETTY);
		try {
			generator.writeJsr(jstType);
			jsrWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}catch(Exception e){
			throw new DsfRuntimeException("type could not be generated" + jstType.getName(), e);
		}
		
	}

	/** copy the mixin property and methods into the target type
	 * 
	 * @param jstType
	 */



	private static void generateNativeProxy(IJstType jstType, BuildInfo info
			) throws IOException {
		FileWriter writerForNativeJsProxy = null;
		if (!skipProxyGen(jstType)) {
			File nativeJsProxyFile = info.getNJPFile();
			
			if (!nativeJsProxyFile.exists()) {
				nativeJsProxyFile.createNewFile();
			}
			if (nativeJsProxyFile.canWrite()) {
				writerForNativeJsProxy = new FileWriter(nativeJsProxyFile);
			}
		}
		
		
		if (writerForNativeJsProxy != null) {
			new NativeJsProxyGenerator(new PrintWriter(writerForNativeJsProxy),
					CodeStyle.PRETTY).writeProxy(jstType);
			writerForNativeJsProxy.flush();
			writerForNativeJsProxy.close();
//			System.out.println("Proxy class generated: " + jstType.getName()
//					+ ".java");
		}
		

		
	}
	
	/**
	 * 
	 * @param jstType
	 * @return
	 */
	private static boolean skipProxyGen(IJstType jstType) {
		if (jstType.getName().startsWith("vjo.java.")) {
			return true;
		}
		if (jstType.isInterface() || jstType.isEnum()) {
			return false;
		}
		if (jstType.isClass() || jstType.isOType()) {
			String typeName = jstType.getSimpleName();
			if ("Object".equals(typeName) || "Class".equals(typeName)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static File getVjo3OutputFile(String pkg, File jsFile, File outputDir) {

		String outputFileName = "";
//		File jsFile = info.getFile();
//		String pkg = type.getPackage().getName();
//		File outputDir = outputDir;

		if (outputDir != null && pkg != null) {
//			String pkgAsURI = pkg.replace('.', File.separatorChar);
			String jsrName = jsFile.getName().substring(0,
					jsFile.getName().indexOf("."));

			File f = new File(outputDir, "");
			if (!f.exists()) {
				f.mkdirs();
			}
			outputFileName = f.getAbsolutePath() + File.separatorChar + jsrName
					+ JSR_FILE_EXTENSION;
		} else {
			outputFileName = jsFile.getPath().replace(JS_FILE_EXTENSION,
					JSR_FILE_EXTENSION);
		}

		return new File(outputFileName);
	}

	public Collection<IJstType> getTypesToProcess() {
		if(m_typesToProcess==null){
			throw new DsfRuntimeException("no types to process, loading must be done first");
		}
		return m_typesToProcess;
	}

//	/**
//	 * API for providing list of resolved JstTypes to 
//	 * be code generated from. The JstSource binding must
//	 * be a file binding for this to work currently.
//	 * @param typesToProcess
//	 */
//	
//	public void setTypesToProcess(List<IJstType> typesToProcess) {
//		m_typesToProcess = typesToProcess;
//	}



	public void clean() {
		if(m_result!=null){
		clean(m_result);
		}
		
	}
	public void clean(GeneratedResult result) {
		List<File> files = result.getAllGeneratedFiles();
		for(File f:files){
			f.delete();
		}
		
	}


	public void printOut(PrintStream out, GeneratedResult result) {
		if(result==null){
			return;
		}
		for(String key : result.getAllSourceFiles()){
			out.println("source:" + key);
			for(File f : result.getGenFilesForSource(key)){
				out.println("\t" + f);
			}
		}
		
	}


	public GeneratedResult getResult() {
		return m_result;
	}

	public IJstTypeLoader getJstTypeLoader() {
		if (m_jstTypeLoader == null) {
			m_jstTypeLoader = new DefaultJstTypeLoader(getSuffixes());
		}
		return m_jstTypeLoader;
	}

	public void setJstTypeLoader(IJstTypeLoader typeLoader) {
		m_jstTypeLoader = typeLoader;
	}

}
