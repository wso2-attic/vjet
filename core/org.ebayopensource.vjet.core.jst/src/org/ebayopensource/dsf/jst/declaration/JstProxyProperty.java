/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * Proxy of another JstProperty. For example, a method of mixin in the type
 * 
 * 
 */
public class JstProxyProperty implements IJstProperty {
	
	private static final long serialVersionUID = 1L;
	private IJstProperty m_targetProp;

	public JstProxyProperty(IJstProperty targetProp){
		if (targetProp == null){
			throw new AssertionError("target cannot be null");
		}
		m_targetProp = targetProp;
	}

	public IJstProperty getTargetProperty() {
		return m_targetProp;
	}

	public IJstDoc getDoc() {
		return m_targetProp.getDoc();
	}

	public IExpr getInitializer() {
		return m_targetProp.getInitializer();
	}

	public JstModifiers getModifiers() {
		return m_targetProp.getModifiers();
	}

	public JstName getName() {
		return m_targetProp.getName();
	}

	public IJstType getType() {
		return m_targetProp.getType();
	}

	public IJstTypeReference getTypeRef() {
		return m_targetProp.getTypeRef();
	}

	public ISimpleTerm getValue() {
		return m_targetProp.getValue();
	}

	public boolean isFinal() {
		return m_targetProp.isFinal();
	}

	public boolean isInternal() {
		return m_targetProp.isInternal();
	}

	public boolean isPrivate() {
		return m_targetProp.isPrivate();
	}

	public boolean isProtected() {
		return m_targetProp.isProtected();
	}

	public boolean isPublic() {
		return m_targetProp.isPublic();
	}

	public boolean isStatic() {
		return m_targetProp.isStatic();
	}

	public IJstAnnotation getAnnotation(String name) {
		return m_targetProp.getAnnotation(name);
	}

	public List<IJstAnnotation> getAnnotations() {
		return m_targetProp.getAnnotations();
	}

	public List<? extends IJstNode> getChildren() {
		return m_targetProp.getChildren();
	}

	public List<String> getComments() {
		return m_targetProp.getComments();
	}

	public IJstType getOwnerType() {
		return m_targetProp.getOwnerType();
	}

	public IJstNode getParentNode() {
		return m_targetProp.getParentNode();
	}

	public IJstNode getRootNode() {
		return m_targetProp.getRootNode();
	}

	public IJstType getRootType() {
		return m_targetProp.getRootType();
	}

	public JstSource getSource() {
		return m_targetProp.getSource();
	}

	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}
