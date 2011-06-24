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
package org.ebayopensource.vjet.eclipse.internal.ui.text;

import org.ebayopensource.vjet.eclipse.internal.ui.editor.semantic.highlighting.VjoSemanticHighlightings;
import org.eclipse.dltk.mod.javascript.internal.ui.text.JavascriptTextTools;
import org.eclipse.dltk.mod.ui.editor.highlighting.SemanticHighlighting;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 
 * 
 */
// FIXME replace this extends by ScriptTextTools class
public class VjoTextTools extends JavascriptTextTools {

//	private final class VjoSH extends SemanticHighlighting {
//		private String m_bgColor;
//
//		private String m_preferenceKey;
//
//		public VjoSH(String editorXmlTagNameColor, String bgColor) {
//			this.m_preferenceKey = editorXmlTagNameColor;
//			this.m_bgColor = bgColor;
//		}
//
//		@Override
//		public String getBackgroundPreferenceKey() {
//			return m_bgColor;
//		}
//
//		@Override
//		public String getPreferenceKey() {
//			return m_preferenceKey;
//		}
//
//	}

	

	public VjoTextTools(boolean autoDisposeOnDisplayDispose) {
		super(autoDisposeOnDisplayDispose);
	      
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.ScriptTextTools#createSourceViewerConfiguraton(org.eclipse.jface.preference.IPreferenceStore,
	 *      org.eclipse.ui.texteditor.ITextEditor, java.lang.String)
	 */
	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		return new VjoSourceViewerConfiguration(getColorManager(),
				preferenceStore, editor, partitioning);
	}

	@Override
	// TODO remove this method or add implementation
	public IPartitionTokenScanner getPartitionScanner() {
		return super.getPartitionScanner();
	}

	@Override
	public SemanticHighlighting[] getSemanticHighlightings() {
		
		//do not need support xml tag
//		return new SemanticHighlighting[] {
//				// FIXME change to VJO constants
//				new VjoSH(VjetPreferenceConstants.EDITOR_XML_TAG_NAME_COLOR,
//						null),
//				new VjoSH(VjetPreferenceConstants.EDITOR_XML_ATTR_NAME_COLOR,
//						null),
//				new VjoSH(VjetPreferenceConstants.EDITOR_XML_COMMENT_COLOR,
//						null), };
		
		 return VjoSemanticHighlightings.getSemanticHighlightings();
		
		
	}
}
