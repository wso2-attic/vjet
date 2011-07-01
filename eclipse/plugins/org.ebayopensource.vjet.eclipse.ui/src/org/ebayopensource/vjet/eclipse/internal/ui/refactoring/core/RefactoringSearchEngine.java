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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchEngine;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.SearchMatch;
import org.eclipse.dltk.mod.core.search.SearchParticipant;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.core.search.SearchRequestor;
import org.eclipse.dltk.mod.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.mod.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.mod.internal.corext.util.SearchUtils;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.mod.ui.search.PatternQuerySpecification;
import org.eclipse.dltk.mod.ui.search.QuerySpecification;
import org.eclipse.ltk.core.refactoring.IRefactoringStatusEntryComparator;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;

/**
 * Convenience wrapper for {@link VjoSearchEngine} - performs searching and
 * sorts the results by {@link IResource}. TODO: throw CoreExceptions from
 * search(..) methods instead of wrapped JavaModelExceptions.
 */
public class RefactoringSearchEngine {

	private RefactoringSearchEngine() {
		// no instances
	}

	// TODO: throw CoreException
	public static ISourceModule[] findAffectedSourceModules(
			QuerySpecification querySpecification, final IProgressMonitor pm,
			RefactoringStatus status, final boolean tolerateInAccurateMatches)
			throws ModelException {

		boolean hasNonCuMatches = false;

		class ResourceSearchRequestor extends SearchRequestor {
			boolean hasPotentialMatches = false;
			Set resources = new HashSet(5);
			private IResource fLastResource;

			public void acceptSearchMatch(SearchMatch match) {
				if (!tolerateInAccurateMatches
						&& match.getAccuracy() == SearchMatch.A_INACCURATE) {
					hasPotentialMatches = true;
				}
				if (fLastResource != match.getResource()) {
					fLastResource = match.getResource();
					resources.add(fLastResource);
				}
			}
		}
		ResourceSearchRequestor requestor = new ResourceSearchRequestor();

		// SearchQueryParameters params = new SearchQueryParameters();
		// QuerySpecification querySpecification=new
		// PatternQuerySpecification(null,);

		// new VjoSearchEngine().search(pattern, SearchUtils
		// .getDefaultSearchParticipants(), scope, requestor, pm);
		new VjoSearchEngine().search(createQueryParameters(querySpecification));

		List result = new ArrayList(requestor.resources.size());
		for (Iterator iter = requestor.resources.iterator(); iter.hasNext();) {
			IResource resource = (IResource) iter.next();
			IModelElement element = DLTKCore.create(resource);
			if (element instanceof ISourceModule) {
				result.add(element);
			} else {
				hasNonCuMatches = true;
			}
		}
		addStatusErrors(status, requestor.hasPotentialMatches, hasNonCuMatches);
		return (ISourceModule[]) result
				.toArray(new ISourceModule[result.size()]);
	}

	private static SearchQueryParameters createQueryParameters(
			QuerySpecification specification) {
		SearchPattern pattern;
		String stringPattern;
		IDLTKLanguageToolkit toolkit = specification.getScope()
				.getLanguageToolkit();

		SearchQueryParameters parameters;
		parameters = new SearchQueryParameters();

		if (specification instanceof ElementQuerySpecification) {

			IModelElement element = ((ElementQuerySpecification) specification)
					.getElement();
			stringPattern = ScriptElementLabels.getDefault().getElementLabel(
					element, ScriptElementLabels.ALL_DEFAULT);

			if (!element.exists()) {
				return new SearchQueryParameters(null, stringPattern);
			}

			pattern = SearchPattern.createPattern(element, specification
					.getLimitTo(), SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE,
					toolkit);

			ElementQuerySpecification elSpecification = (ElementQuerySpecification) specification;
			parameters.setElementSpecification(true);
			parameters.setElement(elSpecification.getElement());

		} else {

			PatternQuerySpecification patternSpec = (PatternQuerySpecification) specification;
			stringPattern = patternSpec.getPattern();

			int matchMode = getMatchMode(stringPattern)
					| SearchPattern.R_ERASURE_MATCH;

			if (patternSpec.isCaseSensitive())
				matchMode |= SearchPattern.R_CASE_SENSITIVE;

			pattern = SearchPattern.createPattern(patternSpec.getPattern(),
					patternSpec.getSearchFor(), patternSpec.getLimitTo(),
					matchMode, toolkit);
		}

		parameters.setStringPattern(stringPattern);
		parameters.setPattern(pattern);
		parameters.setScope(specification.getScope());
		parameters.setLimitTo(specification.getLimitTo());

		return parameters;
	}

	private static int getMatchMode(String pattern) {
		if (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1) {
			return SearchPattern.R_PATTERN_MATCH;
		} else if (SearchUtils.isCamelCasePattern(pattern)) {
			return SearchPattern.R_CAMELCASE_MATCH;
		}
		return SearchPattern.R_EXACT_MATCH;
	}

	// // TODO: throw CoreException
	public static ISourceModule[] findAffectedSourceModules(
			QuerySpecification querySpecification, final IProgressMonitor pm,
			RefactoringStatus status) throws ModelException {
		return findAffectedSourceModules(querySpecification, pm, status, false);
	}

	/**
	 * Performs a search and groups the resulting {@link SearchMatch}es by
	 * {@link SearchResultGroup#getCompilationUnit()}.
	 * 
	 * @param pattern
	 *            the search pattern
	 * @param scope
	 *            the search scope
	 * @param monitor
	 *            the progress monitor
	 * @param status
	 *            an error is added here if inaccurate or non-cu matches have
	 *            been found
	 * @return a {@link SearchResultGroup}[], where each {@link
	 *         SearchResultGroup} has a different
	 *         {@link SearchMatch#getResource() getResource()}s.
	 * @see SearchMatch
	 * @throws ModelException
	 *             when the search failed
	 */
	// TODO: throw CoreException
	public static SearchResultGroup[] search(
			QuerySpecification querySpecification, IProgressMonitor monitor,
			RefactoringStatus status) throws ModelException {
		return internalSearch(new VjoSearchEngine(), querySpecification,
				new CollectingSearchRequestor(), monitor, status);
	}

	// //TODO: throw CoreException
	// public static SearchResultGroup[] search(SearchPattern pattern,
	// WorkingCopyOwner owner, IDLTKSearchScope scope, IProgressMonitor monitor,
	// RefactoringStatus status)
	// throws ModelException {
	// return internalSearch(owner != null ? new SearchEngine(owner) : new
	// SearchEngine(), pattern, scope, new CollectingSearchRequestor(), monitor,
	// status);
	// }

	// TODO: throw CoreException
	public static SearchResultGroup[] search(
			QuerySpecification querySpecification,
			CollectingSearchRequestor requestor, IProgressMonitor monitor,
			RefactoringStatus status) throws ModelException {
		return internalSearch(new VjoSearchEngine(), querySpecification,
				requestor, monitor, status);
	}

	// //TODO: throw CoreException
	// public static SearchResultGroup[] search(SearchPattern pattern,
	// WorkingCopyOwner owner, IDLTKSearchScope scope,
	// CollectingSearchRequestor requestor, IProgressMonitor monitor,
	// RefactoringStatus status) throws ModelException {
	// return internalSearch(owner != null ? new SearchEngine(owner) : new
	// SearchEngine(), pattern, scope, requestor, monitor, status);
	// }

	// TODO: throw CoreException
	private static SearchResultGroup[] internalSearch(
			VjoSearchEngine searchEngine,
			QuerySpecification querySpecification,
			CollectingSearchRequestor requestor, IProgressMonitor monitor,
			RefactoringStatus status) throws ModelException {
		// try {
		// searchEngine.search(pattern., SearchUtils
		// .getDefaultSearchParticipants(), scope, requestor, monitor);
		// } catch (CoreException e) {
		// throw new ModelException(e);
		// }

		List matches = searchEngine
				.search(createQueryParameters(querySpecification));
		for (Iterator iterator = matches.iterator(); iterator.hasNext();) {
			VjoMatch vjoMatch = (VjoMatch) iterator.next();
			try {
				requestor.acceptSearchMatch(new VJOMatchWrapper(vjoMatch));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return groupByCu(requestor.getResults(), status);
	}

	static class VJOMatchWrapper extends SearchMatch {

		// private VjoMatch vjoMatch;

		public VJOMatchWrapper(IModelElement element, int accuracy, int offset,
				int length, SearchParticipant participant, IResource resource) {
			super(element, accuracy, offset, length, participant, resource);
		}

		public VJOMatchWrapper(VjoMatch vjoMatch) throws ModelException {
			super((IModelElement) vjoMatch.getElement(),
					vjoMatch.getAccuracy(), vjoMatch.getOffset(), vjoMatch
							.getLength(), null,
					getResource((IModelElement) vjoMatch.getElement()));
			// this.vjoMatch = vjoMatch;
		}

		private static IResource getResource(IModelElement modelElement) {
			if (modelElement.getAdapter(IResource.class) != null) {
				return (IResource) modelElement.getAdapter(IResource.class);
			}
			if (modelElement instanceof IMember) {
				return ResourceUtil.getResource(modelElement);
			}

			return null;

		}
	}

	public static SearchResultGroup[] groupByCu(SearchMatch[] matches,
			RefactoringStatus status) {
		return groupByCu(Arrays.asList(matches), status);
	}

	/**
	 * @param matchList
	 *            a List of SearchMatch
	 * @param status
	 *            the status to report errors.
	 * @return a SearchResultGroup[], grouped by SearchMatch#getResource()
	 */
	public static SearchResultGroup[] groupByCu(List matchList,
			RefactoringStatus status) {
		Map/* <IResource, List<SearchMatch>> */grouped = new HashMap();
		boolean hasPotentialMatches = false;
		boolean hasNonCuMatches = false;

		for (Iterator iter = matchList.iterator(); iter.hasNext();) {
			SearchMatch searchMatch = (SearchMatch) iter.next();
			if (searchMatch.getAccuracy() == SearchMatch.A_INACCURATE)
				hasPotentialMatches = true;
			if (!grouped.containsKey(searchMatch.getResource()))
				grouped.put(searchMatch.getResource(), new ArrayList(1));
			((List) grouped.get(searchMatch.getResource())).add(searchMatch);
		}

		for (Iterator iter = grouped.keySet().iterator(); iter.hasNext();) {
			IResource resource = (IResource) iter.next();
			IModelElement element = DLTKCore.create(resource);
			if (!(element instanceof ISourceModule)) {
				iter.remove();
				hasNonCuMatches = true;
			}
		}

		SearchResultGroup[] result = new SearchResultGroup[grouped.keySet()
				.size()];
		int i = 0;
		for (Iterator iter = grouped.keySet().iterator(); iter.hasNext();) {
			IResource resource = (IResource) iter.next();
			List searchMatches = (List) grouped.get(resource);
			SearchMatch[] matchArray = (SearchMatch[]) searchMatches
					.toArray(new SearchMatch[searchMatches.size()]);
			result[i] = new SearchResultGroup(resource, matchArray);
			i++;
		}
		addStatusErrors(status, hasPotentialMatches, hasNonCuMatches);
		return result;
	}

	// public static SearchPattern createOrPattern(IModelElement[] elements,
	// int limitTo) {
	// if (elements == null || elements.length == 0)
	// return null;
	// Set set = new HashSet(Arrays.asList(elements));
	// Iterator iter = set.iterator();
	// IModelElement first = (IModelElement) iter.next();
	// SearchPattern pattern = SearchPattern.createPattern(first, limitTo,
	// SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
	// if (pattern == null) // check for bug 90138
	// throw new IllegalArgumentException("Invalid java element: "
	// + first.getHandleIdentifier() + "\n" + first.toString()); //$NON-NLS-1$
	// //$NON-NLS-2$
	// while (iter.hasNext()) {
	// IModelElement each = (IModelElement) iter.next();
	// SearchPattern nextPattern = SearchPattern.createPattern(each,
	// limitTo, SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE);
	// if (nextPattern == null) // check for bug 90138
	// throw new IllegalArgumentException("Invalid java element: "
	// + each.getHandleIdentifier() + "\n" + each.toString()); //$NON-NLS-1$
	// //$NON-NLS-2$
	// pattern = SearchPattern.createOrPattern(pattern, nextPattern);
	// }
	// return pattern;
	// }

	private static boolean containsStatusEntry(final RefactoringStatus status,
			final RefactoringStatusEntry other) {
		return status.getEntries(new IRefactoringStatusEntryComparator() {
			public final int compare(final RefactoringStatusEntry entry1,
					final RefactoringStatusEntry entry2) {
				return entry1.getMessage().compareTo(entry2.getMessage());
			}
		}, other).length > 0;
	}

	private static void addStatusErrors(RefactoringStatus status,
			boolean hasPotentialMatches, boolean hasNonCuMatches) {
		if (hasPotentialMatches) {
			final RefactoringStatusEntry entry = new RefactoringStatusEntry(
					RefactoringStatus.ERROR,
					RefactoringCoreMessages.RefactoringSearchEngine_potential_matches);
			if (!containsStatusEntry(status, entry))
				status.addEntry(entry);
		}
		if (hasNonCuMatches) {
			final RefactoringStatusEntry entry = new RefactoringStatusEntry(
					RefactoringStatus.ERROR,
					RefactoringCoreMessages.RefactoringSearchEngine_non_cu_matches);
			if (!containsStatusEntry(status, entry))
				status.addEntry(entry);
		}
	}

}
