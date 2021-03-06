/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.preferences;

import java.io.InputStream;

import org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.mod.javascript.internal.ui.editor.JavaScriptDocumentSetupParticipant;
import org.eclipse.dltk.mod.javascript.internal.ui.text.SimpleJavascriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.javascript.ui.JavascriptPreferenceConstants;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.preferences.AbstractScriptEditorColoringConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.mod.ui.preferences.PreferencesMessages;
import org.eclipse.dltk.mod.ui.text.IColorManager;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.ITextEditor;

public class JavascriptEditorColoringConfigurationBlock extends
		AbstractScriptEditorColoringConfigurationBlock implements
		IPreferenceConfigurationBlock {

	private static final String PREVIEW_FILE_NAME = "PreviewFile.txt";

	private static final String[][] fSyntaxColorListModel = new String[][] {
			{
					PreferencesMessages.DLTKEditorPreferencePage_singleLineComment,
					JavascriptPreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR,
					sCommentsCategory },
			{ PreferencesMessages.DLTKEditorPreferencePage_CommentTaskTags,
					JavascriptPreferenceConstants.COMMENT_TASK_TAGS,
					sCommentsCategory },
			{ PreferencesMessages.DLTKEditorPreferencePage_keywords,
					JavascriptPreferenceConstants.EDITOR_KEYWORD_COLOR,
					sCoreCategory },

			{ PreferencesMessages.DLTKEditorPreferencePage_strings,
					JavascriptPreferenceConstants.EDITOR_STRING_COLOR,
					sCoreCategory },

			{ PreferencesMessages.DLTKEditorPreferencePage_numbers,
					JavascriptPreferenceConstants.EDITOR_NUMBER_COLOR,
					sCoreCategory },

			{
					PreferencesMessages.DLTKEditorPreferencePage_function_colors,
					JavascriptPreferenceConstants.EDITOR_FUNCTION_DEFINITION_COLOR,
					sCoreCategory },

			{ "XML Tag Name",
					JavascriptPreferenceConstants.EDITOR_XML_TAG_NAME_COLOR,
					"XML" },
			{ "XML Attribute Name",
					JavascriptPreferenceConstants.EDITOR_XML_ATTR_NAME_COLOR,
					"XML" },
			{ "XML Comment",
					JavascriptPreferenceConstants.EDITOR_XML_COMMENT_COLOR,
					"XML" },
	// { "XML Area Background
	// color",JavascriptPreferenceConstants.EDITOR_XML_BODY_ALL
	// , "XML"},
	};

	public JavascriptEditorColoringConfigurationBlock(
			OverlayPreferenceStore store) {
		super(store);
	}

	protected String[] getCategories() {
		return new String[] { sCoreCategory, sDocumentationCategory,
				sCommentsCategory, "XML" };
	}

	protected String[][] getSyntaxColorListModel() {
		return fSyntaxColorListModel;
	}

	protected ProjectionViewer createPreviewViewer(Composite parent,
			IVerticalRuler verticalRuler, IOverviewRuler overviewRuler,
			boolean showAnnotationsOverview, int styles, IPreferenceStore store) {
		return new ScriptSourceViewer(parent, verticalRuler, overviewRuler,
				showAnnotationsOverview, styles, store);
	}

	protected ScriptSourceViewerConfiguration createSimpleSourceViewerConfiguration(
			IColorManager colorManager, IPreferenceStore preferenceStore,
			ITextEditor editor, boolean configureFormatter) {
		return new SimpleJavascriptSourceViewerConfiguration(colorManager,
				preferenceStore, editor, IJavaScriptPartitions.JS_PARTITIONING,
				configureFormatter);
	}

	protected void setDocumentPartitioning(IDocument document) {
		JavaScriptDocumentSetupParticipant participant = new JavaScriptDocumentSetupParticipant();
		participant.setup(document);
	}

	protected InputStream getPreviewContentReader() {
		return getClass().getResourceAsStream(PREVIEW_FILE_NAME);
	}
}
