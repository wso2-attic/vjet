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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuffer;
import org.eclipse.dltk.mod.core.IModelStatusConstants;
import org.eclipse.dltk.mod.core.ISourceElementParser;
import org.eclipse.dltk.mod.core.ISourceElementParserExtension;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.SourceParserUtil;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.mod.core.environment.IFileHandle;
import org.eclipse.dltk.mod.utils.CorePrinter;

/**
 * 
 * 
 */
public class ExternalJSSourceModule extends ExternalSourceModule implements
		IJSSourceModule {
	private IStorage m_storage;

	/**
	 * @param parent
	 * @param name
	 * @param owner
	 * @param readOnly
	 * @param storage
	 */
	public ExternalJSSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner, boolean readOnly, IStorage storage) {
		super(parent, name, owner, readOnly, storage);
		this.m_storage = storage;
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
						IModelStatusConstants.INVALID_NAME));
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
				System.out.println("Source Module Debug print:");

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
		return new ExternalJSSourceModule((ScriptFolder) getParent(),
				getElementName(), DefaultWorkingCopyOwner.PRIMARY, true,
				m_storage);
	}

	@Override
	public char[] getBufferContent() throws ModelException {
		IFileHandle file = EnvironmentPathUtils.getFile(getPath());
		InputStream stream = null;
		ProjectFragment projectFragment = this.getProjectFragment();
		boolean inProjectArchive = projectFragment.isArchive();

		try {
			try {
				//Here to support fetch file content from jar
				if (file != null && file.exists() && !inProjectArchive) {
					stream = new BufferedInputStream(file.openInputStream(null));
				} else {
					// This is an archive entry
					stream = new BufferedInputStream(m_storage.getContents());
				}

			} catch (IOException e) {
				throw new ModelException(e,
						IModelStatusConstants.ELEMENT_DOES_NOT_EXIST);
			} catch (CoreException e) {
				throw new ModelException(e,
						IModelStatusConstants.ELEMENT_DOES_NOT_EXIST);
			}

			char[] content;
			content = org.eclipse.dltk.mod.compiler.util.Util
					.getInputStreamAsCharArray(stream, -1, "utf-8"); //$NON-NLS-1$
			return content;
		} catch (IOException e) {
			throw new ModelException(e, IModelStatusConstants.IO_EXCEPTION);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	/**
	 * @return m_storage
	 */
	protected IStorage getStorage() {
		return m_storage;
	}
}
