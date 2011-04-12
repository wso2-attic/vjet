/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.ebayopensource.dsf.jsnative.Plugin;
import org.ebayopensource.dsf.jsnative.PluginArray;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class APluginArray extends ActiveObject implements PluginArray {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<APlugin> m_plugins = new ArrayList<APlugin>();
	
	/**
	 * Constructor
	 */
	public APluginArray() {
		populateScriptable(APluginArray.class, BrowserType.IE_6P);
	}
	
	//
	// Satisfy PluginArray
	//
	/**
	 * @see PluginArray#getLength()
	 */
	public int getLength() {
		return m_plugins.size() ;
	}
	
	/**
	 * @see PluginArray#item(int)
	 */
	public Plugin item(int index) {
		return get(index) ;
	}
	
	/**
	 * @see PluginArray#namedItem(String)
	 */
	public Plugin namedItem(String name){
		return get(name);
	}
	
	/**
	 * @see PluginArray#valueOf(String)
	 */
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
	
	/**
	 * @see PluginArray#get(String)
	 */
	public Plugin get(String name) {
		if (name == null){
			return null;
		}
		for (APlugin p: m_plugins){
			if (p == null){
				continue;
			}
			if (name.equals(p.getName())){
				return p;
			}
		}
		return null;
	}
	
	//
	// API
	//
	public APlugin get(int index) {
		if (index < 0 || index >= m_plugins.size()) {
			return null;
		}
		return m_plugins.get(index);
	}
	
	public Iterator<APlugin> iterator() {
		return m_plugins.iterator();
	}
	
	public Object get(int index, Scriptable start) {
	   Object obj = super.get(index, start);
	   if (obj != NOT_FOUND) {
			return obj;
	   }
	   if (m_plugins == null) {
		   return NOT_FOUND;
	   }
		   
	   obj = item(index);
	   if (obj != null) {
		   return obj;
	   }
	   return NOT_FOUND;
    }
	
	public Object get(String name, Scriptable start) {
		Object obj = get(name);
		if (obj != null){
			return obj;
		}

		return NOT_FOUND;
	}
   
	public boolean add(APlugin plugin) {
		return m_plugins.add(plugin) ;
	}
	
	void remove(APlugin plugin) {
		m_plugins.remove(plugin) ;
	}
	
	void remove(int index) {
		m_plugins.remove(index);
	}
	
	public boolean isEmpty() {
		return m_plugins.isEmpty();
	}

	int indexOf(APlugin plugin) {
		return m_plugins.indexOf(plugin);
	}

	public int size() {
		return m_plugins.size();
	}

	void add(int index, APlugin plugin) {
		m_plugins.add(index, plugin);
	}

	public boolean addAll(Collection<? extends APlugin> c) {
		return m_plugins.addAll(c);
	}

	public void clear() {
		m_plugins.clear();
	}
}