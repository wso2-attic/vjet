/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.pref;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelElementVisitor;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IScriptModel;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.debug.ui.IDLTKDebugUIPreferenceConstants;
import org.eclipse.dltk.mod.debug.ui.preferences.Filter;
import org.eclipse.dltk.mod.debug.ui.preferences.FilterLabelProvider;
import org.eclipse.dltk.mod.debug.ui.preferences.FilterViewerComparator;
import org.eclipse.dltk.mod.debug.ui.preferences.ScriptDebugPreferencesMessages;
import org.eclipse.dltk.mod.debug.ui.preferences.dialogs.CreateStepFilterDialog;
import org.eclipse.dltk.mod.internal.debug.ui.ScriptDebugOptionsManager;
import org.eclipse.dltk.mod.ui.DLTKExecuteExtensionHelper;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.ModelElementLabelProvider;
import org.eclipse.dltk.mod.ui.util.SWTFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.ui.VjetSmartStepEvaluator;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoTypeSelectionDialog;

/**
 * The preference page for Java step filtering, located at the node Java > Debug
 * > Step Filtering
 */
public class VjetStepFilterPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage, IExecutableExtension {

	public static final String	PAGE_ID	= "org.ebayopensource.vjet.eclipse.preferences.debug.stepfiltering";	//$NON-NLS-1$

	/**
	 * Content provider for the table. Content consists of instances of
	 * StepFilter.
	 * 
	 */
	class StepFilterContentProvider implements IStructuredContentProvider {
		public StepFilterContentProvider() {
			initTableState(false);
		}

		public Object[] getElements(Object inputElement) {
			return getAllFiltersFromTable();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	}

	// widgets
	private CheckboxTableViewer		m_tableViewer;
	private Button					m_useStepFiltersButton;
	// private Button m_addPackageButton;
	// private Button m_addTypeButton;
	private Button					m_removeFilterButton;
	// private Button fAddFilterButton;
	private Button					m_selectAllButton;
	private Button					m_deselectAllButton;
	private IDLTKLanguageToolkit	m_toolkit;
	// private Button m_filterDBGPFileButton;
	// private Button m_filterVjoBootstrapButton;
	private Button					m_addFilterButton;

	/**
	 * Constructor
	 */
	public VjetStepFilterPreferencePage() {
		super();
		setTitle(ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_title);
		setDescription(ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		// The main composite
		Composite composite = SWTFactory.createComposite(parent, parent
				.getFont(), 1, 1, GridData.FILL_BOTH, 0, 0);
		createStepFilterPreferences(composite);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * handles the filter button being clicked
	 * 
	 * @param event
	 *            the clicked event
	 */
	private void handleFilterViewerKeyPress(KeyEvent event) {
		if (event.character == SWT.DEL && event.stateMask == 0) {
			removeFilters();
		}
	}

	/**
	 * Create a group to contain the step filter related widgetry
	 */
	private void createStepFilterPreferences(Composite parent) {
		Composite container = SWTFactory.createComposite(parent, parent
				.getFont(), 2, 1, GridData.FILL_BOTH, 0, 0);
		m_useStepFiltersButton = SWTFactory
				.createCheckButton(
						container,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage__Use_step_filters,
						null, DebugUITools.isUseStepFilters(), 2);
		m_useStepFiltersButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				setPageEnablement(m_useStepFiltersButton.getSelection());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		SWTFactory
				.createLabel(
						container,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Defined_step_fi_lters__8,
						2);
		m_tableViewer = CheckboxTableViewer.newCheckList(container,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI
						| SWT.FULL_SELECTION);
		m_tableViewer.getTable().setFont(container.getFont());
		m_tableViewer.setLabelProvider(new FilterLabelProvider() {
			@Override
			public Image getColumnImage(Object object, int column) {
				return null;
			}
		});
		m_tableViewer.setComparator(new FilterViewerComparator());
		m_tableViewer.setContentProvider(new StepFilterContentProvider());
		m_tableViewer.setInput(getAllStoredFilters(false));
		m_tableViewer.getTable()
				.setLayoutData(new GridData(GridData.FILL_BOTH));
		m_tableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				((Filter) event.getElement()).setChecked(event.getChecked());
			}
		});
		m_tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (selection.isEmpty()) {
							m_removeFilterButton.setEnabled(false);
						} else {
							m_removeFilterButton.setEnabled(true);
						}
					}
				});
		m_tableViewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				handleFilterViewerKeyPress(event);
			}
		});

		createStepFilterButtons(container);
		createStepFilterCheckboxes(container);

		setPageEnablement(m_useStepFiltersButton.getSelection());
	}

	/**
	 * initializes the checked state of the filters when the dialog opens
	 * 
	 * @since 3.2
	 */
	private void initTableState(boolean defaults) {
		Filter[] filters = getAllStoredFilters(defaults);
		for (int i = 0; i < filters.length; i++) {
			m_tableViewer.add(filters[i]);
			m_tableViewer.setChecked(filters[i], filters[i].isChecked());
		}
	}

	/**
	 * Enables or disables the widgets on the page, with the exception of
	 * <code>fUseStepFiltersButton</code> according to the passed boolean
	 * 
	 * @param enabled
	 *            the new enablement status of the page's widgets
	 * @since 3.2
	 */
	protected void setPageEnablement(boolean enabled) {
		m_addFilterButton.setEnabled(enabled);
		// m_addPackageButton.setEnabled(enabled);
		// m_addTypeButton.setEnabled(enabled);
		// m_filterDBGPFileButton.setEnabled(enabled);
		// m_filterVjoBootstrapButton.setEnabled(enabled);
		m_deselectAllButton.setEnabled(enabled);
		m_selectAllButton.setEnabled(enabled);
		m_tableViewer.getTable().setEnabled(enabled);
		m_removeFilterButton.setEnabled(enabled
				& !m_tableViewer.getSelection().isEmpty());
	}

	/**
	 * create the checked preferences for the page
	 * 
	 * @param container
	 *            the parent container
	 */
	private void createStepFilterCheckboxes(Composite container) {
		// m_filterDBGPFileButton = SWTFactory.createCheckButton(container,
		// "filter DBGP files", null, getPreferenceStore().getBoolean(
		// VjetDebugPreferenceConstants.PREF_FILTER_DBGP_FILE), 2);
		// m_filterVjoBootstrapButton = SWTFactory
		// .createCheckButton(
		// container,
		// "filter VjoBootstrap files",
		// null,
		// getPreferenceStore()
		// .getBoolean(
		// VjetDebugPreferenceConstants.PREF_FILTER_VJOBOOTSTRAP_FILE),
		// 2);
	}

	/**
	 * Creates the button for the step filter options
	 * 
	 * @param container
	 *            the parent container
	 */
	private void createStepFilterButtons(Composite container) {
		initializeDialogUnits(container);
		// button container
		Composite buttonContainer = new Composite(container, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		buttonContainer.setLayoutData(gd);
		GridLayout buttonLayout = new GridLayout();
		buttonLayout.numColumns = 1;
		buttonLayout.marginHeight = 0;
		buttonLayout.marginWidth = 0;
		buttonContainer.setLayout(buttonLayout);
		// Add filter button
		m_addFilterButton = SWTFactory
				.createPushButton(
						buttonContainer,
						VjetDebugPrefMessages.VjetStepFilterPreferencePage_addFilterButton_text,
						VjetDebugPrefMessages.VjetStepFilterPreferencePage_addFilterButton_description,
						null);
		m_addFilterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				addNewFilter();
			}
		});
		// TODO support vjo types, packages
		// // Add type button
		// m_addTypeButton = SWTFactory
		// .createPushButton(
		// buttonContainer,
		// ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Add__Type____11,
		// ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Choose_a_Java_type_and_add_it_to_step_filters_12,
		// null);
		// m_addTypeButton.addListener(SWT.Selection, new Listener() {
		// public void handleEvent(Event e) {
		// addType();
		// }
		// });
		// // Add package button
		// m_addPackageButton = SWTFactory
		// .createPushButton(
		// buttonContainer,
		// "Add package",
		// ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Choose_a_package_and_add_it_to_step_filters_14,
		// null);
		// m_addPackageButton.addListener(SWT.Selection, new Listener() {
		// public void handleEvent(Event e) {
		// addPackage();
		// }
		// });
		// Remove button
		m_removeFilterButton = SWTFactory
				.createPushButton(
						buttonContainer,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage__Remove_15,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Remove_all_selected_step_filters_16,
						null);
		m_removeFilterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				removeFilters();
			}
		});
		m_removeFilterButton.setEnabled(false);

		Label separator = new Label(buttonContainer, SWT.NONE);
		separator.setVisible(false);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);
		// Select All button
		m_selectAllButton = SWTFactory
				.createPushButton(
						buttonContainer,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage__Select_All_1,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Selects_all_step_filters_2,
						null);
		m_selectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				m_tableViewer.setAllChecked(true);
			}
		});
		// De-Select All button
		m_deselectAllButton = SWTFactory
				.createPushButton(
						buttonContainer,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Deselect_All_3,
						ScriptDebugPreferencesMessages.ScriptStepFilterPreferencePage_Deselects_all_step_filters_4,
						null);
		m_deselectAllButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				m_tableViewer.setAllChecked(false);
			}
		});

	}

	protected void addNewFilter() {
		Filter newFilter = CreateStepFilterDialog.showCreateStepFilterDialog(
				getShell(), getAllFiltersFromTable());
		addFilter(newFilter);
	}

	protected void addPackage() {
		IScriptFolder selectedPackage = selectPackage();
		if (selectedPackage == null) {
			return;
		}
		String filterString = getPackageFilterString(selectedPackage);
		addFilter(filterString, true, Flags.AccNameSpace);
	}

	private String getPackageFilterString(IScriptFolder pack) {
		return pack.getPath().removeFirstSegments(
				getProjectFragement(pack).getPath().segmentCount())
				.toPortableString().replace(IPath.SEPARATOR, '.')
				+ ".*";//$NON-NLS-1$
	}

	private IProjectFragment getProjectFragement(IScriptFolder pack) {
		IModelElement e;
		do {
			e = pack.getParent();
		} while (IModelElement.PROJECT_FRAGMENT != e.getElementType());
		return (IProjectFragment) e;
	}

	protected IScriptFolder selectPackage() {

		IScriptFolder[] packages = getAllPacakges();
		if (packages == null) {
			packages = new IScriptFolder[0];
		}
		ILabelProvider labelProvider = new ModelElementLabelProvider(
				ModelElementLabelProvider.SHOW_DEFAULT);
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		dialog.setIgnoreCase(false);
		dialog.setTitle("Package Selection"); //$NON-NLS-1$
		dialog.setMessage("&Choose a folder:"); //$NON-NLS-1$
		dialog.setElements(packages);
		dialog.setHelpAvailable(false);

		if (dialog.open() == Window.OK) {
			return (IScriptFolder) dialog.getFirstResult();
		}
		return null;

	}

	/**
	 * add a new type to the listing of available filters
	 */
	protected void addType() {
		IType type = selectType();
		if (type == null) {
			return;
		}
		try {
			addFilter(type.getTypeQualifiedName("."), true, type //$NON-NLS-1$
					.getFlags());
		} catch (ModelException e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}
	}

	protected IType selectType() {
		VjoTypeSelectionDialog dialog = new VjoTypeSelectionDialog(getShell(),
				true, PlatformUI.getWorkbench().getProgressService(),
				getSearchScope(), IDLTKSearchConstants.TYPE, this
						.getUILanguageToolkit());

		dialog.setTitle(getTypeDialogTitle());
		dialog.setMessage(getTypeDialogMessage());

		int result = dialog.open();
		if (result != IDialogConstants.OK_ID)
			return null;

		Object[] types = dialog.getResult();
		if (types != null && types.length > 0) {
			IType type = null;
			type = (IType) types[0];
			return type;
		}

		return null;
	}

	protected IDLTKSearchScope getSearchScope() {
		return null;
	}

	protected String getTypeDialogTitle() {
		return VjetDebugPrefMessages.VjetStepFilterPreferencePage_typeDialog_title;
	}

	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return DLTKUILanguageManager
				.getLanguageToolkit(m_toolkit.getNatureId());
	}

	protected String getTypeDialogMessage() {
		return VjetDebugPrefMessages.VjetStepFilterPreferencePage_typeDialog_message;
	}

	/**
	 * add a new package to the list of all available package filters
	 * 
	 * @return
	 */
	private IScriptFolder[] getAllPacakges() {
		IScriptModel model = DLTKCore.create(ResourcesPlugin.getWorkspace()
				.getRoot());
		final List<IScriptFolder> folderList = new ArrayList<IScriptFolder>();
		try {
			model.accept(new IModelElementVisitor() {
				List<String>	nameList	= new ArrayList<String>();

				public boolean visit(IModelElement element) {
					if (element.getElementType() == IModelElement.SCRIPT_PROJECT) {
						IDLTKLanguageToolkit languageToolkit;
						languageToolkit = DLTKLanguageManager
								.getLanguageToolkit(element);
						if (!m_toolkit.getNatureId().equals(
								languageToolkit.getNatureId())) {
							return false;
						}
					}
					if (element.getElementType() == IModelElement.SCRIPT_FOLDER) {
						if (!folderList.contains(element)
								&& !nameList.contains(element.getElementName())) {
							folderList.add((IScriptFolder) element);
							nameList.add(element.getElementName());
						}
					}
					if (element.getElementType() == IModelElement.TYPE) {
						return false;
					}
					return true;
				}
			});
		} catch (ModelException e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}

		return folderList.toArray(new IScriptFolder[0]);
	}

	/**
	 * Removes the currently selected filters.
	 */
	protected void removeFilters() {
		m_tableViewer.remove(((IStructuredSelection) m_tableViewer
				.getSelection()).toArray());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		DebugUITools.setUseStepFilters(m_useStepFiltersButton.getSelection());
		IPreferenceStore store = getPreferenceStore();
		List<String> active = new ArrayList<String>();
		List<String> inactive = new ArrayList<String>();
		String name = ""; //$NON-NLS-1$
		Filter[] filters = getAllFiltersFromTable();
		for (int i = 0; i < filters.length; i++) {
			name = filters[i].getName();
			String modifiers = VjetSmartStepEvaluator.MODIFIER_SPLITTER
					+ Integer.toString(filters[i].getModifiers());
			if (filters[i].isChecked()) {
				active.add(name + modifiers);
			} else {
				inactive.add(name + modifiers);
			}
		}
		String pref = ScriptDebugOptionsManager.serializeList((String[]) active
				.toArray(new String[active.size()]));
		store.setValue(
				IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST, pref);
		pref = ScriptDebugOptionsManager.serializeList((String[]) inactive
				.toArray(new String[inactive.size()]));
		store.setValue(
				IDLTKDebugUIPreferenceConstants.PREF_INACTIVE_FILTERS_LIST,
				pref);

		// store.setValue(VjetDebugPreferenceConstants.PREF_FILTER_DBGP_FILE,
		// m_filterDBGPFileButton.getSelection());
		// store.setValue(
		// VjetDebugPreferenceConstants.PREF_FILTER_VJOBOOTSTRAP_FILE,
		// m_filterVjoBootstrapButton.getSelection());
		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		boolean stepenabled = DebugUITools.isUseStepFilters();
		m_useStepFiltersButton.setSelection(stepenabled);
		setPageEnablement(stepenabled);
		m_tableViewer.getTable().removeAll();
		initTableState(true);

		// m_filterDBGPFileButton.setSelection(getPreferenceStore()
		// .getDefaultBoolean(
		// VjetDebugPreferenceConstants.PREF_FILTER_DBGP_FILE));
		// m_filterVjoBootstrapButton
		// .setSelection(getPreferenceStore()
		// .getDefaultBoolean(
		// VjetDebugPreferenceConstants.PREF_FILTER_VJOBOOTSTRAP_FILE));
		super.performDefaults();
	}

	/**
	 * adds a single filter to the viewer
	 * 
	 * @param filter
	 *            the new filter to add
	 * @param checked
	 *            the checked state of the new filter
	 * @since 3.2
	 */
	protected void addFilter(String filter, boolean checked, int modifiers) {
		if (filter != null) {
			Filter f = new Filter(filter, checked, modifiers);
			addFilter(f);
		}
	}

	protected void addFilter(Filter filter) {
		if (filter != null) {
			Filter[] allFiltersFromTable = getAllFiltersFromTable();
			for (int i = 0; i < allFiltersFromTable.length; i++) {
				if (filter.equals(allFiltersFromTable[i])) {
					return;
				}
			}
			m_tableViewer.add(filter);
			m_tableViewer.setChecked(filter, filter.isChecked());
		}
	}

	/**
	 * returns all of the filters from the table, this includes ones that have
	 * not yet been saved
	 * 
	 * @return a possibly empty lits of filters fron the table
	 * @since 3.2
	 */
	protected Filter[] getAllFiltersFromTable() {
		TableItem[] items = m_tableViewer.getTable().getItems();
		Filter[] filters = new Filter[items.length];
		for (int i = 0; i < items.length; i++) {
			filters[i] = (Filter) items[i].getData();
			filters[i].setChecked(items[i].getChecked());
		}
		return filters;
	}

	/**
	 * Returns all of the committed filters
	 * 
	 * @return an array of committed filters
	 * @since 3.2
	 */
	protected Filter[] getAllStoredFilters(boolean defaults) {
		Filter[] filters = null;
		String[] activefilters, inactivefilters;
		IPreferenceStore store = getPreferenceStore();
		if (defaults) {
			activefilters = ScriptDebugOptionsManager
					.parseList(store
							.getDefaultString(IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST));
			inactivefilters = ScriptDebugOptionsManager
					.parseList(store
							.getDefaultString(IDLTKDebugUIPreferenceConstants.PREF_INACTIVE_FILTERS_LIST));
		} else {
			activefilters = ScriptDebugOptionsManager
					.parseList(store
							.getString(IDLTKDebugUIPreferenceConstants.PREF_ACTIVE_FILTERS_LIST));
			inactivefilters = ScriptDebugOptionsManager
					.parseList(store
							.getString(IDLTKDebugUIPreferenceConstants.PREF_INACTIVE_FILTERS_LIST));
		}
		filters = new Filter[activefilters.length + inactivefilters.length];
		for (int i = 0; i < activefilters.length; i++) {
			String[] split = activefilters[i]
					.split(VjetSmartStepEvaluator.MODIFIER_SPLITTER); //$NON-NLS-1$
			if (split.length == 1) {
				filters[i] = new Filter(split[0], true, 0);
			} else {
				filters[i] = new Filter(split[0], true, (new Integer(split[1]))
						.intValue());
			}
		}
		for (int i = 0; i < inactivefilters.length; i++) {
			String[] split = inactivefilters[i]
					.split(VjetSmartStepEvaluator.MODIFIER_SPLITTER); //$NON-NLS-1$
			if (split.length == 1) {
				filters[i + activefilters.length] = new Filter(split[0], false,
						0);
			} else {
				filters[i + activefilters.length] = new Filter(split[0], false,
						(new Integer(split[1])).intValue());
			}
		}
		return filters;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		m_toolkit = DLTKExecuteExtensionHelper.getLanguageToolkit(config,
				propertyName, data);
		setPreferenceStore(VjetDebugUIPlugin.getDefault().getPreferenceStore());
	}
}
