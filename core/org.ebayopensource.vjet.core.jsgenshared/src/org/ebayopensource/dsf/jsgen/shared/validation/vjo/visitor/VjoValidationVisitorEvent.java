/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jst.IJstNode;

public class VjoValidationVisitorEvent implements IVjoValidationVisitorEvent {

	private VjoValidationCtx m_validationCtx;
	private IJstNode m_visitNode;
	private IJstNode m_visitChildNode;
	private VjoValidationVisitorState m_visitState;
	
	public VjoValidationVisitorEvent(){
		
	}
	
	public VjoValidationVisitorEvent(final VjoValidationCtx ctx,
			final IJstNode visitNode,
			final IJstNode visitChildNode,
			final VjoValidationVisitorState visitState){
		m_validationCtx = ctx;
		m_visitNode = visitNode;
		m_visitChildNode = visitChildNode;
		m_visitState = visitState;
	}
	
	public VjoValidationCtx getValidationCtx() {
		return m_validationCtx;
	}
	
	public VjoValidationVisitorEvent setValidationCtx(final VjoValidationCtx ctx){
		m_validationCtx = ctx;
		return this;
	}

	public IJstNode getVisitChildNode() {
		return m_visitChildNode;
	}
	
	public VjoValidationVisitorEvent setVisitChildNode(final IJstNode childNode){
		m_visitChildNode = childNode;
		return this;
	}

	public IJstNode getVisitNode() {
		return m_visitNode;
	}
	
	public VjoValidationVisitorEvent setVisitNode(final IJstNode visitNode){
		m_visitNode = visitNode;
		return this;
	}

	public VjoValidationVisitorState getVisitState() {
		return m_visitState;
	}
	
	public VjoValidationVisitorEvent setVisitState(final VjoValidationVisitorState state){
		m_visitState = state;
		return this;
	}

}
