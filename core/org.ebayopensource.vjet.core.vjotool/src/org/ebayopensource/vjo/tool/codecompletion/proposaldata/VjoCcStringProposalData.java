/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata;

import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcStringProposalData extends AbstractVjoCcProposalData{

	private String m_node;
	private VjoCcCtx m_ctx;
	private String m_advisor;

	public VjoCcStringProposalData(String str, VjoCcCtx ctx, String advisor) {
		this.m_node = str;
		this.m_ctx = ctx;
		this.m_advisor = advisor;
	}

	public String getData() {
		return m_node;
	}

	public String getAdvisor() {
		return m_advisor;
	}

	public VjoCcCtx getContext() {
		return m_ctx;
	}

	public String getName() {
		return m_node;
	}

	public String toString() {
		return m_node;
	}

}
