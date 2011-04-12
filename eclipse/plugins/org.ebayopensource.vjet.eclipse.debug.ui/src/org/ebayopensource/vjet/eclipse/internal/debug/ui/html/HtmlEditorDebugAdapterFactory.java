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
package org.ebayopensource.vjet.eclipse.internal.debug.ui.html;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * 
 *
 */
public class HtmlEditorDebugAdapterFactory implements IAdapterFactory {
	/**
	 * WTP Html Content Type ID
	 */
	private static final String HTML_CONTENT_TYPE_ID = "org.eclipse.wst.html.core.htmlsource";

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (!(adaptableObject instanceof StructuredTextEditor))
			return null;
		
		try {
			StructuredTextEditor sseEditor = (StructuredTextEditor)adaptableObject;
			if (!this.isWtpHtmlEditor(sseEditor))
				return null;
			
			if (IToggleBreakpointsTarget.class == adapterType)
				return new HtmlScriptToggleBreakpointAdapter();
			else if (IRunToLineTarget.class == adapterType)
				return new HtmlScriptRunToLineAdapter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {IToggleBreakpointsTarget.class, IRunToLineTarget.class};
	}

	
	/**
	 * determine wether current wtp sse editor is html editor.
	 * 
	 * @param sseEditor
	 * @return
	 * @throws CoreException
	 */
	private boolean isWtpHtmlEditor(StructuredTextEditor sseEditor) throws CoreException{
		TextFileDocumentProvider fileDocumentProvider = (TextFileDocumentProvider)sseEditor.getDocumentProvider();
		IContentType contentType = fileDocumentProvider.getContentType(sseEditor.getEditorInput());
		
		//WTP SSE is designed for multi page content types, like jsp/html/...
		return HTML_CONTENT_TYPE_ID.equals(contentType.getId());
	}
}
