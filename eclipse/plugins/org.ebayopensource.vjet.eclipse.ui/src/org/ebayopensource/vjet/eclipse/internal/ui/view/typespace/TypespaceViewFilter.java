/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.view.typespace;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TypespaceViewFilter extends ViewerFilter {
	private String m_filterStr = "";
	private int m_selectedTypeCount;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IJstType) {
			if (!StringUtils.isBlankOrEmpty(m_filterStr)) {
				IJstType type = (IJstType) element;
				String name = type.getName();
				String simpleName = type.getSimpleName();
				if (StringUtils.isBlankOrEmpty(name)
						|| StringUtils.isBlankOrEmpty(simpleName)) {
					return false;
				} else {
					boolean bol = name.toLowerCase().startsWith(m_filterStr)
							|| simpleName.toLowerCase().startsWith(m_filterStr);
					if (bol) {
						m_selectedTypeCount++;
					}
					return bol;
				}
			} else {
				return true;
			}

		} else {
			return true;
		}
	}

	@Override
	public boolean isFilterProperty(Object element, String property) {
		return super.isFilterProperty(element, property);
	}

	public void setFilterStr(String filterStr) {
		this.m_filterStr = filterStr.toLowerCase();
	}

	public boolean isEmptyFilter() {
		return StringUtils.isBlankOrEmpty(m_filterStr);
	}

	void clearCount() {
		this.m_selectedTypeCount = 0;
	}

	public int getSelectedTypeCount() {
		return m_selectedTypeCount;
	}

}
