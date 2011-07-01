/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.actions;

import org.ebayopensource.vjet.eclipse.ui.VjetUIConstants;
import org.ebayopensource.vjet.eclipse.ui.actions.VjoOpenTypeAction;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jdt.internal.ui.actions.OpenTypeAction;
import org.eclipse.jdt.ui.JavaUI;

public class VjoOpenTypeHandler extends AbstractHandler {

	private VjoOpenTypeAction action = new VjoOpenTypeAction();
	private OpenTypeAction jdtAction = new OpenTypeAction();
	private static final String[] VJETUIElementID = new String[] {
			VjetUIConstants.ID_AST, VjetUIConstants.ID_CALLHIERARCHY,
			VjetUIConstants.ID_JAVADOC_VIEW, VjetUIConstants.ID_MEMBERS_VIEW,
			VjetUIConstants.ID_PACKAGES, VjetUIConstants.ID_PACKAGES_VIEW,
			VjetUIConstants.ID_PROJECTS_VIEW,
			VjetUIConstants.ID_SCRIPTEXPLORER, VjetUIConstants.ID_SCRIPTUNIT,
			VjetUIConstants.ID_SOURCE_VIEW, VjetUIConstants.ID_TYPES_VIEW,
			VjetUIConstants.ID_TYPESPACE, VjetUIConstants.ID_VJOEDITOR };
	private static final String[] ViewIDDependOnEditor = new String[] {
			"org.eclipse.ui.views.PropertySheet",
			"org.eclipse.ui.views.ContentOutline" };
	private static final String[] JavaUIElementID = new String[] {
			JavaUI.ID_JAVADOC_VIEW, JavaUI.ID_MEMBERS_VIEW,
			JavaUI.ID_PACKAGES_VIEW, JavaUI.ID_PROJECTS_VIEW,
			JavaUI.ID_SOURCE_VIEW, JavaUI.ID_TYPES_VIEW, JavaUI.ID_CF_EDITOR,
			JavaUI.ID_CU_EDITOR, JavaUI.ID_PACKAGES, JavaUI.ID_TYPE_HIERARCHY };

	public VjoOpenTypeHandler() {
		System.out.println();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object object = event.getApplicationContext();
		if (object instanceof IEvaluationContext) {
			IEvaluationContext appContext = (IEvaluationContext) object;
			Object obj = appContext.getVariable("activePartId");
			if (obj == null || !(obj instanceof String)) {
				return null;
			} else {
				String activePartId = (String) obj;
				if (isJavaUIElement(activePartId)) {
					jdtAction.run();
				} else if (isViewDependOnEditor(activePartId)) {
					Object obj1 = appContext.getVariable("activeEditorId");
					if (obj1 != null && (obj1 instanceof String)) {
						String activeEditorId = (String) obj1;
						if (isJavaUIElement(activeEditorId)) {
							jdtAction.run();
						} else {
							action.run();
						}
					}
				} else if (isVjoUIElement(activePartId)) {
					action.run();
				}
			}
		}
		return null;
	}

	private boolean checkArray(String string, String[] sArray) {
		for (String id : sArray) {
			if (id.equals(string)) {
				return true;
			}
		}
		return false;

	}

	private boolean printID() {
		for (String id : VJETUIElementID) {
			System.out.println("<equals " + "value=\"" + id + "\"/equals>");
		}
		for (String id : JavaUIElementID) {
			System.out.println("<equals " + "value=\"" + id + "\"/equals>");
		}
		for (String id : ViewIDDependOnEditor) {
			System.out.println("<equals " + "value=\"" + id + "\"/equals>");
		}
		return false;

	}

	private boolean isJavaUIElement(String activePartId) {
		return checkArray(activePartId, JavaUIElementID);
	}

	private boolean isVjoUIElement(String activePartId) {
		return checkArray(activePartId, VJETUIElementID);
	}

	private boolean isViewDependOnEditor(String activePartId) {
		return checkArray(activePartId, ViewIDDependOnEditor);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
