/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.browsing;

import org.ebayopensource.vjet.eclipse.ui.VjetUIConstants;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

public class JavascriptBrowsingPerspectiveFactory implements
		IPerspectiveFactory {

	/*
	 * XXX: This is a workaround for:
	 * http://dev.eclipse.org/bugs/show_bug.cgi?id=13070
	 */
	static IModelElement fgJavascriptElementFromAction;

	/**
	 * Constructs a new Default layout engine.
	 */
	public JavascriptBrowsingPerspectiveFactory() {
		super();
	}

	public void createInitialLayout(IPageLayout layout) {
		if (stackBrowsingViewsVertically())
			createVerticalLayout(layout);
		else
			createHorizontalLayout(layout);

		// action sets
		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(VjetUIConstants.ID_ACTION_SET);
		
		// Comment by Oliver. 2009-10-31. Because this action set is not
		// existing, throw exceptions.
		// layout.addActionSet(VjetUIConstants.ID_ELEMENT_CREATION_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

		// views - java
		layout.addShowViewShortcut(DLTKUIPlugin.ID_TYPE_HIERARCHY);
		layout.addShowViewShortcut(VjetUIConstants.ID_PROJECTS_VIEW);
		layout.addShowViewShortcut(VjetUIConstants.ID_PACKAGES_VIEW);
		layout.addShowViewShortcut(VjetUIConstants.ID_TYPES_VIEW);
		layout.addShowViewShortcut(VjetUIConstants.ID_MEMBERS_VIEW);

		// Comment by Oliver. 2009-10-31. Because these shortcut are not
		// existing, throw exceptions.
		// layout.addShowViewShortcut(VjetUIConstants.ID_PACKAGES);
		// layout.addShowViewShortcut(VjetUIConstants.ID_SOURCE_VIEW);
		// layout.addShowViewShortcut(VjetUIConstants.ID_JAVADOC_VIEW);

		// views - search
		layout.addShowViewShortcut(NewSearchUI.SEARCH_VIEW_ID);

		// views - debugging
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

		// views - standard workbench
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);

		// new actions - Java project creation wizard
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VJETProjectWizard"); //$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.JsFileCreation"); //$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoPackageCreationWizard"); //$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoClassCreationWizard"); //$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoInterfaceCreationWizard"); //$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoEnumCreationWizard");
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoMixinCreationWizard");
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.VjoOTypeCreationWizard");
		layout
				.addNewWizardShortcut("org.ebayopensource.vjet.eclipse.ui.NewSourceFolderCreationWizard");

		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$
		layout
				.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");//$NON-NLS-1$
	}

	private void createVerticalLayout(IPageLayout layout) {
		String relativePartId = IPageLayout.ID_EDITOR_AREA;
		int relativePos = IPageLayout.LEFT;

		IPlaceholderFolderLayout placeHolderLeft = layout
				.createPlaceholderFolder(
						"left", IPageLayout.LEFT, (float) 0.25, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		placeHolderLeft.addPlaceholder(DLTKUIPlugin.ID_TYPE_HIERARCHY);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_OUTLINE);
		// placeHolderLeft.addPlaceholder(VjetUIConstants.ID_PACKAGES);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_RES_NAV);

		if (shouldShowProjectsView()) {
			layout.addView(VjetUIConstants.ID_PROJECTS_VIEW, IPageLayout.LEFT,
					(float) 0.25, IPageLayout.ID_EDITOR_AREA);
			relativePartId = VjetUIConstants.ID_PROJECTS_VIEW;
			relativePos = IPageLayout.BOTTOM;
		}
		if (shouldShowPackagesView()) {
			layout.addView(VjetUIConstants.ID_PACKAGES_VIEW, relativePos,
					(float) 0.25, relativePartId);
			relativePartId = VjetUIConstants.ID_PACKAGES_VIEW;
			relativePos = IPageLayout.BOTTOM;
		}
		layout.addView(VjetUIConstants.ID_TYPES_VIEW, relativePos,
				(float) 0.33, relativePartId);
		layout.addView(VjetUIConstants.ID_MEMBERS_VIEW, IPageLayout.BOTTOM,
				(float) 0.50, VjetUIConstants.ID_TYPES_VIEW);

		IPlaceholderFolderLayout placeHolderBottom = layout
				.createPlaceholderFolder(
						"bottom", IPageLayout.BOTTOM, (float) 0.75, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		placeHolderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
		placeHolderBottom.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		placeHolderBottom.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		placeHolderBottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		// placeHolderBottom.addPlaceholder(VjetUIConstants.ID_SOURCE_VIEW);
		// placeHolderBottom.addPlaceholder(VjetUIConstants.ID_JAVADOC_VIEW);
		placeHolderBottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
	}

	private void createHorizontalLayout(IPageLayout layout) {
		String relativePartId = IPageLayout.ID_EDITOR_AREA;
		int relativePos = IPageLayout.TOP;

		if (shouldShowProjectsView()) {
			layout.addView(VjetUIConstants.ID_PROJECTS_VIEW, IPageLayout.TOP,
					(float) 0.25, IPageLayout.ID_EDITOR_AREA);
			relativePartId = VjetUIConstants.ID_PROJECTS_VIEW;
			relativePos = IPageLayout.RIGHT;
		}
		if (shouldShowPackagesView()) {
			layout.addView(VjetUIConstants.ID_PACKAGES_VIEW, relativePos,
					(float) 0.25, relativePartId);
			relativePartId = VjetUIConstants.ID_PACKAGES_VIEW;
			relativePos = IPageLayout.RIGHT;
		}
		layout.addView(VjetUIConstants.ID_TYPES_VIEW, relativePos,
				(float) 0.33, relativePartId);
		layout.addView(VjetUIConstants.ID_MEMBERS_VIEW, IPageLayout.RIGHT,
				(float) 0.50, VjetUIConstants.ID_TYPES_VIEW);

		IPlaceholderFolderLayout placeHolderLeft = layout
				.createPlaceholderFolder(
						"left", IPageLayout.LEFT, (float) 0.25, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		placeHolderLeft.addPlaceholder(DLTKUIPlugin.ID_TYPE_HIERARCHY);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_OUTLINE);
		// placeHolderLeft.addPlaceholder(VjetUIConstants.ID_PACKAGES);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_RES_NAV);

		IPlaceholderFolderLayout placeHolderBottom = layout
				.createPlaceholderFolder(
						"bottom", IPageLayout.BOTTOM, (float) 0.75, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		placeHolderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
		placeHolderBottom.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		placeHolderBottom.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		placeHolderBottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		// placeHolderBottom.addPlaceholder(VjetUIConstants.ID_SOURCE_VIEW);
		// placeHolderBottom.addPlaceholder(VjetUIConstants.ID_JAVADOC_VIEW);
		placeHolderBottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
	}

	private boolean shouldShowProjectsView() {
		return fgJavascriptElementFromAction == null
				|| fgJavascriptElementFromAction.getElementType() == IModelElement.SCRIPT_MODEL;
	}

	private boolean shouldShowPackagesView() {
		if (fgJavascriptElementFromAction == null)
			return true;
		int type = fgJavascriptElementFromAction.getElementType();
		return type == IModelElement.SCRIPT_MODEL
				|| type == IModelElement.SCRIPT_PROJECT
				|| type == IModelElement.PROJECT_FRAGMENT;
	}

	private boolean stackBrowsingViewsVertically() {
		return VjetUIPlugin.getDefault().getPreferenceStore().getBoolean(
				VjetUIConstants.BROWSING_STACK_VERTICALLY);
	}

	/*
	 * XXX: This is a workaround for:
	 * http://dev.eclipse.org/bugs/show_bug.cgi?id=13070
	 */
	static void setInputFromAction(IAdaptable input) {
		if (input instanceof IModelElement)
			fgJavascriptElementFromAction = (IModelElement) input;
		else
			fgJavascriptElementFromAction = null;
	}

}