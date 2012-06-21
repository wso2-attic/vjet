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
package org.ebayopensource.vjet.eclipse.internal.ui.editor;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IImportDeclaration;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.VjoLanguageToolkit;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.search.VjoMatch;
import org.ebayopensource.vjet.eclipse.core.search.VjoSearchOccurrenceEngine;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;
import org.ebayopensource.vjet.eclipse.internal.ui.actions.RemoveOccurrenceAnnotations;
import org.ebayopensource.vjet.eclipse.internal.ui.actions.ToggleMarkOccurrencesAction;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.UnknownContentTypeDialog;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.semantic.highlighting.VjoSemanticHighlightingManager;
import org.ebayopensource.vjet.eclipse.internal.ui.text.VjoSourceViewerConfiguration;
import org.ebayopensource.vjet.eclipse.internal.ui.text.folding.VjoFoldingStructureProvider;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.IHelpContextIds;
import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.ebayopensource.vjet.eclipse.ui.VjetUIImages;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjet.eclipse.ui.VjetUIUtils;
import org.ebayopensource.vjet.eclipse.ui.actions.VjoAddCommentAction;
import org.ebayopensource.vjet.eclipse.ui.actions.nature.AddVjoNaturePolicyManager;
import org.ebayopensource.vjet.eclipse.ui.actions.nature.IAddVjoNaturePolicy;
import org.ebayopensource.vjet.eclipse.ui.viewsupport.SelectionListenerWithJSTManager;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.mod.core.IBuffer;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.ISourceReference;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.JSSourceField;
import org.eclipse.dltk.mod.internal.core.JSSourceMethod;
import org.eclipse.dltk.mod.internal.core.JSSourceType;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.ScriptFolder;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.internal.ui.actions.FoldingActionGroup;
import org.eclipse.dltk.mod.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.mod.internal.ui.editor.ExternalFileEditorInput;
import org.eclipse.dltk.mod.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptOutlinePage;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.mod.internal.ui.text.PreferencesAdapter;
import org.eclipse.dltk.mod.internal.ui.text.ScriptWordFinder;
import org.eclipse.dltk.mod.javascript.internal.ui.editor.JavaScriptEditor;
import org.eclipse.dltk.mod.ui.actions.DLTKActionConstants;
import org.eclipse.dltk.mod.ui.actions.IScriptEditorActionDefinitionIds;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.dltk.mod.ui.text.folding.IFoldingStructureProvider;
import org.eclipse.jdt.ui.actions.IJavaEditorActionDefinitionIds;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ISelectionValidator;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * Editor for vjo files.
 * 
 * 
 * 
 */
public class VjoEditor extends JavaScriptEditor {

    public class SelectionListenerWithJST {
        public void selectionChanged(IEditorPart part,
                ITextSelection selection, IModelElement astRoot) {
            updateOccurrenceAnnotations(selection, astRoot);
        }
    }

    class OccurrencesFinderJob extends Job {
        private boolean fCanceled = false;

        /**
         * Finds and marks occurrence annotations.
         * 
         * @since 3.0
         */
        private IDocument fDocument;

        private Position[] fPositions;

        private ISelectionValidator fPostSelectionValidator;

        private IProgressMonitor fProgressMonitor;

        private ISelection fSelection;

        public OccurrencesFinderJob(IDocument document, Position[] positions,
                ISelection selection) {
            super(VjoEditorMessages.VjoEditor_markOccurrences_job_name);
            fDocument = document;
            fSelection = selection;
            fPositions = positions;

            if (getSelectionProvider() instanceof ISelectionValidator)
                fPostSelectionValidator = (ISelectionValidator) getSelectionProvider();
        }

        /*
         * @see Job#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        public IStatus run(IProgressMonitor progressMonitor) {

            fProgressMonitor = progressMonitor;

            if (isCanceled())
                return Status.CANCEL_STATUS;

            ITextViewer textViewer = getViewer();
            if (textViewer == null)
                return Status.CANCEL_STATUS;

            IDocument document = textViewer.getDocument();
            if (document == null)
                return Status.CANCEL_STATUS;

            IDocumentProvider documentProvider = getDocumentProvider();
            if (documentProvider == null)
                return Status.CANCEL_STATUS;

            IAnnotationModel annotationModel = documentProvider
                    .getAnnotationModel(getEditorInput());
            if (annotationModel == null)
                return Status.CANCEL_STATUS;

            // Add occurrence annotations
            int length = fPositions.length;
            Map annotationMap = new HashMap(length);
            for (int i = 0; i < length; i++) {

                if (isCanceled())
                    return Status.CANCEL_STATUS;

                String message;
                Position position = fPositions[i];

                // Create & add annotation
                try {
                    message = document.get(position.getOffset(),
                            position.length);
                } catch (BadLocationException ex) {
                    // Skip this match
                    continue;
                }
                annotationMap.put(new Annotation("org.eclipse.jdt.ui.occurrences", false, message), //$NON-NLS-1$
                        position);
            }

            if (isCanceled())
                return Status.CANCEL_STATUS;

            synchronized (getLockObject(annotationModel)) {
                if (annotationModel instanceof IAnnotationModelExtension) {
                    ((IAnnotationModelExtension) annotationModel)
                            .replaceAnnotations(m_occurrenceAnnotations,
                                    annotationMap);
                } else {
                    removeOccurrenceAnnotations();
                    Iterator iter = annotationMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry mapEntry = (Map.Entry) iter.next();
                        annotationModel.addAnnotation((Annotation) mapEntry
                                .getKey(), (Position) mapEntry.getValue());
                    }
                }
                m_occurrenceAnnotations = (Annotation[]) annotationMap.keySet()
                        .toArray(new Annotation[annotationMap.keySet().size()]);
            }

            return Status.OK_STATUS;
        }

        // cannot use cancel() because it is declared final
        void doCancel() {
            fCanceled = true;
            cancel();
        }

        private boolean isCanceled() {
            return fCanceled
                    || fProgressMonitor.isCanceled()
                    || fPostSelectionValidator != null
                    && !(fPostSelectionValidator.isValid(fSelection) || m_forcedMarkOccurrencesSelection == fSelection)
                    || LinkedModeModel.hasInstalledModel(fDocument);
        }
    }

    /**
     * Cancels the occurrences finder job upon document changes.
     * 
     * @since 3.0
     */
    class OccurrencesFinderJobCanceler implements IDocumentListener,
            ITextInputListener {

        /*
         * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
         */
        public void documentAboutToBeChanged(DocumentEvent event) {
            if (m_occurrencesFinderJob != null)
                m_occurrencesFinderJob.doCancel();
        }

        /*
         * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
         */
        public void documentChanged(DocumentEvent event) {
        }

        /*
         * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument,
         *      org.eclipse.jface.text.IDocument)
         */
        public void inputDocumentAboutToBeChanged(IDocument oldInput,
                IDocument newInput) {
            if (oldInput == null)
                return;

            oldInput.removeDocumentListener(this);
        }

        /*
         * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument,
         *      org.eclipse.jface.text.IDocument)
         */
        public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
            if (newInput == null)
                return;
            newInput.addDocumentListener(this);
        }

        public void install() {
            ISourceViewer sourceViewer = getSourceViewer();
            if (sourceViewer == null)
                return;

            StyledText text = sourceViewer.getTextWidget();
            if (text == null || text.isDisposed())
                return;

            sourceViewer.addTextInputListener(this);

            IDocument document = sourceViewer.getDocument();
            if (document != null)
                document.addDocumentListener(this);
        }

        public void uninstall() {
            ISourceViewer sourceViewer = getSourceViewer();
            if (sourceViewer != null)
                sourceViewer.removeTextInputListener(this);

            IDocumentProvider documentProvider = getDocumentProvider();
            if (documentProvider != null) {
                IDocument document = documentProvider
                        .getDocument(getEditorInput());
                if (document != null)
                    document.removeDocumentListener(this);
            }
        }
    }

    /**
     * Internal activation listener.
     * 
     * @since 3.0
     */
    private class ActivationListener implements IWindowListener {

        /*
         * @see org.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.IWorkbenchWindow)
         * @since 3.1
         */
        public void windowActivated(IWorkbenchWindow window) {

            if (window == getEditorSite().getWorkbenchWindow()
                    && m_markOccurrenceAnnotations && isActivePart()) {
                m_forcedMarkOccurrencesSelection = getSelectionProvider()
                        .getSelection();
                if (CodeassistUtils.isVjoSourceModule(getInputModelElement())) {

                    updateOccurrenceAnnotations(
                            (ITextSelection) m_forcedMarkOccurrencesSelection,
                            getInputModelElement());
                }
            }
        }

        /*
         * @see org.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.IWorkbenchWindow)
         * @since 3.1
         */
        public void windowClosed(IWorkbenchWindow window) {
        }

        /*
         * @see org.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.IWorkbenchWindow)
         * @since 3.1
         */
        public void windowDeactivated(IWorkbenchWindow window) {

            if (window == getEditorSite().getWorkbenchWindow()
                    && m_markOccurrenceAnnotations && isActivePart())
                removeOccurrenceAnnotations();
        }

        /*
         * @see org.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.IWorkbenchWindow)
         * @since 3.1
         */
        public void windowOpened(IWorkbenchWindow window) {
        }
    }

    /**
     * Vjo Editor ID
     */
    public static final String EDITOR_ID = "org.ebayopensource.vjet.ui.VjetJsEditor"; //$NON-NLS-1$

    private static final String VJO_CONTENT_TYPE = "org.eclipse.dltk.mod.vjoContentType"; //$NON-NLS-1$

    private static final String VJO_EDITOR_CNTX_MENU_ID = "#VjoEditorContext"; //$NON-NLS-1$

    protected VjoSemanticHighlightingManager fSemanticManager;

    IFoldingStructureProvider m_foldingProvider = null;

    private ActivationListener m_activationListener = new ActivationListener();

    /**
     * The selection used when forcing occurrence marking through code.
     * 
     * @since 3.0
     */
    private ISelection m_forcedMarkOccurrencesSelection;

    private boolean m_isArchiveFile;

    /**
     * Tells whether to mark field occurrences in this editor. Only valid if
     * {@link #m_markOccurrenceAnnotations} is <code>true</code>.
     * 
     * @since 3.0
     */
    private boolean m_markFieldOccurrences;

    /**
     * Tells whether to mark local variable occurrences in this editor. Only
     * valid if {@link #m_markOccurrenceAnnotations} is <code>true</code>.
     * 
     * @since 3.0
     */
    private boolean m_markLocalVariableTypeOccurrences;

    /**
     * Tells whether to mark method occurrences in this editor. Only valid if
     * {@link #m_markOccurrenceAnnotations} is <code>true</code>.
     * 
     * @since 3.0
     */
    private boolean m_markMethodOccurrences;

    /**
     * Tells whether all occurrences of the element at the current caret
     * location are automatically marked in this editor.
     * 
     * @since 3.0
     */
    private boolean m_markOccurrenceAnnotations;

    /**
     * The document modification stamp at the time when the last occurrence
     * marking took place.
     * 
     * @since 3.1
     */
    private long m_markOccurrenceModificationStamp = IDocumentExtension4.UNKNOWN_MODIFICATION_STAMP;

    /**
     * The region of the word under the caret used to when computing the current
     * occurrence markings.
     * 
     * @since 3.1
     */
    private IRegion m_markOccurrenceTargetRegion;

    /**
     * Holds the current occurrence annotations.
     * 
     * @since 3.0
     */
    private Annotation[] m_occurrenceAnnotations = null;

    private OccurrencesFinderJob m_occurrencesFinderJob;

    /** The occurrences finder job canceler */
    private OccurrencesFinderJobCanceler m_occurrencesFinderJobCanceler;

    private SelectionListenerWithJST m_postSelectionListenerWithAST;

    /**
     * Tells whether the occurrence annotations are sticky i.e. whether they
     * stay even if there's no valid Javascript element at the current caret
     * position. Only valid if {@link #m_markOccurrenceAnnotations} is
     * <code>true</code>.
     * 
     * @since 3.0
     */
    private boolean m_stickyOccurrenceAnnotations;

    private ToggleMarkOccurrencesAction m_toggleMarkOccurrencesAction;

    private TogglePresentationAction m_togglePresentation;

	private NativeVjoSourceModule m_readOnlyType;

    public VjoEditor() {
//        System.out.println("Vjo Editor Created"); //$NON-NLS-1$
//        WorkbenchPlugin.log(new Status(IStatus.INFO, VjetPlugin.PLUGIN_ID,
//                IStatus.INFO, "!USAGE_TRACKING: NAME=" + VjetPlugin.PLUGIN_ID //$NON-NLS-1$
//                        + ".editor.opened; ACCESS_TIME=" //$NON-NLS-1$
//                        + new Date().toString() + ";", null)); //$NON-NLS-1$

    }

    /**
     * 
     * @return the default content type assocaited this editor
     */
    public static IContentType getDefaultContentType() {
        return Platform.getContentTypeManager()
                .getContentType(VJO_CONTENT_TYPE);
    }

    public void createActions() {
        super.createActions();

        // add remove occurrence action
        RemoveOccurrenceAnnotations action = new RemoveOccurrenceAnnotations(
                this);
        action
                .setActionDefinitionId(IScriptEditorActionDefinitionIds.REMOVE_OCCURRENCE_ANNOTATIONS);
        setAction("RemoveOccurrenceAnnotations", action); //$NON-NLS-1$

        m_togglePresentation = new TogglePresentationAction(this);
        m_togglePresentation
                .setActionDefinitionId(IScriptEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES);
        setAction("TogglePresentationAction", m_togglePresentation); //$NON-NLS-1$

        m_toggleMarkOccurrencesAction = new ToggleMarkOccurrencesAction(this);
        m_toggleMarkOccurrencesAction
                .setActionDefinitionId(IScriptEditorActionDefinitionIds.TOGGLE_PRESENTATION);
        setAction("ToggleMarkOccurrencesAction", m_toggleMarkOccurrencesAction); //$NON-NLS-1$

        IEditorSite site = getEditorSite();
        IActionBars bars = site.getActionBars();

        bars
                .setGlobalActionHandler(
                        ITextEditorActionDefinitionIds.TOGGLE_SHOW_SELECTED_ELEMENT_ONLY,
                        m_togglePresentation);
        bars.setGlobalActionHandler(
                IScriptEditorActionDefinitionIds.TOGGLE_MARK_OCCURRENCES,
                m_toggleMarkOccurrencesAction);

        VjoAddCommentAction addCommentAction = new VjoAddCommentAction(this);
        addCommentAction
                .setActionDefinitionId(IJavaEditorActionDefinitionIds.ADD_JAVADOC_COMMENT);
        setAction(DLTKActionConstants.ADD_BLOCK_COMMENT, addCommentAction);
        markAsStateDependentAction(DLTKActionConstants.ADD_BLOCK_COMMENT, true);
        markAsSelectionDependentAction(DLTKActionConstants.ADD_BLOCK_COMMENT, true);
        bars.setGlobalActionHandler("org.ebayopensource.vjet.eclipse.ui.AddJavaDocComment", addCommentAction); //$NON-NLS-1$
    }

    public void createPartControl(Composite parent) {
        super.createPartControl(parent);

        // reset the default font to fix eclipse tab size bug
        // ITheme theme =
        // PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
        // Font font =
        // theme.getFontRegistry().get("org.eclipse.jface.textfont");
        // this.getSourceViewer().getTextWidget().setFont(font);

        // install mark occurrence
        installOccurrencesFinder(false);
        PlatformUI.getWorkbench().addWindowListener(m_activationListener);

        // Add by Oliver. 2009-11-02. add the F1 help for vjet editor.
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
                IHelpContextIds.VJET_EDITOR);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
        uninstallSemanticHighlighting();
    }

    public void doSelectionChanged(SelectionChangedEvent event) {
        super.doSelectionChanged(event);

        if (event.getSelection() != null) {
            m_forcedMarkOccurrencesSelection = event.getSelection();
            if (m_forcedMarkOccurrencesSelection instanceof ITextSelection) {
                updateOccurrenceAnnotations(
                        (ITextSelection) m_forcedMarkOccurrencesSelection,
                        getInputModelElement());
            }
            // Add by Oliver, 2009-03-09. Fix
            // bug:http://quickbugstage.arch.ebay.com/show_bug.cgi?id=2097
            // When the selectionChange is from outline view, the selection
            // is an instance of ITreeSelection, so I add the codes below.
            else if (m_forcedMarkOccurrencesSelection instanceof ITreeSelection) {
                ISelection editorSelection = getSelectionProvider()
                        .getSelection();
                if (editorSelection instanceof ITextSelection) {
                    m_forcedMarkOccurrencesSelection = (ITextSelection) editorSelection;
                    updateOccurrenceAnnotations(
                            (ITextSelection) editorSelection,
                            getInputModelElement());
                }
            }
        }
    }

    public IModelElement getInputModelElement() {
        IEditorInput input = getEditorInput();
        IModelElement element = super.getInputModelElement();
        
        
        
        if (element != null) {
        	
        	if(element instanceof VjoSourceModule){
        		VjoSourceModule module = (VjoSourceModule)element;
        		if(module.getJstType()==null && isTypeSpaceScheme(input)){
        			
        			if(m_readOnlyType!=null){
        				return m_readOnlyType;
        			}
        			
        			IPath fullPath = ((FileEditorInput)input).getFile().getFullPath();
        	    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        	    	IResource resource = root.findMember(fullPath);
        	    	URI location = resource.getLocationURI();
        	    	String typeName = location.getPath().replace("/", ".");
        			
        			// TODO add support for type here
//        			System.out.println("no type info for model");
        			String groupName = getTypspaceGroupFromURI(input);
        			
        			typeName = typeName.substring(1, typeName.lastIndexOf(".js"));
					NativeVjoSourceModule m2= new NativeVjoSourceModule( CodeassistUtils.getDefaultNativeSourceFolder(groupName), groupName,
        					typeName);
        			m2.setJstType(CodeassistUtils.findJstType(groupName, typeName));
        			
        			m_readOnlyType = m2;
        			return m2;
        		}
        	}
        	
            return element;
        } else if (input instanceof ExternalFileEditorInput) {
            return ((ExternalFileEditorInput) input).getModelElement();
        }
        // if (input instanceof ExternalFileEditorInput) {
        // } else {
        return element;
        // }
    }

    @Override
    public IDLTKLanguageToolkit getLanguageToolkit() {
        return VjoLanguageToolkit.getDefault();
    }

    @Override
    public ScriptTextTools getTextTools() {
        return VjetUIPlugin.getDefault().getTextTools();
    }

    public Image getTitleImage() {
    	
    	
    	
        IEditorInput input = getEditorInput();
        
        if(input instanceof FileEditorInput){
        	FileEditorInput fei = (FileEditorInput)input;
        	if(isTypeSpaceScheme(fei)){
        	    	  return VjetUIImages
        	                    .getImage(VjetUIImages.IMAGE_BINARY_EDITOR_TITLE);
        	    }
        }
        
        if (input instanceof ExternalFileEditorInput || input instanceof ExternalStorageEditorInput) {
            return VjetUIImages
                    .getImage(VjetUIImages.IMAGE_BINARY_EDITOR_TITLE);

        }
        IModelElement element = getInputModelElement();
        if (element == null || !CodeassistUtils.isModuleInBuildPath(element)) {
            return VjetUIImages
                    .getImage(VjetUIImages.IMAGE_OUTOFPATH_EDITOR_TITLE);
        }
        return super.getTitleImage();
    }
    
    private boolean isTypeSpaceScheme(IEditorInput ei){
    	FileEditorInput fei = null;
    	if(ei instanceof FileEditorInput){
    		fei = (FileEditorInput)ei;
    	}else{
    		return false;
    	}
    	IPath fullPath = fei.getFile().getFullPath();
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	IResource resource = root.findMember(fullPath);
    	if (resource != null) {
    	    URI location = resource.getLocationURI();
        	if (location != null) {
        	    if(location.getScheme().equals("typespace")){
        	    	return true;
        	    }
        	}
    	}
		
    	return false;
    }
    
    private String getTypspaceGroupFromURI(IEditorInput ei){
    	FileEditorInput fei = null;
    	if(ei instanceof FileEditorInput){
    		fei = (FileEditorInput)ei;
    	}else{
    		return null;
    	}
    	// TODO move into utility
    	IPath fullPath = fei.getFile().getFullPath();
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	IResource resource = root.findMember(fullPath);
    	
    	URI location = resource.getLocationURI();
    	if(location.getScheme().equals("typespace")){
    		return location.getHost();
    	}
    	
    	return null;
   
    }
    

    @Override
    public boolean isDirty() {
        if (m_isArchiveFile) {
            return false;
        }
        return super.isDirty();
    }

    public boolean isEditable() {
        boolean bol = super.isEditable();
        if (!bol) {
            return bol;
        } else {
            IEditorInput input = getEditorInput();
            if(isTypeSpaceScheme(input)){
            	return false;
            }else if (input instanceof ExternalFileEditorInput) {
                return false;
            } else if (input instanceof org.eclipse.dltk.mod.internal.debug.ui.ExternalFileEditorInput) {
            	return false;
            } else {
                return bol;
            }
        }
    }

    public boolean isMarkingOccurrences() {
        return m_markOccurrenceAnnotations;
    }

    public void refreshOutlinePage() {
        ScriptOutlinePage outlinePage = getOutlinePage();

        if (outlinePage != null) {
            TreeViewer outlineViewer = outlinePage.getOutlineViewer();

            if (outlineViewer != null) {
                outlineViewer.refresh();
            }
        }
    }

    public void removeOccurrenceAnnotations() {
        m_markOccurrenceModificationStamp = IDocumentExtension4.UNKNOWN_MODIFICATION_STAMP;
        m_markOccurrenceTargetRegion = null;
        IDocumentProvider documentProvider = getDocumentProvider();
        if (documentProvider == null)
            return;

        IAnnotationModel annotationModel = documentProvider
                .getAnnotationModel(getEditorInput());
        if (annotationModel == null || m_occurrenceAnnotations == null)
            return;

        synchronized (getLockObject(annotationModel)) {
            if (annotationModel instanceof IAnnotationModelExtension) {
                ((IAnnotationModelExtension) annotationModel)
                        .replaceAnnotations(m_occurrenceAnnotations, null);
            } else {
                for (int i = 0, length = m_occurrenceAnnotations.length; i < length; i++)
                    annotationModel
                            .removeAnnotation(m_occurrenceAnnotations[i]);
            }
            m_occurrenceAnnotations = null;
        }
    }

    @Override
    protected ISourceReference computeHighlightRangeSourceReference() {
        return super.computeHighlightRangeSourceReference();
    }

    @Override
    protected FoldingActionGroup createFoldingActionGroup() {
        return new FoldingActionGroup(this, getViewer(), VjetUIPlugin
                .getDefault().getPreferenceStore());
    }

    @Override
    protected ScriptOutlinePage doCreateOutlinePage() {
        return new VjoOutlinePage(this, VjetUIPlugin.getDefault()
                .getPreferenceStore());
    }

    /**
     * Updates the occurrences annotations based on the current selection.
     * 
     * @param selection
     *            the text selection
     * @param astRoot
     *            the compilation unit AST
     */
    protected void doUpdateOccurrenceAnnotations(ITextSelection selection,
            IModelElement astRoot) {

        if (m_occurrencesFinderJob != null)
            m_occurrencesFinderJob.cancel();

        if (!m_markOccurrenceAnnotations)
            return;

        if (astRoot == null || selection == null)
            return;

        if(getSourceViewer()==null){
        	return;
        }
        
        IDocument document = getSourceViewer().getDocument();
        if (document == null)
            return;

        if (document instanceof IDocumentExtension4) {
            int offset = selection.getOffset();
            long currentModificationStamp = ((IDocumentExtension4) document)
                    .getModificationStamp();
            if (m_markOccurrenceTargetRegion != null
                    && currentModificationStamp == m_markOccurrenceModificationStamp) {
                if (m_markOccurrenceTargetRegion.getOffset() <= offset
                        && offset <= m_markOccurrenceTargetRegion.getOffset()
                                + m_markOccurrenceTargetRegion.getLength())
                    return;
            }
            // find an offset of selected word
            offset = CodeassistUtils.findWordOffset(document.get()
                    .toCharArray(), offset);
            m_markOccurrenceTargetRegion = ScriptWordFinder.findWord(document,
                    offset);
            m_markOccurrenceModificationStamp = currentModificationStamp;
        }

        if ((!(astRoot instanceof VjoSourceModule))
                || (m_markOccurrenceTargetRegion == null))
            return;

        VjoSourceModule vjoSourceModule = ((VjoSourceModule) astRoot);

        // modify by patrick
        VjoSelectionEngine vjoSelectionEngine = VjetUIUtils.getSelectionEngine();
        if (vjoSelectionEngine == null) {
            return;
        }
        IJstNode jstNode = vjoSelectionEngine.convertSelection2JstNode(
                vjoSourceModule, selection.getOffset(), selection.getOffset()
                        + selection.getLength());
        IJstType scopeTree = CodeassistUtils.getJstType(vjoSourceModule);

        if (jstNode == null) {
            if (!m_stickyOccurrenceAnnotations)
                removeOccurrenceAnnotations();
            return;
        }

        if (scopeTree == null) {
            return;
        }

        // filter keyword not need occurence marker
        if (!this.isNeedOccurrencesMarker(vjoSourceModule, selection))
            return;

        // check preferences
        boolean isEnabled = isEnableMarkOccurences(jstNode);

        if (!isEnabled) {
            if (!m_stickyOccurrenceAnnotations)
                removeOccurrenceAnnotations();
            return;
        }

        // TODO this should be based off of IScriptUnit not JstType
        List<VjoMatch> matches = VjoSearchOccurrenceEngine.findOccurrence(
                jstNode, scopeTree);

        if (matches == null || matches.isEmpty()) {
            if (!m_stickyOccurrenceAnnotations)
                removeOccurrenceAnnotations();
            return;
        }

        // run job on updating mark occurences
        m_occurrencesFinderJob = new OccurrencesFinderJob(document,
                getPositions(matches), selection);
        m_occurrencesFinderJob.run(new NullProgressMonitor());
    }

    @Override
    protected IFoldingStructureProvider getFoldingStructureProvider() {
        if (m_foldingProvider == null) {
            m_foldingProvider = new VjoFoldingStructureProvider();
        }
        return m_foldingProvider;
    }

    @Override
    protected IPreferenceStore getScriptPreferenceStore() {
        return VjetUIPlugin.getDefault().getPreferenceStore();
    }

    @Override
    protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
        String property = event.getProperty();
        ISourceViewer sourceViewer = getSourceViewer();

        boolean newBooleanValue = false;
        Object newValue = event.getNewValue();
        if (newValue != null)
            newBooleanValue = Boolean.valueOf(newValue.toString())
                    .booleanValue();

        if (VjetPreferenceConstants.EDITOR_FOLDING_METHODS.equals(property)) {
            if (sourceViewer instanceof ProjectionViewer) {
                fProjectionModelUpdater.initialize();
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_FOLDING_IMPORTS
                .equals(property)) {
            if (sourceViewer instanceof ProjectionViewer) {
                fProjectionModelUpdater.initialize();
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_FOLDING_JAVADOC
                .equals(property)) {
            if (sourceViewer instanceof ProjectionViewer) {
                fProjectionModelUpdater.initialize();
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_FOLDING_HEADERS
                .equals(property)) {
            if (sourceViewer instanceof ProjectionViewer) {
                fProjectionModelUpdater.initialize();
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_FOLDING_INNERTYPES
                .equals(property)) {
            if (sourceViewer instanceof ProjectionViewer) {
                fProjectionModelUpdater.initialize();
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_MARK_OCCURRENCES
                .equals(property)) {
            if (newBooleanValue != m_markOccurrenceAnnotations) {
                m_markOccurrenceAnnotations = newBooleanValue;
                if (!m_markOccurrenceAnnotations)
                    uninstallOccurrencesFinder();
                else
                    installOccurrencesFinder(true);
            }
            return;
        } else if (VjetPreferenceConstants.EDITOR_MARK_METHOD_OCCURRENCES
                .equals(property)) {
            m_markMethodOccurrences = newBooleanValue;
            return;
        } else if (VjetPreferenceConstants.EDITOR_MARK_FIELD_OCCURRENCES
                .equals(property)) {
            m_markFieldOccurrences = newBooleanValue;
            return;
        } else if (VjetPreferenceConstants.EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES
                .equals(property)) {
            m_markLocalVariableTypeOccurrences = newBooleanValue;
            return;
        } else if (VjetPreferenceConstants.EDITOR_STICKY_OCCURRENCES
                .equals(property)) {
            m_stickyOccurrenceAnnotations = newBooleanValue;
            return;
        }

        super.handlePreferenceStoreChanged(event);
    }

    /*
     * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#initializeEditor()
     */
    protected void initializeEditor() {
        IPreferenceStore store = createCombinedPreferenceStore(null);
        setPreferenceStore(store);
        m_markOccurrenceAnnotations = store
                .getBoolean(VjetPreferenceConstants.EDITOR_MARK_OCCURRENCES);
        m_stickyOccurrenceAnnotations = store
                .getBoolean(VjetPreferenceConstants.EDITOR_STICKY_OCCURRENCES);
        m_markMethodOccurrences = store
                .getBoolean(VjetPreferenceConstants.EDITOR_MARK_METHOD_OCCURRENCES);
        m_markFieldOccurrences = store
                .getBoolean(VjetPreferenceConstants.EDITOR_MARK_FIELD_OCCURRENCES);
        m_markLocalVariableTypeOccurrences = store
                .getBoolean(VjetPreferenceConstants.EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES);

        // set context menu id
        setEditorContextMenuId(VJO_EDITOR_CNTX_MENU_ID);

    }

    /**
     * Install the finder job for mark occurences.
     * 
     * @param forceUpdate
     */
    protected void installOccurrencesFinder(boolean forceUpdate) {
        m_markOccurrenceAnnotations = true;

        m_postSelectionListenerWithAST = new SelectionListenerWithJST();

        SelectionListenerWithJSTManager.getDefault().addListener(this,
                m_postSelectionListenerWithAST);
        if (forceUpdate && getSelectionProvider() != null) {
            m_forcedMarkOccurrencesSelection = getSelectionProvider()
                    .getSelection();
            updateOccurrenceAnnotations(
                    (ITextSelection) m_forcedMarkOccurrencesSelection,
                    getInputModelElement());
        }

        if (m_occurrencesFinderJobCanceler == null) {
            m_occurrencesFinderJobCanceler = new OccurrencesFinderJobCanceler();
            m_occurrencesFinderJobCanceler.install();
        }
    }

    protected void installSemanticHighlighting() {
        // ebay modify, change to protected
        ScriptTextTools textTools = getTextTools();
        if (fSemanticManager == null && textTools != null) {
            fSemanticManager = new VjoSemanticHighlightingManager();
            fSemanticManager.install(this,
                    (ScriptSourceViewer) getSourceViewer(), textTools
                            .getColorManager(), getPreferenceStore());
        }
    }

    /**
     * Add enable vjo nature for project didn't have it.
     */
    private void enableVjoNature() {

        if (!TypeSpaceMgr.getInstance().isLoaded())
            return;

        Preferences pluginPreferences = VjetUIPlugin.getDefault().getPluginPreferences();
        boolean notShowDialog = pluginPreferences.getBoolean(VjoEditorMessages.VjoEditor_10);
        int result = pluginPreferences.getInt(VjoEditorMessages.VjoEditor_11);
        try {
        	IEditorInput input = getEditorInput();
            if (input instanceof ExternalFileEditorInput) {//input is out of work space            	
                return;
            } else if (input instanceof org.eclipse.dltk.mod.internal.debug.ui.ExternalFileEditorInput) {
            	return;
            }else if(input instanceof FileEditorInput){
            	if(isTypeSpaceScheme((FileEditorInput)input)){
            		return;
            	}
            }
            
            IProject project = this.getInputModelElement().getScriptProject().getProject();
            boolean hasNature = project.hasNature(VjoNature.NATURE_ID);
            if (!hasNature && !notShowDialog) {
                String message = MessageFormat.format(VjoEditorMessages.VjoEditor_13,
                        new Object[] { this.getInputModelElement().getElementName(), project.getName() });
                EnableVjoNatureDialog md = new EnableVjoNatureDialog(Display.getCurrent().getActiveShell(),
                        VjoEditorMessages.VjoEditor_12,
                        getTitleImage(), message, SWT.ICON_INFORMATION,
                        new String[] { VjoEditorMessages.VjoEditor_15, VjoEditorMessages.VjoEditor_16 }, 0);
                result = md.open();
                if (md.isSelection()) {
                    pluginPreferences.setValue(VjoEditorMessages.VjoEditor_10, true);
                    pluginPreferences.setValue(VjoEditorMessages.VjoEditor_11, result);
                }
            }

           IModelElement inputModelElement = getInputModelElement();
           if( getSourceViewerConfiguration() instanceof VjoSourceViewerConfiguration && inputModelElement instanceof VjoSourceModule){
        	   	VjoSourceViewerConfiguration vsvc = (VjoSourceViewerConfiguration)getSourceViewerConfiguration();
        	   	vsvc.setModule((VjoSourceModule)inputModelElement);
           }
            
            if (!hasNature && result == 0) {
                // fetch the corresponding adding vjo nature policy
                // implementation
                IAddVjoNaturePolicy policy = AddVjoNaturePolicyManager.getInstance().getPolicy(project);
                policy.addVjoNature(project);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    protected void internalDoSetInput(IEditorInput input) throws CoreException {
        super.internalDoSetInput(input);

        enableVjoNature();

        IModelElement original = getInputModelElement();
        if (original instanceof IVjoSourceModule
        		&& isTypeSpaceScheme(input)) {
//            ITextFileBufferManager manager = FileBuffers
//                    .getTextFileBufferManager();
//            ITextFileBuffer buffer = manager
//                    .getTextFileBuffer(
//                           ((FileEditorInput)input).getFile().getFullPath(),
//                            LocationKind.NORMALIZE);
            // TODO use this version when Eclipse 3.2 support is not longer
            // required
//             ITextFileBuffer buffer = manager.getTextFileBuffer(
//            		  ((FileEditorInput)input).getFile().getFullPath(),
//             LocationKind.NORMALIZE);
//            IDocument doc = buffer.getDocument();
//            IVjoSourceModule extSource = (IVjoSourceModule) original;
//            IBuffer buf = extSource.getBuffer();
//            String content = (buf != null) ? buf.getContents() : new String(
//                    extSource.getContentsAsCharArray());
//            doc.set(content);
            
            checkFileSuffix((FileEditorInput) input);
            
            m_isArchiveFile = true;
        } else if (!CodeassistUtils.isVjoSourceModule(original)
                && input instanceof FileEditorInput) {
            checkFileSuffix((FileEditorInput) input);
        }
    }

    protected void performSaveAs(IProgressMonitor progressMonitor) {
        IPreferenceStore store = EditorsUI.getPreferenceStore();
        String key = getEditorSite().getId() + ".internal.delegateSaveAs"; //$NON-NLS-1$
        store.setValue(key, true);
        super.performSaveAs(progressMonitor);
    }

    @Override
    protected void setSelection(ISourceReference reference, boolean moveCursor) {
        if (getSelectionProvider() == null)
            return;

        // Add by Oliver. 2009-07-24. If a method comes from the parent type
        // that it is mixin type, don't do set selection operation.
        if (reference instanceof JSSourceMethod
                || reference instanceof JSSourceField) {
            IModelElement parent = ((IModelElement) reference).getParent();
            if (parent instanceof JSSourceType) {
                IModelElement parentOfParent = parent.getParent();
                if (parentOfParent instanceof VjoSourceModule) {
                    IJstType jstType = ((VjoSourceModule) parentOfParent)
                            .getJstType();
                    // Add by Austin to fix bug 8890. 2009-11-14 the jstType in
                    // VjoSourceModule for Window is null.
                    // need more investigation. Now just add a not null judgment
                    // since Window is not a mixin type.
                    if (jstType == null) {
                        return;
                    }
                    if (reference instanceof JSSourceMethod) {
                        IJstMethod jstMethod = jstType
                                .getMethod(((JSSourceMethod) reference)
                                        .getElementName());
                        if (jstMethod instanceof JstProxyMethod) {
                            return;
                        }
                    }
                    if (reference instanceof JSSourceField) {
                        IJstProperty jstProperty = jstType
                                .getProperty(((JSSourceField) reference)
                                        .getElementName());
                        if (jstProperty instanceof JstProxyProperty) {
                            return;
                        }
                    }
                }
                // String parentTypeName = ((JSSourceType) parent)
                // .getElementName();
                // // Sometimes the opened type is native, so its resource is
                // null.
                // if (((IModelElement) reference).getResource() != null) {
                // String belongingTypeName = ((IModelElement) reference)
                // .getResource().getName();
                // if (!(parentTypeName + ".js").equals(belongingTypeName)
                // && (parentTypeName + ".js").equals(parent
                // .getParent().getElementName())) {
                // return;
                // }
                // }
            }
        }

        ISelection selection = getSelectionProvider().getSelection();
        if (selection instanceof TextSelection) {
            TextSelection textSelection = (TextSelection) selection;
            // PR 39995: [navigation] Forward history cleared after going back
            // in navigation history:
            // mark only in navigation history if the cursor is being moved
            // (which it isn't if
            // this is called from a PostSelectionEvent that should only update
            // the magnet)
            if (moveCursor
                    && (textSelection.getOffset() != 0 || textSelection
                            .getLength() != 0))
                markInNavigationHistory();
        }
        if (reference != null) {
            StyledText textWidget = null;
            ISourceViewer sourceViewer = getSourceViewer();
            if (sourceViewer != null)
                textWidget = sourceViewer.getTextWidget();
            if (textWidget == null)
                return;
            try {
                ISourceRange range = null;
                range = reference.getSourceRange();
                if (range == null)
                    return;
                int offset = range.getOffset();
                int length = range.getLength();
                if (offset < 0 || length < 0)
                    return;
                setHighlightRange(offset, length, moveCursor);
                if (!moveCursor)
                    return;
                offset = -1;
                length = -1;
                if (reference instanceof IMember) {
                    range = ((IMember) reference).getNameRange();
                    if (range != null) {
                        offset = range.getOffset();
                        length = range.getLength();
                    }
                } else if (reference instanceof IImportDeclaration
                        || reference instanceof IPackageDeclaration) {
                    String name = ((IModelElement) reference).getElementName();
                    if (name != null && name.length() > 0) {
                        offset = range.getOffset();
                        length = range.getLength();
                    }
                }
                if (offset > -1 && length > 0) {
                    try {
                        textWidget.setRedraw(false);
                        sourceViewer.revealRange(offset, length);
                        sourceViewer.setSelectedRange(offset, length);
                    } finally {
                        textWidget.setRedraw(true);
                    }
                    markInNavigationHistory();
                }
            } catch (ModelException x) {
            } catch (IllegalArgumentException x) {
            }
        } else if (moveCursor) {
            resetHighlightRange();
            markInNavigationHistory();
        }
    }

    @Override
    protected void synchronizeOutlinePage(ISourceReference element,
            boolean checkIfOutlinePageActive) {
        super.synchronizeOutlinePage(element, checkIfOutlinePageActive);
    }

    /**
     * Uninstall the finder job for mark occurences.
     * 
     */
    protected void uninstallOccurrencesFinder() {
        m_markOccurrenceAnnotations = false;

        if (m_occurrencesFinderJob != null) {
            m_occurrencesFinderJob.cancel();
            m_occurrencesFinderJob = null;
        }

        if (m_occurrencesFinderJobCanceler != null) {
            m_occurrencesFinderJobCanceler.uninstall();
            m_occurrencesFinderJobCanceler = null;
        }

        if (m_postSelectionListenerWithAST != null) {
            SelectionListenerWithJSTManager.getDefault().removeListener(this,
                    m_postSelectionListenerWithAST);
            m_postSelectionListenerWithAST = null;
        }

        removeOccurrenceAnnotations();
    }

    /**
     * Updates the occurrences annotations based on the current selection.
     * 
     * @param selection
     *            the text selection
     * @param astRoot
     *            the compilation unit AST
     * @since 3.0
     */
    protected void updateOccurrenceAnnotations(ITextSelection selection,
            IModelElement astRoot) {
        try {
            // bug fix 2709

            if (!TypeSpaceMgr.getInstance().isLoaded()) {
                return;
            }
            doUpdateOccurrenceAnnotations(selection, astRoot);
        } catch (Exception e) {
            logError(e);
        }
    }

    private void checkFileSuffix(FileEditorInput input) {
        IFile file = input.getFile();
        if (file != null && !VjoLanguageToolkit.isVjetContentType(file)) {
            logUnexpectedDocumentKind(input);
        }
    }

    /**
     * Creates and returns the preference store for this Javascript editor with
     * the given input.
     * 
     * @param input
     *            The editor input for which to create the preference store
     * @return the preference store for this editor
     * 
     * @since 3.0
     */
    private IPreferenceStore createCombinedPreferenceStore(IEditorInput input) {
        List stores = new ArrayList(3);

        IScriptProject project = EditorUtility.getScriptProject(input);
        if (project != null) {
            stores.add(new EclipsePreferencesAdapter(new ProjectScope(project
                    .getProject()), VjetUIPlugin.PLUGIN_ID));
        }

        stores.add(VjetUIPlugin.getDefault().getPreferenceStore());
        stores.add(new PreferencesAdapter(VjetUIPlugin.getDefault()
                .getPluginPreferences()));
        stores.add(EditorsUI.getPreferenceStore());

        return new ChainedPreferenceStore((IPreferenceStore[]) stores
                .toArray(new IPreferenceStore[stores.size()]));
    }

    /**
     * Returns the lock object for the given annotation model.
     * 
     * @param annotationModel
     *            the annotation model
     * @return the annotation model's lock object
     * @since 3.0
     */
    private Object getLockObject(IAnnotationModel annotationModel) {
        if (annotationModel instanceof ISynchronizable) {
            Object lock = ((ISynchronizable) annotationModel).getLockObject();
            if (lock != null)
                return lock;
        }
        return annotationModel;
    }

    /**
     * Returns list of position {@link Position} of specified list of the
     * matches.
     * 
     * @param matches
     *            the list of matches {@link VjoMatch}
     * @return the list of position of matches.
     */
    private Position[] getPositions(List<VjoMatch> matches) {
        Position[] positions = new Position[matches.size()];
        int i = 0;
        for (Iterator each = matches.iterator(); each.hasNext();) {
            VjoMatch currentMatch = (VjoMatch) each.next();
            positions[i++] = new Position(currentMatch.getOffset(),
                    currentMatch.getLength());
        }

        return positions;
    }

    // modify by patrick
    /**
     * Checks if mark occurences of the selected jst node are enabled.
     * 
     * @param jstNode
     * @return true if mark occurences is enabled, otherwise false.
     */
    private boolean isEnableMarkOccurences(IJstNode jstNode) {
        boolean isEnabled = true;
        if ((jstNode instanceof IJstMethod) && (!m_markMethodOccurrences))
            isEnabled = false;
        else if (((jstNode instanceof JstVars) || (jstNode instanceof JstArg))
                && (!m_markLocalVariableTypeOccurrences))
            isEnabled = false;
        else if ((jstNode instanceof IJstProperty) && (!m_markFieldOccurrences))
            isEnabled = false;
        return isEnabled;
    }

    // end modify

    /**
     * filter the keyword that does not need update occurence marker
     * 
     * @param jstType
     * @return
     */
    private boolean isNeedOccurrencesMarker(VjoSourceModule sourceModule,
            ITextSelection textSelection) {
        IJstNode jstNode = JstUtil.getLeafNode(sourceModule.getJstType(),
                textSelection.getOffset(), textSelection.getOffset());
        if (jstNode instanceof JstIdentifier) {
            String identifierName = ((JstIdentifier) jstNode).getName();
            if ("this".equals(identifierName) || "outer".equals(identifierName) //$NON-NLS-1$ //$NON-NLS-2$
                    || "parent".equals(identifierName) //$NON-NLS-1$
                    || "vj$".equals(identifierName)) //$NON-NLS-1$
                return false;
        }

        return true;
    }

    private void logError(Exception e) {
        VjetPlugin.getDefault().getLog().log(
                new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID, IStatus.ERROR,
                        VjoEditorMessages.VjoEditor_21, e));
        System.err.println(e);
    }
    

    private void logUnexpectedDocumentKind(IEditorInput input) {
        // display a dialog informing user of uknown content type
        if (VjetUIPlugin.getDefault().getPreferenceStore().getBoolean(
                VjetPreferenceConstants.SHOW_UNKNOWN_CONTENT_TYPE_MSG)) {
            Job job = new UIJob(VjoEditorMessages.StructuredTextEditor_0) {
                public IStatus runInUIThread(IProgressMonitor monitor) {
                    UnknownContentTypeDialog dialog = new UnknownContentTypeDialog(
                            getSite().getShell(),
                            VjetUIPlugin.getDefault().getPreferenceStore(),
                            VjetPreferenceConstants.SHOW_UNKNOWN_CONTENT_TYPE_MSG);
                    dialog.open();
                    return Status.OK_STATUS;
                }
            };
            job.schedule();
        }

        String name = null;
        if (input != null) {
            name = input.getName();
        } else {
            name = "input was null"; //$NON-NLS-1$
        }
        VjetUIPlugin.log(IStatus.WARNING,
                "VJET editor being used on incorrect file type: " + name); //$NON-NLS-1$
    }

    /**
     * Uninstall Semantic Highlighting.
     * 
     * @since 3.0
     */
    private void uninstallSemanticHighlighting() {
        if (fSemanticManager != null) {
            fSemanticManager.uninstall();
            fSemanticManager = null;
        }
    }

}
