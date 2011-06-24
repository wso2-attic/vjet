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

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IImportDeclaration;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.IHelpContextIds;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceType;
import org.eclipse.dltk.mod.ui.MembersOrderPreferenceCache;
import org.eclipse.dltk.mod.ui.browsing.MembersView;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class in representation of the vjet members view.
 * 
 * 
 * 
 */
public class VjetMembersView extends MembersView {

	@Override
	protected IContentProvider createContentProvider() {
		final ITreeContentProvider original = new VjetBrowsingContentProvider(
				true, this, this.getToolkit());
		return new ITreeContentProvider() {

			public Object[] getChildren(Object parentElement) {
				final Object[] children = original.getChildren(parentElement);
				final List<Object> newChildren = new ArrayList<Object>(children.length);
				for (int i = 0; i < children.length; i++) {
					final Object child = children[i];
					if (child instanceof IField
							|| child instanceof IMethod
							|| child instanceof IPackageDeclaration
							|| child instanceof IImportContainer
							|| child instanceof IImportDeclaration
							|| child instanceof IJSInitializer
							|| child instanceof IJSType) {
						newChildren.add(child);
					}
				}
				return newChildren.toArray();
			}

			public Object getParent(Object element) {
				return original.getParent(element);
			}

			public boolean hasChildren(Object element) {
				// original.getParent(element);
				if (element instanceof IImportContainer) {
					return true;
				}
				return false;
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof VjoSourceType) {
					IModelElement module = ((VjoSourceType) inputElement)
							.getParent();

					if (module instanceof VjoSourceModule) {

						try {
							((VjoSourceModule) module).refreshSourceFields();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
				Object[] children = original.getElements(inputElement);
				final List newChildren = new ArrayList(children.length);
				for (int i = 0; i < children.length; i++) {
					final Object child = children[i];
					if (child instanceof IField
							|| child instanceof IMethod
							|| child instanceof IPackageDeclaration
							|| child instanceof IImportContainer
							|| child instanceof IImportDeclaration
							|| child instanceof IJSInitializer
							|| child instanceof IJSType) {
						newChildren.add(child);
					}
				}
				return newChildren.toArray();
			}

			public void dispose() {
				original.dispose();
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				original.inputChanged(viewer, oldInput, newInput);
			}
		};
	}

	public void propertyChange(PropertyChangeEvent event) {
		if (MembersOrderPreferenceCache.isMemberOrderProperty(event
				.getProperty())) {
			Object oldValue = event.getOldValue();
			Object newValue = event.getNewValue();

			boolean shouldRefresh = false;
			if (oldValue != null) {
				shouldRefresh = !oldValue.equals(newValue);
			} else if (newValue != null) {
				shouldRefresh = !newValue.equals(oldValue);
			}
			if (shouldRefresh) {
				getViewer().refresh();
			}
		}
	}

	// Add by Oliver. 2009-11-02. add the F1 help for vjet members view.
	@Override
	protected String getHelpContextId() {
		return IHelpContextIds.MEMBERS_VIEW;
	}
	
	
	// @Override
	// public void createPartControl(Composite parent) {
	// // TODO Auto-generated method stub
	// super.createPartControl(parent);
	//		
	// // Add by Oliver. 2009-11-02. add the F1 help for vjet members view.
	// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
	// IHelpContextIds.MEMBERS_VIEW);
	// }
}
