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

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;


/**
 * code template type constants
 * 
 * 
 *
 */
public class CodeTemplateContextType extends TemplateContextType {
	private static final String CODETEMPLATES_PREFIX = "org.ebayopensource.vjet.eclipse.ui.text.codetemplates."; //$NON-NLS-1$
	public static final String COMMENT_SUFFIX= "comment";
	
	public static final String CTYPE_ID = CODETEMPLATES_PREFIX + "Ctype";
	public static final String INTERFACE_ID = CODETEMPLATES_PREFIX + "Interface";
	
	public CodeTemplateContextType(String contextName) {
		super(contextName);
	}
	
	public static void registerContextTypes(ContextTypeRegistry registry) {
		registry.addContextType(new CodeTemplateContextType(CodeTemplateContextType.CTYPE_ID));
		registry.addContextType(new CodeTemplateContextType(CodeTemplateContextType.INTERFACE_ID));
	}
}
