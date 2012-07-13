/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.prebuild;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.BuildException;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.translate.config.CodeGenConfig;
import org.ebayopensource.dsf.javatojs.translate.config.CodeGenConfig.TranslationMode;
import org.ebayopensource.dsf.logger.LogLevel;

/**
 * Ant task for Java to JavaScript pre-build
 *
 */
public class J2JsBuildTask extends BaseBuildTask {

	/**
	 * Project directory
	 */
	private String m_projectDir;
	
	/**
	 * Project source directories. 
	 */
	private String m_sourceDirs;

	/**
	 * Exclusion directories
	 */
	private String m_exclusion;
	
	/**
	 * Package names that contains Java source files for translation
	 * This is a comma seperated list of package names.
	 */
	private String m_j2jPkgName;
	
	private boolean m_genJsr = false;
		
	private boolean m_enableParallel = false;
	
	private boolean m_enableTrace = false;
	
	private boolean m_enableDebug = false;
	
	// use on-demand translation
	private boolean m_useOnDemand = false;
	
	private String m_sourceSearchPath;
	
	private BuildController m_controller;

	@Override
	public void execute() throws BuildException {

		if (m_sourceDirs == null) {
			throw new BuildException("Java2JsPreBuild - source dirctory not set");
		}
		if (m_projectDir == null) {
			throw new BuildException("Java2JsPreBuild - project not set");
		}
		
		System.out.println("executing Java2JsPreBuild....");
		System.out.println("projectDir=" + m_projectDir);
		System.out.println("sourceDirs=" + m_sourceDirs);
		System.out.println("exclusion=" + m_exclusion);
		
		if (m_sourceSearchPath != null && !"".equals(m_sourceSearchPath)) {
			System.out.println("sourceSearchPath=" + m_sourceSearchPath);
			System.setProperty("java.source.path", m_sourceSearchPath);
		} else {
			System.out.println("sourceSearchPath=null!!!");
		}
//		String[] exclusion = null;
//		if(m_exclusion!=null){
//			exclusion = m_exclusion.split(",");
//		}

		try {
			long startTime = System.currentTimeMillis();
		
			initCodeGenConfig();
			File[] dirs = parseSourceDirs(getSourceDirs(), getProjectDir());
			
			for (File srcDir : dirs) {
				if (srcDir == null) {
					continue;
				}
				List<URL> files = new ArrayList<URL>();
				getBuildFiles(srcDir, files, m_j2jPkgName, null, m_enableDebug);
				if(files.size()>0){
					BuildController controller = getBuildController();
					controller.buildFiles(files);
					if (m_enableDebug) {
						dump(controller.getTranslatedJstTypes());
					}
					List<TranslateError> errors = controller.getAllErrors();
					printErrors(errors);
				}
			}
			long totalTime = (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("Java2JsPreBuild completed in " + totalTime +
					" sec");
		} catch (Throwable e) {
			System.out.println("Java2JsPreBuild: exception - " + e.getMessage());
			e.printStackTrace();  // KEEPME
//			throw new BuildException("Java2JsPreBuild: exception - " + e);
		} 
	}

	// If a java2js.properties file is available, 
	// use it to override the properties supplied
	// via ANT task
	private void initCodeGenConfig() {
		if (!shouldOverrideConfig()) {
			return;
		}
		CodeGenConfig config = new CodeGenConfig(getCodeGenProps());
		m_j2jPkgName = config.getGenPkgNames();
		m_genJsr = config.shouldGenJsr();
		m_enableParallel = config.isParallelEnabled();
		m_enableTrace = config.isTraceEnabled();
		m_enableDebug = config.isVerbose();
		m_useOnDemand = (config.getMode() == TranslationMode.ONDEMAND);
	}

	private boolean shouldOverrideConfig() {
		String globalGenPropPath = 
			System.getProperty(CodeGenConfig.GLOBAL_CODE_GEN_PROP);
		if (globalGenPropPath != null && globalGenPropPath.length() != 0) {
			File file = new File(globalGenPropPath);
			if (file.exists()) {
				return true;
			}
		}
		
		String codeGenPropPath = getProjectDir() +
			File.separatorChar + CodeGenConfig.PROP_FILE_NAME;
		File file = new File(codeGenPropPath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	private Properties getCodeGenProps() {
		Properties prop = null;
		// Look for global config properties
		String globalGenPropPath = 
			System.getProperty(CodeGenConfig.GLOBAL_CODE_GEN_PROP);
		Properties globalProp = loadPropFile(globalGenPropPath, new Properties());
		if (globalProp != null && !globalProp.isEmpty()) {
			// we have global properties, use these as default values
			// of properties
			prop = new Properties(globalProp);
		}
		// Look for project specific config properties
		// If one exists, load the properties to override default
		String codeGenPropPath = getProjectDir() +
			File.separatorChar + CodeGenConfig.PROP_FILE_NAME;
		prop = loadPropFile(codeGenPropPath, 
				prop == null ? new Properties() : prop);
		return prop;
	}

	private Properties loadPropFile(String propFilePath, Properties prop) {
		FileInputStream fis=null;
		if (propFilePath == null || propFilePath.length() == 0) {
			return prop;
		}
		File file = new File(propFilePath);
		
		if (!file.exists()) {
			return prop;
		}
		
		try
		{
			fis = new FileInputStream(file);
//			Fixed findbugs issue 6553
//			prop.load(new FileInputStream(file));
			prop.load(fis);
		}
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		} 
		catch (Exception e)
		{
			System.err.println("Error loading properties from " + file); //KEEPME
		}
		finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		return prop;
	}

	private BuildController getBuildController() {
		if (m_controller == null) {
			m_controller = new BuildController();
			m_controller.enableParallel(m_enableParallel)
				.enableTrace(m_enableTrace);
			m_controller.setGenJsr(getGenJsr());
			m_controller.setUseOnDemand(m_useOnDemand);
			m_controller.setVerbose(m_enableDebug);
			m_controller.getTranslateController().setInitializer(new DefaultTranslationInitializer());
			if (m_enableDebug) {
				System.out.println("m_useOnDemand="+m_useOnDemand);
				System.out.println("m_genJsr="+m_genJsr);
				System.out.println("m_enableParallel="+m_enableParallel);
				System.out.println("m_enableTrace="+m_enableTrace);
			}
		}
		return m_controller;
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

	public void setExclusion(String string) {
		m_exclusion = string;
	}

	public String getExclusion() {
		return m_exclusion;
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
	
	public boolean getEnableDebug() {
		return m_enableDebug;
	}
	
	public void setEnableDebug(boolean debug) {
		m_enableDebug = debug;
	}

	public boolean getGenJsr() {
		return m_genJsr;
	}

	public void setGenJsr(boolean value) {
		m_genJsr = value;
	}

	public String getJ2jPkgName() {
		return m_j2jPkgName;
	}

	public void setJ2jPkgName(String pkgName) {
		m_j2jPkgName = pkgName;
	}
	
	private void printErrors(List<TranslateError> errors){
		if (!m_enableDebug) {
			return;
		}
		for (TranslateError e: errors){
			if (e.getLevel() == LogLevel.ERROR){
				System.err.println(e.toString());
			}
		}
	}

	public String getSourceSearchPath() {
		return m_sourceSearchPath;
	}

	public void setSourceSearchPath(String searchPath) {
		m_sourceSearchPath = searchPath;
	}

}