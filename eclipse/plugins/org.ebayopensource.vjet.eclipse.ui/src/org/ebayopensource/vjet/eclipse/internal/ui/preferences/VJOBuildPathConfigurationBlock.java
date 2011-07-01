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
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 
 *
 */
class VJOBuildPathConfigurationBlock extends AbstractConfigurationBlock {
	private Button fFoldersAsSourceFolder;
	private Button fProjectAsSourceFolder;
	
	private Label fSrcFolderNameLabel;
	private Text fSrcFolderNameText;
	
	private SelectionListener fSelectionListener;
	private ModifyListener fModifyListener;
	
	private static final String SRCBIN_FOLDERS_IN_NEWPROJ = VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ;
	private static final String SRCBIN_SRCNAME = VjetPreferenceConstants.SRC_SRCNAME;
	
	
	public VJOBuildPathConfigurationBlock(OverlayPreferenceStore store) {
		super(store);
		
		store.addKeys(this.createOverlayStoreKeys());
		
		this.fSelectionListener =  new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() == fProjectAsSourceFolder) {
					fSrcFolderNameLabel.setEnabled(false);
					fSrcFolderNameText.setEnabled(false);
				}
				else {
					fSrcFolderNameLabel.setEnabled(true);
					fSrcFolderNameText.setEnabled(true);
				}
			}
		};
		
		this.fModifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//TODO: validate source folder name
			}
		};
	}
	
	private OverlayKey[] createOverlayStoreKeys() {
		List<OverlayKey> overlayKeys = new ArrayList<OverlayKey>();
		overlayKeys.add(new OverlayKey(OverlayPreferenceStore.BOOLEAN,
				VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ));
		overlayKeys.add(new OverlayKey(OverlayPreferenceStore.STRING,
				VjetPreferenceConstants.SRC_SRCNAME));
		return overlayKeys.toArray(new OverlayKey[overlayKeys.size()]);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public Control createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		
		Group sourceFolderGroup= new Group(composite, SWT.NONE);
		sourceFolderGroup.setLayout(new GridLayout(2, false));
		sourceFolderGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sourceFolderGroup.setText(VjetPreferenceMessages.NewVJOProjectPreferencePage_sourcefolder_label); 
		
		this.fProjectAsSourceFolder = addRadioButton(sourceFolderGroup, VjetPreferenceMessages.NewVJOProjectPreferencePage_sourcefolder_project, SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.FALSE, 0);
		this.fProjectAsSourceFolder.addSelectionListener(this.fSelectionListener);
		
		this.fFoldersAsSourceFolder = addRadioButton(sourceFolderGroup, VjetPreferenceMessages.NewVJOProjectPreferencePage_sourcefolder_folder, SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.TRUE, 0);
		this.fFoldersAsSourceFolder.addSelectionListener(this.fSelectionListener);
		this.fSrcFolderNameLabel = new Label(sourceFolderGroup, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		gridData.horizontalIndent = 10;
		this.fSrcFolderNameLabel.setLayoutData(gridData);
		this.fSrcFolderNameLabel.setText(VjetPreferenceMessages.NewVJOProjectPreferencePage_folders_src);
		this.fSrcFolderNameText = addTextControl(sourceFolderGroup, this.fSrcFolderNameLabel, SRCBIN_SRCNAME, 10);
		this.fSrcFolderNameText.addModifyListener(this.fModifyListener);
		
		return composite;
	}
	
	private Button addRadioButton(Composite parent, String label, String key, String value, int indent) { 
		GridData gd= new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan= 2;
		gd.horizontalIndent= indent;
		
		Button button= new Button(parent, SWT.RADIO);
		button.setText(label);
		button.setLayoutData(gd);
		button.setSelection(value.equals(getPreferenceStore().getString(key)));
		return button;
	}
	
	private Text addTextControl(Composite parent, Label labelControl, String key, int indent) {
		GridData gd= new GridData();
		gd.horizontalIndent= indent;
		
		labelControl.setLayoutData(gd);
		
		gd= new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint= convertWidthInCharsToPixels(30);
		
		Text text= new Text(parent, SWT.SINGLE | SWT.BORDER);
		text.setText(getPreferenceStore().getString(key));
		text.setLayoutData(gd);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock#performDefaults()
	 */
	public void performDefaults() {
		boolean folderAsSrc = this.getPreferenceStore().getDefaultBoolean(VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ);
		if (folderAsSrc) {
			this.fFoldersAsSourceFolder.setSelection(true);
			this.fProjectAsSourceFolder.setSelection(false);
			String srcFolderName = this.getPreferenceStore().getDefaultString(VjetPreferenceConstants.SRC_SRCNAME);
			this.fSrcFolderNameText.setText(srcFolderName);
		}
		else {
			this.fFoldersAsSourceFolder.setSelection(false);
			this.fProjectAsSourceFolder.setSelection(true);
			this.fSrcFolderNameText.setText("");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock#performOk()
	 */
	public void performOk() {
		if (this.fFoldersAsSourceFolder.getSelection()) {
			this.getPreferenceStore().setValue(VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.TRUE);
			this.getPreferenceStore().setValue(VjetPreferenceConstants.SRC_SRCNAME, this.fSrcFolderNameText.getText().trim());
		}
		else {
			this.getPreferenceStore().setValue(VjetPreferenceConstants.SRCBIN_FOLDERS_IN_NEWPROJ, IPreferenceStore.FALSE);
			this.getPreferenceStore().setValue(VjetPreferenceConstants.SRC_SRCNAME, "");
		}
	}
	
}
