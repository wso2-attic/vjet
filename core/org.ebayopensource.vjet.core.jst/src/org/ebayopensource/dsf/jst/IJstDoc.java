/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

/**
 * JST Javadoc style comment
 *
 */
public interface IJstDoc {
	
	/**
	 * Returns the doc comment string, including the starting
	 * and ending comment delimiters, and any embedded line breaks.
	 * @return String
	 */
	String getComment();

}
