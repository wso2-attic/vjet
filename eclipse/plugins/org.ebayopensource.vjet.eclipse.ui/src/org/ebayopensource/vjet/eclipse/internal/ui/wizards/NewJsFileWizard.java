/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;


import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class NewJsFileWizard extends Wizard implements INewWizard {
	
	private NewJSFileWizardPage		fNewFilePage;
	private IStructuredSelection	fSelection;

	public void addPages() {
		fNewFilePage = new NewJSFileWizardPage("JSWizardNewFileCreationPage", new StructuredSelection(IDE.computeSelectedResources(fSelection))); //$NON-NLS-1$
		fNewFilePage.setTitle(VjetWizardMessages.Javascript_UI_Wizard_New_Heading); //$NON-NLS-1$
		fNewFilePage.setDescription(VjetWizardMessages.Javascript_UI_Wizard_New_Description); //$NON-NLS-1$
		addPage(fNewFilePage);
	}

	public void init(IWorkbench aWorkbench, IStructuredSelection aSelection) {
		fSelection = aSelection;
		setWindowTitle(VjetWizardMessages.Javascript_UI_Wizard_New_Title); //$NON-NLS-1$
//		setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWJSFILE);
	}

	private void openEditor(final IFile file) {
		if (file != null) {
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, file, true, false);
					}
					catch (PartInitException e) {
						// STP Logger.log(Logger.WARNING_DEBUG, e.getMessage(), e);
						VjetUIPlugin.log(e);
					}
				}
			});
		}
	}

	public boolean performFinish() {
		boolean performedOK = false;

		// no file extension specified so add default extension
		String fileName = fNewFilePage.getFileName();
		if (fileName.lastIndexOf('.') == -1) {
			String newFileName = fNewFilePage.addDefaultExtension(fileName);
			fNewFilePage.setFileName(newFileName);
		}

		// create a new empty file
		IFile file = fNewFilePage.createNewFile();

		// if there was problem with creating file, it will be null, so make
		// sure to check
		if (file != null) {
			// open the file in editor
			openEditor(file);

			// everything's fine
			performedOK = true;
		}
		return performedOK;
	}

}


