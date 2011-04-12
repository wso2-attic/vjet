/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.compiler.CharOperation;
import org.eclipse.dltk.mod.core.IModelStatus;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.dbgp.IDbgpSession;
import org.eclipse.dltk.mod.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.ScriptFolder;
import org.eclipse.dltk.mod.internal.core.VjoExternalSourceModule;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjoDBGPSourceModule extends VjoExternalSourceModule {

	private char[]	m_bufferContent;

	public VjoDBGPSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner, boolean readOnly, IStorage storage) {
		super(parent, name, owner, readOnly, storage);
	}

	/*
	 * @see AbstractSourceModule#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof VjoDBGPSourceModule)) {
			return false;
		}

		if (obj instanceof IStorage) {
			return obj.equals(getStorage());
		}

		return super.equals(obj);
	}

	/*
	 * @see AbstractSourceModule#getBufferContent()
	 */
	public char[] getBufferContent() throws ModelException {
		if (m_bufferContent != null) {
			return m_bufferContent;
		}
		try {
			InputStream contents = getContents();
			char[] cache = new char[contents.available()];
			for (int i = 0; i < cache.length; i++) {
				cache[i] = (char) contents.read();
			}
			m_bufferContent = cache;
			return m_bufferContent;
		} catch (Exception e) {
			VjetPlugin.error(e.getLocalizedMessage(), e);
		}
		return new char[0];
	}

	/*
	 * @see org.eclipse.dltk.mod.compiler.env.IDependent#getFileName()
	 */
	public char[] getFileName() {
		return CharOperation.NO_CHAR;
	}

	/*
	 * @see org.eclipse.dltk.mod.internal.core.AbstractSourceModule#getPath()
	 */
	public IPath getPath() {
		return getStorage().getFullPath();
	}

	public void setDBGPSession(IDbgpSession session) {
		if (getStorage() instanceof VjoDBGPSourceStorage) {
			((VjoDBGPSourceStorage) getStorage()).setSession(session);
		}
	}

	/*
	 * @see AbstractExternalSourceModule#getModuleType()
	 */
	protected String getModuleType() {
		return "VJO Remote Source Module: ";
	}

	/*
	 * @see AbstractSourceModule#getOriginalSourceModule()
	 */
	protected ISourceModule getOriginalSourceModule() {
		return new VjoDBGPSourceModule((ScriptFolder) getParent(),
				getElementName(), DefaultWorkingCopyOwner.PRIMARY,
				isReadOnly(), getStorage());
	}

	protected IStatus validateSourceModule(IResource resource) {
		return IModelStatus.VERIFIED_OK;
	}
}
