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
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;

public interface IVjoProposalAditionalInfoGeneratorAdapter {

	String getAdditionalPropesalInfo(IJstNode node);
	
	String getElementBriefDesc(IJstProperty property);
	
	String getElementBriefDesc(IJstMethod method);
	
	String getModifierListStr(JstModifiers jstModifiers);
	
	boolean isBrowserNoneNode(IJstNode node);
}
