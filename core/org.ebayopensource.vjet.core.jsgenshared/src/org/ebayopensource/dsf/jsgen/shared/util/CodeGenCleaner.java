/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;

public class CodeGenCleaner {
	
	public static final String ALL_OPT = "all";
	public static final String CSS_OPT = "css";
	public static final String JS_OPT = "js";
	
	private static final String JAVA_SUFFIX = ".java";
	private static final String JSR_SUFFIX = "Jsr.java";
	private static final String JS_SUFFIX = ".js";
	private static final String CCSR_SUFFIX = "Cssr.java";
	private static final String VJ_PKG_SPEC_SUFFIX = "VjPkgSpec.java";
	
	private static final String CODEGEN_STR = "@" + IClassR.CodeGen;
	private static final String CODEGEN_STR_V23 = "Generator: JsRefGenerator23";
	
		
	private static FilenameFilter s_cssFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			if (name.endsWith(CCSR_SUFFIX)) {
				return true;
			}
			return false;
		}
	};
		

	private static FilenameFilter s_jsFilter = new FilenameFilter() 
	{
		public boolean accept(File dir, String name) {
			if (name.endsWith(JS_SUFFIX)) {
				return true;
			}
			return false;
		}
	};
	
	private static FilenameFilter s_allFilter = new FilenameFilter() 
	{
		public boolean accept(File dir, String name) {
			if (name.endsWith(JS_SUFFIX) || name.endsWith(CCSR_SUFFIX)) {
				return true;
			}
			return false;
		}
	};
	
	private static Map<String, FilenameFilter> s_filterMap = 
		new HashMap<String, FilenameFilter>();
	
	private static boolean s_initialized = false;
		
	/**
	 * Cleans up the code generated files in the given source directories
	 * The first argument can be "all", "js", or "css"
	 * The second argument is the comma-separated list of source directories to check
	 * The third argument is optional and can only be "verbose"
	 * For "css" argument, all *Cssr.java files that are writable are deleted.
	 * For "js" argument, if xxx.js file has the CodeGen signature line, then this is Java2Js 
	 * code-gened and we delete xxx.js and xxxJsr.java file in the same directory.
	 * If xxx.js file does not contain the CodeGen signature line, then this is Js2Java JS file so
	 * we delete xxx.java (JavaNativeProxy) and xxxJsr.java files.
	 * For "all" argument, we perform all the above.
	 * @param args
	 */
	public static void main(String... args) {
		if (args.length < 1) {
			System.out.println("Usage: CodeGenCleaner all|js|css " +
				"src_dir_path_1,src_dir_path_2,..,src_dir_path_n  [verbose]");
			System.exit(1);
		}
		
		init();
		
		if (!s_filterMap.containsKey(args[0])) {
			System.out.println("Unknow argument " + args[0]);
			System.exit(1);
		}
		
		List<String> srcDirs = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(args[1], ",");
		while(st.hasMoreTokens()) {
			srcDirs.add(st.nextToken());
		}
		
		boolean verbose = false;
		if (args.length == 3 && args[2].equalsIgnoreCase("verbose")) {
			verbose = true;
		}
		
		try {
			clean(args[0], srcDirs, verbose);
		} catch (Exception e) {
			System.out.println("Error - " + e.getMessage());
			e.printStackTrace();	//KEEPME
			System.exit(1);
		}
	
	}

	/**
	 * Cleans up the code generated files in the given source directories
	 * 
	 * @param option
	 *            string can be "all", "js", or "css"
	 * @param srcDirs
	 *            list of source directory paths
	 * @param verbose
	 *            if true, prints the files being deleted
	 * @return number of files deleted
	 */
	public static int clean(String option, List<String> srcDirs, boolean verbose) {
		int deleted = 0;
		for (String dir : srcDirs) {
			File f = new File(dir);
			if (!f.exists()) {
				throw new RuntimeException("Directory doesn't exists - " + dir);
			}
			if (!f.isDirectory()) {
				throw new RuntimeException("Not a directory - " + dir);
			}
			List<File> answer;
			if (JS_OPT.equalsIgnoreCase(option) || ALL_OPT.equalsIgnoreCase(option)) {
				answer = new ArrayList<File>();
				getFiles(f, s_filterMap.get(JS_OPT), answer);
				deleted += deleteJsFiles(option, answer, verbose);
			}
			if (CSS_OPT.equalsIgnoreCase(option) || ALL_OPT.equalsIgnoreCase(option)) {
				answer = new ArrayList<File>();
				getFiles(f, s_filterMap.get(CSS_OPT), answer);
				deleted += deleteCssrFiles(option, answer, verbose);
			}
		}
		return deleted;
	}

	/**
	 * Returns true if file exits and is not a directory and is writable and
	 * contains the CodeGen annotation line.
	 * 
	 * @param file
	 *            File
	 * @return true if file is code-gened
	 */
	public static boolean isCodeGened(URL file) {
		return isCodeGened(file, true);
	}

	/**
	 * Returns true if file exits and is not a directory and is writable and
	 * contains the CodeGen annotation line.
	 * 
	 * @param file
	 *            File
	 * @return true if file is code-gened
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws MalformedURLException
	 */
	public static boolean isCodeGened(File file) {
		try {
			return isCodeGened(new FileInputStream(file), true);

		} catch (Exception e) {
			System.out.println("Error reading file - " + file); // KEEPME
			e.printStackTrace(); // KEEPME
		}
		return false;
	}
	/**
	 * Returns true if file exits and is not a directory and is writable and
	 * contains the CodeGen annotation line.
	 * 
	 * @param file
	 *            File
	 * @param checkWritable
	 *            true to check if file is writable
	 * @return true if file is code-gened
	 */
	public static boolean isCodeGened(File file, boolean checkWritable) {

		try {
			return isCodeGened(new FileInputStream(file), checkWritable);

		} catch (Exception e) {
			System.out.println("Error reading file - " + file); // KEEPME
			e.printStackTrace(); // KEEPME
		}
		return false;
	}
	/**
	 * Returns true if file exits and is not a directory and is writable and
	 * contains the CodeGen annotation line.
	 * 
	 * @param file
	 *            File
	 * @param checkWritable
	 *            true to check if file is writable
	 * @return true if file is code-gened
	 */
	public static boolean isCodeGened(URL file, boolean checkWritable) {

		try {
			return isCodeGened(file.openStream(), checkWritable);

		} catch (Exception e) {
			System.out.println("Error reading file - " + file); // KEEPME
			e.printStackTrace(); // KEEPME
		}
		return false;
	}

	public static boolean isCodeGened(InputStream stream, boolean checkWritable)
			throws IOException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			String line;
			boolean noContent = true;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					noContent = false;
				}
				// Check for both the old v23 codegen signature as well as the
				// current signature
				if (line.contains(CODEGEN_STR)
						|| line.contains(CODEGEN_STR_V23)) {
					return true;
				}
			}
			// If there is no content in the file and it's read-only
			// then consider it code-gen'ed so it gets overwritten
			if (noContent)
				return true;

		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// NOPMD - ignore
			}
		}
		return false;
	}

	private static int deleteCssrFiles(String option, List<File> list,
			boolean verbose) {
		int deleted = 0;
		for (File f : list) {
			try {
				if (!isCodeGened(f.toURI().toURL())) {
					continue;
				}
			} catch (MalformedURLException e) {
				System.out.println("Could not deleted file " + f);
			}

			deleted += delete(f, verbose);
		}
		return deleted;
	}

	private static int delete(File f, boolean verbose) {
		if (f.delete()) {
			if (verbose) {
				System.out.println("Deleted file " + f);
			}
			return 1;
		} else if (verbose) {
			System.out.println("Could not deleted file " + f);
		}
		return 0;
	}

	private static int deleteJsFiles(String option, List<File> list,
			boolean verbose) {
		int deleted = 0;
		for (File f : list) {
			int index = f.getAbsolutePath().lastIndexOf(JS_SUFFIX);
			if (index == -1) {
				continue;
			}
			String jsPath = f.getAbsolutePath().substring(0, index);
			String jsrPath = jsPath + JSR_SUFFIX;

			if (isCodeGened(f)) {
				// If xxx.js file is code-gened, then we delete
				// xxx.js and xxxJsr.java files
				deleted += delete(f, verbose);
				File jsr = new File(jsrPath);

				if (isCodeGened(jsr)) {
					// delete xxxJsr.java
					deleted += delete(jsr, verbose);
				}

			} else { // xxx.js file is not code-gened.
				// Delete _xxxVjPkgSpec.java, xxxJsr.java, and xxx.java
				File jsr = new File(jsrPath);

				if (isCodeGened(jsr)) {
					// delete xxxJsr.java
					deleted += delete(jsr, verbose);
				}

				String javaProxyPath = jsPath + JAVA_SUFFIX;
				File jProxy = new File(javaProxyPath);

				if (isCodeGened(jProxy)) {
					// delete xxx.java
					deleted += delete(jProxy, verbose);
				}

				String pkgSpecPath = createPkgSpecPath(jsPath);
				File jPkgSpec = new File(pkgSpecPath);
				if (jPkgSpec.exists() && !jPkgSpec.isDirectory()
						&& jPkgSpec.canWrite()) {
					// delete _xxxVjPkgSpec.java
					deleted += delete(jPkgSpec, verbose);
				}

			}

		}
		return deleted;
	}

	private static String createPkgSpecPath(final String path) {
		String pkgName = path.trim();
		int lastIndexOfPathSepertor = pkgName.lastIndexOf(File.separator);
		if (lastIndexOfPathSepertor != -1) {
			pkgName = pkgName.substring(0, lastIndexOfPathSepertor);
		}
		int lastIndexOfPathSepertor2 = pkgName.lastIndexOf(File.separator);
		if (lastIndexOfPathSepertor2 != -1) {
			pkgName = pkgName.substring(lastIndexOfPathSepertor2 + 1);
		}

		pkgName = pkgName.substring(0, 1).toUpperCase() + pkgName.substring(1);
		if (lastIndexOfPathSepertor != -1) {
			pkgName = path.substring(0, lastIndexOfPathSepertor + 1) + "_"
					+ pkgName + VJ_PKG_SPEC_SUFFIX;
		}

		return pkgName;
	}

	private static void getFiles(File src, FilenameFilter filter,
			List<File> answer) {

		File[] subDirs = src.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		for (File subDir : subDirs) {
			getFiles(subDir, filter, answer);
		}

		File[] files = src.listFiles(filter);
		if (files != null) {
			for (File f : files) {
				answer.add(f);
			}
		}
	}

	private static void init() {
		if (!s_initialized) {
			s_filterMap.put(ALL_OPT, s_allFilter);
			s_filterMap.put(JS_OPT, s_jsFilter);
			s_filterMap.put(CSS_OPT, s_cssFilter);
			s_initialized = true;
		}
	}

}
