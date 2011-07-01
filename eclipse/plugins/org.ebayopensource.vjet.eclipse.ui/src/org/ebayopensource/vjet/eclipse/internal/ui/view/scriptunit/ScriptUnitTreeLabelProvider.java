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
package org.ebayopensource.vjet.eclipse.internal.ui.view.scriptunit;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * 
 *
 */
class ScriptUnitTreeLabelProvider extends LabelProvider {
	private boolean checkNode;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (!this.checkNode 
				|| !(element instanceof IJstNode))
			return null;
		
		IJstNode jstNode = (IJstNode)element;
		if ((jstNode.getSource() == null) 
				|| (jstNode.getRootType() == null))
			return null;
		
		int startOffset = jstNode.getSource().getStartOffSet();
		int endOffset = jstNode.getSource().getEndOffSet();
		
		//switch to new version JstUtil
		Object node = JstUtil.getLeafNode(jstNode.getRootType(), startOffset, endOffset);
//		Object node = JstUtil.getNode(jstNode.getRootType(), startOffset, endOffset);
		if (node == element)
			return null;
		else
			return ImageDescriptor.getMissingImageDescriptor().createImage();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof IScriptUnit)
			return "Script Unit";
			
		if (element instanceof IJstNode)
			return getJstNodeLabel((IJstNode)element);
		
		return null;
	}
	
	private String getJstNodeLabel(IJstNode jstNode) {
		return getName(jstNode) + getSourceRange(jstNode);
	}
	
	private String getName(IJstNode jstNode) {
		String simpleName = jstNode.getClass().getSimpleName();
		
		if (jstNode instanceof IJstType)
			return simpleName + ":" + ((IJstType)jstNode).getName();
		
		if (jstNode instanceof IJstMethod)
			return simpleName + ":" + ((IJstMethod)jstNode).getName();
		
		if (jstNode instanceof IJstProperty)
			return simpleName + ":" + ((IJstProperty)jstNode).getName();
		
		if (jstNode instanceof JstVar)
			return simpleName + ":" + ((JstVar)jstNode).getName();
		
		if (jstNode instanceof JstIdentifier)
			return simpleName + ":" + ((JstIdentifier)jstNode).getName();
		
		return simpleName;
	}

	private String getSourceRange(IJstNode jstNode) {
		JstSource jstSource = jstNode.getSource();
		if (jstSource != null)
			return "[" + jstSource.getStartOffSet() + "," + jstSource.getEndOffSet() + "]";
		
		return "";
	}

	/**
	 * @param checkNode the checkNode to set
	 */
	public void setCheckNode(boolean checkNode) {
		this.checkNode = checkNode;
	}
	
}
