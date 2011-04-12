/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IEditorInput;

public class HtmlLaunchUtils {

	public static boolean isEditorInputHtml(IEditorInput input) {
		String temp = input.toString().toLowerCase() ;
		return temp.contains(".htm") ; 
	}
	
	public static String getSelectedResource() { 
		try {
			return VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(IHtmlLaunchConstants.HTML_SELECTED_RESOURCE) ;
			//return VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(IHtmlLaunchConstants.HTML_SELECTED_RESOURCE) ;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null ;		
	}
	
	public static String getEditorInputProject(IEditorInput input) { 
		Path temp = new Path(input.toString()) ; 
		return temp.segment(1) ;
	}
	
	// Check for available configurations to display to user, based on selection
	// Is the Main Class name 'VjoRunner'
	public static boolean isVjoRunnerMatch(ILaunchConfiguration config, Object resource) { 
		 try {
			 if (config.getAttribute("org.eclipse.jdt.launching.MAIN_TYPE", "")
					 .equals(IHtmlLaunchConstants.HTML_VJO_RUNNER_CLASS)) { 
				 return isResourceMatch(config, resource) ;
			 }
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false ; 
	}
	
	// Is the Main Class name 'HtmlDervletRunner'
	public static boolean isHtmlDervletMatch(ILaunchConfiguration config, Object resource) { 
		 try {
			 if (config.getAttribute(IHtmlLaunchConstants.ATTR_MAIN_TYPE_NAME,"")
					 .equals(IHtmlLaunchConstants.HTML_DERVLET_RUNNER_CLASS)){ 
				return isResourceMatch(config, resource);
			 }
		 } catch (CoreException e) {
			 e.printStackTrace();
		 }
		 return false ; 
	}
	// Does the project and full resource location match that of the selected resource
	// Matching resource by full location, since simple names (eg. Ex1.html) appear to be commonly used across packages.
	public static boolean isResourceMatch(ILaunchConfiguration config, Object resource) { 
		try { 
			String projectName = config.getAttribute(IHtmlLaunchConstants.ATTR_PROJECT_NAME,"") ;
			//String resourceName = config.getAttribute(IHtmlLaunchConstants.HTML_RESOURCE_NAME, "") ; 
			String resourceLoc = config.getAttribute(IHtmlLaunchConstants.HTML_RESOURCE_LOCATION, "") ; 
			if (resource instanceof IResource) { 
				IResource r = (IResource)resource ;  
				return (projectName.equals(r.getProject().getName()) && resourceLoc.equals((r.getLocation().toString().replace("/", "\\")) ) ) ;
				//return (projectName.equals(r.getProject().getName()) && resourceName.equals(r.getName()) ) ; 
			}
			if (resource instanceof IEditorInput) { 
				IEditorInput i = (IEditorInput)resource ; 
				String loc = i.toString(); 
				loc = i.toString().substring(loc.indexOf("/"), loc.indexOf(")")).replace("/", "\\") ; 
				return (projectName.equals(HtmlLaunchUtils.getEditorInputProject(i)) && resourceLoc.contains(loc));
				//return (projectName.equals(HtmlLaunchUtils.getEditorInputProject(i)) && resourceName.equals(i.getName())); 
			}
		} catch (CoreException e) { 
			e.printStackTrace() ; 
		}
		return false ; 
		
	}

}