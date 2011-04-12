/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;


/**
 * Data object used to transfer proposal infomation
 * 
 * 
 * 
 */
public interface IVjoCcProposalData {

	/**
	 * Get the Data will be shown as proposal
	 * 
	 * @return
	 */
	Object getData();

	/**
	 * VjoCcProposal data's type
	 * 
	 * @return
	 */
	public String getAdvisor();
	
	public String getName();
	
	/**
	 * @return the data is match to the token accurately.
	 */
	public boolean isAccurateMatch();
	
	/**
	 * 
	 * @param match
	 * if true: the data is match to the token accurately.
	 */
	public void setAccurateMatch(boolean match);
}
