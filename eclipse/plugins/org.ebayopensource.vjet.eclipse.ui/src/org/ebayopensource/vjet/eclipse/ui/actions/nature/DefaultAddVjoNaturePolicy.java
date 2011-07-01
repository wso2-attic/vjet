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
package org.ebayopensource.vjet.eclipse.ui.actions.nature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 *
 */
public class DefaultAddVjoNaturePolicy implements IAddVjoNaturePolicy {
	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.ui.actions.nature.IAddVjoNaturePolicy#accept(org.eclipse.core.resources.IProject)
	 */
	public boolean accept(IProject project) {
		return true;
	}
	
	/* 
	 * template method
	 * 
	 * @see org.ebayopensource.vjet.eclipse.ui.actions.nature.IAddVjoNaturePolicy#addVjoNature(org.eclipse.core.resources.IProject)
	 */
	public void addVjoNature(IProject project) {
		//code extracted from AddVjoNatureAction class
		this.buildBuildPathFile(project);
		this.addVjoNatureID(project);
	}

	/**
	 * @param project
	 */
	protected void addVjoNatureID(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natureIds = description.getNatureIds();
			
			//remove first
			List<String> natureList = Arrays.asList(natureIds);
			Set<String> natureSet = new HashSet<String>();
			natureSet.addAll(natureList);
			natureSet.remove(VjoNature.NATURE_ID);
			description.setNatureIds(natureSet.toArray(new String[natureSet.size()]));
			project.setDescription(description, null);
			
			//add VJO Nature
			String[] newNatureIds = new String[natureSet.size() + 1];
			System.arraycopy(natureSet.toArray(), 0, newNatureIds, 1,
					natureSet.size());
			newNatureIds[0] = VjoNature.NATURE_ID;
			description.setNatureIds(newNatureIds);
			project.setDescription(description, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param project
	 */
	protected void buildBuildPathFile(IProject project) {
		try {
			if (project.getFile(".buildpath").exists())
				project.getFile(".buildpath").delete(false, null);
			
			project.getFile(".buildpath").create(
					DefaultAddVjoNaturePolicy.class.getResourceAsStream(
							"buildpath.snap"), true, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
