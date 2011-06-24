/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import java.util.ArrayList;

import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.eclipse.dltk.mod.ui.PreferenceConstants;
import org.eclipse.dltk.mod.ui.preferences.CodeAssistConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.mod.ui.preferences.PreferencesMessages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class VjetCodeAssistConfigurationBlock extends CodeAssistConfigurationBlock{

	private Button fCompletion_useVj$;
	private Button fCompletion_appendComment;

	public VjetCodeAssistConfigurationBlock(PreferencePage mainPreferencePage,
			OverlayPreferenceStore store) {
		super(mainPreferencePage, store);
	}
	
	/**
	 * Creates page for appearance preferences.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the control for the preference page
	 */
	public Control createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout());

		Composite composite;

		composite = createSubsection(
				control,
				null,
				PreferencesMessages.CodeAssistConfigurationBlock_insertionSection_title);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		addInsertionSection(composite);
		
		
		composite = createSubsection(
				control,
				null,
				PreferencesMessages.CodeAssistConfigurationBlock_sortingSection_title);
		
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		//TOD
		addSortFilterSection(composite);

		composite = createSubsection(
				control,
				null,
				PreferencesMessages.CodeAssistConfigurationBlock_autoactivationSection_title);
		composite.setLayout(layout);
		addAutoActivationSection(composite);

		// createTabsGroup(control);

		return control;
	}
	
	@Override
	protected void addInsertionSection(Composite composite) {
		super.addInsertionSection(composite);
		String label;
		label = VjetPreferenceMessages.VJETEditorCodeAssistBlock_ReplacePackageNameWithVj$Automatically;
		fCompletion_useVj$ = addCheckBox(composite, label,
				VjetPreferenceConstants.CODEASSIST_USETHISVJ$, 2);
		label = VjetPreferenceMessages.VJETEditorCodeAssistBlock_AppendCommentAfetExpressionAutomatically;
		fCompletion_appendComment = addCheckBox(composite, label,
				VjetPreferenceConstants.CODEASSIST_APPENDCOMMENT, 2);
	}
	
	@Override
	protected void getOverlayKeys(ArrayList overlayKeys) {
		super.getOverlayKeys(overlayKeys);
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				VjetPreferenceConstants.CODEASSIST_USETHISVJ$));
		overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.BOOLEAN,
				VjetPreferenceConstants.CODEASSIST_APPENDCOMMENT));
		//Jack, because CODEASSIST_SORTER was added as boolean type. so here add the key again with String type
		overlayKeys.add(0, new OverlayPreferenceStore.OverlayKey(
				OverlayPreferenceStore.STRING,
				PreferenceConstants.CODEASSIST_SORTER));
	}
	
	@Override
	protected void initializeFields() {
		super.initializeFields();
//		fCompletion_useVj$.setSelection(getPreferenceStore().getBoolean(
//				VjetPreferenceConstants.CODEASSIST_USETHISVJ$));
//		fCompletion_appendComment.setSelection(getPreferenceStore().getBoolean(
//				VjetPreferenceConstants.CODEASSIST_APPENDCOMMENT));
	}

	private void addSortFilterSection(Composite composite) {

		String label;
		label= PreferencesMessages.CodeAssistConfigurationBlock_sortingSection_title;
//		ProposalSorterHandle[] sorters= ProposalSorterRegistry.getDefault().getSorters();
//		String[] labels= new String[sorters.length];
//		String[] values= new String[sorters.length];
//		for (int i= 0; i < sorters.length; i++) {
//			ProposalSorterHandle handle= sorters[i];
//			labels[i]= handle.getName();
//			values[i]= handle.getId();
//		}
		
		String[] labels= new String[]{ VjetPreferenceMessages.VJETEditorCodeAssistBlock_Sorter_Relevance,  VjetPreferenceMessages.VJETEditorCodeAssistBlock_Sorter_Alphabet};
		String[] values= new String[]{ VjetPreferenceMessages.VJETEditorCodeAssistBlock_Sorter_Relevance,  VjetPreferenceMessages.VJETEditorCodeAssistBlock_Sorter_Alphabet};
		addComboBox(composite, label, PreferenceConstants.CODEASSIST_SORTER, values, labels);
		
//		label= PreferencesMessages.CodeAssistConfigurationBlock_matchCamelCase_label;
//		addCheckBox(composite, label, PreferencesMessages.PREF_CODEASSIST_CAMEL_CASE_MATCH, enabledDisabled, 0);
		label= VjetPreferenceMessages.VJETEditorCodeAssistBlock_CamelMatch;
		addCheckBox(composite, label, VjetPreferenceConstants.CODEASSIST_CAMEL_MATCH, 0);
	
	}
	
}