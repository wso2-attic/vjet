/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp.file;

import java.net.URI;

import org.ebayopensource.dsf.js.dbgp.ISourceProvider;
import org.ebayopensource.dsf.js.dbgp.SourceProvider;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VFileSourceProvider extends SourceProvider implements
		ISourceProvider {

	private IVFileManager	m_manager;

	public VFileSourceProvider(IVFileManager manager) {
		this.m_manager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.ISourceProvider#getSource(java.net.URI, int,
	 * int)
	 */
	@Override
	public String getSource(String filePath, int beginLine, int endLine) {
		if (filePath == null) {
			throw new IllegalArgumentException("File path shouldn't be null!");
		}

		URI fileUri = URI.create(filePath);
		IVFile file = m_manager.resolve(fileUri);
		String contents = file != null ? file.getContents() : "";
		return getContents(contents, beginLine, endLine);
	}

	@Override
	public String[] list() {
		IVFile[] files = m_manager.getFiles();
		String[] uris = new String[files.length];
		for (int i = 0; i < uris.length; i++) {
			uris[i] = files[i].toURI().toString();
		}
		return uris;
	}
}
