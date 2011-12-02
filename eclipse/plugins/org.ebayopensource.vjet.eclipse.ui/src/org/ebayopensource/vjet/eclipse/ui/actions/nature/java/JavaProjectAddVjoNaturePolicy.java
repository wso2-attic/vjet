/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.ui.actions.nature.java;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.core.PiggyBackClassPathUtil;
import org.ebayopensource.vjet.eclipse.core.sdk.VjetSdkRuntime;
import org.ebayopensource.vjet.eclipse.ui.actions.nature.DefaultAddVjoNaturePolicy;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.internal.core.ScriptProject;
import org.eclipse.dltk.mod.launching.ScriptRuntime;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * later, this class will be extract to a new plug-in project
 * 
 * 
 *
 */
public class JavaProjectAddVjoNaturePolicy extends DefaultAddVjoNaturePolicy {
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.ui.actions.nature.DefaultAddVjoNaturePolicy#accept(org.eclipse.core.resources.IProject)
	 */
	public boolean accept(IProject project) {
		try {
			String[] natures = project.getDescription().getNatureIds();
			for (int i = 0; i < natures.length; i++) {
				if (JavaCore.NATURE_ID.equals(natures[i]))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.ui.actions.nature.DefaultAddVjoNaturePolicy#buildBuildPathFile(org.eclipse.core.resources.IProject)
	 */
	protected void buildBuildPathFile(IProject project) {
		try {
			if (project.getFile(".buildpath").exists())
				project.getFile(".buildpath").delete(false, null);
			
			IJavaProject javaProject = JavaCore.create(project);
			IBuildpathEntry[] entries = this.buildBuildpathEntry(javaProject);
			
			//temporary script project for encode build path
			ScriptProject scriptProject = new ScriptProject(project, null);
			String buildPathContent = scriptProject.encodeBuildpath(entries, true, null);
			
			IFile buildPathFile = project.getFile(ScriptProject.BUILDPATH_FILENAME);
			buildPathFile.create(new ByteArrayInputStream(buildPathContent.getBytes()), false, null);
			PiggyBackClassPathUtil.setProjectInitialized(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the dltk build path entries based on java project 
	 * 
	 * @param javaProject
	 * @return
	 */
	private IBuildpathEntry[] buildBuildpathEntry(IJavaProject javaProject) {
		try {
			List<IBuildpathEntry> buildpathEntryList = new ArrayList<IBuildpathEntry>();
			
			//create source dltk build path entries
			IClasspathEntry[] classPathEntries = javaProject.getRawClasspath();
			for (int i = 0; i < classPathEntries.length; i++) {
				if (IClasspathEntry.CPE_SOURCE == classPathEntries[i].getEntryKind()) {
					IBuildpathEntry buildpathEntry = DLTKCore.newSourceEntry(classPathEntries[i].getPath());
					buildpathEntryList.add(buildpathEntry);
				}
			}
			
			//create default interpreter build path entry
			IBuildpathEntry defaultInterpreterEntry = ScriptRuntime.getDefaultInterpreterContainerEntry();
			buildpathEntryList.add(defaultInterpreterEntry);
			//create default SDK build path entry
			IBuildpathEntry defaultSdkEntry = VjetSdkRuntime.getDefaultSdkContainerEntry();
			buildpathEntryList.add(VjetSdkRuntime.getJsSdkContainerEntry());
			buildpathEntryList.add(defaultSdkEntry);
			//create build path entry for vjet jar
			buildpathEntryList.addAll(PiggyBackClassPathUtil.fetchBuildEntryFromJavaProject(javaProject));
			return buildpathEntryList.toArray(new IBuildpathEntry[buildpathEntryList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return new IBuildpathEntry[0];
		}
	}

}
