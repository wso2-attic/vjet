/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

import java.io.FileFilter;
import java.util.Properties;

import org.ebayopensource.dsf.javatojs.control.BuildFileFilter;
import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.IBuildResourceFilter;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;

public class CodeGenConfig {
	
	public static final String GLOBAL_CODE_GEN_PROP = "java2js.codegen.prop.file";
	public static final String PROP_FILE_NAME = "java2js.properties";
	
	private static final String TRANSLATION_MODE = "translationMode";
	private static final String TRACE = "traceEnabled";
	private static final String GEN_JSR = "genJsr";
	private static final String INCLUDE_CHILD_PKGS = "includeChildPkgs";
	private static final String VERBOSE = "verbose";
	private static final String PARALLEL_ENABLED = "parallelEnabled";
	private static final String CONFIG_INITIALIZER = "configInitializer";
	private static final String FILE_FILTER = "fileFilter";
	private static final String GEN_PKG_NAMES = "genPkgNames";
	
	private TranslationMode m_mode = TranslationMode.TARGETED;
	private boolean m_traceEnabled = false;
	private boolean m_genJsr = true;
	private boolean m_includeChildPkgs = true;
	private boolean m_verbose = false;
	private boolean m_parallelEnabled = false;
	private String m_genPkgNames = "j2j";
	private Class m_configInitializer = DefaultTranslationInitializer.class; 
	private Class m_fileFilter = BuildFileFilter.class; 
	
	public CodeGenConfig() {
	}

	public CodeGenConfig(Properties prop) {
		if (prop.getProperty(TRANSLATION_MODE) != null) {
			m_mode = TranslationMode.valueOf(
					prop.getProperty(TRANSLATION_MODE));
		}
		if (prop.getProperty(TRACE) != null) {
			m_traceEnabled = Boolean.parseBoolean(
					prop.getProperty(TRACE));
		}
		if (prop.getProperty(GEN_JSR) != null) {
			m_genJsr = Boolean.parseBoolean(
					prop.getProperty(GEN_JSR));
		}
		if (prop.getProperty(INCLUDE_CHILD_PKGS) != null) {
			m_includeChildPkgs = 
				Boolean.parseBoolean(prop.getProperty(INCLUDE_CHILD_PKGS));
		}
		if (prop.getProperty(VERBOSE) != null) {
			m_verbose = Boolean.parseBoolean(
					prop.getProperty(VERBOSE));
		}
		if (prop.getProperty(PARALLEL_ENABLED) != null) {
			m_parallelEnabled = Boolean.parseBoolean(
					prop.getProperty(PARALLEL_ENABLED));
		}
		if (prop.getProperty(GEN_PKG_NAMES) != null) {
			m_genPkgNames = 
				prop.getProperty(GEN_PKG_NAMES);
		}
		
		String configInitializer = prop.getProperty(CONFIG_INITIALIZER);
		if (configInitializer != null) {
			try {
				m_configInitializer = Class.forName(configInitializer);
				if (!ITranslationInitializer.class.isAssignableFrom(
						m_configInitializer)) {
					throw new RuntimeException(configInitializer + 
							" class is not assignable from " +
							ITranslationInitializer.class.getName());
					
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(
					"Could not found specified configInitializer class " + 
					configInitializer, e);
			}
		}
		
		String fileFilter = prop.getProperty(FILE_FILTER);
		if (fileFilter != null) {
			try {
				m_fileFilter = Class.forName(fileFilter);
				if (!IBuildResourceFilter.class.isAssignableFrom(m_fileFilter)) {
					throw new RuntimeException(fileFilter + 
							" class is not assignable from " +
							FileFilter.class.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(
					"Could not found specified fileFilter class " + 
					fileFilter, e);
			}
		}
	}


	public TranslationMode getMode() {
		return m_mode;
	}


	public void setMode(TranslationMode mode) {
		this.m_mode = mode;
	}


	public boolean isTraceEnabled() {
		return m_traceEnabled;
	}


	public void setTraceEnabled(boolean enabled) {
		m_traceEnabled = enabled;
	}


	public boolean shouldGenJsr() {
		return m_genJsr;
	}


	public void setGenJsr(boolean value) {
		m_genJsr = value;
	}
	
	public boolean shouldIncludeChildPkgs() {
		return m_includeChildPkgs;
	}


	public void setIncludeChildPkgs(boolean value) {
		m_includeChildPkgs = value;
	}
	
	public boolean isVerbose() {
		return m_verbose;
	}


	public void setVerbose(boolean verbose) {
		this.m_verbose = verbose;
	}

	public Class getConfigInitializer() {
		return m_configInitializer;
	}


	public void setConfigInitializer(Class initializer) {
		m_configInitializer = initializer;
	}


	public Class getFileFilter() {
		return m_fileFilter;
	}


	public void setFileFilter(Class filter) {
		m_fileFilter = filter;
	}
	
	public boolean isParallelEnabled() {
		return m_parallelEnabled;
	}

	public void setParallelEnabled(boolean enabled) {
		m_parallelEnabled = enabled;
	}
	
	public enum TranslationMode {
		ONDEMAND,
		TARGETED
	}

	public String getGenPkgNames() {
		return m_genPkgNames;
	}

	public void setGenPkgNames(String pkgNames) {
		m_genPkgNames = pkgNames;
	}
}
