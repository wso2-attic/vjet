/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import java.net.URI;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.dialogs.StatusInfo;
import org.eclipse.dltk.mod.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.wizards.NewPackageWizardPage;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.internal.corext.util.Messages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

/**
 * The page for creating a new vjo package.
 * 
 */

public final class VjoNewPackageWizardPage extends NewPackageWizardPage {

	private static final char SLASH = '/';

	private static final char DOT = '.';

	private static final String PACKAGE = "NewPackageWizardPage.package"; //$NON-NLS-1$

	private IStatus fPackageStatus;
	private IStatus fDUlpackageStatus;

	public VjoNewPackageWizardPage() {
		super();
	}

	// @Override
	// public void init(IStructuredSelection selection) {
	// IModelElement element = getInitialScriptElement(selection);
	// initContainerPage(element);
	// updateStatus(new IStatus[] { containerStatus, fPackageStatus });
	// }

	@Override
	protected String getRequiredNature() {
		return VjoNature.NATURE_ID;
	}

	@Override
	public String getTitle() {
		return VjetWizardMessages.PackageCreationWizard_page_title;
	}

	@Override
	public String getDescription() {
		return VjetWizardMessages.PackageCreationWizard_page_description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewPackageWizardPage#getModifiedResource()
	 */
	public IResource getModifiedResource() {
		IProjectFragment root = getProjectFragment();
		if (root != null) {
			String text = getPackageText();
			text = replaceDotToSlash(text);
			return root.getScriptFolder(text).getResource();
		}
		return null;
	}

	private String replaceDotToSlash(String text) {
		text = text.replace(DOT, SLASH);
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewPackageWizardPage#createPackage(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void createPackage(IProgressMonitor monitor) throws CoreException,
			InterruptedException {

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		IProjectFragment root = getProjectFragment();
		String packName = getPackageText();
		packName = replaceDotToSlash(packName);
		fCreatedScriptFolder = root.createScriptFolder(packName, true, monitor);

		if (monitor.isCanceled()) {
			throw new InterruptedException();
		}
	}

	@Override
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);

		// Add by Oliver. Begin. 2009-10-30. If the project or source folder
		// name is
		// not existed, we will report an error message and make the 'Finish'
		// button disable. We can refer to the codes in
		// 'org.eclipse.dltk.mod.ui.wizards.NewContainerWizardPage.containerChanged()'
		// to modify some logic.
		// if (CONTAINER.equals(fieldName)) {
		if (containerStatus.getSeverity() != IStatus.OK) {
			updateStatus(new IStatus[] { containerStatus });
			return;
		}
		// }
		// End.

		// if (PACKAGE.equals(fieldName)) {
		fPackageStatus = packageTextChanged();
		fDUlpackageStatus = sourceFolderTextChanged();
		// }

		// do status line update
		if (fPackageStatus != null
				&& fPackageStatus.getSeverity() != IStatus.OK) {
			updateStatus(new IStatus[] { fPackageStatus });
			return;
		}
		if (fDUlpackageStatus != null
				&& fDUlpackageStatus.getSeverity() != IStatus.OK) {
			updateStatus(new IStatus[] { fDUlpackageStatus });
			return;
		}
	}

	private IStatus packageTextChanged() {

		StatusInfo status = new StatusInfo();

		String packName = getPackageText();

		if (VjoSourceModulePage.isEmptyName(packName) != null) {
			return new StatusInfo(IStatus.ERROR,
					VjetWizardMessages.convention_package_nullName);
		}
		int length;
		if ((length = packName.length()) == 0) {
			return new StatusInfo(IStatus.ERROR,
					VjetWizardMessages.convention_package_emptyName);
		}

		if (VjoNameValidator.startOrEndWithDot(packName) != null) {
			return VjoNameValidator.startOrEndWithDot(packName);
		}

		if (packName.length() > 0) {
			// Check the name with the common validation codes.
			checkCorrectName(status, packName);
		} else {
			enterName(status);
		}

		return status;
	}

	private IStatus sourceFolderTextChanged() {

		StatusInfo status = new StatusInfo();

		String packName = getPackageText();
		IProjectFragment root = getProjectFragment();

		// Add by Oliver. If the given project or src folder on the wizard does
		// not exist in current workspace, the 'root' varaible will be null.
		// Handle it here or handleFieldChanged() method.

		// if (containerStatus.getSeverity() != IStatus.OK) {
		// status = (StatusInfo) containerStatus;
		// }

		if (root != null && root.getScriptProject().exists()) {
			IScriptFolder pack = root.getScriptFolder(packName);
			try {
				checkExistence(status, pack);
			} catch (CoreException e) {
				DLTKUIPlugin.log(e);
			}
		}

		return status;

	}

	private void checkCorrectName(StatusInfo status, String packName) {
		IStatus val = JavaConventions.validatePackageName(packName);

		if (val.getSeverity() == IStatus.ERROR) {
			invalidPackageName(status, val, packName);
		} else if (val.getSeverity() == IStatus.WARNING) {
			discouragedPackageName(status, val);
		}
	}

	private void discouragedPackageName(StatusInfo status, IStatus val) {
		String m = VjetWizardMessages.PackageCreationWizard_discouraged_package_name;
		status.setWarning(Messages.format(m, val.getMessage()));
	}

	private void invalidPackageName(StatusInfo status, IStatus val,
			String packName) {

		if (packName != null && packName.length() > 0) {
			if (Character.isDigit(packName.charAt(0))
			// || VjoNameValidator.isContainInvalidChar(packName)
					// || VjoNameValidator.isContainBlank(packName)
					// || VjoNameValidator.isInKeywords(packName)
					|| VjoSourceModulePage
							.containInvalidCharInPackageName(packName)) {
				String m = VjetWizardMessages.PackageCreationWizard_invalid_package_name;
				status.setError(Messages.format(m, "'" + packName + "'"
						+ " is not a valid name"));
			}
		}
		// String m =
		// VjetWizardMessages.PackageCreationWizard_invalid_package_name;
		// status.setError(Messages.format(m, val.getMessage()));
	}

	private void enterName(StatusInfo status) {
		status.setError(NewWizardMessages.NewPackageWizardPage_error_EnterName);
	}

	private void checkExistence(StatusInfo status, IScriptFolder pack)
			throws ModelException, CoreException {

		if (pack.exists()) {
			if (pack.containsScriptResources() || !pack.hasSubfolders()) {
				packageExist(status);
			} else {
				packageNotShown(status);
			}
		} else {
			URI location = pack.getResource().getLocationURI();
			if (location != null) {
				IFileStore store = EFS.getStore(location);
				if (store.fetchInfo().exists()) {
					packageExistDifferenseCase(status);
				}
			}
		}
	}

	private void packageExistDifferenseCase(StatusInfo status) {
		String m = NewWizardMessages.NewPackageWizardPage_error_PackageExistsDifferentCase;
		status.setError(m);
	}

	private void packageNotShown(StatusInfo status) {
		String m = NewWizardMessages.NewPackageWizardPage_error_PackageNotShown;
		status.setError(m);
	}

	private void packageExist(StatusInfo status) {
		String m = NewWizardMessages.NewPackageWizardPage_error_PackageExists;
		status.setError(m);
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IHelpContextIds.NEW_PACKAGE);
	}

}