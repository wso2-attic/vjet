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

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstGlobalFunc;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstGlobalFunc extends BaseJstNode implements IJstGlobalFunc {

	private static final long serialVersionUID = 1L;
	private final IJstMethod m_method;
	
	public JstGlobalFunc(IJstMethod method) {
		m_method = method;
		addChild(method);
	}
	
	@Override
	public List<JstArg> getArgs() {
		return m_method.getArgs();
	}

	@Override
	public JstBlock getBlock() {
		return m_method.getBlock();
	}

	@Override
	public IJstDoc getDoc() {
		return m_method.getDoc();
	}

	@Override
	public JstModifiers getModifiers() {
		return m_method.getModifiers();
	}

	@Override
	public JstName getName() {
		return m_method.getName();
	}

	@Override
	public String getOriginalName() {
		return m_method.getOriginalName();
	}

	@Override
	public List<IJstMethod> getOverloaded() {
		// TODO Auto-generated method stub
		return m_method.getOverloaded();
	}

	@Override
	public List<String> getParamNames() {
		return m_method.getParamNames();
	}

	@Override
	public List<JstParamType> getParamTypes() {
		return m_method.getParamTypes();
	}

	@Override
	public String getParamsDecoration() {
		return m_method.getParamsDecoration();
	}

	@Override
	public IJstType getRtnType() {
		return m_method.getRtnType();
	}

	@Override
	public IJstTypeReference getRtnTypeRef() {
		return m_method.getRtnTypeRef();
	}

	@Override
	public boolean hasJsAnnotation() {
		return m_method.hasJsAnnotation();
	}

	@Override
	public boolean isAbstract() {
		return m_method.isAbstract();
	}

	@Override
	public boolean isConstructor() {
		return m_method.isConstructor();
	}

	@Override
	public boolean isDispatcher() {
		return m_method.isDispatcher();
	}

	@Override
	public boolean isDuplicate() {
		return m_method.isDuplicate();
	}

	@Override
	public boolean isFinal() {
		return m_method.isAbstract();
	}

	@Override
	public boolean isInternal() {
		return m_method.isInternal();
	}

	@Override
	public boolean isOType() {
		return m_method.isOType();
	}

	@Override
	public boolean isParamName(String name) {
		return m_method.isParamName(name);
	}

	@Override
	public boolean isPrivate() {
		return m_method.isPrivate();
	}

	@Override
	public boolean isProtected() {
		return m_method.isProtected();
	}

	@Override
	public boolean isPublic() {
		return m_method.isPublic();
	}

	@Override
	public boolean isStatic() {
		return m_method.isStatic();
	}

	@Override
	public boolean isVarArgs() {
		return m_method.isVarArgs();
	}
	
	@Override
	public boolean isTypeFactoryEnabled() {
		return m_method.isTypeFactoryEnabled();
	}
	
	@Override
	public boolean isFuncArgMetaExtensionEnabled() {
		return m_method.isFuncArgMetaExtensionEnabled();
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		m_method.accept(visitor);
		
	}

	@Override
	public JstSource getSource() {
		return m_method.getSource();
	}

	@Override
	public boolean isReturnTypeOptional() {
		return m_method.isReturnTypeOptional();
	}



}
