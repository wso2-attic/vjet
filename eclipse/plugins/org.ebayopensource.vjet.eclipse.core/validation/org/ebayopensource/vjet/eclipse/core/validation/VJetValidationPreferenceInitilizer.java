/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VJetValidationPreferenceInitilizer.java, Nov 25, 2009, 1:59:27 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.eclipse.core.validation;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRuleSet;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VJetValidationPreferenceInitilizer extends AbstractPreferenceInitializer{

    Preferences corePreference = VjetPlugin.getDefault().getPluginPreferences();
    
    @Override
    public void initializeDefaultPreferences() {
        corePreference.setDefault(VjetPlugin.VJETVALIDATION, true);
        initializeCustomRuleValue();
    }

    private void initializeCustomRuleValue() {
        VjoSemanticRuleRepo ruleRepo = VjoSemanticRuleRepo.getInstance();
        ruleRepo.restoreDefaultPolicies();
        String keyName = "";
        for (IVjoSemanticRuleSet ruleSet : ruleRepo.getRuleSets()) {     
                //Set default value
                keyName = ruleSet.getRuleSetName();
                corePreference.setDefault(keyName, "default");
        }
    }
}
