/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.common.naming.DsfInvalidNameException;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.IFacetsMap;
import org.ebayopensource.dsf.common.node.IllegalDNodeRelationshipException;


class FacetsMap extends LinkedHashMap<String, DNode> implements IFacetsMap {
	private static final long serialVersionUID = -3003881245238347508L;
	final DNode m_owner;
	
	//
	// Constructor(s)
	//
	public FacetsMap(final DNode owner, final int initialSize) {
		super(initialSize);
		m_owner = owner;
	}
	
	//
	// API
	//
//	public IDNodeIterator getChildrenAndFacetsItr() {
//		IDNodeIterator itr = getItrIfFacetOrChildIsEmpty();
//		if (itr != null) {
//			return itr;
//		}
//		final List<DNode> combined = new ArrayList<DNode>();
//// MrPperf - handle m_owners childNodes being null
//		if (m_owner.m_childNodes != null) {
//			combined.addAll(m_owner.m_childNodes);
//		}
//		combined.addAll(values());
//
//		itr = new IDNodeIterator.IteratorWrapper(combined.iterator());
//
//		return itr;
//	}
//
//	public IDNodeIterator getFacetsAndChildrenItr() {
//		IDNodeIterator itr = getItrIfFacetOrChildIsEmpty();
//		if (itr != null) {
//			return itr;
//		}
//		
//		final List<DNode> combined = new ArrayList<DNode>();
//		combined.addAll(values());
//// MrPperf - handle m_owner.m_childNodes being null
//		if (m_owner.m_childNodes != null) {
//			combined.addAll(m_owner.m_childNodes);
//		}
//		itr = new IDNodeIterator.IteratorWrapper(combined.iterator());
//
//		return itr;
//	}
//	
//	private IDNodeIterator getItrIfFacetOrChildIsEmpty() {
//		if (isEmpty()) {
//// MrPperf - handle m_owner.m_childNodes being null
//			if (m_owner.hasChildNodes()) {
//				return new IDNodeIterator.IteratorWrapper(m_owner.m_childNodes.iterator());
//			}
//			else {
//				return EMPTY_ITERATOR;
//			}
//		}
//
//// MrPperf - handle m_owner.m_childNodes being null
//		if (!m_owner.hasChildNodes()) {
//			return new IDNodeIterator.IteratorWrapper(values().iterator());
//		}
//
//		return null;
//	}
	
	@Override
	public void clear() {
		Iterator keys = keySet().iterator();
		while (keys.hasNext()) {
			keys.next();
			keys.remove();
		}
		super.clear();
	}
	
	@Override
	public Set<Map.Entry<String, DNode>> entrySet() {
		return (new FacetsMapEntrySet(this));
	}
	
	@Override
	public Set<String> keySet() {
		return (new FacetsMapKeySet(this));
	}
	
	protected static void assertParentFacetRelationship(
		final DNode parent, 
		final String facetName,
		final DNode facet)
	{
		IDNodeRelationshipVerifier.Status status = parent
			.getDsfRelationshipVerifier().acceptableAsFacet(
				parent, facetName, facet);
		assertRelationship(status);
			
		status = facet.getDsfRelationshipVerifier().acceptableAsParent(
			facet, parent);		
		assertRelationship(status);				
	}	
	
	protected static void assertRelationship(
		final IDNodeRelationshipVerifier.Status status)
	{
		if (!status.isOk()) {
			throw new IllegalDNodeRelationshipException
				(status.getErrorMessage());
		}
	}
	
	/** TODO: please note that we will set the local name of the facet
	 * name.  I.e. the value (DsfComponent) might change.
	 */
	@Override
	public DNode put(final String key, final DNode value) {
		if (key == null) {
			throw new NullPointerException(
				"Facet key must be a non-null String value");
		}
		if (value == null) {
			throw new NullPointerException(
				"Facet value must be a non-null DNode type");
		}
		
		// Null name and facet check happen in the put(name, facet)
		if (m_owner.hasDsfChildWithLocalName(key)) {
			throw new DsfInvalidNameException(
				"there is a child node with name '" + key + "'");
		}

		// verify relationship before continuing		
		assertParentFacetRelationship(m_owner, key, value);		

		// deal with possibility that name set could fail.  This requires
		// us to reset the old parent and then re-throw the naming except.
		final DNode oldParent = value.getDsfParentNode();
		final boolean sameParent = (oldParent == m_owner);
			
		if (!sameParent) {
			value.setParent(m_owner);				
		}
			
		try {
			// Only set local name if we are introducing a new key
			if (!super.containsKey(key)) {
				value.getDsfName().setLocalName(key);
			}
		}
		catch(DsfInvalidNameException e) {
			if (!sameParent) {
				value.setParent(oldParent) ;					
			}				
			throw e ;
		}
			
		// de-parent if we are replacing someone						
		final DNode previous = get(key);
		if (previous != null && previous != value) {
			previous.setParent(null);
		}
					
		return super.put(key, value);
	}
	@Override
	public void putAll(final Map<? extends String, ? extends DNode> map) {
		if (map == null) {
			throw new NullPointerException();
		}
		final Iterator<? extends String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			put(key, map.get(key));
		}
	}

	@Override
	public DNode remove(final Object key) {
		final DNode previous = get(key);
		if (previous != null) {
			previous.setParent(null);
		}
		super.remove(key);
		return previous;
	}
	@Override
	public Collection<DNode> values() {
		return (new FacetsMapValues(this));
	}
	
	public Iterator<DNode> iterator() {
		return this.values().iterator();
	}

	Iterator<String> keySetIterator() {
		return ((new ArrayList<String>(super.keySet())).iterator());
	}
	// Private implementation of Set for FacetsMap.getEntrySet()
	private class FacetsMapEntrySet extends AbstractSet<Map.Entry<String, DNode>> {

		public FacetsMapEntrySet(FacetsMap map) {
			this.m_map = map;
		}

		private FacetsMap m_map = null;
		@Override
		public boolean add(final Map.Entry<String, DNode> o) {
			throw new UnsupportedOperationException();
		}
		@SuppressWarnings("unused")
		public boolean add(final Collection c) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void clear() {
			m_map.clear();
		}
		@Override
		public boolean contains(final Object o) {
			if (o == null) {
				throw new NullPointerException();
			}
			if (!(o instanceof Map.Entry)) {
				return (false);
			}
			Map.Entry e = (Map.Entry) o;
			Object k = e.getKey();
			Object v = e.getValue();
			if (!m_map.containsKey(k)) {
				return (false);
			}
			if (v == null) {
				return (m_map.get(k) == null);
			}
			return (v.equals(m_map.get(k)));
		}
		@Override
		public boolean isEmpty() {
			return (m_map.isEmpty());
		}
		@Override
		public Iterator<Map.Entry<String, DNode>> iterator() {
			return (new FacetsMapEntrySetIterator(m_map));
		}
		@Override
		public boolean remove(final Object o) {
			if (o == null) {
				throw new NullPointerException();
			}
			if (!(o instanceof Map.Entry)) {
				return false;
			}
			final Object k = ((Map.Entry) o).getKey();
			if (m_map.containsKey(k)) {
				m_map.remove(k);
				return true;
			}
			return false;
		}
		@Override
		public boolean removeAll(final Collection c) {
			boolean result = false;
			final Iterator v = c.iterator();
			while (v.hasNext()) {
				if (remove(v.next())) {
					result = true;
				}
			}
			return (result);
		}
		@Override
		public boolean retainAll(final Collection c) {
			boolean result = false;
			final Iterator v = iterator();
			while (v.hasNext()) {
				if (!c.contains(v.next())) {
					v.remove();
					result = true;
				}
			}
			return (result);
		}
		@Override
		public int size() {
			return (m_map.size());
		}

	}


	// Private implementation of Map.Entry for FacetsMapEntrySet
	private class FacetsMapEntrySetEntry implements Map.Entry<String, DNode> {

		public FacetsMapEntrySetEntry(final FacetsMap map, final String key) {
			m_map = map;
			m_key = key;
		}

		private FacetsMap m_map;
		private String m_key;
		@Override
		public boolean equals(final Object o) {
			if (o == null) {
				return (false);
			}
			if (!(o instanceof Map.Entry)) {
				return (false);
			}
			Map.Entry e = (Map.Entry) o;
			if (m_key == null) {
				if (e.getKey() != null) {
					return (false);
				}
			} else {
				if (!m_key.equals(e.getKey())) {
					return (false);
				}
			}
			final Object v = m_map.get(m_key);
			if (v == null) {
				if (e.getValue() != null) {
					return (false);
				}
			} else {
				if (!v.equals(e.getValue())) {
					return (false);
				}
			}
			return (true);
		}

		public String getKey() {
			return (m_key);
		}

		public DNode getValue() {
			return (m_map.get(m_key));
		}
		@Override
		public int hashCode() {
			Object value = m_map.get(m_key);
			return (((m_key == null) ? 0 : m_key.hashCode()) ^
					((value == null) ? 0 : value.hashCode()));
		}

		public DNode setValue(final DNode value) {
			final DNode previous = m_map.get(m_key);
			m_map.put(m_key, value);
			return previous;
		}

	}


	// Private implementation of Set for FacetsMap.getEntrySet().iterator()
	private class FacetsMapEntrySetIterator 
		implements Iterator<Map.Entry<String, DNode>>
	{
		public FacetsMapEntrySetIterator(final FacetsMap map) {
			m_map = map;
			m_iterator = map.keySetIterator();
		}

		private FacetsMap m_map = null;
		private Iterator<String> m_iterator = null;
		private Map.Entry<String, DNode> m_last = null;

		public boolean hasNext() {
			return (m_iterator.hasNext());
		}

		public Map.Entry<String, DNode> next() {
			m_last = new FacetsMapEntrySetEntry(m_map, m_iterator.next());
			return (m_last);
		}

		public void remove() {
			if (m_last == null) {
				throw new IllegalStateException();
			}
			m_map.remove(((Map.Entry) m_last).getKey());
			m_last = null;
		}

	}


	// Private implementation of Set for FacetsMap.getKeySet()
	private class FacetsMapKeySet extends AbstractSet<String> {

		public FacetsMapKeySet(final FacetsMap map) {
			m_map = map;
		}

		private FacetsMap m_map = null;
		@Override
		public boolean add(String o) {
			throw new UnsupportedOperationException();
		}
		@SuppressWarnings("unused")
		public boolean add(final Collection c) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void clear() {
			m_map.clear();
		}
		@Override
		public boolean contains(final Object o) {
			return (m_map.containsKey(o));
		}
		@Override
		public boolean containsAll(final Collection c) {
			final Iterator v = c.iterator();
			while (v.hasNext()) {
				if (!m_map.containsKey(v.next())) {
					return (false);
				}
			}
			return (true);
		}
		@Override
		public boolean isEmpty() {
			return (m_map.size() == 0);
		}
		@Override
		public Iterator<String> iterator() {
			return (new FacetsMapKeySetIterator(m_map));
		}
		@Override
		public boolean remove(final Object o) {
			if (m_map.containsKey(o)) {
				m_map.remove(o);
				return (true);
			}
			return (false);
		}
		@Override
		public boolean removeAll(final Collection c) {
			boolean result = false;
			final Iterator v = c.iterator();
			while (v.hasNext()) {
				Object o = v.next();
				if (m_map.containsKey(o)) {
					m_map.remove(o);
					result = true;
				}
			}
			return (result);
		}
		@Override
		public boolean retainAll(final Collection c) {
			boolean result = false;
			final Iterator v = iterator();
			while (v.hasNext()) {
				if (!c.contains(v.next())) {
					v.remove();
					result = true;
				}
			}
			return (result);
		}
		@Override
		public int size() {
			return (m_map.size());
		}

	}


	// Private implementation of Set for FacetsMap.getKeySet().iterator()
	private class FacetsMapKeySetIterator implements Iterator<String> {

		public FacetsMapKeySetIterator(final FacetsMap map) {
			m_map = map;
			m_iterator = map.keySetIterator();
		}

		private FacetsMap m_map = null;
		private Iterator<String> m_iterator = null;
		private String m_last = null;

		public boolean hasNext() {
			return (m_iterator.hasNext());
		}

		public String next() {
			m_last = m_iterator.next();
			return (m_last);
		}

		public void remove() {
			if (m_last == null) {
				throw new IllegalStateException();
			}
			m_map.remove(m_last);
			m_last = null;
		}
	}

	// Private implementation of Collection for FacetsMap.values()
	private class FacetsMapValues extends AbstractCollection<DNode> {

		public FacetsMapValues(final FacetsMap map) {
			m_map = map;
		}

		private FacetsMap m_map;
		@Override
		public boolean add(final DNode o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean addAll(final Collection c) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void clear() {
			m_map.clear();
		}
		@Override
		public boolean isEmpty() {
			return (m_map.size() == 0);
		}
		@Override
		public Iterator<DNode> iterator() {
			return (new FacetsMapValuesIterator(m_map));
		}
		@Override
		public int size() {
			return (m_map.size());
		}
	}


	// Private implementation of Iterator for FacetsMap.values().iterator()
	private class FacetsMapValuesIterator implements Iterator<DNode> {

		public FacetsMapValuesIterator(final FacetsMap map) {
			m_map = map;
			m_iterator = map.keySetIterator();
		}

		private FacetsMap m_map = null;
		private Iterator m_iterator = null;
		private Object m_last = null;

		public boolean hasNext() {
			return (m_iterator.hasNext());
		}

		public DNode next() {
			m_last = m_iterator.next();
			return (m_map.get(m_last));
		}

		public void remove() {
			if (m_last == null) {
				throw new IllegalStateException();
			}
			m_map.remove(m_last);
			m_last = null;
		}
	}
}
