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
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * Proxy of another JstMethod. For example, a method of mixin in the type
 * 
 * 
 */
public class JstProxyMethod implements IJstMethod {
	private static final long serialVersionUID = 1L;
	private IJstMethod m_targetMethod;
	
	public JstProxyMethod(IJstMethod targetType){
		if (targetType == null){
			throw new AssertionError("target cannot be null");
		}
		m_targetMethod = targetType;
	}

	//
	// API
	//
	public IJstMethod getTargetMethod(){
		return m_targetMethod;
	}
	
	public List<JstArg> getArgs() {
		return m_targetMethod.getArgs();
	}
	
	public boolean isVarArgs() {
		return m_targetMethod.isVarArgs();
	}

	public JstBlock getBlock() {
		return m_targetMethod.getBlock();
	}

	public IJstDoc getDoc() {
		return m_targetMethod.getDoc();
	}

	public JstModifiers getModifiers() {
		return m_targetMethod.getModifiers();
	}

	public JstName getName() {
		return m_targetMethod.getName();
	}

	public String getOriginalName() {
		return m_targetMethod.getOriginalName();
	}

	public List<IJstMethod> getOverloaded() {
		return m_targetMethod.getOverloaded();
	}

	public List<String> getParamNames() {
		return m_targetMethod.getParamNames();
	}

	public List<JstParamType> getParamTypes() {
		return m_targetMethod.getParamTypes();
	}

	public String getParamsDecoration() {
		return m_targetMethod.getParamsDecoration();
	}

	public IJstType getRtnType() {
		return m_targetMethod.getRtnType();
	}

	public IJstTypeReference getRtnTypeRef() {
		return m_targetMethod.getRtnTypeRef();
	}

	public boolean isAbstract() {
		return m_targetMethod.isAbstract();
	}

	public boolean isConstructor() {
		return m_targetMethod.isConstructor();
	}

	public boolean isDispatcher() {
		return m_targetMethod.isDispatcher();
	}

	public boolean isDuplicate() {
		return m_targetMethod.isDuplicate();
	}

	public boolean isFinal() {
		return m_targetMethod.isFinal();
	}

	public boolean isInternal() {
		return m_targetMethod.isInternal();
	}

	public boolean isParamName(String name) {
		return m_targetMethod.isParamName(name);
	}

	public boolean isPrivate() {
		return m_targetMethod.isPrivate();
	}

	public boolean isProtected() {
		return m_targetMethod.isProtected();
	}

	public boolean isPublic() {
		return m_targetMethod.isPublic();
	}

	public boolean isStatic() {
		return m_targetMethod.isStatic();
	}
	
	public boolean isTypeFactoryEnabled() {
		return m_targetMethod.isTypeFactoryEnabled();
	}
	
	public boolean isFuncArgMetaExtensionEnabled() {
		return m_targetMethod.isFuncArgMetaExtensionEnabled();
	}

	public IJstAnnotation getAnnotation(String name) {
		return m_targetMethod.getAnnotation(name);
	}

	public List<IJstAnnotation> getAnnotations() {
		return m_targetMethod.getAnnotations();
	}

	public List<? extends IJstNode> getChildren() {
		return m_targetMethod.getChildren();
	}

	public List<String> getComments() {
		return m_targetMethod.getComments();
	}

	public IJstType getOwnerType() {
		return m_targetMethod.getOwnerType();
	}

	public IJstNode getParentNode() {
		return m_targetMethod.getParentNode();
	}

	public IJstNode getRootNode() {
		return m_targetMethod.getRootNode();
	}

	public IJstType getRootType() {
		return m_targetMethod.getRootType();
	}

	public JstSource getSource() {
		return m_targetMethod.getSource();
	}
	
	public boolean isOType() {
		return m_targetMethod.isOType();
	}

	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}

	@Override
	public boolean hasJsAnnotation() {
		return m_targetMethod.hasJsAnnotation();
	}

	@Override
	public boolean isReturnTypeOptional() {
		return m_targetMethod.isReturnTypeOptional();
	}
}
