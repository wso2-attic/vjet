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
import java.util.Arrays;

import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IParent;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.browsing.ScriptBrowsingContentProvider;
import org.eclipse.dltk.mod.ui.browsing.ScriptBrowsingPart;

/**
 * A base content provider for VJET elements. It provides access to the VJET
 * element hierarchy without listening to changes in the VJET model.
 * 
 * 
 * 
 */
public class VjetBrowsingContentProvider extends ScriptBrowsingContentProvider {

	/**
	 * @param provideMembers
	 * @param browsingPart
	 * @param languageToolkit
	 */
	public VjetBrowsingContentProvider(boolean provideMembers,
			ScriptBrowsingPart browsingPart,
			IDLTKLanguageToolkit languageToolkit) {
		super(provideMembers, browsingPart, languageToolkit);
	}

	@Override
	protected Object[] removeImportAndPackageDeclarations(Object[] members) {
		ArrayList tempResult = new ArrayList(members.length);
		for (int i = 0; i < members.length; i++)
			if (!(members[i] instanceof IImportContainer)
					&& !(members[i] instanceof IPackageDeclaration))
				tempResult.add(members[i]);
		return tempResult.toArray();
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IMethod) {
			return false;
		}
		return super.hasChildren(element);
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof IMethod) {
			return NO_CHILDREN;
		}
		return super.getChildren(element);
	}

	protected Object[] getChildren(IType type) throws ModelException {
		IParent parent = type.getSourceModule();

		if (type.getDeclaringType() != null) {
			return type.getChildren();
		}

		// Add import declarations
		IModelElement[] members = parent.getChildren();
		ArrayList tempResult = new ArrayList(members.length);
		for (int i = 0; i < members.length; i++) {
			if ((members[i] instanceof IImportContainer)) {
				tempResult.add(members[i]);
			}
		}
		tempResult.addAll(Arrays.asList(type.getChildren()));
		return tempResult.toArray();
	}
}
