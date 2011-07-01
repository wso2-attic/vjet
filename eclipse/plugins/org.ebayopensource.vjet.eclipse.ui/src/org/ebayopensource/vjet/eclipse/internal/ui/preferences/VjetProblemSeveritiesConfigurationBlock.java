/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoGroupRulesCache;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticRulePolicy;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.validation.DefaultValidator;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.mod.ui.preferences.PreferenceKey;
import org.eclipse.dltk.mod.ui.util.IStatusChangeListener;
import org.eclipse.dltk.mod.ui.util.PixelConverter;
import org.eclipse.jdt.internal.ui.preferences.ScrolledPageContent;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class VjetProblemSeveritiesConfigurationBlock extends
        AbstractOptionsBlock {

    public final static String VJETVALIDATION = VjetPlugin.VJETVALIDATION;

	private static final String ERROR = "error";

	private static final String IGNORE = "ignore";

	private static final String WARNING = "warning";
	
	private static final String DEFAULT = "default";

    private PixelConverter fPixelConverter;

    String[] errorWarningIgnoreLabels = new String[] {
            VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_error,
            VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_warning,
            VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_ignore,
            "Default"};

    private HashMap<PreferenceKey, Combo> comboPool = new HashMap<PreferenceKey, Combo>();

    private Button validationCheckbox;

    private static VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo
            .getInstance();

    public VjetProblemSeveritiesConfigurationBlock(
            IStatusChangeListener context, IProject project,
            IWorkbenchPreferenceContainer container) {
        super(context, project, getKeys(), container);
    }

    private static PreferenceKey[] getKeys() {
        // Add by Eric.ma for adding "Enable VJET validation" function on
        // 2009.09.17
        VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
        String keyName = ""; //$NON-NLS-1$
        List<PreferenceKey> list = new ArrayList<PreferenceKey>();
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
        	keyName = ruleSet.getRuleSetName(); //$NON-NLS-1$
        	list.add(new PreferenceKey(VjetPlugin.PLUGIN_ID, keyName));
//            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
//            }
        }
        list.add(new PreferenceKey(VjetPlugin.PLUGIN_ID, VJETVALIDATION));
        PreferenceKey[] keys = new PreferenceKey[list.size()];
        return list.toArray(keys);
        // End of adding
    }

    protected static PreferenceKey getKey(String keyName) {
        for (PreferenceKey key : getKeys()) {
            if (key.getName().equals(keyName)) {
                return key;
            }
        }
        return null;
    }

    protected final static PreferenceKey getVJETCoreKey(String key) {
        return getKey(VjetUIPlugin.PLUGIN_ID, key);
    }

    private Composite createStyleTabContent(Composite folder) {
        String[] errorWarningIgnore = new String[] { ERROR,
                WARNING, IGNORE };
//        String[] errorWarningIgnoreLabels = new String[] {
//                VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_error,
//                VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_warning,
//                VjetPreferenceMessages.ProblemSeveritiesConfigurationBlock_ignore };

        int nColumns = 3;
        final Composite buttonComposite = new Composite(folder, SWT.NONE);
        GridLayout layout1 = new GridLayout();
        layout1.numColumns = 2;
        layout1.marginHeight = 0;
        layout1.marginWidth = 0;
        layout1.horizontalSpacing = 5;
        layout1.makeColumnsEqualWidth = false;
        buttonComposite.setLayout(layout1);
        Button exportButton = new Button(buttonComposite, SWT.PUSH);
        Dialog.applyDialogFont(exportButton);
        Button importButton = new Button(buttonComposite, SWT.PUSH);
        Dialog.applyDialogFont(importButton);
        exportButton.setText("&Export Policy"); //$NON-NLS-1$
        importButton.setText("&Import Policy"); //$NON-NLS-1$
        importButton.setVisible(false);
        exportButton.setVisible(false);
        
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        data.widthHint = 20;
        data.heightHint = 12;
        IProject project = getProject();
        if (project == null) {
            exportButton.setToolTipText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_4);
            importButton.setToolTipText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_5);
        } else {
            exportButton.setToolTipText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_6 + project.getName()
                    + VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_7);
            importButton
                    .setToolTipText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_8
                            + project.getName() + "\""); //$NON-NLS-1$
        }
        exportButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                exportValidationPolicy(buttonComposite);
            }

        });

        importButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                importValidationPolicy(buttonComposite);
            }
        });

        final ScrolledPageContent sc1 = new ScrolledPageContent(folder);

        Composite composite = sc1.getBody();
        GridLayout layout = new GridLayout(nColumns, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
//        composite.setVisible(false);
        
        Label description = new Label(composite, SWT.LEFT | SWT.WRAP);
        description.setFont(description.getFont());
        description
                .setText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_10);
        description.setLayoutData(new GridData(GridData.BEGINNING,
                GridData.CENTER, true, false, nColumns - 1, 1));

        int indentStep = fPixelConverter.convertWidthInCharsToPixels(1);

        int defaultIndent = indentStep * 0;
        int extraIndent = indentStep * 2;

        String label;
//        ExpandableComposite excomposite;
        Composite inner;

        // --- style
        // Add by Eric.ma for adding "Enable VJET validation" function on
        // 2009.09.17
        if (validationCheckbox != null) {
            bindControl(validationCheckbox, getKey(VJETVALIDATION), null);
        }
        // End of added
        String keyName = VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_11;
        PreferenceKey key = null;
        comboPool.clear();
        
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
            label = ruleSet.getRuleSetDescription();
//            excomposite = createStyleSection(composite, label, nColumns);
      
            
//            inner = new Composite(composite, SWT.NONE);
//            inner.setFont(composite.getFont());
//            inner.setLayout(new GridLayout(nColumns, false));
//            excomposite.setClient(inner);
            
//            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
//                label = rule.getRuleDescription();
             label = ruleSet.getRuleSetDescription();
            	keyName = ruleSet.getRuleSetName();// + VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_12 + rule.getRuleName();
                key = getKey(keyName);
                if (key != null) {
                    String value = getValue(key);
                    if (value == null) {
                    	// TODO come back here
                        setValue(key, getSeverity(ruleSet));
                    }
                    Combo combo = addComboBox(composite, label, key,
                            errorWarningIgnore, errorWarningIgnoreLabels,
                            defaultIndent);
                   
                    bindControl(combo, key);
                    comboPool.put(key, combo);
                }
//            }
        }
        return sc1;
    }

    protected Combo addComboBox(Composite parent, String label,
            PreferenceKey key, String[] values, String[] valueLabels, int indent) {
        GridData gd = new GridData(GridData.FILL, GridData.CENTER, true, false,
                2, 1);
        gd.horizontalIndent = indent;

        Label labelControl = new Label(parent, SWT.LEFT);
        labelControl.setFont(JFaceResources.getDialogFont());
        labelControl.setText(label);
        labelControl.setLayoutData(gd);

        Combo comboBox = newComboControl(parent, key, values, valueLabels);
        comboBox.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

        return comboBox;
    }

    protected Combo newComboControl(Composite composite, PreferenceKey key,
            String[] values, String[] valueLabels) {
        ControlData data = new ControlData(key, values);

        Combo comboBox = new Combo(composite, SWT.READ_ONLY);
        comboBox.setItems(valueLabels);
        comboBox.setData(data);
        // comboBox.addSelectionListener(getSelectionListener());
        comboBox.setFont(JFaceResources.getDialogFont());

        makeScrollableCompositeAware(comboBox);

        String currValue = getValue(key);
        comboBox.select(data.getSelection(currValue));

        return comboBox;
    }

    @Override
    protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
        String title = VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_13;
        String message = VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_14;
        return new String[] { title, message };
    }

    private String getSeverity(IVjoSemanticRuleSet ruleSet) {
//    	if (VjoSemanticRulePolicy.GLOBAL_ERROR_POLICY.equals(policy)) {
//    		return ERROR;
//    	} else if (VjoSemanticRulePolicy.GLOBAL_WARNING_POLICY.equals(policy)) {
//    		return WARNING;
//    	} else if (VjoSemanticRulePolicy.GLOBAL_IGNORE_POLICY.equals(policy)) {
//    		return IGNORE;
//    	} 
    	return DEFAULT;
    }
    
    
    private String getSeverity(VjoSemanticRulePolicy policy) {
        if (VjoSemanticRulePolicy.GLOBAL_ERROR_POLICY.equals(policy)) {
            return ERROR;
        } else if (VjoSemanticRulePolicy.GLOBAL_WARNING_POLICY.equals(policy)) {
            return WARNING;
        } else if (VjoSemanticRulePolicy.GLOBAL_IGNORE_POLICY.equals(policy)) {
            return IGNORE;
	    } 
        return DEFAULT;
    }

    @Override
    protected Control createOptionsBlock(Composite parent) {
        fPixelConverter = new PixelConverter(parent);
        setShell(parent.getShell());

        Composite mainComp = new Composite(parent, SWT.NONE);
        mainComp.setFont(parent.getFont());
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        mainComp.setLayout(layout);

        // Create valdiation check box compsoite
        creteValidationCheckBoxComposite(mainComp);

        Composite commonComposite = createStyleTabContent(mainComp);
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
                true);
        gridData.heightHint = fPixelConverter.convertHeightInCharsToPixels(20);
        commonComposite.setLayoutData(gridData);

        // validateSettings(null, null, null);

        return mainComp;
    }

    /**
     * Add by Eric.ma for adding "Enable VJET validation" function Create
     * validation check box composite Didn't create for project preference
     * 
     * @param ancestor
     *            {@link Composite}
     */
    private void creteValidationCheckBoxComposite(Composite ancestor) {
        IProject project = getProject();
        String groupName = null;
        if (project != null) {
            groupName = project.getName();
        }
        if (groupName != null)
            return;

        Composite validationCheckBoxComposite = new Composite(ancestor,
                SWT.None);
        validationCheckbox = new Button(validationCheckBoxComposite, SWT.CHECK);
        validationCheckbox.setText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_15);
        validationCheckbox.setSelection(VjetUIPlugin.getDefault()
                .getPreferenceStore().getBoolean(VJETVALIDATION));
        validationCheckBoxComposite.setLayout(new GridLayout());
    }

    protected void changesSaved() {
        IProject project = getProject();
        String groupName = null;
        if (project != null) {
            groupName = project.getName();
        }

        // Modify by Eric.ma for rvmove extension point
        DefaultValidator.getValidator().ruleChanged(groupName);
        // end of modified
    }

    private void exportValidationPolicy(Composite folder) {
        FileDialog exportDialog = new FileDialog(folder.getShell(), SWT.SAVE);
        IProject p = getProject();
        exportDialog.setFilterExtensions(new String[] { "*.prefs" }); //$NON-NLS-1$
        if (p == null) {
            exportDialog.setText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_17);
        } else {
            exportDialog.setText("Export project:\"" + p.getName() //$NON-NLS-1$
                    + "\"valdiation policy"); //$NON-NLS-1$
        }
        String path = exportDialog.open();
        if (path == null)
            return;
        exportPolicy2File(folder, p, path);

    }

    /**
     * Export policy to user specify file
     * 
     * @param folder {@link Composite}
     * @param p {@link IProject}
     * @param path String
     */
    public static void exportPolicy2File(Composite folder, IProject p,
            String path) {
        File f = new File(path);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                MessageDialog.openError(folder.getShell(), VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_20,
                        VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_21);
                return;
            }
        }
        Properties pro = new Properties();
        String ruleSetName = ""; //$NON-NLS-1$
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
            ruleSetName = ruleSet.getRuleSetName();
            for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                if (p == null) {
                    pro.put(ruleSetName + "." + rule.getRuleName(), rule //$NON-NLS-1$
                            .getGlobalRulePolicy().getProblemSeverity(null)
                            .toString());
                } else {
                    pro.put(ruleSetName + "." + rule.getRuleName(), VjoGroupRulesCache.getInstance().getRulePolicy(p.getName(), rule) //$NON-NLS-1$
                            .getProblemSeverity(null).toString());
                }
            }
        }
        try {
            if (f.canWrite()) {
                pro.store(new FileOutputStream(path), ""); //$NON-NLS-1$
            } else {
                MessageDialog.openError(folder.getShell(), VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_26,
                        VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_27);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Import validation policy
     * 
     * @param folder {@link Composite}
     */
    private void importValidationPolicy(Composite folder) {
        FileDialog exportDialog = new FileDialog(folder.getShell(),
                SWT.READ_ONLY);
        IProject p = getProject();
        exportDialog.setFilterExtensions(new String[] { "*.prefs" }); //$NON-NLS-1$
        if (p == null) {
            exportDialog.setText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_29);
        } else {
            exportDialog.setText(VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_30
                    + p.getName() + "\""); //$NON-NLS-1$
        }
        String path = exportDialog.open();
        if (path == null)
            return;
        importPre2Repo(folder, path);
    }

    /**
     * Modify combobox value via user specify properties;
     * 
     * @param folder
     * @param path
     */
    public void importPre2Repo(Composite folder, String path) {
        File f = new File(path);
        if (!f.exists()) {
            MessageDialog.openError(folder.getShell(), VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_32,
                    VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_33);
        }
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(path));
            String ruleSetName = ""; //$NON-NLS-1$
            String proKey = ""; //$NON-NLS-1$
            for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {
                ruleSetName = ruleSet.getRuleSetName();
                for (IVjoSemanticRule<?> rule : ruleSet.getRules()) {
                    proKey = ruleSetName + "." + rule.getRuleName(); //$NON-NLS-1$
                    PreferenceKey key = getKey(proKey);
                    setValue(key, pro.getProperty(proKey));
                    if (comboPool.get(key) != null) {
                        comboPool.get(key).select(
                                getComboIndex(pro.getProperty(proKey)));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            MessageDialog.openError(folder.getShell(), VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_37,
                    VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_38);
        } catch (IOException e) {
            MessageDialog.openError(folder.getShell(), VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_39,
                    VjetPreferenceMessages.VjetProblemSeveritiesConfigurationBlock_40);
        }
    }

    private int getComboIndex(String property) {
        if (property.equalsIgnoreCase(errorWarningIgnoreLabels[0])) {
            return 0;
        } else if (property.equalsIgnoreCase(errorWarningIgnoreLabels[1])) {
            return 1;
        } else if (property.equalsIgnoreCase(errorWarningIgnoreLabels[2])) {
            return 2;
        }
        return 0;
    }
}
