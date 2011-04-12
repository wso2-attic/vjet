/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.containers.ZipEntryStorage;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import org.ebayopensource.vjet.eclipse.core.ClassPathUtils;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;

/**
 * Copied from com.ebay.tools.applauncher.launch.LauncherUtil, to fix "arg too
 * long" issue
 * 
 * 
 * 
 */
public class LauncherUtil {

	public static final String	TEMP_VJET_LAUNCH	= "c:\\vjetl";
	public static final String	EXCLAMATION_MARK	= "!";
	public static final String	SCHEME_FILE			= "file";
	public static final String	SCHEME_JAR			= "jar";
	public static final String	SCHEME_ZIP			= "zip";

	/**
	 * Return the classpath using /* for java 6.
	 * 
	 * @param classpath
	 * @param rootDir
	 */
	private static void copyClasspathFiles(Set<String> classpath) {
		Set<String> libSet = new HashSet<String>(20000);

		System.out.println("Classpath:");

		try {
			File destDir = new File(TEMP_VJET_LAUNCH);
			destDir.mkdirs();

			for (String path : classpath) {

				System.out.println("\t" + path);
				File inputFile = new File(path);
				if (libSet.contains(inputFile.getName())) {
					continue;
				}
				if (path.endsWith(".jar")) {
					LauncherUtil.copyFile(destDir, inputFile);
					libSet.add(inputFile.getName());
				}
			}

		} catch (Exception e) {// Catch exception if any
			VjetPlugin.error("Failed to copy the jars to destination : "
					+ TEMP_VJET_LAUNCH, e, IStatus.ERROR);
		}
	}

	public static String[] getOptimizedClasspath(IJavaProject javaProject) {
		try {
			Set<String> paths = ClassPathUtils
					.getProjectDependencyUrls(javaProject);
			boolean isJava6 = isJava6Vm(null);
			if (!isJava6) {
				copyClasspathFiles(paths);
			}
			String[] optimizedClasspath = getOptimizedClasspath(paths, isJava6,
					TEMP_VJET_LAUNCH);
			return optimizedClasspath;
		} catch (CoreException e) {
			VjetPlugin.error("Failed to get optimized class path ", e,
					IStatus.ERROR);
			return new String[0];
		} catch (MalformedURLException e) {
			VjetPlugin.error("Failed to get optimized class path ", e,
					IStatus.ERROR);
			return new String[0];
		}
	}

	public static Set<String> getRawClasspath(IJavaProject javaProject)
			throws JavaModelException {
		Map<String, IJavaProject> transitiveClosureProjectList = new LinkedHashMap<String, IJavaProject>();
		getTransitiveClosureProjectDependnecyList(javaProject,
				transitiveClosureProjectList);
		Set<String> jars = new LinkedHashSet<String>();
		getProjectDependentJars(jars, javaProject.getRawClasspath());
		for (IJavaProject project : transitiveClosureProjectList.values()) {
			getProjectDependentJars(jars, project.getRawClasspath());
		}
		return jars;
	}

	/**
	 * 
	 * @param classpath
	 *            the raw class path array
	 * @param isJava6
	 * @param rootDir
	 * @return classPath array based on jre6 version as argument string as
	 *         argName (-cp or -Djava.class.path)
	 * @throws CoreException
	 */
	public static String[] getOptimizedClasspath(Set<String> classpath,
			boolean isJava6, String rootDir) throws CoreException {

		StringBuilder m_vmArg = new StringBuilder(20000);

		try {
			// Create file

			if (isJava6) {
				getJava6Classpath(classpath, m_vmArg);
			} else {
				getJava5Classpath(classpath, rootDir, m_vmArg);
			}
		} catch (Exception e) {// Catch exception if any
			VjetPlugin.error("Failed to get optimized class path", e,
					IStatus.ERROR);
		}

		return m_vmArg.toString().split(";");

	}

	/**
	 * 
	 * @param classpath
	 *            the raw class path array
	 * @param isJava6
	 * @param rootDir
	 * @return classPath array based on jre6 version as argument string as
	 *         argName (-cp or -Djava.class.path)
	 * @throws CoreException
	 */
	public static String getOptimizedClasspathArg(Set<String> classpath,
			boolean isJava6, String rootDir) throws CoreException {

		StringBuilder m_vmArg = new StringBuilder(20000);

		try {
			// Create file

			if (isJava6) {
				m_vmArg.append("-cp \"");
				getJava6Classpath(classpath, m_vmArg);
			} else {
				m_vmArg.append("-Djava.class.path=\"");
				getJava5Classpath(classpath, rootDir, m_vmArg);
			}
			m_vmArg.append("\"");

		} catch (Exception e) {// Catch exception if any
			VjetPlugin.error("Failed to get optimized class path", e,
					IStatus.ERROR);
		}

		return m_vmArg.toString();

	}

	public static void getTransitiveClosureProjectDependnecyList(
			IJavaProject javaProject,
			Map<String, IJavaProject> transitiveClosureProjectList)
			throws JavaModelException {

		IClasspathEntry[] classPathEntries = javaProject
				.getResolvedClasspath(true);

		if (classPathEntries != null) {
			for (IClasspathEntry classPathEntry : classPathEntries) {
				if (classPathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					IResource classPathProject = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(classPathEntry.getPath());
					if (classPathProject != null) {
						if (!transitiveClosureProjectList
								.containsKey(classPathProject.getName())) {

							IJavaProject subJavaProject = getJavaProject(classPathProject);
							transitiveClosureProjectList.put(classPathProject
									.getName(), subJavaProject);

							getTransitiveClosureProjectDependnecyList(
									subJavaProject,
									transitiveClosureProjectList);
						}
					}
				}
			}
		}

	}

	public static IJavaProject getJavaProject(IResource resource) {
		if (resource instanceof IProject) {
			return getJavaProject((IProject) resource);
		}
		return null;
	}

	public static IJavaProject getJavaProject(IProject project) {
		IJavaProject javaProject = JavaCore.create(project);
		return javaProject.exists() ? javaProject : null;
	}

	private static void getProjectDependentJars(Set<String> jars,
			IClasspathEntry[] classPathEntries) {
		if (classPathEntries != null) {
			for (IClasspathEntry classPathEntry : classPathEntries) {
				if (classPathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY
						|| classPathEntry.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
					IPath path = JavaCore.getResolvedClasspathEntry(
							classPathEntry).getPath();
					if (path != null) {
						String s = path.toString();
						if (!StringUtils.isBlankOrEmpty(s)) {
							jars.add(s);
						}
					}
				}
			}
		}
	}

	/**
	 * This method is used to get the class path for java6 based jvms. This
	 * method will use the existing directories and will take advantage of a
	 * feature in jvm6 that allows wildcards in the classpath.
	 * 
	 * @param classpath
	 * @param m_vmArg
	 */
	private static void getJava6Classpath(Set<String> classpath,
			StringBuilder m_vmArg) {

		Set<String> parentFolders = new HashSet<String>(1000);
		for (String path : classpath) {

			File inputFile = new File(path);
			String classPathEntry = "";

			if (inputFile.isFile()) {
				File parentFolder = inputFile.getParentFile();
				if (parentFolders.contains(parentFolder.getAbsolutePath())) {
					continue;
				}

				classPathEntry = parentFolder.getAbsolutePath() + "\\*;";
				parentFolders.add(parentFolder.getAbsolutePath());

			} else {
				classPathEntry = path + ";";

			}

			m_vmArg.append(classPathEntry);
		}
	}

	private static void getJava5Classpath(Set<String> classpath,
			String rootDir, StringBuilder m_vmArg) {

		Set<String> libSet = new HashSet<String>(1000);

		for (String path : classpath) {

			File inputFile = new File(path);
			String classPathEntry = "";
			if (libSet.contains(inputFile.getName())) {
				continue;
			}

			if (path.contains(".jar")) {
				classPathEntry = rootDir + "\\" + inputFile.getName() + ";";
				libSet.add(inputFile.getName());

			} else {
				classPathEntry = path + ";";
			}

			m_vmArg.append(classPathEntry);
		}
	}

	private static void copyFile(File destDir, File inputFile) throws Exception {

		IFileSystem fs = EFS.getLocalFileSystem();
		File outputFile = new File(destDir, inputFile.getName());

		// If the modified dates are different. Then do the copy.
		if (inputFile.lastModified() != outputFile.lastModified()) {

			long inputDateTime = inputFile.lastModified();
			IFileStore source = fs.getStore(inputFile.toURI());
			IFileStore dest = fs.getStore(outputFile.toURI());

			source.copy(dest, EFS.OVERWRITE, new NullProgressMonitor());

			outputFile.setLastModified(inputDateTime);
		}
	}

	/**
	 * Tries to determine the version of Java.
	 * 
	 * Warning: This assumes the version of java eclipse is running is the same
	 * version that the launcher is going to run.
	 * 
	 * @param configuration
	 * @return
	 * @throws CoreException
	 */
	public static boolean isJava6Vm(ILaunchConfiguration configuration)
			throws CoreException {

		boolean isJava6 = false;
		String javaVersion = System.getProperty("java.version");

		if (javaVersion.contains("1.6")) {
			isJava6 = true;
		}
		return isJava6;
	}

	public static ZipEntryStorage createZipEntryFile(final URI uri)
			throws ZipException, IOException {
		URI tempURI = uri;
		String schemeSpecificPart = null;
		// remove all schemes
		do {
			schemeSpecificPart = tempURI.getSchemeSpecificPart();
			tempURI = URI.create(schemeSpecificPart);
		} while (tempURI.getScheme() != null);

		// get the jar file name
		int pos = schemeSpecificPart.indexOf(EXCLAMATION_MARK);
		if (pos == -1) {
			return null;
		}
		String[] paths = schemeSpecificPart.split(EXCLAMATION_MARK);
		if (paths.length != 2) {
			return null;
		}
		File jarFile = new File(paths[0]);

		ZipEntry zipEntry = new ZipEntry(paths[1].substring(1));
		ZipFile zipFile = new ZipFile(jarFile);
		return new ZipEntryStorage(zipFile, zipEntry) {

			@Override
			public IPath getFullPath() {
				return new Path(uri.toString());
			}

		};
	}

	public static boolean isFileScheme(String scheme) {
		return SCHEME_FILE.equals(scheme);
	}

	public static boolean isZipScheme(String scheme) {
		return SCHEME_JAR.equals(scheme) || SCHEME_ZIP.equals(scheme);
	}

	public static String[] getOptimizedBuildpath(IScriptProject scriptProject) {
		try {
			Set<String> urlsString = ClassPathUtils
					.getProjectDependencyUrls(scriptProject);
			boolean isJava6 = isJava6Vm(null);
			if (!isJava6) {
				copyClasspathFiles(urlsString);
			}
			return getOptimizedClasspath(urlsString, isJava6, TEMP_VJET_LAUNCH);
		} catch (Exception e) {
			VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
		}
		return new String[0];

	}

}
