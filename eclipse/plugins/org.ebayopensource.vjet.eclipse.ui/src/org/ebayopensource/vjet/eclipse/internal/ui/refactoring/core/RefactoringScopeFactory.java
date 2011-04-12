/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.core.search.SearchEngine;
import org.eclipse.dltk.mod.internal.core.util.VjoFlags;

public class RefactoringScopeFactory {

	/*
	 * Adds to <code> projects </code> IJavaProject objects for all projects
	 * directly or indirectly referencing focus. @param projects IJavaProjects
	 * will be added to this set
	 */
	private static void addReferencingProjects(IScriptProject focus,
			Set projects) throws ModelException {
		IProject[] referencingProjects = focus.getProject()
				.getReferencingProjects();
		for (int i = 0; i < referencingProjects.length; i++) {
			IScriptProject candidate = DLTKCore.create(referencingProjects[i]);
			if (candidate == null || projects.contains(candidate)
					|| !candidate.exists())
				continue; // break cycle
			IBuildpathEntry entry = getReferencingClassPathEntry(candidate,
					focus);
			if (entry != null) {
				projects.add(candidate);
				if (entry.isExported())
					addReferencingProjects(candidate, projects);
			}
		}
	}

	//
	// private static void addRelatedReferencing(IJavaProject focus, Set
	// projects)
	// throws CoreException {
	// IProject[] referencingProjects = focus.getProject()
	// .getReferencingProjects();
	// for (int i = 0; i < referencingProjects.length; i++) {
	// IJavaProject candidate = JavaCore.create(referencingProjects[i]);
	// if (candidate == null || projects.contains(candidate)
	// || !candidate.exists())
	// continue; // break cycle
	// IClasspathEntry entry = getReferencingClassPathEntry(candidate,
	// focus);
	// if (entry != null) {
	// projects.add(candidate);
	// if (entry.isExported()) {
	// addRelatedReferencing(candidate, projects);
	// addRelatedReferenced(candidate, projects);
	// }
	// }
	// }
	// }
	//
	// private static void addRelatedReferenced(IJavaProject focus, Set
	// projects)
	// throws CoreException {
	// IProject[] referencedProjects = focus.getProject()
	// .getReferencedProjects();
	// for (int i = 0; i < referencedProjects.length; i++) {
	// IJavaProject candidate = JavaCore.create(referencedProjects[i]);
	// if (candidate == null || projects.contains(candidate)
	// || !candidate.exists())
	// continue; // break cycle
	// IClasspathEntry entry = getReferencingClassPathEntry(focus,
	// candidate);
	// if (entry != null) {
	// projects.add(candidate);
	// if (entry.isExported()) {
	// addRelatedReferenced(candidate, projects);
	// addRelatedReferencing(candidate, projects);
	// }
	// }
	// }
	// }
	//
	/**
	 * Creates a new search scope with all compilation units possibly
	 * referencing <code>javaElement</code>, considering the visibility of
	 * the element.
	 * 
	 * @param javaElement
	 *            the java element
	 * @return the search scope
	 * @throws ModelException
	 *             if an error occurs
	 */
	public static IDLTKSearchScope create(IModelElement javaElement)
			throws ModelException {
		return RefactoringScopeFactory.create(javaElement, true);
	}

	/**
	 * Creates a new search scope with all compilation units possibly
	 * referencing <code>javaElement</code>.
	 * 
	 * @param javaElement
	 *            the java element
	 * @param considerVisibility
	 *            consider visibility of javaElement iff <code>true</code>
	 * @return the search scope
	 * @throws ModelException
	 *             if an error occurs
	 */
	public static IDLTKSearchScope create(IModelElement javaElement,
			boolean considerVisibility) throws ModelException {
		if (considerVisibility & javaElement instanceof IMember) {
			IMember member = (IMember) javaElement;
			if (VjoFlags.isPrivate(member)) {
				if (member.getSourceModule() != null)
					return SearchEngine.createSearchScope(member
							.getSourceModule());
				else
					return SearchEngine.createSearchScope(member);
			}
			// Removed code that does some optimizations regarding package
			// visible members. The problem is that
			// there can be a package fragment with the same name in a different
			// source folder or project. So we
			// have to treat package visible members like public or protected
			// members.
		}
		return create(javaElement.getScriptProject());
	}

	private static IDLTKSearchScope create(IScriptProject javaProject)
			throws ModelException {
		return SearchEngine.createSearchScope(getAllScopeElements(javaProject),
				false, DLTKLanguageManager.getLanguageToolkit(javaProject));
	}

	/**
	 * Creates a new search scope comprising <code>members</code>.
	 * 
	 * @param members
	 *            the members
	 * @return the search scope
	 * @throws ModelException
	 *             if an error occurs
	 */
	// public static IJavaSearchScope create(IMember[] members)
	// throws ModelException {
	// Assert.isTrue(members != null && members.length > 0);
	// IMember candidate = members[0];
	// int visibility = getVisibility(candidate);
	// for (int i = 1; i < members.length; i++) {
	// int mv = getVisibility(members[i]);
	// if (mv > visibility) {
	// visibility = mv;
	// candidate = members[i];
	// }
	// }
	// return create(candidate);
	// }
	//
	// /**
	// * Creates a new search scope with all projects possibly referenced from
	// the
	// * given <code>javaElements</code>.
	// *
	// * @param javaElements
	// * the java elements
	// * @return the search scope
	// */
	// public static IJavaSearchScope createReferencedScope(
	// IJavaElement[] javaElements) {
	// Set projects = new HashSet();
	// for (int i = 0; i < javaElements.length; i++) {
	// projects.add(javaElements[i].getJavaProject());
	// }
	// IJavaProject[] prj = (IJavaProject[]) projects
	// .toArray(new IJavaProject[projects.size()]);
	// return SearchEngine.createJavaSearchScope(prj, true);
	// }
	//
	// /**
	// * Creates a new search scope with all projects possibly referenced from
	// the
	// * given <code>javaElements</code>.
	// *
	// * @param javaElements
	// * the java elements
	// * @param includeMask
	// * the include mask
	// * @return the search scope
	// */
	// public static IJavaSearchScope createReferencedScope(
	// IJavaElement[] javaElements, int includeMask) {
	// Set projects = new HashSet();
	// for (int i = 0; i < javaElements.length; i++) {
	// projects.add(javaElements[i].getJavaProject());
	// }
	// IJavaProject[] prj = (IJavaProject[]) projects
	// .toArray(new IJavaProject[projects.size()]);
	// return SearchEngine.createJavaSearchScope(prj, includeMask);
	// }
	//
	// /**
	// * Creates a new search scope containing all projects which reference or
	// are
	// * referenced by the specified project.
	// *
	// * @param project
	// * the project
	// * @param includeMask
	// * the include mask
	// * @return the search scope
	// * @throws CoreException
	// * if a referenced project could not be determined
	// */
	// public static IJavaSearchScope createRelatedProjectsScope(
	// IJavaProject project, int includeMask) throws CoreException {
	// IJavaProject[] projects = getRelatedProjects(project);
	// return SearchEngine.createJavaSearchScope(projects, includeMask);
	// }
	//
	private static IModelElement[] getAllScopeElements(IScriptProject project)
			throws ModelException {
		Collection sourceRoots = getAllSourceRootsInProjects(getReferencingProjects(project));
		return (IProjectFragment[]) sourceRoots
				.toArray(new IProjectFragment[sourceRoots.size()]);
	}

	/*
	 * @param projects a collection of IJavaProject @return Collection a
	 * collection of IPackageFragmentRoot, one element for each
	 * packageFragmentRoot which lies within a project in <code> projects
	 * </code> .
	 */
	private static Collection getAllSourceRootsInProjects(Collection projects)
			throws ModelException {
		List result = new ArrayList();
		for (Iterator it = projects.iterator(); it.hasNext();)
			result.addAll(getSourceRoots((IScriptProject) it.next()));
		return result;
	}

	/*
	 * Finds, if possible, a classpathEntry in one given project such that this
	 * classpath entry references another given project. If more than one entry
	 * exists for the referenced project and at least one is exported, then an
	 * exported entry will be returned.
	 */
	private static IBuildpathEntry getReferencingClassPathEntry(
			IScriptProject referencingProject, IScriptProject referencedProject)
			throws ModelException {
		IBuildpathEntry result = null;
		IPath path = referencedProject.getProject().getFullPath();
		IBuildpathEntry[] classpath = referencingProject
				.getResolvedBuildpath(true);
		for (int i = 0; i < classpath.length; i++) {
			IBuildpathEntry entry = classpath[i];
			if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT
					&& path.equals(entry.getPath())) {
				if (entry.isExported())
					return entry;
				// Consider it as a candidate. May be there is another entry
				// that is
				// exported.
				result = entry;
			}
		}
		return result;
	}

	//
	// private static IJavaProject[] getRelatedProjects(IJavaProject focus)
	// throws CoreException {
	// final Set projects = new HashSet();
	//
	// addRelatedReferencing(focus, projects);
	// addRelatedReferenced(focus, projects);
	//
	// projects.add(focus);
	// return (IJavaProject[]) projects.toArray(new IJavaProject[projects
	// .size()]);
	// }
	//
	private static Collection getReferencingProjects(IScriptProject focus)
			throws ModelException {
		Set projects = new HashSet();

		addReferencingProjects(focus, projects);
		projects.add(focus);
		return projects;
	}

	//
	private static List getSourceRoots(IScriptProject javaProject)
			throws ModelException {
		List elements = new ArrayList();

		IProjectFragment[] roots = javaProject.getProjectFragments();
		// Add all package fragment roots except archives
		for (int i = 0; i < roots.length; i++) {
			IProjectFragment root = roots[i];
			if (!root.isArchive())
				elements.add(root);
		}
		return elements;
	}

	//
	// private static int getVisibility(IMember member) throws
	// ModelException {
	// if (JdtFlags.isPrivate(member))
	// return 0;
	// if (JdtFlags.isPackageVisible(member))
	// return 1;
	// if (JdtFlags.isProtected(member))
	// return 2;
	// return 4;
	// }
	private RefactoringScopeFactory() {
		// no instances
	}

}
