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
package org.ebayopensource.vjet.eclipse.internal.ui.view.ast;

import org.eclipse.debug.internal.ui.DebugPluginImages;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * skip document change action.
 * 
 * 
 *
 */
class SkipDocumentChangeAction extends Action {
	private boolean skipDocumentChange;
	
	public SkipDocumentChangeAction() {
		super("Skip", IAction.AS_CHECK_BOX);
		setToolTipText("Skip Document Change");
		setImageDescriptor(DebugPluginImages.getImageDescriptor(IDebugUIConstants.IMG_SKIP_BREAKPOINTS));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		skipDocumentChange = !skipDocumentChange;
		this.setChecked(skipDocumentChange);
	}
	
}
