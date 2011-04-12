/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

/**
 * Resolve and provide script contents
 * 
 *  Ouyang
 * 
 */
public interface ISourceProvider {

	/**
	 * Get source contents by the given URI, begin line and end line. Or empty
	 * string if cannot get the source contents.
	 * 
	 * @param filePath
	 * @param beginLine
	 * @param endLine
	 * @return
	 */
	public String getSource(String filePath, int beginLine, int endLine);

	/**
	 * 
	 * @return all file paths of URI string format
	 */
	public String[] list();
}
