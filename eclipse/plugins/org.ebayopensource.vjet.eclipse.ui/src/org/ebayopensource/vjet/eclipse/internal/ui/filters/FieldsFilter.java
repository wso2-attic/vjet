/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.filters;

import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.ui.viewsupport.ModelElementFilter;

public class FieldsFilter extends ModelElementFilter {

	public FieldsFilter() {
		super(IModelElement.FIELD);
	}

}
