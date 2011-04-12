/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.search.SearchMatch;
import org.eclipse.dltk.mod.internal.corext.util.SearchUtils;

/**
 * Collects the results returned by a <code>SearchEngine</code>. Only
 * collects matches in CUs ands offers a scanner to trim match ranges.
 */
public abstract class SourceModuleCollectingSearchRequestor extends
		CollectingSearchRequestor {

	// private ISourceModule fCuCache;

	// private IScanner fScannerCache;
	//	
	// protected IScanner getScanner(ICompilationUnit unit) {
	// if (unit.equals(fCuCache))
	// return fScannerCache;
	//		
	// fCuCache= unit;
	// IJavaProject project= unit.getJavaProject();
	// String sourceLevel= project.getOption(JavaCore.COMPILER_SOURCE, true);
	// String complianceLevel= project.getOption(JavaCore.COMPILER_COMPLIANCE,
	// true);
	// fScannerCache= ToolFactory.createScanner(false, false, false,
	// sourceLevel, complianceLevel);
	// return fScannerCache;
	// }

	/**
	 * This is an internal method. Do not call from subclasses! Use
	 * {@link #collectMatch(SearchMatch)} instead.
	 * 
	 * @param match
	 * @throws CoreException
	 * @deprecated
	 */
	public final void acceptSearchMatch(SearchMatch match) throws CoreException {
		ISourceModule unit = SearchUtils.getSourceModule(match);
		if (unit == null)
			return;
		acceptSearchMatch(unit, match);
	}

	public void collectMatch(SearchMatch match) throws CoreException {
		super.acceptSearchMatch(match);
	}

	protected abstract void acceptSearchMatch(ISourceModule unit,
			SearchMatch match) throws CoreException;

	public void endReporting() {
		// fCuCache = null;
		// fScannerCache= null;
	}
}
