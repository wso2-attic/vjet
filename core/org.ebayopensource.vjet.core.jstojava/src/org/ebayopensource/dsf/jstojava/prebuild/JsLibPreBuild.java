/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jstojava.prebuild;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
//import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
//import org.ebayopensource.dsf.jsgen.shared.generate.NativeJsProxyGenerator;
//import org.ebayopensource.dsf.jsgen.shared.util.CodeGenCleaner;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.declaration.JstCache;
//import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
//import org.ebayopensource.dsf.jst.ts.IJstTypeLoader.SourceType;
//import org.ebayopensource.dsf.jstojava.codegen.JsCodeGenHelper;
//import org.ebayopensource.dsf.jstojava.codegen.JsMeta;
//import org.ebayopensource.dsf.jstojava.codegen.JsPackageSpecGenerator;
//import org.ebayopensource.dsf.jstojava.codegen.JsPreGenHelper;
//import org.ebayopensource.dsf.jstojava.codegen.JsRefGener;
//import org.ebayopensource.dsf.jstojava.codegen.inspector.JsMetaCtx;
//import org.ebayopensource.dsf.jstojava.codegen.inspector.JsMetaDebugLevel;
//import org.ebayopensource.dsf.jstojava.controller.BuildController;
//import org.ebayopensource.dsf.jstojava.controller.GenerationConfig;
//import org.ebayopensource.dsf.jstojava.controller.NativeJsLibProvider;
//import org.ebayopensource.dsf.jstojava.exception.DsfJs2JavaExceptionHelper;
//import org.ebayopensource.dsf.jstojava.importer.IJsCodeImporter;
//import org.ebayopensource.dsf.jstojava.loader.CodeGenJstTypeLoader;
//import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
//import org.ebayopensource.dsf.jstojava.parser.VjoParser;
//import org.ebayopensource.dsf.jstojava.translator.TranslateConfig;
//import org.ebayopensource.dsf.ts.type.TypeName;
//
///**
// * Packages JavaScript library for use in the pre-build step.
// * 
// * 
// *
// */
//public class JsLibPreBuild {
//
//	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
//
//	private final static String JS_FILE_EXTENSION = ".js";
//	
//	private final static String JSR_FILE_EXTENSION = "Jsr.java";
//
//	private final static List<String> s_skipJsrGenFolders = new ArrayList<String>();
//	private final static List<String> s_skipJsrGenFoldersAll = new ArrayList<String>();
//
//	private static boolean m_init;
//	static {
//		s_skipJsrGenFoldersAll.add("j2j");
//		s_skipJsrGenFolders.add("generated");
//	}
//	
//	private boolean m_returnJstList = false;
//	private List<IJstType> m_jstList = null;
//
//	private Map<File, JsPkgInfo> m_directories;
//
//	private File[] m_jsSourceDir = new File[5];
//
//	private final int m_debugLevel;
//
//	private JsPackageSpecGenerator m_gen;
//
//	private String[] m_exclusion;
//
//	private File m_outputDir;
//	
//	// the root source directories 
//	private String[] m_sourceDirs = null;
//
//	private String m_groupPath;
//	
//	/**
//	 * 
//	 * @param baseDir location of the JavaScript files.
//	 * @param debugLevel 
//	 */
//	public JsLibPreBuild(String baseDir, int debugLevel) {
//		super();
//		m_debugLevel = debugLevel;
//		m_jsSourceDir[0] = new File(baseDir);
//		//m_directories = new HashMap<File, JsPkgInfo>(20);
//	}
//
//	/**
//	 * 
//	 * @param baseDir location of the JavaScript files.
//	 * @param debugLevel 
//	 */
//	public JsLibPreBuild(String baseDir, String outputDir, int debugLevel) {
//		super();
//		m_debugLevel = debugLevel;
//		m_jsSourceDir[0] = new File(baseDir);
//		m_outputDir = new File(outputDir);
//		//m_directories = new HashMap<File, JsPkgInfo>(20);
//	}
//	/**
//	 * 
//	 * @param sourceDirs source directories
//	 * @param debugLevel
//	 */
//	public JsLibPreBuild(File[] sourceDirs, String[] exclusion, int debugLevel) {
//		super();
//		m_debugLevel = debugLevel;
//		m_jsSourceDir = sourceDirs;
//		m_exclusion = exclusion;
//		//m_directories = new HashMap<File, JsPkgInfo>(20);
//	}
//	/**
//	 * 
//	 * @param sourceDirs source directories
//	 * @param debugLevel
//	 */
//	public JsLibPreBuild(File[] sourceDirs, String outputDir, String[] exclusion, int debugLevel) {
//		super();
//		m_debugLevel = debugLevel;
//		m_jsSourceDir = sourceDirs;
//		m_exclusion = exclusion;
//		m_outputDir = (outputDir!=null && !"".equals(outputDir))?new File(outputDir) : null;
//		//m_directories = new HashMap<File, JsPkgInfo>(20);
//	}
//
//	/**
//	 *   Generates the package file.
//	 *
//	 */
//	public void execute() {
//		//Clear cache before translation
//		
//		for(File f:m_jsSourceDir){
//			if(f != null && !f.isDirectory()){
//				DsfExceptionHelper.chuck("Src directories received file rather than directory " + f);
//			}
//		}
//		
//		JstCache.getInstance().clear();
//		
//		GenerationConfig config = new GenerationConfig();
//		if (m_outputDir != null && !m_outputDir.exists()) {
//			m_outputDir.mkdirs();
//		}
//		config.setOutputDir(m_outputDir);
//		
//		BuildController c = new BuildController();
//		c.setJstTypeLoader(new CodeGenJstTypeLoader());
//		c.loadTypes(m_groupPath, m_groupPath, m_sourceDirs);
//		try {
//			c.generateAll(config).printOut(System.out, c.getResult());
//		} catch (IOException e) {
//			e.printStackTrace();
//		};
//		
//		if (shouldReturnJstList()) {
//			for (IJstType jst : c.getTypesToProcess()) {
//				getJstList().add(jst);
//			}
//		}
//		
//		for (File sourceDir : m_jsSourceDir) {
//			if (sourceDir != null) {
//				codeGenJsr(sourceDir, null); //TODO remove this after vjo upgrade complete
//			}
//		}
//		
//		codeGenPkgSpec();
//	}
//	
//	/**
//	 * Return list of translated JstTypes following call to execute() method. 
//	 * Returns an empty list if setReturnJstList was not set to true priot to
//	 * calling execute() method.
//	 * @return list of JstTypes. 
//	 */
//	public List<IJstType> getJstList() {
//		if (m_jstList == null) {
//			m_jstList = new ArrayList<IJstType>();
//		}
//		return m_jstList;
//	}
//	
//	public boolean shouldReturnJstList() {
//		return m_returnJstList;
//	}
//
//	public void setReturnJstList(boolean value) {
//		m_returnJstList = value;
//	}
//
//	private void codeGenPkgSpec() {
//		for (File dir : getDirectories().keySet()) {
//			JsPkgInfo pkg = getDirectories().get(dir);
//			if (getDirectories().get(dir) != null) {
//				File outputDir = m_outputDir==null ? dir : m_outputDir;
//				m_gen = new JsPackageSpecGenerator(outputDir, pkg.getPkgName());
//				m_gen.generate();
//			}
//		}
//
//	}
//
//	private void codeGenJsr(File dir, File lastDir) {
//		if (dir == null) {
//			return;
//		}
//		if (isExcluded(dir)) {
//			return;
//		}
//		if (m_debugLevel > 0) {
//			JsMetaCtx.ctx().setDebuglevel(JsMetaDebugLevel.error);
//		}
//		if (dir.isDirectory()) {
//			lastDir = dir;
//			for (File file : dir.listFiles()) {
//				if (m_debugLevel > 0) {
//					System.out.println("dir=" + file.getPath());
//				}
//				codeGenJsr(file, lastDir); // RECURSIVE
//			}
//		} else {
//			genFile(dir);
//		}
//	}
//
//	private void genFile(File file) {
//		//System.out.println(dir.getAbsolutePath());
// 		if (isSkipJsrGen(file)) {
// 			if (m_debugLevel > 0) {
//				System.out.println("Skipped for JSR gen: " + file.getName());
//			}
//			return;
// 		}
//		if (file.getName().endsWith(JS_FILE_EXTENSION)) {
//			if (m_debugLevel > 0) {
//				System.out.println("Js file path: " + file.getAbsolutePath());
//			}
//			JsMeta preGenImport = JsPreGenHelper.getMetaFromAnnotations(JsPreGenHelper.file2String(file));
//			String classN = file.getName().substring(0, file.getName().indexOf("."));
//			preGenImport.setClass(classN);
//			if (!(preGenImport.isVjo() || preGenImport.isVjo3())
//					&& preGenImport.getPackage() == null) {
//				if (m_debugLevel > 0) {
//					System.out.println("non vjo file");
//				}
//				return;
//			}
//			try {
//				if (preGenImport.isVjo3()) {
////					genVjo3(file);
//				} else {
//					genVjo(file, preGenImport);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				DsfJs2JavaExceptionHelper.chuck(
//						"\n[error] Js2Java proxy generation issue for file: "
//								+ file.getAbsolutePath(), e);
//			}
//		}
//	}
//
//	private boolean isSkipJsrGen(File file) {
//		// skip if this is a codegen'd file
//		if (CodeGenCleaner.isCodeGened(file, false)) {
//			if (m_debugLevel > 0) {
//				System.out.println("Skipped code generated file " + file.getAbsolutePath());
//			}
//			return true;
//		}
//		
//		for (String item: s_skipJsrGenFolders) {
//			if (file.getParent().endsWith(FILE_SEPARATOR + item)) {
//				return true;
//			}
//		}
//		
//		for (String item : s_skipJsrGenFoldersAll) {
//			if (file.getAbsolutePath().indexOf(FILE_SEPARATOR + item + FILE_SEPARATOR) > -1) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private void genVjo(File dir, JsMeta preGenImport) throws IOException, UnsupportedEncodingException, FileNotFoundException {
//		JsRefGener gen = new JsRefGener(dir, m_outputDir, m_debugLevel);
//		File genFile = gen.generate();
//		if (genFile == null) {
//			return;
//		}
//		IJsCodeImporter importer = gen.getImporter();
//		if (m_debugLevel > 0) {
//			System.out
//					.println("js file getPath = " + dir.getPath());
//			System.out.println("js file getCanonicalPath = "
//					+ dir.getCanonicalPath());
//			System.out.println("js file getAbsolutePath ="
//					+ dir.getAbsolutePath());
//			System.out.println("contents of js:\n"
//					+ FileUtil.readFile(dir.getCanonicalPath(),
//							"utf-8"));
//			System.out.println("contents of jsr:\n"
//					+ FileUtil.readFile(genFile.getCanonicalPath(),
//							"utf-8"));
//			System.out.println("genFile path ="
//					+ genFile.getCanonicalPath());
//			System.out.println("path = " + dir.getCanonicalPath());
//			if (importer != null) {
//				System.out.println("importer value = " + importer);
//				System.out.println("header value = "
//						+ importer.getJsMetaObj());
//				System.out.println("package value = "
//						+ importer.getJsMetaObj().getPackage());
//			}
//		}
//		JsMeta jsHeader = null;
//		String packageName = null;
//		if (importer != null) {
//			jsHeader = importer.getJsMetaObj();
//			packageName = jsHeader.getPackage();
//		} else {
//			packageName = preGenImport.getPackage();
//		}
//		if (packageName!=null) {
//			packageName = packageName.trim();
//		}
//
//		JsPkgInfo pkg = new JsPkgInfo();
//		pkg.setPkgName(packageName);
//		getDirectories().put(dir.getParentFile(), pkg);
//	}
//
//	public static void build(URL url, File outputDir) {
//		System.out.println(url.getPath());
//	}
//	
////	private JstType generateVjo3Jsr(File file, File outputDir)
////		throws IOException {
////		
////		JstType jst = getJst(getSrcDir(file), file);
////		String pkg = jst.getPackage().getName();
////		String fileName = getVjo3OutputFile(file, pkg, outputDir);
////		File outputFile = new File(fileName);
////		FileWriter jsrWriter = new FileWriter(outputFile);
////		
////		generateVjo3Jsr(jst,jsrWriter, outputFile.getParentFile());
////		
////		jsrWriter.flush();
////		jsrWriter.close();
////		return jst;
////	}
//	
//	private String getSrcDir(File file) {
//		if (m_sourceDirs == null) {
//			return null;
//		}
//		for (String srcDir : m_sourceDirs) {
//			String filePath = file.getAbsolutePath().replace('\\', '/');
//			String srcPath = srcDir.replace('\\', '/');
//			if (srcPath.startsWith("/")) {
//				srcPath = srcPath.substring(1);
//			}
//			if (filePath.startsWith(srcPath)) {
//				return srcDir;
//			}
//		}
//		return null;
//	}
//
//	public static IJstType generateVjo3Jsr(File inputFile, Writer jsrWriter, File outputParentDir)
//		throws IOException {
//	
//		if(!m_init){
//			new NativeJsLibProvider();
//		}
//		
//		String ONDEMAND = "ONDEMAND";
//		TranslateConfig cfg = new TranslateConfig();
//		cfg.setSkiptImplementation(true);
//		VjoParser p = new VjoParser(cfg);
//		IJstType t = p.parse(ONDEMAND, inputFile).getType();
//		BuildController c = new BuildController();
//		JstTypeSpaceMgr mgr = c.loadType(ONDEMAND,t);
////		GenerationConfig config = new GenerationConfig();
////		config.setGenJsr(true);
////		config.setGenNJP(true);
//		IJstType type = mgr.getQueryExecutor().findType(new TypeName(ONDEMAND,t.getName()));
////		if(type!=null){
////			c.generate(type, config);
////		}
//		return generateVjo3Jsr(type, jsrWriter, outputParentDir);
//	}
//	
//	private static IJstType generateVjo3Jsr(IJstType jstType, Writer jsrWriter, File outputParentDir)
//		throws IOException {
//		FileWriter writerForNativeJsProxy = null;
//		File nativeJsProxyFile = null;
//		if (!skipProxyGen(jstType)) {
//			nativeJsProxyFile = new File(outputParentDir, jstType.getSimpleName() + ".java");
//			if (!nativeJsProxyFile.exists()) {
//				nativeJsProxyFile.createNewFile();
//			}
//			if (nativeJsProxyFile.canWrite()) {
//				writerForNativeJsProxy = new FileWriter(nativeJsProxyFile);
//			}
//		}
//		JsCodeGenHelper.genJsr(jstType, jsrWriter, CodeStyle.PRETTY);
//
//		if (writerForNativeJsProxy != null) {
//			new NativeJsProxyGenerator(new PrintWriter(writerForNativeJsProxy), CodeStyle.PRETTY)
//				.writeProxy(jstType);
//			writerForNativeJsProxy.flush();
//			writerForNativeJsProxy.close();
//			System.out.println("Proxy class generated: " + nativeJsProxyFile.getAbsolutePath());
//		}
//		return jstType;
//	}
//	
//	private static boolean skipProxyGen(IJstType jstType) {
//		if (jstType.getName().startsWith("vjo.java.")) {
//			return true;
//		}
//		if (jstType.isInterface() || jstType.isEnum()) {
//			return false;
//		}
//		if (jstType.isClass() || jstType.isOType()) {
//			String typeName = jstType.getSimpleName();
//			if ("Object".equals(typeName) || "Class".equals(typeName)) {
//				return true;
//			}
//			return false;
//		}
//		return true;
//	}
//	
//	private static IJstType getJst(String srcDir, File file) throws  IOException {
//		String fileName = null;
//		if (srcDir != null) {
//			// if we have a source directory, figure out the 
//			// the package name and use the package name as file name.
//			SourceType source = DefaultJstTypeLoader.createType(
//					null, srcDir, file);
//			fileName = source.getFileName();
//		} else {
//			fileName = file.getAbsolutePath();
//		}
//		VjoParser parser = new VjoParser();
//		IJstType jstType = parser.parse(
//				null, fileName, file, true).getType();
//		return jstType;
//	}
//	
////	private void genVjo3(File file) {
////		try {
////			//Clear cache before generating JSR
//////			JstCache.getInstance().clear();
////			
////			JstType jst = generateVjo3Jsr(file, m_outputDir);
////			if (shouldReturnJstList()) {
////				getJstList().add(jst);
////			}
////			
////			//update for pkg spec
////			if (jst.getPackage()!=null) {
////				JsPkgInfo pkg = new JsPkgInfo();
////				pkg.setPkgName(jst.getPackage().getName());
////				getDirectories().put(file.getParentFile(), pkg);
////			}
////			//System.out.println(writer.toString());
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////			DsfJs2JavaExceptionHelper.chuck("File not found: " + file.getPath());
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
//
//	private boolean isExcluded(File dir) {
//		if(m_exclusion==null){
//			return false;
//		}
//		for(String exclude:m_exclusion){
//			if(dir.getPath().contains(exclude)){
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 
//	 * 
//	 *
//	 */
//	class JsPkgInfo {
//		String m_pkgName;
//
//		Set<String> m_genedFiles;
//
//		/**
//		 * 
//		 * @return code generated files
//		 */
//		public Set<String> getGenedFiles() {
//			return m_genedFiles;
//		}
//
//		/**
//		 *
//		 * @param genedFiles the generated files
//		 */
//		public void setGenedFiles(Set<String> genedFiles) {
//			if (m_genedFiles == null) {
//				m_genedFiles = new HashSet<String>();
//			}
//			m_genedFiles = genedFiles;
//		}
//
//		/**
//		 * 
//		 * @return package name
//		 */
//		public String getPkgName() {
//			return m_pkgName;
//		}
//
//		/**
//		 * 
//		 * @param pkgName package name
//		 */
//		public void setPkgName(String pkgName) {
//			m_pkgName = pkgName;
//		}
//
//		/**
//		 * 
//		 * @param genFileName add file to package
//		 */
//		public void addGenedFile(String genFileName) {
//			getGenedFiles().add(genFileName);
//		}
//
//	}
//
//	/**
//	 * 
//	 * @return generated package
//	 */
//	public JsPackageSpecGenerator getGen() {
//		return m_gen;
//	}
//
//	/**
//	 * 
//	 * @param gen generated package
//	 */
//	public void setGen(JsPackageSpecGenerator gen) {
//		m_gen = gen;
//	}
//
//	public Map<File, JsPkgInfo> getDirectories() {
//		if(m_directories==null){
//			m_directories = new HashMap<File, JsPkgInfo>(20);
//		}
//		return m_directories;
//	}
//
//	public void setDirectories(Map<File, JsPkgInfo> directories) {
//		m_directories = directories;
//	}
//	
//	private static String getVjo3OutputFile(File jsFile, String pkg, 
//			File outputDir){
//		String outputFileName = "";
//		
//		if (outputDir != null && pkg != null){
//			String pkgAsURI = pkg.replace('.', File.separatorChar);
//			String jsrName = jsFile.getName().substring(0, jsFile.getName().indexOf("."));
//			
//			File f = new File (outputDir, pkgAsURI);
//			if (!f.exists()){
//				f.mkdirs();
//			}
//			outputFileName = f.getAbsolutePath() + File.separatorChar + jsrName
//							+ JSR_FILE_EXTENSION;
//		} else {
//			outputFileName = jsFile.getPath().replace(JS_FILE_EXTENSION, 
//					JSR_FILE_EXTENSION);
//		}
//		
//		return outputFileName;
//	}
//
//	public String[] getSourceDirs() {
//		return m_sourceDirs;
//	}
//
//	public void setSourceDirs(String[] srcDirs) {
//		m_sourceDirs = srcDirs;
//	}
//
//	public String getGroupPath() {
//		return m_groupPath;
//	}
//
//	public void setGroupPath(String groupPath) {
//		m_groupPath = groupPath;
//	}
//}
