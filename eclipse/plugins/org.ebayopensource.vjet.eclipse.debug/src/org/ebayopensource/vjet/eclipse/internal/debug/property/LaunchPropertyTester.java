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
package org.ebayopensource.vjet.eclipse.internal.debug.property;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

import org.ebayopensource.vjet.eclipse.core.VjoNature;

/**
 * 
 *
 */
public class LaunchPropertyTester extends PropertyTester {
	private static final String LAUNCH_JS = "launchJS";
	private static final String LAUNCH_HTML = "launchHTML";
	
	
	
	/**
	 * 
	 */
	public LaunchPropertyTester() {
		System.out.print("");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		IResource resource = (IResource)((IAdaptable)receiver).getAdapter(IResource.class);
		if (!(resource instanceof IFile))
			return false;
		
		if (LAUNCH_JS.equalsIgnoreCase(property)) {
			return this.lauchJS(resource);
		}
		else if (LAUNCH_HTML.equalsIgnoreCase(property)) {
			return this.lauchHTML(resource);
		}
		return false;
	}

	/**
	 * first, has vjo nature
	 * second, file extension is js
	 * 
	 * @param resource
	 * @return
	 */
	private boolean lauchJS(IResource resource) {
		try {
			IProject project = resource.getProject();
			final boolean isVJOProject = project.isAccessible() && project.hasNature(VjoNature.NATURE_ID);
			
			final boolean isJSFile = "js".equalsIgnoreCase(resource.getFileExtension());
			
			return isVJOProject&&isJSFile;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 *  first, has vjo nature
	 *  second, file extension is html or htm
	 *  
	 * @param resource
	 * @return
	 */
	private boolean lauchHTML(IResource resource) {
		try {
			IProject project = resource.getProject();
			final boolean isVJOProject = project.isAccessible() && project.hasNature(VjoNature.NATURE_ID);
			
			final boolean isHTMLFile = "html".equalsIgnoreCase(resource
					.getFileExtension())
					|| "htm".equalsIgnoreCase(resource.getFileExtension());
			
			return isVJOProject&&isHTMLFile;
		} catch (Exception e) {
			return false;
		}
	}
}
