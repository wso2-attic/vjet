/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.util.List;

import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceListener;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelStatus;
import org.eclipse.dltk.mod.core.IModelStatusConstants;
import org.eclipse.dltk.mod.core.IRegion;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ITypeHierarchy;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.internal.core.hierarchy.VjoTypeHierarchy;

/**
 * This operation creates an <code>ITypeHierarchy</code> for a specific type
 * within a specified region, or for all types within a region. The specified
 * region limits the number of resolved subtypes (to the subset of types in the
 * specified region). The resolved supertypes may go outside of the specified
 * region in order to reach the root(s) of the type hierarchy. A Java Project is
 * required to provide a context (classpath) to use while resolving supertypes
 * and subtypes.
 * 
 * @see ITypeHierarchy
 */
public class VjoCreateTypeHierarchyOperation extends ModelOperation implements
		TypeSpaceListener {
	/**
	 * The generated type hierarchy
	 */
	protected VjoTypeHierarchy m_typeHierarchy;

//	private TypeSpaceMgr typeSpaceMgr = TypeSpaceMgr.getInstance();

	/**
	 * Constructs an operation to create a type hierarchy for the given type
	 * within the specified region, in the context of the given project.
	 */
	public VjoCreateTypeHierarchyOperation(IRegion region,
			ISourceModule[] workingCopies, IType element,
			boolean computeSubtypes) {
		super(element);
//		System.err.println("Needs VjoRegionBasedTypeHierarchy");
		/*
		 * this.m_typeHierarchy = new VjoRegionBasedTypeHierarchy(region,
		 * workingCopies, element, computeSubtypes);
		 */
	}

	/**
	 * Constructs an operation to create a type hierarchy for the given type and
	 * working copies.
	 */
	public VjoCreateTypeHierarchyOperation(IType element,
			ISourceModule[] workingCopies, IDLTKSearchScope scope,
			boolean computeSubtypes) {
		super(element);
//		ISourceModule[] copies;
//		if (workingCopies != null) {
//			int length = workingCopies.length;
//			copies = new ISourceModule[length];
//			System.arraycopy(workingCopies, 0, copies, 0, length);
//		} else {
//			copies = null;
//		}
		this.m_typeHierarchy = new VjoTypeHierarchy(element);
		TypeSpaceMgr.getInstance().addTypeSpaceListener(this);
	}

	/**
	 * Constructs an operation to create a type hierarchy for the given type and
	 * working copies.
	 */
	public VjoCreateTypeHierarchyOperation(IType element,
			ISourceModule[] workingCopies, IScriptProject project,
			boolean computeSubtypes) {
		super(element);
		this.m_typeHierarchy = new VjoTypeHierarchy(element);
		TypeSpaceMgr.getInstance().addTypeSpaceListener(this);
	}

	/**
	 * Performs the operation - creates the type hierarchy
	 * 
	 * @exception ModelException
	 *                The operation has failed.
	 */
	@Override
	protected void executeOperation() throws ModelException {
		this.m_typeHierarchy.refresh(this);
	}

	/**
	 * Returns the generated type hierarchy.
	 */
	public ITypeHierarchy getResult() {
		return this.m_typeHierarchy;
	}

	/**
	 * @see ModelOperation
	 */
	@Override
	public boolean isReadOnly() {
		return true;
	}

	/**
	 * Possible failures:
	 * <ul>
	 * <li>NO_ELEMENTS_TO_PROCESS - at least one of a type or region must be
	 * provided to generate a type hierarchy.
	 * <li>ELEMENT_NOT_PRESENT - the provided type or type's project does not
	 * exist
	 * </ul>
	 */
	@Override
	public IModelStatus verify() {
		IModelElement elementToProcess = getElementToProcess();
		
		if (elementToProcess != null && !elementToProcess.exists()) {
			return new ModelStatus(
					IModelStatusConstants.ELEMENT_DOES_NOT_EXIST,
					elementToProcess);
		}
		IScriptProject project = this.m_typeHierarchy.javaProject();
		if (project!=null&&(project.getElementName().equals("Native project")||project instanceof ExternalScriptProject)) {
			return IModelStatus.VERIFIED_OK;
		}else if (project != null && !project.exists()) {
			return new ModelStatus(
					IModelStatusConstants.ELEMENT_DOES_NOT_EXIST, project);
		}
		return IModelStatus.VERIFIED_OK;
	}

	private class VjoTypeSpaceListener implements TypeSpaceListener {

		private IType type;

		private VjoTypeSpaceListener(IType type) {
			super();
			this.type = type;
		}

		public void loadTypesFinished() {

		}

		public void refreshFinished(List<SourceTypeName> list) {
			// nothing
			
		}

	}

	public void loadTypesFinished() {
		this.m_typeHierarchy.fireChanged();
	}

	public void refreshFinished(List<SourceTypeName> list) {
		this.m_typeHierarchy.fireChanged();		
	}
}
