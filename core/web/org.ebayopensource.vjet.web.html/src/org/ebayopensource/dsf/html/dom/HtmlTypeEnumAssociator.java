/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

/** Internal methods, only for use within this library.  Any use
 * by code above the library will result in a P1 bug being filed.  By
 * using these methods one agrees to the terms.
 */
public class HtmlTypeEnumAssociator {
	private HtmlTypeEnumAssociator(){}
	/** pass through to package friendly HtmlTypeEnum.getNameChars() **/
	public static char [] getNameCharArray(final HtmlTypeEnum type){
		return type.getNameChars();
	}
}
