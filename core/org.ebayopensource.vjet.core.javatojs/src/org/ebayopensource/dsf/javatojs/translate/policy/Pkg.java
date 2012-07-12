/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.policy;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.Z;

public class Pkg {
	
	private String m_name;
	private List<String> m_exemptedClz = new ArrayList<String>();
	private List<String> m_exemptedPkgs = new ArrayList<String>();
	
	//
	// Constructor
	//
	public Pkg(String name){
		assert name != null : "pkg name cannot be null";
		m_name = (name.endsWith("*")?name: name+".");			
	}
	
	public Pkg(Class<?> anchorCls){
		this(anchorCls,false);
	}
	
	public Pkg(Class<?> anchorCls, boolean recursive){
		assert anchorCls != null : "pkg name cannot be null";
		String pkgName = anchorCls.getPackage().getName();
		if(recursive){
			pkgName = pkgName +".*";
		}		
		m_name = pkgName;	
	}
	
	//
	// API
	//
	public boolean containsClass(String clsName){
		if (clsName == null){
			return false;
		}
		
		// Missing pkg or name
		int index = clsName.lastIndexOf(".");
		if (index < 1 || clsName.length() <= index+1){
			return false;
		}
		
		// Exempted class
		for(String exemptedClz: m_exemptedClz){
			if(matches(exemptedClz,clsName)){
				return false;
			}
		}
		String pkgName = clsName.substring(0, index+1);
		// Exempted pkg
		for(String exemptedPkg: m_exemptedPkgs){
			if(matches(exemptedPkg,pkgName)){
				return false;
			}			
		}	
		return matches(m_name,pkgName);		
	}
	
	public Pkg addExemptedClass(final Class cls){
		if (cls == null){
			return this;
		}
		return addExemptedClass(cls.getName());
	}
	
	public Pkg addExemptedClass(final String clsName){
		if (clsName == null){
			return this;
		}
		if (!m_exemptedClz.contains(clsName)){
			m_exemptedClz.add(clsName);
		}
		return this;
	}
	
	public Pkg addExemptedPkg(String pkgName){
		pkgName = (pkgName.endsWith("*")?pkgName: pkgName+".");		
		if (!m_exemptedPkgs.contains(pkgName)){
			m_exemptedPkgs.add(pkgName);
		}
		return this;
	}

	public String toString() {
		Z z = new Z();
		z.format("m_name", m_name);		
		return z.toString();
	}
	
	static boolean matches(String pattern, String text) {
	      // add sentinel so don't need to worry about *'s at end of pattern
	      text    += '\0';
	      pattern += '\0';

	      int N = pattern.length();

	      boolean[] states = new boolean[N+1];
	      boolean[] old = new boolean[N+1];
	      old[0] = true;

	      for (int i = 0; i < text.length(); i++) {
	         char c = text.charAt(i);
	         states = new boolean[N+1];       // initialized to false
	         for (int j = 0; j < N; j++) {
	            char p = pattern.charAt(j);

	            // hack to handle *'s that match 0 characters
	            if (old[j] && (p == '*')) old[j+1] = true;
	            if (old[j] && (p ==  c )) states[j+1] = true;
	           // if (old[j] && (p == '.')) states[j+1] = true;
	            if (old[j] && (p == '*')) states[j]   = true;
	            if (old[j] && (p == '*')) states[j+1] = true;
	         }
	         old = states;
	      }
	      return states[N];
	   }
}
