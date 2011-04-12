/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.view.typespace;

import org.eclipse.dltk.mod.internal.ui.scriptview.ScriptMessages;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.jface.action.Action;


/**
 * Collapse all nodes.
 */
class CollapseAllAction extends Action {
	
	private TypespaceView view;
	
	CollapseAllAction(TypespaceView part) {
		super(ScriptMessages.CollapseAllAction_label); 
		setDescription(ScriptMessages.CollapseAllAction_description); 
		setToolTipText(ScriptMessages.CollapseAllAction_tooltip); 
		DLTKPluginImages.setLocalImageDescriptors(this, "collapseall.gif"); //$NON-NLS-1$
		
		view= part;
	}
 
	public void run() { 
		view.collapseAll();
	}
}
