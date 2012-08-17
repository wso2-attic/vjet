/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifactmanager.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.ebayopensource.vjet.testframework.artifact.IArtifactDef;
import org.ebayopensource.vjet.testframework.artifact.IArtifactManager;
import org.ebayopensource.vjet.testframework.sandbox.ISandbox;
import org.ebayopensource.vjet.testframework.util.TestUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;



/**
 * @author ddodd
 *
 * This artifact manager is used to materialize projects into 
 * a sandbox directory.
 * 
 */
public class ProjectArtifactManager  implements IArtifactManager {

	String m_projectName;

	String m_testBundleId;

	ISandbox m_sandbox;

	IArtifactDef m_artifactDef;

	public static final String PROJECT_NAME_ATTRIBUTE = "projectName";

	public static final String TESTBUNDLE_NAME_ATTRIBUTE = "testBundleId";

	public final String ARTIFACT_MANAGER_ID = "org.ebayopensource.vjet.testframework.artifact.project";

	public void init(IArtifactDef artifactDef, ISandbox sandBox) {

		Map<String, String> artifactAttributes = artifactDef
				.getArtifactAttributes();
		m_projectName = artifactAttributes.get(PROJECT_NAME_ATTRIBUTE);
		m_testBundleId = artifactAttributes.get(TESTBUNDLE_NAME_ATTRIBUTE);

		if (m_projectName == null) {
			throw new RuntimeException(
					"projectName attribute not found in project artifact element");

		}
		
		if (m_testBundleId == null) {
			throw new RuntimeException(
					"Test bundle id not defined.  Please enter the id of the bundle that houses the project artifact.");
		}


		m_artifactDef = artifactDef;
		m_sandbox = sandBox;
	}

	public boolean setUp() {
		File targetProjectDir = getTargetProjectDir();

		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject project = workspace.getRoot().getProject(m_projectName);
			if (project.exists()) {
				return false;
			}
			
			IWorkspaceDescription wsDescription= workspace.getDescription();
			wsDescription.setAutoBuilding(true);

			Bundle bundle = Platform.getBundle(m_testBundleId);

			// Places project into sandbox.
			materializeProject(bundle, m_projectName, targetProjectDir);

			return true;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	File getTargetProjectDir() {
		File projectDir = new File(m_sandbox.getSandBoxDir(), "projects"
				+ File.separator + m_projectName);
		return projectDir;

	}

	public void tearDown() {

		File  targetProjectDir = getTargetProjectDir();

		try {
			FileUtils.deleteDirectory(targetProjectDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void materializeProject(Bundle bundle, String projectName, File targetProjectDir) {

		try {

			URL root = bundle.getEntry("");
			root = FileLocator.resolve(root);

			// If this is not a jar, then it is installed on the file system,
			// someplace
			if (!"jar".equals(root.getProtocol())) {

				handleFileInstallation(bundle, projectName, targetProjectDir);

			} else {

				handleZipInstallation(bundle, projectName, targetProjectDir);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleZipInstallation(Bundle bundle, String projectName, File targetProjectDir) {

		URL bundleURL;
		try {
			bundleURL = FileLocator.resolve(bundle.getEntry("/"));
			String jarFilename = bundleURL.getFile();

			URL jarUrl = new URL(jarFilename);
			File  bundleJar = new File(jarUrl.getPath().substring(0, jarUrl.getPath().length()-2));
			
			if (bundleJar.exists() == false) {
				throw new RuntimeException(
						"Project artifact not found:"
								+ bundleJar.getAbsoluteFile().toString());
			}

			// Since the zip file contains projects/<projectName> we will output
			// into a folder two directories up.
			File targetZipDir = new File(targetProjectDir, "../..");
			
			unzipProject(bundleJar, targetZipDir, projectName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to create project directory:" +projectName);
		} 

	}

	public static String unzipProject(File zipFile, File destDir,
			String projectName) throws IOException {
		if (!TestUtils.isDirectoryWritable(destDir, true)) {
			return "unable to create writable directory: " + destDir;
		}
		if (!TestUtils.isFileReadable(zipFile)) {
			return "no readable file: " + zipFile;
		}

		ZipFile zip = null;
		String projectEntryName = "projects" + "/" + projectName;
		try {
			zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (!entry.isDirectory()
						&& entry.getName().startsWith(projectEntryName)) {
					File destFile = new File(destDir, entry.getName());
					InputStream src = null;
					try {
						src = zip.getInputStream(entry);
						String result = TestUtils.write(src, destFile);
						if (null != result) {
							return result;
						}
					} finally {
						if (null != src) {
							src.close();
						}
					}
				}
			}
		} finally {
			if (null != zip) {
				zip.close();
			}
		}
		return null;
	}

	private void handleFileInstallation(Bundle bundle, String projectName, File targetProjectDir) {

		URL bundleURL = null;
		try {

			bundleURL = FileLocator.resolve(bundle.getEntry("/"));
			File bundleDir = new File(bundleURL.toURI());
			File sourceProjectDir = new File(bundleDir, "projects" + File.separator
					+ projectName);

			FileUtils.copyDirectory(sourceProjectDir, targetProjectDir);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to create project directory:" +projectName);
		} 
	}

	public String getArtifactType() {
		return ARTIFACT_MANAGER_ID;
	}

	
	
}
