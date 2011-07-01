/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import java.util.List;

import org.ebayopensource.vjet.eclipse.core.search.SearchQueryParameters;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchEngine;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceType;
import org.eclipse.dltk.mod.internal.corext.util.Messages;
import org.eclipse.dltk.mod.internal.corext.util.SearchUtils;
import org.eclipse.dltk.mod.internal.ui.search.DLTKElementMatch;
import org.eclipse.dltk.mod.internal.ui.search.DLTKSearchQuery;
import org.eclipse.dltk.mod.internal.ui.search.DLTKSearchResult;
import org.eclipse.dltk.mod.internal.ui.search.SearchMessages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.mod.ui.search.PatternQuerySpecification;
import org.eclipse.dltk.mod.ui.search.QuerySpecification;

/**
 * The search query based on {@link VjoSearchEngine} functionality.
 * 
 * 
 * 
 */
public class VjoSearchQuery extends DLTKSearchQuery {

	private QuerySpecification specification;

	private boolean isForSatisfier = false;

	public VjoSearchQuery(QuerySpecification specification) {
		super(specification);
		this.specification = specification;
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {

		final DLTKSearchResult result = (DLTKSearchResult) getSearchResult();
		result.removeAll();

		SearchQueryParameters parameters = createQueryParameters();

		if (parameters.getStringPattern() == null) {
			return createErrorStatus(parameters.getStringPattern());
		}

		monitor.beginTask(Messages.format(
				SearchMessages.DLTKSearchQuery_task_label, parameters
						.getStringPattern()), 1);

		VjoSearchEngine engine = new VjoSearchEngine();
		List<VjoMatch> list = engine.search(parameters);

		for (VjoMatch vm : list) {

			// Add by Oliver.2009-06-04.Begin. Sometimes there is no .satisfies
			// block for a type, only use .need to import the IType and use the
			// constant of ITYPE.It is not a satisfier,so filter it.
			if (isForSatisfier) {
				if (vm.getElement() instanceof VjoSourceType) {
					try {
						String[] satisfiers = ((VjoSourceType) vm.getElement())
								.getSuperInterfaceNames();
						if (parameters.getElement() instanceof VjoSourceType) {
							String satisfierName = ((VjoSourceType) parameters
									.getElement()).getFullyQualifiedName();
							if (!isStringInArray(satisfiers, satisfierName)) {
								continue;
							}
						}

					} catch (ModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// Add by Oliver.2009-06-04.End

			DLTKElementMatch match = new DLTKElementMatch(vm.getElement(), vm
					.getMatchRule(), vm.getOffset(), vm.getLength(), vm
					.getAccuracy(), vm.isReadAccess(), vm.isWriteAccess(), vm
					.isScriptdoc());

			match.setIsImport(vm.isIsImport());
			match.setPublic(vm.isPublic());
			match.setStatic(vm.isStatic());
//			match.setIsImport(vm.isScriptdoc());

			result.addMatch(match);
		}

		String message = createStatusMessage(result);
		return createSuccessStatus(message);
	}

	private boolean isStringInArray(String[] satisfiers, String satisfierName) {
		boolean isContainInSatisfiesAree = false;
		for (String name : satisfiers) {
			if (name.equals(satisfierName)) {
				isContainInSatisfiesAree = true;
			}
		}
		return isContainInSatisfiesAree;
	}

	private SearchQueryParameters createQueryParameters() {
		SearchPattern pattern;
		String stringPattern;
		IDLTKLanguageToolkit toolkit = this.specification.getScope()
				.getLanguageToolkit();

		SearchQueryParameters parameters;
		parameters = new SearchQueryParameters();

		int limitedTo = specification.getLimitTo();

		// Add by Oliver.2009-06-04.Begin.
		// Handle the satisfier searching.
		if (limitedTo == IDLTKSearchConstants.SATISFIER) {
			limitedTo = IDLTKSearchConstants.REFERENCES;
			isForSatisfier = true;
		}
		// Add by Oliver.2009-06-04.End.

		if (specification instanceof ElementQuerySpecification) {

			IModelElement element = ((ElementQuerySpecification) specification)
					.getElement();
			stringPattern = ScriptElementLabels.getDefault().getElementLabel(
					element, ScriptElementLabels.ALL_DEFAULT);

			if (!element.exists()) {
				return new SearchQueryParameters(null, stringPattern);
			}

			// Add the code below to fit with native type.
			if (element instanceof NativeVjoSourceModule) {
				element = ((NativeVjoSourceModule) element).getVjoType();
			}
			
			pattern = SearchPattern.createPattern(element, limitedTo,
					SearchUtils.GENERICS_AGNOSTIC_MATCH_RULE, toolkit);

			ElementQuerySpecification elSpecification = (ElementQuerySpecification) specification;
			parameters.setElementSpecification(true);
			parameters.setElement(element);

		} else {

			PatternQuerySpecification patternSpec = (PatternQuerySpecification) specification;
			stringPattern = patternSpec.getPattern();

			int matchMode = getMatchMode(stringPattern)
					| SearchPattern.R_ERASURE_MATCH;

			if (patternSpec.isCaseSensitive())
				matchMode |= SearchPattern.R_CASE_SENSITIVE;

			pattern = SearchPattern.createPattern(patternSpec.getPattern(),
					patternSpec.getSearchFor(), limitedTo, matchMode, toolkit);
		}

		parameters.setStringPattern(stringPattern);
		parameters.setPattern(pattern);
		parameters.setScope(specification.getScope());
		parameters.setLimitTo(limitedTo);

		return parameters;
	}

	private Status createErrorStatus(String stringPattern) {
		return new Status(
				IStatus.ERROR,
				DLTKUIPlugin.getPluginId(),
				0,
				Messages
						.format(
								SearchMessages.DLTKSearchQuery_error_element_does_not_exist,
								stringPattern), null);
	}

	private Status createSuccessStatus(String message) {
		return new Status(IStatus.OK, DLTKUIPlugin.getPluginId(), 0, message,
				null);
	}

	private String createStatusMessage(final DLTKSearchResult textResult) {
		return Messages.format(
				SearchMessages.DLTKSearchQuery_status_ok_message, String
						.valueOf(textResult.getMatchCount()));
	}

	private int getMatchMode(String pattern) {
		if (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1) {
			return SearchPattern.R_PATTERN_MATCH;
		} else if (SearchUtils.isCamelCasePattern(pattern)) {
			return SearchPattern.R_CAMELCASE_MATCH;
		}
		return SearchPattern.R_EXACT_MATCH;
	}

}
