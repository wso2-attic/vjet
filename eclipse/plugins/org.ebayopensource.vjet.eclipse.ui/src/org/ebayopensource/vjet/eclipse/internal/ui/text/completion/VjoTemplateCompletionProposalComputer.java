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
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.ebayopensource.vjet.eclipse.internal.ui.templates.VjoTemplateCompletionProcessor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

/**
 * 
 * 
 */
public class VjoTemplateCompletionProposalComputer extends
		AbstractVjoCompletionProposalComputer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.text.completion.AbstractVjoCompletionProposalComputer#getRequestorCategory()
	 */
	@Override
	protected String getRequestorCategory() {
		return ICategoryRequestor.TYPE_CATEGORY;
	}

	@Override
	protected List computeScriptCompletionProposals(int offset,
			ScriptContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		return Collections.EMPTY_LIST;
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		return new VjoTemplateCompletionProcessor(context);
	}
}
