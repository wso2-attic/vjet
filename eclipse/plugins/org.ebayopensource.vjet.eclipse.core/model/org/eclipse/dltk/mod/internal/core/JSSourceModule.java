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
package org.eclipse.dltk.mod.internal.core;

import java.util.Map;

import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuffer;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProblemRequestor;
import org.eclipse.dltk.mod.core.ISourceElementParser;
import org.eclipse.dltk.mod.core.ISourceElementParserExtension;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.SourceParserUtil;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.util.Messages;
import org.eclipse.dltk.mod.utils.CorePrinter;

/**
 * 
 * 
 */
public class JSSourceModule extends SourceModule implements IJSSourceModule {

	/**
	 * @param parent
	 * @param name
	 * @param owner
	 */
	public JSSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner) {
		super(parent, name, owner);
		//System.out.println("JSSourceModule created for type :" + name + " in parent :" + parent);
	}

	@Override
	protected boolean buildStructure(OpenableElementInfo info,
			IProgressMonitor pm, Map newElements, IResource underlyingResource)
			throws ModelException {
		try {
			// check if this source module can be opened
			if (!isWorkingCopy()) {
				// no check is done on root kind or
				// exclusion pattern for working copies
				final IStatus status = validateSourceModule(underlyingResource);
				if (!status.isOK()) {
					throw newModelException(status);
				}
			}
			// prevents reopening of non-primary working copies (they are closed
			// when they are discarded and should not be reopened)
			if (preventReopen()) {
				throw newNotPresentException();
			}

			final JSSourceModuleElementInfo moduleInfo = (JSSourceModuleElementInfo) info;

			// ensure buffer is opened
			if (hasBuffer()) {
				final IBuffer buffer = getBufferManager().getBuffer(this);
				if (buffer == null) {
					openBuffer(pm, moduleInfo);
				}
			}

			// generate structure and compute syntax problems if needed
			final VjoSourceModuleStructureRequestor requestor = new VjoSourceModuleStructureRequestor(
					this, moduleInfo, newElements);

			// System.out.println("==> Parsing: " + resource.getName());
			final String natureId = getNatureId();
			if (natureId == null) {
				throw new ModelException(new ModelStatus(
						ModelStatus.INVALID_NAME));
			}

			final ISourceElementParser parser = getSourceElementParser(natureId);
			if (!isReadOnly()) {
				if (parser instanceof ISourceElementParserExtension) {
					((ISourceElementParserExtension) parser)
							.setScriptProject(this.getScriptProject());
				}
			}

			parser.setRequestor(requestor);

			final AccumulatingProblemReporter problemReporter = getAccumulatingProblemReporter();
			parser.setReporter(problemReporter);

			SourceParserUtil.parseSourceModule(this, parser);
			if (problemReporter != null) {
				if (!problemReporter.hasErrors()) {
					StructureBuilder.build(natureId, this, problemReporter);
				}
				problemReporter.reportToRequestor();
			}

			if (DLTKCore.DEBUG_PRINT_MODEL) {
				System.out.println("Source Module Debug print:"); //$NON-NLS-1$

				CorePrinter printer = new CorePrinter(System.out);
				printNode(printer);
				printer.flush();
			}
			// update timestamp (might be IResource.NULL_STAMP if original does
			// not exist)
			if (underlyingResource == null) {
				underlyingResource = getResource();
			}
			// underlying resource is null in the case of a working copy out of
			// workspace
			if (underlyingResource != null) {
				moduleInfo.setTimestamp(((IFile) underlyingResource)
						.getModificationStamp());
			}

			return moduleInfo.isStructureKnown();
		} catch (CoreException e) {
			throw new ModelException(e);
		}
	}

	@Override
	protected Object createElementInfo() {
		return new JSSourceModuleElementInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.IJSSourceModule#getImportContainer()
	 */
	public IImportContainer getImportContainer() {
		return new ImportContainer(this);
	}

	@Override
	protected ISourceModule getOriginalSourceModule() {
		return new JSSourceModule((ScriptFolder) getParent(), getElementName(),
				DefaultWorkingCopyOwner.PRIMARY);
	}

	@Override
	public ISourceModule getWorkingCopy(WorkingCopyOwner workingCopyOwner,
			IProblemRequestor problemRequestor, IProgressMonitor monitor)
			throws ModelException {
		if (!isPrimary()) {
			return this;
		}

		ModelManager manager = ModelManager.getModelManager();

		SourceModule workingCopy = new JSSourceModule(
				(ScriptFolder) getParent(), getElementName(), workingCopyOwner);
		ModelManager.PerWorkingCopyInfo perWorkingCopyInfo = manager
				.getPerWorkingCopyInfo(workingCopy, false /* don't create */,
						true /* record usage */, null /*
														 * not used since don't
														 * create
														 */);
		if (perWorkingCopyInfo != null) {
			return perWorkingCopyInfo.getWorkingCopy(); // return existing
			// handle instead of the
			// one
			// created above
		}

		BecomeWorkingCopyOperation op = new BecomeWorkingCopyOperation(
				workingCopy, problemRequestor);
		op.runOperation(monitor);
		return workingCopy;
	}

	@Override
	public IType getType(String typeName) {
		return new JSSourceType(this, typeName);
	}

	@Override
	public void rename(String newName, boolean replace, IProgressMonitor monitor) throws ModelException {
		//jsSourceModule rename should rename its typeName
		if (newName == null) {
			throw new IllegalArgumentException(Messages.operation_nullName);
		}

		IModelElement[] elements = new IModelElement[] { this };
		IModelElement[] dests = new IModelElement[] { this.getParent() };
		String[] renamings = new String[] { newName };
//		getModel().rename(elements, dests, renamings, replace, monitor);
		
		MultiOperation op;
		if (elements != null && elements.length > 0 && elements[0] != null
				&& elements[0].getElementType() < IModelElement.TYPE) {
			op = new VjetRenameResourceElementsOperation(elements, dests,
					renamings, replace);
		} else {
			op = new RenameElementsOperation(elements, dests, renamings,
					replace);
		}

		op.runOperation(monitor);
		
	}

	
	
	
	
}
