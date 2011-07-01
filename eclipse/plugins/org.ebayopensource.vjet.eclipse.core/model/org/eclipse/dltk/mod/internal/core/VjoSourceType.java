/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import org.ebayopensource.dsf.ts.type.TypeName;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ITypeHierarchy;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.core.search.SearchEngine;

public class VjoSourceType extends JSSourceType {

	public VjoSourceType(ModelElement parent, String name) {
		super(parent, name);
	}

	@Override
	public ITypeHierarchy newSupertypeHierarchy(ISourceModule[] workingCopies,
			IProgressMonitor monitor) throws ModelException {
		VjoCreateTypeHierarchyOperation op = new VjoCreateTypeHierarchyOperation(
				this, workingCopies, SearchEngine
						.createWorkspaceScope(DLTKLanguageManager
								.getLanguageToolkit(this)), false);
		op.runOperation(monitor);
		return op.getResult();
	}

	@Override
	public ITypeHierarchy newSupertypeHierarchy(WorkingCopyOwner owner,
			IProgressMonitor monitor) throws ModelException {
		ISourceModule[] workingCopies = ModelManager.getModelManager()
				.getWorkingCopies(owner, true/* add primary working copies */);
		VjoCreateTypeHierarchyOperation op = new VjoCreateTypeHierarchyOperation(
				this, workingCopies, SearchEngine
						.createWorkspaceScope(DLTKLanguageManager
								.getLanguageToolkit(this)), false);
		op.runOperation(monitor);
		return op.getResult();
	}

	@Override
	public ITypeHierarchy newTypeHierarchy(IProgressMonitor monitor)
			throws ModelException {
		VjoCreateTypeHierarchyOperation op = new VjoCreateTypeHierarchyOperation(
				this, null, SearchEngine
						.createWorkspaceScope(DLTKLanguageManager
								.getLanguageToolkit(this)), true);
		op.runOperation(monitor);
		return op.getResult();
	}

	@Override
	public ITypeHierarchy newTypeHierarchy(ISourceModule[] workingCopies,
			IProgressMonitor monitor) throws ModelException {
		VjoCreateTypeHierarchyOperation op = new VjoCreateTypeHierarchyOperation(
				this, workingCopies, SearchEngine
						.createWorkspaceScope(DLTKLanguageManager
								.getLanguageToolkit(this)), true);
		op.runOperation(monitor);
		return op.getResult();
	}

	@Override
	public ITypeHierarchy newTypeHierarchy(WorkingCopyOwner owner,
			IProgressMonitor monitor) throws ModelException {
		ISourceModule[] workingCopies = ModelManager.getModelManager()
				.getWorkingCopies(owner, true/* add primary working copies */);
		VjoCreateTypeHierarchyOperation op = new VjoCreateTypeHierarchyOperation(
				this, workingCopies, SearchEngine
						.createWorkspaceScope(DLTKLanguageManager
								.getLanguageToolkit(this)), true);

		op.runOperation(monitor);
		return op.getResult();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof VjoSourceType)) {
			return false;
		}
		IModelElement oparent = ((VjoSourceType) o).getParent();
		IModelElement parent = getParent();
		if (parent instanceof NativeVjoSourceModule
				|| oparent instanceof NativeVjoSourceModule) {
			if (!(oparent instanceof NativeVjoSourceModule)
					|| !(parent instanceof NativeVjoSourceModule)) {
				return false;
			} else {
				TypeName tname = ((NativeVjoSourceModule) parent).getTypeName();
				TypeName otname = ((NativeVjoSourceModule) oparent)
						.getTypeName();
				return tname.groupName().equals(otname.groupName())
						&& tname.typeName().equals(otname.typeName());
			}
		} else {
			return super.equals(o);
		}
	}
	
	public IType getType(String typeName) {
		return new VjoSourceType(this, typeName);
	}

}
