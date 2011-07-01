/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/



package org.ebayopensource.vjet.eclipse.internal.ui.view.typespace;

import java.net.URI;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.NodePrinterFactory;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceListener;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

/**
 * TypeSpace View
 * 
 * 
 *
 */
public class TypespaceView extends ViewPart {
	private TreeViewer m_viewer;
	private HideEmptyBinaryGroupAction m_hideAction = new HideEmptyBinaryGroupAction(this);
	private CollapseAllAction m_collapseAllAction = new CollapseAllAction(this);
	private TypespaceTreeContentProvider m_contentProvider;
	private TypespaceViewFilter m_filter = new TypespaceViewFilter();
	private IDoubleClickListener m_doubleClickListener = new InnerDoubleClickListener();
	private Text m_patternText;
	private Label m_libLabel;
	private Label m_proLabel;
	private Label m_typeLabel;
	private TypeSpaceListener m_typeSpaceListener = new TypeSpaceListener() {
		/* (non-Javadoc)
		 * @see org.ebayopensource.vjo.tool.typespace.TypeSpaceListener#loadTypesFinished()
		 */
		public void loadTypesFinished() {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					m_viewer.setInput(TypeSpaceMgr.getInstance());
					updateStateLabel();
				}
			});
		}
		
		/* (non-Javadoc)
		 * @see org.ebayopensource.vjo.tool.typespace.TypeSpaceListener#refreshFinished(java.util.List)
		 */
		public void refreshFinished(List<SourceTypeName> list) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					m_filter.clearCount();
					m_viewer.refresh();
					updateStateLabel();
				}

			});
		}
	};


	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		
		TypeSpaceMgr.getInstance().addTypeSpaceListener(this.m_typeSpaceListener);
	}
	private void fillActionBar() {
		IActionBars actionBars = this.getViewSite().getActionBars();
		actionBars.getToolBarManager().add(m_collapseAllAction);
		actionBars.getToolBarManager().add(m_hideAction);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(2, false));
		
		Label label = new Label(parent, SWT.NULL);
		label.setText("Search:");
		
		m_patternText = new Text(parent, SWT.BORDER);
		m_patternText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		m_patternText.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				//do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.CR) {
					filterAndRefreshTree();
				}
			}});

		
		//create tree viewer
		this.m_viewer = new TreeViewer(parent);
		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
		gridData.horizontalSpan = 2;
		this.m_viewer.getTree().setLayoutData(gridData);
		this.m_contentProvider = new TypespaceTreeContentProvider();
		this.m_viewer.setContentProvider(m_contentProvider);
		this.m_viewer.setLabelProvider(new TypespaceTreeLabelProvider());
		this.m_viewer.setFilters(new ViewerFilter[]{m_filter});
		this.m_viewer.addDoubleClickListener(m_doubleClickListener);





































		
		//set selection provider
		this.getSite().setSelectionProvider(this.m_viewer);
		
		//create state label
		Composite composite = new Composite(parent, SWT.NONE);
		gridData = new GridData(GridData.VERTICAL_ALIGN_END | GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan= 2;
		composite.setLayoutData(gridData);
		
		createStateLabel(composite);
		
		//if type space loading finished, set input
		if (TypeSpaceMgr.getInstance().isLoaded()) {
			m_filter.clearCount();
			this.m_viewer.setInput(TypeSpaceMgr.getInstance());
			updateStateLabel();
		}
		
		//context menu
	    this.initContextMenu();
	}
	
	private void createStateLabel(Composite composite) {
		composite.setLayout(new GridLayout(6, false));
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.minimumWidth = 90;
		Label proImageLable = new Label(composite, SWT.NONE);
		proImageLable.setImage(TypespaceTreeLabelProvider.getImageFromIDEImage(IDE.SharedImages.IMG_OBJ_PROJECT));
		m_proLabel = new Label(composite, SWT.NONE);
		m_proLabel.setLayoutData(data);
		m_proLabel.setText("...");
		m_proLabel.setToolTipText("Count of project group");
		Label libImageLable = new Label(composite, SWT.NONE);
		libImageLable.setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LIBRARY));
		m_libLabel = new Label(composite, SWT.NONE);
		m_libLabel.setLayoutData(data);
		m_libLabel.setText("...");
		libImageLable.setToolTipText("Count of binary group");
		Label typeImageLable = new Label(composite, SWT.NONE);
		typeImageLable.setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS));
		m_typeLabel = new Label(composite, SWT.NONE);
		m_typeLabel.setLayoutData(data);
		m_typeLabel.setText("...");
		libImageLable.setToolTipText("Count of all Jst type");
	}
	
	private void updateStateLabel() {
		this.m_proLabel.setText(String.valueOf(m_contentProvider.getProCount()));
		this.m_libLabel.setText(m_contentProvider.getOpenLibCount() + "/" + m_contentProvider.getLibCount());
		int totalCount = m_contentProvider.getTotalTypeCount();
		int selectedTypeCount = m_filter.isEmptyFilter() ? totalCount : m_filter.getSelectedTypeCount();
		this.m_typeLabel.setText(selectedTypeCount + "/" + totalCount);
		((Composite)m_proLabel.getParent()).layout();
	}
	
	private void filterAndRefreshTree() {
		m_filter.clearCount();
		String pattern = m_patternText.getText();
		m_filter.setFilterStr(pattern);
		if (m_viewer.getInput() == null) {
			m_viewer.setInput(TypeSpaceMgr.getInstance());
		}
		m_viewer.refresh();
		if(!m_filter.isEmptyFilter()) {
			m_viewer.expandToLevel(2);
		}
		updateStateLabel();
	}

	private void initContextMenu() {
		MenuManager popupMenuManager = new MenuManager("#PopupMenu");
		
		popupMenuManager.add(new Action("Copy Structure") {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				Object selectedNode = ((IStructuredSelection)m_viewer.getSelection()).getFirstElement();
				String structure = copyStructure(selectedNode, 0);
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				clipboard.setContents(new Object[] {structure}, new Transfer[] {TextTransfer.getInstance()});
				clipboard.dispose();
			}
		});
		
		popupMenuManager.add(new Action("Copy Properties") {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				Object selectedNode = ((IStructuredSelection)m_viewer.getSelection()).getFirstElement();
				String properties = copyProperties(selectedNode);
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				clipboard.setContents(new Object[] {properties}, new Transfer[] {TextTransfer.getInstance()});
				clipboard.dispose();
			}
		});
		
		Menu popupMenu = popupMenuManager.createContextMenu(this.m_viewer.getTree());
		this.m_viewer.getTree().setMenu(popupMenu);
		getSite().registerContextMenu(popupMenuManager, this.m_viewer); 
		
		fillActionBar();
	}
	
	private String copyStructure(Object node, int tier) {
		ILabelProvider labelProvider = (ILabelProvider)this.m_viewer.getLabelProvider();
		StringBuilder structureBuilder = new StringBuilder(labelProvider.getText(node));
		
		ITreeContentProvider contentProvider = (ITreeContentProvider)this.m_viewer.getContentProvider();
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
	
	@Override
	public void dispose() {
		TypeSpaceMgr.getInstance().removeTypeSpaceListener(this.m_typeSpaceListener);
	}
	public void updateHideState() {
		m_filter.clearCount();
		m_contentProvider.setHideEmptyBinaryGoupr(!m_contentProvider.ifHideEmptyBinaryGroup());
		m_viewer.refresh();
		updateStateLabel();
	}
	public boolean ifHideEmptyBinaryGroup() {
		return m_contentProvider.ifHideEmptyBinaryGroup();
		
	}
	public void collapseAll() {
		m_viewer.collapseAll();
	}
	
	private class InnerDoubleClickListener implements IDoubleClickListener {
		/* 
		 * select source code range in editor
		 * 
		 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
		 */
		public void doubleClick(DoubleClickEvent event) {
			IStructuredSelection structuredSelection = (IStructuredSelection)event.getSelection();
			if (!(structuredSelection.getFirstElement() instanceof IJstNode))
				return;
			
			Object firstElement = structuredSelection.getFirstElement();
			if (!(firstElement instanceof IJstType))
				return;
			
			IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			
			IJstType jstType = (IJstType)firstElement;
			URI uri = TypeSpaceMgr.getInstance().getTypeToFileMap().get(jstType.getPackage().getGroupName() + "#" + jstType.getName());
			
			try {
				//process native type
				if (jstType != null && uri == null) {
					IType vjoSourceType = CodeassistUtils.findNativeSourceType(jstType);
					DLTKUIPlugin.openInEditor(vjoSourceType, true, true);
					return;
				}
				
				else {
					//process source type
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(workspacePath.append(uri.getPath()));
					IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
