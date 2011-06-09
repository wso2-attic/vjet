/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class ANodeList extends ActiveObject implements NodeList, Collection<ANode>  {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ANode> m_nodes = new ArrayList<ANode>();
	
	public ANodeList() {
		populateScriptable(ANodeList.class, BrowserType.IE_6P);
	}
	
	public Node item(final int index) {
		if (index >= m_nodes.size()) {
			return null;
		}
		return m_nodes.get(index) ;
	}
	
	public int getLength() {
		return m_nodes.size() ;
	}
	
	public Iterator<ANode> iterator() {
		return m_nodes.iterator();
	}
	
   public Object get(int index, Scriptable start) {
	   Object obj = super.get(index, start);
	   if (obj != NOT_FOUND) {
			return obj;
	   }
	   if (m_nodes == null) {
		   return NOT_FOUND;
	   }
		   
	   obj = item(index);
	   if (obj != null) {
		   return obj;
	   }
	   return NOT_FOUND;
    }
	
   public Object get(String name, Scriptable start) {
		Object obj = super.get(name, start);
		if (obj != NOT_FOUND) {
			return obj;
		}
		if (m_nodes == null) {
			return NOT_FOUND;
		}

		for (int i = 0; i < getLength(); i++) {
			if (item(i) instanceof AElement) {
				AElement elem = (AElement) item(i);
				if (name.equals(elem.getAttribute("id"))) {
					return elem;
				}
				if (name.equals(elem.getAttribute("name"))) {
					return elem;
				}
			}
		}

		return NOT_FOUND;
	}
   
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
	
	public boolean add(ANode child) {
		return m_nodes.add(child) ;
	}
	
	void remove(ANode child) {
		m_nodes.remove(child) ;
	}
	
	void remove(int index) {
		m_nodes.remove(index);
	}
	
	public boolean isEmpty() {
		return m_nodes.isEmpty();
	}

	ANode get(int index) {
		return m_nodes.get(index);
	}

	int indexOf(ANode node) {
		return m_nodes.indexOf(node);
	}

	public int size() {
		return m_nodes.size();
	}

	void add(int index, ANode newChild) {
		m_nodes.add(index, newChild);
	}

	public boolean addAll(Collection<? extends ANode> c) {
		return m_nodes.addAll(c);
	}

	public void clear() {
		m_nodes.clear();
	}

	public boolean contains(Object o) {
		return m_nodes.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return m_nodes.containsAll(c);
	}

	public boolean remove(Object o) {
		return m_nodes.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return m_nodes.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return m_nodes.retainAll(c);
	}

	public Object[] toArray() {
		return m_nodes.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return m_nodes.toArray(a);
	}

	@Override
	public NodeList tags(String sTag) {
		// TODO Auto-generated method stub
		return null;
	}
}
