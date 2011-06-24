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

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.PartSite;

/**
 * 
 * 
 */
class VjetPartListener implements IPartListener {

	/*
	 * comment by kevin, activing context logic has been moved to VjetPerspectiveListener.
	 * JUST based on perspective, not part!
	 */
//	private IContextActivation m_activatedContext;

	public void partActivated(IWorkbenchPart part) {
			
		if (part instanceof VjoEditor) {
			VjoEditor editor = (VjoEditor)part;
			IModelElement module = editor.getInputModelElement();
			if (CodeassistUtils.isVjoSourceModule(module)) {
				try {
					boolean refresh = ((VjoSourceModule)module).refreshSourceFields();
					
					if (refresh) {
						editor.refreshOutlinePage();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
//		IWorkbenchPartSite site = part.getSite();
//		if (DLTKUIPlugin.ID_SCRIPTEXPLORER.equals(site.getId())) {
//			IContextService contextService = (IContextService) part.getSite()
//					.getService(IContextService.class);
//			m_activatedContext = contextService
//					.activateContext("org.eclipse.dltk.mod.ui.scriptEditorScope");
//		}
		
		//add by xingzhu, to fix bug 3332, change add exception action tooltip to 'Add Javascript Exception Breakpoint' 
		try {
			if (part instanceof IViewPart) {
				IToolBarManager toolBarManager = ((PartSite)(part.getSite())).getActionBars().getToolBarManager();
				IContributionItem[] items = toolBarManager.getItems();
				for (int i = 0; i < items.length; i++) {
					if ("org.eclipse.jdt.debug.ui.actions.AddException".equals(items[i].getId())) {
						ActionContributionItem actionItem = (ActionContributionItem)items[i];
						actionItem.getAction().setToolTipText("Add JavaScript Exception Breakpoint");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	public void partDeactivated(IWorkbenchPart part) {
//		IWorkbenchPartSite site = part.getSite();
//		if (DLTKUIPlugin.ID_SCRIPTEXPLORER.equals(site.getId())) {
//			IContextService contextService = (IContextService) part.getSite()
//					.getService(IContextService.class);
//			contextService.deactivateContext(m_activatedContext);
//		}
	}

	public void partOpened(IWorkbenchPart part) {
	}
};
