/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstGlobalProp;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstGlobalProp extends BaseJstNode implements IJstGlobalProp {


	private IJstProperty m_prop;

	public JstGlobalProp(IJstProperty prop) {
		m_prop = prop;
		addChild(prop);
	}

	public void setProperty(IJstProperty prop) {
		m_prop = prop;
		addChild(prop);
	}
	
	@Override
	public IJstDoc getDoc() {
		return m_prop.getDoc();
	}

	@Override
	public IExpr getInitializer() {
		return m_prop.getInitializer();
	}

	@Override
	public JstModifiers getModifiers() {
		return m_prop.getModifiers();
	}

	@Override
	public JstName getName() {
		return m_prop.getName();
	}

	@Override
	public IJstType getType() {
		return m_prop.getType();
		
	}

	@Override
	public IJstTypeReference getTypeRef() {
		return m_prop.getTypeRef();
	}

	@Override
	public ISimpleTerm getValue() {
		return m_prop.getValue();
	}

	@Override
	public boolean isFinal() {
		return m_prop.isFinal();
	}

	@Override
	public boolean isInternal() {
		return false;
	}

	@Override
	public boolean isPrivate() {
		return false;
	}

	@Override
	public boolean isProtected() {
		return false;
	}

	@Override
	public boolean isPublic() {
		return true;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		m_prop.accept(visitor);
		
	}
	
	@Override
	public JstSource getSource() {
		return m_prop.getSource();
	}



}