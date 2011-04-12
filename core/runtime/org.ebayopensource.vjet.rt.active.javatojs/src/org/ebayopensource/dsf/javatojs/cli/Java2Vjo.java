/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.cli;

import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.control.ICodeGenPathResolver;
import org.ebayopensource.dsf.javatojs.util.JavaToJsHelper;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class Java2Vjo {

	private static String s_help = "Java2Vjo [options]\n"
			+ "Options:\n"
			+ "\t[file-path] is the path to a single java file or a directory that contains\n"
			+ "\tjava source files. You MUST provide at least one valid path.  You can provide\n" 
			+ "\t more than one.\n\n"
			+ "\t-verbose print debugging information. Default: Off.\n"
			+ "\t-ot OnDemand translation. Default: On\n"
			+ "\t-tt Targetted translation. Default: Off [Either runs in OnDemand or Targeted not Both]\n"
			+ "\t-trace Enable tracing. Default: Off.\n"
			+ "\t-nojsr Do not create Jsr files. Default: Off [will create jsr files].\n"
			+ "\t-help Display this help menu.\n\n"
			+ "Example: Java2Vjo c:/sample c:/sample2 -ot -nojsr";

	private boolean m_traceEnabled = false;
	private boolean m_genJsr = true;
	private boolean m_includeChildPkgs = true;
	private boolean m_verbose = false;
	private boolean m_onDemand = true;
	private boolean m_help = false;

	public void generate(List<URL> files) {

		BuildController controller = new BuildController();
		controller.enableTrace(m_traceEnabled);

		controller.setGenJsr(m_genJsr);
		controller.setVerbose(m_verbose);
		if (m_onDemand) {
			controller.setUseOnDemand(true);
		} else {
			controller.setUseOnDemand(false);
		}
		setUpCodeGenPathResolver(controller, files);
		for (URL file : files) {
			if (file.getPath().endsWith(".java")) {
				ArrayList<URL> list = new ArrayList<URL>(1);
				list.add(file);
				controller.buildFiles(list);
			} else {
				controller.setIncludeChildPkgs(m_includeChildPkgs);
				controller.buildPackage(file);
			}
		}
		controller.reset();
	}

	private void setUpCodeGenPathResolver(final BuildController controller,
			List<URL> files) {
		try {
			final List<File> javaFiles = new ArrayList<File>();
			for (URL url : files) {
				if (url.getProtocol().equalsIgnoreCase("file")) {

					final File currentFile = new File(url.toURI());
					listJavaFiles(currentFile, javaFiles);

				}
			}
			if (!javaFiles.isEmpty()) {
				final File currentFile = javaFiles.get(0);
				final String packagePath = JavaToJsHelper
						.getPkgNameFromSrc(JavaToJsHelper
								.readFromInputReader(new InputStreamReader(
										currentFile.toURI().toURL()
												.openStream())));
				final File genDir = new File(currentFile.getCanonicalPath()
						.substring(
								0,
								currentFile.getCanonicalPath().indexOf(
										(packagePath.replace('.',File.separatorChar)))));

				controller.setCodeGenPathResolver(new ICodeGenPathResolver() {
					@Override
					public URL getJavaFilePath(JstType type)
							throws MalformedURLException {
						
						URL url = ICodeGenPathResolver.DEFAULT
								.getJavaFilePath(type);
						System.out.println("Java : "+url);
						return  url;
					}

					@Override
					public URL getJsrFilePath(JstType type)
							throws MalformedURLException {
						final URL url = ICodeGenPathResolver.DEFAULT
								.getJsrFilePath(type);
						System.out.println("JSR  : "+url);
						if (url.getProtocol().equalsIgnoreCase("file")) {
							return url;
						} else {
							return new File(genDir, ((type.getName().replace(
									'.', '/')) + "Jsr.java")).toURI().toURL();
						}
					}

					@Override
					public URL getVjoFilePath(JstType type)
							throws MalformedURLException {
						final URL url = ICodeGenPathResolver.DEFAULT
								.getVjoFilePath(type);
						System.out.println("JS   : "+url);
						if (url.getProtocol().equalsIgnoreCase("file")) {
							return url;
						} else {
							return new File(genDir, ((type.getName().replace(
									'.', '/')) + ".js")).toURI().toURL();
						}
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listJavaFiles(File dir, final List<File> files) {
		if (dir.isDirectory()) {
			final File[] children = dir.listFiles();
			for (File child : children) {
				if (child.isFile()) {
					if (child.getName().endsWith(".java")) {
						files.add(child);
					}
				} else {
					listJavaFiles(child, files);
				}
			}
		} else {
			if (dir.getName().endsWith(".java")) {
				files.add(dir);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args == null || args.length < 1) {
			showHelp();
			return;
		}

		Java2Vjo java2Vjo = new Java2Vjo();
		List<URL> files = new ArrayList<URL>();
		processOptions(java2Vjo, args, files);

		if (java2Vjo.isHelp()) {
			showHelp();
			return;
		}

		if (files.size() == 0) {
			System.out.println("ERROR: No Valid File Paths Found!");
			return;
		}

		System.out.print("Options: [");
		System.out.print("GenJsr "
				+ (java2Vjo.m_genJsr ? "enabled" : "disabled"));
		System.out.print(", OnDemand "
				+ (java2Vjo.m_onDemand ? "enabled" : "disabled"));
		System.out.print(", Trace "
				+ (java2Vjo.m_traceEnabled ? "enabled" : "disabled"));
		System.out.print(", Verbose "
				+ (java2Vjo.m_verbose ? "enabled" : "disabled"));
		System.out.println("]");

		java2Vjo.generate(files);
		System.out.println("Java2Vjo completed successfully");
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
		m_verbose = verbose;
	}

	public boolean isOnDemand() {
		return m_onDemand;
	}

	public void setOnDemand(boolean value) {
		m_onDemand = value;
	}

	public boolean isHelp() {
		return m_help;
	}

	public void setHelp(boolean value) {
		m_help = value;
	}

	private static void processOptions(Java2Vjo java2Vjo, String[] args,
			List<URL> files) {
		File file = null;
		for (int i = 0; i < args.length; i++) {
			String opt = args[i].trim();
			if (opt.equalsIgnoreCase(Options.NOJSR.toString())) {
				java2Vjo.setGenJsr(false);
			} else if (opt.equalsIgnoreCase(Options.OT.toString())) {
				java2Vjo.setOnDemand(true);
			} else if (opt.equalsIgnoreCase(Options.TT.toString())) {
				java2Vjo.setOnDemand(false);
			} else if (opt.equalsIgnoreCase(Options.TRACE.toString())) {
				java2Vjo.setTraceEnabled(true);
			} else if (opt.equalsIgnoreCase(Options.VERBOSE.toString())) {
				java2Vjo.setVerbose(true);
			} else if (opt.equalsIgnoreCase(Options.HELP.toString())) {
				java2Vjo.setHelp(true);
				return;
			} else if (!opt.startsWith("-")) {
				file = new File(opt);
				if (file.exists()) {
					try {
						files.add(file.toURI().toURL());
					} catch (MalformedURLException e) {
						System.out.println("File/Path Does Not Exist: " + opt);
					}
				} else {
					System.out.println("File/Path Does Not Exist: " + opt);
				}
			} else {
				System.out.println("Ignoring unknown option: " + opt);
			}
		}

	}

	private static void showHelp() {
		System.out.println(s_help);
	}

	enum Options {

		OT("-ot"), TT("-tt"), TRACE("-trace"), NOJSR("-nojsr"), VERBOSE(
				"-verbose"), HELP("-help");

		private String m_value;

		Options(String value) {
			m_value = value;
		}

		@Override
		public String toString() {
			return m_value;
		}

	}

}
