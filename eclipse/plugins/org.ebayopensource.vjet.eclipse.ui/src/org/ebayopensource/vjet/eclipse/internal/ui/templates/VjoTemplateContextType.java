/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.templates;

import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;

public class VjoTemplateContextType extends ScriptTemplateContextType{

	public static final String VJO_CONTEXT_TYPE_ID = "vjoTemplateContextType";
	
	@Override
	public ScriptTemplateContext createContext(IDocument document,
			int completionPosition, int length, ISourceModule sourceModule) {

		return new VjoTemplateContext(this, document,
				completionPosition, length, sourceModule);

	}

}
