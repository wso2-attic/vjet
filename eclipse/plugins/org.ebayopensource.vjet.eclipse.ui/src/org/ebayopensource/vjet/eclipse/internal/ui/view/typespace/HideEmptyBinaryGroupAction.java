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

import org.eclipse.debug.internal.ui.DebugPluginImages;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * Hide empty binary group action.
 * 
 * 
 *
 */
class HideEmptyBinaryGroupAction extends Action {
	private TypespaceView view;
	
	public HideEmptyBinaryGroupAction(TypespaceView typespaceView) {
		super("Hide", IAction.AS_CHECK_BOX);
		this.view = typespaceView;
		setToolTipText("Hide empty binary group");
		setImageDescriptor(DebugPluginImages.getImageDescriptor(IDebugUIConstants.IMG_SKIP_BREAKPOINTS));
		setChecked(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		view.updateHideState();
	}
	
}
