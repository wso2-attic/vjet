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
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.rename;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.dltk.mod.internal.ui.refactoring.reorg.RenameInputWizardPage;
import org.eclipse.dltk.mod.internal.ui.refactoring.reorg.RenameRefactoringWizard;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;

/**
 * The type renaming wizard.
 */
public class RenameTypeWizard extends RenameRefactoringWizard {

	public RenameTypeWizard(Refactoring refactoring) {
		this(refactoring,
				RefactoringMessages.RenameTypeWizard_defaultPageTitle,
				RefactoringMessages.RenameTypeWizardInputPage_description,
				DLTKPluginImages.DESC_WIZBAN_REFACTOR_TYPE, ""/* IScriptHelpContextIds.RENAME_TYPE_WIZARD_PAGE */); //$NON-NLS-1$
	}

	public RenameTypeWizard(Refactoring refactoring, String defaultPageTitle,
			String inputPageDescription,
			ImageDescriptor inputPageImageDescriptor, String pageContextHelpId) {
		super(refactoring, defaultPageTitle, inputPageDescription,
				inputPageImageDescriptor, pageContextHelpId);
	}

	/*
	 * non java-doc
	 * 
	 * @see RefactoringWizard#addUserInputPages
	 */
	protected void addUserInputPages() {
		super.addUserInputPages();
		// if (isRenameType())
		// addPage(new RenameTypeWizardSimilarElementsPage());

	}

	public RenameVjoTypeProcessor getRenameTypeProcessor() {
		RefactoringProcessor proc = ((RenameRefactoring) getRefactoring())
				.getProcessor();
		if (proc instanceof RenameVjoTypeProcessor)
			return (RenameVjoTypeProcessor) proc;
		else if (proc instanceof RenameVjoSourceModuleProcessor) {
			RenameVjoSourceModuleProcessor rcu = (RenameVjoSourceModuleProcessor) proc;
	        return rcu.getRenameTypeProcessor();
		}
		Assert.isTrue(false); // Should never get here
		return null;
	}

	protected boolean isRenameType() {
		return true;
	}

	protected RenameInputWizardPage createInputPage(String message,
			String initialSetting) {
		return new RenameTypeWizardInputPage(message, "unknown help id", true,
				initialSetting) {

			protected RefactoringStatus validateTextField(String text) {
				return validateNewName(text);
			}
		};
	}
}
