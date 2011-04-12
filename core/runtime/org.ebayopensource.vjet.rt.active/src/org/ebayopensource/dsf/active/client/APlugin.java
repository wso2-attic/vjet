/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.jsnative.MimeType;
import org.ebayopensource.dsf.jsnative.Plugin;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class APlugin extends ActiveObject  implements Plugin {

	private static final long serialVersionUID = 1L;
	
	public APlugin(BrowserType browserType) {
		populateScriptable(APlugin.class, browserType);
	}
	
	// Description of the plugin
	private String m_description;
	// File name of the plugin itself
	private String m_filename;
	// Name of the pugin
	private String m_name;
	
	// Array elements that specify the data formats supported by the plugin.
	private MimeType[] m_mimeTypes = new MimeType[0];

	public void setDescription(String description) {
		m_description = description;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	public void setName(String name){
		m_name = name;
	}

	public String getName() {
		return m_name;
	}

	public String getFilename() {
		return m_filename;
	}

	public int getLength() {
		return m_mimeTypes.length;
	}

	public MimeType[] getMimeTypes() {
		return m_mimeTypes;
	}

}
