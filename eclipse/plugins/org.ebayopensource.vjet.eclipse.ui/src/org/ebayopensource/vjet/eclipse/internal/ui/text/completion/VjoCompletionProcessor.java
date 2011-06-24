/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import java.util.List;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.javascript.internal.ui.text.completion.JavaScriptCompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.ContentAssistInvocationContext;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProcessor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.ui.IEditorPart;

/**
 * JavaScript completion processor
 */
public class VjoCompletionProcessor extends ScriptCompletionProcessor {

	public VjoCompletionProcessor(IEditorPart editor,
			ContentAssistant assistant, String partition) {
		super(editor, assistant, partition);
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProcessor#getNatureId()
	 */
	@Override
	protected String getNatureId() {
		return VjoNature.NATURE_ID;
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.text.completion.ContentAssistProcessor#getPreferenceStore()
	 */
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return VjetUIPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProcessor#getProposalLabelProvider()
	 */
	@Override
	protected CompletionProposalLabelProvider getProposalLabelProvider() {
		// FIXME replace label provider
		return new JavaScriptCompletionProposalLabelProvider();
	}
	
	protected List filterAndSortProposals(List proposals,
			IProgressMonitor monitor, ContentAssistInvocationContext context) {
		return proposals;
	}

}
