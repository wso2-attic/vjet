/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.comment.VjoCcCommentUtil;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.PreferenceConstants;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.dltk.mod.ui.text.completion.ScriptTextMessages;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

public class VjoCommentCompletionProposalComputer extends
		ScriptCompletionProposalComputer {

	private String fErrorMessage;

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		return null;
	}

	// Script language specific completion proposals like types or keywords
	protected List computeScriptCompletionProposals(int offset,
			ScriptContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		IDocument document = context.getDocument();
		IEditorPart editor = context.getEditor();

		String commentTxt = "";
		int relativeCursorPos = offset;
		int length = 0;
		try {
			ITypedRegion region = TextUtilities.getPartition(document,
					IJavaScriptPartitions.JS_PARTITIONING, offset, true);
			relativeCursorPos = offset - region.getOffset();
			length = region.getLength();
			commentTxt = document.get(region.getOffset(), region.getLength());
			if (!VjoCcCommentUtil.isVjoComment(commentTxt, relativeCursorPos)) {
				return Collections.EMPTY_LIST;
			}
		} catch (BadLocationException e) {
			VjetPlugin
					.error("The position is not correct!", e, IStatus.WARNING);
			return Collections.emptyList();
		}
		// Source module getting
		if (relativeCursorPos >= length) {
			return Collections.emptyList();
		}

		ISourceModule sourceModule = context.getSourceModule();
		if (sourceModule == null) {
			return Collections.EMPTY_LIST;
		}
		VjoSourceModule vjoSourceModule = null;
		if (sourceModule instanceof VjoSourceModule) {
			vjoSourceModule = (VjoSourceModule) sourceModule;
		} else {
			return Collections.emptyList();
		}
		// VjoParserToJstAndIType parser = new VjoParserToJstAndIType();
		// IJstType type = parser.parse(vjoSourceModule.getGroupName(),
		// new String(vjoSourceModule.getFileName()),
		// vjoSourceModule.getSourceContents(), offset).getType();
		//
		// TypeSpaceMgr.parser().resolve(type);
		IVjoCcEngine engine = new VjoCcEngine(TypeSpaceMgr.parser());

		String groupName = CodeassistUtils.getGroupName(vjoSourceModule);
		List<IVjoCcProposalData> list = engine.completeComment(groupName,
				new String(vjoSourceModule.getFileName()), vjoSourceModule
						.getSourceContents(), offset, commentTxt,
				relativeCursorPos);
		// printProposal(list);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		CodeCompletionUtils.printProposal(list);
		VjoProposalEclipsePresenter presenter = new VjoProposalEclipsePresenter(
				engine.getContext(), offset, context.getViewer()
						.getSelectedRange(), context.getDocument());
		return presenter.doPresenter(list);
	}

	private void handleCodeCompletionException(ModelException e,
			ScriptContentAssistInvocationContext context) {
		ISourceModule module = context.getSourceModule();
		Shell shell = context.getViewer().getTextWidget().getShell();
		if (e.isDoesNotExist()
				&& !module.getScriptProject().isOnBuildpath(module)) {
			IPreferenceStore store = DLTKUIPlugin.getDefault()
					.getPreferenceStore();
			boolean value = store
					.getBoolean(PreferenceConstants.NOTIFICATION_NOT_ON_BUILDPATH_MESSAGE);
			if (!value) {
				MessageDialog
						.openInformation(
								shell,
								ScriptTextMessages.CompletionProcessor_error_notOnBuildPath_title,
								ScriptTextMessages.CompletionProcessor_error_notOnBuildPath_message);
			}
			store.setValue(
					PreferenceConstants.NOTIFICATION_NOT_ON_BUILDPATH_MESSAGE,
					true);
		} else
			ErrorDialog
					.openError(
							shell,
							ScriptTextMessages.CompletionProcessor_error_accessing_title,
							ScriptTextMessages.CompletionProcessor_error_accessing_message,
							e.getStatus());
	}

	public String getErrorMessage() {
		return fErrorMessage;
	}

}
