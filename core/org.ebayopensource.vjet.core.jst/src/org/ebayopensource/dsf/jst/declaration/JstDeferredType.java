/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * A JstProxyType inferring a given JstType
 */
public class JstDeferredType extends JstProxyType implements IInferred {

	private static final long serialVersionUID = 1L;

	private List<IJstType> m_candidateTypes;
	
	private IJstType m_resolvedType;
	
	public JstDeferredType(IJstType targetType) {
		super(targetType);
	}
	
	public List<IJstType> getCandidateTypes(){
		if(m_candidateTypes == null){
			return Collections.emptyList();
		}
		else{
			return Collections.unmodifiableList(m_candidateTypes);
		}
	}
	
	public void addCandidateType(final IJstType candidateType){
		if(m_candidateTypes == null){
			m_candidateTypes = new ArrayList<IJstType>(2);
		}
		m_candidateTypes.add(candidateType);
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}

	public void setResolvedType(IJstType m_resolvedType) {
		this.m_resolvedType = m_resolvedType;
	}

	public IJstType getResolvedType() {
		return m_resolvedType;
	}
	
	@Override
	public IJstType getType(){
		if(m_resolvedType != null){
			return m_resolvedType;
		}
		return super.getType();
	}
}
