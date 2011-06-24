/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import org.eclipse.jdt.ui.IPackagesViewPart;

public class VjetUIConstants {

	/**
	 * The id of the Java action set
	 * (value <code>"org.eclipse.jdt.ui.JavaActionSet"</code>).
	 */
	public static final String ID_ACTION_SET = "org.ebayopensource.vjet.eclipse.ui.VjetActionSet"; //$NON-NLS-1$
	
	/**
	 * The id of the Java Element Creation action set
	 * (value <code>"org.eclipse.jdt.ui.JavaElementCreationActionSet"</code>).
	 * 
	 * @since 2.0
	 */
	public static final String ID_ELEMENT_CREATION_ACTION_SET = "org.eclipse.dltk.mod.ui.JavascriptElementCreationActionSet"; //$NON-NLS-1$
	
	/**
	 * The view part id of the Packages view
	 * (value <code>"org.eclipse.jdt.ui.PackageExplorer"</code>).
	 * <p>
	 * When this id is used to access
	 * a view part with <code>IWorkbenchPage.findView</code> or 
	 * <code>showView</code>, the returned <code>IViewPart</code>
	 * can be safely cast to an <code>IPackagesViewPart</code>.
	 * </p>
	 *
	 * @see IPackagesViewPart
	 * @see org.eclipse.ui.IWorkbenchPage#findView(java.lang.String)
	 * @see org.eclipse.ui.IWorkbenchPage#showView(java.lang.String)
	 */ 
	public static final String ID_PACKAGES = "org.eclipse.dltk.mod.ui.browsing.PackageExplorer"; //$NON-NLS-1$
	public static final String ID_SCRIPTEXPLORER = "org.eclipse.dltk.mod.ui.ScriptExplorer"; //$NON-NLS-1$
	public static final String ID_CALLHIERARCHY = "org.eclipse.dltk.mod.callhierarchy.view"; //$NON-NLS-1$
	public static final String ID_SCRIPTUNIT = "org.ebayopensource.vjet.eclipse.ui.view.scriptUnit"; //$NON-NLS-1$
	public static final String ID_TYPESPACE = "org.ebayopensource.vjet.eclipse.ui.view.typeSpace"; //$NON-NLS-1$
	public static final String ID_AST = "org.ebayopensource.vjet.eclipse.ui.view.AST"; //$NON-NLS-1$
	public static final String ID_VJOEDITOR = "org.ebayopensource.vjet.ui.VjetJsEditor"; //$NON-NLS-1$
	
	/**
	 * The view part id of the Java Browsing Projects view
	 * (value <code>"org.eclipse.jdt.ui.ProjectsView"</code>).
	 * 
	 * @since 2.0
	 */
	public static final String ID_PROJECTS_VIEW = "org.eclipse.dltk.mod.ui.browsing.ProjectsView"; //$NON-NLS-1$

	/**
	 * The view part id of the Java Browsing Packages view
	 * (value <code>"org.eclipse.jdt.ui.PackagesView"</code>).
	 * 
	 * @since 2.0
	 */
	public static final String ID_PACKAGES_VIEW = "org.eclipse.dltk.mod.ui.browsing.PackagesView"; //$NON-NLS-1$

	/**
	 * The view part id of the Java Browsing Types view
	 * (value <code>"org.eclipse.jdt.ui.TypesView"</code>).
	 * 
	 * @since 2.0
	 */
	public static final String ID_TYPES_VIEW = "org.eclipse.dltk.mod.ui.browsing.TypesView"; //$NON-NLS-1$

	/**
	 * The view part id of the Java Browsing Members view
	 * (value <code>"org.eclipse.jdt.ui.MembersView"</code>).
	 * 
	 * @since 2.0
	 */
	public static final String ID_MEMBERS_VIEW = "org.eclipse.dltk.mod.ui.browsing.MembersView"; //$NON-NLS-1$
	
	/** 
	 * The view part id of the source (declaration) view
	 * (value <code>"org.eclipse.jdt.ui.SourceView"</code>).
	 *
	 * @see org.eclipse.ui.IWorkbenchPage#findView(java.lang.String)
	 * @see org.eclipse.ui.IWorkbenchPage#showView(java.lang.String)
	 * @since 3.0
	 */ 
	public static final String ID_SOURCE_VIEW =	"org.eclipse.dltk.mod.ui.browsing.SourceView"; //$NON-NLS-1$
	
	/** 
	 * The view part id of the Javadoc view
	 * (value <code>"org.eclipse.jdt.ui.JavadocView"</code>).
	 *
	 * @see org.eclipse.ui.IWorkbenchPage#findView(java.lang.String)
	 * @see org.eclipse.ui.IWorkbenchPage#showView(java.lang.String)
	 * @since 3.0
	 */ 
	public static final String ID_JAVADOC_VIEW = "org.eclipse.dltk.mod.ui.browsing.JavadocView"; //$NON-NLS-1$
	
	/**
	 * A named preference that controls the layout of the Java Browsing views vertically. Boolean value.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true<code> the views are stacked vertical.
	 * If <code>false</code> they are stacked horizontal.
	 * </p>
	 */
	public static final String BROWSING_STACK_VERTICALLY= "org.eclipse.dltk.mod.ui.browsing.stackVertically"; //$NON-NLS-1$
}
