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
package org.ebayopensource.vjet.eclipse.internal.ui.browsing;

import org.ebayopensource.vjet.eclipse.internal.ui.wizards.IHelpContextIds;
import org.eclipse.dltk.mod.ui.browsing.TypesView;
import org.eclipse.jface.viewers.IContentProvider;

/**
 * This class in representation of the vjet types view.
 * 
 * 
 * 
 */
public class VjetTypesView extends TypesView {

	@Override
	protected IContentProvider createContentProvider() {
		return new VjetBrowsingContentProvider(true, this, this.getToolkit());
	}
	
	@Override
	protected String getHelpContextId() {
		// TODO Auto-generated method stub
		return IHelpContextIds.TYPES_VIEW;
	}
	// @Override
	// public void createPartControl(Composite parent) {
	// // TODO Auto-generated method stub
	// super.createPartControl(parent);
	//		
	// // Add by Oliver. 2009-11-02. add the F1 help for vjet types view.
	// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
	// IHelpContextIds.TYPES_VIEW);
	//	}
}
