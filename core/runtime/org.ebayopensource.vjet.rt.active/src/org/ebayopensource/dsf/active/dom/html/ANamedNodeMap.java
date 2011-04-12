/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.jsnative.NamedNodeMap;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class ANamedNodeMap extends ActiveObject 
	implements NamedNodeMap, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, ANode> m_nodes = new LinkedHashMap<String, ANode>();
	private short m_nodeType;
	
	public ANamedNodeMap(short nodeType) {
		m_nodeType = nodeType;
		populateScriptable(ANamedNodeMap.class, BrowserType.IE_6P);
	}

	public int getLength() {
		return m_nodes.size();
	}

	public Node getNamedItem(String name) {
		return m_nodes.get(name);
	}

	public Node getNamedItemNS(String namespaceURI, String localName) {
		throw new ADOMException(
				new DOMException(DOMException.NOT_SUPPORTED_ERR, 
						"getNamedItemNS not supported"));
	}

	public Node item(int index) {
		if (index < 0 || index > getLength() - 1) {
			return null ;
		}
		
		final Iterator<Map.Entry<String, ANode>> entrySet 
			= m_nodes.entrySet().iterator() ;
		int count = 0 ;
		while(entrySet.hasNext()) {
			Map.Entry<String, ANode> mapEntry = entrySet.next() ;
			if (count == index) {
				return mapEntry.getValue() ;
			}else{
                             count = count+1;
                        }
		}
		
		return null;
	}

	public Node removeNamedItem(String name) {
		if (!m_nodes.containsKey(name)) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR, 
					"node not found - "+name));
		}
		return m_nodes.remove(name);
	}

	public Node removeNamedItemNS(String namespaceURI, String localName) {
		throw new ADOMException(
				new DOMException(DOMException.NOT_SUPPORTED_ERR, 
						"removeNamedItemNS not supported"));
	}

	public Node setNamedItem(Node node) {
		if (node == null) {
			throw new ADOMException(new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
				"null node not allowed"));
		}
		if (node.getNodeType() != m_nodeType) {
			throw new ADOMException(new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
				"node of type " + node.getNodeType() + "not allowed"));
		}
		return m_nodes.put(node.getNodeName(), (ANode)node);
	}

	public Node setNamedItemNS(Node arg) {
		throw new ADOMException(
				new DOMException(DOMException.NOT_SUPPORTED_ERR, 
						"setNamedItemNS not supported"));
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

		obj = getNamedItem(name);
		if (obj != null) {
			return obj;
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

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return cloneMap();
	}
	
	ANamedNodeMap cloneMap() {
		ANamedNodeMap copy = new ANamedNodeMap(m_nodeType);
		for (String key : m_nodes.keySet()) {
			ANode node = m_nodes.get(key);
			ANode newNode = (ANode) node.cloneNode(true);
			copy.m_nodes.put(key, newNode);
		}
		
		return copy;
	}
	
}
