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

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoCompletionProcessor;
import org.ebayopensource.vjet.eclipse.internal.ui.typehierarchy.VjoHierarchyInformationControl;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjet.eclipse.ui.viewsupport.VjoLabelProvider;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.mod.internal.ui.text.ScriptElementProvider;
import org.eclipse.dltk.mod.javascript.internal.ui.text.JavascriptCodeScanner;
import org.eclipse.dltk.mod.javascript.internal.ui.text.JavascriptStringScanner;
import org.eclipse.dltk.mod.javascript.internal.ui.text.completion.JavaScriptContentAssistPreference;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.CodeFormatterConstants;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.mod.ui.text.IColorManager;
import org.eclipse.dltk.mod.ui.text.ScriptOutlineInformationControl;
import org.eclipse.dltk.mod.ui.text.ScriptPresentationReconciler;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.completion.ContentAssistPreference;
import org.eclipse.dltk.mod.ui.text.completion.ContentAssistProcessor;
import org.eclipse.dltk.mod.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 
 * 
 */
public class VjoSourceViewerConfiguration extends
		ScriptSourceViewerConfiguration {

	// FIXME replace this with other scanner
	private JavascriptCodeScanner m_codeScanner;

	private AbstractScriptScanner m_single_commentScanner;

	private AbstractScriptScanner m_multi_commentScanner;

	private AbstractScriptScanner m_stringScanner;

	private VjoTextTools m_textTools;

	private VjoJavaDocScanner mJavaDocScanner;

	private VjoDoubleClickSelector fVjoDoubleClickSelector;


	public void setModule(ISourceModule module) {
		if(m_codeScanner instanceof VjoCodeScanner){
			VjoCodeScanner s = (VjoCodeScanner)m_codeScanner;
			s.setModule(module);
		}
		
	}

	/**
	 * @param colorManager
	 * @param preferenceStore
	 * @param editor
	 * @param partitioning
	 */
	public VjoSourceViewerConfiguration(IColorManager colorManager,
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		super(colorManager, preferenceStore, editor, partitioning);
	}
	

	@Override
	protected void alterContentAssistant(ContentAssistant assistant) {
		IContentAssistProcessor scriptProcessor = new VjoCompletionProcessor(
				getEditor(), assistant, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(scriptProcessor,
				IDocument.DEFAULT_CONTENT_TYPE);

		ContentAssistProcessor singleLineProcessor = new VjoCompletionProcessor(
				getEditor(), assistant, IJavaScriptPartitions.JS_SINGLE_COMMENT);
		assistant.setContentAssistProcessor(singleLineProcessor,
				IJavaScriptPartitions.JS_SINGLE_COMMENT);

		ContentAssistProcessor multiLineProcessor = new VjoCompletionProcessor(
				getEditor(), assistant, IJavaScriptPartitions.JS_MULTI_COMMENT);
		assistant.setContentAssistProcessor(multiLineProcessor,
				IJavaScriptPartitions.JS_MULTI_COMMENT);

		ContentAssistProcessor stringProcessor = new VjoCompletionProcessor(
				getEditor(), assistant, IJavaScriptPartitions.JS_STRING);
		assistant.setContentAssistProcessor(stringProcessor,
				IJavaScriptPartitions.JS_STRING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration#getContentAssistPreference()
	 */
	@Override
	protected ContentAssistPreference getContentAssistPreference() {
		// TODO change this
		return JavaScriptContentAssistPreference.getDefault();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration#getOutlinePresenter(org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer,
	 *      boolean)
	 */
	public IInformationPresenter getOutlinePresenter(
			ScriptSourceViewer sourceViewer, boolean doCodeResolve) {
		IInformationPresenter informationPresenter = super.getOutlinePresenter(
				sourceViewer, doCodeResolve);
		IInformationProvider informationProvider = informationPresenter
				.getInformationProvider(IDocument.DEFAULT_CONTENT_TYPE);

		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IJavaScriptPartitions.JS_SINGLE_COMMENT);
		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IJavaScriptPartitions.JS_MULTI_COMMENT);

		// Add by Oliver. 2009-10-23. BEGIN. Fix bug that cursor is on the string
		// (type name, string parameter) can't pop up quick outline dialog.
		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IDocument.DEFAULT_CONTENT_TYPE);
		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IJavaScriptPartitions.JS_DOC);
		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IJavaScriptPartitions.JS_STRING);
		((InformationPresenter) informationPresenter).setInformationProvider(
				informationProvider, IJavaScriptPartitions.JS_PARTITIONING);
		// Add by Oliver. 2009-10-23. END.

		return informationPresenter;
	}

	@Override
	public IInformationPresenter getHierarchyPresenter(
			ScriptSourceViewer sourceViewer, boolean doCodeResolve) {

		// Do not create hierarchy presenter if there's no SOurce Module.
		if (getEditor() != null
				&& getEditor().getEditorInput() != null
				&& DLTKUIPlugin.getEditorInputModelElement(getEditor()
						.getEditorInput()) == null)
			return null;

		InformationPresenter presenter = new InformationPresenter(
				getHierarchyPresenterControlCreator(sourceViewer));
		presenter
				.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		IInformationProvider provider = new ScriptElementProvider(getEditor(),
				doCodeResolve) {
			// FIXME remove this method - temporary until source-ref info is
			// missing
			@Override
			public Object getInformation2(ITextViewer textViewer,
					IRegion subject) {
				Object el = super.getInformation2(textViewer, subject);
				if (el instanceof IJSSourceModule) {
					IType[] types;
					try {
						types = ((IJSSourceModule) el).getAllTypes();
						if (types != null && types.length > 0) {
							el = types[0];
						}
					} catch (ModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return el;
			}
		};
		presenter.setInformationProvider(provider,
				IDocument.DEFAULT_CONTENT_TYPE);
		presenter
				.setInformationProvider(provider, IJavaScriptPartitions.JS_DOC);
		// presenter.setInformationProvider(provider,
		// IJavaScriptPartitions.JAVA_MULTI_LINE_COMMENT);
		presenter.setInformationProvider(provider,
				IJavaScriptPartitions.JS_SINGLE_COMMENT);
		presenter.setInformationProvider(provider,
				IJavaScriptPartitions.JS_MULTI_COMMENT);
		presenter.setInformationProvider(provider,
				IJavaScriptPartitions.JS_STRING);
		// presenter.setInformationProvider(provider,
		// IJavaScriptPartitions.JAVA_CHARACTER);
		presenter.setSizeConstraints(50, 20, true, false);
		return presenter;
	}

	private IInformationControlCreator getHierarchyPresenterControlCreator(
			ISourceViewer sourceViewer) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new VjoHierarchyInformationControl(parent, shellStyle,
						treeStyle) {

					@Override
					protected IPreferenceStore getPreferenceStore() {
						return VjetUIPlugin.getDefault().getPreferenceStore();
					}
				};
			}
		};
	}

	@Override
	protected IInformationControlCreator getOutlinePresenterControlCreator(
			ISourceViewer sourceViewer, final String commandId) {

		return new IInformationControlCreator() {

			public IInformationControl createInformationControl(Shell parent) {

				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;

				return new ScriptOutlineInformationControl(parent, shellStyle,
						treeStyle, commandId) {

					@Override
					protected IPreferenceStore getPreferenceStore() {
						return VjetUIPlugin.getDefault().getPreferenceStore();
					}

					@Override
					protected OutlineLabelProvider getLabelProvider() {
						long txtFlags = AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS
								| ScriptElementLabels.F_APP_TYPE_SIGNATURE
								| ScriptElementLabels.ALL_CATEGORY;

						int imgFlags = AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS;

						VjoLabelProvider vjoLabelProvider = new VjoLabelProvider(
								txtFlags, imgFlags, fPreferenceStore);

						return new QuickOutlineVJOLabelProvider(
								vjoLabelProvider);
					}

					class QuickOutlineVJOLabelProvider extends
							OutlineLabelProvider {
						VjoLabelProvider vjoLabelProvider = null;

						public QuickOutlineVJOLabelProvider(
								VjoLabelProvider vjoLabelProvider) {
							super();
							this.vjoLabelProvider = vjoLabelProvider;
						}

						@Override
						public String getText(Object element) {
							String text = vjoLabelProvider.getText(element);
							// the super class is setting text flags to label
							// provider
							// so set it to our composition VjoLabelProvider

							vjoLabelProvider.setTextFlags(this.getTextFlags());
							if (isShowDefiningType()) {
								try {
									IType type = getDefiningType(element);
									if (type != null) {
										StringBuffer buf = new StringBuffer(
												vjoLabelProvider.getText(type));
										buf
												.append(ScriptElementLabels.CONCAT_STRING);
										buf.append(text);
										return buf.toString();
									}
								} catch (ModelException e) {
								}
							}
							return text;
						}

					}

				};
			}
		};
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		ScriptPresentationReconciler reconciler = new ScriptPresentationReconciler();
		reconciler
				.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				this.m_codeScanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(mJavaDocScanner);
		reconciler.setDamager(dr, IJavaScriptPartitions.JS_DOC);
		reconciler.setRepairer(dr, IJavaScriptPartitions.JS_DOC);

		dr = new DefaultDamagerRepairer(m_single_commentScanner);
		reconciler.setDamager(dr, IJavaScriptPartitions.JS_SINGLE_COMMENT);
		reconciler.setRepairer(dr, IJavaScriptPartitions.JS_SINGLE_COMMENT);

		dr = new DefaultDamagerRepairer(m_multi_commentScanner);
		reconciler.setDamager(dr, IJavaScriptPartitions.JS_MULTI_COMMENT);
		reconciler.setRepairer(dr, IJavaScriptPartitions.JS_MULTI_COMMENT);

		dr = new DefaultDamagerRepairer(m_stringScanner);
		reconciler.setDamager(dr, IJavaScriptPartitions.JS_STRING);
		reconciler.setRepairer(dr, IJavaScriptPartitions.JS_STRING);

		return reconciler;
	}

	// FIXME replace code scanners
	@Override
	protected void initializeScanners() {
		Assert.isTrue(isNewSetup());
		m_codeScanner = new VjoCodeScanner(getColorManager(), fPreferenceStore);
		m_stringScanner = new JavascriptStringScanner(getColorManager(),
				fPreferenceStore);
		// m_commentScanner = new SingleTokenScriptScanner(getColorManager(),
		// fPreferenceStore, VjetColorConstants.VJET_SINGLE_LINE_COMMENT);

		m_single_commentScanner = new VjoCommentScanner(getColorManager(),
				fPreferenceStore, VjetColorConstants.VJET_SINGLE_LINE_COMMENT);

		m_multi_commentScanner = new VjoCommentScanner(getColorManager(),
				fPreferenceStore, VjetColorConstants.VJET_MULTI_LINE_COMMENT);
		mJavaDocScanner = new VjoJavaDocScanner(getColorManager(),
				fPreferenceStore);
	}

	/**
	 * @return <code>true</code> iff the new setup without text tools is in
	 *         use.
	 */
	private boolean isNewSetup() {
		return m_textTools == null;
	}

	@Override
	protected String getCommentPrefix() {
		return "//";
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				IJavaScriptPartitions.JS_STRING,
				IJavaScriptPartitions.JS_SINGLE_COMMENT,
				IJavaScriptPartitions.JS_MULTI_COMMENT,
				IJavaScriptPartitions.JS_DOC };
	}

	@Override
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return m_codeScanner.affectsBehavior(event)
				|| m_stringScanner.affectsBehavior(event)
				|| m_single_commentScanner.affectsBehavior(event)
				|| m_multi_commentScanner.affectsBehavior(event)
				|| mJavaDocScanner.affectsBehavior(event);
	}

	@Override
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		Assert.isTrue(isNewSetup());
		if (m_codeScanner.affectsBehavior(event))
			m_codeScanner.adaptToPreferenceChange(event);
		if (m_stringScanner.affectsBehavior(event))
			m_stringScanner.adaptToPreferenceChange(event);
		if (m_single_commentScanner.affectsBehavior(event))
			m_single_commentScanner.adaptToPreferenceChange(event);
		if (m_multi_commentScanner.affectsBehavior(event))
			m_multi_commentScanner.adaptToPreferenceChange(event);
		if (mJavaDocScanner.affectsBehavior(event))
			mJavaDocScanner.adaptToPreferenceChange(event);
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		// String partitioning =
		// getConfiguredDocumentPartitioning(sourceViewer);
		// return new IAutoEditStrategy[] { new
		// VjoAutoEditStrategy(partitioning,
		// null) };

		String partitioning = getConfiguredDocumentPartitioning(sourceViewer);
		if (IJavaScriptPartitions.JS_DOC.equals(contentType)
				|| IJavaScriptPartitions.JS_MULTI_COMMENT.equals(contentType))
			return new IAutoEditStrategy[] { new VjoDocAutoIndentStrategy(
					partitioning) };

		return new IAutoEditStrategy[] { new VjoAutoEditStrategy(partitioning,
				null) };

	}

	@Override
	public int getTabWidth(ISourceViewer sourceViewer) {
		if (fPreferenceStore == null)
			return super.getTabWidth(sourceViewer);
		return fPreferenceStore
				.getInt(CodeFormatterConstants.FORMATTER_TAB_SIZE);
	}

	/*
	 * @see SourceViewerConfiguration#getDoubleClickStrategy(ISourceViewer,
	 *      String)
	 */
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (fVjoDoubleClickSelector == null) {
			fVjoDoubleClickSelector = new VjoDoubleClickSelector();
		}
		return fVjoDoubleClickSelector;
	}

}
