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

/**
 * Virtual file implementation
 * 
 *  Ouyang
 * 
 */
public class VFile implements IVFile {

	private String m_contents;
	private boolean m_deleted;
	private int m_id;
	private IVFileManager m_manager;
	private String m_name;
	private URI m_uri;

	public VFile(int id, String name, String contents, IVFileManager manager) {
		this.m_id = id;
		this.m_name = name;
		this.m_contents = contents;
		this.m_manager = manager;
		this.m_uri = URI.create("dbgp:///temp_" + id + ".js");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#delete()
	 */
	@Override
	public void delete() {
		m_deleted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#getContents()
	 */
	@Override
	public String getContents() {
		return m_contents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#getId()
	 */
	@Override
	public int getId() {
		return m_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#getName()
	 */
	@Override
	public String getName() {
		return m_name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#getVFileManager()
	 */
	@Override
	public IVFileManager getVFileManager() {
		return m_manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#isDeleted()
	 */
	@Override
	public boolean isDeleted() {
		return m_deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.js.dbgp.file.IVFile#setVFileManager(org.ebayopensource.dsf.js.dbgp
	 * .file.IVFileManager)
	 */
	@Override
	public void setVFileManager(IVFileManager manager) {
		m_manager = manager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.file.IVFile#toURI()
	 */
	@Override
	public URI toURI() {
		return m_uri;
	}

}
