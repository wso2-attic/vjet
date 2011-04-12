/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.ICallHierarchyFactory;
import org.eclipse.dltk.mod.core.ICallProcessor;
import org.eclipse.dltk.mod.core.ICalleeProcessor;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;

/**
 * Vjo call hierarchy factory implementation
 * 
 * 
 * 
 */
public class VjoCallHierarchyFactory implements ICallHierarchyFactory {

	public ICallProcessor createCallProcessor() {
		return new VjoCallProcessor();
	}

	public ICalleeProcessor createCalleeProcessor(IMethod method,
			IProgressMonitor monitor, IDLTKSearchScope scope) {
		return new VjoCalleeProcessor(method, monitor, scope);
	}

}
