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
package org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle;

import java.io.File;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.templates.Template;

/**
 * 
 *
 */
public class CodeTemplates extends TemplateSet {

	private static final String TEMPLATE_FILE= "codetemplates.xml"; //$NON-NLS-1$

	/** Singleton. */
	private static CodeTemplates fgTemplates;

	public static Template getCodeTemplate(String name) {
		return getInstance().getFirstTemplate(name);
	}

	/**
	 * Returns an instance of templates.
	 */
	public static CodeTemplates getInstance() {
		if (fgTemplates == null)
			fgTemplates= new CodeTemplates();
		
		return fgTemplates;
	}
	
	private CodeTemplates() {
		super("codetemplate", VjetUIPlugin.getDefault().getCodeTemplateContextRegistry()); //$NON-NLS-1$
		create();
	}
	
	private void create() {
		
		try {
			File templateFile= getTemplateFile();
			if (templateFile.exists()) {
				addFromFile(templateFile, false);
			}

		} catch (CoreException e) {
			e.printStackTrace();
			clear();
		}

	}	
	
	/**
	 * Resets the template set.
	 */
	public void reset() throws CoreException {
	}

	/**
	 * Resets the template set with the default templates.
	 */
	public void restoreDefaults() throws CoreException {
	}

	/**
	 * Saves the template set.
	 */
	public void save() throws CoreException {					
	}

	private static File getTemplateFile() {
		IPath path= VjetUIPlugin.getDefault().getStateLocation();
		path= path.append(TEMPLATE_FILE);
		
		return path.toFile();
	}

}

