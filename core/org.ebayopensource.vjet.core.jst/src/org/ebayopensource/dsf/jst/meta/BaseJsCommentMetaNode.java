/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * TODO move to DSFJst, otherwise @see {@link IJstNodeVisitor} won't be able to access this node type correctly
 * 
 *
 */
public class BaseJsCommentMetaNode<M> extends BaseJstNode implements IExpr{

	private static final long serialVersionUID = 1L;
	
	transient private List<M> m_jsCommentMetaList;
	
	private IJstType m_metaResolvedType;
	
	private IJstNode m_metaJstBinding;
	
	public void setJsCommentMetas(final M meta) {
		final List<M> jsCommentMetas = new ArrayList<M>(1);
		jsCommentMetas.add(meta);
		setJsCommentMetas(jsCommentMetas);
	}

	public void setJsCommentMetas(final M[] metaArr) {
		setJsCommentMetas(Arrays.asList(metaArr));
	}

	public void setJsCommentMetas(final List<M> jsCommentMetaList) {
		m_jsCommentMetaList = new ArrayList<M>(jsCommentMetaList);
	}
	
	public List<M> getJsCommentMetas() {
		if(m_jsCommentMetaList == null){
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableList(m_jsCommentMetaList);
	}
	
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}

	@Override
	public IJstType getResultType() {
		return m_metaResolvedType;
	}
	
	public void setResultType(final IJstType metaResolvedType){
		m_metaResolvedType = metaResolvedType;
	}
	
	public IJstNode getJstBinding() {
		return m_metaJstBinding;
	}

	public void setJstBinding(final IJstNode jstBinding){
		m_metaJstBinding = jstBinding;
	}
	
	@Override
	public String toExprText() {
		return m_jsCommentMetaList.toString();
	}
}
