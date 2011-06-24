/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui.viewsupport;

import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.ImportContainer;
import org.eclipse.dltk.mod.internal.core.JSPackageDeclaration;
import org.eclipse.dltk.mod.ui.viewsupport.SourcePositionSorter;
import org.eclipse.jface.viewers.Viewer;

public class VjoSourcePositionSorter extends SourcePositionSorter {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		//move imports above type declaration, fix 4137
		boolean isE1PkgOrImporter=(e1 instanceof ImportContainer)||(e1 instanceof JSPackageDeclaration)||e1 instanceof IType;
		boolean isE2PkgOrImporter=(e2 instanceof ImportContainer)||(e2 instanceof JSPackageDeclaration)||e2 instanceof IType;
		
		if(isE1PkgOrImporter&&isE2PkgOrImporter&&e1.getClass()!=e2.getClass()){
			return getDefaultSequence(e1)-getDefaultSequence(e2);
		}
		return super.compare(viewer, e1, e2);
	}
	
	private int getDefaultSequence(Object e1){
		//make package always before importContainer, importContainer before IType
		if(e1 instanceof JSPackageDeclaration){
			return 0;
		}
		
		if(e1 instanceof ImportContainer){
			return 1;
		}
		
		if(e1 instanceof IType){
			return 2;
		}
		
		return -1;
		
	}
}
