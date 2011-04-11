/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui;

import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.javascript.core.IJavaScriptConstants;
import org.eclipse.dltk.mod.javascript.core.JavaScriptLanguageToolkit;
import org.eclipse.dltk.mod.javascript.internal.ui.text.SimpleJavascriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.dltk.mod.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;

public class JavaScriptUILanguageToolkit implements IDLTKUILanguageToolkit {
	private static ScriptElementLabels sInstance = new ScriptElementLabels() {};

	public ScriptElementLabels getScriptElementLabels() {
		return sInstance;
	}

	public IPreferenceStore getPreferenceStore() {
		return JavaScriptUI.getDefault().getPreferenceStore();
	}

	public IDLTKLanguageToolkit getCoreToolkit() {
		return JavaScriptLanguageToolkit.getDefault();
	}

	public IDialogSettings getDialogSettings() {
		return JavaScriptUI.getDefault().getDialogSettings();
	}
	public String getEditorId(Object inputElement) {
		return "org.eclipse.dltk.mod.javascript.ui.editor.JavascriptEditor";
	}
	public String getPartitioningId() {
		return IJavaScriptConstants.JS_PARTITIONING;
	}

	public String getInterpreterContainerId() {
		return "org.eclipse.dltk.mod.javascript.launching.INTERPRETER_CONTAINER";
	}

	public ScriptUILabelProvider createScriptUILabelProvider() {
		return null;
	}

	public boolean getProvideMembers(ISourceModule element) {
		return true;
	}
	
	public ScriptTextTools getTextTools() {
		return JavaScriptUI.getDefault().getTextTools();
	}
	
	public ScriptSourceViewerConfiguration createSourceViewerConfiguration() {
		return new SimpleJavascriptSourceViewerConfiguration(getTextTools()
				.getColorManager(), getPreferenceStore(), null,
				getPartitioningId(), false);
	}

	public String getInterpreterPreferencePage() {
		return "org.eclipse.dltk.mod.debug.ui.JavaScriptInterpreters";
	}

	public String getDebugPreferencePage() {
		return "org.eclipse.dltk.mod.javascript.preferences.debug";
	}

	private static final String[] EDITOR_PREFERENCE_PAGES_IDS = {
		"org.eclipse.dltk.mod.javascript.ui.EditorPreferences", 
		"org.eclipse.dltk.mod.javascript.ui.editor.SyntaxColoring", 
		"org.eclipse.dltk.mod.javascript.ui.editor.SmartTyping", 
		"org.eclipse.dltk.mod.javascript.ui.editor.JavascriptFolding", 
		"javascriptTemplatePreferencePage" 
	};
	
	public String[] getEditorPreferencePages() {
		return EDITOR_PREFERENCE_PAGES_IDS;
	}
}
