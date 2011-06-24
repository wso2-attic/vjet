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
package org.ebayopensource.vjet.eclipse.internal.ui.view.ast;

import java.io.File;
import java.io.FileReader;
import java.util.Collections;

import org.apache.tools.ant.util.FileUtils;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.NodePrinterFactory;
import org.ebayopensource.vjet.eclipse.internal.ui.text.SimpleVjoSourceViewerConfiguration;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.dltk.mod.ui.util.PixelConverter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.mod.wst.jsdt.core.compiler.IProblem;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.part.ViewPart;

/**
 * ast view
 * 
 * 
 *
 */
public class ASTView extends ViewPart implements ISelectionListener {
	private TreeViewer leftTreeViewer;
	private TreeViewer rightTreeViewer;
	
	private VjoEditor vjoEditor;  //the editor  that provide the content
	private IPartListener partListener = new PartListener();
	private IDocumentListener documentListener = new DocumentListener();
	private IDoubleClickListener doubleClickListener = new DoubleClickListener();
	
	private CompilationUnitDeclaration orignalAst;
	private CompilationUnitDeclaration recoveryAst;
	
	private ScriptSourceViewer leftSourceViewer;
	private ScriptSourceViewer rightSourceViewer;
	
	private SkipDocumentChangeAction skipAction = new SkipDocumentChangeAction();
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		
		this.getSite().getPage().getWorkbenchWindow().getSelectionService().addPostSelectionListener(this);
		this.getSite().getPage().getWorkbenchWindow().getPartService().addPartListener(partListener);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
		
		//fill action bar
		this.fillActionBar();
		
		SashForm fullForm = new SashForm(parent, SWT.HORIZONTAL);
		fullForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//left panel
		SashForm leftForm = new SashForm(fullForm, SWT.VERTICAL);
		leftForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.leftTreeViewer = this.createTreeViewer(leftForm, false);
		this.leftSourceViewer = this.createSourceViewer(leftForm);
		this.leftTreeViewer.addSelectionChangedListener(new SelectionChangeListener(this.leftSourceViewer));
		
		//right panel
		SashForm rightForm = new SashForm(fullForm, SWT.VERTICAL);
		rightForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.rightTreeViewer = this.createTreeViewer(rightForm, true);
		this.rightSourceViewer = this.createSourceViewer(rightForm);
		this.rightTreeViewer.addSelectionChangedListener(new SelectionChangeListener(this.rightSourceViewer));
		
		//set selection provider
		this.getSite().setSelectionProvider(this.leftTreeViewer);
		
	}
	
	private void fillActionBar() {
		IActionBars actionBars = this.getViewSite().getActionBars();
		actionBars.getToolBarManager().add(skipAction);
	}
	
	private TreeViewer createTreeViewer(Composite parent, boolean isRecovery) {
		TreeViewer treeViewer = new TreeViewer(parent);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		treeViewer.setContentProvider(new ASTTreeContentProvider());
		treeViewer.setLabelProvider(new ASTTreeLabelProvider(isRecovery));
		
		treeViewer.addDoubleClickListener(this.doubleClickListener);
		this.initContextMenu(treeViewer);
		
		return treeViewer;
	}
	
	private ScriptSourceViewer createSourceViewer(Composite parent) {
		IDocument document= new Document();
		IDLTKUILanguageToolkit toolkit = DLTKUILanguageManager.getLanguageToolkit(VjoNature.NATURE_ID);
		ScriptTextTools textTools = VjetUIPlugin.getDefault().getTextTools();
		textTools.setupDocumentPartitioner(document, IJavaScriptPartitions.JS_PARTITIONING);
		IPreferenceStore store = toolkit.getPreferenceStore();
		
		ScriptSourceViewer viewer = new ScriptSourceViewer(parent, null, null, false, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, store);
		
//		ScriptSourceViewerConfiguration configuration = toolkit.createSourceViewerConfiguration();
		ScriptSourceViewerConfiguration configuration = new SimpleVjoSourceViewerConfiguration(toolkit.getTextTools()
				.getColorManager(), toolkit.getPreferenceStore(), null,
				IJavaScriptPartitions.JS_PARTITIONING, false);
		viewer.configure(configuration);
		viewer.setEditable(false);
		viewer.setDocument(document);
		
		Control control= viewer.getControl();
		GridData data= new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_HORIZONTAL);
		data.heightHint= new PixelConverter(parent).convertHeightInCharsToPixels(3);
		control.setLayoutData(data);
		
		return viewer;
	}
	
	
	private void initContextMenu(TreeViewer viewer) {
		MenuManager popupMenuManager = new MenuManager("#PopupMenu");
		
		popupMenuManager.add(new CopyStructureAction(viewer));
		popupMenuManager.add(new CopyPropertiesAction(viewer));

		Menu popupMenu = popupMenuManager.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(popupMenu);
		getSite().registerContextMenu(popupMenuManager, viewer); 
	}
	
	private String copyStructure(Object node, int tier) {
		ILabelProvider labelProvider = (ILabelProvider)this.leftTreeViewer.getLabelProvider();
		StringBuilder structureBuilder = new StringBuilder(labelProvider.getText(node));
		
		ITreeContentProvider contentProvider = (ITreeContentProvider)this.leftTreeViewer.getContentProvider();
		Object[] children = contentProvider.getChildren(node);
		++ tier;
		for (int i = 0; i < children.length; i++) {
			String childrenStructure = copyStructure(children[i], tier);
			structureBuilder.append("\n");
			for (int j = 0; j < tier; j++) {
				structureBuilder.append("\t");
			}
			structureBuilder.append(childrenStructure);
		}
		return structureBuilder.toString();
	}
	
	private String copyProperties(Object node) {
		INodePrinter nodePrinter = NodePrinterFactory.getNodePrinter(node);
		if (nodePrinter == null)
			return "";
		
		String[] names = nodePrinter.getPropertyNames(node);
		if (names.length == 0)
			return "";
		
		StringBuilder stringBuilder = new StringBuilder();
		Object[] values = nodePrinter.getPropertyValuies(node);
		for (int i = 0; i < names.length; i++) {
			stringBuilder.append(names[i] + ":" + values[i]);
			if (i != names.length - 1)
				stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!(part instanceof IEditorPart))
			return;
		
		if (!(part instanceof VjoEditor)) {
			this.cleanUp();
			return;
		}
		
		if (this.vjoEditor != part) {
			IModelElement modelElement = ((VjoEditor)part).getInputModelElement();
			if (!(modelElement instanceof VjoSourceModule)) {
				this.cleanUp();
				return;
			}
			
			VjoSourceModule sourceModule = ((VjoSourceModule)modelElement);
			CompilationUnitDeclaration ast = getASTUnit(sourceModule, (VjoEditor)part);
			
			//add to fix NPE
			if (ast == null)
				return;
			
			CompilationUnitDeclaration recoveryAST = getRecoveryASTUnit(ast, sourceModule, (VjoEditor)part);
			this.orignalAst = ast;
			this.recoveryAst = recoveryAST;
			this.leftTreeViewer.setInput(new Object[]{ast});
			this.rightTreeViewer.setInput(new Object[]{recoveryAST});
			
			this.vjoEditor = (VjoEditor)part;
			this.vjoEditor.getScriptSourceViewer().getDocument().addDocumentListener(this.documentListener);
			
			this.leftTreeViewer.expandAll();
			this.leftTreeViewer.collapseAll();
			
			this.rightTreeViewer.expandAll();
			this.rightTreeViewer.collapseAll();
		}
		
		//select the corresponding ast node
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection)selection;
			
			IASTNode node = getASTNode(this.orignalAst, textSelection.getOffset());
			if (node != null)
				this.leftTreeViewer.setSelection(new StructuredSelection(node), true);
			
			node = getASTNode(this.recoveryAst, textSelection.getOffset());
			if (node != null)
				this.rightTreeViewer.setSelection(new StructuredSelection(node), true);
		}
	}
	
	private IASTNode getASTNode(CompilationUnitDeclaration ast, int offset) {
		IASTNode parentNode = ast;
		while (getSubNode(parentNode, offset) != null) {
			parentNode = getSubNode(parentNode, offset);
		}
		
		return parentNode;
	}
	
	private IASTNode getSubNode(IASTNode parentNode, int offset) {
		ITreeContentProvider contentProvider = (ITreeContentProvider)this.leftTreeViewer.getContentProvider();
		Object[] children = contentProvider.getChildren(parentNode);
		if (children == null || children.length == 0)
			return null;
		
		for (int i = 0; i < children.length; i++) {
			if (!(children[i] instanceof IASTNode))
				continue;
			
			IASTNode child = (IASTNode)children[i];
			if (offset >= child.sourceStart() && offset < child.sourceEnd())
				return child;
		}
		
		return null;
	}
	
	/**
	 * create the recovery AST Unit based on the existing ast unit
	 * 
	 * @param declaration
	 * @param sourceModule
	 * @param vjoEditor
	 * @return
	 */
	private CompilationUnitDeclaration getRecoveryASTUnit(CompilationUnitDeclaration declaration, VjoSourceModule sourceModule, VjoEditor vjoEditor) {
		String fileName = getFileName(sourceModule, vjoEditor);
		final String jsSource = getSource(sourceModule, vjoEditor);
		return SyntaxTreeFactory2.fixedProblems(Collections.EMPTY_MAP, jsSource.toCharArray(), fileName, null, declaration);
	}
	
	/**
	 * create the AST unit
	 * 
	 * @param sourceModule
	 * @param vjoEditor
	 * @return
	 */
	private CompilationUnitDeclaration getASTUnit(VjoSourceModule sourceModule, VjoEditor vjoEditor) {
		try {
			String fileName = getFileName(sourceModule, vjoEditor);
			final String jsSource = getSource(sourceModule, vjoEditor);
			CompilationUnitDeclaration ast = SyntaxTreeFactory2.
				createASTCompilationResult(Collections.EMPTY_MAP, jsSource.toCharArray(), fileName, null)
				.getCompilationUnitDeclaration();
			return ast;
		} catch (Exception e) {
			return null;
		}
	}

	private String getFileName(VjoSourceModule sourceModule, VjoEditor vjoEditor) {
		if (sourceModule instanceof NativeVjoSourceModule) {
			IEditorInput editorInput = vjoEditor.getEditorInput();
			String filePath = ((ILocationProvider)editorInput).getPath(editorInput).toOSString();
			return new File(filePath).getName();
		}
		else {
			return new String(sourceModule.getFileName());
		}
	}
	
	private String getSource(VjoSourceModule sourceModule, VjoEditor vjoEditor) {
		try {
			if (sourceModule instanceof NativeVjoSourceModule) {
				IEditorInput editorInput = vjoEditor.getEditorInput();
				String filePath = ((ILocationProvider)editorInput).getPath(editorInput).toOSString();
				return FileUtils.readFully(new FileReader(filePath));
			}
			else {
				return  convertHTMLCommentsToJsComments(sourceModule.getSource());
			}
		} catch (Exception e) {
			DLTKUIPlugin.log(e);
			return null;
		}
	}
	
	private String convertHTMLCommentsToJsComments(String source) {
		if(source.contains("<!--")){
			source = source.replace("<!--", "//--");
		}
		if(source.contains("-->")){
			source = source.replace("-->", "///");
		}
		return source;
		
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	public void dispose() {
		if (this.vjoEditor != null)
			this.vjoEditor.getScriptSourceViewer().getDocument().removeDocumentListener(this.documentListener);
		
		super.dispose();
		
		this.getSite().getPage().getWorkbenchWindow().getSelectionService().removePostSelectionListener(this);
		this.getSite().getPage().getWorkbenchWindow().getPartService().removePartListener(partListener);
	}
	
	private void cleanUp() {
		this.leftTreeViewer.setInput(null);
		this.rightTreeViewer.setInput(null);
		
		if (this.vjoEditor != null)
			this.vjoEditor.getScriptSourceViewer().getDocument().removeDocumentListener(this.documentListener);
		this.vjoEditor = null;
	}

	/**
	 * vjo editor part listener
	 * 
	 * 
	 *
	 */
	private class PartListener implements IPartListener {
		public void partActivated(IWorkbenchPart part) {
		}
		
		public void partBroughtToTop(IWorkbenchPart part) {
		}
		
		/* 
		 * clean the content in script view
		 * 
		 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
		 */
		public void partClosed(IWorkbenchPart part) {
			if (vjoEditor == part) {
				cleanUp();
			}
		}
		
		public void partDeactivated(IWorkbenchPart part) {
			// TODO Auto-generated method stub
		}
		
		public void partOpened(IWorkbenchPart part) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * update ast unit view, when ast unit changed (document changed)
	 * 
	 * 
	 *
	 */
	private class DocumentListener implements IDocumentListener {
		public void documentChanged(DocumentEvent event) {
			//if skip document change, return
			if (skipAction.isChecked())
				return;
			IModelElement modelElement = vjoEditor.getInputModelElement();
			if (!(modelElement instanceof VjoSourceModule)) {
				return;
			}
			VjoSourceModule sourceModule = (VjoSourceModule)modelElement;
			CompilationUnitDeclaration astUnit = getASTUnit(sourceModule, vjoEditor);
			
			//add to fix NPE
			if (astUnit == null)
				return;
			
			CompilationUnitDeclaration recoveryUnit = getRecoveryASTUnit(astUnit, sourceModule, vjoEditor);
			orignalAst = astUnit;
			recoveryAst = recoveryUnit;
			
			leftTreeViewer.setInput(new Object[] {astUnit});
			leftTreeViewer.expandAll();
			leftTreeViewer.collapseAll();
			rightTreeViewer.setInput(new Object[]{recoveryUnit});
			rightTreeViewer.expandAll();
			rightTreeViewer.collapseAll();
		}
		
		public void documentAboutToBeChanged(DocumentEvent event) {
			// nothing to do
		}
	}
	
	/**
	 * tree viewer selection change listener, fetch ast code and update the source viewer
	 * 
	 * 
	 *
	 */
	private class SelectionChangeListener implements ISelectionChangedListener {
		private ScriptSourceViewer sourceViewer;
		
		public SelectionChangeListener(ScriptSourceViewer sourceViewer) {
			this.sourceViewer = sourceViewer;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			Object object = ((TreeSelection)event.getSelection()).getFirstElement();
			if (object instanceof IProblem) {
				String message = ((IProblem)object).getMessage();
				
				//update source viewer
				IDocument document = this.sourceViewer.getDocument();
				document.set(message);
				this.sourceViewer.setDocument(document, 0, document.getLength());
			}
			
			if (!(object instanceof IASTNode))
				return;
			
			String astCode = ((IASTNode)object).toString();
			if (astCode == null)
				return;
			
			//update source viewer
			IDocument document = this.sourceViewer.getDocument();
			document.set(astCode);
			this.sourceViewer.setDocument(document, 0, document.getLength());
		}
	}
	
	/**
	 * 
	 *
	 */
	private class DoubleClickListener implements IDoubleClickListener {
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
		 */
		public void doubleClick(DoubleClickEvent event) {
			Object element = ((IStructuredSelection)event.getSelection()).getFirstElement();
			if (!(element instanceof IASTNode) && !(element instanceof IProblem))
				return;
			
			if (element instanceof IProblem) {
				IProblem problem = (IProblem)element;
				int length = problem.getSourceEnd() - problem.getSourceStart() + 1;
				vjoEditor.getScriptSourceViewer().revealRange(problem.getSourceStart(), length);
				vjoEditor.getScriptSourceViewer().setSelectedRange(problem.getSourceStart(), length);
			}
			
			if (element instanceof IASTNode) {
				IASTNode node = (IASTNode)element;
				int length = node.sourceEnd() - node.sourceStart() + 1;
				vjoEditor.getScriptSourceViewer().revealRange(node.sourceStart(), length);
				vjoEditor.getScriptSourceViewer().setSelectedRange(node.sourceStart(), length);
			}
		}
	};
	
	private class CopyStructureAction extends Action {
		private TreeViewer viewer;
		
		public CopyStructureAction(TreeViewer viewer) {
			super("Copy Structure");
			
			this.viewer = viewer;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			Object selectedNode = ((IStructuredSelection)this.viewer.getSelection()).getFirstElement();
			String structure = copyStructure(selectedNode, 0);
			Clipboard clipboard = new Clipboard(Display.getCurrent());
			clipboard.setContents(new Object[] {structure}, new Transfer[] {TextTransfer.getInstance()});
			clipboard.dispose();
		}
	}
	
	private class CopyPropertiesAction extends Action {
		private TreeViewer viewer;
		
		public CopyPropertiesAction(TreeViewer viewer) {
			super("Copy Properties");
			
			this.viewer = viewer;
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			Object selectedNode = ((IStructuredSelection)this.viewer.getSelection()).getFirstElement();
			String properties = copyProperties(selectedNode);
			Clipboard clipboard = new Clipboard(Display.getCurrent());
			clipboard.setContents(new Object[] {properties}, new Transfer[] {TextTransfer.getInstance()});
			clipboard.dispose();
		}
	}
}
