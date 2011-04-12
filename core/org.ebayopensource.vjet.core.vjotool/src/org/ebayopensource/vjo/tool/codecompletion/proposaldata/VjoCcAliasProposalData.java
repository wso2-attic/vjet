/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcAliasProposalData extends AbstractVjoCcProposalData {

	private VjoCcCtx m_ctx;
	private String m_advisor;
	private String m_alias;
	private IJstType m_type;

	public VjoCcAliasProposalData(String alias, IJstType type, VjoCcCtx ctx,
			String advisor) {
		this.m_type = type;
		this.m_alias = alias;
		this.m_ctx = ctx;
		this.m_advisor = advisor;
	}

	public String getData() {
		return m_alias;
	}

	public IJstType getType() {
		return m_type;
	}

	public String getAlias() {
		return m_alias;
	}

	public String getAdvisor() {
		return m_advisor;
	}

	public VjoCcCtx getContext() {
		return m_ctx;
	}

	public String getName() {
		return m_alias;
	}

	public String toString() {
		return m_alias + " : " + m_type.getName();
	}

}
