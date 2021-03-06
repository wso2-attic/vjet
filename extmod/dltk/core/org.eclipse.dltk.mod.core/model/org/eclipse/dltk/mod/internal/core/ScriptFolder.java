/*******************************************************************************
 * Copyright (c) 2000-2011 IBM Corporation and others, eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceModuleFactory;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.util.MementoTokenizer;
import org.eclipse.dltk.mod.internal.core.util.Messages;
import org.eclipse.dltk.mod.internal.core.util.Util;
import org.eclipse.dltk.mod.utils.CorePrinter;

public class ScriptFolder extends Openable implements IScriptFolder {

	protected IPath path;

	protected ScriptFolder(ProjectFragment parent, IPath path) {
		super(parent);
		this.path = path;
	}

	/**
	 * @see ModelElement
	 */
	protected Object createElementInfo() {
		return new ScriptFolderInfo();
	}

	public int getElementType() {
		return SCRIPT_FOLDER;
	}

	/**
	 * @see IModelElement#getPath()
	 */
	public IPath getPath() {
		ProjectFragment root = this.getProjectFragment();
		if (root.isArchive()) {
			// Ebay MOD to support js file in jar
			return new Path(root.getPath().toPortableString() + "!")
					.append(path);
			// Ebay MOD
		} else {
			return root.getPath().append(path);
		}
	}

	/**
	 * @see IModelElement#getResource()
	 */
	public IResource getResource() {
		ProjectFragment root = this.getProjectFragment();
		if (root.isArchive()) {
			return root.getResource();
		} else {
			if (path.segmentCount() == 0)
				return root.getResource();
			IContainer container = (IContainer) root.getResource();
			if (container != null) {
				return container.getFolder(path);
			}
			return null;
		}
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ScriptFolder))
			return false;

		ScriptFolder other = (ScriptFolder) o;
		return this.path.equals(other.path) && this.parent.equals(other.parent);
	}

	public int hashCode() {
		return Util.combineHashCodes(parent.hashCode(), path.hashCode());
	}

	public int getKind() throws ModelException {
		return getProjectFragment().getKind();
	}

	protected boolean buildStructure(OpenableElementInfo info,
			IProgressMonitor pm, Map newElements, IResource underlyingResource)
			throws ModelException {
		// check whether this folder can be opened
		if (!underlyingResource.isAccessible())
			throw newNotPresentException();

		int kind = getKind();

		if (kind == IProjectFragment.K_SOURCE && Util.isExcluded(this))
			throw newNotPresentException();

		// add modules from resources
		HashSet vChildren = new HashSet();
		try {
			ProjectFragment root = getProjectFragment();
			char[][] inclusionPatterns = root.fullInclusionPatternChars();
			char[][] exclusionPatterns = root.fullExclusionPatternChars();
			IResource[] members = ((IContainer) underlyingResource).members();
			for (int i = 0, max = members.length; i < max; i++) {
				IResource child = members[i];
				if (child.getType() != IResource.FOLDER
						&& !Util.isExcluded(child, inclusionPatterns,
								exclusionPatterns)) {
					IModelElement childElement;
					if (kind == IProjectFragment.K_SOURCE
							&& Util.isValidSourceModule(this, child)) {
						childElement = getSourceModule(child.getName());
						vChildren.add(childElement);
					}
				}
			}
		} catch (CoreException e) {
			throw new ModelException(e);
		}

		if (kind == IProjectFragment.K_SOURCE) {
			// add primary source modules
			ISourceModule[] primarySourceModules = getSourceModules(DefaultWorkingCopyOwner.PRIMARY);
			for (int i = 0, length = primarySourceModules.length; i < length; i++) {
				ISourceModule primary = primarySourceModules[i];
				vChildren.add(primary);
			}
		}

		IModelElement[] children = new IModelElement[vChildren.size()];
		vChildren.toArray(children);
		info.setChildren(children);
		return true;
	}

	public ISourceModule[] getSourceModules(WorkingCopyOwner owner) {
		ISourceModule[] workingCopies = ModelManager.getModelManager()
				.getWorkingCopies(owner, false/* don't add primary */);
		if (workingCopies == null)
			return ModelManager.NO_WORKING_COPY;
		int length = workingCopies.length;
		ISourceModule[] result = new ISourceModule[length];
		int index = 0;
		for (int i = 0; i < length; i++) {
			ISourceModule wc = workingCopies[i];
			IResource res = wc.getResource();
			boolean valid;
			if (res != null)
				valid = Util.isValidSourceModule(this, res);
			else
				valid = Util.isValidSourceModule(this, wc.getPath());
			if (equals(wc.getParent()) && !Util.isExcluded(wc) && valid) {
				result[index++] = wc;
			}
		}
		if (index != length) {
			System.arraycopy(result, 0, result = new ISourceModule[index], 0,
					index);
		}
		return result;
	}

	public ISourceModule getSourceModule(String name) {
		// EBAY - START MOD
		// return new SourceModule(this, name, DefaultWorkingCopyOwner.PRIMARY);
		return createSourceModule(name, DefaultWorkingCopyOwner.PRIMARY);
		// EBAY -- STOP MOD
	}

	/**
	 * @see IScriptFolder
	 */
	public ISourceModule createSourceModule(String cuName, String contents,
			boolean force, IProgressMonitor monitor) throws ModelException {
		CreateSourceModuleOperation op = new CreateSourceModuleOperation(this,
				cuName, contents, force);
		op.runOperation(monitor);
		// EBAY - START MOD
		// return new SourceModule(this, cuName,
		// DefaultWorkingCopyOwner.PRIMARY);
		return createSourceModule(cuName, DefaultWorkingCopyOwner.PRIMARY);
		// EBAY -- STOP MOD
	}

	public final ProjectFragment getProjectFragment() {
		return (ProjectFragment) getParent();
	}

	/**
	 * Debugging purposes
	 */
	protected void toStringName(StringBuffer buffer) {
		String elementName = getElementName();
		if (elementName.length() == 0) {
			buffer.append("<default>"); //$NON-NLS-1$
		} else {
			buffer.append(elementName);
		}
	}

	public String getElementName() {
		String name = ""; //$NON-NLS-1$
		if (this.path.segmentCount() == 0) {
			return ""; //$NON-NLS-1$
		}
		name = this.path.segment(0);
		for (int i = 1; i < this.path.segmentCount(); ++i) {
			name += PACKAGE_DELIMITER + this.path.segment(i);
		}
		return name;
	}

	public boolean isRootFolder() {
		return path.segmentCount() == 0;
	}

	public void printNode(CorePrinter output) {
		output.formatPrint("DLTK Script folder:" + getElementName()); //$NON-NLS-1$
		output.indent();
		try {
			IModelElement modelElements[] = this.getChildren();
			for (int i = 0; i < modelElements.length; ++i) {
				IModelElement element = modelElements[i];
				if (element instanceof ModelElement) {
					((ModelElement) element).printNode(output);
				} else {
					output.print("Unknown element:" + element); //$NON-NLS-1$
				}
			}
		} catch (ModelException ex) {
			output.formatPrint(ex.getLocalizedMessage());
		}
		output.dedent();
	}

	public ISourceModule[] getSourceModules() throws ModelException {
		ArrayList list = getChildrenOfType(SOURCE_MODULE);
		ISourceModule[] array = new ISourceModule[list.size()];
		list.toArray(array);
		return array;
	}

	public Object[] getForeignResources() throws ModelException {
		if (this.isRootFolder()) {
			return ModelElementInfo.NO_NON_SCRIPT_RESOURCES;
		} else {
			return ((ScriptFolderInfo) getElementInfo()).getForeignResources(
					getResource(), getProjectFragment());
		}
	}

	public boolean hasSubfolders() throws ModelException {
		IModelElement[] packages = ((IProjectFragment) getParent())
				.getChildren();
		int namesLength = this.path.segmentCount();
		nextPackage: for (int i = 0, length = packages.length; i < length; i++) {
			IPath otherNames = ((ScriptFolder) packages[i]).path;
			if (otherNames.segmentCount() <= namesLength)
				continue nextPackage;
			for (int j = 0; j < namesLength; j++)
				if (!this.path.segment(j).equals(otherNames.segment(j)))
					continue nextPackage;
			return true;
		}
		return false;
	}

	public IModelElement getHandleFromMemento(String token,
			MementoTokenizer memento, WorkingCopyOwner owner) {
		switch (token.charAt(0)) {
		case JEM_SOURCEMODULE:
			if (!memento.hasMoreTokens())
				return this;
			String classFileName = memento.nextToken();
			ModelElement classFile = (ModelElement) getSourceModule(classFileName);
			return classFile.getHandleFromMemento(memento, owner);
		}
		return null;
	}

	protected char getHandleMementoDelimiter() {
		return JEM_SCRIPTFOLDER;
	}

	public boolean containsScriptResources() throws ModelException {
		Object elementInfo = getElementInfo();
		if (!(elementInfo instanceof ScriptFolderInfo))
			return false;
		ScriptFolderInfo scriptElementInfo = (ScriptFolderInfo) elementInfo;
		return scriptElementInfo.containsScriptResources();
	}

	public boolean hasChildren() throws ModelException {
		return getChildren().length > 0;
	}

	public void copy(IModelElement container, IModelElement sibling,
			String rename, boolean replace, IProgressMonitor monitor)
			throws ModelException {
		if (container == null) {
			throw new IllegalArgumentException(Messages.operation_nullContainer);
		}
		IModelElement[] elements = new IModelElement[] { this };
		IModelElement[] containers = new IModelElement[] { container };
		IModelElement[] siblings = null;
		if (sibling != null) {
			siblings = new IModelElement[] { sibling };
		}
		String[] renamings = null;
		if (rename != null) {
			renamings = new String[] { rename };
		}
		getModel().copy(elements, containers, siblings, renamings, replace,
				monitor);
	}

	public void delete(boolean force, IProgressMonitor monitor)
			throws ModelException {
		IModelElement[] elements = new IModelElement[] { this };
		getModel().delete(elements, force, monitor);
	}

	public void move(IModelElement container, IModelElement sibling,
			String rename, boolean replace, IProgressMonitor monitor)
			throws ModelException {
		if (container == null) {
			throw new IllegalArgumentException(Messages.operation_nullContainer);
		}
		IModelElement[] elements = new IModelElement[] { this };
		IModelElement[] containers = new IModelElement[] { container };
		IModelElement[] siblings = null;
		if (sibling != null) {
			siblings = new IModelElement[] { sibling };
		}
		String[] renamings = null;
		if (rename != null) {
			renamings = new String[] { rename };
		}
		getModel().move(elements, containers, siblings, renamings, replace,
				monitor);
	}

	public void rename(String newName, boolean force, IProgressMonitor monitor)
			throws ModelException {
		if (newName == null) {
			throw new IllegalArgumentException(Messages.element_nullName);
		}
		IModelElement[] elements = new IModelElement[] { this };
		IModelElement[] dests = new IModelElement[] { this.getParent() };
		String[] renamings = new String[] { newName };
		getModel().rename(elements, dests, renamings, force, monitor);
	}

	public IPath getRelativePath() {
		return this.path;
	}

	// EBAY - START MOD
	public ISourceModule createSourceModule(String name, WorkingCopyOwner owner) {
		ISourceModuleFactory factory = getSourceModuleFactory(name);

		ISourceModule sourceModule;
		if (factory != null) {
			sourceModule = factory.createSourceModule(this, name, owner);
		} else {
			sourceModule = new SourceModule(this, name, owner);
		}
		return sourceModule;
	}

	protected ISourceModuleFactory getSourceModuleFactory(String moduleName) {
		IPath modulePath = path.append(moduleName);
		IDLTKLanguageToolkit toolkit = DLTKLanguageManager
				.findToolkit(modulePath);
		ISourceModuleFactory factory = null;
		if (toolkit != null) {
			String natureId = toolkit.getNatureId();
			factory = DLTKLanguageManager.getSourceModuleFactory(natureId);
		}
		return factory;
	}

	/**
	 * @see IModelElement#getUnderlyingResource()
	 */
	public IResource getUnderlyingResource() throws ModelException {
		IResource rootResource = this.parent.getUnderlyingResource();
		if (rootResource == null) {
			// jar package fragment root that has no associated resource
			return null;
		}
		// the underlying resource may be a folder or a project (in the case
		// that the project folder
		// is atually the package fragment root)
		if (rootResource.getType() == IResource.FOLDER
				|| rootResource.getType() == IResource.PROJECT) {
			IContainer folder = (IContainer) rootResource;
			String[] segs = this.path.segments();
			for (int i = 0; i < segs.length; ++i) {
				IResource child = folder.findMember(segs[i]);
				if (child == null || child.getType() != IResource.FOLDER) {
					throw newNotPresentException();
				}
				folder = (IFolder) child;
			}
			return folder;
		} else {
			return rootResource;
		}
	}

	// EBAY -- STOP MOD
}
