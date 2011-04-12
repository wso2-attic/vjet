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

public class AMimeType extends ActiveObject  implements MimeType {

	private static final long serialVersionUID = 1L;
	
	public AMimeType(BrowserType browserType) {
		populateScriptable(AMimeType.class, browserType);
	}
	
	// Description of the MIME type
	private String m_description;
	// Reference to the Plugin object that is configured for the MIME type
	private Plugin m_enabledPlugin;
	// String listing possible file extensions for the MIME type, separated by commas
	private String m_suffixes;
	// Name of the MIME type
	private String m_type;

	public String getDescription() {
		return m_description;
	}
	public Plugin getEnabledPlugin() {
		return m_enabledPlugin;
	}
	public String getSuffixes() {
		return m_suffixes;
	}
	public String getType() {
		return m_type;
	}

}
