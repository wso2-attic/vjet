/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;



public interface IHtmlLaunchConstants {
	public static final String UNIQUE_IDENTIFIER = "org.eclipse.jdt.launching";
	
	// Specified Vjo Class/Type from the VJO Arguments tab
	@SuppressWarnings("restriction")
	public static final String HTML_MAIN_TYPE = UNIQUE_IDENTIFIER + ".VJO_MAIN_TYPE";	 
	
	// Specified browser type from the VJO Arguments tab.  
	// Drop down options include "IE" and "Firefox"; user selection is cast to the proper type: VjoRunner uses BrowserType; 
	// HtmlAsDervletRunner uses DisplayType 
	@SuppressWarnings("restriction")
	public static final String HTML_BROWSER_TYPE = UNIQUE_IDENTIFIER + ".VJO_BROWSER_TYPE" ;
	
	// The name of the selected resource. Used to locate appropriate config types in the launch shortcut
	@SuppressWarnings("restriction")
	public static final String HTML_RESOURCE_NAME = UNIQUE_IDENTIFIER + ".RESOURCE_NAME" ;
	

	
	// Argument representing the resource type, in this case, an HTML file.  This argument is not specified by the user, 
	// but needs to be passed to VjoRunner
	public static final String HTML_KEY_HTML_ARG_TYPE = "-Vtype=html" ;
	
	// Argument representing the browser type.  This argument is not specified by the user, but needs to be passed to VjoRunner
	public static final String HTML_KEY_BROWSER_TYPE = "-Vbrowser" ; 
	
	
	public static final String HTML_SELECTED_RESOURCE = "${resource_loc}" ; 
	// Fully qualified location of the resource.
	//
	@SuppressWarnings("restriction")
	//public static final String HTML_RESOURCE_LOCATION = LaunchingPlugin.getUniqueIdentifier() + ".VJO_HTML_LOC" ; 
//	public static final String HTML_RESOURCE_LOCATION = "${resource_loc}" ;
	public static final String HTML_RESOURCE_LOCATION = UNIQUE_IDENTIFIER + ".RESOURCE_LOC" ; 
	
	// ConfigurationType representing 'Run As -> VjO'
	public static final String HTML_RUN_AS_VJO_CONFIG_TYPE = "org.ebayopensource.vjet.eclipse.html.RunAsVjOLaunchConfigurationType" ;
	
	// Class used to run HTML as VJO
	public static final String HTML_VJO_RUNNER_CLASS = "org.ebayopensource.vjo.runner.VjoRunner" ; 
	
	// ConfigurationType representing 'Run As -> Dervlet'
	public static final String HTML_RUN_AS_DERVLET_CONFIG_TYPE = "org.ebayopensource.vjet.eclipse.html.DervletLaunchConfigurationType" ;
	
	// Class used to run HTML as a Dervlet
	public static final String HTML_DERVLET_RUNNER_CLASS = "org.ebayopensource.vjet.eclipse.html.launching.dervlet.HtmlDervletRunner" ; 

	public static final String ATTR_MAIN_TYPE_NAME = UNIQUE_IDENTIFIER + ".MAIN_TYPE";
	
	public static final String ATTR_PROJECT_NAME = UNIQUE_IDENTIFIER + ".PROJECT_ATTR";
	
	public static final String ATTR_PROGRAM_ARGUMENTS = UNIQUE_IDENTIFIER + ".PROGRAM_ARGUMENTS";
	
}
