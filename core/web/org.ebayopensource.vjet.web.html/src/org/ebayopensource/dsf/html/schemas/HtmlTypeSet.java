/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import java.util.BitSet;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class HtmlTypeSet {
	private BitSet m_set;
	public HtmlTypeSet() {
		final int size = HtmlTypeEnum.size();
		m_set = new BitSet(size+1);
	}
	public void add(final HtmlTypeEnum htmlType) {
		m_set.set(htmlType.getId());
	}
	public void remove(final HtmlTypeEnum htmlType) {
		m_set.clear(htmlType.getId());
	}
	public boolean contains(final HtmlTypeEnum htmlType) {
		return m_set.get(htmlType.getId());
	}
	/** true always wins on the merge.
	 * @param set
	 */
	public void merge(final HtmlTypeSet set) {
		m_set.or(set.m_set);
	}
}
