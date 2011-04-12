/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.wizards.Messages;
import org.eclipse.dltk.mod.ui.wizards.NewElementWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

/**
 * The wizard for creating a new vjo source module.
 * 
 */
public abstract class VjoSourceModuleWizard extends NewElementWizard {

	protected VjoSourceModulePage page;

	private ISourceModule module;

	private String modifierIndex = "";

	protected abstract VjoSourceModulePage createVjoSourceModulePage();

	public void addPages() {
		super.addPages();

		page = createVjoSourceModulePage();
		page.init(getSelection());
		addPage(page);
	}

	public IModelElement getCreatedElement() {
		return module;
	}

	protected void finishPage(IProgressMonitor monitor)
			throws InterruptedException, CoreException {
		modifierIndex = page.getModifiers();
		module = page.createFile(monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewElementWizard#performFinish()
	 */
	public boolean performFinish() {
		final boolean result = super.performFinish();
		if (result && module != null) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						EditorUtility.openInEditor(module);
					} catch (PartInitException e) {
						DLTKUIPlugin
								.logErrorMessage(
										MessageFormat
												.format(
														Messages.NewSourceModuleWizard_errorInOpenInEditor,
														new Object[] { module
																.getElementName() }),
										e);
					} catch (ModelException e) {
						DLTKUIPlugin
								.logErrorMessage(
										MessageFormat
												.format(
														Messages.NewSourceModuleWizard_errorInOpenInEditor,
														new Object[] { module
																.getElementName() }),
										e);
					}
				}
			});
		}
		return result;
	}

	/**
	 * Before finish this wizard, get the selected modifier.
	 * 
	 * @return
	 */
	public String getModifierIndex() {
		return modifierIndex;
	}

	public VjoSourceModulePage getPage() {
		return page;
	}
}
