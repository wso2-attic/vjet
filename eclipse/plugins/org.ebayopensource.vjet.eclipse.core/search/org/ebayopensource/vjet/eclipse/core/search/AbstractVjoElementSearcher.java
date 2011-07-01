/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.internal.core.search.HierarchyScope;

/**
 * Abstract class for all element searchers. Contains support methods.
 * 
 * 
 * 
 * 
 */
abstract class AbstractVjoElementSearcher implements IVjoElementSearcher,
		IVjoOccurrenceSearcher {
	protected TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();

	protected IProject[] m_projects;

	protected HierarchyScope m_hierarchyScope;

	protected void initScope(IDLTKSearchScope scope) {
		if (scope instanceof HierarchyScope) {
			m_hierarchyScope = (HierarchyScope) scope;
		} else {
			m_projects = getProjects(scope);
			m_hierarchyScope = null;
		}
	}

	/**
	 * Return true if type exists in any projects of the search scope 
	 * 
	 * @param type {@link IType} object
	 * @return true if type exists in any projects of the search scope
	 */
	protected boolean isInScope(IType type) {
		boolean inScope = false;
		if (type == null) {
			return inScope;
		}
		if(CodeassistUtils.isNativeObject(type)){
			return true;
		}
		if (m_projects != null) {
			IProject typeProject = type.getParent().getScriptProject()
					.getProject();
			for (IProject project : m_projects) {
				if (project.equals(typeProject)) {
					inScope = true;
					break;
				}
			}
		} else {
			// hierarchy
			inScope = m_hierarchyScope.encloses(type);
		}
		return inScope;
	}

	public void searchByModel(SearchQueryParameters params,
			List<VjoMatch> result) {
		initScope(params.getScope());
		int maskedLimitTo = params.getLimitTo()
				& ~(IDLTKSearchConstants.IGNORE_DECLARING_TYPE + IDLTKSearchConstants.IGNORE_RETURN_TYPE);
		if (maskedLimitTo == IDLTKSearchConstants.DECLARATIONS) {
			searchDeclarations(params, result);
		} else if (maskedLimitTo == IDLTKSearchConstants.REFERENCES) {
			searchReferences(params, result);
		} else if (maskedLimitTo == IDLTKSearchConstants.ALL_OCCURRENCES) {
			searchDeclarations(params, result);
			searchReferences(params, result);
		}
	}

	protected abstract void searchDeclarations(
			SearchQueryParameters parameters, List<VjoMatch> result);

	protected abstract void searchReferences(SearchQueryParameters parameters,
			List<VjoMatch> result);

	public void searchByPattern(SearchQueryParameters parameters,
			List<VjoMatch> result) {
		// empty implementation
	}

	/**
	 * Returns list of the {@link IProject} objects for {@link IDLTKSearchScope}
	 * object.
	 * 
	 * @param scope
	 *            {@link IDLTKSearchScope} object
	 * @return list of the {@link IProject} objects for {@link IDLTKSearchScope}
	 *         object.
	 */
	public IProject[] getProjects(IDLTKSearchScope scope) {
		IPath[] paths = scope.enclosingProjectsAndZips();
		HashSet temp = new HashSet();
		for (int i = 0; i < paths.length; i++) {
			IResource resource = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(paths[i]);
			if (resource != null && resource.getType() == IResource.PROJECT)
				temp.add(resource);
		}
		return (IProject[]) temp.toArray(new IProject[temp.size()]);
	}

	// add by patrick
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.search.IVjoOccurrenceSearcher#findOccurrence(org.ebayopensource.dsf.jst.IJstNode,
	 *      org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public List<VjoMatch> findOccurrence(IJstNode jstNode, IJstNode scope) {
		return Collections.emptyList();
	}
	// end add
}
