/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Matt Chapman, mpchapman@gmail.com - 89977 Make JDT .java agnostic
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.rename;

import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ScriptModelUtil;
import org.eclipse.dltk.mod.internal.corext.refactoring.Checks;
import org.eclipse.dltk.mod.internal.corext.refactoring.tagging.INameUpdating;
import org.eclipse.dltk.mod.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.dltk.mod.internal.ui.refactoring.reorg.RenameInputWizardPage;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;

public class RenameVjoSourceModuleWizard extends RenameTypeWizard {

	public RenameVjoSourceModuleWizard(Refactoring refactoring) {
		super(refactoring, RefactoringMessages.RenameCuWizard_defaultPageTitle, RefactoringMessages.RenameCuWizard_inputPage_description,
				DLTKPluginImages.DESC_WIZBAN_REFACTOR_CU,
				/* IJavaHelpContextIds.RENAME_CU_WIZARD_PAGE */"");
	}

	protected RefactoringStatus validateNewName(String newName) {
		String fullName = ScriptModelUtil.getRenamedCUName(getSourceModule(), newName);
		return super.validateNewName(fullName);
	}

	private ISourceModule getSourceModule() {
		return (ISourceModule) getSourceModuleProcessor().getElements()[0];
	}

	private RenameVjoSourceModuleProcessor getSourceModuleProcessor() {
		return ((RenameVjoSourceModuleProcessor) ((RenameRefactoring) getRefactoring()).getProcessor());
	}

	protected RenameInputWizardPage createInputPage(String message, String initialSetting) {
		return new RenameTypeWizardInputPage(message, /* IJavaHelpContextIds.RENAME_CU_WIZARD_PAGE */"", true, initialSetting) {
			protected RefactoringStatus validateTextField(String text) {
				return validateNewName(text);
			}

			protected String getNewName(INameUpdating nameUpdating) {
				String result = nameUpdating.getNewElementName();
				// If renaming a CU we have to remove the java file extension
				return Checks.removeJavaScriptLikeExtension(result);
			}
		};
	}

	protected boolean isRenameType() {
		// the flag 'willRenameType' may change in checkInitialConditions(), but
		// only from true to false.
		return getSourceModuleProcessor().isWillRenameType();
	}

}
