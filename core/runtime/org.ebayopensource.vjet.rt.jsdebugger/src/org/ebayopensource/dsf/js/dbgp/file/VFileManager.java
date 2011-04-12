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
import java.util.HashMap;
import java.util.Map;

/**
 * Virtual file manager implementation
 * 
 *  Ouyang
 * 
 */
public class VFileManager implements IVFileManager {

	private Map<URI, IVFile>	m_uri2FileMap	= new HashMap<URI, IVFile>();
	private Map<String, IVFile>	m_name2FileMap	= new HashMap<String, IVFile>();
	private int m_count = 1;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.js.dbgp.file.IVFileManager#addFile(org.ebayopensource.dsf.js.dbgp.
	 * file.IVFile)
	 */
	@Override
	public void addFile(IVFile file) {
		m_uri2FileMap.put(file.toURI(), file);
		m_name2FileMap.put(file.getName(), file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFileManager#clear()
	 */
	@Override
	public void clear() {
		m_uri2FileMap.clear();
		m_name2FileMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.js.dbgp.file.IVFileManager#contains(org.ebayopensource.dsf.js.dbgp
	 * .file.IVFile)
	 */
	@Override
	public boolean contains(IVFile file) {
		return m_name2FileMap.get(file.getName()) != null;
	}

	@Override
	public IVFile createFile(int id, String name, String contents) {
		VFile file = new VFile(m_count++, name, contents, this);
		addFile(file);
		return file;
	}

	@Override
	public IVFile getFileByName(String sourceName) {
		return m_name2FileMap.get(sourceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFileManager#getFiles()
	 */
	@Override
	public IVFile[] getFiles() {
		return m_uri2FileMap.values().toArray(new IVFile[m_uri2FileMap.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.js.dbgp.file.IVFileManager#remove(org.ebayopensource.dsf.js.dbgp.file
	 * .IVFile)
	 */
	@Override
	public IVFile remove(IVFile file) {
		if (!contains(file)) {
			return null;
		}

		m_uri2FileMap.remove(file.toURI());
		m_name2FileMap.remove(file.getName());

		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFileManager#remove(java.net.URI)
	 */
	@Override
	public IVFile remove(URI uri) {
		IVFile file = m_uri2FileMap.get(uri);
		if (file != null) {
			remove(file);
		}
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFileManager#resolve(java.net.URI)
	 */
	@Override
	public IVFile resolve(URI fileUri) {
		return m_uri2FileMap.get(fileUri);
	}

}
