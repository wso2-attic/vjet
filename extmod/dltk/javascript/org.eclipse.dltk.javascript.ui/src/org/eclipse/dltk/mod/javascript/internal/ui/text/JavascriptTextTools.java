/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.text;

import org.eclipse.dltk.mod.javascript.ui.JavascriptPreferenceConstants;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.editor.highlighting.ISemanticHighlighter;
import org.eclipse.dltk.mod.ui.editor.highlighting.SemanticHighlighting;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.texteditor.ITextEditor;

public class JavascriptTextTools extends ScriptTextTools {

	private final class SH extends SemanticHighlighting {

		private String preferenceKey;

		public String getBackgroundPreferenceKey() {
			return bgColor;
		}

		private String bgColor;

		public SH(String editorXmlTagNameColor, String bgColor) {
			this.preferenceKey = editorXmlTagNameColor;
			this.bgColor = bgColor;
		}

		public String getPreferenceKey() {
			return preferenceKey;
		}

		public RGB getDefaultDefaultTextColor() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isBoldByDefault() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isItalicByDefault() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	private IPartitionTokenScanner fPartitionScanner;

	private final static String[] LEGAL_CONTENT_TYPES = new String[] {
			IJavaScriptPartitions.JS_STRING,
			IJavaScriptPartitions.JS_SINGLE_COMMENT,
			IJavaScriptPartitions.JS_MULTI_COMMENT,
			IJavaScriptPartitions.JS_DOC };

	public JavascriptTextTools(boolean autoDisposeOnDisplayDispose) {
		super(IJavaScriptPartitions.JS_PARTITIONING, LEGAL_CONTENT_TYPES,
				autoDisposeOnDisplayDispose);
		fPartitionScanner = new JavascriptPartitionScanner();
	}

	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		return new JavascriptSourceViewerConfiguration(getColorManager(),
				preferenceStore, editor, partitioning);
	}

	public IPartitionTokenScanner getPartitionScanner() {
		return fPartitionScanner;
	}

	public SemanticHighlighting[] getSemanticHighlightings() {
		return new SemanticHighlighting[] {
				new SH(JavascriptPreferenceConstants.EDITOR_XML_TAG_NAME_COLOR,
						null),
				new SH(
						JavascriptPreferenceConstants.EDITOR_XML_ATTR_NAME_COLOR,
						null),
				new SH(JavascriptPreferenceConstants.EDITOR_XML_COMMENT_COLOR,
						null), };
	}

	public ISemanticHighlighter getSemanticPositionUpdater() {
		return new JavaScriptPositionUpdater();
	}

}
