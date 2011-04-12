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
 * Virtual file for generated scripts.
 * 
 *  Ouyang
 * 
 */
public interface IVFile {
	public int getId();
	
	public String getContents();

	public URI toURI();

	public void delete();

	public String getName();

	public boolean isDeleted();

	public IVFileManager getVFileManager();

	public void setVFileManager(IVFileManager manager);
}
