/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.internal.ui.refactoring.core.SearchResultGroup;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.corext.refactoring.Checks;
import org.eclipse.dltk.mod.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class VjoChecks {

	private VjoChecks() {

	}

	public static SearchResultGroup[] excludeSourceModules(SearchResultGroup[] grouped, RefactoringStatus status) throws ModelException {
		List result = new ArrayList();
		boolean wasEmpty = grouped.length == 0;
		for (int i = 0; i < grouped.length; i++) {
			IResource resource = grouped[i].getResource();
			IModelElement element = DLTKCore.create(resource);
			if (!(element instanceof ISourceModule))
				continue;
			// XXX this is a workaround for a jcore feature that shows errors in
			// cus only when you get the original element
			ISourceModule cu = (ISourceModule) DLTKCore.create(resource);
			if (!cu.isStructureKnown()) {
				String path = Checks.getFullPath(cu);
				status.addError(MessageFormat.format(RefactoringCoreMessages.Checks_cannot_be_parsed, path));
				continue; // removed, go to the next one
			}
			result.add(grouped[i]);
		}

		if ((!wasEmpty) && result.isEmpty())
			status.addFatalError(RefactoringCoreMessages.Checks_all_excluded);

		return (SearchResultGroup[]) result.toArray(new SearchResultGroup[result.size()]);
	}

	public static RefactoringStatus checkCompileErrorsInAffectedFile(SearchResultGroup[] grouped) throws ModelException {
		RefactoringStatus result = new RefactoringStatus();
		for (int i = 0; i < grouped.length; i++)
			Checks.checkCompileErrorsInAffectedFile(result, grouped[i].getResource());
		return result;
	}

}
