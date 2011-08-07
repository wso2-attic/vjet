/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.prebuild;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.prebuild.BaseBuildTask;
import org.ebayopensource.dsf.javatojs.translate.config.JsNativeConfigInitializer;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstStaticOnlyProxyType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;

public class JsNativeLibBuildTask extends BaseBuildTask {
		
	/**
	 * Project directory
	 */
	private String m_projectDir;
	
	/**
	 * Project source directories. 
	 */
	private String m_sourceDirs;
	
	/**
	 * Output director to write serialized files to
	 */
	private String m_outputDirectory;
	
	/**
	 * Output file to write the serialized object to
	 * If this value is not set, the package name will be used as the file name.
	 */
	private String m_outputFile;
	
	/**
	 * Package names for JS native files
	 * This is a comma seperated list of package names.
	 */
	private String m_jsNativePkgNames;
	
	private String m_excludePkgNames;
	
	private List<String> m_excludePkgName = new ArrayList<String>(3);
	
	private boolean m_enableParallel = false;
	
	private boolean m_enableTrace = false;
	
	private boolean m_enableDebug = false;
	
	private String m_sourceSearchPath;
	
	private TranslationController m_controller;
	
	// Following two fields are for test only when debug is ture
	private List<File> m_fileList = new ArrayList<File>();
	private List<JstType> m_originalJstTypeList = new ArrayList<JstType>();
	
	
	protected void getBuildFiles(final File dir, final List<URI> list, 
			final String pkgNamesStr, final List<String> excludePkg, final boolean debug) throws MalformedURLException{
    	if (dir == null) {
    		throw new RuntimeException("dir is null");
    	}
    	if (!dir.exists()) {
    		throw new RuntimeException("dir " + dir.getAbsolutePath() + " doesn't exist");
    	}
  
		File[] j2jFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (!name.endsWith(JAVA_SUFFIX) || 
					name.endsWith(JSR_SUFFIX) ||
					pkgNamesStr == null) {
					return false;
				}
				String[] pkgNames = pkgNamesStr.split(",");
				for (String pkgName : pkgNames) {
					String subdir = pkgName.replace(".", File.separator);
					if (excludePkg != null) {
						for(String exluded:excludePkg){
							String excludeSubDir = exluded.replace(".", File.separator);
							if (dir.getPath().contains(excludeSubDir)) {
								return false;
							}
						}
					}
					if((dir.getPath().contains(
						File.separator+ subdir + File.separator) ||
						dir.getPath().endsWith(
							File.separator + subdir))) {
//						if (debug) {
							System.out.println("Accept: " + dir.toString()+File.separator+name); //KEEPME
//						}
						return true;
					}
				}
				return false;
			}			
		});
		if (j2jFiles != null) {
			for (File f : j2jFiles) {
				list.add(f.toURI());
			}
		}
		
		File[] subDirs = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}			
		});
		for (File subDir : subDirs) {
			getBuildFiles(subDir, list, pkgNamesStr, excludePkg, debug);
		}
	}
	
	@Override
	public void execute() throws BuildException {
		
		System.out.println("executing JsNativeBuildTask....");
		System.out.println("sourceDirs=" + m_sourceDirs);
		System.out.println("projectDir=" + m_projectDir);
		
		if(m_excludePkgNames!=null){
			String[] exlusions = m_excludePkgNames.split(",");
			for(String s:exlusions){
				m_excludePkgName.add(s);
			}
		}
		
		
		if (m_sourceSearchPath != null && !"".equals(m_sourceSearchPath)) {
//			System.out.println("sourceSearchPath=" + m_sourceSearchPath);
			System.setProperty("java.source.path", m_sourceSearchPath);
		} else {
//			System.out.println("sourceSearchPath=" + System.getProperty("java.source.path"));
//			System.out.println("sourceSearchPath=null!!!");
			m_sourceSearchPath = System.getProperty("java.source.path");
		}
		
		if (m_jsNativePkgNames == null) {
			throw new BuildException("jsNativePkgNames is null");
		}
		
		if (m_outputDirectory == null) {
			throw new BuildException("m_outputDirectory is null");
		}
		
		long startTime = System.currentTimeMillis();
		try {
			File[] dirs = parseSourceDirs(getSourceDirs(), getProjectDir());
			if (dirs.length == 0) {
				return;
			}
			
			createOutputDirectory();
			
			for (File srcDir : dirs) {
				if (srcDir == null) {
					continue;
				}
				String[] pkgNames = m_jsNativePkgNames.split(",");
				for (String pkgName : pkgNames) {
					List<URI> files = new ArrayList<URI>();
					getBuildFiles(srcDir, files, pkgName, 
							getExcludedPkgNames(), m_enableDebug);
					getExcludedPkgNames().add(pkgName);
					//System.out.println("FileList: " + fileList);
					
					List<JstType> jstTypes = getJstTypes(getJsNativeFiles(files));
					List<JstType> aliasTypes = new ArrayList<JstType>();
					// Fix JstType category
					if(jstTypes.size()==0){
						throw new DsfRuntimeException("no types found under files:" + files);
					}
					
					for (JstType t : jstTypes) {
						
						JstType alias =JstFactory.getInstance().createJstType(new JstPackage("js"), t.getName(), true);
						alias.addExtend(t);
						aliasTypes.add(alias);
						alias.setMetaType(true);
						
						
						
						
						fixCategory(t);
						mixinFunction(t);
						JstCache.getInstance().addType(t);
					}
					aliasTypes.addAll(jstTypes);
					serialize(pkgName, aliasTypes);
					if (m_enableDebug) {
						addFiles(files);
						addJstTypes(aliasTypes);
					}
				}
			}
		} catch (Throwable e) {
			System.out.println("Exception - " + e.getMessage());
			e.printStackTrace();  // KEEPME
			throw new BuildException("JsNativeBuildTask: exception - " + e);
		} finally {
			//TranslateCtx.ctx().getConfig().setJsNativeTranslationEnabled(false);
			//TODO: check with choi abt correctness of the below statement
			//TranslateCtx.ctx().getConfig().setJsNativeInitializer(null);
			long totalTime = (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("JsNativeBuildTask completed in " + totalTime +
					" sec");
		}
	}

	private static String[] s_jsNativeGlobals = new String[] {
		org.ebayopensource.dsf.jsnative.global.Array.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Boolean.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Date.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Error.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Number.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.RegExp.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.String.class.getSimpleName()
	};
	
	private void mixinFunction(JstType t) {
		if (contains(s_jsNativeGlobals, t.getName())) {
			
			
			
			IJstType function = JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Function.class.getName());
			if (function != null) { //only mixin static properties and methods
				t.addMixin(new JstTypeReference(
					new JstStaticOnlyProxyType(function)));
			}
		}
	}

	private boolean contains(String[] nativeGlobals, String typeName) {
		for (String name : nativeGlobals) {
			if (name.equals(typeName)) {
				return true;
			}
		}
		return false;
	}

	private void fixCategory(JstType jstType) {
		if (jstType.isClass()) {
			
			// TODO before converting to abstract make sure all methods are available in Jsttype
			
			jstType.getModifiers().setAbstract(false);
			
			for (IJstType extend : jstType.getExtends()) {
				if (extend.isInterface()) {
					// Moved the extends types to satisfies
					jstType.removeExtend(extend);
					jstType.addSatisfy(extend);
				} else if (extend.isMixin()) {
					// Moved the extends types to mixin
					jstType.removeExtend(extend);
				
					jstType.addMixin(new JstTypeReference(extend));
				}
			}
		} else if (jstType.isMixin()) {
			// Moved the extends types to satisfies
			for (IJstType extend : jstType.getExtends()) {
				jstType.removeExtend(extend);
				jstType.addSatisfy(extend);
			}
		}
	}

	private void addJstTypes(List<JstType> jstTypes) {
		for (JstType t : jstTypes) {
				m_originalJstTypeList.add(t);
		}
		
	}

	private void addFiles(List<URI> files) throws URISyntaxException {
		for (URI f : files) {
				m_fileList.add(new File(f));
		}
	}

	public String getSourceDirs() {
		return m_sourceDirs;
	}

	public void setSourceDirs(String baseDir) {
		m_sourceDirs = baseDir;
	}

	public String getProjectDir() {
		return m_projectDir;
	}

	public void setProjectDir(String projectDir) {
		m_projectDir = projectDir;
	}

	public boolean getEnableParallel() {
		return m_enableParallel;
	}

	public void setEnableParallel(boolean parallel) {
		m_enableParallel = parallel;
	}

	public boolean getEnableTrace() {
		return m_enableTrace;
	}

	public void setEnableTrace(boolean trace) {
		m_enableTrace = trace;
	}
	
	public String getJsNativePkgNames() {
		return m_jsNativePkgNames;
	}

	public void setJsNativePkgNames(String nativePkgNames) {
		m_jsNativePkgNames = nativePkgNames;
	}
	
	public String getOutputDirectory() {
		return m_outputDirectory;
	}

	public void setOutputDirectory(String directory) {
		m_outputDirectory = directory;
	}
	
	public String getOutputFile() {
		return m_outputFile;
	}

	public void setOutputFile(String file) {
		m_outputFile = file;
	}
	
	public boolean getEnnableDebug() {
		return m_enableDebug;
	}

	public void setEnableDebug(boolean debug) {
		m_enableDebug = debug;
	}
	
	public List<File> getFileList() {
		return m_fileList;
	}
	
	public List<JstType> getJstTypeList() {
		return m_originalJstTypeList;
	}
	
	public List<String> getExcludedPkgNames() {
		return m_excludePkgName;
	}

	public void addExcludePkgName(String pkgName) {
		m_excludePkgName.add(pkgName);
	}
	
	public String getExcludePkgName() {
		return m_excludePkgNames;
	}

	public void setExcludePkgName(String pkgName) {
		m_excludePkgNames = pkgName;
	}

	
	public String getSourceSearchPath() {
		return m_sourceSearchPath;
	}

	public void setSourceSearchPath(String searchPath) {
		m_sourceSearchPath = searchPath;
	}
	
	
	
	//map of jsnative file name and its contents	
	private Map<URI,String> getJsNativeFiles(List<URI> files) {
		Map<URI,String> srcFiles = new LinkedHashMap<URI,String>();
		String src;
//		TranslateCtx tCtx = TranslateCtx.ctx();
		for (int i = files.size()-1; i >= 0; i-- ){
			URI f = files.get(i);
			try{
			src = JavaToJsHelper.readFromInputReader(new InputStreamReader(f.toURL().openStream()));
			/*if (!tCtx.getConfig().getFilter().accept(src)){
				tCtx.addExcludedType(TranslateHelper.getTypeName(f.getPath()));
				continue;
			}*/
			srcFiles.put(f, src);
			}catch(MalformedURLException e){
				e.printStackTrace();
				System.out.println("Could not read file : "+f.toString());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Could not read file : "+f.toString());
			}
		}
		return srcFiles;
	}
	
	private void createOutputDirectory() {
		File outDir = new File(m_outputDirectory);
		if (outDir.exists() && outDir.isDirectory()) {
			return;
		}
		if (!outDir.mkdir()) {
			throw new RuntimeException("Could not create directory: " + 
					m_outputDirectory);
		} 
	}

	private void serialize(String pkgName, List<JstType> jstTypes) {
		String fileName = getSerializedFileName(pkgName);
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(jstTypes);
			if (m_enableDebug) {
				dump(jstTypes);
			}
			System.out.println("Wrote " + fileName);	//KEEPME
		} catch(IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}		
	}

	private String getSerializedFileName(String pkgName) {
		if (m_outputFile != null) {
			return m_outputDirectory + File.separator + m_outputFile;
		}
		return m_outputDirectory + File.separator + pkgName.replace('.', '_') + ".ser";
	}

	
	private List<JstType> getJstTypes(final Map<URI,String> files){
		return getController().targetedTranslation(files);
	}

	private TranslationController getController() {
		if (m_controller == null) {
			m_controller = new TranslationController(new JsNativeConfigInitializer());
			m_controller.enableParallel(m_enableParallel)
				.enableTrace(m_enableTrace);
		}
		return m_controller;
	}
	
	public static void main(String argv[]) {
		
		JsNativeLibBuildTask task = new JsNativeLibBuildTask();
		
		String base  = new File("../../../core/resource/").getAbsolutePath();
		String inputbase  = new File("../../../core/").getAbsolutePath();
		
		
		task.m_enableDebug = true;
		/**
		 * sourceDirs="${jsnative.src.dir}"
			projectDir="${jsnative.proj.dir}"
			outputDirectory="${outDir}"
			jsNativePkgNames="org.ebayopensource.dsf.jsnative.global,org.ebayopensource.dsf.jsnative"
			excludePkgName="org.ebayopensource.dsf.jsnative.anno"
			enableParallel="false"
			enableTrace="false"
			enableDebug="false"
			sourceSearchPath="${toString:source.path}"
		 */
		
		
		task.m_sourceSearchPath = inputbase+"/org.ebayopensource.vjet.core.jsnative/src";
		task.m_sourceDirs= "src";
		task.m_projectDir =inputbase+"/org.ebayopensource.vjet.core.jsnative";
		task.m_jsNativePkgNames = "org.ebayopensource.dsf.jsnative.global,org.ebayopensource.dsf.jsnative";
		task.m_excludePkgNames = "org.ebayopensource.dsf.jsnative.anno";
		task.m_outputDirectory = base+"/org.ebayopensource.vjet.resource.jsnativetypes/src/org/ebayopensource/jsnative/generated";
		task.m_enableDebug = true;
		
		task.execute();
	}
}
