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
//import java.io.FileOutputStream;
//import java.util.List;
//
//import org.apache.tools.ant.BuildException;
//import org.apache.tools.ant.Task;
//
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
//
//public class JsLibPreBuildTask extends Task {
//
//	private String m_sourceDirs;
//
//	private int m_version;
//
//	private int m_debugLevel = 0;
//
//	private String m_projectDir;
//
//	private String m_exclusion;
//	
//	private String m_sourceSearchPath;
//	
//	private boolean m_buildJstLib;
//	
//	private String m_buildJstOutDir;
//	
//	private String m_buildJstFileName;
//
//	@Override
//	public void execute() throws BuildException {
//
//		if (m_sourceDirs == null) {
//			throw new BuildException("JsPreBuild - m_inBaseDir is null");
//		}
//		if (m_projectDir == null) {
//			throw new BuildException("JsPreBuild - project not set");
//		}
//
//		long startTime = System.currentTimeMillis();
//		try {
//			
//			System.out.println("executing JsLibPreBuild....");
//			System.out.println("sourceDirs=" + m_sourceDirs);
//			System.out.println("projectDir=" + m_projectDir);
//			System.out.println("exclusion=" + m_exclusion);
//			if (shouldBuildJstLib()) {
//				System.out.println("buildJstLib=" + m_buildJstLib);
//				System.out.println("buildJstOutDir=" + m_buildJstOutDir);
//				System.out.println("buildJstFileName=" + m_buildJstFileName);
//			}
//			
//			if (m_sourceSearchPath != null && !"".equals(m_sourceSearchPath)) {
//				System.out.println("sourceSearchPath=" + m_sourceSearchPath);
//				System.setProperty("java.source.path", m_sourceSearchPath);
//			} else {
//				System.out.println("sourceSearchPath=null!!!");
//			}
//			
//			String[] exclusion = null;
//			JsLibPreBuild build = null;
//			if(m_exclusion!=null){
//				exclusion = m_exclusion.split(",");
//			}
//			build = new JsLibPreBuild(parseDirs(getSourceDirs(),
//					getProjectDir()), exclusion, m_debugLevel);
//			build.setReturnJstList(shouldBuildJstLib());
//			build.setGroupPath(getProjectDir());
//			build.setSourceDirs(getSrcDirs(getSourceDirs()));
//			build.execute();
//			
//			if (shouldBuildJstLib()) {
//				serialize(build.getJstList());
//			}
//		} catch (Exception e) {
//			e.printStackTrace(); //KEEPME
//			throw new BuildException("CodeGenErorrIds: exception - " + e, e);
//		} finally {
//			long totalTime = (System.currentTimeMillis() - startTime) / 1000;
//			System.out.println("JsLibPreBuildTask completed in " + totalTime +
//					" sec");
//		}
//	}
//
//	private String[] getSrcDirs(String srcDirs) {
//		return srcDirs.split(File.pathSeparator);
//	}
//
//	private void serialize(List<IJstType> jstList) {
//		String filePath = m_projectDir + File.separator + 
//			m_buildJstOutDir + File.separator + m_buildJstFileName;
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(filePath);
//			JstTypeSerializer.serialize(jstList, fos);
//			System.out.println("JsLibPreBuildTask: wrote " + filePath);
//		} catch (Exception e) {
//			e.printStackTrace();	//KEEPME
//			System.out.println("Error serializing JstType - " + e.getMessage());
//		}
//	}
//
//	public String getSourceDirs() {
//		return m_sourceDirs;
//	}
//
//	public void setSourceDirs(String baseDir) {
//		m_sourceDirs = baseDir;
//	}
//
//	public int getVersion() {
//		return m_version;
//	}
//
//	public void setVersion(String version) {
//		m_version = Integer.parseInt(version);
//	}
//
//	public int getDebugLevel() {
//		return m_debugLevel;
//	}
//
//	public void setDebugLevel(int debugLevel) {
//		m_debugLevel = debugLevel;
//	}
//
//	private static File[] parseDirs(String srcDirs, String projRoot) {
//		System.out.println("srcDirs = " + srcDirs);
//		String[] sources = srcDirs.split(File.pathSeparator);
//		File[] dirs = new File[sources.length];
//		int count = 0;
//		for (String source : sources) {
//			String sourceFile = source;
//			if (source.indexOf(projRoot) == -1) {
//				sourceFile = projRoot + File.separatorChar + source;
//			}
//			// if(getDebugLevel()>0)
//			// System.out.println("sourceFile = " + sourceFile);
//			dirs[count] = new File(sourceFile);
//			count++;
//		}
//		return dirs;
//	}
//
//	public String getProjectDir() {
//		return m_projectDir;
//	}
//
//	public void setProjectDir(String projectDir) {
//		m_projectDir = projectDir;
//	}
//
//	public static void main(String[] args) {
//
//		JsLibPreBuildTask build0 = new JsLibPreBuildTask();
//		//build0.setSourceDirs("src");
//		build0.setSourceDirs("util");
//		build0.setDebugLevel(0);
//		build0.setExclusion(".unloaded");
//		build0.setProjectDir(args[0]);
//		build0.m_buildJstLib = true;
//		build0.m_buildJstOutDir = "bin\\vjo\\generated";
//		build0.m_buildJstFileName = "testlib.ser";
//		build0.execute();
//		
//		
////		JsLibPreBuildTask build1 = new JsLibPreBuildTask();
////		build1.setSourceDirs("src");
////		build1.setDebugLevel(1);
////		build1.setExclusion(".unloaded");
////		build1.setProjectDir("D:\\cc\\jearly_m_v4_chief\\v4ebaypres\\DarwinCoreComponents");
////		build1.execute();
//
//		// JsLibPreBuildTask build = new JsLibPreBuildTask();
//		// build
//		// .setSourceDirs("src\\vjo\\dsf\\;src\\vjo\\example\\");
//		// build.setDebugLevel(1);
//		// build.setProjectDir("D:\\cc\\jearly_m_v4_3_ro\\v4darwin\\VjLib\\");
//		// build.execute();
//
//	}
//
//	public void setExclusion(String string) {
//		m_exclusion = string;
//	}
//
//	public String getExclusion() {
//		return m_exclusion;
//	}
//
//	public String getSourceSearchPath() {
//		return m_sourceSearchPath;
//	}
//
//	public void setSourceSearchPath(String sourceSearchPath) {
//		m_sourceSearchPath = sourceSearchPath;
//	}
//
//	public boolean shouldBuildJstLib() {
//		return m_buildJstLib;
//	}
//
//	public void setBuildJstLib(boolean value) {
//		m_buildJstLib = value;
//	}
//
//	public String getBuildJstOutDir() {
//		return m_buildJstOutDir;
//	}
//
//	public void setBuildJstOutDir(String jstOutDir) {
//		m_buildJstOutDir = jstOutDir;
//	}
//
//	public String getBuildJstFileName() {
//		return m_buildJstFileName;
//	}
//
//	public void setBuildJstFileName(String jstFileName) {
//		m_buildJstFileName = jstFileName;
//	}
//
//}