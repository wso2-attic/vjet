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
package org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref;

import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * vjet debugger attachment configuration block 
 * 
 * 
 *
 */
public class DebuggerAttachmentConfigurationBlock extends
		AbstractConfigurationBlock {
	private Button attachCheckBox;
	
	private Group opitionsGroup;
	private Button breakCheckBox;
	private Button consoleCheckBox;
	private Button loggingCheckBox;
	
	public DebuggerAttachmentConfigurationBlock(OverlayPreferenceStore store) {
		super(store);
		
		store.addKeys(this.createKeys());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public Control createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite= new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		
		this.attachCheckBox = addCheckBox(composite, "Attach VJET debugger to Java based project",VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH, 0);
		this.attachCheckBox.addSelectionListener(new Selectionhandler());
		
		this.opitionsGroup = new Group(composite, SWT.NONE);
		opitionsGroup.setLayout(new GridLayout(2, false));
		opitionsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		this.breakCheckBox = addCheckBox(opitionsGroup, "Break on first line", VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_BREAK_FIRST_LINE, 10);
		this.consoleCheckBox = addCheckBox(opitionsGroup, "Use interactive console", VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_USE_INTERACTIVE_CONSOLE, 10);
		this.loggingCheckBox = addCheckBox(opitionsGroup, "Enable DBGP logging", VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_ENABLE_DBGP_LOGGING, 10);
		return composite;
	}
	
	@Override
	public void performDefaults() {
		super.performDefaults();
		
		this.attachCheckBox.setSelection(false);
		
		this.opitionsGroup.setEnabled(false);
		this.breakCheckBox.setSelection(false);
		this.breakCheckBox.setEnabled(false);
		this.consoleCheckBox.setSelection(false);
		this.consoleCheckBox.setEnabled(false);
		this.loggingCheckBox.setSelection(false);
		this.loggingCheckBox.setEnabled(false);
	}

	private OverlayKey[] createKeys() {
		OverlayKey attachDebuggerKey = new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH);
		OverlayKey breakFristLineKey = new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_BREAK_FIRST_LINE);
		OverlayKey consoleKey = new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_USE_INTERACTIVE_CONSOLE);
		OverlayKey loggingKey = new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.BOOLEAN, VjetDebugPreferenceConstants.PREF_VJET_DEBUGGER_ATTACH_ENABLE_DBGP_LOGGING);
		
		return new OverlayKey[] {attachDebuggerKey, breakFristLineKey, consoleKey, loggingKey};
	}
	
	private void handleSelection(SelectionEvent e) {
		if (this.attachCheckBox == e.getSource()) {
			if (this.attachCheckBox.getSelection()) {
				this.opitionsGroup.setEnabled(true);
				this.breakCheckBox.setEnabled(true);
				this.consoleCheckBox.setEnabled(true);
				this.loggingCheckBox.setEnabled(true);
			}
			else {
				this.opitionsGroup.setEnabled(false);
				this.breakCheckBox.setSelection(false);
				this.breakCheckBox.setEnabled(false);
				this.consoleCheckBox.setSelection(false);
				this.consoleCheckBox.setEnabled(false);
				this.loggingCheckBox.setSelection(false);
				this.loggingCheckBox.setEnabled(false);
			}
		}
	}
	
	private class Selectionhandler implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			handleSelection(e);
		}
		
		public void widgetSelected(SelectionEvent e) {
			handleSelection(e);
		}
	}
	
}
