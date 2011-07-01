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

import java.util.Observable;
import java.util.Observer;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.preferences.VJOBuildPathPreferencePage;
import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.dltk.mod.launching.IInterpreterInstall;
import org.eclipse.dltk.mod.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * extract class from VjetProjectCreationWizard support for src library
 * 
 * 
 * 
 */
class VJOProjectWizardFirstPage extends ProjectWizardFirstPage {
	private JavascriptInterpreterGroup fInterpreterGroup;
	private LayoutGroup layoutGroup;

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IHelpContextIds.NEW_PROJECT);
	}

    /*@Override
    protected void createInterpreterGroup(Composite parent) {
    	fInterpreterGroup = new JavascriptInterpreterGroup(parent);
    }

    @Override
    protected IInterpreterInstall getInterpreter() {
    	return fInterpreterGroup.getSelectedInterpreter();
    }

    @Override
    protected Observable getInterpreterGroupObservable() {
    	return fInterpreterGroup;
    }

    @Override
    protected void handlePossibleInterpreterChange() {
    	fInterpreterGroup.handlePossibleInterpreterChange();
    }

    @Override
    protected boolean interpeterRequired() {
    	return false;
    }

    @Override
    protected boolean supportInterpreter() {
    	return true;
    }*/

	/*
	 * Overriden by Kevin, support for project layout
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.ProjectWizardFirstPage#createCustomGroups(org.eclipse.swt.widgets.Composite)
	 */
	protected void createCustomGroups(Composite composite) {
		this.layoutGroup = new LayoutGroup(composite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.wizards.ProjectWizardFirstPage#isSrc()
	 */
	public boolean isSrc() {
		return this.layoutGroup.isSrc();
	}

	private final class JavascriptInterpreterGroup extends
			AbstractInterpreterGroup {

		public JavascriptInterpreterGroup(Composite composite) {
			super(composite);
		}

		@Override
		protected String getCurrentLanguageNature() {
			return VjoNature.NATURE_ID;
		}

		@Override
		protected String getIntereprtersPreferencePageId() {
			// Modify by Oliver. 2009-03-27. Fix the bug that can't open
			// the interpreter preference page.
			return "org.ebayopensource.vjet.eclipse.preferences.interpreter";
		}
	}

	/**
	 * a project layout for src folder configuration, similar to JDT
	 */
	private final class LayoutGroup implements Observer {

		private final SelectionButtonDialogField fStdRadio, fSrcRadio;
		private final Group fGroup;
		private final Link fPreferenceLink;

		public LayoutGroup(Composite composite) {

			fGroup = new Group(composite, SWT.NONE);
			fGroup.setFont(composite.getFont());
			fGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			fGroup.setLayout(initGridLayout(new GridLayout(3, false), true));
			fGroup
					.setText(VjetWizardMessages.NewScriptProjectWizardPage_LayoutGroup_title);

			fStdRadio = new SelectionButtonDialogField(SWT.RADIO);
			fStdRadio
					.setLabelText(VjetWizardMessages.NewScriptProjectWizardPage_LayoutGroup_option_oneFolder);

			fSrcRadio = new SelectionButtonDialogField(SWT.RADIO);
			fSrcRadio
					.setLabelText(VjetWizardMessages.NewScriptProjectWizardPage_LayoutGroup_option_separateFolders);

			fStdRadio.doFillIntoGrid(fGroup, 3);
			LayoutUtil
					.setHorizontalGrabbing(fStdRadio.getSelectionButton(null));

			fSrcRadio.doFillIntoGrid(fGroup, 2);

			fPreferenceLink = new Link(fGroup, SWT.NONE);
			fPreferenceLink
					.setText(VjetWizardMessages.NewScriptProjectWizardPage_LayoutGroup_link_description);
			fPreferenceLink.setLayoutData(new GridData(GridData.END,
					GridData.END, false, false));
			fPreferenceLink.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					String id = VJOBuildPathPreferencePage.ID;
					PreferencesUtil.createPreferenceDialogOn(getShell(), id,
							new String[] { id }, null).open();
				}

				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}
			});

			boolean useSrcBin = VjetUIPlugin.getDefault().getPreferenceStore()
					.getBoolean(
							VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ);
			fSrcRadio.setSelection(useSrcBin);
			fStdRadio.setSelection(!useSrcBin);
		}

		public void update(Observable o, Object arg) {
			final boolean detect = fDetectGroup.mustDetect();
			fStdRadio.setEnabled(!detect);
			fSrcRadio.setEnabled(!detect);
			fPreferenceLink.setEnabled(!detect);
			fGroup.setEnabled(!detect);
		}

		public boolean isSrc() {
			return fSrcRadio.isSelected();
		}
	}

    @Override
    protected void createInterpreterGroup(Composite parent) {
        // TODO Auto-generated method stub

    }

    @Override
    protected IInterpreterInstall getInterpreter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Observable getInterpreterGroupObservable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void handlePossibleInterpreterChange() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean interpeterRequired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean supportInterpreter() {
        // TODO Auto-generated method stub
        return false;
    }

}
