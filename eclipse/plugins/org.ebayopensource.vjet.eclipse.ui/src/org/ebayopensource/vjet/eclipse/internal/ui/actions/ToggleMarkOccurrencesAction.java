/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.actions;

import java.util.ResourceBundle;

import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditorMessages;
import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

/**
 * A toolbar action which toggles the
 * {@linkplain org.eclipse.dltk.mod.ui.PreferenceConstants#EDITOR_MARK_OCCURRENCES mark occurrences preference}.
 * 
 * @since 3.0
 */
public class ToggleMarkOccurrencesAction extends TextEditorAction implements
		IPropertyChangeListener {

	private IPreferenceStore fStore;

	/**
	 * Constructs and updates the action.
	 */
	public ToggleMarkOccurrencesAction(ITextEditor editor) {
		super(ResourceBundle.getBundle(VjoEditorMessages.BUNDLE_NAME),
				"ToggleMarkOccurrencesAction.", null, IAction.AS_CHECK_BOX); //$NON-NLS-1$
		super.setEditor(editor);
		DLTKPluginImages.setToolImageDescriptors(this, "mark_occurrences.gif"); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				VjetPreferenceConstants.TOGGLE_MARK_OCCURRENCES_ACTION);
		update();
	}

	/*
	 * @see IAction#actionPerformed
	 */
	public void run() {
		if (fStore == null)
			fStore = VjetUIPlugin.getDefault().getPreferenceStore();
		fStore.setValue(VjetPreferenceConstants.EDITOR_MARK_OCCURRENCES,
				isChecked());
	}

	/*
	 * @see TextEditorAction#update
	 */
	public void update() {
		ITextEditor editor = getTextEditor();

		boolean checked = false;
		if (editor instanceof VjoEditor)
			checked = ((VjoEditor) editor).isMarkingOccurrences();

		setChecked(checked);
		setEnabled(editor != null);
	}

	/*
	 * @see TextEditorAction#setEditor(ITextEditor)
	 */
	public void setEditor(ITextEditor editor) {

		super.setEditor(editor);

		if (editor != null) {

			if (fStore == null) {
				fStore = VjetUIPlugin.getDefault().getPreferenceStore();
				fStore.addPropertyChangeListener(this);
			}

		} else if (fStore != null) {
			fStore.removePropertyChangeListener(this);
			fStore = null;
		}

		update();
	}

	/*
	 * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(
				VjetPreferenceConstants.EDITOR_MARK_OCCURRENCES))
			setChecked(Boolean.valueOf(event.getNewValue().toString())
					.booleanValue());
	}
}
