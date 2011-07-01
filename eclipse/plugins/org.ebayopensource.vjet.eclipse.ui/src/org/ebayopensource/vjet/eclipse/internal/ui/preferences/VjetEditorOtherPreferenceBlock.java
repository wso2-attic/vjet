/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjetEditorOtherPreferenceBlock.java, Aug 12, 2010, 2:22:45 AM, liama. Exp$
 *
 * Copyright (c) 2010-2013 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.internal.ui.editor.VjoEditorMessages;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since 0.1
 * @since JDK 1.6
 * @since Aug 12, 2010,2:22:45 AM
 */
public class VjetEditorOtherPreferenceBlock extends AbstractConfigurationBlock {

    Button button = null;

    public VjetEditorOtherPreferenceBlock(OverlayPreferenceStore store, PreferencePage prefPage) {
        super(store, prefPage);
    }

    @Override
    public Control createControl(Composite parent) {
        Composite buttonComposite = new Composite(parent, SWT.NONE);
        buttonComposite.setLayout(new GridLayout());
        buttonComposite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
        button = new Button(buttonComposite, SWT.CHECK);
        button.setSelection(!VjetUIPlugin.getDefault().getPreferenceStore().getBoolean(VjoEditorMessages.VjoEditor_10));
        button.setText("Show Enable VJO nature dialog");
        return parent;
    }

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock#performOk()
     */
    @Override
    public void performOk() {
        super.performOk();
        VjetUIPlugin.getDefault().getPreferenceStore().setValue(VjoEditorMessages.VjoEditor_10, !button.getSelection()
                );
    }

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlock#performDefaults()
     */
    @Override
    public void performDefaults() {
        button.setSelection(true);
    }

}
