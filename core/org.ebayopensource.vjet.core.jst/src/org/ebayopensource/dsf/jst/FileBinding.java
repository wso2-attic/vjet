/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.io.File;
import java.io.IOException;

import org.ebayopensource.dsf.jst.JstSource.IBinding;
import org.ebayopensource.dsf.common.FileUtils;

public class FileBinding implements IBinding{

	private File m_file;
	private String m_text;

	public FileBinding(File file) {
		m_file = file;
	}
	
	public String getName() {
		if (m_file == null) return null;//Bug 2913
		
		return m_file.getAbsolutePath();
	}

	public String toText() {
		if (m_text == null) {
			try {
				m_text = FileUtils.readFile(m_file.getAbsolutePath(), "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();	//KEEPME
			}
		}
		return m_text;
	}
	
	public File getFile(){
		return m_file;
	}

}
