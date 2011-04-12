/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CodeGenCleanerTask extends Task {
	
	private String m_option;
	private String m_sourceDirs;
	private String m_projectDir;
	private boolean m_verbose = false;

	@Override
	public void execute() throws BuildException {
		
		if (m_option == null) {
			throw new BuildException("option is null");
		}
		
		if (m_sourceDirs == null) {
			throw new BuildException("sourceDirs is null");
		}
		
		if (m_projectDir == null) {
			throw new BuildException("projectDir is null");
		}
		
		long startTime = System.currentTimeMillis();
		try {
			int deleted = CodeGenCleaner.clean(m_option, 
				parseSourceDirs(m_sourceDirs, m_projectDir), m_verbose);
			System.out.println("Deleted " + deleted + " files");
		} catch (Exception e) {
			e.printStackTrace();  // KEEPME
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime) / 1000;
			System.out.println("CodeGenCleaner completed in " + totalTime + " sec");
		}
	}
	
	protected static List<String> parseSourceDirs(String srcDirs, String projRoot) {
		String[] sources = srcDirs.split(File.pathSeparator);
		List<String> dirs = new ArrayList<String>(sources.length);
		for (String source : sources) {
			String sourceFile = source;
			if (source.indexOf(projRoot) == -1) {
				sourceFile = projRoot + File.separatorChar + source;
			}
			dirs.add(sourceFile);
		}
		return dirs;
	}

	public String getOption() {
		return m_option;
	}

	public void setOption(String option) {
		m_option = option;
	}

	public boolean getVerbose() {
		return m_verbose;
	}

	public void setVerbose(boolean verbose) {
		m_verbose = verbose;
	}

	public String getSourceDirs() {
		return m_sourceDirs;
	}

	public void setSourceDirs(String dirs) {
		m_sourceDirs = dirs;
	}

	public String getProjectDir() {
		return m_projectDir;
	}

	public void setProjectDir(String dir) {
		m_projectDir = dir;
	}
	
}
