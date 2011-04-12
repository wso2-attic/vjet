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

public interface IVjoProposalLabelProviderAdapter<IMAGE_DESCRIPTOR, IMAGE> {

	IMAGE_DESCRIPTOR getScriptImageDescriptor(IJstNode node);
	
	IMAGE getScriptImage(IJstNode node);
	
	IMAGE getMethodImage(int flags);
	
	IMAGE getTypeImageDescriptor(int flags, boolean useLightIcons);
}
