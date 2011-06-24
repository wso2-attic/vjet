/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.internal.core.search.matching.PackageReferencePattern;

public class VjoPackageReferenceSearcher extends AbstractVjoElementSearcher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.ui.VjoElementSearcher#getSearchPatternClass()
	 */
	public Class<? extends SearchPattern> getSearchPatternClass() {
		return PackageReferencePattern.class;
	}

	@Override
	protected void searchDeclarations(SearchQueryParameters params, List<VjoMatch> result) {
		// should not be called
	}

	@Override
	protected void searchReferences(SearchQueryParameters params, List<VjoMatch> result) {
		IScriptFolder pkg = null;
		if (params.getElement() instanceof IPackageDeclaration) {
			IPackageDeclaration pkgDeclaration = (IPackageDeclaration) params.getElement();
			IVjoSourceModule vjoSourceModule = (IVjoSourceModule) pkgDeclaration.getParent();
			pkg = (IScriptFolder) vjoSourceModule.getParent();

		} else {
			pkg = (IScriptFolder) params.getElement();
		}
		System.out.println("Searching for references of " + pkg.getElementName());
		// VjoSourceModule module = (VjoSourceModule) pkg.getSourceModule();

		// first find all dependent types
		List<IJstType> dependents = new ArrayList<IJstType>();

		// dependents = getPackageDepends(pkg);
		dependents = ((TypeSpace) mgr.getTypeSpace()).getPackageDependents(pkg.getElementName());

		//temp bug fix of getPackageDependents.
		ISourceModule[] sourceModules;
		try {
			sourceModules = pkg.getSourceModules();
			for (ISourceModule sourceModule : sourceModules) {
				if (sourceModule instanceof VjoSourceModule) {
					IJstType type = ((IVjoSourceModule) sourceModule).getJstType();
					if (type == null) {
						// temp fix, this should be a bug of ScriptFolder
						type = TypeSpaceMgr.getInstance().findType(((IVjoSourceModule) sourceModule).getTypeName());
					}
					
					if(!dependents.contains(type)){
						dependents.add(type);
					}
					
				}
			}
		} catch (ModelException e) {
			DLTKCore.error("find source modules of scriptfolder:"+pkg.getElementName()+" failed.",e);
		}
		
		
		for (IJstType dependentType : dependents) {
			// now find all references of the type within dependent type
			findRefs(pkg, dependentType, result);
		}

	}

//	 private List<IJstType> getPackageDepends(IScriptFolder pkg) throws ModelException {
//		// TODO should use List<IJstType> dependents =
//		// mgr.getPackageDepends(pkg.getElementName());
//		// API not added, use temp solution to calculate all references toall
//		List<IJstType> al = new ArrayList<IJstType>();
//		ISourceModule[] sourceModules = pkg.getSourceModules();
//		for (ISourceModule sourceModule : sourceModules) {
//			if (sourceModule instanceof VjoSourceModule) {
//				IJstType type = ((VjoSourceModule) sourceModule).getJstType();
//				if (type == null) {
//					// temp fix, this should be a bug of ScriptFolder
//					type = TypeSpaceMgr.getInstance().findType(((VjoSourceModule) sourceModule).getTypeName());
//				}
//				if (!al.contains(type))
//					al.add(type);
//
//				// find depends
//				List<IJstType> types = mgr.getDirectDependents(((VjoSourceModule) sourceModule).getTypeName());
//				for (IJstType jstType : types) {
//					if (!al.contains(jstType))
//						al.add(jstType);
//				}
//
//			}
//		}
//
//		return al;
//
//	}

	private void findRefs(IScriptFolder pkg, IJstType dependentType, List<VjoMatch> result) {
		VjoPackageReferencesVisitor typeRefVisitor = new VjoPackageReferencesVisitor(pkg, dependentType, result);
		JstDepthFirstTraversal.accept(dependentType, typeRefVisitor);
	}

}
