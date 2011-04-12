/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.List;

/**
 * Embedded in VjoCcCtx,and get back all the proposals info
 * 
 *
 */
public interface IVjoCcReporter {
	
	/**
	 * add a VjoCcProposalData, if a confict proposal data is there, the data should be thrown away
	 * @param data
	 */
	void addPropsal(IVjoCcProposalData data);
	
	/**
	 * Return all the proposal data
	 * @return
	 */
	List<IVjoCcProposalData> getProposalData();

}
