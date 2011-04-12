/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata.integration;


public interface IVjoTypeCompletionProposalFactory<IMAGE, CONTEXT_INFO> {

	IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> createTypeCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, int cursorPosition, int needsPosition, String typeName);

	IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> createTypeCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, int cursorPosition, int needsPosition, String typeName,
			IMAGE image, String displayString,
			CONTEXT_INFO contextInformation,
			String additionalProposalInfo);
}
