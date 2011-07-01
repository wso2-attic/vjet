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

import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_abstract;
import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_browse;
import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_superclass;
import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_superclass_selection;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoOpenTypeSelectionDialog;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.dialogfields.CheckBoxDialogField;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.viewsupport.IViewPartInputProvider;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.ContentOutline;

/**
 * The page for creating a new vjo class.
 * 
 */
public final class VjoClassCreationPage extends VjoSourceModulePage {

	private CheckBoxDialogField isAbstractButton;
	protected StringButtonDialogField superclassField;
	private CheckBoxDialogField mainStubsButton;
	private CheckBoxDialogField constructorStubsButton;
	// private SelectionButtonDialogFieldGroup fMethodStubsButtons;
	private IJstType baseType;

	/**
	 * @param vjoClassCreationWizard
	 */
	public VjoClassCreationPage() {
		super();
		fCurrSuperTypeCompletionProcessor = new VjoSuperTypeCompletionProcessor();
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IHelpContextIds.NEW_CTYPE);
	}
	
	@Override
	protected String getPageDescription() {
		return VjetWizardMessages.ClassCreationWizard_page_description;
	}

	@Override
	protected String getPageTitle() {
		return VjetWizardMessages.ClassCreationWizard_page_title;
	}

	@Override
	protected String getRequiredNature() {
		return VjoNature.NATURE_ID;
	}

	@Override
	protected String getPageType() {
		return "ctype";
	}

	@Override
	protected void createFileControls(Composite parent, int columns) {
		super.createFileControls(parent, columns);
		// this.createAbstractButton(parent, columns);
		this.createSuperClassField(parent, columns);
		// this.createMethodStubButton(parent, columns);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.NewContainerWizardPage#getInitialScriptElement(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	protected IModelElement getInitialScriptElement(
			IStructuredSelection selection) {
		IModelElement scriptElement = null;

		// Check selection
		if (selection != null && !selection.isEmpty()) {
			Object selectedElement = selection.getFirstElement();
			// Check for adapters
			if (selectedElement instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) selectedElement;
				scriptElement = (IModelElement) adaptable
						.getAdapter(IModelElement.class);
				if (scriptElement == null) {
					IResource resource = (IResource) adaptable
							.getAdapter(IResource.class);
					scriptElement = (IModelElement) resource
							.getAdapter(IModelElement.class);
					if (scriptElement == null) {
						scriptElement = DLTKCore.create(resource);
					}

				}
			} else {
				// scriptElement = selectedElement;
			}
		}

		// Check view
		if (scriptElement == null) {
			IWorkbenchPart part = DLTKUIPlugin.getActivePage().getActivePart();
			if (part instanceof ContentOutline) {
				part = DLTKUIPlugin.getActivePage().getActiveEditor();
			}

			if (part instanceof IViewPartInputProvider) {
				Object provider = ((IViewPartInputProvider) part)
						.getViewPartInput();
				if (provider instanceof IModelElement) {
					scriptElement = (IModelElement) provider;
				}
			}
		}

		if (scriptElement == null
				|| scriptElement.getElementType() == IModelElement.SCRIPT_MODEL) {
			try {
				IScriptProject[] projects = DLTKCore.create(getWorkspaceRoot())
						.getScriptProjects();
				if (projects.length == 1) {
					scriptElement = projects[0];
				}
			} catch (ModelException e) {
				DLTKUIPlugin.log(e);
			}
		}

		return scriptElement;
	}

	private boolean isPackageFragment(Object selectedElement) {
		return (selectedElement instanceof PackageFragment);
	}

	protected void createAbstractButton(Composite parent, int columns) {

		isAbstractButton = new CheckBoxDialogField(SWT.CHECK);
		isAbstractButton.setLabelText("");
		isAbstractButton.setCheckBoxText(ClassCreationWizard_abstract);
		isAbstractButton.doFillIntoGrid(parent, columns);
	}

	private VjoSuperTypeCompletionProcessor fCurrSuperTypeCompletionProcessor;

	protected void createSuperClassField(Composite parent, int columns) {
		SearchButtonAdapter adapter = new SearchButtonAdapter();
		superclassField = new StringButtonDialogField(adapter);
		superclassField.setLabelText(ClassCreationWizard_superclass);
		superclassField.setButtonLabel(ClassCreationWizard_browse);
		superclassField.doFillIntoGrid(parent, columns);
		superclassField.setDialogFieldListener(adapter);

		// Add by Oliver. 2009-06-17. Add the super type proposal for super type
		// text box.
		Text text = superclassField.getTextControl(parent);
		ControlContentAssistHelper.createTextContentAssistant(text,
				fCurrSuperTypeCompletionProcessor);
	}

	protected void createExtraControls(Composite parent, int columns) {
		Link link = new Link(parent, SWT.NONE);
		link.setText(VjetWizardMessages.ClassCreationWizard_methods_label);
		link.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false,
				false, columns, 1));
		mainStubsButton = new CheckBoxDialogField(SWT.CHECK);
		mainStubsButton
				.setCheckBoxText(VjetWizardMessages.ClassCreationWizard_methods_main);
		mainStubsButton.doFillIntoGrid(parent, columns);

		constructorStubsButton = new CheckBoxDialogField(SWT.CHECK);
		constructorStubsButton
				.setCheckBoxText(VjetWizardMessages.NewClassWizardPage_methods_constructors);
		constructorStubsButton.doFillIntoGrid(parent, columns);

	}

	public String getSuperclassFieldText() {
		return superclassField.getText();
	}

	public boolean isAbstractButtonSelected() {
		return isAbstractButton.isSelected();
	}

	public void setAbstractButton(boolean isAbstract) {
		if (isAbstractButton == null)
			isAbstractButton = new CheckBoxDialogField(SWT.CHECK);
		isAbstractButton.setSelection(isAbstract);
	}

	public void setSuperclassFieldText(String superclass) {
		this.superclassField.setText(superclass);
	}

	public void initializeSuperClassField() {
		if (this.superclassField == null) {
			SearchButtonAdapter adapter = new SearchButtonAdapter();
			this.superclassField = new StringButtonDialogField(adapter);
		}

	}

	public boolean isMethodStubsButtonSelected() {
		return mainStubsButton.isSelected();
	}

	public void setMethodStubGeneration(boolean isMethodStubGeneration) {
		if (mainStubsButton == null)
			mainStubsButton = new CheckBoxDialogField(SWT.CHECK);
		mainStubsButton.setSelection(isMethodStubGeneration);
	}

	public boolean isConstructorButtonSelected() {
		return constructorStubsButton.isSelected();
	}

	public void setConstructorStubGeneration(boolean isMethodStubGeneration) {
		if (constructorStubsButton == null)
			constructorStubsButton = new CheckBoxDialogField(SWT.CHECK);
		constructorStubsButton.setSelection(isMethodStubGeneration);
	}

	protected final class SearchButtonAdapter implements IStringButtonAdapter,
			IDialogFieldListener {

		public void changeControlPressed(DialogField field) {
			baseType = chooseSuperclass();
			if (baseType != null) {
				setSuperclassFieldText(baseType.getName());
			}
		}

		public void dialogFieldChanged(DialogField field) {
			// nothing
		}
	}

	/**
	 * Gets the parent class by active shell and returns the name of choosen
	 * class.
	 * 
	 * @return the name of super class.
	 */
	protected IJstType chooseSuperclass() {

		// String className = null;

		Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
		VjoOpenTypeSelectionDialog dialog = new VjoOpenTypeSelectionDialog(
				parent, true, PlatformUI.getWorkbench().getProgressService(),
				getSearchScope(), IDLTKSearchConstants.TYPE, this
						.getUILanguageToolkit());

		dialog.setShownTypeFlag(VjoOpenTypeSelectionDialog.SHOWCTYPEONLY);
		dialog.setTitle(getOpenTypeDialogTitle());
		dialog.setMessage(getOpenTypeDialogMessage());
		// Add by Oliver, 2009-02-25.
		// Fix bug--http://quickbugstage.arch.ebay.com/show_bug.cgi?id=2337
		String inputtedSuperClassName = getSuperclassFieldText();
		if (inputtedSuperClassName != null
				&& inputtedSuperClassName.trim().length() > 0) {
			dialog.setFilter(inputtedSuperClassName.trim());
		}

		int result = dialog.open();
		if (result != IDialogConstants.OK_ID)
			return null;

		Object[] types = dialog.getResult();
		if (types != null && types.length > 0) {
			IType type = null;
			type = (IType) types[0];
			return org.ebayopensource.vjet.eclipse.internal.core.util.Util.toJstType(type);

			// for (int i = 0; i < types.length; i++) {
			// type = (IJstType) types[i];
			// // IFile file = (IFile) type.getResource();
			// className = type.getName();
			// }
		}

		return null;
	}

	protected IDLTKSearchScope getSearchScope() {
		return null;
	}

	protected String getOpenTypeDialogTitle() {
		return ClassCreationWizard_superclass_selection;
	}

	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return DLTKUILanguageManager.getLanguageToolkit(VjoNature.NATURE_ID);
	}

	protected String getOpenTypeDialogMessage() {
		return DLTKUIMessages.OpenTypeAction_dialogMessage;
	}

	public IJstType getBaseType() {
		return baseType;
	}

}