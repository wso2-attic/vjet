/**
 * 
 */
package org.eclipse.dltk.mod.internal.corext.refactoring.vjet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.internal.corext.refactoring.ScriptRefactoringContribution;
import org.eclipse.dltk.mod.internal.corext.refactoring.vjet.descriptors.RenameModelElementDescriptor;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public abstract class AbstractVjoRenameRefactoringContribution extends
		ScriptRefactoringContribution {

	private Refactoring fRefactoring;

	public RefactoringDescriptor createDescriptor() {
		return new RenameModelElementDescriptor(getId());
	}

	/*
	 * (non-Javadoc) won't create again once created
	 * 
	 * @see org.eclipse.dltk.mod.internal.corext.refactoring.ScriptRefactoringContribution#createRefactoring(org.eclipse.ltk.core.refactoring.RefactoringDescriptor)
	 */
	public final Refactoring createRefactoring(RefactoringDescriptor descriptor)
			throws CoreException {

		if (fRefactoring == null) {
			synchronized (this) {
				fRefactoring = createNewRefactoring(descriptor);
			}

		}
		return fRefactoring;
	}

	public abstract Refactoring createNewRefactoring(
			RefactoringDescriptor descriptor) throws CoreException;

	/**
	 * for loading wizards in vjet projects
	 * 
	 * @return the rename wizard
	 */
	public abstract RefactoringWizard getRenameWizard() throws CoreException;

}
