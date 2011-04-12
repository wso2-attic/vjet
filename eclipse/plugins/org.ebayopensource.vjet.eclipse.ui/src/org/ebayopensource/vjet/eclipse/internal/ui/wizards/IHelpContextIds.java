/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;

/**
 * Help context ids.
 * 
 * 
 */
public interface IHelpContextIds {
	public static final String PREFIX = VjetUIPlugin.PLUGIN_ID + '.';

	// Wizard pages
	public static final String NEW_PROJECT = PREFIX
			+ "new_vjetproject_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_PACKAGE = PREFIX
			+ "new_package_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_CTYPE = PREFIX
			+ "new_ctype_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_ITYPE = PREFIX
			+ "new_interface_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_ETYPE = PREFIX
			+ "new_enum_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_MTYPE = PREFIX
			+ "new_mixin_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_OTYPE = PREFIX
			+ "new_otype_wizard_page_context"; //$NON-NLS-1$
	public static final String NEW_SOURCEFOLDER = PREFIX
			+ "new_sourcefolder_wizard_page_context"; //$NON-NLS-1$
	
	public static final String OUTLINE_VIEW = PREFIX
	+ "show_vjet_outline_action"; //$NON-NLS-1$
	public static final String SCRIPT_EXPLORER_VIEW = PREFIX
	+ "script_explorer_view_context"; //$NON-NLS-1$	
	public static final String MEMBERS_VIEW = PREFIX
	+ "members_view_context"; //$NON-NLS-1$	
	public static final String TYPES_VIEW = PREFIX
	+ "types_view_context"; //$NON-NLS-1$	
	public static final String Hierarchy_VIEW = PREFIX
	+ "hierarchy_view_context"; //$NON-NLS-1$	
	
	
	
	public static final String VJET_EDITOR = PREFIX
	+ "vjet_editor_context"; //$NON-NLS-1$
}
