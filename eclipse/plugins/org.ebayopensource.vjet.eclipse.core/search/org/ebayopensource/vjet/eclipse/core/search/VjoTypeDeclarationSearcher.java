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
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.List;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.search.matching.TypeDeclarationPattern;

/**
 * 
 * 
 */
public class VjoTypeDeclarationSearcher extends AbstractVjoElementSearcher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.ui.VjoElementSearcher#getSearchPatternClass()
	 */
	public Class<? extends SearchPattern> getSearchPatternClass() {
		return TypeDeclarationPattern.class;
	}

	@Override
	protected void searchDeclarations(SearchQueryParameters params,
			List<VjoMatch> result) {
		// TODO Auto-generated method stub
		IType type = (IType) params.getElement();
		System.out.println("Searching for declaration of "
				+ type.getElementName());
		if (isInScope(type)) {
			try {
				ISourceRange nameRange = type.getNameRange();
				VjoMatch match = VjoMatchFactory.createFieldMatch(type, nameRange
						.getOffset(), nameRange.getLength());
				result.add(match);
			} catch (ModelException e) {
				VjetPlugin.getDefault().getLog().log(
						new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID,
								IStatus.ERROR, "Wrong name range", e));
			}
		}
	}

	@Override
	protected void searchReferences(SearchQueryParameters params,
			List<VjoMatch> result) {
		// sould not be called
	}
}
