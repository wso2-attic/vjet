/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.view.typespace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 *
 */
class TypespaceTreeContentProvider implements ITreeContentProvider {

	private boolean m_hideEmptyBinaryGroup = true;
	private int m_proCount;
	private int m_openLibCount;
	private int m_libCount;
	private int m_totalTypeCount;
	private Comparator<? super IGroup> m_groupComparator = new InnerGroupComparator();

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement.getClass().isArray())
			return (Object[])parentElement;
		
		if (parentElement instanceof TypeSpaceMgr) {
			Object[] objs = TypeSpaceMgr.getInstance().getController().getJstTypeSpaceMgr().getTypeSpace().getGroups().values().toArray();
			List<IGroup> groups = new ArrayList<IGroup>();
			for (int i = 0; i < objs.length; i++) {
				Object obj = objs[i];
				if (obj instanceof IGroup) {
					IGroup group = (IGroup)obj;
					if (ifAddGroup(group)) {
						groups.add(group);
						m_totalTypeCount += group.getEntities().size();
					} 
				}
			}
			Collections.sort(groups, m_groupComparator  );
			return groups.toArray();
		}
		
		if (parentElement instanceof IGroup) {
			return ((IGroup)parentElement).getEntities().values().toArray();
		}
		
		if (parentElement instanceof IJstType) {
			IJstType jstType = (IJstType)parentElement;
			
			List children = new ArrayList();
			
			//add embeded types
			if(jstType.getEmbededTypes() != null) {
				Collections.addAll(children, jstType.getEmbededTypes().toArray());
			}
			
			//add children 
			Collections.addAll(children, jstType.getChildren().toArray());
			
			return children.toArray();
		}
		
		if (parentElement instanceof BaseJstNode) {
			List<BaseJstNode> children = ((BaseJstNode)parentElement).getChildren();
			return children.toArray(new BaseJstNode[children.size()]);
		}
		
		return null;
	}

	private boolean ifAddGroup(IGroup group) {
		//count the number
		String groupName = group.getName();
		boolean hasChildren = !group.getEntities().isEmpty();
		if (CodeassistUtils.isBinaryPath(groupName)|| CodeassistUtils.isNativeGroup(groupName)) {
			m_libCount++;
			if (hasChildren) {
				m_openLibCount++;
			}
		} else {
			m_proCount++;
		}
			
		if (!m_hideEmptyBinaryGroup ) {
			return true;
		}
		boolean bol = !(CodeassistUtils.isBinaryPath(group.getName()) && !hasChildren);
		
		return bol;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		if (element instanceof BaseJstNode)
			return ((BaseJstNode)element).getParentNode();
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return this.getChildren(element).length > 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		clearCount();
		return this.getChildren(inputElement);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		clearCount();

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// doNothing

	}

	public void setHideEmptyBinaryGoupr(boolean hideEmptyBinaryGroup) {
		this.m_hideEmptyBinaryGroup = hideEmptyBinaryGroup;
	}

	public boolean ifHideEmptyBinaryGroup() {
		return this.m_hideEmptyBinaryGroup;
	}
	
	private int getGroupType(IGroup group) {
		String groupName = group.getName();
		if (CodeassistUtils.isBinaryPath(groupName)) {
			return 2;
		} else if(CodeassistUtils.isNativeGroup(groupName)){
			return 0;
		}else {
			return 1;
		}
	}
	
	public int getProCount() {
		return m_proCount;
	}

	public int getOpenLibCount() {
		if (!m_hideEmptyBinaryGroup ) {
			return m_libCount;
		} else {
			return m_openLibCount;
		}
	}

	public int getLibCount() {
		return m_libCount;
	}
	public int getTotalTypeCount() {
		return m_totalTypeCount;
	}
	
	void clearCount() {
		m_proCount = 0;
		m_openLibCount = 0;
		m_libCount = 0;
		m_totalTypeCount = 0;
	}
	
	private class InnerGroupComparator implements Comparator<IGroup>{
		@Override
		public int compare(IGroup object1, IGroup object2) {
			int iGroup1 = getGroupType(object1);
			int iGroup2 = getGroupType(object2);
			int result = iGroup1 - iGroup2;
			if (result != 0) {
				return result;
			} else {
				String name1 = object1.getName();
				String name2 = object2.getName();
				return name1.compareTo(name2);
			}
		}}

}
