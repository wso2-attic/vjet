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
package org.ebayopensource.vjet.eclipse.ui;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

/**
 * monitor perspective change event to activate corresponding context
 * 
 * 
 *
 */
public class VjetPerspectiveListener extends PerspectiveAdapter {
	private static final String JS_BROWSING_PERSPECTIVE_ID = "org.ebayopensource.vjet.eclipse.ui.JavascriptBrowsingPerspective";
	private static final String VJET_PERSPECTIVE_ID = "org.eclipse.dltk.mod.javascript.ui.JavascriptPerspective";
	private IContextActivation activatedContext;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.PerspectiveAdapter#perspectiveActivated(org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IPerspectiveDescriptor)
	 */
	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		if (JS_BROWSING_PERSPECTIVE_ID.equals(perspective.getId())
				|| VJET_PERSPECTIVE_ID.equals(perspective.getId())) {
			IContextService contextService = (IContextService)page.getWorkbenchWindow().getService(IContextService.class);
			this.activatedContext = contextService.activateContext("org.eclipse.dltk.mod.ui.scriptEditorScope");
		}
		else
			this.activatedContext = null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.PerspectiveAdapter#perspectiveDeactivated(org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IPerspectiveDescriptor)
	 */
	@Override
	public void perspectiveDeactivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		if (JS_BROWSING_PERSPECTIVE_ID.equals(perspective.getId())
				|| VJET_PERSPECTIVE_ID.equals(perspective.getId())) {
			IContextService contextService = (IContextService)page.getWorkbenchWindow().getService(IContextService.class);
			
			if (this.activatedContext != null) {
				contextService.deactivateContext(this.activatedContext);
				this.activatedContext = null;
			}
		}
	}
	
}
