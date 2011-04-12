/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.views;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.DelegatingModelPresentation;
import org.eclipse.debug.internal.ui.sourcelookup.SourceLookupResult;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.debug.core.DLTKDebugConstants;
import org.eclipse.dltk.mod.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.mod.debug.core.model.IScriptThread;
import org.eclipse.dltk.mod.internal.core.VjoExternalSourceModule;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.command.VjetSourceCommands;
import org.ebayopensource.vjet.eclipse.internal.debug.ui.VjetDebugUIMessages;
import org.ebayopensource.vjet.eclipse.internal.launching.VjoDBGPSourceModule;
import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditor;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;
import org.ebayopensource.vjet.eclipse.ui.VjetUIImages;

/**
 * View for browsing running scripts in debugger engine
 * 
 *  Ouyang
 * 
 */
public class RunningScriptView extends ViewPart implements ISelectionListener,
		IDebugEventSetListener {

	private class NameSorter extends ViewerSorter {

		boolean	m_order;

		NameSorter(boolean asc) {
			this.m_order = asc;
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return m_order ? super.compare(viewer, e1, e2) : super.compare(
					viewer, e2, e1);
		}

	}

	private class RunningScriptViewContentProvider implements
			IStructuredContentProvider {

		@Override
		public void dispose() {
			// do nothing
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (!(inputElement instanceof URI[])) {
				return new URI[0];
			}
			return (URI[]) inputElement;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing
		}

	}

	private class RunningScriptViewLabelProvider extends LabelProvider
			implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			Image img = null;
			switch (columnIndex) {
			case 0:
				try {
					Object sourceElement = getSourceElement((URI) element);
					img = getElementImage(sourceElement);
				} catch (Exception e) {
					VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
				}
				break;
			default:
				break;
			}
			return img;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
			case 1:
				return format(element);
			}
			return null;
		}

		private String format(Object element) {
			URI uri = (URI) element;
			String text = "";
			if (DLTKDebugConstants.DBGP_SCHEME.equals(uri.getScheme())) {
				Object sourceElement;
				try {
					sourceElement = getSourceElement(uri);
					if (sourceElement instanceof VjoExternalSourceModule) {
						text = ((VjoExternalSourceModule) sourceElement)
								.getName();
					}
				} catch (Exception e) {
					VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
				}
			}
			if (text.isEmpty()) {
				text = uri.toString();
			}
			return text;
		}

	}

	public static final String	ID			= "org.ebayopensource.vjet.eclipse.debug.ui.runningScriptView";	//$NON-NLS-1$

	private Map<URI, Object>	m_contentsCache;

	private IScriptThread		m_lastSelectedThread;

	private Action				m_openFileAction;
	private TableViewer			m_viewer;
	private Image				m_defaultScriptImg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		m_viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION
				| SWT.H_SCROLL | SWT.V_SCROLL);
		m_viewer.setContentProvider(new RunningScriptViewContentProvider());
		m_viewer.setLabelProvider(new RunningScriptViewLabelProvider());
		m_viewer.setSorter(new NameSorter(true));

		Table table = m_viewer.getTable();
		table.setHeaderVisible(true);

		// column 1
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setWidth(20);

		// column 2
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(VjetDebugUIMessages.RunningScriptView_column_name);
		column.setWidth(600);
		column.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				NameSorter sorter = (NameSorter) m_viewer.getSorter();
				sorter.m_order = !sorter.m_order;
				m_viewer.setSorter(sorter);
				m_viewer.refresh();
			}
		});

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	@Override
	public void dispose() {
		super.dispose();

		// remove listeners
		getSite().getWorkbenchWindow().getSelectionService()
				.removePostSelectionListener(this);

		DebugPlugin.getDefault().removeDebugEventListener(this);

		// dispose resource
		if (m_defaultScriptImg != null) {
			m_defaultScriptImg.dispose();
		}

		if (m_viewer != null) {
			m_viewer.getTable().dispose();
		}
	}

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		// fetch the script list only to breakpoint suspend event
		for (DebugEvent event : events) {
			Object source = event.getSource();
			int kind = event.getKind();
			int detail = event.getDetail();
			switch (kind) {
			case DebugEvent.SUSPEND:
				switch (detail) {
				case DebugEvent.BREAKPOINT:
				case DebugEvent.STEP_END:
					if (source instanceof IScriptThread) {
						fetchScriptList((IScriptThread) source);
					}
					break;
				default:
					break;
				}

				break;
			case DebugEvent.TERMINATE:
				if (source instanceof IScriptThread) {
					if (source.equals(m_lastSelectedThread)) {
						clear();
						m_lastSelectedThread = null;
					}
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		// create img resource
		IEditorRegistry editorRegistry = PlatformUI.getWorkbench()
				.getEditorRegistry();
		IEditorDescriptor editorDesc = editorRegistry
				.findEditor(VjoEditor.EDITOR_ID);
		ImageDescriptor imageDesc = editorDesc != null ? editorDesc
				.getImageDescriptor() : null;
		m_defaultScriptImg = imageDesc != null ? imageDesc.createImage() : null;

		// add listener
		ISelectionService selectionService = getSite().getWorkbenchWindow()
				.getSelectionService();
		selectionService.addPostSelectionListener(this);

		DebugPlugin.getDefault().addDebugEventListener(this);

		// fill data if there is debug thread selected
		ISelection selection = selectionService
				.getSelection("org.eclipse.debug.ui.DebugView");
		if (selection != null) {
			selectionChanged(null, selection);
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		Object sel = ((IStructuredSelection) selection).getFirstElement();
		if (!(sel instanceof IScriptThread)
				&& !(sel instanceof IScriptStackFrame)) {
			return;
		}
		IScriptThread currentSelectedThread = (IScriptThread) (sel instanceof IScriptThread ? sel
				: ((IScriptStackFrame) sel).getThread());

		// verify selection is same with last time
		if (m_lastSelectedThread != null
				&& m_lastSelectedThread.equals(currentSelectedThread)) {
			return;
		}

		// handle event
		handleSelectionChangedEvent(currentSelectedThread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		m_viewer.getControl().setFocus();
	}

	protected void openEditor(Object input) {
		if (!(input instanceof URI)) {
			return;
		}
		URI uri = (URI) input;
		try {
			Object sourceElement = getSourceElement(uri);
			if (sourceElement == null) {
				return;
			}
			IDebugModelPresentation presentation = ((DelegatingModelPresentation) DebugUIPlugin
					.getModelPresentation())
					.getPresentation(m_lastSelectedThread.getModelIdentifier());
			IEditorInput editorInput = null;
			String editorId = null;
			if (presentation == null) {
				return;
			}
			editorInput = presentation.getEditorInput(sourceElement);
			editorId = presentation.getEditorId(editorInput, sourceElement);
			SourceLookupResult result = new SourceLookupResult(uri,
					sourceElement, editorId, editorInput);
			DebugUITools.displaySource(result, getViewSite().getPage());
		} catch (Exception e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}

	}

	private void clear() {
		new UIJob(getDisplay(), "") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				m_viewer.setInput(new URI[0]);
				m_viewer.refresh();
				return Status.OK_STATUS;
			}
		}.schedule();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
	}

	private void fetchScriptList(final IScriptThread thread) {
		if (thread.isTerminated()) {
			return;
		}

		m_lastSelectedThread = thread;

		// clear cached source contents
		if (m_contentsCache != null) {
			m_contentsCache.clear();
		}

		new UIJob(getDisplay(),
				VjetDebugUIMessages.RunningScriptView_refresh_job_name) {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					VjetSourceCommands vjetSourceCommands = new VjetSourceCommands(
							thread.getDbgpSession().getCommunicator());
					URI[] fileURIs = vjetSourceCommands.list();
					m_viewer.setInput(fileURIs);
				} catch (Exception e) {
					return new Status(IStatus.ERROR,
							VjetDebugUIPlugin.PLUGIN_ID, e
									.getLocalizedMessage(), e);
				}
				return Status.OK_STATUS;
			}
		}.schedule();
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(m_openFileAction);
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(m_openFileAction);
	}

	private Display getDisplay() {
		return getViewSite().getShell().getDisplay();
	}

	private Image getElementImage(Object object) {
		Image img = null;
		if (object instanceof VjoExternalSourceModule) {
			img = VjetUIImages.getImage(VjetUIImages.IMAGE_BINARY_EDITOR_TITLE);
		} else {
			img = m_defaultScriptImg;
		}
		return img;
	}

	private Object getSourceElement(URI uri) throws MalformedURLException,
			ModelException {
		if (m_contentsCache == null) {
			m_contentsCache = new HashMap<URI, Object>();
		}
		Object sourceElement = m_contentsCache.get(uri);
		if (sourceElement != null) {
			return sourceElement;
		}
		if (m_lastSelectedThread == null) {
			return null;
		}
		ISourceLocator sourceLocator = m_lastSelectedThread.getLaunch()
				.getSourceLocator();
		if (sourceLocator != null
				&& sourceLocator instanceof ISourceLookupDirector) {
			sourceElement = ((ISourceLookupDirector) sourceLocator)
					.getSourceElement(uri);
			if (sourceElement instanceof VjoDBGPSourceModule) {
				VjoDBGPSourceModule sm = (VjoDBGPSourceModule) sourceElement;
				sm.setDBGPSession(m_lastSelectedThread.getDbgpSession());
			}
			return sourceElement;
		}

		m_contentsCache.put(uri, sourceElement);
		return sourceElement;
	}

	private void handleSelectionChangedEvent(IScriptThread selectedThread) {
		fetchScriptList(selectedThread);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				RunningScriptView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(m_viewer.getControl());
		m_viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, m_viewer);
	}

	private void hookDoubleClickAction() {
		m_viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				m_openFileAction.run();
			}
		});

	}

	private void makeActions() {
		m_openFileAction = new Action(
				VjetDebugUIMessages.RunningScriptView_open_file_action_name) {

			@Override
			public void run() {
				ISelection selection = m_viewer.getSelection();
				Object element = ((IStructuredSelection) selection)
						.getFirstElement();
				openEditor(element);
			}
		};
		m_openFileAction
				.setToolTipText(VjetDebugUIMessages.RunningScriptView_open_file_action_tooltip);

	}

}
