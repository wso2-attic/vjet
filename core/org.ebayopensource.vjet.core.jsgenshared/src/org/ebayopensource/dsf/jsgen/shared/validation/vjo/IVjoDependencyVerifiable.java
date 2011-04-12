/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.Collections;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;

public interface IVjoDependencyVerifiable {

	boolean verify(final IJstType thisType, final String name);
	
	void addDependency(final IJstType dep);
	
	Set<IJstType> getDirectDependenciesFilteredByGroup(final IJstType thisType);
	
	Set<IJstType> getDirectDependencies();
	
	JstTypeSpaceMgr getTypeSpaceMgr();
	
	IVjoDependencyVerifiable FALSE_VERIFIER = new IVjoDependencyVerifiable() {
		
		@Override
		public boolean verify(final IJstType thisType, final String name) {
			return false;
		}

		@Override
		public void addDependency(IJstType dep) {
			//do nothing
		}

		@Override
		public Set<IJstType> getDirectDependenciesFilteredByGroup(final IJstType thisType) {
			return Collections.emptySet();
		}
		
		@Override
		public Set<IJstType> getDirectDependencies(){
			return Collections.emptySet();
		}

		@Override
		public JstTypeSpaceMgr getTypeSpaceMgr() {
			return null;
		}
	};
}
