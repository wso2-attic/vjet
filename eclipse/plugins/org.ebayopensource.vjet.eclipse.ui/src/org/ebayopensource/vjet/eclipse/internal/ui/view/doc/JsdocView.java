/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.view.doc;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalAditionalInfoGenerator;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjet.eclipse.ui.VjetUIUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceReference;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.infoviews.AbstractDocumentationView;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.progress.UIJob;

/**
 * View for js documentation.
 * 
 *  Ouyang
 * 
 */
public class JsdocView extends AbstractDocumentationView {

	private ISelection	m_selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.infoviews.AbstractDocumentationView#getNature()
	 */
	@Override
	protected String getNature() {
		return VjoNature.NATURE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.infoviews.AbstractDocumentationView#getPreferenceStore()
	 */
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return VjetUIPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.infoviews.AbstractDocumentationView#setInput(java.lang.Object)
	 */
	@Override
	protected void setInput(final Object input) {
		new UIJob(getViewSite().getShell().getDisplay(), "Jsdoc Update Job") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				VjoEditor vjoEditor = VjetUIPlugin.getVjoEditor();
				if (vjoEditor == null) {
					return Status.CANCEL_STATUS;
				}
				Object inputSrc = getInput();
				if (!(inputSrc instanceof IModelElement)) {
					return Status.CANCEL_STATUS;
				}
				IModelElement selectedElement = (IModelElement) inputSrc;
				if (!(vjoEditor.getInputModelElement() instanceof IVjoSourceModule)) {
					return Status.CANCEL_STATUS;
				}
				ISourceModule sm = (ISourceModule) vjoEditor
						.getInputModelElement();
				int offset = getOffset(selectedElement);
				if (offset == -1) {
					return Status.CANCEL_STATUS;
				}
				VjoSelectionEngine selectionEngine = VjetUIUtils
						.getSelectionEngine();
				IJstNode selectedJstNode = selectionEngine
						.convertSelection2JstNode(
								(org.eclipse.dltk.mod.compiler.env.ISourceModule) sm,
								offset, offset);
				String javadocHtml = VjoProposalAditionalInfoGenerator
						.getAdditionalPropesalInfo(selectedJstNode);
				if ((javadocHtml == null) || (0 == javadocHtml.length())) {
					javadocHtml = input.toString();
				}
				Control control = getControl();
				if (control instanceof Browser) {
					((Browser) control).setText(javadocHtml);
				}
				return Status.OK_STATUS;
			}

			private int getOffset(IModelElement selectedElement) {
				int offset = -1;
				if (selectedElement instanceof ISourceReference) {
					try {
						offset = ((ISourceReference) selectedElement)
								.getSourceRange().getOffset();
					} catch (ModelException e) {
						VjetUIPlugin.log(e);
					}
				}
				// handle text selection on native vjo type/property/method
				if ((offset == -1) && (m_selection instanceof ITextSelection)) {
					offset = ((ITextSelection) m_selection).getOffset();
				}
				return offset;
			}
		}.schedule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.infoviews.AbstractInfoView#selectionChanged(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		ISelectionProvider selectionProvider = part.getSite()
				.getSelectionProvider();
		this.m_selection = selectionProvider != null ? selectionProvider
				.getSelection() : selection;
		super.selectionChanged(part, selection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.infoviews.AbstractDocumentationView#getControl()
	 */
	@Override
	public Control getControl() {
		return super.getControl();
	}

}
