/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class ClassPathUtils {

	public static Set<String> getProjectDependencyUrls(IJavaProject javaProject)
			throws JavaModelException, MalformedURLException {
		URL[] urls = getProjectDependencyUrlsInternal(javaProject);
		Set<String> urlsString = new HashSet<String>(urls.length);
		for (URL u : urls) {
			urlsString.add(u.getFile());
		}
		return urlsString;
	}

	private static URL[] getProjectDependencyUrlsInternal(
			IJavaProject javaProject) throws JavaModelException,
			MalformedURLException {
		List<IJavaProject> jps = new ArrayList<IJavaProject>(1);
		jps.add(javaProject);

		return getProjectDependencyUrls(jps, null,
				new HashMap<String, String>());
	}

	public static URL[] getProjectDependencyUrls(
			List<IJavaProject> javaProjectList, List<URL> currentUrlList,
			HashMap<String, String> projectMap) throws JavaModelException,
			MalformedURLException {

		List<URL> projectDependencyUrlList = currentUrlList;

		if (projectDependencyUrlList == null) {
			projectDependencyUrlList = new ArrayList<URL>();
		}

		for (IJavaProject project : javaProjectList) {

			if (projectMap.containsKey(project.getElementName()) == true) {
				continue;
			} else {
				projectMap.put(project.getElementName(), project
						.getElementName());
			}

			// Add the dependencies to the URL list
			IClasspathEntry[] entries;
			entries = project.getResolvedClasspath(true);

			for (IClasspathEntry entry : entries) {

				IPath path = entry.getPath();
				File f = path.toFile();
				URL entryUrl;
				entryUrl = f.toURI().toURL();
				switch (entry.getEntryKind()) {

				case IClasspathEntry.CPE_LIBRARY:
					if (projectDependencyUrlList.contains(entryUrl) == false) {
						projectDependencyUrlList.add(entryUrl);
					}
					break;

				case IClasspathEntry.CPE_PROJECT:
					List<IJavaProject> subjavaProjectList = new ArrayList<IJavaProject>();
					IResource subResource = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(entry.getPath());
					if (subResource == null) {
						// String projectName = entry.getPath().toString();
						// String parentProjectName = project.getElementName();
						// throw new EclipseProjectNotFoundException(
						// projectName,
						// MessageFormat
						// .format(
						// "The dependent project {0} of project {1} is not
						// available.\nPlease update your workspace to include
						// this project",
						// projectName, parentProjectName));
					}
					if (subResource != null
							&& subResource.getType() == IResource.PROJECT) {
						IProject subProject = (IProject) subResource;
						IJavaProject subJavaProject = JavaCore
								.create(subProject);
						if (subJavaProject != null && subJavaProject.exists()) {
							subjavaProjectList.add(subJavaProject);

							// Recursively call our selves to populate the
							// project
							// dependency's for the sub projects.
							getProjectDependencyUrls(subjavaProjectList,
									projectDependencyUrlList, projectMap);
						}

					}
					break;

				default:
					break;
				}
			}

			IPath path = project.getOutputLocation();
			IPath projectResourceLocation = project.getResource().getLocation();
			File projectFilePath = projectResourceLocation.append(
					path.removeFirstSegments(1)).toFile();
			URL projectOutputUrl;
			projectOutputUrl = projectFilePath.toURI().toURL();

			if (projectDependencyUrlList.contains(projectOutputUrl) == false) {
				projectDependencyUrlList.add(projectOutputUrl);
			}
		}

		URL[] arrayList = new URL[projectDependencyUrlList.size()];
		URL[] returnURLArray = projectDependencyUrlList.toArray(arrayList);

		return returnURLArray;

	}

	public static URL[] getProjectDependencyUrls(
			List<IScriptProject> scriptProjectList, List<URL> currentUrlList,
			Set<String> checkedProjects) throws MalformedURLException,
			ModelException {

		List<URL> projectDependencyUrlList = currentUrlList;

		for (IScriptProject project : scriptProjectList) {

			if (checkedProjects.contains(project.getElementName())) {
				continue;
			} else {
				checkedProjects.add(project.getElementName());
			}

			// Add the dependencies to the URL list
			IBuildpathEntry[] entries = null;
			entries = project.getResolvedBuildpath(true);

			for (IBuildpathEntry entry : entries) {
				IPath path = entry.getPath();
				if (EnvironmentPathUtils.isFull(path)) {
					path = EnvironmentPathUtils.getLocalPath(path);
				}
				URL entryUrl;
				switch (entry.getEntryKind()) {
				case IBuildpathEntry.BPE_SOURCE:
					IResource member = project.getProject().getWorkspace()
							.getRoot().findMember(path);
					if (member != null) {
						entryUrl = member.getLocationURI().toURL();
						addDependencyUrl(entryUrl, projectDependencyUrlList);
					}
					break;
				case IBuildpathEntry.BPE_LIBRARY:
					File f = path.toFile();
					entryUrl = f.toURI().toURL();
					addDependencyUrl(entryUrl, projectDependencyUrlList);
					break;

				case IBuildpathEntry.BPE_PROJECT:
					List<IScriptProject> subjavaProjectList = new ArrayList<IScriptProject>();
					IResource subResource = ResourcesPlugin.getWorkspace()
							.getRoot().findMember(entry.getPath());
					if (subResource != null
							&& subResource.getType() == IResource.PROJECT) {
						IProject subProject = (IProject) subResource;
						IScriptProject subScriptProject = DLTKCore
								.create(subProject);
						if (subScriptProject != null
								&& subScriptProject.exists()) {
							subjavaProjectList.add(subScriptProject);

							// Recursively call our selves to populate the
							// project
							// dependency's for the sub projects.
							getProjectDependencyUrls(subjavaProjectList,
									projectDependencyUrlList, checkedProjects);
						}

					}
					break;
				default:
					break;
				}
			}

		}

		URL[] arrayList = new URL[projectDependencyUrlList.size()];
		URL[] returnURLArray = projectDependencyUrlList.toArray(arrayList);

		return returnURLArray;

	}

	private static void addDependencyUrl(URL entryUrl,
			List<URL> projectDependencyUrlList) {
		if (!projectDependencyUrlList.contains(entryUrl)) {
			projectDependencyUrlList.add(entryUrl);
		}
	}

	public static Set<String> getProjectDependencyUrls(
			IScriptProject scriptProject) throws MalformedURLException,
			ModelException {
		URL[] urls = getProjectDependencyUrlsInternal(scriptProject);
		Set<String> urlsString = new HashSet<String>(urls.length);
		for (URL u : urls) {
			urlsString.add(u.getFile());
		}
		return urlsString;
	}

	private static URL[] getProjectDependencyUrlsInternal(
			IScriptProject scriptProject) throws MalformedURLException,
			ModelException {
		List<IScriptProject> scriptProjectList = new ArrayList<IScriptProject>(
				1);
		scriptProjectList.add(scriptProject);

		Set<String> checkedProjects = new HashSet<String>(1);

		List<URL> projectDependencyUrlList = new ArrayList<URL>();

		URL[] urls = getProjectDependencyUrls(scriptProjectList,
				projectDependencyUrlList, checkedProjects);
		return urls;
	}

}
