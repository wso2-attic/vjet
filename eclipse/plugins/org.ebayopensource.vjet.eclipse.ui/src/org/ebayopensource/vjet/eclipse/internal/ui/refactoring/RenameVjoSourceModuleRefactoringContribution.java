/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring;

import org.ebayopensource.vjet.eclipse.internal.ui.refactoring.rename.RenameVjoSourceModuleProcessor;
import org.ebayopensource.vjet.eclipse.internal.ui.refactoring.rename.RenameVjoSourceModuleWizard;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.internal.corext.refactoring.rename.ScriptRenameRefactoring;
import org.eclipse.dltk.mod.internal.corext.refactoring.vjet.AbstractVjoRenameRefactoringContribution;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;


/**
 * Refactoring contribution for the rename compilation unit refactoring.
 * 
	 *
 */
public final class RenameVjoSourceModuleRefactoringContribution extends AbstractVjoRenameRefactoringContribution {


	@Override
	public RefactoringWizard getRenameWizard() throws CoreException{
		return new RenameVjoSourceModuleWizard(createRefactoring(null));
	}

	@Override
	public Refactoring createNewRefactoring(RefactoringDescriptor descriptor)throws CoreException  {
		return new ScriptRenameRefactoring(new RenameVjoSourceModuleProcessor(null));
	}
}
