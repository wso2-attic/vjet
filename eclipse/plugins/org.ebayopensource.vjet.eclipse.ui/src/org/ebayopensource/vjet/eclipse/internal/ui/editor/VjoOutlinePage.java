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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.ebayopensource.vjet.eclipse.internal.ui.filters.FieldsFilter;
import org.ebayopensource.vjet.eclipse.internal.ui.filters.LocalTypesFilter;
import org.ebayopensource.vjet.eclipse.internal.ui.filters.NonPublicFilter;
import org.ebayopensource.vjet.eclipse.internal.ui.filters.StaticsFilter;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.IHelpContextIds;
import org.ebayopensource.vjet.eclipse.ui.viewsupport.VjoLabelProvider;
import org.ebayopensource.vjet.eclipse.ui.viewsupport.VjoSourcePositionSorter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.ElementChangedEvent;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IElementChangedListener;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelElementDelta;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IParent;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceReference;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.ScriptModelUtil;
import org.eclipse.dltk.mod.internal.core.AbstractSourceModule;
import org.eclipse.dltk.mod.internal.core.JSSourceMethod;
import org.eclipse.dltk.mod.internal.ui.actions.AbstractToggleLinkingAction;
import org.eclipse.dltk.mod.internal.ui.actions.CCPActionGroup;
import org.eclipse.dltk.mod.internal.ui.actions.CompositeActionGroup;
import org.eclipse.dltk.mod.internal.ui.dnd.DLTKViewerDragSupport;
import org.eclipse.dltk.mod.internal.ui.dnd.DLTKViewerDropSupport;
import org.eclipse.dltk.mod.internal.ui.editor.DLTKEditorMessages;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptOutlinePage;
import org.eclipse.dltk.mod.internal.ui.editor.TogglePresentationAction;
import org.eclipse.dltk.mod.javascript.internal.ui.editor.ActionMessages;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.IContextMenuConstants;
import org.eclipse.dltk.mod.ui.MembersOrderPreferenceCache;
import org.eclipse.dltk.mod.ui.ModelElementSorter;
import org.eclipse.dltk.mod.ui.PreferenceConstants;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.actions.CustomFiltersActionGroup;
import org.eclipse.dltk.mod.ui.actions.GenerateActionGroup;
import org.eclipse.dltk.mod.ui.actions.MemberFilterActionGroup;
import org.eclipse.dltk.mod.ui.actions.OpenViewActionGroup;
import org.eclipse.dltk.mod.ui.actions.SearchActionGroup;
import org.eclipse.dltk.mod.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.dltk.mod.ui.viewsupport.DecoratingModelLabelProvider;
import org.eclipse.dltk.mod.ui.viewsupport.MemberFilterAction;
import org.eclipse.dltk.mod.ui.viewsupport.SourcePositionSorter;
import org.eclipse.dltk.mod.ui.viewsupport.StatusBarUpdater;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchAdapter;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * The content outline page of the Java editor. The viewer implements a
 * proprietary update mechanism based on Java model deltas. It does not react on
 * domain changes. It is specified to show the content of ICompilationUnits and
 * IClassFiles. Publishes its context menu under
 * <code>JavaPlugin.getDefault().getPluginId() + ".outline"</code>.
 */
// TODO cleanup this class
public class VjoOutlinePage extends ScriptOutlinePage {

	/**
	 * Content provider for the children of an ICompilationUnit or an IClassFile
	 * 
	 * @see ITreeContentProvider
	 */
	protected class ChildrenProvider implements ITreeContentProvider {

		private Object[] NO_CLASS = new Object[] { new NoClassElement() };
		private ElementChangedListener fListener;

		public void dispose() {
			if (fListener != null) {
				DLTKCore.removeElementChangedListener(fListener);
				fListener = null;
			}
		}

		protected IModelElement[] filter(IModelElement[] children) {
			boolean initializers = false;
			for (int i = 0; i < children.length; i++) {
				if (matches(children[i])) {
					initializers = true;
					break;
				}
			}

			if (!initializers) {
				return children;
			}

			Vector v = new Vector();
			for (int i = 0; i < children.length; i++) {
				if (matches(children[i])) {
					continue;
				}
				v.addElement(children[i]);
			}

			IModelElement[] result = new IModelElement[v.size()];
			v.copyInto(result);
			return result;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof IParent) {
				IParent c = (IParent) parent;
				try {
					return filter(c.getChildren());
				} catch (ModelException x) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
					// don't log NotExist exceptions as this is a valid case
					// since we might have been posted and the element
					// removed in the meantime.
					if (DLTKCore.DEBUG || !x.isDoesNotExist()) {
						DLTKUIPlugin.log(x);
					}
				}
			}
			return NO_CHILDREN;
		}

		public Object[] getElements(Object parent) {
			if (fTopLevelTypeOnly) {
				if (parent instanceof AbstractSourceModule) {
					try {
						IModelElement[] types = ((AbstractSourceModule) parent)
								.getTypes();
						if (types.length > 0 && types[0] instanceof IType) {
							return types[0] != null ? ((IType) types[0])
									.getChildren() : NO_CLASS;

						}
					} catch (Exception e) {
						JavaPlugin.log(e);
					}
				}
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof IModelElement) {
				IModelElement e = (IModelElement) child;
				return e.getParent();
			}
			return null;
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof IParent) {
				IParent c = (IParent) parent;
				try {
					IModelElement[] children = filter(c.getChildren());
					return (children != null && children.length > 0);
				} catch (ModelException x) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
					// don't log NotExist exceptions as this is a valid case
					// since we might have been posted and the element
					// removed in the meantime.
					if (DLTKUIPlugin.isDebug() || !x.isDoesNotExist()) {
						DLTKUIPlugin.log(x);
					}
				}
			}
			return false;
		}

		/*
		 * @see IContentProvider#inputChanged(Viewer, Object, Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			boolean isCU = (newInput instanceof ISourceModule);

			if (isCU && fListener == null) {
				fListener = new ElementChangedListener();
				DLTKCore.addElementChangedListener(fListener);
			} else if (!isCU && fListener != null) {
				DLTKCore.removeElementChangedListener(fListener);
				fListener = null;
			}
		}

		public boolean isDeleted(Object o) {
			return false;
		}

		protected boolean matches(IModelElement element) {
			if (element.getElementType() == IModelElement.METHOD) {
				String name = element.getElementName();
				return (name != null && name.indexOf('<') >= 0);
			}
			// filter out method arguments and local vars
			if (element.getElementType() == IModelElement.FIELD
					&& element.getParent() instanceof JSSourceMethod) {
				return true;
			}

			// filter out default package
			if (element.getElementType() == IModelElement.PACKAGE_DECLARATION) {
				IPackageDeclaration pkg = (IPackageDeclaration) element;
				return pkg.getElementName().length() == 0;
			}
			return false;
		}
	}

	/**
	 * The element change listener of the java outline viewer.
	 * 
	 * @see IElementChangedListener
	 */
	protected class ElementChangedListener implements IElementChangedListener {

		public void elementChanged(final ElementChangedEvent e) {

			if (getControl() == null) {
				return;
			}

			Display d = getControl().getDisplay();
			if (d != null) {
				d.asyncExec(new Runnable() {
					public void run() {
						ISourceModule cu = (ISourceModule) fInput;
						IModelElement base = cu;
						if (fTopLevelTypeOnly) {
							try {
								base = cu.getTypes().length > 0 ? cu.getTypes()[0]
										: null;
							} catch (ModelException e1) {

							}
							if (base == null) {
								if (fOutlineViewer != null)
									fOutlineViewer.refresh(true);
								return;
							}
						}

						IModelElementDelta delta = findElement(base, e
								.getDelta());
						if (delta != null && fOutlineViewer != null) {
							fOutlineViewer.reconcile(delta);
						}
					}
				});
			}
		}

		protected IModelElementDelta findElement(IModelElement unit,
				IModelElementDelta delta) {

			if (delta == null || unit == null) {
				return null;
			}

			IModelElement element = delta.getElement();

			if (unit.equals(element)) {
				if (isPossibleStructuralChange(delta)) {
					return delta;
				}
				return null;
			}

			if (element.getElementType() > IModelElement.SOURCE_MODULE) {
				return null;
			}

			IModelElementDelta[] children = delta.getAffectedChildren();
			if (children == null || children.length == 0) {
				return null;
			}

			for (int i = 0; i < children.length; i++) {
				IModelElementDelta d = findElement(unit, children[i]);
				if (d != null) {
					return d;
				}
			}

			return null;
		}

		private boolean isPossibleStructuralChange(IModelElementDelta cuDelta) {
			if (cuDelta.getKind() != IModelElementDelta.CHANGED) {
				return true; // add or remove
			}
			int flags = cuDelta.getFlags();
			if ((flags & IModelElementDelta.F_CHILDREN) != 0) {
				return true;
			}
			return (flags & (IModelElementDelta.F_CONTENT | IModelElementDelta.F_FINE_GRAINED)) == IModelElementDelta.F_CONTENT;
		}
	}

	/**
	 * Empty selection provider.
	 * 
	 * @since 3.2
	 */
	private static final class EmptySelectionProvider implements
			ISelectionProvider {
		public void addSelectionChangedListener(
				ISelectionChangedListener listener) {
		}

		public ISelection getSelection() {
			return StructuredSelection.EMPTY;
		}

		public void removeSelectionChangedListener(
				ISelectionChangedListener listener) {
		}

		public void setSelection(ISelection selection) {
		}
	}

	/**
	 * The tree viewer used for displaying the outline.
	 * 
	 * @see TreeViewer
	 */
	class LexicalSortingAction extends Action {

		private ModelElementSorter fComparator = new ModelElementSorter();
		private SourcePositionSorter fSourcePositonComparator = new VjoSourcePositionSorter();

		public LexicalSortingAction() {
			super();
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
			// IJavaHelpContextIds.LEXICAL_SORTING_OUTLINE_ACTION);
			setText(DLTKEditorMessages.ScriptOutlinePage_Sort_label);
			DLTKPluginImages.setLocalImageDescriptors(this,
					"alphab_sort_co.gif"); //$NON-NLS-1$
			setToolTipText(DLTKEditorMessages.ScriptOutlinePage_Sort_tooltip);
			setDescription(DLTKEditorMessages.ScriptOutlinePage_Sort_description);

			boolean checked = fStore
					.getBoolean("LexicalSortingAction.isChecked"); //$NON-NLS-1$
			valueChanged(checked, false);
		}

		@Override
		public void run() {
			valueChanged(isChecked(), true);
		}

		private void valueChanged(final boolean on, boolean store) {
			setChecked(on);
			BusyIndicator.showWhile(fOutlineViewer.getControl().getDisplay(),
					new Runnable() {
						public void run() {
							if (on) {
								fOutlineViewer.setComparator(fComparator);
								fDropSupport.setFeedbackEnabled(false);
							} else {
								fOutlineViewer
										.setComparator(fSourcePositonComparator);
								fDropSupport.setFeedbackEnabled(true);
							}
						}
					});

			if (store) {
				DLTKUIPlugin.getDefault().getPreferenceStore().setValue(
						"LexicalSortingAction.isChecked", on); //$NON-NLS-1$
			}
		}
	}

	private boolean fTopLevelTypeOnly;

	class ClassOnlyAction extends Action {

		public ClassOnlyAction() {
			super();
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
			// IJavaHelpContextIds.GO_INTO_TOP_LEVEL_TYPE_ACTION);
			setText(DLTKEditorMessages.VJOOutlinePage_GoIntoTopLevelType_label);
			setToolTipText(DLTKEditorMessages.VJOOutlinePage_GoIntoTopLevelType_tooltip);
			setDescription(DLTKEditorMessages.VJOOutlinePage_GoIntoTopLevelType_description);
			DLTKPluginImages.setLocalImageDescriptors(this,
					"gointo_toplevel_type.gif"); //$NON-NLS-1$

			IPreferenceStore preferenceStore = DLTKUIPlugin.getDefault()
					.getPreferenceStore();
			boolean showclass = preferenceStore
					.getBoolean("GoIntoTopLevelTypeAction.isChecked"); //$NON-NLS-1$
			setTopLevelTypeOnly(showclass);
		}

		/*
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			setTopLevelTypeOnly(!fTopLevelTypeOnly);
		}

		private void setTopLevelTypeOnly(boolean show) {
			fTopLevelTypeOnly = show;
			setChecked(show);
			fOutlineViewer.refresh(false);

			IPreferenceStore preferenceStore = DLTKUIPlugin.getDefault()
					.getPreferenceStore();
			preferenceStore
					.setValue("GoIntoTopLevelTypeAction.isChecked", show); //$NON-NLS-1$
		}
	}

	static class NoClassElement extends WorkbenchAdapter implements IAdaptable {
		/*
		 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
		 */
		public Object getAdapter(Class clas) {
			if (clas == IWorkbenchAdapter.class) {
				return this;
			}
			return null;
		}

		/*
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return DLTKEditorMessages.ScriptOutlinePage_error_NoTopLevelType;
		}
	}

	/**
	 * This action toggles whether this Java Outline page links its selection to
	 * the active editor.
	 * 
	 * @since 3.0
	 */
	public class ToggleLinkingAction extends AbstractToggleLinkingAction {

		VjoOutlinePage fJavaOutlinePage;

		/**
		 * Constructs a new action.
		 * 
		 * @param outlinePage
		 *            the Java outline page
		 */
		public ToggleLinkingAction(VjoOutlinePage outlinePage) {
			boolean isLinkingEnabled = DLTKUIPlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE);
			setChecked(isLinkingEnabled);
			fJavaOutlinePage = outlinePage;
		}

		/**
		 * Runs the action.
		 */
		@Override
		public void run() {
			DLTKUIPlugin.getDefault().getPreferenceStore().setValue(
					PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE,
					isChecked());
			if (isChecked() && fEditor != null) {
				fEditor.synchronizeOutlinePage(fEditor
						.computeHighlightRangeSourceReference(), false);
			}
		}
	}

	static Object[] NO_CHILDREN = new Object[0];

	private CompositeActionGroup fActionGroups;

	private Hashtable fActions = new Hashtable();
	/**
	 * Custom filter action group.
	 * 
	 * @since 3.0
	 */
	private CustomFiltersActionGroup fCustomFiltersActionGroup;
	private DLTKViewerDropSupport fDropSupport;
	private VjoEditor fEditor;
	/** A flag to show contents of top level type only */
	// private boolean fTopLevelTypeOnly;
	private IModelElement fInput;

	// private MemberFilterActionGroup fMemberFilterActionGroup;
	// private String fContextMenuID;
	private Menu fMenu;
	private ListenerList fPostSelectionChangedListeners = new ListenerList(
			ListenerList.IDENTITY);

	private IPropertyChangeListener fPropertyChangeListener;

	private ListenerList fSelectionChangedListeners = new ListenerList(
			ListenerList.IDENTITY);

	protected IPreferenceStore fStore;

	private ToggleLinkingAction fToggleLinkingAction;
	private TogglePresentationAction fTogglePresentation;

	// /**
	// * Category filter action group.
	// *
	// * @since 3.2
	// */
	// private CategoryFilterActionGroup fCategoryFilterActionGroup;

	public VjoOutlinePage(VjoEditor editor, IPreferenceStore store) {
		super(editor, store);
		Assert.isNotNull(editor);

		// fContextMenuID = "#CompilationUnitOutlinerContext";// contextMenuID;
		fEditor = editor;
		fStore = store;

		fTogglePresentation = new TogglePresentationAction();
		fTogglePresentation.setEditor(editor);

		fPropertyChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				doPropertyChange(event);
			}
		};
		fStore.addPropertyChangeListener(fPropertyChangeListener);
	}

	/*
	 * @see org.eclipse.jface.text.IPostSelectionProvider#addPostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addPostSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (fOutlineViewer != null) {
			fOutlineViewer.addPostSelectionChangedListener(listener);
		} else {
			fPostSelectionChangedListeners.add(listener);
		}
	}

	/*
	 * @see ISelectionProvider#addSelectionChangedListener(ISelectionChangedListener)
	 */
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (fOutlineViewer != null) {
			fOutlineViewer.addSelectionChangedListener(listener);
		} else {
			fSelectionChangedListeners.add(listener);
		}
	}

	@Override
	protected void contextMenuAboutToShow(IMenuManager menu) {

		// DLTKUIPlugin.createStandardGroups(menu);
		if (menu.isEmpty()) {
			// menu.add(new Separator(IContextMenuConstants.GROUP_NEW));
			menu.add(new GroupMarker(IContextMenuConstants.GROUP_GOTO));
			menu.add(new Separator(IContextMenuConstants.GROUP_OPEN));
			menu.add(new GroupMarker(IContextMenuConstants.GROUP_SHOW));
			menu.add(new Separator(ICommonMenuConstants.GROUP_EDIT));
			menu.add(new Separator(IContextMenuConstants.GROUP_REORGANIZE));
			// menu.add(new Separator(IContextMenuConstants.GROUP_GENERATE));
			menu.add(new Separator(IContextMenuConstants.GROUP_SEARCH));
			// menu.add(new Separator(IContextMenuConstants.GROUP_BUILD));
			// menu.add(new Separator(IContextMenuConstants.GROUP_ADDITIONS));
			// menu.add(new
			// Separator(IContextMenuConstants.GROUP_VIEWER_SETUP));
			menu.add(new Separator(IContextMenuConstants.GROUP_PROPERTIES));
		}

		IStructuredSelection selection = (IStructuredSelection) getSelection();
		fActionGroups.setContext(new ActionContext(selection));
		fActionGroups.fillContextMenu(menu);
	}

	/*
	 * @see IPage#createControl
	 */
	@Override
	public void createControl(Composite parent) {

		Tree tree = new Tree(parent, SWT.MULTI);

		AppearanceAwareLabelProvider lprovider = new VjoLabelProvider(
				AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS
						| ScriptElementLabels.F_APP_TYPE_SIGNATURE
						| ScriptElementLabels.ALL_CATEGORY,
				AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS, fStore);

		ILabelDecorator ldecorator = getLabelDecorator();
		if (ldecorator != null) {
			lprovider.addLabelDecorator(ldecorator);
		}

		fOutlineViewer = new ScriptOutlineViewer(tree) {

			@Override
			public void reconcile(IModelElementDelta delta) {

				if (getComparator() == null && fTopLevelTypeOnly) {
					if (delta.getElement() instanceof IType
							&& (delta.getKind() & IModelElementDelta.ADDED) != 0) {
						refresh(true);
						return;

					}

				}

				super.reconcile(delta);
			}

		};
		initDragAndDrop();
		fOutlineViewer.setContentProvider(new ChildrenProvider());
		fOutlineViewer.setLabelProvider(new DecoratingModelLabelProvider(
				lprovider));

		Object[] listeners = fSelectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			fSelectionChangedListeners.remove(listeners[i]);
			fOutlineViewer
					.addSelectionChangedListener((ISelectionChangedListener) listeners[i]);
		}

		listeners = fPostSelectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			fPostSelectionChangedListeners.remove(listeners[i]);
			fOutlineViewer
					.addPostSelectionChangedListener((ISelectionChangedListener) listeners[i]);
		}

		String contextMenuId = DLTKUIPlugin.getPluginId() + ".outline";
		MenuManager manager = new MenuManager(contextMenuId, contextMenuId);
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager m) {
				contextMenuAboutToShow(m);
			}
		});
		fMenu = manager.createContextMenu(tree);
		tree.setMenu(fMenu);

		IPageSite site = getSite();
		site.registerContextMenu(contextMenuId, manager, fOutlineViewer);
		//$NON-NLS-1$

		updateSelectionProvider(site);

		IDLTKLanguageToolkit toolkit = fEditor.getLanguageToolkit();
		// we must create the groups after we have set the selection provider to
		// the site
		fActionGroups = new CompositeActionGroup(
				new ActionGroup[] {
						new OpenViewActionGroup(this),
						new CCPActionGroup(this),
						new GenerateActionGroup(this,
								IContextMenuConstants.GROUP_SHOW),
						// TODO Disable menus
						// new RefactorActionGroup( this),
						new SearchActionGroup(this, toolkit) });

		// register global actions
		IActionBars actionBars = site.getActionBars();
		actionBars.setGlobalActionHandler(ITextEditorActionConstants.UNDO,
				fEditor.getAction(ITextEditorActionConstants.UNDO));
		actionBars.setGlobalActionHandler(ITextEditorActionConstants.REDO,
				fEditor.getAction(ITextEditorActionConstants.REDO));

		IAction action = fEditor.getAction(ITextEditorActionConstants.NEXT);
		actionBars.setGlobalActionHandler(
				ITextEditorActionDefinitionIds.GOTO_NEXT_ANNOTATION, action);
		actionBars.setGlobalActionHandler(ITextEditorActionConstants.NEXT,
				action);
		action = fEditor.getAction(ITextEditorActionConstants.PREVIOUS);
		actionBars
				.setGlobalActionHandler(
						ITextEditorActionDefinitionIds.GOTO_PREVIOUS_ANNOTATION,
						action);
		actionBars.setGlobalActionHandler(ITextEditorActionConstants.PREVIOUS,
				action);

		actionBars
				.setGlobalActionHandler(
						ITextEditorActionDefinitionIds.TOGGLE_SHOW_SELECTED_ELEMENT_ONLY,
						fTogglePresentation);

		fActionGroups.fillActionBars(actionBars);

		IStatusLineManager statusLineManager = actionBars
				.getStatusLineManager();
		if (statusLineManager != null) {
			StatusBarUpdater updater = new StatusBarUpdater(statusLineManager);
			fOutlineViewer.addPostSelectionChangedListener(updater);
		}
		// Custom filter group
		fCustomFiltersActionGroup = new CustomFiltersActionGroup(
				"org.eclipse.dltk.mod.ui.ScriptOutlinePage", fOutlineViewer); //$NON-NLS-1$

		registerToolbarActions(actionBars);

		fOutlineViewer.setInput(fInput);

		// Add by Oliver. 2009-11-02. add the F1 help for vjet outline view.
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IHelpContextIds.OUTLINE_VIEW);
	}

	@Override
	public void dispose() {

		if (fEditor == null) {
			return;
		}

		// if (fMemberFilterActionGroup != null) {
		// fMemberFilterActionGroup.dispose();
		// fMemberFilterActionGroup = null;
		// }

		// if (fCategoryFilterActionGroup != null) {
		// fCategoryFilterActionGroup.dispose();
		// fCategoryFilterActionGroup = null;
		// }

		if (fCustomFiltersActionGroup != null) {
			fCustomFiltersActionGroup.dispose();
			fCustomFiltersActionGroup = null;
		}

		fEditor.outlinePageClosed();
		fEditor = null;

		fSelectionChangedListeners.clear();
		fSelectionChangedListeners = null;

		fPostSelectionChangedListeners.clear();
		fPostSelectionChangedListeners = null;

		if (fPropertyChangeListener != null) {
			fStore.removePropertyChangeListener(fPropertyChangeListener);
			fPropertyChangeListener = null;
		}

		if (fMenu != null && !fMenu.isDisposed()) {
			fMenu.dispose();
			fMenu = null;
		}

		if (fActionGroups != null) {
			fActionGroups.dispose();
		}

		fTogglePresentation.setEditor(null);

		fOutlineViewer = null;

		super.dispose();
	}

	private void doPropertyChange(PropertyChangeEvent event) {
		if (fOutlineViewer != null) {
			if (MembersOrderPreferenceCache.isMemberOrderProperty(event
					.getProperty())) {
				fOutlineViewer.refresh(false);
			}
		}
	}

	@Override
	public IAction getAction(String actionID) {
		Assert.isNotNull(actionID);
		return (IAction) fActions.get(actionID);
	}

	/*
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class key) {
		if (key == IShowInSource.class) {
			return getShowInSource();
		}
		if (key == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { DLTKUIPlugin.ID_SCRIPTEXPLORER };
				}

			};
		}
		if (key == IShowInTarget.class) {
			return getShowInTarget();
		}

		return null;
	}

	@Override
	public Control getControl() {
		if (fOutlineViewer != null) {
			return fOutlineViewer.getControl();
		}
		return null;
	}

	/**
	 * Returns the <code>IShowInSource</code> for this view.
	 * 
	 * @return the {@link IShowInSource}
	 */
	@Override
	protected IShowInSource getShowInSource() {
		return new IShowInSource() {
			public ShowInContext getShowInContext() {
				return new ShowInContext(null, getSite().getSelectionProvider()
						.getSelection());
			}
		};
	}

	/**
	 * Returns the <code>IShowInTarget</code> for this view.
	 * 
	 * @return the {@link IShowInTarget}
	 */
	@Override
	protected IShowInTarget getShowInTarget() {
		return new IShowInTarget() {
			public boolean show(ShowInContext context) {
				ISelection sel = context.getSelection();
				if (sel instanceof ITextSelection) {
					ITextSelection tsel = (ITextSelection) sel;
					int offset = tsel.getOffset();
					IModelElement element = fEditor.getElementAt(offset);
					if (element != null) {
						setSelection(new StructuredSelection(element));
						return true;
					}
				} else if (sel instanceof IStructuredSelection) {
					setSelection(sel);
					return true;
				}
				return false;
			}
		};
	}

	private void initDragAndDrop() {
		fDropSupport = new DLTKViewerDropSupport(fOutlineViewer);
		fDropSupport.start();

		new DLTKViewerDragSupport(fOutlineViewer).start();
	}

	/**
	 * Checks whether a given Java element is an inner type.
	 * 
	 * @param element
	 *            the java element
	 * @return <code>true</code> iff the given element is an inner type
	 */
	private boolean isInnerType(IModelElement element) {

		if (element != null && element.getElementType() == IModelElement.TYPE) {

			IModelElement parent = element.getParent();
			if (parent != null) {
				int parentElementType = parent.getElementType();
				return (parentElementType != IModelElement.SOURCE_MODULE);
			}
		}

		return false;
	}

	@Override
	protected void registerSpecialToolbarActions(IActionBars actionBars) {
		String title, helpContext;
		ArrayList actions = new ArrayList(5);
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		MemberFilterActionGroup fMemberFilterActionGroup = new MemberFilterActionGroup(
				fOutlineViewer, fStore);

		// hide fields
		title = ActionMessages.MemberFilterActionGroup_hide_variables_label;
		// TODO help support
		helpContext = "";// IDLTKHelpContextIds.FILTER_FIELDS_ACTION;
		MemberFilterAction hideVariables = new MemberFilterAction(
				fMemberFilterActionGroup, title, new FieldsFilter(),
				helpContext, true);
		hideVariables
				.setDescription(ActionMessages.MemberFilterActionGroup_hide_variables_description);
		hideVariables
				.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_variables_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hideVariables,
				"fields_co.gif"); //$NON-NLS-1$
		actions.add(hideVariables);

		// Hide Static Fields and Methods
		title = ActionMessages.MemberFilterActionGroup_hide_static_label;
		// TODO help support
		helpContext = "";// IDLTKHelpContextIds.FILTER_STATIC_ACTION;
		MemberFilterAction hideStatic = new MemberFilterAction(
				fMemberFilterActionGroup, title, new StaticsFilter(),
				helpContext, true);

		hideStatic
				.setDescription(ActionMessages.MemberFilterActionGroup_hide_static_description);
		hideStatic
				.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_static_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hideStatic, "static_co.gif"); //$NON-NLS-1$
		actions.add(hideStatic);

		// Hide Non-Public Member

		title = ActionMessages.MemberFilterActionGroup_hide_nonpublic_label;
		// TODO help support
		helpContext = "";// IDLTKHelpContextIds.FILTER_STATIC_ACTION;
		MemberFilterAction hideNonPublic = new MemberFilterAction(
				fMemberFilterActionGroup, title, new NonPublicFilter(),
				helpContext, true);
		hideNonPublic
				.setDescription(ActionMessages.MemberFilterActionGroup_hide_nonpublic_description);
		hideNonPublic
				.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_nonpublic_tooltip);

		DLTKPluginImages.setLocalImageDescriptors(hideNonPublic,
				"public_co.gif"); //$NON-NLS-1$
		actions.add(hideNonPublic);

		// Hide Local Types
		title = ActionMessages.MemberFilterActionGroup_hide_localtypes_label;
		// TODO help support
		helpContext = "";// IDLTKHelpContextIds.FILTER_STATIC_ACTION;
		MemberFilterAction hideLocalType = new MemberFilterAction(
				fMemberFilterActionGroup, title, new LocalTypesFilter(),
				helpContext, true);

		hideLocalType
				.setDescription(ActionMessages.MemberFilterActionGroup_hide_localtypes_description);
		hideLocalType
				.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_localtypes_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hideLocalType,
				"localtypes_co.gif"); //$NON-NLS-1$
		actions.add(hideLocalType);

		// order corresponds to order in toolbar
		MemberFilterAction[] fFilterActions = (MemberFilterAction[]) actions
				.toArray(new MemberFilterAction[actions.size()]);
		fMemberFilterActionGroup.setActions(fFilterActions);
		fMemberFilterActionGroup.contributeToToolBar(toolBarManager);
	}

	private void registerToolbarActions(IActionBars actionBars) {
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		toolBarManager.add(new LexicalSortingAction());

		// fMemberFilterActionGroup = new
		// MemberFilterActionGroup(fOutlineViewer,
		// fStore);
		// fMemberFilterActionGroup.contributeToToolBar(toolBarManager);

		registerSpecialToolbarActions(actionBars);

		fCustomFiltersActionGroup.fillActionBars(actionBars);

		IMenuManager viewMenuManager = actionBars.getMenuManager();
		viewMenuManager.add(new Separator("EndFilterGroup")); //$NON-NLS-1$

		fToggleLinkingAction = new ToggleLinkingAction(this);
		viewMenuManager.add(new ClassOnlyAction());
		viewMenuManager.add(fToggleLinkingAction);

		// fCategoryFilterActionGroup = new CategoryFilterActionGroup(
		// fOutlineViewer,
		// "org.eclipse.jdt.ui.JavaOutlinePage", new IModelElement[] { fInput
		// }); //$NON-NLS-1$
		// fCategoryFilterActionGroup.contributeToViewMenu(viewMenuManager);
	}

	/*
	 * @see org.eclipse.jface.text.IPostSelectionProvider#removePostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void removePostSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (fOutlineViewer != null) {
			fOutlineViewer.removePostSelectionChangedListener(listener);
		} else {
			fPostSelectionChangedListeners.remove(listener);
		}
	}

	/*
	 * @see ISelectionProvider#removeSelectionChangedListener(ISelectionChangedListener)
	 */
	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (fOutlineViewer != null) {
			fOutlineViewer.removeSelectionChangedListener(listener);
		} else {
			fSelectionChangedListeners.remove(listener);
		}
	}

	@Override
	public void select(ISourceReference reference) {
		if (fOutlineViewer != null) {

			ISelection s = fOutlineViewer.getSelection();
			if (s instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) s;
				List elements = ss.toList();
				if (!elements.contains(reference)) {
					s = (reference == null ? StructuredSelection.EMPTY
							: new StructuredSelection(reference));
					fOutlineViewer.setSelection(s, true);
				}
			}
		}
	}

	@Override
	public void setAction(String actionID, IAction action) {
		Assert.isNotNull(actionID);
		if (action == null) {
			fActions.remove(actionID);
		} else {
			fActions.put(actionID, action);
		}
	}

	@Override
	public void setInput(IModelElement inputElement) {
		fInput = inputElement;
		if (fOutlineViewer != null) {
			fOutlineViewer.setInput(fInput);
			updateSelectionProvider(getSite());
		}
		// if (fCategoryFilterActionGroup != null)
		// fCategoryFilterActionGroup.setInput(new IModelElement[] { fInput });
	}

	/*
	 * @since 3.2
	 */
	private void updateSelectionProvider(IPageSite site) {
		ISelectionProvider provider = fOutlineViewer;
		if (fInput != null) {
			ISourceModule cu = (ISourceModule) fInput
					.getAncestor(IModelElement.SOURCE_MODULE);
			if (cu != null && !ScriptModelUtil.isPrimary(cu)) {
				provider = new EmptySelectionProvider();
			}
		}
		site.setSelectionProvider(provider);
	}
}
