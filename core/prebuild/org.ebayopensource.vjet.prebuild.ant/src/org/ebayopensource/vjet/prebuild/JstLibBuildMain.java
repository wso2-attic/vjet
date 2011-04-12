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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.ebayopensource.dsf.javatojs.control.TranslationController;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.policy.TranslationPolicy;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;

public class JstLibBuildMain {

	public static final String JAVA_SUFFIX = ".java";
	public static final String JSR_SUFFIX = "Jsr.java";
	public static final String JS_SUFFIX = ".js";
	public static final String VJO_SUFFIX = ".vjo";
	public static final String LIB_SUFFIX = ".ser";
	public static final String JAR_SUFFIX = ".jar";

	private static String JS_NATIVE_ANCHOR = "org.ebayopensource.jsnative.generated.JsNativeAnchor";
	private static String VJO_JAVA_LIB_ANCHOR = "vjo.generated.JstLibAnchor";

	/**
	 * Project source directories.
	 */
	private String m_sourceDirs;

	/**
	 * Keep track of current source folder
	 */
	private File m_currentSrcDir;

	/**
	 * Output director to write serialized files to
	 */
	private String m_outputDirectory;

	/**
	 * Output file to write the serialized object to If this value is not set,
	 * the package name will be used as the file name.
	 */
	private String m_outputFile;

	/**
	 * Implementing either FileFilter or FilenameFilter interfaces to filter
	 * source files to be included in the library
	 */
	private Class m_filterClass;
	
	/**
	 * Jar the generated ser file
	 */
	private boolean m_jarIt = true;

	/**
	 * Java2Js translation controller to translate Java to JST
	 */
	private TranslationController m_controller;

	/**
	 * JstParseController used to parse and resolve JS into JST
	 */
	IJstParseController m_JstParseController;

	/**
	 * VJO JS Parser to parse JS to JST
	 */
	// commenting var as it is never used or read from.
	// private VjoParser m_parser;
	private static class DefaultFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.endsWith(JS_SUFFIX) || name.endsWith(VJO_SUFFIX)) {
				return true;
			} else if (name.endsWith(JAVA_SUFFIX) && !name.endsWith(JSR_SUFFIX)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out
					.println("Usage: Java -cp <Classpath> org.ebayopensource.dsf.jstlib.build.JstBuildLibCmd <Source Folders> <Output Folder> <Library File Name> [Optional Filter Class Name]");
			System.out
					.println("<Source Folders> are list of source directories seperated by ',' or ';'.");
			System.out
					.println("<Output Folder> is the directory where the generated library is put under.");
			System.out
					.println("<Library File Name> is the generated library file name.");
			System.out
					.println("[Optional Filter Class] implements FileFilter or FilenameFilter interface.");
			return;
		}

		String srcDir = args[0];
		String outDir = args[1];
		String libName = args[2];
		String filterClassName = null;

		System.out.println("Source folders are " + srcDir);
		System.out.println("Output folder is " + outDir);
		System.out.println("Library name is " + libName);

		if (args.length > 3) {
			filterClassName = args[3];
			System.out.println("Filter class name is " + filterClassName);
		} else {
			System.out.println("No filter class provided.");
		}
		/*
		 * try { Class transControllerCls =
		 * Class.forName("org.ebayopensource.dsf.javatojs.control.TranslationController");
		 * 
		 * Class vjoParserCls =
		 * Class.forName("org.ebayopensource.dsf.jstojava.parser.VjoParser");
		 * 
		 * if (transControllerCls == null || vjoParserCls == null) {
		 * System.out.println("Please put DsfBase.jar, DsfPrebuild.jar on
		 * classpath."); } } catch (ClassNotFoundException e1) {
		 * e1.printStackTrace(); System.out.println("Please put DsfBase.jar,
		 * DsfPrebuild.jar on classpath."); }
		 */

		try {
			Class anchorNative = Class.forName(JS_NATIVE_ANCHOR);

			if (anchorNative == null) {
				System.out
						.println("Please put JsNativeResource.jar on classpath.");
			}
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
			System.out.println("Please put JsNativeResource.jar on classpath.");
		}

		try {
			Class anchorJavaLib = Class.forName(VJO_JAVA_LIB_ANCHOR);

			if (anchorJavaLib == null) {
				System.out.println("Please put VjoJavaLib.jar on classpath.");
			}
		} catch (ClassNotFoundException e3) {
			e3.printStackTrace();
			System.out.println("Please put VjoJavaLib.jar on classpath.");
		}

		Class fileFilterCls = null;

		if (filterClassName != null && filterClassName.length() > 0) {

			try {
				fileFilterCls = Class.forName(filterClassName);

				if (fileFilterCls == null) {
					System.out.println("Please put filter class "
							+ filterClassName + " on classpath.");
				}
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				System.out.println("Please put filter class " + filterClassName
						+ " on classpath.");
			}
		}

		JstLibBuildMain libBuildCmd = new JstLibBuildMain(srcDir, outDir,
				libName, fileFilterCls);

		long startTime = System.currentTimeMillis();
		libBuildCmd.buildLibrary();
		long endTime = System.currentTimeMillis();

		System.out.println("Total time is " + (float) (endTime - startTime)
				/ 1000.00 + " seconds.");

	}

	public JstLibBuildMain(String srcFolder, String outputFolder,
			String libName, Class filterClass) {
		m_sourceDirs = srcFolder;
		m_outputDirectory = outputFolder;
		m_outputFile = libName;
		m_filterClass = filterClass;
	}
	
	public void setJarIf(boolean jarIt){
		m_jarIt = jarIt;
	}

	protected void buildLibrary() {

		Object filter = null;

		if (m_filterClass != null) {
			try {
				filter = m_filterClass.newInstance();

				if (filter == null) {
					System.out.println("Filter class "
							+ m_filterClass.getName()
							+ " cann't be instantiated!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Filter class " + m_filterClass.getName()
						+ " cann't be instantiated!");
			}
		}

		if (filter == null) { // create default filter
			filter = new DefaultFilter();
		}

		List<File> srcDirs = getSourceDirs();

		if (srcDirs.size() == 0) {
			System.out.println("Cann't locate input source directory at "
					+ m_sourceDirs);
			return; // nothing to build
		}

		createOutputDirectory();

		List<JstType> jstTypeList = new ArrayList<JstType>();

		for (File srcDir : srcDirs) {
			m_currentSrcDir = srcDir; // build all types under current source
										// folder
			buildJstTypeList(srcDir, filter, jstTypeList);
		}

		serialize(jstTypeList); // serialize JstType library

		if (m_jarIt){
			createJarPackage(); // create the final package in jar
		}
	}

	protected void buildJstTypeList(File srcDir, Object filter,
			List<JstType> jstTypeList) {

		List<File> jsFileList = getJsFiles(srcDir, filter);

		Map<URI, String> javaFileMap = getJavaFiles(srcDir, filter);

		if (jsFileList.size() > 0) {
			jstTypeList.addAll(getJstTypes(jsFileList)); // add all JS - JST
															// types under
															// current src dir
		}

		if (javaFileMap.size() > 0) {
			jstTypeList.addAll(getJstTypes(javaFileMap)); // add all Java -
															// JST types under
															// current src dir
		}
	}

	private void createOutputDirectory() {
		File outDir = new File(m_outputDirectory);
		if (outDir.exists() && outDir.isDirectory()) {
			return;
		}
		if (!outDir.mkdir()) {
			throw new RuntimeException("Could not create directory: "
					+ m_outputDirectory);
		}
	}

	private List<File> getJsFiles(File srcDir, Object filter) {
		List<File> files = new ArrayList<File>();

		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File srcFile) {
				if (srcFile.isFile()
						&& (srcFile.getName().endsWith(JS_SUFFIX) || srcFile
								.getName().endsWith(VJO_SUFFIX))) {
					return true;
				} else {
					return false;
				}
			}
		};

		collectAllFiles(srcDir, filter, fileFilter, files);

		return files;
	}

	private void collectAllFiles(File srcDir, Object filter,
			FileFilter fileFilter, List<File> srcFileList) {
		File[] files = null;

		if (filter instanceof FileFilter) {
			files = srcDir.listFiles((FileFilter) filter);
		} else if (filter instanceof FilenameFilter) {
			files = srcDir.listFiles((FilenameFilter) filter);
		} else {
			files = srcDir.listFiles();
		}

		for (File file : files) {
			if (fileFilter.accept(file)) {
				srcFileList.add(file);
			}
		}

		File[] subDirs = srcDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		for (File subDir : subDirs) {
			collectAllFiles(subDir, filter, fileFilter, srcFileList); // add
																		// all
																		// types
																		// in
																		// each
																		// sub
																		// dir
		}

	}

	private String getSerializedFileName() {

		if (!m_outputFile.endsWith(LIB_SUFFIX)) {
			return m_outputDirectory + File.separator + m_outputFile
					+ LIB_SUFFIX;
		} else {
			return m_outputDirectory + File.separator + m_outputFile;
		}
	}

	private String getJarFileName() {

		if (!m_outputFile.endsWith(LIB_SUFFIX)) {
			return m_outputDirectory + File.separator + m_outputFile
					+ JAR_SUFFIX;
		} else {
			String jarFile = m_outputFile.replace(LIB_SUFFIX, JAR_SUFFIX);
			return m_outputDirectory + File.separator + jarFile;
		}
	}

	private void serialize(List<JstType> jstTypes) {
		String fileName = getSerializedFileName();

		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(fileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(jstTypes);
			System.out.println("Wrote library " + fileName); // KEEPME
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private List<JstType> getJstTypes(final Map<URI, String> files) {
		return getController().targetedTranslation(files);
	}

	private List<JstType> getJstTypes(final List<File> fileList) {
		List<JstType> jstTypeList = new ArrayList<JstType>();
		IJstParseController controller = getJstParseController();

		for (File srcFile : fileList) {
			try {
				IJstTypeLoader.SourceType srcType = DefaultJstTypeLoader
						.createType(m_outputFile, m_currentSrcDir.getPath(),
								srcFile);

				JstType jstType = (JstType) controller.parse(m_outputFile,
						srcType.getFileName(), srcType.getSource()).getType();
				jstTypeList.add(jstType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (JstType type : jstTypeList) {
			try {
				controller.resolve(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jstTypeList;
	}

	private IJstParseController getJstParseController() {
		if (m_JstParseController == null) {

			JstCache.getInstance().clear();
			LibManager.getInstance().clear();

			m_JstParseController = new JstParseController(new VjoParser());
			JstTypeSpaceMgr tsMgr = new JstTypeSpaceMgr(m_JstParseController,
					new DefaultJstTypeLoader());
			TsLibLoader.loadDefaultLibs(tsMgr);
		}

		return m_JstParseController;
	}

	protected TranslationController getController() {
		if (m_controller == null) {
			JstCache.getInstance().clear();
			LibManager.getInstance().clear();

			m_controller = new TranslationController();
//			m_controller = new TranslationController(new DefaultTranslationInitializer());
			TranslateCtx ctx = TranslateCtx.ctx().enableParallel(false)
					.enableTrace(false);
			// ctx.getConfig().setJsNativeTranslationEnabled(true);
			// ctx.getConfig().setConfigInitializer(new
			// JsNativeConfigInitializer());
			ctx.getConfig().setParseComments(true);
			// ctx.getConfig().setPreloadJsNativeTypes(false);
			m_controller.getConfig().setPolicy(getPolicy());
		}
		return m_controller;
	}

	private ITranslationPolicy getPolicy() {
		return new TranslationPolicy() {

			public boolean isClassExcluded(String clsName) {
				if (clsName
						.startsWith("com.ebay.internal.org.mozilla.javascript")
						|| clsName.startsWith("org.ebayopensource.dsf.jsnative.anno")
						|| clsName.endsWith("Scriptable")) {
					return true;
				}
				return false;
			}
		};
	}

	private Map<URI, String> getJavaFiles(File srcDir, Object filter) {
		Map<URI, String> srcFiles = new LinkedHashMap<URI, String>();
		List<File> files = new ArrayList<File>();
		String src;

		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File srcFile) {
				if (srcFile.isFile() && srcFile.getName().endsWith(JAVA_SUFFIX)
						&& !srcFile.getName().endsWith(JSR_SUFFIX)) {
					return true;
				} else {
					return false;
				}
			}
		};

		collectAllFiles(srcDir, filter, fileFilter, files);

		for (File file : files) {
			src = JavaToJsHelper.readFromFile(file.getPath());
				srcFiles.put(file.toURI(), src);
		}

		return srcFiles;
	}

	protected List<File> getSourceDirs() {

		String[] sources = m_sourceDirs.split("[,|;]");
		List<File> dirs = new ArrayList<File>();
		String currentDir = System.getProperty("user.dir");

		System.out.println("Current working directory is " + currentDir);

		for (String source : sources) {

			File srcDir = new File(source);

			if (!srcDir.exists() || !srcDir.isDirectory()) {

				String srcPath = currentDir + File.separatorChar + source;
				srcDir = new File(srcPath);
			}

			if (srcDir.exists() && srcDir.isDirectory()) {
				dirs.add(srcDir);
			}
		}

		return dirs;
	}

	private Manifest createManiFest() {

		Manifest mf = new Manifest();

		Attributes attrs = new Attributes();

		attrs.putValue("Bundle-SymbolicName", m_outputFile);
		attrs
				.putValue("VJO-Library-Type",
						"Jst library from Java or Js source");
		attrs.putValue("Jst-Version", "1.0");

		Map<String, Attributes> entries = mf.getEntries();

		entries.put(m_outputFile, attrs);

		return mf;
	}

	private void createJarPackage() {
		String jarFile = getJarFileName();
		ZipOutputStream out = null;

		try {
			File outputDir = new File(m_outputDirectory);

			if (outputDir.exists() && outputDir.isDirectory()) {

				Manifest mf = createManiFest();
				out = new JarOutputStream(new FileOutputStream(jarFile), mf);

				File[] files = outputDir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						if (name.endsWith(LIB_SUFFIX)
								&& name.contains(m_outputFile))
							return true;
						else
							return false;
					}
				});

				byte[] buf = new byte[1000000]; // 1M buffer for reading file

				for (File file : files) {
					FileInputStream in = new FileInputStream(file);
					out.putNextEntry(new ZipEntry(file.getName()));
					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					// Complete the entry
					out.closeEntry();
					in.close();

					file.delete(); // delete the file after it's packaged in
									// jar
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					// Complete the JAR file
					System.out.println("Wrote jar file " + jarFile);
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

	}
}
