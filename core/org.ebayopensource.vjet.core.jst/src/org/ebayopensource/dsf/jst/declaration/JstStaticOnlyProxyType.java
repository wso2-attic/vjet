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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

/**
 * This proxy type hides all instance properties and methods.
 */
public class JstStaticOnlyProxyType extends JstProxyType {

	private static final long serialVersionUID = 1L;
	
	public JstStaticOnlyProxyType(IJstType targetType) {
		super(targetType);
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
		return;
	}

	@Override
	public IJstProperty getProperty(final String name) {
		return getStaticProperty(name);
	}

	@Override
	public IJstProperty getProperty(final String name, boolean isStatic) {
		if (!isStatic) {
			return null;
		}
		return getStaticProperty(name);
	}

	@Override
	public IJstProperty getProperty(final String name, boolean isStatic, boolean recursive) {
		if (!isStatic) {
			return null;
		}
		return getStaticProperty(name, recursive);
	}
	
	@Override
	public List< IJstProperty> getProperties(){
		return getStaticProperties();
	}

	@Override
	public List< IJstProperty> getProperties(boolean isStatic) {
		if (!isStatic) {
			return new ArrayList<IJstProperty>();
		}
		return getStaticProperties();
	}

	@Override
	public List< IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive) {
		if (!isStatic) {
		   return new ArrayList<IJstProperty>();
		}
		return super.getAllPossibleProperties(true, recursive);
	}
	
	@Override
	public boolean hasInstanceProperties() {
		return false;
	}

	@Override
	public IJstProperty getInstanceProperty(final String name) {
		return null;
	}
	
	@Override
	public IJstProperty getInstanceProperty(final String name, boolean recursive) {
		return null;
	}

	@Override
	public List< IJstProperty> getInstanceProperties() {
		return null;
	}
	
	@Override
	public IJstMethod getConstructor() {
		return null;
	}

	@Override
	public IJstMethod getMethod(final String name) {
		return getStaticMethod(name);
	}

	@Override
	public IJstMethod getMethod(final String name, boolean isStatic) {
		if (!isStatic) {
			return null;
		}
		return getStaticMethod(name);
	}

	@Override
	public IJstMethod getMethod(final String name, boolean isStatic, boolean recursive) {
		if (!isStatic) {
			return null;
		}
		return getStaticMethod(name, recursive);
	}
	
	@Override
	public List<? extends IJstMethod> getMethods(){
		return getStaticMethods();
	}

	@Override
	public List<? extends IJstMethod> getMethods(boolean isStatic) {
		if (!isStatic) {
			return new ArrayList<IJstMethod>();
		}
		return getStaticMethods();
	}

	@Override
	public List<? extends IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		if (!isStatic) {
			return new ArrayList<IJstMethod>();
		}
		return super.getMethods(true, recursive);
	}

	@Override
	public boolean hasInstanceMethods() {
		return false;
	}
	
	@Override
	public boolean hasInstanceMethod(String mtdName, boolean recursive) {
		return false;
	}

	@Override
	public List<? extends IJstMethod> getInstanceMethods() {
		return null;
	}
	
	@Override
	public IJstMethod getInstanceMethod(final String name) {
		return null;
	}

	@Override
	public IJstMethod getInstanceMethod(final String name, boolean recursive) {
		return null;
	}

}
