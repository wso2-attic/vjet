/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata.integration;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;

public interface IVjoProposalLabelUtilAdapter<DOCUMENT, INDENTER> {

	String evaluateIndent(String str, DOCUMENT document,
			int replaceOffset);
	
	
	String calculateIndent(DOCUMENT document, int offset);
	
	int translateModifers(int jstModifer);
	
	INDENTER getIndenter();
	
	int getVjoModifierForImage(JstModifiers modifiers);
	
	int getDltkModifyFlag(JstModifiers modifiers);
	
	JstModifiers getModifiers(IJstNode node);
}
