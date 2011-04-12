/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.ebayopensource.vjet.eclipse.internal.ui.filters;

import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.ui.viewsupport.ModelElementFlagsFilter;
import org.eclipse.jface.viewers.Viewer;

/**
 * Statics filter.
 * 
 * @since 3.0
 */
public class StaticsFilter extends ModelElementFlagsFilter {
	
	
	public StaticsFilter() {
		super(ModelElementFlagsFilter.FILTER_STATIC);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IMethod || element instanceof IField) {
			return super.select(viewer, parentElement, element);

		}

		return true;
	}
}
