/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.search.matching.OrPattern;

/**
 * This class perform search type declaration and references.
 * 
 * 
 * 
 */
public class VjoTypeSearcher extends AbstractVjoElementSearcher {

	public Class<? extends SearchPattern> getSearchPatternClass() {
		return OrPattern.class;
	}

	@Override
	protected void searchDeclarations(SearchQueryParameters params,
			List<VjoMatch> result) {
		IType type = (IType) params.getElement();
		if (!isInScope(type))
			return;

		try {
			ISourceRange nameRange = type.getNameRange();
			if (nameRange == null)
				return;

			// add support for inner type
			if (type.getDeclaringType() != null)
				type = type.getDeclaringType();

			VjoMatch match = VjoMatchFactory.createFieldMatch(type, nameRange
					.getOffset(), nameRange.getLength());
			result.add(match);
		} catch (ModelException e) {
		}
	}

	@Override
	protected void searchReferences(SearchQueryParameters params,
			List<VjoMatch> result) {
		IType type = (IType) params.getElement();
		IVjoSourceModule module = (IVjoSourceModule) type.getSourceModule();

		// first find all dependent types
		List<IJstType> dependents = mgr.getDirectDependents(module
				.getTypeName());
		for (IJstType dependentType : dependents) {
			findRefs(type, dependentType.getRootType(), result);
		}

		// visit declaring type also
		String groupName = type.getScriptProject().getElementName();
		String fullTypeName = type.getFullyQualifiedName(".");

		IJstType declaringType = mgr.findType(new TypeName(groupName,
				fullTypeName));
		if (declaringType != null)
			findRefs(type, declaringType.getRootType(), result);
	}

	private void findRefs(IType mainType, IJstType dependentType,
			List<VjoMatch> result) {
		VjoTypeReferencesVisitor typeRefVisitor = new VjoTypeReferencesVisitor(
				mainType, dependentType, result);

		// switch to new traversal to iterate overloaded methods/constructors
		// JstDepthFirstTraversal.accept(dependentType, typeRefVisitor);
		ReferenceSearchTraversal.accept(dependentType, typeRefVisitor);
	}

	//add by patrick
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.search.AbstractVjoElementSearcher#findOccurrence(org.ebayopensource.dsf.jst.IJstNode,
	 *      org.ebayopensource.dsf.jst.IJstNode)
	 */
	public List<VjoMatch> findOccurrence(IJstNode searchedType,
			IJstNode searchedTree) {
		VjoTypeOccurrenceVisitor visitor = new VjoTypeOccurrenceVisitor(
				(IJstType) searchedType);
		searchedTree.accept(visitor);
		return visitor.getMatches();
	}
	
}
