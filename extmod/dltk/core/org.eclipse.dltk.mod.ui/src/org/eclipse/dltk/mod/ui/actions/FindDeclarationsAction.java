/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.ui.actions;

import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.internal.ui.search.SearchMessages;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.ui.IWorkbenchSite;


/**
 * Finds declarations of the selected element in the workspace.
 * The action is applicable to selections representing a Script element.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
	 *
 */
public abstract class FindDeclarationsAction extends FindAction {
	
	/**
	 * Creates a new <code>FindDeclarationsAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public FindDeclarationsAction(IWorkbenchSite site) {
		super(site);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param editor the Script editor
	 */
	public FindDeclarationsAction(ScriptEditor editor) {
		super(editor);
	}
	
	void init() {
		setText(SearchMessages.Search_FindDeclarationAction_label); 
		setToolTipText(SearchMessages.Search_FindDeclarationAction_tooltip); 
		setImageDescriptor(DLTKPluginImages.DESC_OBJS_SEARCH_DECL);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.FIND_DECLARATIONS_IN_WORKSPACE_ACTION);
		if (DLTKCore.DEBUG) {
			System.out.println("TODO: Add jelp support here..."); //$NON-NLS-1$
		}
	}
	
	Class[] getValidTypes() {
		return new Class[] { IField.class, IMethod.class, IType.class, ISourceModule.class, IPackageDeclaration.class, IScriptFolder.class};
	}
	
	int getLimitTo() {
		return IDLTKSearchConstants.DECLARATIONS | IDLTKSearchConstants.IGNORE_DECLARING_TYPE | IDLTKSearchConstants.IGNORE_RETURN_TYPE;
	}
	
}
