/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.dltk.mod.internal.core;

import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.core.IJSField;

/**
 * 
 * 
 */
public class JSSourceField extends SourceField implements IJSField {

	/**
	 * @param parent
	 * @param name
	 */
	public JSSourceField(ModelElement parent, String name) {
		super(parent, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.IJSField#getConstant()
	 */
	public Object getConstant() throws ModelException {
		Object constant = null;
		JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) getElementInfo();
		final String constantSourceChars = info.getInitializationSource();
		if (constantSourceChars == null) {
			return null;
		}

		constant = constantSourceChars;
		return constant;
	}
}
