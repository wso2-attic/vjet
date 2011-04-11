/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.templates;

import org.eclipse.dltk.mod.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateCompletionProcessor;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;

/**
 * JavaScript template completion processor
 */
public class JavaScriptTemplateCompletionProcessor extends
		ScriptTemplateCompletionProcessor {

	private static char[] IGNORE = new char[] {'.', ':', '@', '$' };
	
	public JavaScriptTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext context) {
		super(context);
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplateCompletionProcessor#getContextTypeId()
	 */
	protected String getContextTypeId() {
		return JavaScriptUniversalTemplateContextType.CONTEXT_TYPE_ID;
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplateCompletionProcessor#getIgnore()
	 */
	protected char[] getIgnore() {
		return IGNORE;
	}
	
	/*
	 * @see org.eclipse.dltk.mod.ui.templates.ScriptTemplateCompletionProcessor#getTemplateAccess()
	 */
	protected ScriptTemplateAccess getTemplateAccess() {
		return JavaScriptTemplateAccess.getInstance();
	}
}
