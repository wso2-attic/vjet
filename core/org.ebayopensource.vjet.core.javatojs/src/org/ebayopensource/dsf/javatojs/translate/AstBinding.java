/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import org.ebayopensource.dsf.jst.JstSource.IBinding;
import org.ebayopensource.dsf.common.Z;

public class AstBinding implements IBinding {
	
	private URL m_pkgPath;
	private String m_pkgName;
	private String m_clsName;
	private ASTNode m_astNode;
	
	private Map<String,String> m_interfaces;
	
	//
	// Constructor
	//
	public AstBinding(URL pkgPath, String pkgName, final String clsName, final ASTNode astNode){
		assert pkgPath != null : "pkgPath cannot be null";
		assert pkgName != null : "pkgName cannot be null";
		assert clsName != null : "clsName cannot be null";
		assert astNode != null : "astNode cannot be null";
		m_pkgPath = pkgPath;
		m_pkgName = pkgName;
		m_clsName = clsName;
		m_astNode = astNode;
	}

	public AstBinding(final ASTNode astNode){
		assert astNode != null : "astNode cannot be null";
		m_astNode = astNode;
	}

	//
	// Satisfy IBinding
	//
	public String getName(){
		if (m_astNode instanceof CompilationUnit){
			return m_pkgName == null ? m_clsName : m_pkgName + "." + m_clsName;
		}
		else if (m_astNode instanceof TypeDeclaration){
			return ((TypeDeclaration)m_astNode).getName().toString();
		}
		return null;
	}
	
	public String toText(){
		return m_astNode.toString();
	}
	
	//
	// API
	//
	public URL getPkgPath(){
		return m_pkgPath;
	}
	
	public String getPkgName(){
		return m_pkgName;
	}
	
	public String getClassName(){
		return m_clsName;
	}
	
	public ASTNode getAstNode(){
		return m_astNode;
	}
	
	public synchronized void addInterfaceName(final String key, final String fullName){
		if (m_interfaces == null){
			m_interfaces = new LinkedHashMap<String,String>();
		}
		m_interfaces.put(key, fullName);
	}
	
	public synchronized Map<String,String> getInterfaceNames(){
		if (m_interfaces == null){
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(m_interfaces);
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		
		z.format("m_pkgPath", m_pkgPath);
		z.format("m_pkgName", m_pkgName);
		z.format("m_clsName", m_clsName);
		z.format("m_astNode", m_astNode.getClass().getName());
		
		return z.toString();
	}
}
