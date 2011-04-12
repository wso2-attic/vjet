/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** TODO: Answer questions.
 * - What is the significance of the order???
 * - Why does addScope put it at the beginning of the list???
 * - What is the purpose of knowing whether it has parent, when one
 *   can't get the parent???
 */
public class ParentScopes {
	private List<String> m_ancestorScopes = new ArrayList<String>();
	private boolean m_hasScopedParent = false;

	public Iterator<String> iterator() {
		return m_ancestorScopes.iterator();
	}

	public void addParentToCurrentScope(final String scope) {
		m_ancestorScopes.add(0, scope);
	}
	
	public void appendScope(final String scope) {
		m_ancestorScopes.add(scope);
	}
	
	public String removeFirst() {
		if (m_ancestorScopes.isEmpty()) {
			return null;
		}
		return m_ancestorScopes.remove(0);
	}
	
	public String removeLast() {
		if (m_ancestorScopes.isEmpty()) {
			return null;
		}
		return m_ancestorScopes.remove(m_ancestorScopes.size() - 1);
	}
	
	public String getFirst() {
		if (m_ancestorScopes.isEmpty()) {
			return null;
		}
		return m_ancestorScopes.get(0);
	}
	
	public String getLast() {
		if (m_ancestorScopes.isEmpty()) {
			return null;
		}
		return m_ancestorScopes.get(m_ancestorScopes.size() - 1);
	}	
	public String get(final int index) {
		return m_ancestorScopes.get(index);
	}
	
	public int size() {
		return m_ancestorScopes.size();
	}
	/** If the size is more than 1, then this should return true.
	 */
	public boolean hasScopedParent() {
		return m_hasScopedParent;
	}

	/** This seems dangerous given the fact that a size of more than
	 * one should return true.
	 */
	public void setHasScopedParent(final boolean set) {
		m_hasScopedParent = set;		
	}
	
	public boolean equals(final Object obj) {
		if (!(obj instanceof ParentScopes)) {
			return false;
		}
		final ParentScopes scopes2 = (ParentScopes)obj;
		return isEqual(this, scopes2);
	}

	private static boolean isEqual(
		final ParentScopes scopes1,
		final ParentScopes scopes2)
	{
		if (scopes1.size() != scopes2.size()) {
			return false;
		}
		final Iterator<String> iter1 = scopes1.iterator();
		final Iterator<String> iter2 = scopes2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			final String s1 = iter1.next();
			final String s2 = iter2.next();
//				out("s1=" + s1 + ", s2=" + s2);
			if (!s1.equals(s2)) {
				return false;
			}
		}
		if (iter1.hasNext() || iter2.hasNext()) {
			return false; // one of the iterators ended up being longer,
		}
		return true;
	}
}
