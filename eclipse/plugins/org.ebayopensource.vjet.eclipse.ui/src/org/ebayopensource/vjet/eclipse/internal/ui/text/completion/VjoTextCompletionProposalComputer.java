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

import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

/**
 * 
 * 
 */
public class VjoTextCompletionProposalComputer extends
		AbstractVjoCompletionProposalComputer {

	@Override
	protected String getRequestorCategory() {
		return ICategoryRequestor.TEXT_CATEGORY;
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		return null;
	}
}
