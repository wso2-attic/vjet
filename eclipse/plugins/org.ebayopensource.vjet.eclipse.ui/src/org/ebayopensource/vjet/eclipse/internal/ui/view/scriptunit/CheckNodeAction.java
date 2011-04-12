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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.internal.ui.SearchPluginImages;

/**
 * check node action, to determine whether certain node can be located
 * 
 * 
 *
 */
class CheckNodeAction extends Action {
	private TreeViewer treeViewer;
	
	private boolean checkNode;
	
	/**
	 * @param treeViewer
	 */
	public CheckNodeAction(TreeViewer treeViewer) {
		super("Find Node", IAction.AS_CHECK_BOX);
		setToolTipText("verify JstUtil");
		SearchPluginImages.setImageDescriptors(this, SearchPluginImages.T_TOOL, SearchPluginImages.IMG_TOOL_SEARCH);
		
		this.treeViewer = treeViewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		this.checkNode = !this.checkNode;
		this.setChecked(this.checkNode);
		
		//config label provider and refresh tree
		ScriptUnitTreeLabelProvider labelProvider = (ScriptUnitTreeLabelProvider)this.treeViewer.getLabelProvider();
		labelProvider.setCheckNode(this.checkNode);
		this.treeViewer.setInput(this.treeViewer.getInput());
	}

}
