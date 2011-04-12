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
 * Manager for virtual files.
 * 
 *  Ouyang
 * 
 */
public interface IVFileManager {
	public void addFile(IVFile file);

	public void clear();

	public boolean contains(IVFile file);

	public IVFile createFile(int id,String name, String contents);

	public IVFile getFileByName(String sourceName);

	public IVFile[] getFiles();
	
	public IVFile remove(IVFile file);

	public IVFile remove(URI uri);

	public IVFile resolve(URI fileUri);
}
