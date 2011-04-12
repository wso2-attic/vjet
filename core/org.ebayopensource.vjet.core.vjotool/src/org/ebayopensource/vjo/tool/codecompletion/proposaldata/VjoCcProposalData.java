/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata;

import org.ebayopensource.dsf.jst.IJstGlobalFunc;
import org.ebayopensource.dsf.jst.IJstGlobalProp;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcProposalData extends AbstractVjoCcProposalData {
	
	private IJstNode m_node;
	private VjoCcCtx m_ctx;
	private String m_advisor;

	public VjoCcProposalData(IJstNode jstNode, VjoCcCtx ctx, String advisor) {
		this.m_node = jstNode;
		this.m_ctx = ctx;
		this.m_advisor = advisor;
	}
	public VjoCcProposalData(IJstNode jstNode, VjoCcCtx ctx, String advisor, int proposalType) {
		this.m_node = jstNode;
		this.m_ctx = ctx;
		this.m_advisor = advisor;
	}


	public IJstNode getData() {
		return m_node;
	}


	public String getAdvisor() {
		return m_advisor;
	}
	
	public VjoCcCtx getContext() {
		return m_ctx;
	}


	public String getName() {
		if (m_node instanceof IJstMethod) {
			return ((IJstMethod)m_node).getName().getName();
		} else if (m_node instanceof IJstProperty) {
			return ((IJstProperty)m_node).getName().getName();
		} else if (m_node instanceof JstArg) {
			return ((JstArg)m_node).getName();
		} else if (m_node instanceof JstVar) {
			return ((JstVar)m_node).getName();
		} else if (m_node instanceof IJstGlobalProp) {
			return ((IJstGlobalProp)m_node).getName().getName();
		} else if (m_node instanceof IJstGlobalFunc) {
			return ((IJstGlobalFunc)m_node).getName().getName();
		} else {
			return m_node.toString();
		}
	}
	
	public String toString() {
		if (m_node instanceof IJstMethod) {
			return "JstMethod: " + ((IJstMethod)m_node).getName().getName();
		} else if (m_node instanceof IJstProperty) {
			return "JstProperty: " + ((IJstProperty)m_node).getName().getName();
		} else if (m_node instanceof JstArg) {
			return "JstArg: " + ((JstArg)m_node).getName();
		} else if (m_node instanceof JstVar) {
			return "JstVar: " + ((JstVar)m_node).getName();
		} else {
			return m_node.toString();
		}
	}

}
