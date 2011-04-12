/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata.integration;

public interface IVjoEclipseCompletionProposal <IMAGE, CONTEXT_INFO> {

	String getAdditionalProposalInfo();
	
	String getReplacementString();
	
	int getReplacementOffset();
	
	int getReplacementLength();
	
	int getCursorPosition();
	
	int getContextInformationPosition();
	
	char[] getTriggerCharacters();
	
	IMAGE getImage();
	
	String getDisplayString();
	
	CONTEXT_INFO getContextInformation();
}
