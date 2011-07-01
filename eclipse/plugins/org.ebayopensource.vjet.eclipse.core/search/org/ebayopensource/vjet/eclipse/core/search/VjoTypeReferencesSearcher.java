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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.search.matching.TypeReferencePattern;

/**
 * 
 * 
 */
public class VjoTypeReferencesSearcher extends AbstractVjoElementSearcher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.ui.VjoElementSearcher#getSearchPatternClass()
	 */
	public Class<? extends SearchPattern> getSearchPatternClass() {
		return TypeReferencePattern.class;
	}

	@Override
	protected void searchDeclarations(SearchQueryParameters params,
			List<VjoMatch> result) {
		// should not be called
	}

	@Override
	protected void searchReferences(SearchQueryParameters params,
			List<VjoMatch> result) {
		IType type = (IType) params.getElement();

		System.out.println("Searching for references of "
				+ type.getElementName());
		IVjoSourceModule module = (IVjoSourceModule) type.getSourceModule();
		
		// first find all dependent types
		// List<IJstType> dependents = mgr.getDirectDependents(new TypeName(
		// "VjoBaseLib", "vjo"));

		TypeName typeName = module
				.getTypeName();
		
		
		TypeName typeName2 = new SourceTypeName(typeName.groupName(), type.getFullyQualifiedName("."));
		List<IJstType> dependents = mgr.getDirectDependents(typeName2);
//		dependents = mgr.getDirectDependents(new TypeName(
//				JstTypeSpaceMgr.JS_NATIVE_GRP, "Array"));
		// CodeassistUtils.findNativeJstType("Array");
		for (IJstType dependentType : dependents) {
			// now find all references of the type within dependent type
			findRefs(type, dependentType, result);
		}
		// visit declaring type also
		String groupName = type.getScriptProject().getElementName();
		String fullTypeName = type.getFullyQualifiedName(".").replace('/', '.');

		IJstType declaringType = mgr.findType(new TypeName(groupName,
				fullTypeName));
		findRefs(type, declaringType, result);
	}

	private void findRefs(IType mainType, IJstType dependentType,
			List<VjoMatch> result) {
		VjoTypeReferencesVisitor typeRefVisitor = new VjoTypeReferencesVisitor(
				mainType, dependentType, result);
		JstDepthFirstTraversal.accept(dependentType, typeRefVisitor);
	}
}
