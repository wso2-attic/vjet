/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjetEditorOtherPreferencePage.java, Aug 12, 2010, 2:20:48 AM, liama. Exp$
 *
 * Copyright (c) 2010-2013 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.mod.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since 0.1
 * @since JDK 1.6
 * @since Aug 12, 2010,2:20:48 AM
 */
public class VjetEditorOtherPreferencePage extends AbstractConfigurationBlockPreferencePage {

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#createConfigurationBlock(org.eclipse.dltk.mod.ui.preferences.OverlayPreferenceStore)
     */
    @Override
    protected IPreferenceConfigurationBlock createConfigurationBlock(OverlayPreferenceStore overlayPreferenceStore) {
        return new VjetEditorOtherPreferenceBlock(overlayPreferenceStore, this);
    }

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#getHelpId()
     */
    @Override
    protected String getHelpId() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setDescription()
     */
    @Override
    protected void setDescription() {
        setDescription("Editor other settings");
    }

    /* (non-Javadoc)
     * @see org.eclipse.dltk.mod.ui.preferences.AbstractConfigurationBlockPreferencePage#setPreferenceStore()
     */
    @Override
    protected void setPreferenceStore() {
        setPreferenceStore(VjetUIPlugin.getDefault().getPreferenceStore());
    }
}
