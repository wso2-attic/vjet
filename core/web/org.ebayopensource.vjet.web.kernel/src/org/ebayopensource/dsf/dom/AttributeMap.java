/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.binding.IValueBinding;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.DsfInvalidNameException;
import org.ebayopensource.dsf.common.naming.NameStatusCheck;
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.dom.support.DsfDomNotSupportedRuntimeException;
import org.ebayopensource.dsf.common.Z;

/**
 * This class is basically a LinkedHashMap<String, Object> that is aware
 * of Value-Managed attributes.
 */
class AttributeMap implements IAttributeMap, NamedNodeMap, Serializable {
	private static final long serialVersionUID = 1171928675580312397L;

	private static final Object EMPTY[] = new Object[0];
	
	private final DNode /* IPropertyHolder */ m_propertySource;
	final LinkedHashMap<String, DAttr> m_map;
	final DNode m_owner;
	
//	@Override
	public void putAll(Map<? extends String, ? extends Object> t) {
		throw new DsfRuntimeException("Not implemented") ;
	}
	public Set<Map.Entry<String, DAttr>> attrEntrySet() {
		return m_map.entrySet() ;
	}
	
	/**
	 * We need to think through what it means to an Attr.
	 */
//	@Override
	public void clear() {
		final Iterator<Map.Entry<String,DAttr>> iter =
			m_map.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String,DAttr> entry = iter.next();
			final DAttr attr = entry.getValue();
			iter.remove();
			attr.setOwnerElement(null);
		}
	}
	
	AttributeMap(final DElement owner, final DNode /* IPropertyHolder */ propSource, 
		final int initialSize)
	{
		m_owner = owner;
		m_map = new LinkedHashMap<String,DAttr>(initialSize);
		m_propertySource = propSource;
	}
		
	public boolean hasAttribute(final String key) {
		return containsKey(key) ;
	}
	
//	@Override
	public boolean containsKey(Object key) {
		if (key == null) {
			return false ;
		}
		if (!(key instanceof String)) {
			chuck("containsKey(Object) signature really requires type String") ;
		}
		final PropertyDescriptor pd = 
			m_propertySource.getPropertyDescriptor((String)key);
		if (pd == null) {
			return m_map.containsKey(key);
		}
		
		return false;
	}
	
//	@Override
	public Collection<Object> values() {
		final ArrayList<Object> answer = new ArrayList<Object>(this.size()) ;
		
		for(Map.Entry<String, DAttr> mapEntry:m_map.entrySet()) {
			final DAttr attr = mapEntry.getValue();
			answer.add(attr.getObjectValue());
		}
		
		return answer ;
	}
	
//	@Override
	public boolean containsValue(final Object value) {
		for(Map.Entry<String,DAttr> mapEntry:m_map.entrySet()) {
			final DAttr attr = mapEntry.getValue();
			if (value == null) {
				if (attr.getObjectValue() == null) {
					return true;
				}
				continue;
			}
			final Object attrValue = attr.getObjectValue();
			if (value.equals(attrValue)) {
				return true;
			}
		}
		
		return false ;
	}
	
	public Iterator<String> getKeys() {
		return new Iterator<String>() {
			Iterator<String> backingItr = m_map.keySet().iterator() ;
			public boolean hasNext() { return backingItr.hasNext() ; }
			public String next() { return backingItr.next() ; }
			public void remove() {
				throw new DsfRuntimeException("Not supported operation") ;
			}
		};
	}
	
	public DAttr getAttributeNode(final String key) {
		return m_map.get(key);
	}
	
	DAttr put(final DAttr attr) {
		// TODO: better exception on null, test will need to be in same
		// package or use reflection.
		if (attr == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
				"null node is not allowed") ;			
		}
		attr.setOwnerElement((DElement)m_owner);
//		attr.setParent((DElement)m_owner) ;
//		attr.setDsfOwnerDocument(m_owner.getDsfOwnerDocument()) ;
		return m_map.put(attr.getNodeName(), attr);
	}
	
	/** this will get the value of the attribute; i.e. it will not return
	 * the attribute node.
	 * @param key
	 * @return
	 */
//	@Override
	public Object get(final Object key) {
		if (key == null) {
			return null ;
		}
		final DAttr attr = m_map.get(key);
		if (attr != null) {
			return attr.getObjectValue();
		}

		final PropertyDescriptor pd =
			m_propertySource.getPropertyDescriptor((String)key);
		if (pd == null) {
			return null;
		}
		
		final IValueBinding<?> binding =
			m_propertySource.getIntrinsicPropertyValueBinding((String)key);
		if (binding != null) {
			return binding.getValue();
		}
		
		final Method readMethod = pd.getReadMethod();
		if (readMethod == null) {
			throw new DsfRuntimeException("no read method for " + key);
		}
		
		try {
			return readMethod.invoke(m_propertySource, EMPTY);
		} 
		catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public Object put(final String key, final Object value) {
		if (key == null) {
			throw new DsfRuntimeException("Attribute key must not be null") ;
		}
		if (value instanceof Attr) {
			throw new DsfRuntimeException("value cannot be an Attr");			
		}
		
		assertAttributeName(key) ;

		// verify relationship before continuing			
		m_propertySource.assertAttributeRelationship(
			m_propertySource, key, value);
			
		final PropertyDescriptor pd = 
			m_propertySource.getPropertyDescriptor(key);
		
		if (pd == null) {
			DAttr attr = m_map.get(key);
			Object existing = (attr == null) ? null : attr.getObjectValue();
			if (attr == null) {
				attr = new DAttr(m_owner.getDsfOwnerDocument(), key);
				put(attr);
			}
			attr.setObjectValue(value);
			return existing;
		}
		
		final IValueBinding binding =
			m_propertySource.getIntrinsicPropertyValueBinding(key);
		if (binding != null) {
			final Object previous = binding.getValue();
			binding.setValue(value);
			return previous;
		}
		
		try {
			Object result = null;
			final Method readMethod = pd.getReadMethod();
			if (readMethod != null) {
				result = readMethod.invoke(m_propertySource, EMPTY);
			}
			final Method writeMethod = pd.getWriteMethod();
			if (writeMethod == null) {
				throw new DsfRuntimeException("no write method for "+ key);
			}
			writeMethod.invoke(m_propertySource, new Object[] {value});
			return result;
		} 
		catch (Exception e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		} 
	}

	public Object remove(final Object key) {
		if (key == null) {
			return null ;
		}
		if (!(key instanceof String)) {
			chuck("remove(Object) signature really requires type String") ;
		}
		final PropertyDescriptor pd 
			= m_propertySource.getPropertyDescriptor((String)key);
		if (pd != null) {
			throw new DsfRuntimeException("Can not remove an intrinsic property") ;
		}
		final DAttr attr = m_map.remove(key);
		if (attr == null) {
			return null;
		}
		return attr.getObjectValue();
	}
		
	public IValueBinding getValueBinding(final String name) {
		final PropertyDescriptor pd = m_propertySource.getPropertyDescriptor(name);
		if (pd == null)	{
			final DAttr attr = m_map.get(name);
			if (attr == null) {
				return null;
			}
			return attr.getValueBinding();
		}

		return m_propertySource.getIntrinsicPropertyValueBinding(name);
	}
		
	@SuppressWarnings("unchecked")
	/**
	 * @param name must not be null
	 * @param binding may be null
	 */
	public IValueBinding setValueBinding(
		final String name, final IValueBinding binding)
	{
		if (name == null) {
			throw new DsfRuntimeException("Binding name must not be null") ;
		}
		
		final PropertyDescriptor pd = m_propertySource.getPropertyDescriptor(name);
		if (pd == null)	{			
			DAttr attr = m_map.get(name);
			if (attr == null) {
				if (binding == null) {
					//don't create new DAttr if binding is null
					return null;
				}
				attr = new DAttr(m_owner.getDsfOwnerDocument(), name);
				put(attr);
			}
			return attr.setValueBinding(binding);
		}

		final IValueBinding previousBinding = 
			m_propertySource.getIntrinsicPropertyValueBinding(name);
		if (previousBinding != null) {
			m_propertySource.setDsfIntrinsicPropertyValueBinding(name, binding);
			return previousBinding;
		}

		throw new DsfRuntimeException(
			"The intrinsic property " + name + " is not bindable");
	}
	
	//
	// Satisfy INamedNodeMap
	//
	   /**
	 * Retrieves a node specified by name.
	 * @param name The <code>nodeName</code> of a node to retrieve.
	 * @return A <code>Node</code> (of any type) with the specified 
	 *   <code>nodeName</code>, or <code>null</code> if it does not identify 
	 *   any node in this map.
	 */
	public Node getNamedItem(final String name) {
		final DAttr attr = getAttributeNode(name);
		return attr;
	}
	
	/**
	 * Adds a node using its <code>nodeName</code> attribute. If a node with 
	 * that name is already present in this map, it is replaced by the new 
	 * one. Replacing a node by itself has no effect.
	 * <br>As the <code>nodeName</code> attribute is used to derive the name 
	 * which the node must be stored under, multiple nodes of certain types 
	 * (those that have a "special" string value) cannot be stored as the 
	 * names would clash. This is seen as preferable to allowing nodes to be 
	 * aliased.
	 * @param arg A node to store in this map. The node will later be 
	 *   accessible using the value of its <code>nodeName</code> attribute.
	 * @return If the new <code>Node</code> replaces an existing node the 
	 *   replaced <code>Node</code> is returned, otherwise <code>null</code> 
	 *   is returned.
	 * @exception DOMException
	 *   WRONG_DOCUMENT_ERR: Raised if <code>arg</code> was created from a 
	 *   different document than the one that created this map.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
	 *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>arg</code> is an 
	 *   <code>Attr</code> that is already an attribute of another 
	 *   <code>Element</code> object. The DOM user must explicitly clone 
	 *   <code>Attr</code> nodes to re-use them in other elements.
	 *   <br>HIERARCHY_REQUEST_ERR: Raised if an attempt is made to add a node 
	 *   doesn't belong in this NamedNodeMap. Examples would include trying 
	 *   to insert something other than an Attr node into an Element's map 
	 *   of attributes, or a non-Entity node into the DocumentType's map of 
	 *   Entities.
	 */
	public Node setNamedItem(final Node node) throws DOMException {
		if ((node instanceof Attr) == false) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Expected an Attr type") ;
		}
		return put((DAttr)node) ;
	}
	
	/**
	 * Removes a node specified by name. When this map contains the attributes 
	 * attached to an element, if the removed attribute is known to have a 
	 * default value, an attribute immediately appears containing the 
	 * default value as well as the corresponding namespace URI, local name, 
	 * and prefix when applicable.
	 * @param name The <code>nodeName</code> of the node to remove.
	 * @return The node removed from this map if a node with such a name 
	 *   exists.
	 * @exception DOMException
	 *   NOT_FOUND_ERR: Raised if there is no node named <code>name</code> in 
	 *   this map.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
	 */
	public Node removeNamedItem(final String name) throws DOMException {
		final DAttr removedAttr = getAttributeNode(name);
		if (removedAttr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"Did not find a node named: " + name) ;
		}
		m_map.remove(name) ;
		removedAttr.setOwnerElement(null) ;
		return removedAttr;
	}
	
	/**
	 * Returns the <code>index</code>th item in the map. If <code>index</code> 
	 * is greater than or equal to the number of nodes in this map, this 
	 * returns <code>null</code>.
	 * @param index Index into this map.
	 * @return The node at the <code>index</code>th position in the map, or 
	 *   <code>null</code> if that is not a valid index.
	 * This can be an expensive operation to call since there is no optimizable
	 * way to search for an attr in the map and thus is brute force.
	 */
	public Node item(final int index) {
		if (index < 0 || index >= size()) {
			return null ;
		}
		
		int count = 0 ;
		for(Map.Entry<String, DAttr> mapEntry:m_map.entrySet()) {
			if (count == index) {
				return mapEntry.getValue();
			}
			count++;
		}
		throw new DsfRuntimeException(
			"assertion failed: size changed after initial inspection");
	}
	
	/**
	 * The number of nodes in this map. The range of valid child node indices 
	 * is <code>0</code> to <code>length-1</code> inclusive.
	 */
	public int getLength() {
		return size() ;
	}
	
	/**
	 * Retrieves a node specified by local name and namespace URI.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value null as the namespaceURI parameter 
	 * for methods if they wish to have no namespace.
	 * @param namespaceURI The namespace URI of the node to retrieve.
	 * @param localName The local name of the node to retrieve.
	 * @return A <code>Node</code> (of any type) with the specified local 
	 *   name and namespace URI, or <code>null</code> if they do not 
	 *   identify any node in this map.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature "XML" and the language exposed through the 
	 *   Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	public Node getNamedItemNS(
		final String namespaceURI, final String localName) throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException("getNamedItemNS(...)") ;
	}
	
	/**
	 * Adds a node using its <code>namespaceURI</code> and 
	 * <code>localName</code>. If a node with that namespace URI and that 
	 * local name is already present in this map, it is replaced by the new 
	 * one. Replacing a node by itself has no effect.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value null as the namespaceURI parameter 
	 * for methods if they wish to have no namespace.
	 * @param arg A node to store in this map. The node will later be 
	 *   accessible using the value of its <code>namespaceURI</code> and 
	 *   <code>localName</code> attributes.
	 * @return If the new <code>Node</code> replaces an existing node the 
	 *   replaced <code>Node</code> is returned, otherwise <code>null</code> 
	 *   is returned.
	 * @exception DOMException
	 *   WRONG_DOCUMENT_ERR: Raised if <code>arg</code> was created from a 
	 *   different document than the one that created this map.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
	 *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>arg</code> is an 
	 *   <code>Attr</code> that is already an attribute of another 
	 *   <code>Element</code> object. The DOM user must explicitly clone 
	 *   <code>Attr</code> nodes to re-use them in other elements.
	 *   <br>HIERARCHY_REQUEST_ERR: Raised if an attempt is made to add a node 
	 *   doesn't belong in this NamedNodeMap. Examples would include trying 
	 *   to insert something other than an Attr node into an Element's map 
	 *   of attributes, or a non-Entity node into the DocumentType's map of 
	 *   Entities.
	 *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature "XML" and the language exposed through the 
	 *   Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	public Node setNamedItemNS(final Node arg) throws DOMException {
		throw new DsfDomNotSupportedRuntimeException("setNamedItemNS(...)") ;    	
	}
	
	/**
	 * Removes a node specified by local name and namespace URI. A removed 
	 * attribute may be known to have a default value when this map contains 
	 * the attributes attached to an element, as returned by the attributes 
	 * attribute of the <code>Node</code> interface. If so, an attribute 
	 * immediately appears containing the default value as well as the 
	 * corresponding namespace URI, local name, and prefix when applicable.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value null as the namespaceURI parameter 
	 * for methods if they wish to have no namespace.
	 * @param namespaceURI The namespace URI of the node to remove.
	 * @param localName The local name of the node to remove.
	 * @return The node removed from this map if a node with such a local 
	 *   name and namespace URI exists.
	 * @exception DOMException
	 *   NOT_FOUND_ERR: Raised if there is no node with the specified 
	 *   <code>namespaceURI</code> and <code>localName</code> in this map.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
	 *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature "XML" and the language exposed through the 
	 *   Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	public Node removeNamedItemNS(
			final String namespaceURI,
			final String localName) throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException("removeNamedItemNS(...)") ;
	}
	
	public boolean isEmpty() {
		return m_map.isEmpty();
	}
	
	public int size() {
		return m_map.size();
	}
	
	public Set<String> keySet() {
		return m_map.keySet();
	}
	
	public Set<Map.Entry<String, Object>> entrySet() {
//		Map<String, Object> x = new HashMap<String, Object>(m_map.size()) ;
//		for(Map.Entry<String, DAttr> originalEntry: m_map.entrySet()) {
//			Map.Entry<String, Object> newEntry 
//				= new Map.Entry<String, Object>(originalEntry.getKey(), originalEntry.getValue())
//		}
		return null ;
	}
	
	public DAttr getAttr(final String key) {
		return m_map.get(key) ;
	}
	
	public Iterator<DAttr> iterator() {
		return m_map.values().iterator();
	}
	@Override
	public String toString(){
		final Z z = new Z();
		z.getBuffer().append("{");
		for (final DAttr attr:this){
			z.format(attr.getName(), attr.getValue());
		}
		z.getBuffer().append("}");
		return z.toString();
	}
	
	//
	// Private
	//
	private void chuck(final String msg) {
		throw new DsfRuntimeException(msg) ;
	}
	private void assertAttributeName(final String name) 
		throws DsfInvalidNameException 
	{
// MrPperf - don't assert attributes names if config says its off.		
		if (!DsfVerifierConfig.getInstance().isVerifyNaming()) {
			return ;		
		}
		
//		final NameStatusCheck status = DsfCtx.ctx().getContainer()
//			.getDsfNamingFamily().verifyLocalName(name);
		final NameStatusCheck status 
			= m_owner.getDsfNamingFamily().verifyLocalName(name);
		if (status.isOk() == false) {
			throw new DsfInvalidNameException(status.getErrorMessage()) ;
		}	
	}	
}
