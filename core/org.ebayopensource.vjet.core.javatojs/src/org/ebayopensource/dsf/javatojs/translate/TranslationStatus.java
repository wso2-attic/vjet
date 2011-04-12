/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;

import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class TranslationStatus {
	private static final int DEPENDENCY = 1;
	private static final int DECL_TRANSLATION = 2;
	private static final int IMPL_TRANSLATION = 4;
	
	private JstType m_jstType;
	private int m_status;
	private Map<ASTNode,TranslateError> m_errors;
	
	public TranslationStatus(final JstType jstType){
		m_jstType = jstType;
	}
	
	public TranslationStatus setDependencyDone(){
		m_status |= DEPENDENCY;
		return this;
	}
	
	public TranslationStatus setDeclTranlationDone(){
		m_status |= DECL_TRANSLATION;
		if (m_jstType != null){
			m_jstType.getStatus().setHasDecl();
		}
		return this;
	}
	
	public TranslationStatus setImplTranlationDone(){
		m_status |= IMPL_TRANSLATION;
		if (m_jstType != null){
			m_jstType.getStatus().setHasImpl();
		}
		return this;
	}
	
	public boolean isDependencyDone(){
		return (m_status & DEPENDENCY) == DEPENDENCY;
	}
	
	public boolean isDeclTranlationDone(){
		return (m_status & DECL_TRANSLATION) == DECL_TRANSLATION 
			|| isImplTranlationDone();
	}
	
	public boolean isImplTranlationDone(){
		return (m_status & IMPL_TRANSLATION) == IMPL_TRANSLATION;
	}
	
	public boolean hasError(){
		return m_errors != null && !m_errors.isEmpty();
	}
	
	public boolean hasError(ASTNode astNode){
		return m_errors != null && m_errors.containsKey(astNode);
	}
	
	public List<TranslateError> getErrors(){
		if (m_errors == null){
			return Collections.emptyList();
		}
		List<TranslateError> list = new ArrayList<TranslateError>();
		list.addAll(m_errors.values());
		return list;
	}
	
	//
	// Protected
	//
	void addError(ASTNode astNode, TranslateError error){
		getErrors(true).put(astNode, error);
		if (m_jstType != null){
			m_jstType.getStatus().setIsValid(false);
		}
	}
	
	//
	// Private
	//
	private Map<ASTNode,TranslateError> getErrors(boolean create){
		if (m_errors == null && create){
			m_errors = new HashMap<ASTNode,TranslateError>();
		}
		return m_errors;
	}
}
