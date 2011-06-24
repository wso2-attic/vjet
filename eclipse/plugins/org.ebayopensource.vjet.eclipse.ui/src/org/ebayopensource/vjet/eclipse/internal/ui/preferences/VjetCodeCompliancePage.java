/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.vjet.eclipse.codeassist.compliance.PredefinedBrowsersPreferenceKeys;
import org.ebayopensource.vjet.eclipse.codeassist.compliance.PredefinedBrowsersPreferenceKeys.IBrowserKey;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.ui.PreferencesAdapter;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.util.SWTFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * This class is representation of the code compliance preferences page of the VJET
 * 
 * 
 *
 */
public class VjetCodeCompliancePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static String PROPERTY_PAGE_ID = "org.ebayopensource.vjet.eclipse.propertyPage.codeCompliance";
	
	private static String PREFERENCE_PAGE_ID = "org.ebayopensource.vjet.eclipse.preferencePage.codeCompliance";

	private static final List<BrowserCompliancePreferenceKey> PREF_KEYS = new ArrayList<BrowserCompliancePreferenceKey>();

	static {
		initialize();
	}

	private static void initialize() {

		IBrowserKey[] predefLKeys = PredefinedBrowsersPreferenceKeys
				.getPredefinedKeys();

		for (int iter = 0; iter < predefLKeys.length; iter++) {
			PREF_KEYS.add(new BrowserCompliancePreferenceKey(predefLKeys[iter]
					.getBrowser(), predefLKeys[iter].getVersions(),
					predefLKeys[iter].isTargetedBrowserPredefKey(),
					predefLKeys[iter].takeTargetedBrowserVerPredefKey()));
		}

	}

	private static class BrowserCompliancePreferenceKey {

		private static Map<String, Integer> map = new HashMap<String, Integer>();
		private static int id = 0;

		private String browser;
		private String[] versions;
		private PreferenceKey isTargetedBrowserPredefKey;
		private PreferenceKey takeTargetedBrowserVerPredefKey;

		BrowserCompliancePreferenceKey(String browser, String[] versions,
				String isTargetedBrowserPredefKey,
				String takeTargetedBrowserVerPredefKey) {
			this.browser = browser;
			this.versions = versions;
			this.isTargetedBrowserPredefKey = new PreferenceKey(
					VjetUIPlugin.PLUGIN_ID, isTargetedBrowserPredefKey);
			this.takeTargetedBrowserVerPredefKey = new PreferenceKey(
					VjetUIPlugin.PLUGIN_ID, takeTargetedBrowserVerPredefKey);
		}

	}

	private static PreferenceKey[] toPreferenceKeyArray() {

		PreferenceKey[] array = new PreferenceKey[PREF_KEYS.size() * 2];

		int a = 0;
		for (int iter = 0; iter < PREF_KEYS.size(); iter++) {
			array[a++] = PREF_KEYS.get(iter).isTargetedBrowserPredefKey;
			array[a++] = PREF_KEYS.get(iter).takeTargetedBrowserVerPredefKey;
		}

		return array;
	}
	
	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener changeListener, IProject project,
			IWorkbenchPreferenceContainer container) {

		return new AbstractOptionsBlock(changeListener, project,
				toPreferenceKeyArray(), container) {

			private List<Combo> combos = new ArrayList<Combo>();

			@Override
			protected Control createOptionsBlock(Composite parent) {

				Composite composite = SWTFactory.createComposite(parent, parent
						.getFont(), 1, 1, GridData.FILL_HORIZONTAL);

				final Group allBrowsersGroup = SWTFactory
						.createGroup(
								composite,
								VjetPreferenceMessages.VjetBrowsersCompliance_group_name,
								2, 1, GridData.FILL_HORIZONTAL);

				// create all ui elements and bind all preference keys
				bindAll(allBrowsersGroup);

				return composite;
			}

			private void bindAll(Composite parent) {

				Iterator<BrowserCompliancePreferenceKey> iterator = PREF_KEYS
						.iterator();

				while (iterator.hasNext()) {

					BrowserCompliancePreferenceKey key = iterator.next();

					boolean alreadyTargeted = getBooleanValue(key.isTargetedBrowserPredefKey);

					Button checkBrowser = SWTFactory.createCheckButton(parent,
							key.browser);
					bindControl(checkBrowser, key.isTargetedBrowserPredefKey,
							null);
					// Add by Oliver.2009-07-28. Because it's not supported with VJET V1.0
					checkBrowser.setEnabled(false);

					Combo comboVersions = SWTFactory.createCombo(parent,
							SWT.DROP_DOWN, 1, 1, key.versions);

					combos.add(comboVersions);

					bindControl(comboVersions,
							key.takeTargetedBrowserVerPredefKey);

					comboVersions
							.setEnabled(getBooleanValue(key.isTargetedBrowserPredefKey));

					checkBrowser.addSelectionListener(new CheckBrowserListener(
							key, checkBrowser, comboVersions));

				}

			}

			class CheckBrowserListener implements SelectionListener {

				private BrowserCompliancePreferenceKey key;
				private Button checkBrowserBtn;
				private Combo selectVersCombo;

				CheckBrowserListener(BrowserCompliancePreferenceKey key,
						Button checkBrowserBtn, Combo selectVersCombo) {

					this.key = key;
					this.checkBrowserBtn = checkBrowserBtn;
					this.selectVersCombo = selectVersCombo;

				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// do nothing
				}

				public void widgetSelected(SelectionEvent se) {

					boolean isSelected = checkBrowserBtn.getSelection();
					selectVersCombo.setEnabled(isSelected);

					if (isSelected
							&& getValue(key.takeTargetedBrowserVerPredefKey) == null) {
						setValue(key.takeTargetedBrowserVerPredefKey,
								selectVersCombo.getItem(0));
					}

				}

			}

			public void performDefaults() {
				super.performDefaults();
				for (Combo combo : combos) {
					combo.setEnabled(false);
				}
			}

		};
	}

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {

	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(VjetUIPlugin.getDefault()
				.getPluginPreferences()));

	}

	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}

}
