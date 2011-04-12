/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata.integration;

import org.ebayopensource.dsf.jst.IJstMethod;

public interface IVjoMethodCompletionProposalFactory<IMAGE, CONTEXT_INFO> {

	IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> createMethodCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, int cursorPosition);

	IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> createMethodCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, int cursorPosition,
			IMAGE image, String displayString,
			CONTEXT_INFO contextInformation,
			String additionalProposalInfo, IJstMethod method);
}
