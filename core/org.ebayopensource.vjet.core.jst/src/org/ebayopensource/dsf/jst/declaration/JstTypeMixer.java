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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public abstract class JstTypeMixer extends JstType {
	private static final long serialVersionUID = 1L;
	
	protected List<IJstType> m_types;
	
	protected JstTypeMixer(String name) {
		super(name);
	}
	
	@Override
	public List<IJstProperty> getAllPossibleProperties(boolean isStatic, boolean recursive) {
		if (isStatic) {
			return EMPTY_PROP_LIST;
		};
		List<IJstProperty> allPtys = new ArrayList<IJstProperty>();
		Set<String> names = new HashSet<String>();
		for (IJstType type : m_types) {
			List<? extends IJstProperty> ptys = null;
			if (type instanceof IJstRefType) {
				ptys = type.getAllPossibleProperties(true, recursive);
			}
			else {
				ptys = type.getAllPossibleProperties(false, recursive);
			}
			if (ptys != null) {
				for (IJstProperty pty : ptys) {
					String ptyName = pty.getName().getName();
					if (!names.contains(ptyName)) {
						names.add(ptyName);
						allPtys.add(pty);
					}					
				}
			}
		}
		return allPtys;
	}

	
	@Override
	public java.util.List<IJstType> getExtends() {
		List<IJstType> _extends = new ArrayList<IJstType>();
		
		for (IJstType type : m_types) {
			_extends.addAll(type.getExtends());
		}
		
		return _extends;
	};
	@Override
	public List<IJstTypeReference> getExtendsRef() {
		
		List<IJstTypeReference> _extends = new ArrayList<IJstTypeReference>();
		
		for (IJstType type : m_types) {
			_extends.addAll(type.getExtendsRef());
		}
		
		return _extends;
	};
	
	@Override
	public List<IJstType> getAllDerivedTypes(){
		List<IJstType> types = new ArrayList<IJstType>();
		
		for (IJstType type : m_types) {
			
			for(IJstType mixin: getMixins()){
				if(mixin instanceof JstProxyType){
					JstProxyType pt = (JstProxyType)mixin;
					types.add(pt.getType());
				}else if (mixin instanceof JstType){
					types.add(mixin);
				}
					
				
				//types.addAll(getMixins());
			}
			types.addAll(type.getExtends());
			types.addAll(type.getMixins());
		
		}
		return types;
	}
	
	@Override
	public IJstMethod getInstanceMethod(String name) {
		return getInstanceMethod(name, false);
	}

	@Override
	public IJstMethod getInstanceMethod(String name, boolean recursive) {
		IJstMethod mtd = null;
		for (IJstType type : m_types) {
			if (type instanceof IJstRefType) {
				mtd = type.getStaticMethod(name, recursive);
			}
			else {
				mtd = type.getInstanceMethod(name, recursive);
			}
			if (mtd != null) {
				return mtd;
			}
		}
		return null;
	}

	@Override
	public List<IJstMethod> getInstanceMethods() {
		return getMethods(false, false);
	}

	@Override
	public List<IJstProperty> getInstanceProperties() {
		return getAllPossibleProperties(false, false);
	}

	@Override
	public IJstProperty getInstanceProperty(String name) {
		return getInstanceProperty(name, false);
	}

	@Override
	public IJstProperty getInstanceProperty(String name, boolean recursive) {
		IJstProperty pty = null;
		for (IJstType type : m_types) {
			if (type instanceof IJstRefType) {
				pty = type.getStaticProperty(name, recursive);
			}
			else {
				pty = type.getInstanceProperty(name, recursive);
			}
			if (pty != null) {
				return pty;
			}
		}
		return null;
	}

	@Override
	public IJstMethod getMethod(String name) {
		return getInstanceMethod(name);
	}

	@Override
	public IJstMethod getMethod(String name, boolean isStatic) {
		if (isStatic) {
			return null;
		};
		return getInstanceMethod(name);
	}

	@Override
	public IJstMethod getMethod(String name, boolean isStatic, boolean recursive) {
		if (isStatic) {
			return null;
		};
		return getInstanceMethod(name, recursive);
	}

	@Override
	public List<IJstMethod> getMethods() {
		return getInstanceMethods();
	}

	@Override
	public List<IJstMethod> getMethods(boolean isStatic) {
		if (isStatic) {
			return EMPTY_MTD_LIST;
		};
		return getInstanceMethods();
	}

	@Override
	public List<IJstMethod> getMethods(boolean isStatic, boolean recursive) {
		if (isStatic) {
			return EMPTY_MTD_LIST;
		};
		List<IJstMethod> allMtds = new ArrayList<IJstMethod>();
		Set<String> names = new HashSet<String>();
		for (IJstType type : m_types) {
			List<? extends IJstMethod> mtds = null;
			if (type instanceof IJstRefType) {
				mtds = type.getMethods(true, recursive);
			}
			else {
				mtds = type.getMethods(false, recursive);
			}
			if (mtds != null) {
				for (IJstMethod method : mtds) {
					String mtdName = method.getName().getName();
					if (!names.contains(mtdName)) {
						names.add(mtdName);
						allMtds.add(method);
					}					
				}
			}
		}
		return allMtds;
	}

	@Override
	public List<IJstProperty> getProperties() {
		return getInstanceProperties();
	}

	@Override
	public List<IJstProperty> getProperties(boolean isStatic) {
		if (isStatic) {
			return EMPTY_PROP_LIST;
		};
		return getInstanceProperties();
	}

	@Override
	public IJstProperty getProperty(String name) {
		return getInstanceProperty(name);
	}

	@Override
	public IJstProperty getProperty(String name, boolean isStatic) {
		if (isStatic) {
			return null;
		}
		return getInstanceProperty(name);
	}

	@Override
	public IJstProperty getProperty(String name, boolean isStatic, boolean recursive) {
		if (isStatic) {
			return null;
		}
		return getInstanceProperty(name, recursive);
	}

	@Override
	public IJstMethod getStaticMethod(String name) {
		return null;
	}

	@Override
	public IJstMethod getStaticMethod(String name, boolean recursive) {
		return null;
	}

	@Override
	public List<IJstMethod> getStaticMethods() {
		return EMPTY_MTD_LIST;
	}

	@Override
	public List<IJstProperty> getStaticProperties() {
		return EMPTY_PROP_LIST;
	}

	@Override
	public IJstProperty getStaticProperty(String name) {
		return null;
	}

	@Override
	public IJstProperty getStaticProperty(String name, boolean recursive) {
		return null;
	}

	@Override
	public boolean hasInstanceMethod(String mtdName, boolean recursive) {
		return getInstanceMethod(mtdName, recursive) != null;
	}

	@Override
	public boolean hasInstanceMethods() {
		return true;
	}

	@Override
	public boolean hasInstanceProperties() {
		return true;
	}

	@Override
	public boolean hasInstanceProperty(String name, boolean recursive) {
		return getInstanceProperty(name, recursive) != null;
	}

	@Override
	public boolean hasStaticMethod(String mtdName, boolean recursive) {
		return false;
	}

	@Override
	public boolean hasStaticMethods() {
		return false;
	}

	@Override
	public boolean hasStaticProperties() {
		return false;
	}

	@Override
	public boolean hasStaticProperty(String name, boolean recursive) {
		return false;
	}

	@Override
	public void accept(IJstNodeVisitor visitor) {
	}	
	
	private static final List<IJstMethod> EMPTY_MTD_LIST = new ArrayList<IJstMethod>(0);
	private static final List<IJstProperty> EMPTY_PROP_LIST = new ArrayList<IJstProperty>(0);	
}