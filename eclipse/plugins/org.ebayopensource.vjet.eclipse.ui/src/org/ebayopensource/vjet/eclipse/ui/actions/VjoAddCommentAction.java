/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui.actions;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalLabelUtil;
import org.ebayopensource.vjet.eclipse.ui.VjetUIUtils;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;

public class VjoAddCommentAction extends SelectionDispatchAction {

	private VjoEditor m_Editor;

	public VjoAddCommentAction(IWorkbenchSite site) {
		super(site);
		setText("Add Method Annotation");
	}

	public VjoAddCommentAction(VjoEditor editor) {
		this(editor.getEditorSite());
		this.m_Editor = editor;
		setEnabled(checkEnabledEditor());
	}

	private boolean checkEnabledEditor() {
		return true;
	}

	@Override
	public void run() {
		super.run();
	}

	@Override
	public void run(IStructuredSelection selection) {
		super.run(selection);
	}

	@Override
	public void run(ITextSelection selection) {
		IJstType jstType = VjetUIUtils.getJstType(m_Editor);
		if (jstType == null) {
			// do nothing
			return;
		}
		IJstNode node = JstUtil.getLeafNode(jstType, selection.getOffset(),
				selection.getOffset() + selection.getLength(), false, true);
		if (node == null) {
			return;
		}
		node = VjetUIUtils.getCommentableJstNode(node);
		// add by patrick
		if (node == null) {
			return;
		}
		// end add
		IDocument document = VjetUIUtils.getDocument(m_Editor);
		String text = "";
		int offset = -1;
		if (node instanceof JstVars) {
			text = VjetUIUtils.getCommentText((JstVars) node);
			offset = VjetUIUtils.getCommentOffset((JstVars) node, document);
			if (StringUtils.isBlankOrEmpty(text) || offset == -1) {
				return;
			}
			text = "//<" + text;
		} else if (node instanceof IJstProperty) {
			text = VjetUIUtils.getCommentText((IJstProperty) node);
			offset = VjetUIUtils.getCommentOffset((IJstProperty) node, document);
			if (StringUtils.isBlankOrEmpty(text) || offset == -1) {
				return;
			}
			text = "//<" + text;
		} else if (node instanceof IJstMethod) {
			text = VjetUIUtils.getCommentText((IJstMethod) node);
			offset = VjetUIUtils.getCommentOffset((IJstMethod) node, document);
			if (StringUtils.isBlankOrEmpty(text) || offset == -1) {
				return;
			}
			text = TextUtilities.getDefaultLineDelimiter(document) + "//> " + text;
			text = VjoProposalLabelUtil.evaluateIndent(text,
					document, offset);
		} else {
			// do nothing
		}
		try {
			VjetUIUtils.performChange(m_Editor, text, offset);
		} catch (CoreException e) {
			// do nothing
		}
	}



	/*
	 * Method declared on SelectionDispatchAction
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(checkEnabledEditor());
	}



}
